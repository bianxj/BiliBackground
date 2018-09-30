package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import dao.ManagerDao;
import entity.result.ResultDir;
import entity.result.ResultManager;
import entity.result.ResultRole;
import entity.table.Manager;
import entity.table.Role;
import http.ResponseMessage;
import http.request.RequestAddRole;
import http.request.RequestBatchDeleteManager;
import http.request.RequestBatchDeleteRole;
import http.request.RequestDeleteManager;
import http.request.RequestInsertManager;
import http.request.RequestLogin;
import http.request.RequestRemoveRole;
import http.request.RequestSearchDir;
import http.request.RequestSearchDirByRoleId;
import http.request.RequestSearchManagers;
import http.request.RequestUpdateManager;
import http.request.RequestUpdateRole;
import http.response.BaseResponse;
import http.response.ResponseCheckLogin;
import http.response.ResponseLogin;
import http.response.ResponseSearchAllDir;
import http.response.ResponseSearchDir;
import http.response.ResponseSearchDirByRoleId;
import http.response.ResponseSearchManagers;
import http.response.ResponseSearchRoles;
import util.CheckUtil;
import util.GsonUtil;
import util.HttpUtil;

@Controller
@RequestMapping("/Manager")
public class ManagerController extends BaseController {

	@Autowired
	private ManagerDao managerDao;
	
