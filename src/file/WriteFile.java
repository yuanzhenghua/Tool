package file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class WriteFile {
	/**
	 * 检测文件夹并创建文件夹
	 * @param file 文件夹所在路径
	 * @return
	 * @throws IOException
	 */
	public String CreateFolder(String file) throws IOException {
		String folderpath = file+"\\"+new SimpleDateFormat("yyyy-MM-dd HHmmss").format(new Date());
		//System.out.println(folderpath);
		if(!new File(folderpath).exists()){
			new File(folderpath).mkdirs();
		}
		return folderpath;
	}
	
	/**
	 * 数据总汇记录
	 * @param path txt文件需要保存的路径
	 * @param data 需要保存的数据
	 * @throws IOException
	 */
	public void WriteTxt(String path, TotalData data) throws IOException {
		String filepath = path+"\\InformationCenter.txt";
		if(!new File(filepath).exists()){
			new File(filepath).createNewFile();
		}
		//写文件
		BufferedWriter f = new BufferedWriter(new FileWriter(new File(filepath)));
		f.write("此次测试执行完成！！！\n");
	    f.write("总消耗时间："+data.getTotalExecutionTime()+"秒\n");
	    float avgTime = 0;
	    if(data.getTotal()>0){
	    	avgTime = data.getTotalExecutionTime()/data.getTotal();
	    }
	        
	    f.write("平均每条测试消耗时间："+avgTime+"秒\n");
	    f.write("总执行测试："+data.getTotal()+"条\n");
	    f.write("执行成功："+data.getSuccess()+"条\n");
	    f.write("执行失败："+data.getFail()+"条\n");
	    f.write("最大消耗时间："+data.getMaxTime()+"秒\n");
	    f.write("最小消耗时间："+data.getMinTime()+"秒\n");
	    f.close();
	}
	
	/**
	 * 根据请求返回结果记录数据，更新统计总汇数据记录
	 * @param totalData		统计总汇数据
	 * @param requestLogData	请求返回结果记录数据
	 * @return
	 */
	public TotalData updataTotalData(TotalData totalData, RequestLogData requestLogData){
		totalData.setTotal(totalData.getTotal()+1);
		if (requestLogData.isResult()) {
			totalData.setSuccess(totalData.getSuccess()+1);
		}else {
			totalData.setFail(totalData.getFail()+1);
		}
		if (requestLogData.getExecutionTime()>totalData.getMaxTime()) {
			totalData.setMaxTime(requestLogData.getExecutionTime());
		}else if (requestLogData.getExecutionTime()<totalData.getMinTime()) {
			totalData.setMinTime(requestLogData.getExecutionTime());
		}
		totalData.setTotalExecutionTime(totalData.getTotalExecutionTime()+requestLogData.getExecutionTime());
		return totalData;
	}
	
	/**
	 * 每次请求的明细记录保存
	 * @param path	文件保存路径
	 * @param data	需要保存的数据
	 * @throws IOException
	 */
	public void WriteXls(String path, RequestLogData data) throws IOException {
		String filepath = path+"\\RequestLogData.xlsx";
		Workbook wb = null;
		if(new File(filepath).exists()){
			FileInputStream in = new FileInputStream(filepath);
			wb = new XSSFWorkbook(in);
			in.close();
		}else {
			wb = new XSSFWorkbook();
		}	
		Sheet sheet = null;
		if(wb.getNumberOfSheets() == 0){
			sheet = wb.createSheet();
		}else{
			sheet = wb.getSheetAt(0);
		}
		if(sheet.getLastRowNum()==0){
			sheet.createRow(0);
			sheet.getRow(0).createCell(0).setCellValue("Id");
			sheet.getRow(0).createCell(1).setCellValue("operation");
			sheet.getRow(0).createCell(2).setCellValue("StartTime");
			sheet.getRow(0).createCell(3).setCellValue("EndTime");
			sheet.getRow(0).createCell(4).setCellValue("ExecutionTime");
			sheet.getRow(0).createCell(5).setCellValue("Result");
			sheet.getRow(0).createCell(6).setCellValue("Explain");
		}
		sheet.createRow(sheet.getLastRowNum()+1);
		sheet.getRow(sheet.getLastRowNum()).createCell(0).setCellValue(data.getId());
		sheet.getRow(sheet.getLastRowNum()).createCell(1).setCellValue(data.getOperation());
		sheet.getRow(sheet.getLastRowNum()).createCell(2).setCellValue(data.getStartTime());
		sheet.getRow(sheet.getLastRowNum()).createCell(3).setCellValue(data.getEndTime());
		sheet.getRow(sheet.getLastRowNum()).createCell(4).setCellValue(data.getExecutionTime());
		sheet.getRow(sheet.getLastRowNum()).createCell(5).setCellValue(data.isResult());
		sheet.getRow(sheet.getLastRowNum()).createCell(6).setCellValue(data.getExplain());
		FileOutputStream out = new FileOutputStream(filepath);
		out.flush();
		wb.write(out);
		out.close();
	}
	
	/**
	 * 每秒请求响应数据条数统计记录保存
	 * @param path	文件保存路径
	 * @param data	需要保存的数据
	 * @throws IOException
	 */
	public void WriteXls(String path, TimeAxisData data) throws IOException {
		String filepath = path+"\\TimeAxisData.xlsx";
		Workbook wb = null;
		if(new File(filepath).exists()){
			FileInputStream in = new FileInputStream(filepath);
			wb = new XSSFWorkbook(in);
			in.close();
		}else {
			wb = new XSSFWorkbook();
		}	
		Sheet sheet = null;
		if(wb.getNumberOfSheets() == 0){
			sheet = wb.createSheet();
		}else{
			sheet = wb.getSheetAt(0);
		}
		if(sheet.getLastRowNum()==0){
			sheet.createRow(0);
			sheet.getRow(0).createCell(0).setCellValue("time");
			sheet.getRow(0).createCell(1).setCellValue("requestCount");
			sheet.getRow(0).createCell(2).setCellValue("responseCount");
			sheet.getRow(0).createCell(3).setCellValue("successCount");
			sheet.getRow(0).createCell(4).setCellValue("failCount");
		}
		sheet.createRow(sheet.getLastRowNum()+1);
		sheet.getRow(sheet.getLastRowNum()).createCell(0).setCellValue(data.getTime());
		sheet.getRow(sheet.getLastRowNum()).createCell(1).setCellValue(data.getRequestCount());
		sheet.getRow(sheet.getLastRowNum()).createCell(2).setCellValue(data.getResponseCount());
		sheet.getRow(sheet.getLastRowNum()).createCell(3).setCellValue(data.getSuccessCount());
		sheet.getRow(sheet.getLastRowNum()).createCell(4).setCellValue(data.getFailCount());
		FileOutputStream out = new FileOutputStream(filepath);
		out.flush();
		wb.write(out);
		out.close();
	}
}
