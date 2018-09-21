package util;

public class ExcelUtil {

/*	private void createExcel() {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		HSSFRow row = sheet.createRow(0);
		HSSFCellStyle style = wb.createCellStyle();
		
		String[] titles = {"登录名","手机","邮箱","密码","角色"};
		
		for ( int i=0;i<titles.length;i++ ) {
			row.createCell(i).setCellValue(titles[i]);
		}
		
		DataValidationHelper helper = sheet.getDataValidationHelper();
		CellRangeAddressList address = new CellRangeAddressList(1, 100, 4, 4);
		String[] roles = {"超级管理员","测试"};
		DataValidationConstraint constraint = helper.createExplicitListConstraint(roles);
		DataValidation validation = helper.createValidation(constraint, address);
		//处理Excel兼容性问题  
        if(validation instanceof XSSFDataValidation) {  
        	validation.setSuppressDropDownArrow(true);  
        	validation.setShowErrorBox(true);  
        }else {  
        	validation.setSuppressDropDownArrow(false);  
        }  
          
        sheet.addValidationData(validation);
	}*/
	
}
