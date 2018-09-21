package http.request;

import java.util.List;

public class RequestUpdateRole {

	private int id;
	private String name;
	private List<Integer> dirIds;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
