package filter;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import http.ResponseMessage;
import http.response.BaseResponse;
import util.GsonUtil;
import util.MSessionContext;

public class SessionCheckFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		httpResponse.setHeader("Content-Type", "text/html");
		if ( !httpRequest.getRequestURI().endsWith("login.do") ) {
			String sessionId = httpRequest.getHeader("sessionId");
			HttpSession session = MSessionContext.getInstance().getSession(sessionId);
			if ( session == null ) {
				BaseResponse resp = new BaseResponse();
				resp.setResponseId(ResponseMessage.LOGIN_TIMEOUT);
				OutputStream os = httpResponse.getOutputStream();
				os.write(GsonUtil.getInstance().toJson(resp).getBytes());
				os.close();
				return;
			}
			session.setAttribute("PrevRequestTime", System.currentTimeMillis());
		}
		chain.doFilter(request, response);
	}

}
