package github.javaguide.springsecurityjwtguide.util;


import github.javaguide.springsecurityjwtguide.assist.RequestAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @date 2020年2月25日
 * @addr ThinkPad
 * @desc
 */
public class ForwardUtil {
	
	private static final Logger LOG = LoggerFactory.getLogger(ForwardUtil.class);
	
	private ForwardUtil() {
	}
	
	public static String forward(String baseUrl, HttpServletRequest request) {
		String result = "";
		
		String uri = request.getRequestURI();
		String method = request.getMethod();
		LOG.debug("请求的uri： {}, method：{}", uri, method);
		
		String forwardUrl = baseUrl + uri;
		LOG.debug("转发的uri : {}", forwardUrl);
		
		Map<String, String> headerMap = new HashMap<String, String>();
	    Enumeration<String> headerNames = request.getHeaderNames();
	    LOG.debug("请求头：");
	    while(headerNames.hasMoreElements()) {
	        String key = headerNames.nextElement();
	        String value = request.getHeader(key);
	    	LOG.debug("{} : {}", key, value);
	        headerMap.put(key, value);
	    }
	    
	    Map<String, String> paramMap = new HashMap<String, String>();
	    Enumeration<String> paramNames=request.getParameterNames();
	    LOG.debug("请求的param参数");
	    while(paramNames.hasMoreElements()) {
	    	String key = paramNames.nextElement();
	    	String value = request.getParameter(key);
	    	LOG.debug("{} : {}", key, value);
	    	paramMap.put(key, value);
	    }

	    StringBuilder body = new StringBuilder();
	    InputStream inb = null;
	    
    	try {
			inb = request.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(inb));
			String str = "";
			while ((str = br.readLine()) != null) {
				body.append(str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(null!=inb) {
				try {
					inb.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	    LOG.debug("请求体body：{}", body.toString());
		    
	    MultiValueMap<String, MultipartFile> fileMap = null;
	    
	    if(null!=headerMap.get("content-type") && headerMap.get("content-type").contains("multipart/form-data")) {
		    MultipartRequest req = (MultipartRequest) request;
		    fileMap = req.getMultiFileMap();
		    LOG.debug("fileMap: {}", fileMap);
	    }
	    
	    LOG.debug("-------------------------  华丽的分割线  ---------------------------");
	    result = OkHttpUtil.request(forwardUrl, method, headerMap, paramMap, body.toString(), fileMap);
		LOG.debug(forwardUrl + " 返回的结果result：" + result);
	    
		return result;
	}
	
	public static String forward(RequestAdapter callBack) {
		String result = OkHttpUtil.request(callBack.getRequestUrl(), callBack.getMethod(), callBack.getHeaderMap(), callBack.getParamMap(), callBack.getBody(), callBack.getFileMap());
		LOG.debug(callBack.getMethod() + " " + callBack.getRequestUrl() + " 返回的结果 result：" + result);
		return result;
	}
	


}
