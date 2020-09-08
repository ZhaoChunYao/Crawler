package zhaochunyao.crawler.start;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import zhaochunyao.crawler.entity.Page;
import zhaochunyao.crawler.service.DownloadService;
import zhaochunyao.crawler.service.ProcessService;
import zhaochunyao.crawler.service.StoreService;
import zhaochunyao.crawler.serviceImpl.HttpClientDownloadService;
import zhaochunyao.crawler.serviceImpl.MySQLStoreService;
import zhaochunyao.crawler.serviceImpl.TXTStoreService;
import zhaochunyao.crawler.serviceImpl.ConsoleStoreService;
import zhaochunyao.crawler.serviceImpl.FTXProcessService;

//Here is the real main method of the program

public class StartFTXCrawler {
	private DownloadService downloader;
	private ProcessService processor;
	private StoreService aStore;
	private Queue<String> urlQueue=new ConcurrentLinkedDeque<String>(); //保证 提取-删除 操作的原子性，防止重复提取同一个url
	
	public static void main(String[] args)
	{
		StartFTXCrawler ftxCrawler=new StartFTXCrawler();
		ftxCrawler.setDownloader(new HttpClientDownloadService());
		ftxCrawler.setProcessor(new FTXProcessService());
		ftxCrawler.setStore(new MySQLStoreService());
		
		String url="https://newhouse.fang.com/house/s/";
		ftxCrawler.urlQueue.add(url);
		
		ftxCrawler.start();
		ftxCrawler.end(url);
	}
	
	public void start()
	{
		int i=0;
		
		while(!this.urlQueue.isEmpty())
		{
			
			System.out.println("Iteration: "+i);
			
			String url=this.urlQueue.poll();			
			try {
				//download
				Page page=this.download(url);
				//process
				this.process(page);
				//如果是列表页，就将下一列表页的url和列表中的所有详情页url加入Queue中；如果是详情页，以它调用的getUrlList()将返回空
				this.urlQueue.addAll(page.getUrlList());
				//如果是详情页，就把它的评论储存起来
				if(!page.getPageUrl().contains("/house/s"))
				{
					System.out.println("Page Saved");
					this.save(page);
				}
			}catch(Exception e) {
				e.printStackTrace();
				urlQueue.add(url);
				continue;
			}
			
			i++;
		}
		//返回能通过“下一页”按钮到达的列表页的最后一页的页码
	}
	
	//房天下网站的最后几页不通过“下一页”连接，只能手工拼接出url来获取
	public void end(String url1)
	{
		//找出最后一页的页码
		Page temp=this.download(url1);
		String content=temp.getPageContent();
		Pattern p=Pattern.compile("(?<=\\/s\\/b9).*(?=\\/\">尾页)");
		Matcher m=p.matcher(content);
		int lastPageNo=0;
		if(m.find())
		{
			lastPageNo=Integer.parseInt(m.group(0));
			//System.out.println(lastPageNo);
		}
		
		//把最后几个列表页的url添加到Queue
		for(int i=25;i<=lastPageNo;i++)
		{
			//System.out.println(i);
			this.urlQueue.add("https://newhouse.fang.com/house/s/b9"+i);
		}
		
		int i=0;
		
		//开始下载
		while(!this.urlQueue.isEmpty())
		{	
			System.out.println("Iteration: "+i);
			
			String url=this.urlQueue.poll();			
			try {
				//download
				Page page=this.download(url);
				//process
				this.process(page);
				//如果是列表页，就将下一列表页的url和列表中的所有详情页url加入Queue中；如果是详情页，以它调用的getUrlList()将返回空
				this.urlQueue.addAll(page.getUrlList());
				//如果是详情页，就把它的评论储存起来
				if(!page.getPageUrl().contains("/house/s"))
				{
					System.out.println("Page Saved");
					this.save(page);
				}
			}catch(Exception e) {
				e.printStackTrace();
				urlQueue.add(url);
				continue;
			}
			i++;
		}
	}

	public StoreService getStore() {
		return aStore;
	}

	public void setStore(StoreService store) {
		this.aStore = store;
	}

	public DownloadService getDownloader() {
		return downloader;
	}

	public void setDownloader(DownloadService downloader) {
		this.downloader = downloader;
	}

	public ProcessService getProcessor() {
		return processor;
	}

	public void setProcessor(ProcessService processor) {
		this.processor = processor;
	}

	public Page download(String url)
	{
		Page page=downloader.downloadAndSave(url);
		return page;
	}
	
	public void process(Page page)
	{
		processor.process(page);
	}
	
	public void save(Page page)
	{
		aStore.store(page);
	}
}

