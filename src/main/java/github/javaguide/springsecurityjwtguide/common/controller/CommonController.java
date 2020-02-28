package github.javaguide.springsecurityjwtguide.common.controller;


import com.alibaba.fastjson.JSONObject;
import github.javaguide.springsecurityjwtguide.assist.RequestAdapter;
import github.javaguide.springsecurityjwtguide.common.bean.CustomRequestBody;
import github.javaguide.springsecurityjwtguide.common.bean.CustomResult;
import github.javaguide.springsecurityjwtguide.common.setting.CustomSetting;
import github.javaguide.springsecurityjwtguide.util.ForwardUtil;
import github.javaguide.springsecurityjwtguide.util.JsonUtil;
import github.javaguide.springsecurityjwtguide.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @date 2020年2月25日
 * @addr ThinkPad   通用/api接口匹配转发
 * @desc
 */
@RestController
public class CommonController {
	
	private static final Logger LOG = LoggerFactory.getLogger(CommonController.class);
	
	@PostMapping("/api/**")
	public CustomResult commonRequest(HttpServletRequest request, HttpServletResponse response) {
		CustomResult res = new CustomResult();
		
		String result = ForwardUtil.forward(new RequestAdapter(CustomSetting.getBaseUrl(), request) {
			
			@Override
			public void initHeader(HttpServletRequest request) {
				super.initHeader(request);
				Map<String, String> headerMap = this.getHeaderMap();
				if(StringUtil.isNotEmpty(headerMap.get("original-request-method"))) {
					this.setMethod(headerMap.get("original-request-method").toUpperCase());
				}
			}
			
			@Override
			public void initParam(HttpServletRequest request) {
				super.initParam(request);
			}
			
			@SuppressWarnings("unchecked")
			@Override
			public void initBody(HttpServletRequest request) {
				super.initBody(request);
				String body = super.getBody();
				if(StringUtil.isEmpty(body)) {
					return;
				}
				try {
					CustomRequestBody requestbodyObj = JsonUtil.jsonToObject(body, CustomRequestBody.class);
					res.setId(requestbodyObj.getId());
					res.setJsonrpc(requestbodyObj.getJsonrpc());
					if(this.getMethod().equalsIgnoreCase("GET")) {
						this.getHeaderMap().remove("content-length");
						Object data = requestbodyObj.getParams().getData();
						LOG.info("requestbody.params.data: {}", data);
						if(null!=data) {
							if(data instanceof Map) {
								this.setParamMap((Map<String, String>) data);
							}
						} else {
							this.setBody(null);
						}
					} else {
						String reqBodyStr = JsonUtil.objectToJson(requestbodyObj.getParams().getData());
						LOG.info("request url: {}, body: {}", this.getRequestUrl(), reqBodyStr);
						this.setBody(reqBodyStr);
					}
				} catch (Exception e) {
					LOG.error("common deal request body error, message: {}, detail: ", e.getMessage(), e);
				}
			}
			
			@Override
			public void finish() {
				LOG.debug("url: {}, method: {}, header: {}, param: {}, body: {}, file: {}", JsonUtil.objectToJson(this.getRequestUrl()), JsonUtil.objectToJson(this.getMethod()), JsonUtil.objectToJson(this.getHeaderMap()), JsonUtil.objectToJson(this.getParamMap()), JsonUtil.objectToJson(this.getBody()), JsonUtil.objectToJson(this.getFileMap()));
			}
			
		});
		JSONObject jsonObject = null;
		try {
			jsonObject = JSONObject.parseObject(result);
			if(null!=jsonObject.get("error") && jsonObject.getString("error").equals("invalid_token")) {
				response.setStatus(401);
				res.getResult().setCode(401);
			}
		} catch (Exception e) {
			LOG.error("common exception catch, message: {}, detail: ", e.getMessage(), e);
		}
		res.getResult().setData(jsonObject);
		return res;
	}

}
