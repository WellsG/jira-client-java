package com.example.client.jira;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JiraClientImpl implements JiraClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(JiraClientImpl.class);

    private static final String ISSUE_SERVICE = "issue";
    private static final String ISSUE_SEARCH_SERVICE = "search";

    private final String serverURL;
    private final String user;
    private final String passwd;

    private JiraClientImpl(String serverURL, String user, String passwd) {
        LOGGER.info("Starting Jira client for server url: {}", serverURL);
        this.serverURL = serverURL;
        this.user = user;
        this.passwd = passwd;
    }

    public static JiraClient create(String url, String user, String passwd) {
        final JiraClientImpl impl = new JiraClientImpl(url, user, passwd);
        return impl;
    }

    public Issue createIssue(Issue issue) throws Exception {
        String encodedParamStr = getEncodedParamString(issue);
        LOGGER.info(encodedParamStr);
        String response = sendRequest(ISSUE_SERVICE, "POST", encodedParamStr);
        return new Gson().fromJson(response, Issue.class);
    }

    public void assignIssue(String issueId, String assignee) throws Exception {
        String issueAssignee = ISSUE_SERVICE + issueId + "/assignee";
        Map<String, String> map = new HashMap<>();
        map.put("name", assignee);
        Gson gson = new Gson();
        sendRequest(issueAssignee, "PUT", gson.toJson(map));
    }

    public void closeIssue(String issueId) throws Exception {
        Map<String, Object> parameterMap = new HashMap<>();
        Map<String, String> transitionMap = new HashMap<>();
        transitionMap.put("id", "5");
        parameterMap.put("transition", transitionMap);

        Map<String, Object> fieldMap = new HashMap<>();

        Map<String, String> resolutionMap = new HashMap<>();
        resolutionMap.put("name", "Fixed");

        fieldMap.put("resolution", resolutionMap);

        parameterMap.put("fields", fieldMap);

        String issueAssignee = ISSUE_SERVICE + issueId + "/transitions";

        Gson gson = new Gson();
        String gsonString = gson.toJson(parameterMap);
        sendRequest(issueAssignee, "POST", gsonString);
    }

    public Set<Issue> getIssuesByIds(List<String> ids, List<JiraField> fields) throws Exception {
        // Could provide an empty jql String, but this would perform a full DB sweep, which is probably not what anybody wants.
        if (CollectionUtils.isEmpty(ids)) throw new IllegalArgumentException("No Jira Issue ids were specified!");
        String jql = ids.size() == 1 ? "id=" + ids.get(0) : "id in " + idsList2String(ids);

        List<String> _fields = fieldEnum2StringList(fields);
        if (_fields.isEmpty()) _fields.add(JiraField.ALL.fieldId());

        JiraRestSearchRequest jrsr = new JiraRestSearchRequest(jql, _fields);
        String response = sendRequest(ISSUE_SEARCH_SERVICE, "POST", jrsr.getEncodedRestSearchString());

        JiraRestSearchJsonResult searchResult = JiraRestSearchJsonResult.create(response);
        return searchResult.getIssues();
    }

    private String getEncodedParamString(Issue issue) throws UnsupportedEncodingException {
        Gson gson = new Gson();

        Map<String, Issue> map = new HashMap<>();
        map.put("fields", issue);

        return gson.toJson(map);
    }

	@Override
	public List<Comment> getComments(String issueId) throws Exception {
		String comments = ISSUE_SERVICE + "/" + issueId + "/comment";
		Map<String, String> map = new HashMap<>();
        map.put("orderBy", "created");
        Gson gson = new Gson();
        String response = sendRequest(comments, "GET", null);
        LOGGER.info(response);
        JiraCommentPagedResult pagedList = null;
		if (!StringUtils.isEmpty(response)) {
		    try {
		        pagedList = new Gson().fromJson(response, new TypeToken<JiraCommentPagedResult>(){}.getType());
		    } catch (Exception e) {
		        LOGGER.error("", e);
		    }
		}
		return pagedList != null ? pagedList.getComments() : null;
	}

    //TODO Accomodate RestUtil so that it can be used also for the Jira purpose? (basically allow setting the content-type, which is now fixed
    private String sendRequest(String serviceUrl, String requestMethod, String encodedParamStr) throws Exception {
        LOGGER.info("Service: {}", serviceUrl);
        LOGGER.info("Params: {}", encodedParamStr);
    	StringBuffer sb;
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        BufferedReader br = null;
        try {
            URL searchUrl = new URL(this.serverURL + serviceUrl);
            conn = (HttpURLConnection) searchUrl.openConnection();
            conn.setRequestMethod(requestMethod);
            conn.setRequestProperty("User-Agent", "Mozilla/4.0");
            conn.setRequestProperty("Content-Type", "application/json");

            conn.setRequestProperty("Authorization", "Basic " + getBasicAuthentication());

            if (encodedParamStr != null) {
                conn.setRequestProperty("Content-Length", Integer.toString(encodedParamStr.getBytes().length));
            }
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);

            if (!"GET".equalsIgnoreCase(requestMethod)) {
	            dos = new DataOutputStream(conn.getOutputStream());
	            if (encodedParamStr != null) {
	                dos.writeBytes(encodedParamStr);
	            }
	            dos.flush();
	            dos.close();
            }

            /*
             * InputStream inputStream = conn.getErrorStream(); if (null != inputStream) { List<String> a =
             * IOUtils.readLines(inputStream); for (String string : a) { System.out.println(string); } }
             */
            br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String strLine;
            sb = new StringBuffer();
            while ((strLine = br.readLine()) != null) {
                sb.append(strLine);
                sb.append("\n");
            }
            br.close();

            int rc = conn.getResponseCode();
            if (rc == 200 || rc == 201) {
                return sb.toString();
            } else {
                LOGGER.info("http response code error: {}", rc);
                LOGGER.info("error detail: {}", sb.toString());
                throw new Exception("http response code error: "
    					+ rc + "  " + sb.toString());
            }
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
            if (dos != null) {
                dos.close();
            }
            if (br != null) {
                br.close();
            }
        }
    }

    // TODO switch to cookie-based or Oauth-based authentication?
    private String getBasicAuthentication() {
        return Base64.encodeBase64String((user + ":" + passwd).getBytes()).trim();
    }

    private String idsList2String(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) return null;
        StringBuilder sb = new StringBuilder("(");
        for (int i = 0; i<ids.size(); i++) {
            sb.append(ids.get(i)).append(i==ids.size()-1 ? "" : ",");
        }
        sb.append(")");
        return sb.toString();
    }

    private List<String> fieldEnum2StringList(List<JiraField> enumList) {
        List<String> result = new ArrayList<>();
        if (enumList != null) {
            for (JiraField jf : enumList) {
                result.add(jf.fieldId());
            }
        }
        return result;
    }

}
