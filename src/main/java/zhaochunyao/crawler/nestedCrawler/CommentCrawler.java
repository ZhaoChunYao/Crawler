package zhaochunyao.crawler.nestedCrawler;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import zhaochunyao.crawler.entity.Page;
import zhaochunyao.crawler.service.DownloadService;
import zhaochunyao.crawler.serviceImpl.HttpClientDownloadService;
import zhaochunyao.crawler.util.LoadPropertiesUtil;

//本类是一个嵌套在外层爬虫里，用于抓取评论的内层爬虫

public class CommentCrawler {
	private DownloadService downloader;
	
	public static void main(String args[])
	{
		String url="https://yizhuangzhenlongfu.fang.com/dianping/";
		CommentCrawler cc=new CommentCrawler();
		cc.start(url);
	}
	
	//通过模拟点击“再显示20条”获取一个楼盘的所有评论
	public String start(String url)
	{
		//String allComments="";
		this.downloader=new HttpClientDownloadService();
		Page page=this.download(url);//评论页的第一页
		String content=page.getPageContent();
		HtmlCleaner cleaner=new HtmlCleaner();
		TagNode root=cleaner.clean(content);
		String curComment=this.getComments(root); //current page comments
		//System.out.println(curComment);
		return curComment;
	}
	
	public Page download(String url)
	{
		Page page=downloader.downloadAndSave(url);
		return page;
	}
	
	//获取当前页面的所有comment
	public String getComments(TagNode root)
	{
		String comments="";
		try {
			Object[] arr=root.evaluateXPath(LoadPropertiesUtil.loadFTX("commentCommonXPath"));
			//System.out.println(arr.length);
			if(arr.length>0)
			{
				for(Object o:arr)
				{
					TagNode node=(TagNode) o;
					String comment=node.getText().toString();
					comments=comments+"\r\n"+comment;
				}
			}
		} catch (XPatherException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return comments;
	}
}
