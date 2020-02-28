package github.javaguide.springsecurityjwtguide.common.bean;


/**
 * @date 2020年2月25日
 * @author lei.ys
 * @addr ThinkPad
 * @desc
 */
public class CustomRequestBody {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1829457090238854410L;
	
	private String jsonrpc;
	private String method;
	private String id;
	private Param params;
	
	public String getJsonrpc() {
		return jsonrpc;
	}

	public void setJsonrpc(String jsonrpc) {
		this.jsonrpc = jsonrpc;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Param getParams() {
		return params;
	}

	public void setParams(Param params) {
		this.params = params;
	}

	public class Param {
		Object data;
		String sign;
		
		public Param() {
		}

		public Object getData() {
			return data;
		}

		public void setData(Object data) {
			this.data = data;
		}

		public String getSign() {
			return sign;
		}

		public void setSign(String sign) {
			this.sign = sign;
		}
		
	}

}
