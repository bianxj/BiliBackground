package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileResovler {

	private static FileResovler resovler;
	
	public static FileResovler getInstance() {
		if ( resovler == null ) {
			resovler = new FileResovler();
		}
		return resovler;
	}
	
	private FileResovler() {
		
	}
	
	public List<Map<String, String>> resovleCsvFile(InputStream is) throws IOException , Exception {
		List<Map<String, String>> result = new ArrayList<>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String title = reader.readLine();
		if ( title == null ) {
			return result;
		}
		String[] titles = title.split(",");
		String content;
		String[] contents;
		while ( (content = reader.readLine()) != null ) {
			contents = content.split(",");
			Map<String, String> map = new HashMap<>();
			int count = titles.length < contents.length ? titles.length:contents.length;
			for(int i=0;i<count;i++) {
				titles[i] = contents[i];
			}
			result.add(map);
		}
		return result;
	}
	
}
