package github.javaguide.springsecurityjwtguide.common.setting;


import github.javaguide.springsecurityjwtguide.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @date 2020年1月17日
 * @pc ThinkPad
 * @desc
 */
@Component
public class CustomSetting {
	
	public static String baseUrl = "http://192.168.12.192:8083";
	
	public static String interfaceBaseUrl = "http://192.168.12.192:8083";
	
    @Autowired
    private Environment env;
    
    @PostConstruct
    public void init() {
    	String baseUrl = env.getProperty("forward.deepeye.baseUrl");
    	if(StringUtil.isNotEmpty(baseUrl)) {
    		CustomSetting.setBaseUrl(baseUrl);
    	}
    	String interfaceBaseUrl = env.getProperty("forward.interface.baseUrl");
    	if(StringUtil.isNotEmpty(interfaceBaseUrl)) {
    		CustomSetting.setInterfaceBaseUrl(interfaceBaseUrl);
    	}
    }

	public static String getBaseUrl() {
		return baseUrl;
	}

	public static void setBaseUrl(String baseUrl) {
		CustomSetting.baseUrl = baseUrl;
	}

	public static String getInterfaceBaseUrl() {
		return interfaceBaseUrl;
	}

	public static void setInterfaceBaseUrl(String interfaceBaseUrl) {
		CustomSetting.interfaceBaseUrl = interfaceBaseUrl;
	}
	
}
