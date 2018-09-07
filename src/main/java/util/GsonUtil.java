package util;

import java.io.Reader;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class GsonUtil {

	private static GsonUtil util;

	public static GsonUtil getInstance() {
		if (util == null) {
			util = new GsonUtil();
		}
		return util;
	}

	private Gson gson;

	private GsonUtil() {
		super();
		gson = new Gson();
	}

	public <T> T fromJson(String json, Class<T> classOfT) throws JsonSyntaxException {
		return gson.fromJson(json, classOfT);
	}
	
	public <T> T fromJson(String json, Type typeOfT) throws JsonSyntaxException{
		return gson.fromJson(json, typeOfT);
	}
	
	public <T> T fromJson(Reader json, Class<T> classOfT) throws JsonSyntaxException, JsonIOException{
		return gson.fromJson(json, classOfT);
	}
	
	public <T> T fromJson(Reader json, Type typeOfT) throws JsonIOException, JsonSyntaxException{
		return gson.fromJson(json, typeOfT);
	}
	
	public <T> T fromJson(JsonReader reader, Type typeOfT) throws JsonIOException, JsonSyntaxException{
		return gson.fromJson(reader, typeOfT);
	}
	
	public <T> T fromJson(JsonElement json, Class<T> classOfT) throws JsonSyntaxException{
		return gson.fromJson(json, classOfT);
	}

	public <T> T fromJson(JsonElement json, Type typeOfT) throws JsonSyntaxException{
		return gson.fromJson(json, typeOfT);
	}
	
	
	public String toJson(Object src) {
		return gson.toJson(src);
	}
	
	public String toJson(Object src, Type typeOfSrc) {
		return gson.toJson(src, typeOfSrc);
	}
	
	public void toJson(Object src, Appendable writer) throws JsonIOException{
		gson.toJson(src, writer);
	}
	
	public void toJson(Object src, Type typeOfSrc, Appendable writer) throws JsonIOException{
		gson.toJson(src, typeOfSrc, writer);
	}
	
	public void toJson(Object src, Type typeOfSrc, JsonWriter writer) throws JsonIOException{
		gson.toJson(src, typeOfSrc, writer);
	}

	public String toJson(JsonElement jsonElement) {
		return gson.toJson(jsonElement);
	}
	
	public void toJson(JsonElement jsonElement, Appendable writer) throws JsonIOException{
		gson.toJson(jsonElement, writer);
	}
	
	public void toJson(JsonElement jsonElement, JsonWriter writer) throws JsonIOException{
		gson.toJson(jsonElement, writer);
	}
	
}
