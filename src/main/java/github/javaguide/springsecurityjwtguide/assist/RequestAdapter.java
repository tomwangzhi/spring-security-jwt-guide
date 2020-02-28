package github.javaguide.springsecurityjwtguide.assist;

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
 * @author lei.ys
 * @addr ThinkPad
 * @desc
 */
public class RequestAdapter {

	private String requestUrl;
	
	private String method;
	
	private Map<String, String> headerMap;
	
	private Map<String, String> paramMap;
	
	private String body;
	
	private MultiValueMap<String, MultipartFile> fileMap;
	
	public RequestAdapter() {
	}
	
	public RequestAdapter(String baseUrl, HttpServletRequest request) {
		this.initRequestUrl(baseUrl, request);
		this.initMethod(request);
		this.initHeader(request);
		this.initParam(request);
		this.initBody(request);
		this.initFileMap(request);
		this.finish();
	}
	
	/**
	 * 设置转发路径
	 * @param baseUrl
	 * @param request
	 */
	public void initRequestUrl(String baseUrl, HttpServletRequest request) {
		this.setRequestUrl(baseUrl + request.getRequestURI());
	}
	
	public void initMethod(HttpServletRequest request) {
		this.setMethod(request.getMethod());
	}
	
	public void initHeader(HttpServletRequest request) {
		Map<String, String> headerMap = new HashMap<String, String>();
	    Enumeration<String> headerNames = request.getHeaderNames();
	    while(headerNames.hasMoreElements()) {
	        String key = headerNames.nextElement();
	        String value = request.getHeader(key);
//	        System.out.println(key + " : " + value);
	        headerMap.put(key, value);
	    }
	    this.setHeaderMap(headerMap);
	}
	
	public void initParam(HttpServletRequest request) {
		Map<String, String> paramMap = new HashMap<String, String>();
	    Enumeration<String> paramNames=request.getParameterNames();
//	    System.out.println("请求的param参数");
	    while(paramNames.hasMoreElements()) {
	    	String key = paramNames.nextElement();
	    	String value = request.getParameter(key);
//	    	System.out.println(key + " : " + value);
	    	paramMap.put(key, value);
	    }
	    this.setParamMap(paramMap);
	}
	
	public void initBody(HttpServletRequest request) {
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
//	    System.out.println("请求体body：");
//	    System.out.println(body.toString());
	    this.setBody(body.toString());
	}
	
	public void initFileMap(HttpServletRequest request) {
		MultiValueMap<String, MultipartFile> fileMap = null;
	    
	    if(null!=headerMap.get("content-type") && headerMap.get("content-type").contains("multipart/form-data")) {
		    MultipartRequest req = (MultipartRequest) request;
		    fileMap = req.getMultiFileMap();
//		    System.out.println("fileMap: " + fileMap);
	    }
	    this.setFileMap(fileMap);
	}
	
	public void finish() {
		
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Map<String, String> getHeaderMap() {
		return headerMap;
	}

	public void setHeaderMap(Map<String, String> headerMap) {
		this.headerMap = headerMap;
	}

	public Map<String, String> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, String> paramMap) {
		this.paramMap = paramMap;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public MultiValueMap<String, MultipartFile> getFileMap() {
		return fileMap;
	}

	public void setFileMap(MultiValueMap<String, MultipartFile> fileMap) {
		this.fileMap = fileMap;
	}

}
