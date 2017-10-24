package com.offbytwo.jenkins.model;

public class PendingInputActionsParameter extends BaseModel {
	private String type;
	private String name; 
	private String description;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "PendingInputActionParameter [type=" + type + ", name=" + name + ", description=" + description + "]";
	} 
	
}
