package dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import entity.result.ResultDir;
import entity.result.ResultManager;
import entity.result.ResultRole;
import entity.table.Manager;
import entity.table.Role;

public interface ManagerDao {

	public List<Role> isRoleExists(@Param("id")int id);
	
	public ResultManager searchManagerById(@Param("id")int id);
	
	public ResultManager searchManagerByName(@Param("name")String name);
	
//	public Manager searchManagerByNameAndPwd(@Param("name") String name,@Param("password") String password);
	
	public List<ResultManager> searchManagerList(@Param("name")String name,@Param("role")String role,@Param("startTime")String startTime,@Param("endTime")String endTime);
	
	public List<ResultRole> searchRoleList();
	
	public void insertManager(Manager manager);
	
	public void batchInsertManager(@Param("managers")List<Manager> managers);
	
//	public void batchInsertManager(List<Manager> managers);
	
	public void updateManager(Manager manager);
	
	public void deleteManager(@Param("id")int id);
	
	public int insertRole(Role role);
	
	public void updateRole(Role role);
	
	public void deleteRole(@Param("roleId")int roleId);
	
	public void deleteRelevanceRole(@Param("roleId")int roleId);
	
	public ResultRole searchRoleByName(@Param("role")String role);
	
	public ResultRole searchRoleById(@Param("roleId")int roleId);
	
	public void insertRoleDir(@Param("roleId")int roleId,@Param("dirs")List<Integer> dirs);
	
	public List<ResultDir> searchDirByManagerId(@Param("managerId")int managerId);
	
	public List<ResultDir> searchDirByRoleId(@Param("roleId")int roleId);
	
	public int canRemoveRole(@Param("roleId")int roleId);
	
	public List<ResultDir> searchAllDir();
	
	public List<ResultManager> searchAllManagers();
	
}
