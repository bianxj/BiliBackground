package http.response;

import java.util.List;

public class ResponseBatchInsertManager extends BaseResponse {

	private List<String> errorMsgs;

	public List<String> getErrorMsgs() {
		return errorMsgs;
	}

	public void setErrorMsgs(List<String> errorMsgs) {
		this.errorMsgs = errorMsgs;
	}
	
}
