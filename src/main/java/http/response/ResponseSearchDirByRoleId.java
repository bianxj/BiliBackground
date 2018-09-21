package http.response;

import java.util.List;

import entity.result.ResultDir;
import entity.result.ResultRole;

public class ResponseSearchDirByRoleId extends BaseResponse {

	private ResultRole role;
	private List<ResultDir> dirs;

	public List<ResultDir> getDirs() {
		return dirs;
	}

	public void setDirs(List<ResultDir> dirs) {
		this.dirs = dirs;
	}

	public ResultRole getRole() {
		return role;
	}

	public void setRole(ResultRole role) {
		this.role = role;
	}
	
}
