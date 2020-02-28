package github.javaguide.springsecurityjwtguide.util;

/**
 * @date 2020年1月17日
 * @pc ThinkPad
 * @desc 字符串常用相关方法
 */
public class StringUtil {
	
	private StringUtil() {
	}

	/**
	 * 判断字符串是否为null或空
	 * null, "", " " 都将返回true
	 * @param string
	 * @return
	 */
	public static boolean isEmpty(String string) {
		if(null==string || "".equals(string.trim())) 
			return true;
		return false;
	}
	
	/**
	 * 判断字符串是否不为null或空（至少有一个非空字符）
	 * null, "", " " 都将返回false
	 * @param string
	 * @return
	 */
	public static boolean isNotEmpty(String string) {
		return !isEmpty(string);
	}
	
	/**
	 * 根据分隔符连接字符串
	 * @param separator  分隔符
	 * @param strs
	 * @return
	 */
	public static String concat(String separator, String... strs) {
		if(null==strs || strs.length==0) {
			return null;
		}
		StringBuilder builder = new StringBuilder();
		for(String str : strs) {
			builder.append(str+separator);
		}
		builder.delete(builder.length()-separator.length(), builder.length());
		return builder.toString();
	}

}
