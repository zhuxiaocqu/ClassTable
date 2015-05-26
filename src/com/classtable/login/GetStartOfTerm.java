package com.classtable.login;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

public class GetStartOfTerm {
	public static String getSchoolCalendar() throws IOException {
		String url = "http://202.202.1.176:8080/_data/index_YL.aspx";
		URL getUrl = new URL(url);
		HttpURLConnection connect_get = (HttpURLConnection) getUrl
				.openConnection();
		connect_get.setDoInput(true);
		connect_get.setDoOutput(true);
//		connect_get.setConnectTimeout(20000);
//		connect_get.setReadTimeout(20000);
		connect_get.setRequestMethod("GET");

		connect_get.connect();
		java.io.InputStream input = connect_get.getInputStream();
		StringBuilder html = new StringBuilder();
		String temp;
		BufferedReader reader = new BufferedReader(new InputStreamReader(input,"gb2312"));
		while ((temp = reader.readLine()) != null) {
			html.append(temp);
		}
		reader.close();
		return getStudyTime(html.toString());
	}

	public static String getStudyTime(String html) {
		org.jsoup.nodes.Document doc = Jsoup.parse(html);
		Elements body = doc.getElementsByTag("body");
		Elements time_ele = body.select("td[align=center]");
		String time_str = time_ele.get(0).text();
		int left_bracket = time_str.indexOf("(");
		int right_bracket = time_str.indexOf(")");
		String startOfTerm = time_str
				.substring(left_bracket + 1, right_bracket);
		return startOfTerm;
	}


}
