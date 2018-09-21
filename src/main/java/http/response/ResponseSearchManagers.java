package http.response;

import java.sql.Timestamp;
import java.util.List;

import entity.result.ResultManager;

public class ResponseSearchManagers extends BaseResponse {

	private List<ResultManager> managers;
	
	public List<ResultManager> getManagers() {
		return managers;
	}

	public void setManagers(List<ResultManager> managers) {
		this.managers = managers;
	}

//	public static class Manager{
//		private String name;
//		private String phone;
//		private String email;
//		private String role;
//		
//		private String createTime;
//		private String updateTime;
//		public String getName() {
//			return name;
//		}
//		public void setName(String name) {
//			this.name = name;
//		}
//		public String getPhone() {
//			return phone;
//		}
//		public void setPhone(String phone) {
//			this.phone = phone;
//		}
//		public String getEmail() {
//			return email;
//		}
//		public void setEmail(String email) {
//			this.email = email;
//		}
//		public String getRole() {
//			return role;
//		}
//		public void setRole(String role) {
//			this.role = role;
//		}
//		public String getCreateTime() {
//			return createTime;
//		}
//		public void setCreateTime(String createTime) {
//			this.createTime = createTime;
//		}
//		public String getUpdateTime() {
//			return updateTime;
//		}
//		public void setUpdateTime(String updateTime) {
//			this.updateTime = updateTime;
//		}
//		
//	}
	
}
