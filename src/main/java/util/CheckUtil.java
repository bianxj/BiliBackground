package util;

public class CheckUtil {

	private static CheckUtil util;
	
	public static CheckUtil getInstance() {
		if ( util == null ) {
			util = new CheckUtil();
		}
		return util; 
	}
	
	private CheckUtil() {
		
	}
	
	private final static String PASSWORD_FORMAT = "^[A-Za-z0-9]{6,16}$";
	public boolean isPassword(String pwd) {
		if ( isEmpty(pwd) ) {
			return false;
		}
		return pwd.matches(PASSWORD_FORMAT);
	} 
	
	public boolean isEmpty(String str) {
		if ( str == null || str.length() == 0 ) {
			return true;
		}
		return false;
	}
	
}
