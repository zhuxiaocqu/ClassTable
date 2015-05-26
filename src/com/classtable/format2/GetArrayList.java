package com.classtable.format2;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.TreeSet;
import java.util.Scanner;
import java.util.TreeSet;

import android.os.Environment;

import com.classtable.login.ParseHtml;
import com.classtable.login.SplashActivity;

public class GetArrayList {
	public static TreeSet<CourseObject> getArrayList() throws IOException {
		TreeSet<CourseObject> arr = new TreeSet<CourseObject>();
		int id;
		String name = "";
		String weeks = "";
		String classes = "";
		String classroom = "";
		String credit = "";
		String teacher = "";
		String classHour = "";
		String teachHour = "";
		String experimentHour = "";
		String courseKind = "";
		String teachKind = "";
		String examKind = "";
		int STE_index = 0;
		for (int i = 0; i < SplashActivity.allInfo.get("classday").size(); i++) {
			id = i + 1;
			name=(String) SplashActivity.allInfo.get("className").get(i);
			credit =(String) SplashActivity.allInfo.get("STE").get(STE_index);
			++STE_index;
			teachHour = (String) SplashActivity.allInfo.get("STE").get(STE_index);
			++STE_index;
			experimentHour =(String) SplashActivity.allInfo.get("STE").get(STE_index);
			++STE_index;
			if(!teachHour.equals("")&&!experimentHour.equals("")){
				classHour = String.valueOf(Integer.parseInt(teachHour)
						+ Integer.parseInt(experimentHour));
			}
			else
				classHour="";
			courseKind = (String) SplashActivity.allInfo.get("courseKind").get(i);
			teachKind = (String) SplashActivity.allInfo.get("teachKind").get(i);
			examKind = (String) SplashActivity.allInfo.get("examKind").get(i);
			teacher = (String) SplashActivity.allInfo.get("teacher").get(i);
			weeks =(String) SplashActivity.allInfo.get("classweek").get(i);
			classes = (String) SplashActivity.allInfo.get("classday").get(i);
			classroom = (String) SplashActivity.allInfo.get("classroom").get(i);
			arr.add(new CourseObject(id, name, weeks, classes, classroom,
					credit, teacher, classHour, teachHour, experimentHour,
					courseKind, teachKind, examKind));
		}
		return arr;
	}
}
