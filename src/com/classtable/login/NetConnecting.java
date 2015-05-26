package com.classtable.login;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeSet;

import com.classtable.format2.CourseObject;
import com.classtable.format2.GetArrayList;
import com.classtable.format2.WriteCourseInfo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StrictMode;

public class NetConnecting extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.netconnecting_activity);
//		netconnect();
		new Thread() {
//			public Handler mHandler;
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
//				Looper.prepare();
//				mHandler = new Handler() {
//					public void handleMessage(Message msg) {
						// process incoming messages here
						netconnect();
//					}
//				};
			}
		}.start();
	}

	public void netconnect() {
		Looper.prepare();
		SharedPreferences preferences = getSharedPreferences("userInfo",
				MODE_PRIVATE);
		final Editor editor = preferences.edit();
		Intent getData = getIntent();
		String stuNo = getData.getStringExtra("userName");
		System.out.println(stuNo);
		String passWord = getData.getStringExtra("password");
		System.out.println(passWord);
		boolean isRememberPassword = getData.getBooleanExtra(
				"isRememberPassword", false);
		try {
			if (NetHelper.IsHaveInternet(NetConnecting.this)) {
				System.out.println("has net");
				if (GetHtml.getHtml(stuNo, passWord, true)) {
					if (Environment.getExternalStorageState().equals(
							Environment.MEDIA_MOUNTED)) {
						String filePath = Environment
								.getExternalStorageDirectory()
								.getAbsolutePath()
								+ "/classtable.html";
						File htmlFile = new File(filePath);
						if (htmlFile.exists()) {
							try {
								// 解析得到的html文件，将课表信息装入TreeMap中
								ParseHtml parseHtml = new ParseHtml(filePath);
								SplashActivity.allInfo.put("className",
										parseHtml.getClassName());
								SplashActivity.allInfo.put("STE",
										parseHtml.getSTE());
								SplashActivity.allInfo.put("courseKind",
										parseHtml.getClassKind());
								SplashActivity.allInfo.put("teachKind",
										parseHtml.getTeachKind());
								SplashActivity.allInfo.put("examKind",
										parseHtml.getExamKind());
								SplashActivity.allInfo.put("teacher",
										parseHtml.getTeacher());
								SplashActivity.allInfo.put("classweek",
										parseHtml.getClassWeek());
								SplashActivity.allInfo.put("classday",
										parseHtml.getClassDay());
								SplashActivity.allInfo.put("classroom",
										parseHtml.getPlace());
								// 将得到的TreeMap写入缓存bin文件
								String fileName = "classInfo_TM.bin";
								String fileName_format02 = "young.bin";
								TreeSet<CourseObject> CourseObjectTreeSet=GetArrayList.getArrayList();
								WriteCourseInfo writeInfo = new WriteCourseInfo(
										this);
								writeInfo.WriteInfo_TM(fileName,
										SplashActivity.allInfo);
								writeInfo.WriteInfo(fileName_format02,
										CourseObjectTreeSet);
								System.out.println("successful");
								String timeOfTerm = GetStartOfTerm.getSchoolCalendar();
								//保存用户基本信息
								editor.putString("userName", stuNo);
								editor.putString("password", passWord);
								editor.putBoolean("isRememberPassword", isRememberPassword);
								editor.putString("timeOfTerm", timeOfTerm);
								editor.putString("stuRealName", parseHtml.getStuName());
								editor.commit();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}

					Intent jumpToClassTable = new Intent(NetConnecting.this,
							ClassTableActivity.class);
					startActivity(jumpToClassTable);
					finish();
				}
			} else {
				//
				System.out.println("not has net");
				AlertDialog.Builder tip = new AlertDialog.Builder(
						NetConnecting.this);
				tip.setTitle("网络异常！");
				tip.setIcon(R.drawable.logo);
				tip.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
//								netconnect();
							}
						});
//				tip.setNegativeButton("退出",
//						new DialogInterface.OnClickListener() {
//
//							@Override
//							public void onClick(DialogInterface dialog,
//									int which) {
//								// TODO Auto-generated method stub
//								NetConnecting.this.finish();
//							}
//						});
				tip.create().show();

				//
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Looper.loop();
	}
}
