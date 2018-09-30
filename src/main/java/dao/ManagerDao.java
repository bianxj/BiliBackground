package dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import entity.result.ResultDir;
import entity.result.ResultManager;
import entity.result.ResultRole;
import entity.table.Manager;
import entity.table.Role;

public interface ManagerDao {

	//------------------------------Manager Start------------------------------------------------
	public ResultManager searchManagerById(@Param("id")int id);
	public ResultManager searchManagerByName(@Param("name")String name);
	public List<ResultManager> searchManagerList(@Param("name")String name,@Param("role")String role,@Param("startTime")String startTime,@Param("endTime")String endTime);
	public void insertManager(Manager manager);
	public void batchInsertManager(@Param("managers")List<Manager> managers);
	public void batchDeleteManager(@Param("ids")List<Integer> ids);
	public void updateManager(Manager manager);
	public void deleteManager(@Param("id")int id);
	public List<ResultManager> searchAllManagers();
	//------------------------------Manager End--------------------------------------------------
	
	//------------------------------Role Start------------------------------------------------
	public List<Role> isRoleExists(@Param("id")int id);
	public List<ResultRole> searchRoleList();
	public int insertRole(Role role);
	public void updateRole(Role role);
	public void deleteRole(@Param("roleId")int roleId);
	public ResultRole searchRoleByName(@Param("role")String role);
	public ResultRole searchRoleById(@Param("roleId")int roleId);
	public int canRemoveRole(@Param("roleId")int roleId);
	public void batchDeleteRole(@Param("ids")List<Integer> ids);
	//------------------------------Role End--------------------------------------------------
	
	//------------------------------Dir Start------------------------------------------------
	public void insertRoleDir(@Param("roleId")int roleId,@Param("dirs")List<Integer> dirs);
	public List<ResultDir> searchDirByManagerId(@Param("managerId")int managerId);
	public List<ResultDir> searchDirByRoleId(@Param("roleId")int roleId);
	public List<ResultDir> searchAllDir();
	//------------------------------Dir End--------------------------------------------------
	
//	public Manager searchManagerByNameAndPwd(@Param("name") String name,@Param("password") String password);
//	public void batchInsertManager(List<Manager> managers);
	
	public void deleteRelevanceRole(@Param("roleId")int roleId);
	
	
	
	
	
	
}
