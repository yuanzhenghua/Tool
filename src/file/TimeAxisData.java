package file;
import java.util.Date;

public class TimeAxisData {
	private Date time;
	private int requestCount;
	private int ResponseCount;
	private int successCount;
	private int failCount;
	
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public int getRequestCount() {
		return requestCount;
	}
	public void setRequestCount(int requestCount) {
		this.requestCount = requestCount;
	}
	public int getResponseCount() {
		return ResponseCount;
	}
	public void setResponseCount(int responseCount) {
		ResponseCount = responseCount;
	}
	public int getSuccessCount() {
		return successCount;
	}
	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}
	public int getFailCount() {
		return failCount;
	}
	public void setFail(int failCount) {
		this.failCount = failCount;
	}
	
}
