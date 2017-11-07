package com.offbytwo.jenkins.model;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;

public class PipelineBuild extends Build {

	public static final PipelineBuild BUILD_HAS_NEVER_RUN = new PipelineBuild(-1, -1, "UNKNOWN");

	private PipelineBuild(int number, int queueId, String url) {
		setNumber(number);
		setQueueId(queueId);
		setUrl(url);
	}

	public PipelineBuild() {
	}

	public PipelineBuild(Build from) {
		this(from.getNumber(), from.getUrl());
	}

	public PipelineBuild(int number, String url) {
		super(number, url);
	}

	public WorkflowRun getWorkflowRun() throws IOException {
		String url = getUrl();
		WorkflowRun workflowRun = client.get(url + "wfapi/describe", WorkflowRun.class);
		workflowRun.setClient(client);
		List<Stage> result = workflowRun.getStages();
		for (Stage stage : result) {
			stage.setJobUrl(url);
			stage.setClient(client);
		}
		return workflowRun;

	}

	public List<PendingInputActions> getPendingInputActions() throws IOException {
		String url = getUrl();
		List<PendingInputActions> resultList = client.get(url + "wfapi/pendingInputActions", new TypeReference<List<PendingInputActions>>(){});
		for (PendingInputActions pa : resultList) {
			pa.setClient(client);
		}
		return resultList;
	}

}
