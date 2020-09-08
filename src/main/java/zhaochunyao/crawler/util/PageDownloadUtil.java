package zhaochunyao.crawler.util;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

//This class provides a page download method

public class PageDownloadUtil {
	
	//This main is made for testing purpose;
	public static void main(String[] args)
	{
		String url="https://newhouse.fang.com/house/s/";
		String pageContent=PageDownloadUtil.getPageContent(url);
		System.out.println(pageContent);
	}
	
	public static String getPageContent(String url)
	{
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String content=null;
        
        System.out.println(url);
        
        //试图伪装成浏览器，否则会返回404
        HttpGet request = new HttpGet(url);
        request.addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9" );
        request.addHeader("Accept-Encoding", "gzip, deflate, br");
        request.addHeader("Accept-Language","en-US,en;q=0.9,zh-CN;q=0.8,zh;q=0.7" );
        request.addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.89 Safari/537.36" );
        
        //System.out.println(request.getURI());
        
        try {
            response = httpClient.execute(request);
            
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            	HttpEntity pageContent = response.getEntity(); 
                content = EntityUtils.toString(pageContent, "gb2312");  //charset: utf-8, gb2312 
            } else {
                System.out.println("返回状态不是200");
                content = EntityUtils.toString(response.getEntity(), "utf-8");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            HttpClientUtils.closeQuietly(response);
            HttpClientUtils.closeQuietly(httpClient);
        }
        
        return content;
		
    }
		
}
