package github.javaguide.springsecurityjwtguide.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 * @date 2020年1月19日
 * @pc ThinkPad
 * @desc Jackson的ObjectMapper扩展
 */
public class ObjectMapperExt extends ObjectMapper {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7562217768053626858L;

	public ObjectMapperExt() {
		super();
		
		// json中可存在类中没有对应的字段
		configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		// long型数据序列化后加上双引号（前端js在大的long型数据中精度有失）
		SimpleModule module = new SimpleModule("LongModule");
		module.addSerializer(Long.class, ToStringSerializer.instance);
		module.addSerializer(Long.TYPE, ToStringSerializer.instance);
		
		registerModule(module);
	}

}
