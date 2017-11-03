package com.offbytwo.jenkins;

import java.io.IOException;
import java.util.Map;

import com.offbytwo.jenkins.model.Build;
import com.offbytwo.jenkins.model.BuildResult;
import com.offbytwo.jenkins.model.BuildWithDetails;
import com.offbytwo.jenkins.model.JobWithDetails;
import com.offbytwo.jenkins.model.PipelineBuild;
import com.offbytwo.jenkins.model.PipelineJobWithDetails;
import com.offbytwo.jenkins.model.QueueItem;
import com.offbytwo.jenkins.model.QueueReference;

/**
 * collection of convenient methods which use methods from {@link JenkinsServer}
 * etc.
 * 
 * @author Karl Heinz Marbaise
 *
 */
public class JenkinsTriggerHelper {

    private final JenkinsServer server;
    private final Long retryInterval;
    private static final Long DEFAULT_RETRY_INTERVAL = 200L;

    public JenkinsTriggerHelper(JenkinsServer server) {
        this.server = server;
        this.retryInterval = DEFAULT_RETRY_INTERVAL;
    }

    public JenkinsTriggerHelper(JenkinsServer server, Long retryInterval) {
        this.server = server;
        this.retryInterval = retryInterval;
    }

    /**
     * This method will trigger a build of the given job and will wait until the
     * builds is ended or if the build has been cancelled.
     * 
     * @param jobName The name of the job which should be triggered.
     * @return In case of an cancelled job you will get
     *         {@link BuildWithDetails#getResult()}
     *         {@link BuildResult#CANCELLED}. So you have to check first if the
     *         build result is {@code CANCELLED}.
     * @throws IOException in case of errors.
     * @throws InterruptedException In case of interrupts.
     */
    public BuildWithDetails triggerJobAndWaitUntilFinished(String jobName) throws IOException, InterruptedException {
        return triggerJobAndWaitUntilFinished(jobName, false);
    }

    /**
     * This method will trigger a build of the given job and will wait until the
     * builds is ended or if the build has been cancelled.
     * 
     * @param jobName The name of the job which should be triggered.
     * @param params the job parameters
     * @return In case of an cancelled job you will get
     *         {@link BuildWithDetails#getResult()}
     *         {@link BuildResult#CANCELLED}. So you have to check first if the
     *         build result is {@code CANCELLED}.
     * @throws IOException in case of errors.
     * @throws InterruptedException In case of interrupts.
     */
    public BuildWithDetails triggerJobAndWaitUntilFinished(String jobName, Map<String, String> params)
            throws IOException, InterruptedException {
        return triggerJobAndWaitUntilFinished(jobName, params, false);
    }

    /**
     * This method will trigger a build of the given job and will wait until the
     * builds is ended or if the build has been cancelled.
     * 
     * @param jobName The name of the job which should be triggered.
     * @param params the job parameters
     * @param crumbFlag set to <code>true</code> or <code>false</code>.
     * @return In case of an cancelled job you will get
     *         {@link BuildWithDetails#getResult()}
     *         {@link BuildResult#CANCELLED}. So you have to check first if the
     *         build result is {@code CANCELLED}.
     * @throws IOException in case of errors.
     * @throws InterruptedException In case of interrupts.
     */
    public BuildWithDetails triggerJobAndWaitUntilFinished(String jobName, Map<String, String> params,
            boolean crumbFlag) throws IOException, InterruptedException {
        JobWithDetails job = this.server.getJob(jobName);
        QueueReference queueRef = job.build(params, crumbFlag);

        return triggerJobAndWaitUntilFinished(jobName, queueRef);
    }

    /**
     * This method will trigger a build of the given job and will wait until the
     * builds is ended or if the build has been cancelled.
     * 
     * @param jobName The name of the job which should be triggered.
     * @param crumbFlag set to <code>true</code> or <code>false</code>.
     * @return In case of an cancelled job you will get
     *         {@link BuildWithDetails#getResult()}
     *         {@link BuildResult#CANCELLED}. So you have to check first if the
     *         build result is {@code CANCELLED}.
     * @throws IOException in case of errors.
     * @throws InterruptedException In case of interrupts.
     */
    public BuildWithDetails triggerJobAndWaitUntilFinished(String jobName, boolean crumbFlag)
            throws IOException, InterruptedException {
        JobWithDetails job = this.server.getJob(jobName);
        QueueReference queueRef = job.build(crumbFlag);

        return triggerJobAndWaitUntilFinished(jobName, queueRef);
    }

