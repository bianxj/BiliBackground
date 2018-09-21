package http;

import java.util.HashMap;
import java.util.Map;

public class ResponseMessage {

	private static Map<Integer, String> responseMap;
	
	public final static int SUCCESS = 0; 
	
	public final static int LOGIN_FAILED = 100;
	public final static int LOGIN_TIMEOUT = 101;
	
	public final static int MANAGER_NAME_EXISTS = 102;
	public final static int ERROR_PASSWORD_FORMAT = 103;
	public final static int ERROR_PHONE_EMPTY = 104;
	public final static int ERROR_EMAIL_EMPTY = 105;
	public final static int ERROR_ROLE_EMPTY = 106;
	public final static int ERROR_PASSWORD_INCONFORMITY = 107;
	public final static int ERROR_NAME_EMPTY = 108;
	public final static int ERROR_ROLE_NOT_EXISTS = 109;
	
	public final static int ERROR_MANAGER_NOT_EXISTS = 110;
	
	public final static int ERROR_EMPTY_MESSAGE = 111;
	public final static int ERROR_MANAGER_ID = 112;
	
	public final static int ROLE_NAME_EXISTS = 113;
	public final static int ROLE_IS_USED = 114;
	
	public final static int ERROR_TEMPLATE_FORMAT = 115;
	
	public final static int ERROR_PHONE_FORMAT = 116;
	public final static int ERROR_EMAIL_FORMAT = 117;
	public final static int ERROR_PASSWORD_EMPTY = 118;
	
	static {
		responseMap = new HashMap<>();
		responseMap.put(SUCCESS, "操作成功");
		responseMap.put(LOGIN_FAILED, "用户名或密码错误");
		responseMap.put(LOGIN_TIMEOUT, "登录超时");
		
		responseMap.put(MANAGER_NAME_EXISTS, "登录名已存在");
		responseMap.put(ERROR_PASSWORD_FORMAT, "密码格式不正确");
		responseMap.put(ERROR_PHONE_EMPTY, "手机号不能为空");
		responseMap.put(ERROR_PHONE_FORMAT, "手机号格式不正确");
		responseMap.put(ERROR_EMAIL_EMPTY, "邮箱不能为空");
		responseMap.put(ERROR_EMAIL_FORMAT, "邮箱格式不正确");
		responseMap.put(ERROR_ROLE_EMPTY, "用户角色不能为空");
		responseMap.put(ERROR_PASSWORD_EMPTY, "密码不能为空");
		responseMap.put(ERROR_PASSWORD_INCONFORMITY, "两次密码不一致");
		responseMap.put(ERROR_NAME_EMPTY, "登录名不能为空");
		responseMap.put(ERROR_ROLE_NOT_EXISTS, "用户角色不存在");
		responseMap.put(ERROR_MANAGER_NOT_EXISTS, "该用户不存在");
		
		responseMap.put(ERROR_EMPTY_MESSAGE, "上传信息为空");
		responseMap.put(ERROR_MANAGER_ID, "管理员ID错误");
		
		responseMap.put(ROLE_NAME_EXISTS, "角色名已存在");
		responseMap.put(ROLE_IS_USED, "该角色已有用户使用");
		
		responseMap.put(ERROR_TEMPLATE_FORMAT, "错误的模板格式");
	}
	
	public static String getDesc(int code) {
		return responseMap.get(code);
	}
	
}
