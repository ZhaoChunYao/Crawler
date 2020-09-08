package zhaochunyao.crawler.serviceImpl;

import zhaochunyao.crawler.entity.Page;
import zhaochunyao.crawler.service.StoreService;

//本类仅用于测试下载-解析-存储流程

public class ConsoleStoreService implements StoreService {

	public void store(Page page) {
		System.out.println("楼盘名称: "+page.getHouseName());
		System.out.println("评论页链接: "+page.getCommentPageUrl());
		//System.out.println("楼盘页链接: "+page.getPageUrl());
		System.out.println("楼盘id: "+page.getHouseId());
		System.out.println("城市: "+page.getCity());
	}

}
