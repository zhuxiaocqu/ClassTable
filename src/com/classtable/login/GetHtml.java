package com.classtable.login;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import android.os.Environment;

public class GetHtml {
	private static String classcontent1="";
	public static String getHtmlStr(String stuNo, String passWord,boolean isWriteFile) throws IOException{
		if(getHtml(stuNo,passWord,isWriteFile))
			return classcontent1;
			else 
				return "";
	}
	public static boolean getHtml(String stuNo, String passWord,boolean isWriteFile)
			throws IOException {
		// ---------------学生登录-------------
		String POST_URL = "http://202.202.1.176:8080/_data/index_login.aspx";
		URL postUrl = new URL(POST_URL);
		// 打开连接
		HttpURLConnection connection = (HttpURLConnection) postUrl
				.openConnection();
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod("POST");
		connection.setUseCaches(false);
		connection.setInstanceFollowRedirects(true);
//		connection.setConnectTimeout(30000);
//		connection.setReadTimeout(30000);
		// 设置请求头
		connection
				.setRequestProperty("User-Agent",
						"Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)");
		connection.setRequestProperty("Content-type",
				"application/x-www-form-urlencoded");
		connection.setRequestProperty("Accept-Language", "zh-cn");
		connection.setRequestProperty("Connection", "Keep-Alive");
		connection.setRequestProperty("Cache-Control", "no-cache");
		connection.setRequestProperty("Accept",
				"textml, application/xhtml+xml, */*");
		connection.setRequestProperty("Accept-Encoding", "gzip, deflate");
		connection.setRequestProperty("Referer",
				"http://202.202.1.176:8080/_data/index_login.aspx");
		connection.connect();
		OutputStream getOutputStream = connection.getOutputStream();
		DataOutputStream out = new DataOutputStream(getOutputStream);
		// 设置请求参数
		String value__VIEWSTATE = "dDw1OTgzNjYzMjM7dDw7bDxpPDE+O2k8Mz47aTw1Pjs+O2w8dDxwPGw8VGV4dDs+O2w86YeN5bqG5aSn5a2mOz4+Ozs+O3Q8cDxsPFRleHQ7PjtsPFw8c2NyaXB0IHR5cGU9InRleHQvamF2YXNjcmlwdCJcPgpcPCEtLQpmdW5jdGlvbiBDaGtWYWx1ZSgpewogdmFyIHZVPWRvY3VtZW50LmFsbC5VSUQuaW5uZXJUZXh0XDsKIHZhciB2Y0ZsYWcgPSAiTk8iXDt0cnl7CiBpZiAoZG9jdW1lbnQuYWxsLlVzZXJJRC52YWx1ZT09JycpewogYWxlcnQoJ+mhu+W9leWFpScrdlUrJ++8gScpXDtkb2N1bWVudC5hbGwuVXNlcklELmZvY3VzKClcO3JldHVybiBmYWxzZVw7Cn0KIGVsc2UgaWYgKGRvY3VtZW50LmFsbC5QYXNzV29yZC52YWx1ZT09JycpewogYWxlcnQoJ+mhu+W9leWFpeWvhuegge+8gScpXDtkb2N1bWVudC5hbGwuUGFzc1dvcmQuZm9jdXMoKVw7cmV0dXJuIGZhbHNlXDsKfQogZWxzZSBpZiAoZG9jdW1lbnQuYWxsLmNDb2RlLnZhbHVlPT0nJyAmJiB2Y0ZsYWcgPT0gIllFUyIpewogYWxlcnQoJ+mhu+W9leWFpemqjOivgeegge+8gScpXDtkb2N1bWVudC5hbGwuY0NvZGUuZm9jdXMoKVw7cmV0dXJuIGZhbHNlXDsKfQogZWxzZSB7IGRvY3VtZW50LmFsbC5kaXZMb2dOb3RlLmlubmVySFRNTD0n5q2j5Zyo6YCa6L+H6Lqr5Lu96aqM6K+BLi4u6K+356iN5YCZISdcOwogcmV0dXJuIHRydWVcO30KfWNhdGNoKGUpe30KfQpmdW5jdGlvbiBTZWxUeXBlKG9iail7CiB2YXIgcz1vYmoub3B0aW9uc1tvYmouc2VsZWN0ZWRJbmRleF0udXNySURcOwogZG9jdW1lbnQuYWxsLlVJRC5pbm5lckhUTUw9c1w7CiBzZWxUeWVOYW1lKClcOwp9CmZ1bmN0aW9uIG9wZW5XaW5Mb2codGhlVVJMLHcsaCl7CnZhciBUZm9ybSxyZXRTdHJcOwpldmFsKCJUZm9ybT0nd2lkdGg9Iit3KyIsaGVpZ2h0PSIraCsiLHNjcm9sbGJhcnM9bm8scmVzaXphYmxlPW5vJyIpXDsKcG9wPXdpbmRvdy5vcGVuKHRoZVVSTCwnd2luS1BUJyxUZm9ybSlcOyAvL3BvcC5tb3ZlVG8oMCw3NSlcOwpldmFsKCJUZm9ybT0nZGlhbG9nV2lkdGg6Iit3KyJweFw7ZGlhbG9nSGVpZ2h0OiIraCsicHhcO3N0YXR1czpub1w7c2Nyb2xsYmFycz1ub1w7aGVscDpubyciKVw7CmlmKHR5cGVvZihyZXRTdHIpIT0ndW5kZWZpbmVkJykgYWxlcnQocmV0U3RyKVw7Cn0KZnVuY3Rpb24gc2hvd0xheShkaXZJZCl7CnZhciBvYmpEaXYgPSBldmFsKGRpdklkKVw7CmlmIChvYmpEaXYuc3R5bGUuZGlzcGxheT09Im5vbmUiKQp7b2JqRGl2LnN0eWxlLmRpc3BsYXk9IiJcO30KZWxzZXtvYmpEaXYuc3R5bGUuZGlzcGxheT0ibm9uZSJcO30KfQpmdW5jdGlvbiBzZWxUeWVOYW1lKCl7CiAgZG9jdW1lbnQuYWxsLnR5cGVOYW1lLnZhbHVlPWRvY3VtZW50LmFsbC5TZWxfVHlwZS5vcHRpb25zW2RvY3VtZW50LmFsbC5TZWxfVHlwZS5zZWxlY3RlZEluZGV4XS50ZXh0XDsKfQpmdW5jdGlvbiB3aW5kb3cub25sb2FkKCl7Cgl2YXIgc1BDPXdpbmRvdy5uYXZpZ2F0b3IudXNlckFnZW50K3dpbmRvdy5uYXZpZ2F0b3IuY3B1Q2xhc3Mrd2luZG93Lm5hdmlnYXRvci5hcHBNaW5vclZlcnNpb24rJyBTTjpOVUxMJ1w7CnRyeXtkb2N1bWVudC5hbGwucGNJbmZvLnZhbHVlPXNQQ1w7fWNhdGNoKGVycil7fQp0cnl7ZG9jdW1lbnQuYWxsLlVzZXJJRC5mb2N1cygpXDt9Y2F0Y2goZXJyKXt9CnRyeXtkb2N1bWVudC5hbGwudHlwZU5hbWUudmFsdWU9ZG9jdW1lbnQuYWxsLlNlbF9UeXBlLm9wdGlvbnNbZG9jdW1lbnQuYWxsLlNlbF9UeXBlLnNlbGVjdGVkSW5kZXhdLnRleHRcO31jYXRjaChlcnIpe30KfQpmdW5jdGlvbiBvcGVuV2luRGlhbG9nKHVybCxzY3IsdyxoKQp7CnZhciBUZm9ybVw7CmV2YWwoIlRmb3JtPSdkaWFsb2dXaWR0aDoiK3crInB4XDtkaWFsb2dIZWlnaHQ6IitoKyJweFw7c3RhdHVzOiIrc2NyKyJcO3Njcm9sbGJhcnM9bm9cO2hlbHA6bm8nIilcOwp3aW5kb3cuc2hvd01vZGFsRGlhbG9nKHVybCwxLFRmb3JtKVw7Cn0KZnVuY3Rpb24gb3Blbldpbih0aGVVUkwpewp2YXIgVGZvcm0sdyxoXDsKdHJ5ewoJdz13aW5kb3cuc2NyZWVuLndpZHRoLTEwXDsKfWNhdGNoKGUpe30KdHJ5ewpoPXdpbmRvdy5zY3JlZW4uaGVpZ2h0LTMwXDsKfWNhdGNoKGUpe30KdHJ5e2V2YWwoIlRmb3JtPSd3aWR0aD0iK3crIixoZWlnaHQ9IitoKyIsc2Nyb2xsYmFycz1ubyxzdGF0dXM9bm8scmVzaXphYmxlPXllcyciKVw7CnBvcD1wYXJlbnQud2luZG93Lm9wZW4odGhlVVJMLCcnLFRmb3JtKVw7CnBvcC5tb3ZlVG8oMCwwKVw7CnBhcmVudC5vcGVuZXI9bnVsbFw7CnBhcmVudC5jbG9zZSgpXDt9Y2F0Y2goZSl7fQp9CmZ1bmN0aW9uIGNoYW5nZVZhbGlkYXRlQ29kZShPYmopewp2YXIgZHQgPSBuZXcgRGF0ZSgpXDsKT2JqLnNyYz0iLi4vc3lzL1ZhbGlkYXRlQ29kZS5hc3B4P3Q9IitkdC5nZXRNaWxsaXNlY29uZHMoKVw7Cn0KXFwtLVw+Clw8L3NjcmlwdFw+Oz4+Ozs+O3Q8O2w8aTwxPjs+O2w8dDw7bDxpPDA+Oz47bDx0PHA8bDxUZXh0Oz47bDxcPG9wdGlvbiB2YWx1ZT0nU1RVJyB1c3JJRD0n5a2m5Y+3J1w+5a2m55SfXDwvb3B0aW9uXD4KXDxvcHRpb24gdmFsdWU9J1RFQScgdXNySUQ9J+W4kOWPtydcPuaVmeW4iFw8L29wdGlvblw+Clw8b3B0aW9uIHZhbHVlPSdTWVMnIHVzcklEPSfluJDlj7cnXD7nrqHnkIbkurrlkZhcPC9vcHRpb25cPgpcPG9wdGlvbiB2YWx1ZT0nQURNJyB1c3JJRD0n5biQ5Y+3J1w+6Zeo5oi357u05oqk5ZGYXDwvb3B0aW9uXD4KOz4+Ozs+Oz4+Oz4+Oz4+Oz4uLMOjMlkDVkXCERYZLsjGqo6wlg==";
		String value_pcInfo = "Mozilla/5.0 (compatible; MSIE 9.0;"
				+ " Windows NT 6.1; Trident/5.0; SLCC2; .NET CLR 2.0.50727; "
				+ ".NET CLR 3.5.30729; .NET CLR 3.0.30729; "
				+ "Media Center PC 6.0; InfoPath.3; .NET4.0C; .NET4.0E)"
				+ "x860 SN:NULL";

		String __VIEWSTATE = "__VIEWSTATE="
				+ URLEncoder.encode(value__VIEWSTATE, "gb2312");
		String pcInfo = "pcInfo=" + URLEncoder.encode(value_pcInfo, "gb2312");
		String typeName = "typeName=" + URLEncoder.encode("学生", "gb2312");
		String Sel_Type = "Sel_Type=" + URLEncoder.encode("STU", "gb2312");
		String UserID = "UserID=" + URLEncoder.encode(stuNo, "gb2312");
		String PassWord = "PassWord=" + URLEncoder.encode(passWord, "gb2312");
		String cCode = "cCode=" + URLEncoder.encode("", "gb2312");

		out.writeBytes(__VIEWSTATE + "&" + Sel_Type + "&" + UserID + "&"
				+ PassWord + "&" + cCode + "&" + pcInfo + "&" + typeName);
		out.flush();
		out.close();
		String sessions = connection.getHeaderField("Set-Cookie");
		String[] sessionID = sessions.split(";");
		// System.out.println(sessionID[0]);
		connection.disconnect();

		// ---------------获取课表-------------
		String POST_URL_ClassTable = "http://202.202.1.176:8080/znpk/Pri_StuSel_rpt.aspx";
		URL postUrl_ClassTable = new URL(POST_URL_ClassTable);
		// 打开连接
		HttpURLConnection connection_ClassTable = (HttpURLConnection) postUrl_ClassTable
				.openConnection();
		connection_ClassTable.setDoOutput(true);
		connection_ClassTable.setDoInput(true);
		connection_ClassTable.setRequestMethod("POST");
		connection_ClassTable.setUseCaches(false);
		connection_ClassTable.setInstanceFollowRedirects(true);
//		connection_ClassTable.setConnectTimeout(30000);
//		connection_ClassTable.setReadTimeout(30000);

		connection_ClassTable
				.setRequestProperty("User-Agent",
						"Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)");
		connection_ClassTable.setRequestProperty("Content-type",
				"application/x-www-form-urlencoded");
		connection_ClassTable.setRequestProperty("Accept-Language", "zh-cn");
		connection_ClassTable.setRequestProperty("Connection", "Keep-Alive");
		connection_ClassTable.setRequestProperty("Cache-Control", "no-cache");
		connection_ClassTable.setRequestProperty("Cookie", sessionID[0]);
		connection_ClassTable.setRequestProperty("Accept",
				"textml, application/xhtml+xml, */*");
		connection_ClassTable.setRequestProperty("Accept-Encoding",
				"gzip, deflate");
		connection_ClassTable.setRequestProperty("Referer",
				"http://202.202.1.176:8080/znpk/Pri_StuSel.aspx");

		connection_ClassTable.connect();
		OutputStream getOutputStream_ClassTable = connection_ClassTable
				.getOutputStream();
		DataOutputStream out_ClassTable = new DataOutputStream(
				getOutputStream_ClassTable);
		String rad = "rad=" + URLEncoder.encode("on", "gb2312");
		String zc_input = "zc_input=" + URLEncoder.encode("1-18", "gb2312");
		String px = "px=" + URLEncoder.encode("1", "gb2312");
		String zc_flag = "zc_flag=" + URLEncoder.encode("1", "gb2312");
		String Sel_XNXQ = "Sel_XNXQ=" + URLEncoder.encode("20121", "gb2312");
		String postData = Sel_XNXQ + "&" + rad + "&" + px;
		out_ClassTable.writeBytes(postData);
		out_ClassTable.flush();
		out_ClassTable.close();

		
		classcontent1 = inputStreamToString(connection_ClassTable
				.getInputStream());
//		System.out.println(classcontent1);
		if(isWriteFile)
			writeToFile("classtable",classcontent1);
		connection_ClassTable.disconnect();
		if(!classcontent1.equals(""))
			return true;
		else
			return false;
	}

	public static String inputStreamToString(InputStream input)
			throws IOException {
		StringBuilder str = new StringBuilder();
		String temp;
		BufferedReader buff = new BufferedReader(new InputStreamReader(input,
				"gb2312"));
		while ((temp=buff.readLine()) != null) {
			str.append(temp);
		}
		return str.toString();
	}

	public static void writeToFile(String fileName, String str) throws IOException {
		String path = "/mnt/sdcard/";
		String tail = ".html";
		File file = new File(path + fileName + tail);
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			if (!file.exists()) {
				file.createNewFile();
			}
			PrintWriter fileW = new PrintWriter(file,"gb2312");
			fileW.print(str);
			fileW.flush();
			fileW.close();
		}
	}

}
