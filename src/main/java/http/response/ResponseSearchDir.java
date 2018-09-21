package http.response;

import java.util.List;

import entity.result.ResultDir;

public class ResponseSearchDir extends BaseResponse {

	private List<ResultDir> dirs;

	public List<ResultDir> getDirs() {
		return dirs;
	}

	public void setDirs(List<ResultDir> dirs) {
		this.dirs = dirs;
	}
	
}
