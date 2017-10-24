package com.offbytwo.jenkins.model;

import java.util.Date;
import java.util.List;

public class StageFlowNodes extends BaseModel {
	private String id; 
	private String name;
	private String execNode; 
	private String status; 
	private String parameterDescription;
	private Date startTimeMillis;
	private Integer durationMillis;
	private Integer pauseDurationMillis;
	private List<StageFlowNode> stageFlowNodes;
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
	public String getParameterDescription() {
		return parameterDescription;
	}
	public void setParameterDescription(String parameterDescription) {
		this.parameterDescription = parameterDescription;
	}
	public Date getStartTimeMillis() {
		return startTimeMillis;
	}
	public void setStartTimeMillis(Date startTimeMillis) {
		this.startTimeMillis = startTimeMillis;
	}
	public Integer getDurationMillis() {
		return durationMillis;
	}
	public void setDurationMillis(Integer durationMillis) {
		this.durationMillis = durationMillis;
	}
	public Integer getPauseDurationMillis() {
		return pauseDurationMillis;
	}
	public void setPauseDurationMillis(Integer pauseDurationMillis) {
		this.pauseDurationMillis = pauseDurationMillis;
	}
	public List<StageFlowNode> getStageFlowNodes() {
		return stageFlowNodes;
	}
	public void setStageFlowNodes(List<StageFlowNode> stageFlowNodes) {
		this.stageFlowNodes = stageFlowNodes;
	}
	@Override
	public String toString() {
		return "StageFlowNodes [id=" + id + ", name=" + name + ", execNode=" + execNode + ", status=" + status
				+ ", parameterDescription=" + parameterDescription + ", startTimeMillis=" + startTimeMillis
				+ ", durationMillis=" + durationMillis + ", pauseDurationMillis=" + pauseDurationMillis
				+ ", stageFlowNodes=" + stageFlowNodes + "]";
	}

	

}
