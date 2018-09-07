package controller;

import java.io.Closeable;
import java.io.IOException;

public class BaseController {
	
//	protected void baseRequest(HttpServletRequest request,HttpServletResponse response,RequestCallBack callback,boolean checkTimeOut) {
//		InputStream is = null;
//		OutputStream os = null;
//		String sessionId = null;
//		try {
//			is = request.getInputStream();
//			os = response.getOutputStream();
//			response.setHeader("Content-Type" , "text/html");
//
//			if ( checkTimeOut ) {
//				sessionId = request.getHeader("sessionId");
//				HttpSession session = MSessionContext.getInstance().getSession(sessionId);
//				if ( session == null ) {
//					BaseResponse resp = new BaseResponse();
//					resp.setResponseId(ResponseMessage.LOGIN_TIMEOUT);
//					os.write(GsonUtil.getInstance().toJson(resp).getBytes());
//				} else {
//					callback.request(request,response);
//				}
//			} else {
//				callback.request(request,response);
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (Throwable e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			if ( is != null ) {
//				try {
//					is.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//			if ( os != null ) {
//				try {
//					os.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
//	}
//
//	public interface RequestCallBack{
//		public void request(HttpServletRequest req,HttpServletResponse res) throws Throwable;
//	}
	
	protected void closeStream(Closeable stream) {
		if ( stream != null ) {
			try {
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
