package github.javaguide.springsecurityjwtguide.common.bean;


/**
 * @date 2020年2月25日
 * @author lei.ys
 * @addr ThinkPad
 * @desc
 */
public class CustomResult {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5660677100593951389L;
	
	private String jsonrpc;
	private String id;
	private ResultPara result = new ResultPara();
	
	public CustomResult() {
	}

	public String getJsonrpc() {
		return jsonrpc;
	}

	public void setJsonrpc(String jsonrpc) {
		this.jsonrpc = jsonrpc;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ResultPara getResult() {
		return result;
	}

	public void setResult(ResultPara result) {
		this.result = result;
	}
	
	public class ResultPara  {

		/**
		 * 
		 */
		private static final long serialVersionUID = 3412800610338508180L;
		
		private Object data;
		private int code = 200;
		private String msg = "success";
		private String sign = "";
		
		public ResultPara() {
		}

		public Object getData() {
			return data;
		}

		public void setData(Object data) {
			this.data = data;
		}

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}

		public String getSign() {
			return sign;
		}

		public void setSign(String sign) {
			this.sign = sign;
		}
		
	}

}
