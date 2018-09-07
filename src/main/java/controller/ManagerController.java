package controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mysql.cj.api.Session;
import com.mysql.cj.api.io.ServerSession;

import dao.ManagerDao;
import entity.table.Manager;
import http.ResponseMessage;
import http.request.BaseRequest;
import http.request.RequestCheckLogin;
import http.request.RequestDeleteManager;
import http.request.RequestInsertManager;
import http.request.RequestLogin;
import http.request.RequestSearchManagers;
import http.response.BaseResponse;
import http.response.ResponseBatchInsertManager;
import http.response.ResponseCheckLogin;
import http.response.ResponseLogin;
import http.response.ResponseSearchManagers;
import http.response.ResponseSearchRoles;
import util.CheckUtil;
import util.FileResovler;
import util.FormatUtil;
import util.GsonUtil;
import util.HttpUtil;
import util.MSessionContext;

@Controller
@RequestMapping("/Manager")
public class ManagerController extends BaseController {

	@Autowired
	private ManagerDao managerDao;
	
	@RequestMapping(value="/login.do",method=RequestMethod.POST)
	public void login(HttpServletRequest request,HttpServletResponse response) {
		InputStream is = null;
		OutputStream os = null;
		try {
			is = request.getInputStream();
			
			String req = HttpUtil.getInstance().readStreamToString(is);
			RequestLogin reqObj = GsonUtil.getInstance().fromJson(req, RequestLogin.class);
			Manager manager = managerDao.searchManagerByNameAndPwd(reqObj.getName(), reqObj.getPassword());
			
			ResponseLogin respObj = new ResponseLogin();
			os = response.getOutputStream();

			if ( manager == null ) {
				respObj.setResponseId(ResponseMessage.LOGIN_FAILED);
			} else {
				HttpSession session = request.getSession(true);
				respObj.setSessionId(session.getId());
				respObj.setResponseId(ResponseMessage.SUCCESS);
				respObj.setManager(manager);
			}
			String res = GsonUtil.getInstance().toJson(respObj);
			os.write(res.getBytes("utf-8"));
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeStream(is);
			closeStream(os);
		}
	}
	
	@RequestMapping(value="/checkLogin.do",method=RequestMethod.POST)
	public void checkLogin(HttpServletRequest request,HttpServletResponse response) {
		OutputStream os = null;
		
		try {
			os = response.getOutputStream();

			ResponseCheckLogin respObj = new ResponseCheckLogin();
			respObj.setLogin(true);
			String res = GsonUtil.getInstance().toJson(response);
			os.write(res.getBytes("utf-8"));
		} catch (IOException e) {
			e.printStackTrace();
			closeStream(os);
		}
	}
	
