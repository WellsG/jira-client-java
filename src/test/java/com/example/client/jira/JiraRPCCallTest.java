package com.example.client.jira;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class JiraRPCCallTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(JiraRPCCallTest.class);
	private JiraClient jiraClient;

	@BeforeMethod
	public void setupHandler() {
		jiraClient = JiraClientImpl.create("https://com.example.client/rest/api/2/", "user",
				"passwd");
	}

	private Issue generateIssue() {
		Issue issue = new Issue();
		String summary = "JIRA Rest API Test 3";
		String description = "description";
		Project project = new Project();
		project.setKey("MT");
		issue.setProject(project);
		issue.setSummary(summary);
		issue.setDescription(description);
		IssueType issueType = new IssueType();
		issueType.setName("Task");
		issue.setIssuetype(issueType);
		Component component = new Component();
		component.setName("client");
		List<Component> components = new ArrayList<Component>();
		components.add(component);
		issue.setComponents(components);
		return issue;
	}

	@Test(enabled = true)
	public void testCreateIssue() throws Exception {
		Issue issue = jiraClient.createIssue(generateIssue());
		LOGGER.info("#{}, key: {}", issue.getId(), issue.getKey());
		assertNotNull(issue);
	}

	@Test(enabled = true)
	public void testGetIssue() throws Exception {
		Set<Issue> issues = jiraClient.getIssuesByIds(Arrays.asList("MT-1"), Arrays.asList(JiraField.STATUS));
		for (Issue issue : issues) {
			assertEquals(issue.getStatus().getName(), "To Do");
		}
		assertTrue(issues.size() == 1);
	}

}
