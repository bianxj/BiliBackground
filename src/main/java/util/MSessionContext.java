package util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

public class MSessionContext {

	private static MSessionContext context;
	private Map<String,HttpSession> sessionMap;
	
	public static MSessionContext getInstance() {
		if ( context == null ) {
			context = new MSessionContext();
		}
		return context;
	}
	
	private MSessionContext() {
		sessionMap = new HashMap<>();
	}
	
	public synchronized void addSession(HttpSession session) {
		if ( session != null ) {
			sessionMap.put(session.getId(), session);
		}
	}
	
	public synchronized void removeSession(HttpSession session) {
		if ( session != null ) {
			sessionMap.remove(session.getId());
		}
	}
	
	public synchronized HttpSession getSession(String sessionId) {
		if ( sessionId == null ) {
			return null;
		}
		return sessionMap.get(sessionId);
	}
	
}
