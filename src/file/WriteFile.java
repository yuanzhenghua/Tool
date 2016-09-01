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
	public String CreateFolder(String file) throws IOException {
		String folderpath = file+"\\"+new SimpleDateFormat("yyyy-MM-dd HHmmss").format(new Date());
		System.out.println(folderpath);
		if(!new File(folderpath).exists()){
			new File(folderpath).mkdirs();
		}
		return folderpath;
	}
	
	public void WriteTxt(String path, TextData data) throws IOException {
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
	
	public void WriteXls(String path, XlsData data) throws IOException {
		String filepath = path+"\\Detailed.xlsx";
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
}
