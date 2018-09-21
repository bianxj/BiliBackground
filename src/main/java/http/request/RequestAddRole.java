package http.request;

import java.util.List;

public class RequestAddRole {

	private String name;
	private List<Integer> dirIds;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Integer> getDirIds() {
		return dirIds;
	}
	public void setDirIds(List<Integer> dirIds) {
		this.dirIds = dirIds;
	}
	
}
