package http.response;

import entity.result.ResultManager;

public class ResponseLogin extends BaseResponse {

	private ResultManager manager;
	private String sessionId;

	public ResultManager getManager() {
		return manager;
	}

	public void setManager(ResultManager manager) {
		this.manager = manager;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
}
