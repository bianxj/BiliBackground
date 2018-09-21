package http.response;

import java.util.List;

import entity.result.ResultRole;

public class ResponseSearchRoles extends BaseResponse {

	private List<ResultRole> roles;

	public List<ResultRole> getRoles() {
		return roles;
	}

	public void setRoles(List<ResultRole> roles) {
		this.roles = roles;
	}
	
}
