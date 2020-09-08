package zhaochunyao.crawler.serviceImpl;

import zhaochunyao.crawler.service.DownloadService;
import zhaochunyao.crawler.util.PageDownloadUtil;
import zhaochunyao.crawler.entity.Page;

//It downloads web page using HttpClient

public class HttpClientDownloadService implements DownloadService {
	
	//This main is made for testing purpose;
	public static void main(String[] args)
	{
		HttpClientDownloadService downloader=new HttpClientDownloadService();
		String url="https://jinyuefu010.fang.com/";
		Page page=new Page();
		
		page=downloader.downloadAndSave(url);
		System.out.println(page.getPageContent());
	}

	public Page downloadAndSave(String url) 
	{
		String pageContent=PageDownloadUtil.getPageContent(url);
		Page page=new Page();
		page.setPageContent(pageContent);
		page.setPageUrl(url);
		return page;
	}

}
