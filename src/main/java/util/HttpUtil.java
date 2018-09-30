package util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpRequest;

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
	
	public <T> T readRequestToObject(HttpServletRequest request,Class<T> classOfT) throws IOException{
		String str = readStreamToString(request.getInputStream());
		return GsonUtil.getInstance().fromJson(str, classOfT);
	}
	
	public void writeResponseFromObject(HttpServletResponse response,Object obj) throws IOException {
		OutputStream os = response.getOutputStream();
		os.write(GsonUtil.getInstance().toJson(obj).getBytes("utf-8"));
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
