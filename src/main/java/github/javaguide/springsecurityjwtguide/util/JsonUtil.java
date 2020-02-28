package github.javaguide.springsecurityjwtguide.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;

import java.io.IOException;
import java.util.List;

/**
 * @date 2020年1月19日
 * @pc ThinkPad
 * @desc
 */
public class JsonUtil {
	
	private static final ObjectMapperExt OBJECT_MAPPER = new ObjectMapperExt();

	/**
	 * 对象转换为json
	 * @param object
	 * @return
	 */
	public static String objectToJson(Object object) {
		if(null==object) {
			return null;
		}
		String json = null;
        try {
            json = OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
	}
	
	/**
	 * json转换为对象
	 * @param <T>
	 * @param json
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	public static <T> T jsonToObject(String json, Class<T> cla) throws JsonParseException, JsonMappingException, IOException {
        return OBJECT_MAPPER.readValue(json, cla);
	}
	
	/**
	 * json转List对象列表
	 * @param json
	 * @param clz
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> jsonToList(String json, Class<T> clz) throws Exception {
		JavaType type = OBJECT_MAPPER.getTypeFactory().constructParametricType(List.class, clz);
		List<T> t = OBJECT_MAPPER.readValue(json, type);
		return t;
	}

}
