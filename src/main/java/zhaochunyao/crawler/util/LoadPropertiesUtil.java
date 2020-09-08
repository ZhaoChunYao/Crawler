package zhaochunyao.crawler.util;

import java.util.Locale;
import java.util.ResourceBundle;

//这是一个读取配置文件的工具类

public class LoadPropertiesUtil {
	//读取房天下的配置文件
	public static String loadFTX(String key)
	{
		String value="";
		Locale locale=Locale.getDefault();
		ResourceBundle localResource=ResourceBundle.getBundle("FangTianXia", locale);
		value=localResource.getString(key);
		
		return value;
	}
	
}
