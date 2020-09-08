package zhaochunyao.crawler.util;

import java.util.regex.Pattern;

import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

//一个通过XPath和regex解析页面的工具类

public class PageProcessUtil {
	
	public static String getField(TagNode root, String xPath, String regex)
	{
		String field="";
		
		try {
			Object[] arr=root.evaluateXPath(xPath);
			//System.out.println(arr.length);
			if(arr.length>0)
			{
				TagNode first=(TagNode) arr[0];
				String rawField=first.getText().toString();
				//System.out.println(rawField);
				Pattern fieldPattern=Pattern.compile(regex);
				field=RegexUtil.extract(rawField, fieldPattern, 0);
				//System.out.println(field);
			}
			
		} catch (XPatherException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return field;
	}
	
	public static String getAttributeByName(TagNode root, String xPath, String name)
	{
		String field="";
		
		try {
			Object[] arr = root.evaluateXPath(xPath);
			if(arr.length>0) //最后一页没有下一页，XPath将取不到
			{
				TagNode first=(TagNode) arr[0];
				field=first.getAttributeByName(name);
			}
		} catch (XPatherException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return field;
	}


}