	@RequestMapping(value="/insertManager.do",method=RequestMethod.POST)
	public void insertManager(HttpServletRequest request,HttpServletResponse response) {
		InputStream is = null;
		OutputStream os = null;
		
		try {
			is = request.getInputStream();
			String req = HttpUtil.getInstance().readStreamToString(is);
			
			RequestInsertManager reqObj = GsonUtil.getInstance().fromJson(req, RequestInsertManager.class);
			Manager manager = managerDao.searchManagerByName(reqObj.getName());
			
			BaseResponse respObj = new BaseResponse();
			if ( manager != null ) {
				respObj.setResponseId(ResponseMessage.MANAGER_NAME_EXISTS);
			} else if ( CheckUtil.getInstance().isEmpty(reqObj.getName()) ) {
				respObj.setResponseId(ResponseMessage.ERROR_NAME_EMPTY);
			} else if ( CheckUtil.getInstance().isEmpty(reqObj.getPhone()) ) {
				respObj.setResponseId(ResponseMessage.ERROR_PHONE_EMPTY);
			} else if ( CheckUtil.getInstance().isEmpty(reqObj.getEmail()) ) {
				respObj.setResponseId(ResponseMessage.ERROR_EMAIL_EMPTY);
			} else if ( !CheckUtil.getInstance().isPassword(reqObj.getPwd()) ) {
				respObj.setResponseId(ResponseMessage.ERROR_PASSWORD_FORMAT);
			} else if ( !reqObj.getPwd().equals(reqObj.getConfirmPwd()) ) {
				respObj.setResponseId(ResponseMessage.ERROR_PASSWORD_INCONFORMITY);
			} else if ( managerDao.isRoleExists(reqObj.getRole()) == null ) {
				respObj.setResponseId(ResponseMessage.ERROR_ROLE_NOT_EXISTS);
			} else {
				Timestamp time = new Timestamp(System.currentTimeMillis());
				managerDao.insertManager(reqObj.getName(), reqObj.getPwd(), reqObj.getPhone(), reqObj.getEmail(), reqObj.getRole(), time, time);
			}
			
			os = response.getOutputStream();
			os.write(GsonUtil.getInstance().toJson(respObj).getBytes("utf-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/searchManagers.do",method=RequestMethod.POST)
	public void searchManagers(HttpServletRequest request,HttpServletResponse response) {
		InputStream is = null;
		OutputStream os = null;
		
		try {
			is = request.getInputStream();
			
			String req = HttpUtil.getInstance().readStreamToString(is);
			RequestSearchManagers reqObj = GsonUtil.getInstance().fromJson(req, RequestSearchManagers.class);
			
			List<Manager> datas = managerDao.searchManagerList(reqObj.getName(), reqObj.getRole(), reqObj.getStartTime(), reqObj.getEndTime());
			
			ResponseSearchManagers respObj = new ResponseSearchManagers();
			List<ResponseSearchManagers.Manager> managers = new ArrayList<>();
			respObj.setManagers(managers);
			if ( datas != null && datas.size() > 0 ) {
				for(int i=0;i<datas.size();i++) {
					Manager item = datas.get(i);
					ResponseSearchManagers.Manager manager = new ResponseSearchManagers.Manager();
					manager.setName(item.getName());
					manager.setPhone(item.getPhone());
					manager.setEmail(item.getEmail());
					manager.setRole(item.getRole());
					manager.setCreateTime(FormatUtil.getInstance().timeToString(item.getCreateTime()));
					manager.setUpdateTime(FormatUtil.getInstance().timeToString(item.getUpdateTime()));
					managers.add(manager);
				}
			}
			
			os = response.getOutputStream();
			os.write(GsonUtil.getInstance().toJson(respObj).getBytes("utf-8"));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeStream(is);
			closeStream(os);
		}
	}
	
	@RequestMapping(value="/searchRoles.do",method=RequestMethod.POST)
	public void searchRoles(HttpServletRequest request,HttpServletResponse response) {
		OutputStream os = null;
		try {
			List<String> roles = managerDao.searchRoleList();
			
			ResponseSearchRoles respObj = new ResponseSearchRoles();
			respObj.setRoles(roles);
			os = response.getOutputStream();
			os.write(GsonUtil.getInstance().toJson(respObj).getBytes("utf-8"));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeStream(os);
		}
	}

	public final static String BATCH_INSERT_MANAGER_FILE_NAME = "file";
	@RequestMapping(value="/batchInsertManager.do",method=RequestMethod.POST)
	public void batchInsertManager(HttpServletRequest request,HttpServletResponse response) {
		MultipartHttpServletRequest multRequest = (MultipartHttpServletRequest)request;
		
		List<String> msgs = new ArrayList<>();
		List<MultipartFile> files = multRequest.getFiles(BATCH_INSERT_MANAGER_FILE_NAME);
		for(MultipartFile file:files) {
			if ( !file.isEmpty() ) {
				InputStream is = null;
				try {
					is = file.getInputStream();
					List<Map<String, String>> datas = FileResovler.getInstance().resovleCsvFile(is);
					for(int i=0;i<datas.size();i++) {
						Map<String, String> data = datas.get(i);
						String msg = "第"+(i+1)+"行:";
						if ( CheckUtil.getInstance().isEmpty(data.get("name")) ) {
							msg+="name不能为空";
						} else if ( CheckUtil.getInstance().isEmpty(data.get("phone")) ) {
							msg+="phone不能为空";
						} else if ( CheckUtil.getInstance().isEmpty(data.get("email")) ) {
							msg+="email不能为空";
						} else if ( CheckUtil.getInstance().isEmpty(data.get("password")) ) {
							msg+="password不能为空";
						} else if ( CheckUtil.getInstance().isEmpty(data.get("role")) ) {
							msg+="role不能为空";
						} else if ( managerDao.isRoleExists(data.get("role")) == null ) {
							msg+="role不存在";
						}
						if ( !msg.equals("第"+(i+1)+"行:") ) {
							msgs.add(msg);
						} else {
							Timestamp time = new Timestamp(System.currentTimeMillis());
							managerDao.insertManager(
									data.get("name"), 
									data.get("password"), 
									data.get("phone"), 
									data.get("email"), 
									data.get("role"), 
									time, 
									time);
						}
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					closeStream(is);
				}
			}
		}
		
		ResponseBatchInsertManager respObj = new ResponseBatchInsertManager();
		if ( msgs.size() > 0 ) {
			respObj.setErrorMsgs(msgs);
		}
		
		OutputStream os = null;
		try {
			os = response.getOutputStream();
			os.write(GsonUtil.getInstance().toJson(respObj).getBytes("utf-8"));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeStream(os);
		}
	}

	@RequestMapping(value="deleteManager.do",method=RequestMethod.POST)
	public void deleteManager(HttpServletRequest request,HttpServletResponse response) {
		InputStream is = null;
		OutputStream os = null;
		try {
			is = request.getInputStream();
			String req = HttpUtil.getInstance().readStreamToString(is);
			RequestDeleteManager reqObj = GsonUtil.getInstance().fromJson(req, RequestDeleteManager.class);
			
			Manager manager = managerDao.searchManagerByName(reqObj.getName());
			
			BaseResponse respObj = new BaseResponse();
			if ( manager == null ) {
				respObj.setResponseId(ResponseMessage.ERROR_MANAGER_NOT_EXISTS);
			} else {
				managerDao.deleteManager(reqObj.getName());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeStream(is);
			closeStream(os);
		}
	}
}
