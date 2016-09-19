package net;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.WebDriver;

public class HtmlResources {
	
	public static List<String> getHtmlResUrl(String url, String path, Map<String, String> param, Map<String, String> head) throws Exception{
		List<String> resUrl = new ArrayList<>();
		String content = HttpRequest.sendGet(url+path, param, head).get("resp");
		resUrl.add(url+path);
		resUrl.addAll(HtmlResources.getPicture(url, content));
		resUrl.addAll(HtmlResources.getCss(url, content));
		resUrl.addAll(HtmlResources.getJS(url, content));
		return resUrl;
	}

	public static List<String> getPicture(String url, String content) throws Exception {
		List<String> pictures = new ArrayList<>();	
		String searchImgReg = "(?x)(src|SRC|background|BACKGROUND)=('|\")(/([\\w-]+/)*([\\w-]+\\.(jpg|JPG|png|PNG|gif|GIF)))('|\")";
		String searchImgReg2 = "(?x)(src|SRC|background|BACKGROUND)=('|\")(http://([\\w-]+\\.)+[\\w-]+(:[0-9]+)*(/[\\w-]+)*(/[\\w-]+\\.(jpg|JPG|png|PNG|gif|GIF)))('|\")";
		
		List<String> list1 =  new HtmlResources().getAddress(content, searchImgReg);
		for (String l : list1) {
			pictures.add(url+l);
		}
		List<String> list2 =  new HtmlResources().getAddress(content, searchImgReg2);
		for (String l : list2) {
			pictures.add(l);
		}
		return pictures;
	}
	
	public static List<String> getCss(String url, String content) throws Exception {
		List<String> css = new ArrayList<>();	
		String searchImgReg = "(?x)(href)=('|\")(/([\\w-]+/)*([\\w-]+\\.(css)))('|\")";
		String searchImgReg2 = "(?x)(href)=('|\")(http://([\\w-]+\\.)+[\\w-]+(:[0-9]+)*(/[\\w-]+)*(/[\\w-]+\\.(css)))('|\")";
		
		List<String> list1 =  new HtmlResources().getAddress(content, searchImgReg);
		for (String l : list1) {
			css.add(url+l);
		}
		List<String> list2 =  new HtmlResources().getAddress(content, searchImgReg2);
		for (String l : list2) {
			css.add(l);
		}
		return css;
	}
	
	public static List<String> getJS(String url, String content) throws Exception {
		List<String> js = new ArrayList<>();	
		String searchImgReg = "(?x)(src|SRC)=('|\")(/([\\w-]+/)*([\\w-]+\\.(js)))('|\")";
		String searchImgReg2 = "(?x)(src|SRC)=('|\")(http://([\\w-]+\\.)+[\\w-]+(:[0-9]+)*(/[\\w-]+)*(/[\\w-]+\\.(js)))('|\")";
		
		List<String> list1 =  new HtmlResources().getAddress(content, searchImgReg);
		for (String l : list1) {
//			js = HttpRequest.getHtml(url+l, "");
			js.add(url+l);
		}
		List<String> list2 =  new HtmlResources().getAddress(content, searchImgReg2);
		for (String l : list2) {
//			js = HttpRequest.getHtml(l, "");
			js.add(l);
		}
		return js;
	}
	
	private List<String> getAddress(String content, String reg) throws Exception{
		List<String> address = new ArrayList<>();
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(content);
		while (matcher.find()) {
			//System.out.println(matcher.group(3));
			if (!matcher.group(3).equals("") && matcher.group(3) != null) {
				address.add(matcher.group(3));
			} else {
				throw new Exception("资源地址未空:"+matcher);
			}
			
		}
		return address;
	}
}
