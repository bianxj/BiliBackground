package util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class FormatUtil {

	private static FormatUtil util;
	
	public static FormatUtil getInstance() {
		if ( util == null ) {
			util = new FormatUtil();
		}
		return util;
	} 
	
	private DateFormat df;
	private FormatUtil(){
		df = new SimpleDateFormat("yyyy-MM-dd");
	}
	
	public String timeToString(Timestamp time) {
		return df.format(time);
	}
	
}
