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
	
	private final static String PHONE_FORMAT = "^1[0-9]{10}$";
	public boolean isPhone(String phone) {
		if (isEmpty(phone)) {
			return false;
		}
		return phone.matches(PHONE_FORMAT);
	}
	
	private final static String EMAIL_FORMAT = "^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$";
	public boolean isEmail(String email) {
		if ( isEmpty(email) ) {
			return false;
		}
		return email.matches(EMAIL_FORMAT);
	}
	
	public boolean isEmpty(String str) {
		if ( str == null || str.length() == 0 ) {
			return true;
		}
		return false;
	}
	
}
