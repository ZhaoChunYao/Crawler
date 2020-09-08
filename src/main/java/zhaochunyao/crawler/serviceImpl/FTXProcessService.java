package zhaochunyao.crawler.serviceImpl;


import java.util.regex.Pattern;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import zhaochunyao.crawler.entity.Page;
import zhaochunyao.crawler.nestedCrawler.CommentCrawler;
import zhaochunyao.crawler.service.ProcessService;
import zhaochunyao.crawler.util.LoadPropertiesUtil;
import zhaochunyao.crawler.util.PageProcessUtil;
import zhaochunyao.crawler.util.RegexUtil;

//解析房天下评论页

public class FTXProcessService implements ProcessService{	
	private CommentCrawler cc=new CommentCrawler();
	
	public void process(Page page)
	{
		String content=page.getPageContent();
		HtmlCleaner cleaner=new HtmlCleaner();
		TagNode root=cleaner.clean(content);
		if(!page.getPageUrl().contains("/house/s"))
		{
			//如果是详情页
			this.parseDetailsPage(page,root,content);
		}else{
			//如果是列表页
			//先保存通往下一列表页的url
			//String nextPageUrl="https://newhouse.fang.com"+PageProcessUtil.getAttributeByName(root, LoadPropertiesUtil.loadFTX("nextPageUrlXPath"), "href");
			String nextPageUrl=RegexUtil.extract(content, Pattern.compile("(?<=href=\").*(?=\">下一页)"), 0);
			if(!nextPageUrl.isEmpty())
			{
				page.addToUrlList("https://newhouse.fang.com"+nextPageUrl);
				System.out.println("nextPageUrl: "+nextPageUrl);
			}
			//再保存本页上所有详情页的url
			try {
				Object[] arr=root.evaluateXPath(LoadPropertiesUtil.loadFTX("detailsPageCommonXPath"));//获取所有的含有详情页url的TagNode
				//System.out.println(arr.length);
				
				if(arr.length>0)
				{
					for(Object o:arr)
					{
						TagNode raw=(TagNode) o;
						String detailsPageUrl="https:"+raw.getAttributeByName("href");
						page.addToUrlList(detailsPageUrl);
						System.out.println("detailsPageUrl: "+detailsPageUrl);
					}
				}
			} catch (XPatherException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	public void parseDetailsPage(Page page,TagNode root, String content)
	{		
		//获取楼盘名称
		String name=PageProcessUtil.getField(root, LoadPropertiesUtil.loadFTX("houseNameXPath"), LoadPropertiesUtil.loadFTX("houseNameRegex"));
		if(name.isEmpty())
		{
			//房天下网站上有两种类型的详情页，分别需要不同的XPath
			name=PageProcessUtil.getField(root, LoadPropertiesUtil.loadFTX("houseNameOldXPath"), LoadPropertiesUtil.loadFTX("houseNameRegex"));
		}
		page.setHouseName(name);
		//System.out.println(page.getHouseName());
				
		//获取评论页url
		String url=PageProcessUtil.getAttributeByName(root, LoadPropertiesUtil.loadFTX("commentPageUrlXPath"), "href");
		if(url.isEmpty())
		{
			url=PageProcessUtil.getAttributeByName(root, LoadPropertiesUtil.loadFTX("commentPageUrlOldXPath"), "href");
		}
		
		page.setCommentPageUrl(url);
		//System.out.println(page.getCommentPageUrl());
				
		//获取楼盘id 
		String newCode=RegexUtil.extract(content, Pattern.compile(LoadPropertiesUtil.loadFTX("newCodeRegex")), 0);
		page.setHouseId(newCode);
		//System.out.println(page.getHouseId());
				
		//获取城市名称
		String city=RegexUtil.extract(content, Pattern.compile(LoadPropertiesUtil.loadFTX("cityRegex")), 0);
		page.setCity(city);
					
		//获取评论
		String href=page.getCommentPageUrl();
		if(!href.isEmpty()&&!href.equals("/dianping/"))//有点楼盘没有评论，进入评论页会404
		{
			String comments=cc.start("https:"+href);
			page.setAllComments(comments);
		}
	}
	
}
