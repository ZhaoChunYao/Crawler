package zhaochunyao.crawler.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
	
	public static String extract(String content, Pattern pattern, int groupNo)
	{
		Matcher matcher=pattern.matcher(content);
		String result="";
		if(matcher.find())
		{
			result=result+matcher.group(groupNo);
		}
		return result;
	}

}
