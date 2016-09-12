package net;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
	private static long httpRequestCount = 0;
	
    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url	发送请求的URL
     * @param param	请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @param head	请求头参数
     * @return URL	所代表远程资源的响应结果
     */
    public static Map<String, String> sendGet(String url, String param, Map<String, String> head) throws Exception {
    	Map<String, String> result = new HashMap<>();
    	result.put("id", String.valueOf(getHttpRequestCount()));
        BufferedReader in = null;
        try {
        	//System.out.println(url);
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
                	result.put("info", result.get("info")+line);
//                	result += line;
                }
			} else {
				result.put("info", "");
			}
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * @param url	发送请求的 URL
     * @param param	请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @param head	请求头需修改信息
     * @return		所代表远程资源的响应结果
     */
    public static Map<String, String> sendPost(String url, String param, Map<String, String> head) throws Exception {
        PrintWriter out = null;
        BufferedReader in = null;
        Map<String, String> result = new HashMap<>();
        result.put("id", String.valueOf(getHttpRequestCount()));
        try {
            URL realUrl = new URL(url);
            HttpURLConnection httpConn = (HttpURLConnection) realUrl.openConnection();
            httpConn.setDoOutput(true);	// 使用 URL 连接进行输出   
            httpConn.setDoInput(true);	// 使用 URL 连接进行输入   
            httpConn.setUseCaches(false);	// 忽略缓存   
            httpConn.setRequestMethod("POST");	// 设置URL请求方法 
            httpConn = new HttpRequest().setHead(httpConn, head);
            httpConn.connect();
            //建立输入流，向指向的URL传入参数
            DataOutputStream dos=new DataOutputStream(httpConn.getOutputStream());
            dos.writeBytes(param);
            dos.flush();
            dos.close();
            String line;
            result.put("code", httpConn.getResponseCode()+"");
            if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) { 	
            	while ((line = in.readLine()) != null) {
                    result.put("info", result.get("info")+line);
                }
			}        
        } finally {
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
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
}
