package zhaochunyao.crawler.serviceImpl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import zhaochunyao.crawler.entity.Page;
import zhaochunyao.crawler.service.StoreService;

//This class writes data into a txt file

public class TXTStoreService implements StoreService {
	
	public void store(Page page) {
		File file=new File("D:/DataBase.txt");
		try
		{
			if(!file.exists())
			{
				file.createNewFile();
			}
		
			FileWriter fw= new FileWriter("D:/DataBase.txt",true);
			
			String key=page.getHouseId()+page.getCity();
			
			fw.write("KEY: "+key);
			fw.write("\r\n");
			
			//values:
			fw.write("楼盘名称： "+page.getHouseName());
			fw.write("\r\n");
			fw.write("楼盘页url："+page.getPageUrl());
			fw.write("\r\n");
			fw.write("评论页url："+page.getCommentPageUrl());
			fw.write("\r\n");
			fw.write("评论内容："+page.getAllComments());
			fw.write("\r\n");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
			fw.write("存储时间："+df.format(new Date())+" EDT");
			fw.write("\r\n");
			fw.write("\r\n");
			fw.close();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
