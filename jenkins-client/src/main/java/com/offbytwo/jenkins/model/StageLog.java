package com.offbytwo.jenkins.model;

public class StageLog extends BaseModel {

	private String nodeId;
	private String nodeStatus;
	private Integer length;
	private Boolean hasMore;
	private String text;
	private String consoleUrl;

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeStatus() {
		return nodeStatus;
	}

	public void setNodeStatus(String nodeStatus) {
		this.nodeStatus = nodeStatus;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Boolean getHasMore() {
		return hasMore;
	}

	public void setHasMore(Boolean hasMore) {
		this.hasMore = hasMore;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getConsoleUrl() {
		return consoleUrl;
	}

	public void setConsoleUrl(String consoleUrl) {
		this.consoleUrl = consoleUrl;
	}

	@Override
	public String toString() {
		return "StageLog [nodeId=" + nodeId + ", nodeStatus=" + nodeStatus + ", length=" + length + ", hasMore="
				+ hasMore + ", text=" + text + ", consoleUrl=" + consoleUrl + "]";
	}

}
