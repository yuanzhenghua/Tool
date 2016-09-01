package file;

public class TextData {
	private int total;
	private int success;
	private int fail;
	private float totalExecutionTime;
	private float maxTime;
	private float minTime;
	
	public TextData(int total, int success, int fail, float totalExecutionTime, float maxTime, float minTime) {
		this.total = total;
		this.success = success;
		this.fail = fail;
		this.totalExecutionTime = totalExecutionTime;
		this.maxTime = maxTime;
		this.minTime = minTime;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getSuccess() {
		return success;
	}

	public void setSuccess(int success) {
		this.success = success;
	}

	public int getFail() {
		return fail;
	}

	public void setFail(int fail) {
		this.fail = fail;
	}

	public float getTotalExecutionTime() {
		return totalExecutionTime;
	}

	public void setTotalExecutionTime(float totalExecutionTime) {
		this.totalExecutionTime = totalExecutionTime;
	}

	public float getMaxTime() {
		return maxTime;
	}

	public void setMaxTime(float maxTime) {
		this.maxTime = maxTime;
	}

	public float getMinTime() {
		return minTime;
	}

	public void setMinTime(float minTime) {
		this.minTime = minTime;
	}
	
	
	
}