    /**
     * @param jobName The name of the job.
     * @param queueRef {@link QueueReference}
     * @return In case of an cancelled job you will get
     *         {@link BuildWithDetails#getResult()}
     *         {@link BuildResult#CANCELLED}. So you have to check first if the
     *         build result is {@code CANCELLED}.
     * @throws IOException in case of errors.
     * @throws InterruptedException In case of interrupts.
     */
    private BuildWithDetails triggerJobAndWaitUntilFinished(String jobName, QueueReference queueRef)
            throws IOException, InterruptedException {
        JobWithDetails job = this.server.getJob(jobName);
        QueueItem queueItem = this.server.getQueueItem(queueRef);

        while (!queueItem.isCancelled() && job.isInQueue()) {
            Thread.sleep(retryInterval);
            job = this.server.getJob(jobName);
            queueItem = this.server.getQueueItem(queueRef);
        }

        Build build = server.getBuild(queueItem);
        if (queueItem.isCancelled()) {
            return build.details();
        }

        boolean isBuilding = build.details().isBuilding();
        while (isBuilding) {
            Thread.sleep(retryInterval);
            isBuilding = build.details().isBuilding();
        }

        return build.details();
    }
    
	/**
	 * This method will trigger a build of the given Pipeline Job and will wait
	 * until the Job is building until if the build has been cancelled.
	 * 
	 * @param jobName
	 *            The name of the Pipeline Job which should be triggered.
	 * @param params
	 *            the job parameters
	 * @param crumbFlag
	 *            set to <code>true</code> or <code>false</code>.
	 * @return a {@link PipelineBuild} which is either Building or Canceled
	 * @throws IOException
	 *             in case of errors.
	 * @throws InterruptedException
	 *             In case of interrupts.
	 */
	public PipelineBuild triggerPipelineJobAndWaitUntilBuilding(String jobName, Map<String, String> params,
			boolean crumbFlag) throws IOException, InterruptedException {
		PipelineJobWithDetails job = this.server.getPipelineJob(jobName);
		QueueReference queueRef = job.build(params, crumbFlag);
		return triggerPipelineJobAndWaitUntilBuilding(jobName, queueRef);
	}

	/**
	 * @param jobName
	 *            The name of the Pipeline Job.
	 * @param queueRef
	 *            {@link QueueReference}
	 * @return a {@link PipelineBuild} which is either Building or Canceled
	 * @throws IOException
	 *             in case of errors.
	 * @throws InterruptedException
	 *             In case of interrupts.
	 */
	private PipelineBuild triggerPipelineJobAndWaitUntilBuilding(String jobName, QueueReference queueRef)
			throws IOException, InterruptedException {
		PipelineJobWithDetails job = server.getPipelineJob(jobName);
		QueueItem queueItem = server.getQueueItem(queueRef);
		while (!queueItem.isCancelled() && (job.isInQueue() || queueItem.getExecutable() == null)) {
			Thread.sleep(retryInterval);
			job = server.getPipelineJob(jobName);
			queueItem = server.getQueueItem(queueRef);
		}
		Build build = server.getBuild(queueItem);
		while (true) {
			BuildWithDetails buildWithDetails = build.details();
			if (	buildWithDetails.isBuilding()) {
				job = this.server.getPipelineJob(jobName);
				return job.getLastBuild();
			}
			BuildResult buildResult = buildWithDetails.getResult();
			if (buildResult != null && (buildResult.equals(BuildResult.ABORTED) 
					|| buildResult.equals(BuildResult.SUCCESS) || buildResult.equals(BuildResult.CANCELLED)
					|| buildResult.equals(BuildResult.UNSTABLE))) {
				job = this.server.getPipelineJob(jobName);
				return job.getLastBuild();
			}
			Thread.sleep(retryInterval);
		}

	}
}
