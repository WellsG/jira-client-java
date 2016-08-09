package com.example.client.jira;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JiraCommentPagedResult {

    private static final Logger LOGGER = LoggerFactory.getLogger(JiraRestSearchJsonResult.class);

    private Integer startAt;
    private Integer maxResults;
    private Integer total;
    private List<Comment> comments;

	public Integer getStartAt() {
		return startAt;
	}
	public void setStartAt(Integer startAt) {
		this.startAt = startAt;
	}
	public Integer getMaxResults() {
		return maxResults;
	}
	public void setMaxResults(Integer maxResults) {
		this.maxResults = maxResults;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

}