	//--------------------------------------------------Login Start-----------------------------------------------------
	@RequestMapping(value="/login.do",method=RequestMethod.POST)
	public void login(HttpServletRequest request,HttpServletResponse response) {
		InputStream is = null;
		OutputStream os = null;
		try {
			is = request.getInputStream();
			
			String req = HttpUtil.getInstance().readStreamToString(is);
			RequestLogin reqObj = GsonUtil.getInstance().fromJson(req, RequestLogin.class);
			ResultManager manager = managerDao.searchManagerByName(reqObj.getName());
			
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
	//--------------------------------------------------Login End-------------------------------------------------------
	
	//--------------------------------------------------Manager Start---------------------------------------------------
	@RequestMapping(value="/insertManager.do",method=RequestMethod.POST)
	public void insertManager(HttpServletRequest request,HttpServletResponse response) {
		InputStream is = null;
		OutputStream os = null;
		
		try {
			is = request.getInputStream();
			String req = HttpUtil.getInstance().readStreamToString(is);
			
			RequestInsertManager reqObj = GsonUtil.getInstance().fromJson(req, RequestInsertManager.class);
			ResultManager manager = managerDao.searchManagerByName(reqObj.getName());
			
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
			} else if ( managerDao.isRoleExists(reqObj.getRoleId()) == null ) {
				respObj.setResponseId(ResponseMessage.ERROR_ROLE_NOT_EXISTS);
			} else {
				Timestamp time = new Timestamp(System.currentTimeMillis());
				Manager insert = new Manager();
				insert.setName(reqObj.getName());
				insert.setPassword(reqObj.getPwd());
				insert.setPhone(reqObj.getPhone());
				insert.setEmail(reqObj.getEmail());
				insert.setRoleId(reqObj.getRoleId());
				insert.setCreateTime(time);
				insert.setUpdateTime(time);
				managerDao.insertManager(insert);
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
			
			ResponseSearchManagers respObj = new ResponseSearchManagers();
			List<ResultManager> managers = managerDao.searchManagerList(reqObj.getName(), reqObj.getRole(), reqObj.getStartTime(), reqObj.getEndTime());
			respObj.setManagers(managers);
			
			os = response.getOutputStream();
			os.write(GsonUtil.getInstance().toJson(respObj).getBytes("utf-8"));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeStream(is);
			closeStream(os);
		}
	}
	
	public final static String BATCH_INSERT_MANAGER_FILE_NAME = "file";
	public final static String CACHE_UPLOAD_DIR = "cache";
	@RequestMapping(value="/batchInsertManager.do",method=RequestMethod.POST)
	public void batchInsertManager(HttpServletRequest request, HttpServletResponse response) {

		MultipartHttpServletRequest multRequest = (MultipartHttpServletRequest) request;

		// List<String> msgs = new ArrayList<>();
		BaseResponse respObj = new BaseResponse();
		List<MultipartFile> files = multRequest.getFiles(BATCH_INSERT_MANAGER_FILE_NAME);
		//Managers
		List<ResultManager> managerList = managerDao.searchAllManagers();
		List<String> names = new ArrayList<>();
		for (ResultManager manager : managerList) {
			names.add(manager.getName());
		}
		//Roles
		List<ResultRole> roleList = managerDao.searchRoleList();
		Map<String, Integer> roles = new HashMap<>();
		for(ResultRole role:roleList) {
			roles.put(role.getRole(), role.getId());
		}

		Timestamp time = new Timestamp(System.currentTimeMillis());
		for (MultipartFile file : files) {
			String fileName = request.getServletContext().getRealPath("/") + File.separator + CACHE_UPLOAD_DIR + File.separator + "Manager_" + System.currentTimeMillis() + ".xls";
			File cacheFile = saveUploadFile(file, fileName);
			if (cacheFile != null) {
				InputStream is = null;
				try {
					is = new FileInputStream(cacheFile);
					Workbook wb = getWorkbook(is, cacheFile.getName());
					Sheet sheet = wb.getSheetAt(0);

					List<Manager> managers = new ArrayList<>();
					int count = 0;
					for (Row row : sheet) {
						if (count == 0) {
							for (int i = 0; i < TEMPLATE_MANAGER_TITLES.length; i++) {
								Cell cell = row.getCell(i);
								if (cell == null || !TEMPLATE_MANAGER_TITLES[i].equals(cell.toString().trim())) {
									respObj.setResponseId(ResponseMessage.ERROR_TEMPLATE_FORMAT);
									break;
								}
							}
						} else {
							Manager manager = new Manager();
							String name = row.getCell(0).toString();
							if (CheckUtil.getInstance().isEmpty(name)) {
								respObj.setResponseId(ResponseMessage.ERROR_NAME_EMPTY);
								break;
							}
							if (names.contains(name)) {
								respObj.setResponseId(ResponseMessage.MANAGER_NAME_EXISTS);
								break;
							}
							Cell phoneCell = row.getCell(1);
							phoneCell.setCellType(CellType.STRING);
							String phone = phoneCell.toString();
							if (CheckUtil.getInstance().isEmpty(phone)) {
								respObj.setResponseId(ResponseMessage.ERROR_PHONE_EMPTY);
								break;}
							if (!CheckUtil.getInstance().isPhone(phone)) {
								respObj.setResponseId(ResponseMessage.ERROR_PHONE_FORMAT);
								break;
							}
							String email = row.getCell(2).toString();
							if (CheckUtil.getInstance().isEmpty(email)) {
								respObj.setResponseId(ResponseMessage.ERROR_EMAIL_EMPTY);
								break;
							}
							if (!CheckUtil.getInstance().isEmail(email)) {
								respObj.setResponseId(ResponseMessage.ERROR_EMAIL_FORMAT);
								break;
							}
							Cell passwordCell = row.getCell(3);
							passwordCell.setCellType(CellType.STRING);
							String password = passwordCell.toString();
							if ( CheckUtil.getInstance().isEmpty(password) ) {
								respObj.setResponseId(ResponseMessage.ERROR_PASSWORD_EMPTY);
								break;
							}
							if (!CheckUtil.getInstance().isPassword(password)) {
								respObj.setResponseId(ResponseMessage.ERROR_PASSWORD_FORMAT);
								break;
							}

							String role = row.getCell(4).toString();
							if (CheckUtil.getInstance().isEmpty(role)) {
								respObj.setResponseId(ResponseMessage.ERROR_ROLE_EMPTY);
								break;
							}
							if (!roles.containsKey(role)) {
								respObj.setResponseId(ResponseMessage.ERROR_ROLE_NOT_EXISTS);
								break;
							}
							
							manager.setName(name);
							manager.setPassword(password);
							manager.setPhone(phone);
							manager.setEmail(email);
							manager.setRoleId(roles.get(role));
							manager.setCreateTime(time);
							manager.setUpdateTime(time);
							managers.add(manager);
						}
						count++;
					}
					if ( respObj.getResponseId() == 0 ) {
						managerDao.batchInsertManager(managers);
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					closeStream(is);
				}
			}
		}
	}
	
	@RequestMapping(value="/updateManager.do",method=RequestMethod.POST)
	public void updateManager(HttpServletRequest request,HttpServletResponse response) {
		InputStream is = null;
		OutputStream os = null;
		try {
			is = request.getInputStream();
			String req = HttpUtil.getInstance().readStreamToString(is);
			RequestUpdateManager reqObj = GsonUtil.getInstance().fromJson(req, RequestUpdateManager.class);
			
			BaseResponse respObj = new BaseResponse();
			if ( reqObj == null ) {
				respObj.setResponseId(ResponseMessage.ERROR_EMPTY_MESSAGE);
			} else if ( reqObj.getId() < 0 ) {
				respObj.setResponseId(ResponseMessage.ERROR_MANAGER_ID);
			} else if ( CheckUtil.getInstance().isEmpty(reqObj.getName()) ) {
				respObj.setResponseId(ResponseMessage.ERROR_NAME_EMPTY);
			} else if ( CheckUtil.getInstance().isEmpty(reqObj.getEmail()) ) {
				respObj.setResponseId(ResponseMessage.ERROR_EMAIL_EMPTY);
			} else if ( CheckUtil.getInstance().isEmpty(reqObj.getPhone()) ) {
				respObj.setResponseId(ResponseMessage.ERROR_PHONE_EMPTY);
			} else if ( managerDao.isRoleExists(reqObj.getRoleId()) == null ) {
				respObj.setResponseId(ResponseMessage.ERROR_ROLE_NOT_EXISTS);
			} else {
				Timestamp time = new Timestamp(System.currentTimeMillis());
				Manager manager = new Manager();
				manager.setId(reqObj.getId());
				manager.setName(reqObj.getName());
				manager.setPhone(reqObj.getPhone());
				manager.setEmail(reqObj.getEmail());
				manager.setRoleId(reqObj.getRoleId());
				manager.setUpdateTime(time);
				managerDao.updateManager(manager);
			}
			
			os = response.getOutputStream();
			os.write(GsonUtil.getInstance().toJson(respObj).getBytes("utf-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/deleteManager.do",method=RequestMethod.POST)
	public void deleteManager(HttpServletRequest request,HttpServletResponse response) {
		InputStream is = null;
		OutputStream os = null;
		try {
			is = request.getInputStream();
			String req = HttpUtil.getInstance().readStreamToString(is);
			RequestDeleteManager reqObj = GsonUtil.getInstance().fromJson(req, RequestDeleteManager.class);
			
			ResultManager manager = managerDao.searchManagerById(reqObj.getId());
			
			BaseResponse respObj = new BaseResponse();
			if ( manager == null ) {
				respObj.setResponseId(ResponseMessage.ERROR_MANAGER_NOT_EXISTS);
			} else {
				managerDao.deleteManager(reqObj.getId());
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

	@RequestMapping(value="/batchDeleteManager.do",method=RequestMethod.POST)
	public void batchDeleteManager(HttpServletRequest request,HttpServletResponse response) {
		InputStream is = null;
		OutputStream os = null;
		try {
			is = request.getInputStream();
			String req = HttpUtil.getInstance().readStreamToString(is);
			RequestBatchDeleteManager reqObj = GsonUtil.getInstance().fromJson(req, RequestBatchDeleteManager.class);
			managerDao.batchDeleteManager(reqObj.getIds());
			
			BaseResponse respObj = new BaseResponse();
			os = response.getOutputStream();
			os.write(GsonUtil.getInstance().toJson(respObj).getBytes("utf-8"));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeStream(is);
			closeStream(os);
		}
	}
		
	@RequestMapping(value="managerTemplate.file",method=RequestMethod.GET)
	public void generateManagerTemplate(HttpServletRequest request,HttpServletResponse response) {
		OutputStream os = null;
		try {
			HSSFWorkbook wb = null;
			wb = createManagerTemplate();
			response.setHeader("Content-disposition", "attachment;filename= manager.xls");  //客户端得到的文件名

			response.setContentType("application/x-download");//设置为下载application/x-download  	
			response.setContentType("text/html; charset=UTF-8");   
			response.setHeader("Cache-Control","no-cache");   
			response.setHeader("Cache-Control","no-store");   
			response.setDateHeader("Expires", 0);   
			response.setHeader("Pragma","no-cache");
			os = response.getOutputStream();
			wb.write(os);
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeStream(os);
		}
	}
	//--------------------------------------------------Manager End----------------------------------------------------
	
	//--------------------------------------------------Role Start-----------------------------------------------------
	@RequestMapping(value="/searchRoles.do",method=RequestMethod.POST)
	public void searchRoles(HttpServletRequest request,HttpServletResponse response) {
		OutputStream os = null;
		try {
			List<ResultRole> roles = managerDao.searchRoleList();
			
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
	
	@RequestMapping(value="addRole.do",method=RequestMethod.POST)
	public void addRole(HttpServletRequest request,HttpServletResponse response) {
		InputStream is = null;
		OutputStream os = null;
		try {
			is = request.getInputStream();
			String req = HttpUtil.getInstance().readStreamToString(is);
			RequestAddRole reqObj = GsonUtil.getInstance().fromJson(req, RequestAddRole.class);

			BaseResponse respObj = new BaseResponse();
			
			if ( managerDao.searchRoleByName(reqObj.getName()) == null ) {
				Role role = new Role();
				Timestamp time = new Timestamp(System.currentTimeMillis());
				role.setRole(reqObj.getName());
				role.setCreateTime(time);

				managerDao.insertRole(role);
				managerDao.insertRoleDir(role.getId(), reqObj.getDirIds());
			} else {
				respObj.setResponseId(ResponseMessage.ROLE_NAME_EXISTS);
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
	
	@RequestMapping(value="updateRole.do",method=RequestMethod.POST)
	public void updateRole(HttpServletRequest request,HttpServletResponse response) {
		InputStream is = null;
		OutputStream os = null;
		try {
			is = request.getInputStream();
			String req = HttpUtil.getInstance().readStreamToString(is);
			
			RequestUpdateRole reqObj = GsonUtil.getInstance().fromJson(req, RequestUpdateRole.class);
			Role role = new Role();
			role.setId(reqObj.getId());
			role.setRole(reqObj.getName());
			managerDao.updateRole(role);
			managerDao.deleteRelevanceRole(reqObj.getId());
			managerDao.insertRoleDir(reqObj.getId(), reqObj.getDirIds());
			
			BaseResponse respObj = new BaseResponse();
			
			os = response.getOutputStream();
			os.write(GsonUtil.getInstance().toJson(respObj).getBytes("utf-8"));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeStream(is);
			closeStream(os);
		}
	}
	
	@RequestMapping(value="removeRole.do",method=RequestMethod.POST)
	public void removeRole(HttpServletRequest request,HttpServletResponse response) {
		InputStream is = null;
		OutputStream os = null;
		try {
			is = request.getInputStream();
			String req = HttpUtil.getInstance().readStreamToString(is);
			RequestRemoveRole reqObj = GsonUtil.getInstance().fromJson(req, RequestRemoveRole.class);
			
			BaseResponse respObj = new BaseResponse();
			int count = managerDao.canRemoveRole(reqObj.getRoleId());
			if ( count == 0 ) {
				managerDao.deleteRole(reqObj.getRoleId());
				managerDao.deleteRelevanceRole(reqObj.getRoleId());
			} else {
				respObj.setResponseId(ResponseMessage.ROLE_IS_USED);
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
	
	@RequestMapping(value="/batchDeleteRole.do",method=RequestMethod.POST)
	public void batchDeleteRole(HttpServletRequest request,HttpServletResponse response) {
		try {
			RequestBatchDeleteRole reqObj = HttpUtil.getInstance().readRequestToObject(request, RequestBatchDeleteRole.class);
			
			BaseResponse respObj = new BaseResponse();
			List<Integer> ids = reqObj.getIds();
			boolean canDelete = true;
			for(Integer id:ids) {
				if ( managerDao.canRemoveRole(id) != 0 ) {
					canDelete = false;
					break;
				}
			}
			if (  canDelete ) {
				managerDao.batchDeleteRole(ids);
			} else {
				respObj.setResponseId(ResponseMessage.ROLE_IS_USED);
			}
			HttpUtil.getInstance().writeResponseFromObject(response, respObj);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//--------------------------------------------------Role End-------------------------------------------------------

	//--------------------------------------------------Dir Start------------------------------------------------------
	@RequestMapping(value="searchDir.do",method=RequestMethod.POST)
	public void searchDirById(HttpServletRequest request,HttpServletResponse response) {
		InputStream is = null;
		OutputStream os = null;
		try {
			is = request.getInputStream();
			String req = HttpUtil.getInstance().readStreamToString(is);
			RequestSearchDir reqObj = GsonUtil.getInstance().fromJson(req, RequestSearchDir.class);
			
			List<ResultDir> dirs = managerDao.searchDirByManagerId(reqObj.getManagerId());
			
			ResponseSearchDir respObj = new ResponseSearchDir();
			respObj.setDirs(dirs);
			os = response.getOutputStream();
			os.write(GsonUtil.getInstance().toJson(respObj).getBytes("utf-8"));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeStream(is);
			closeStream(os);
		}
	}
	
	@RequestMapping(value="searchAllDir.do",method=RequestMethod.POST)
	public void searchAllDir(HttpServletRequest request,HttpServletResponse response) {
		InputStream is = null;
		OutputStream os = null;
		
		try {
			is = request.getInputStream();
			String req = HttpUtil.getInstance().readStreamToString(is);
			List<ResultDir> dirs = managerDao.searchAllDir();
			
			ResponseSearchAllDir respObj = new ResponseSearchAllDir();
			respObj.setDirs(dirs);
			os = response.getOutputStream();
			os.write(GsonUtil.getInstance().toJson(respObj).getBytes("utf-8"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeStream(is);
			closeStream(os);
		}
	}
	
	@RequestMapping(value="searchDirByRoleId.do",method=RequestMethod.POST)
	public void searchDirByRoleId(HttpServletRequest request,HttpServletResponse response) {
		InputStream is = null;
		OutputStream os = null;
		
		try {
			is = request.getInputStream();
			String req = HttpUtil.getInstance().readStreamToString(is);
			RequestSearchDirByRoleId reqObj = GsonUtil.getInstance().fromJson(req, RequestSearchDirByRoleId.class);
			
			List<ResultDir> dirs = managerDao.searchDirByRoleId(reqObj.getRoleId());
			ResultRole role = managerDao.searchRoleById(reqObj.getRoleId());
			
			ResponseSearchDirByRoleId respObj = new ResponseSearchDirByRoleId();
			respObj.setDirs(dirs);
			respObj.setRole(role);
			os = response.getOutputStream();
			os.write(GsonUtil.getInstance().toJson(respObj).getBytes("utf-8"));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeStream(is);
			closeStream(os);
		}
	}
	//--------------------------------------------------Dir End------------------------------------------------------
	private Workbook getWorkbook(InputStream is,String fileName) throws IOException {
		Workbook wb = new HSSFWorkbook(is);
		return wb;
	}
	
	private File saveUploadFile(MultipartFile file,String fileName) {
		if ( file.isEmpty() ) {
			return null;
		}
		InputStream is = null;
		OutputStream os = null;
		File cacheFile = null;
		try {
			is = file.getInputStream();
			cacheFile = new File(fileName);
			cacheFile.createNewFile();
			os = new FileOutputStream(cacheFile);
			System.out.println(cacheFile.getAbsolutePath());
			int length = 0;
			byte[] buff = new byte[40960];
			while ( (length = is.read(buff)) > 0 ) {
				os.write(buff, 0, length);
			}
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeStream(os);
			closeStream(is);
		}
		return cacheFile;
	}
	
	
	private HSSFWorkbook createRoleTemplate() {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		HSSFRow row = sheet.createRow(0);
		
		String[] titles = {"角色"};
		
		for ( int i=0;i<titles.length;i++ ) {
			row.createCell(i).setCellValue(titles[i]);
		}
        return wb;
	}
	
	public final static String[] TEMPLATE_MANAGER_TITLES = {"登录名","手机","邮箱","密码","角色"};
	private HSSFWorkbook createManagerTemplate() {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		HSSFRow row = sheet.createRow(0);
		
		for ( int i=0;i<TEMPLATE_MANAGER_TITLES.length;i++ ) {
			row.createCell(i).setCellValue(TEMPLATE_MANAGER_TITLES[i]);
		}
		
		DataValidationHelper helper = sheet.getDataValidationHelper();
		CellRangeAddressList address = new CellRangeAddressList(1, 100, 4, 4);
		
		List<ResultRole> roleList = managerDao.searchRoleList();
		String[] roles = new String[roleList.size()];
		for(int i=0;i<roles.length;i++) {
			roles[i] = roleList.get(i).getRole();
		}
		DataValidationConstraint constraint = helper.createExplicitListConstraint(roles);
		DataValidation validation = helper.createValidation(constraint, address);
    	validation.setSuppressDropDownArrow(false);  
        sheet.addValidationData(validation);
        return wb;
	}
	
}
