package file;

public class RequestLogData {
	private long id;
	private String operation; //请求发送地址
	private String state;  //req:请求， resp:响应
	private boolean result; //结果
	private int resultCode; //响应结果code
	private String startTime; //请求发送时间
	private String endTime; //接受到响应时间
	private float executionTime; //消耗时间
	private String errorMessage; //错误消息
	private String reqData; //请求数据
	private String respData; //响应数据
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public float getExecutionTime() {
		return executionTime;
	}
	public void setExecutionTime(float executionTime) {
		this.executionTime = executionTime;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public int getResultCode() {
		return resultCode;
	}
	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getReqData() {
		return reqData;
	}
	public void setReqData(String reqData) {
		this.reqData = reqData;
	}
	public String getRespData() {
		return respData;
	}
	public void setRespData(String respData) {
		this.respData = respData;
	}	
}
