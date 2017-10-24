package com.offbytwo.jenkins.model;

import java.io.IOException;

public class PipelineJob extends Job {

	public PipelineJob() {
		super();
	}

	public PipelineJob(String name, String url, String fullName) {
		super(name, url, fullName);
	}

	public PipelineJob(String name, String url) {
		super(name, url);
	}
	
	public PipelineJobWithDetails pipelineDetails() throws IOException {
        return client.get(getUrl(), PipelineJobWithDetails.class);
    }
	
}
