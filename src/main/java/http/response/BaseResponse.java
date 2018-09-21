package http.response;

import http.ResponseMessage;

public class BaseResponse {

	private int responseId;
	private String desc;
	
	public int getResponseId() {
		return responseId;
	}
	public void setResponseId(int responseId) {
		this.responseId = responseId;
		this.desc = ResponseMessage.getDesc(responseId);
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
