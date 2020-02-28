package github.javaguide.springsecurityjwtguide.util;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

/**
 * @date 2020年2月25日
 * @addr ThinkPad
 * @desc
 */
public class OkHttpUtil {
	
	private static final Logger LOG = LoggerFactory.getLogger(OkHttpUtil.class);
	
    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build();
    
    private OkHttpUtil() {
    }
	
    /**
     * get请求转发
     * @param url
     * @param headerMap
     * @param params
     * @return
     * @throws Exception
     */
    public static String get(String url, Map<String, String> headerMap, Map<String, String> params) {
    	Headers headers = Headers.of(headerMap);
    	StringBuffer sbUrl = new StringBuffer(url);
		if (params != null && params.keySet().size() > 0) {
            boolean firstFlag = true;
            Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<String, String> entry = (Entry<String, String>) iterator.next();
                if (firstFlag) {
                    sbUrl.append("?" + entry.getKey() + "=" + entry.getValue());
                    firstFlag = false;
                } else {
                    sbUrl.append("&" + entry.getKey() + "=" + entry.getValue());
                }
            }
        }
		url = sbUrl.toString();
		Request request = new Request.Builder()
                .url(url)
				.headers(headers)
                .build();
		Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            int status = response.code();
            LOG.info("response status -> " + status);
            return response.body().string();
        } catch (Exception e) {
            LOG.error("okhttp3 post error >> {}, {}", url, e);
        } finally {
            if (response != null) {
                response.close();
            }
        }
    	return null;
    }

    /**
     * 普通post、put、delete等请求方法转发
     * @param url
     * @param method
     * @param headerMap
     * @param params
     * @param body
     * @return
     */
    public static String request(String url, String method, Map<String, String> headerMap, Map<String, String> params, String body) {
    	Headers headers = Headers.of(headerMap);
    	RequestBody requestBody = null;
		FormBody.Builder builder = new FormBody.Builder();
		if(!StringUtil.isEmpty(body)) {
			String contentType = headerMap.get("content-type");
			requestBody = FormBody.create(MediaType.parse(contentType), body);
		}

		Request request = null;
		if(null==requestBody) {
			if(null!=params && !params.isEmpty()) {
		        //添加参数
		        if (params != null && params.keySet().size() > 0) {
		            for (String key : params.keySet()) {
		                builder.add(key, params.get(key));
		            }
		        }
			}
			request = new Request.Builder()
					.url(url)
					.headers(headers)
					.method(method, builder.build())
					.build();
		} else {
			StringBuffer sb = new StringBuffer(url);
			if (params != null && params.keySet().size() > 0) {
	            boolean firstFlag = true;
	            Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
	            while (iterator.hasNext()) {
	                Entry<String, String> entry = (Entry<String, String>) iterator.next();
	                if (firstFlag) {
	                    sb.append("?" + entry.getKey() + "=" + entry.getValue());
	                    firstFlag = false;
	                } else {
	                    sb.append("&" + entry.getKey() + "=" + entry.getValue());
	                }
	            }
	        }
			url = sb.toString();
			request = new Request.Builder()
					.url(url)
					.headers(headers)
					.method(method, requestBody)
					.build();
		}

		Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            int status = response.code();
            LOG.info("response status -> " + status);
            return response.body().string();
        } catch (Exception e) {
            LOG.error("okhttp3 post error >> {}, {}, {}", method, url, e);
        } finally {
            if (response != null) {
                response.close();
            }
        }
    	return null;
    }

    /**
     * 普通post、put、delete等请求方法转发
     * @param url
     * @param method
     * @param headerMap
     * @param params
     * @param body
     * @return
     */
    public static Response returnReponse(String url, String method, Map<String, String> headerMap, Map<String, String> params, String body) {
    	Headers headers = Headers.of(headerMap);
    	RequestBody requestBody = null;
		FormBody.Builder builder = new FormBody.Builder();
		if(!StringUtil.isEmpty(body)) {
			String contentType = headerMap.get("content-type");
			requestBody = FormBody.create(MediaType.parse(contentType), body);
		}

		Request request = null;
		if(null==requestBody) {
			if(null!=params && !params.isEmpty()) {
		        //添加参数
		        if (params != null && params.keySet().size() > 0) {
		            for (String key : params.keySet()) {
		                builder.add(key, params.get(key));
		            }
		        }
			}
			request = new Request.Builder()
					.url(url)
					.headers(headers)
					.method(method, builder.build())
					.build();
		} else {
			StringBuffer sb = new StringBuffer(url);
			if (params != null && params.keySet().size() > 0) {
	            boolean firstFlag = true;
	            Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
	            while (iterator.hasNext()) {
	                Entry<String, String> entry = (Entry<String, String>) iterator.next();
	                if (firstFlag) {
	                    sb.append("?" + entry.getKey() + "=" + entry.getValue());
	                    firstFlag = false;
	                } else {
	                    sb.append("&" + entry.getKey() + "=" + entry.getValue());
	                }
	            }
	        }
			url = sb.toString();
			request = new Request.Builder()
					.url(url)
					.headers(headers)
					.method(method, requestBody)
					.build();
		}

		Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            int status = response.code();
            LOG.info("response status -> " + status);
            return response;
        } catch (Exception e) {
            LOG.error("okhttp3 post error >> {}, {}, {}", method, url, e);
        }
    	return null;
    }

    /**
     * 转发请求
     * 分三种情况
     * GET请求时，设置 路径url、请求方法method、请求头headerMap、请求参数paramMap
     * 文件上传请求时，设置路径url、请求方法method、请求头headerMap、请求参数paramMap、文件对象fileMap
     * 其它请求时，设置路径url、请求方法method、请求头headerMap、请求参数paramMap、请求体body
     * @param url    请求路径
     * @param method    请求方法
     * @param headerMap    请求头
     * @param paramMap    请求参数
     * @param body    请求体
     * @param fileMap    文件
     * @return
     * @throws Exception
     */
    public static String request(String url, String method, Map<String, String> headerMap, Map<String, String> paramMap, String body, MultiValueMap<String, MultipartFile> fileMap) {
		Headers headers = Headers.of(headerMap);
		Request request = null;
		if(null!=fileMap) {
			StringBuffer sb = new StringBuffer(url);
			if (paramMap != null && paramMap.keySet().size() > 0) {
	            boolean firstFlag = true;
	            Iterator<Entry<String, String>> iterator = paramMap.entrySet().iterator();
	            while (iterator.hasNext()) {
	                Entry<String, String> entry = (Entry<String, String>) iterator.next();
	                if (firstFlag) {
	                    sb.append("?" + entry.getKey() + "=" + entry.getValue());
	                    firstFlag = false;
	                } else {
	                    sb.append("&" + entry.getKey() + "=" + entry.getValue());
	                }
	            }
	        }
			url = sb.toString();

			MultipartBody.Builder builder = new MultipartBody.Builder();
			builder.setType(MultipartBody.FORM);

			try {
				for(String entry : fileMap.keySet()) {
					List<MultipartFile> imageDatas = fileMap.get(entry);
					for(MultipartFile imageData : imageDatas) {
						InputStream fin = imageData.getInputStream();
						byte[] fileByte = new byte[fin.available()];
						fin.read(fileByte);
						fin.close();
						RequestBody fileBody = RequestBody.create(MediaType.parse(imageData.getContentType()), fileByte);
						builder.addFormDataPart(entry, imageData.getOriginalFilename(), fileBody);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
				LOG.error("设置上传文件出错，", e);
			}
			RequestBody requestBody = builder.build();
			request = new Request.Builder()
					.url(url)
					.headers(headers)
					.method(method, requestBody)
					.build();
		} else if(!StringUtil.isEmpty(method) && method.toUpperCase().equals("GET")) {
			StringBuffer sb = new StringBuffer(url);
			if (paramMap != null && paramMap.keySet().size() > 0) {
	            boolean firstFlag = true;
	            Iterator<Entry<String, String>> iterator = paramMap.entrySet().iterator();
	            while (iterator.hasNext()) {
	                Entry<String, String> entry = (Entry<String, String>) iterator.next();
	                if (firstFlag) {
	                    sb.append("?" + entry.getKey() + "=" + entry.getValue());
	                    firstFlag = false;
	                } else {
	                    sb.append("&" + entry.getKey() + "=" + entry.getValue());
	                }
	            }
	        }
	        request = new Request.Builder()
	                .url(sb.toString())
					.headers(headers)
	                .build();
		} else {
			RequestBody requestBody = null;
			FormBody.Builder builder = new FormBody.Builder();
			if(!StringUtil.isEmpty(body)) {
				String contentType = headerMap.get("content-type");
				requestBody = FormBody.create(MediaType.parse(contentType), body);
			}

			if(null==requestBody) {
				if(null!=paramMap && !paramMap.isEmpty()) {
			        //添加参数
			        if (paramMap != null && paramMap.keySet().size() > 0) {
			            for (String key : paramMap.keySet()) {
			                builder.add(key, paramMap.get(key));
			            }
			        }
				}
				request = new Request.Builder()
						.url(url)
						.headers(headers)
						.method(method, builder.build())
						.build();
			} else {
				StringBuffer sb = new StringBuffer(url);
				if (paramMap != null && paramMap.keySet().size() > 0) {
		            boolean firstFlag = true;
		            Iterator<Entry<String, String>> iterator = paramMap.entrySet().iterator();
		            while (iterator.hasNext()) {
		                Entry<String, String> entry = (Entry<String, String>) iterator.next();
		                if (firstFlag) {
		                    sb.append("?" + entry.getKey() + "=" + entry.getValue());
		                    firstFlag = false;
		                } else {
		                    sb.append("&" + entry.getKey() + "=" + entry.getValue());
		                }
		            }
		        }
				url = sb.toString();
				request = new Request.Builder()
						.url(url)
						.headers(headers)
						.method(method, requestBody)
						.build();
			}
		}
		Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            int status = response.code();
            LOG.debug("url: {}, response status -> {}", url, status);
            return response.body().string();
        } catch (Exception e) {
            LOG.error("okhttp3 post error >> {}, {}", method, url, e);
        } finally {
            if (response != null) {
                response.close();
            }
        }
		return null;
	}

}
