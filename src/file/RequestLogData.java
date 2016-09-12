package file;

public class RequestLogData {
	private int id;
	private String operation;
	private boolean result;
	private String startTime;
	private String endTime;
	private float executionTime;
	private String explain;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	public String getExplain() {
		return explain;
	}
	public void setExplain(String explain) {
		explain = explain;
	}
	
}
