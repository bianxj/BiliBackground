package dao;


import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import entity.table.Manager;

public interface ManagerDao {

	public String isRoleExists(@Param("role")String role);
	
	public Manager searchManagerByName(@Param("name")String name);
	
	public Manager searchManagerByNameAndPwd(@Param("name") String name,@Param("password") String password);
	
	public List<Manager> searchManagerList(@Param("name")String name,@Param("role")String role,@Param("startTime")String startTime,@Param("endTime")String endTime);
	
	public List<String> searchRoleList();
	
	public void insertManager(@Param("name")String name,@Param("password")String password,@Param("phone")String phone,@Param("email")String email,@Param("role")String role,@Param("create_time")Timestamp createTime,@Param("update_time")Timestamp updateTime);
	
	public void batchInsertManager(List<Manager> managers);
	
	public void deleteManager(@Param("name")String name);
}
