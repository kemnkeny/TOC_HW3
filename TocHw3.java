/*
林彥旭
F74001022

用inputStream 和 BufferedReader 讀取 URL 的內容
存入JSON Array後，用for loop逐一搜尋
找到符合pattern格式的資訊
再判斷年月份是否符合搜尋條件
加總後印出平均值
*/
import java.net.*;
import java.io.*;
import org.json.*;
import java.lang.String;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TocHw3 
{
	public static void main(String[] args) throws Exception
	{
		try 
		{
			if(args.length == 4)
			{
				String url = args[0];
				String location = args[1];
				String road = args[2];
				int year = Integer.valueOf(args[3]);
				int total = 0, num = 0;
				
	//			String url = "http://www.datagarage.io/api/5365dee31bc6e9d9463a0057";
	//			String location = "文山區";
	//			String road = "辛亥路";
	//			int year = 103;
				
				Pattern pattern = Pattern.compile(".*"+location+".*"+road+".*");
				Matcher matcher;
				String testStr;
				
				InputStream urlFile = new URL(url).openStream();
				BufferedReader rd = new BufferedReader(new InputStreamReader(urlFile, Charset.forName("UTF-8")));
				JSONArray jsonData = new JSONArray(new JSONTokener(rd));
				JSONObject jsonObj;
				
				for(int i=0; i<jsonData.length(); i++)
				{
					jsonObj = jsonData.getJSONObject(i);
					testStr = jsonObj.toString();
					matcher = pattern.matcher(testStr);
					if(matcher.find())
					{
						int getYear = jsonObj.getInt("交易年月");
						if(getYear >= (year*100) && getYear < ((year+1)*100))
						{
							total += jsonObj.getInt("總價元");
							num ++;
						}
					}
				}
				if(num == 0)
				{
					System.out.println("Data not found!");
				}
				else
				{
					System.out.println(total/num);
				}
			}
		}
		catch (Exception e)
		{
			System.out.println("File not found");
		}
	}	
}

