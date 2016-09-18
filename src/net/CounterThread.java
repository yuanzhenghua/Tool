package net;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import file.RequestLogData;
import file.TimeAxisData;
import file.TotalData;
import file.WriteFile;

public class CounterThread extends Thread {
	public List<RequestLogData> requestLogData;
	public List<TimeAxisData> timeAxisData;
	public TotalData totalData;
	public boolean isRun;
	private long currentTime;
	private String path;
	private WriteFile writeFile = new WriteFile();
	
	//初始化计数器
	public void start(){
		super.start();
		if (requestLogData == null) {
			requestLogData = new ArrayList<>();
		}
		if (timeAxisData == null) {
			timeAxisData = new ArrayList<>();
		}
		if (totalData == null) {
			totalData = new TotalData(0, 0, 0, 0, 99999999, 0);
		}
		this.currentTime = 0;
	}
	
	//每秒写1次文件
	@Override
	public void run() {
		super.run();
		while (this.isRun) {
			if (this.currentTime == 0) {
				this.currentTime = System.currentTimeMillis()/10000;
			}else if (System.currentTimeMillis()/10000-currentTime >= 1) {
				try {
					this.saveData();
				} catch (Exception e) {
					e.printStackTrace();
				}
				this.currentTime = System.currentTimeMillis()/10000;
			}				
		}
		try {
			this.saveData();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			this.writeFile.WriteTxt(path, totalData);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//每秒中保存1次文件
	private void saveData() throws Exception{
		List<RequestLogData> requestLogData1 = new ArrayList<>();
		requestLogData1.addAll(this.requestLogData);
		this.requestLogData.clear();
		List<TimeAxisData> timeAxisData1 = new ArrayList<>();
		timeAxisData1.addAll(this.timeAxisData);
		this.timeAxisData.clear();
		for (RequestLogData rld : requestLogData1) {
			System.out.println(rld.getId());
			this.writeFile.updataTotalData(totalData, rld);
			this.writeFile.WriteXls(this.path, rld);
		}
		for (TimeAxisData tad : timeAxisData1) {
			this.writeFile.WriteXls(this.path, tad);
		}	
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public void setRun(Boolean isRun){
		this.isRun = isRun;
	}
}
