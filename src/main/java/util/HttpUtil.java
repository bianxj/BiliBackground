package util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class HttpUtil {

	private static HttpUtil util;
	
	public static HttpUtil getInstance() {
		if ( util == null ) {
			util = new HttpUtil();
		}
		return util;
	}

	private HttpUtil() {
		super();
	}
	
	public String readStreamToString(InputStream is) throws IOException {
        int len = 0;
        byte[] buf = new byte[40960];
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        while ( (len = is.read(buf)) > 0 ){
            os.write(buf,0,len);
        }
        String result = os.toString("utf-8");
        os.close();
        return result;
	}
	
}
