package com.offbytwo.jenkins.model;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.apache.http.entity.ContentType;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class PendingInputActions extends BaseModel {

	private String id;
	private List<PendingInputActionsParameter> inputs;
	private String message;
	private String proceedUrl;
	private String abortUrl;
	private String redirectApprovalUrl;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getProceedUrl() {
		return proceedUrl;
	}

	public void setProceedUrl(String proceedUrl) {
		this.proceedUrl = proceedUrl;
	}

	public String getAbortUrl() {
		return abortUrl;
	}

	public void setAbortUrl(String abortUrl) {
		this.abortUrl = abortUrl;
	}

	public String getRedirectApprovalUrl() {
		return redirectApprovalUrl;
	}

	public void setRedirectApprovalUrl(String redirectApprovalUrl) {
		this.redirectApprovalUrl = redirectApprovalUrl;
	}

	public List<PendingInputActionsParameter> getInputs() {
		return inputs;
	}

	public void setInputs(List<PendingInputActionsParameter> inputs) {
		this.inputs = inputs;
	}

	public void proceed() throws IOException {
		JSONObject inputs = new JSONObject();
		JSONArray inputNVPs = new JSONArray();
		inputs.put("parameter", inputNVPs);
		String inputSubmitParams = "json=" + URLEncoder.encode(inputs.toString(), "UTF-8");
		this.getClient().post_text(getProceedUrl().replace("/job/", "job/"), inputSubmitParams,
				ContentType.create("application/x-www-form-urlencoded", "UTF-8"), true);
	}

	public void proceed(Map<String, Object> parameter) throws IOException {
		JSONObject inputs = new JSONObject();
		JSONArray inputNVPs = new JSONArray();
		for (String key : parameter.keySet()) {
			addNVP(inputNVPs, key, parameter.get(key));
		}
        inputs.put("parameter", inputNVPs);
		String inputSubmitParams = "json=" + URLEncoder.encode(inputs.toString(), "UTF-8");
		this.getClient().post_text(getProceedUrl().replace("/job/", "job/"), inputSubmitParams,
				ContentType.create("application/x-www-form-urlencoded", "UTF-8"), true);
	}

	public void abort() throws IOException {
		this.getClient().post_form(getAbortUrl(), null, true);
	}

	private void addNVP(JSONArray inputNVPs, String name, Object value) {
		JSONObject nvp = new JSONObject();
		nvp.put("name", name);
		nvp.put("value", value);
		inputNVPs.add(nvp);
	}

	@Override
	public String toString() {
		return "PendingInputActions [id=" + id + ", inputs=" + inputs + ", message=" + message + ", proceedUrl="
				+ proceedUrl + ", abortUrl=" + abortUrl + ", redirectApprovalUrl=" + redirectApprovalUrl + "]";
	}

}
