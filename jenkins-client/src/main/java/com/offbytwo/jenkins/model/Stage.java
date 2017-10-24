package com.offbytwo.jenkins.model;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class Stage extends BaseModel {
	
	private String jobUrl; 
	private String id;
    private String name;
    private String execNode;
    private String status;
    private Date startTimeMillis;
    private long durationMillis;
    
	public Stage() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getExecNode() {
		return execNode;
	}
	public void setExecNode(String execNode) {
		this.execNode = execNode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getStartTimeMillis() {
		return startTimeMillis;
	}
	public void setStartTimeMillis(Date startTimeMillis) {
		this.startTimeMillis = startTimeMillis;
	}
	public long getDurationMillis() {
		return durationMillis;
	}
	public void setDurationMillis(long durationMillis) {
		this.durationMillis = durationMillis;
	}
	
	public String getJobUrl() {
		return jobUrl;
	}
	public void setJobUrl(String jobUrl) {
		this.jobUrl = jobUrl;
	}
	
	public StageFlowNodes getStageFlowNodes() throws IOException {
		StageFlowNodes nodes = client.get(getJobUrl() + "execution/node/" + getId() + "/wfapi/describe", StageFlowNodes.class);
		nodes.setClient(this.client);
        return nodes;
    }
	
	public String getConsoleOutput() throws IOException {
		StageFlowNodes nodes = getStageFlowNodes();
		List<StageFlowNode> stageFlowNodes = nodes.getStageFlowNodes(); 
		StringBuffer log = new StringBuffer();
		for (StageFlowNode node:stageFlowNodes ) {
			StageLog stageLog = client.get(getJobUrl() + "execution/node/" + node.getId() + "/wfapi/log", StageLog.class);
			log.append(node.getId() + ", " + node.getStatus() + ", " + node.getName() + ", " +  stageLog.getText());
		}
		return log.toString();
	}

	@Override
	public String toString() {
		return "Stage [id=" + id + ", name=" + name + ", execNode=" + execNode + ", status=" + status
				+ ", startTimeMillis=" + startTimeMillis + ", durationMillis=" + durationMillis + "]";
	}


	

}
