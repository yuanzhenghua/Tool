package net;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import file.WriteFile;

public class HttpRequest {
	private static long httpRequestCount = 1;
	
    /**
     * 向指定URL发送GET方法的请求
     * @param url	发送请求的URL
     * @param param	请求参数，
     * @param head	请求头参数
     * @return 	所代表远程资源的响应结果
     */
    public static Map<String, String> sendGet(String url, Map<String, String> param, Map<String, String> head) throws Exception {
    	Map<String, String> result = new HashMap<>();
    	result.put("id", String.valueOf(getHttpRequestCount()));
        BufferedReader in = null;
        try {
        	//System.out.println(url);
        	String p = setParam2(param);
        	result.put("req", p);
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection)realUrl.openConnection();
            // 设置通用的请求头属性
            conn = new HttpRequest().setHead(conn, head);
            // 建立实际的连接
            conn.connect();
            result.put("code", conn.getResponseCode()+"");
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            	// 定义 BufferedReader输入流来读取URL的响应
                in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                	if (result.get("resp") == null || result.get("resp").equals("")) {
            			result.put("resp", line);
					} else {
						result.put("resp", result.get("resp")+line);
					}
                }
			} else {
				result.put("resp", "");
			}
            result.put("errorMessage", "");
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception ex) {
            	ex.printStackTrace();
                result.put("errorMessage", ex.getMessage());
            }
        }
        return result;
    }

    private static String setParam2(Map<String, String> param) {
		String p = "";
		if (param != null) {
			for (String key : param.keySet()) {
				if (!p.equals("")) {
					p = p + "&";
				}
				if (param.get(key) != null && param.get(key).equals("")) {
					p = p + key + "=" + param.get(key);
				}
			}
		}
		return p;
	}

	/**
     * 向指定 URL 发送POST方法的请求
     * @param method	提交数据的格式，默认或空值：application/x-www-form-urlencoded，表单模式：multipart/form-data，其他模式只用用到再补充实现
     * @param url	发送请求的 URL
     * @param param	请求参数，不包含文件，key为上传字段中的key，value为上传时value
     * @param file	请求上传文件列表，key或value为空时该对键值会被忽略，不上传
     * @param head	请求头需修改信息
     * @return	所代表远程资源的响应结果
     */
    public static Map<String, String> sendPost(String method, String url, Map<String, String> param, Map<String, String> file,  Map<String, String> head) throws Exception {
        PrintWriter out = null;
        BufferedReader in = null;
        Map<String, String> result = new HashMap<>();
        result.put("id", String.valueOf(getHttpRequestCount()));
        try {
            URL realUrl = new URL(url);
            HttpURLConnection httpConn = (HttpURLConnection) realUrl.openConnection();
            httpConn = new HttpRequest().setHead(httpConn, head);
            httpConn.setDoOutput(true);	// 使用 URL 连接进行输出   
            httpConn.setDoInput(true);	// 使用 URL 连接进行输入   
            httpConn.setUseCaches(false);	// 忽略缓存   
            httpConn.setRequestMethod("POST");	// 设置URL请求方法 
            if (method.equals("") || method == null) {
            	httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			} else {
				httpConn.setRequestProperty("Content-Type", method);
			}
            httpConn.connect();
            //建立输入流，向指向的URL传入参数
            DataOutputStream dos=new DataOutputStream(httpConn.getOutputStream());
            String p = setParam(method, param, file);
            result.put("req", p);
            dos.writeBytes(p);
            dos.flush();
            dos.close();
            //获取回复数据
            in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "utf-8"));
            String line;
            result.put("code", httpConn.getResponseCode()+"");
            if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            	while ((line = in.readLine()) != null) {
            		if (result.get("resp") == null || result.get("resp").equals("")) {
            			result.put("resp", line);
					} else {
						result.put("resp", result.get("resp")+line);
					}
                }
			}
            result.put("errorMessage", "");
        } catch (IOException ex){
        	result.put("errorMessage", ex.getMessage());
        } finally {
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }catch(IOException ex){
                ex.printStackTrace();
                result.put("errorMessage", ex.getMessage());
            }
        }
        return result;
    }
    
    private HttpURLConnection setHead(HttpURLConnection conn, Map<String, String> head) throws Exception{
    	if (head == null) {
    		return conn;
		}
    	for (String key : head.keySet()) {
    		if (key.equals("") || key == null) {
				throw new Exception("请求头参数错误："+head);
			}
    		conn.setRequestProperty(key, head.get(key)); 
    	}
    	return conn;
    }
    
    private static synchronized long getHttpRequestCount(){
    	return HttpRequest.httpRequestCount++;
    }
    
    private static String setParam(String method, Map<String, String> param, Map<String, String> file) throws Exception{
    	String p = "";
    	if (method.equals("multipart/form-data")) {
			if (param != null) {
				p = p + new StringBuffer().append("--").append("---------test-----------");
				p = p + new StringBuffer().append("\r\n");
				for (String key : param.keySet()) {
					if (param.get(key) != null && param.get(key).equals("")) {
						p = p + new StringBuffer().append("Content-Disposition: form-data; name=\""+URLEncoder.encode(key, "utf-8")+"\"");
						p = p + new StringBuffer().append("\r\n\r\n");
						p = p + new StringBuffer().append(URLEncoder.encode(param.get(key), "utf-8"));
					}
				}
				p = p + new StringBuffer().append("\r\n");
			}
			if (file != null) {
				p = p + new StringBuffer().append("--").append("---------test-----------");
				p = p + new StringBuffer().append("\r\n");
				for (String key : file.keySet()) {
					if (file.get(key) != null && file.get(key).equals("") && new WriteFile().fileExists(file.get(key))) {
						p = p + new StringBuffer().append("Content-Disposition: form-data; name=\""+URLEncoder.encode(key, "utf-8")+"\"");
						p = p + new StringBuffer().append("\r\n\r\n");
						p = p + new StringBuffer().append(URLEncoder.encode(param.get(key), "utf-8"));
					}
				}
				p = p + new StringBuffer().append("\r\n");
			}
			p = p + new StringBuffer().append("--").append("---------test-----------").append("--").append("\r\n");
		} else {
			if (param != null) {
				for (String key : param.keySet()) {
					if (p != null && p.equals("")) {
						p = p + "&";
					}
					if (param.get(key) != null && param.get(key).equals("")) {
						p = p + new StringBuffer().append(URLEncoder.encode(key, "utf-8")+"=");
						p = p + new StringBuffer().append(URLEncoder.encode(param.get(key), "utf-8"));
					}
				}
			}
			if (file != null) {
				for (String key : file.keySet()) {
					if (p != null && p.equals("")) {
						p = p + "&";
					}
					if (file.get(key) != null && file.get(key).equals("") && new WriteFile().fileExists(param.get(key))) {
						p = p + new StringBuffer().append(URLEncoder.encode(key, "utf-8")+"=");
						p = p + new StringBuffer().append(URLEncoder.encode(file.get(key), "utf-8"));
					}
				}
			}
		} 	
    	return p;
    }
}
