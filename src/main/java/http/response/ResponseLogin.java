package http.response;

import entity.table.Manager;

public class ResponseLogin extends BaseResponse {

	private Manager manager;
	private String sessionId;

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
}
