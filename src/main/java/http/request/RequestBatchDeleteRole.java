package http.request;

import java.util.List;

public class RequestBatchDeleteRole extends BaseRequest {

	private List<Integer> ids;

	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}
	
}
