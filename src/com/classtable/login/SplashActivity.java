package com.classtable.login;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TreeMap;
import java.util.TreeSet;

import com.classtable.format2.CourseObject;
import com.classtable.format2.GetArrayList;
import com.classtable.format2.ReadCourseInfo;
import com.classtable.format2.WriteCourseInfo;
import com.classtable.update.Checkupdate;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class SplashActivity extends Activity {

	private boolean isRememberPassword;
	private TextView versionText;
	public static TreeMap<String, ArrayList> allInfo = new TreeMap<String, ArrayList>();
	private String fileName = "classInfo_TM.bin";
	private String fileName_02="young.bin";
	private Handler myHandler;
	// Update数据
	private AlarmManager alarm = null;
	private Calendar calendar = Calendar.getInstance();// Calendar是一类可以将时间转化成绝对时间
														// 毫秒数的一个类
	private final int HourOfDay = 8;// 定时更新的小时
	private final int TIME_INTERVAL = 1000 * 60 * 60 * 24;// set the interval of
															// the alarm
															// repeating

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.splash_activity);
		versionText=(TextView)findViewById(R.id.splash_version);
		versionText.setText("随时随地\n\n"+"      "+"掌握最新课表信息\n\n"+"            "+"从此摆脱忘课尴尬");
		Log.d("debug", "1");
		SharedPreferences preferences = getSharedPreferences("userInfo",
				MODE_PRIVATE);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		isRememberPassword = preferences
				.getBoolean("isRememberPassword", false);
		myHandler=new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				Intent intentToLogin = new Intent();
				Intent toTable = new Intent();
				switch(msg.what){
				case 0:
					intentToLogin
							.setClass(SplashActivity.this, LoginActivity.class);
					startActivity(intentToLogin);
					finish();
					break;
				case 1:
					toTable.setClass(SplashActivity.this,
							ClassTableActivity.class);
					startActivity(toTable);
					finish();
					break;
				case 2:
					intentToLogin
							.setClass(SplashActivity.this, LoginActivity.class);
					startActivity(intentToLogin);
					finish();
					break;
				case 3:
					toTable.setClass(SplashActivity.this,
							ClassTableActivity.class);
					startActivity(toTable);
					finish();
					break;
					default:
						break;
				}
				
			}
			
		};
		
		new Thread() {
			public void run() {
				Looper.prepare();
				setAlarm();
				selectActivity();
				Looper.loop();
			}
		}.start();
	}

	private void setAlarm() {
		System.out.println("setAlarm");
		Intent intent = new Intent(this, Checkupdate.class);
		// create the Intent between activity and broadcast
		this.alarm = (AlarmManager) super
				.getSystemService(Context.ALARM_SERVICE);
		// get the alarm service
		this.calendar.set(Calendar.HOUR_OF_DAY, this.HourOfDay);
		// set the hour of the calendar to the value that we want
		this.calendar.set(Calendar.MINUTE, 0);
		// set the minute of the calendar to 0
		this.calendar.set(Calendar.SECOND, 0);
		// set the minute of the calendar to 0
		this.calendar.set(Calendar.MILLISECOND, 0);
		// set the millsecond of the calendar to 0
		intent.setAction("org.long.action.setalarm");// define the action of
														// intent
		PendingIntent sender = PendingIntent.getBroadcast(this, 0, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		// define the PendingIntent
		// System.out.println("set----------------------------");
		this.alarm.setRepeating(AlarmManager.RTC_WAKEUP,
				this.calendar.getTimeInMillis(), this.TIME_INTERVAL, sender);
	}

	private void selectActivity() {
		try {
			if (!isRememberPassword) {
				// 跳转到MainActivity
				Log.d("debug", "3");
				
				myHandler.sendEmptyMessage(0);
			} else {
				ReadCourseInfo readInfo = new ReadCourseInfo(this);
				if ((allInfo = readInfo.ReadInfo_TM(fileName)).isEmpty()) {
//					System.out.println("allInfo empty");
					if (getTreeMap()) {
						WriteCourseInfo writeInfo = new WriteCourseInfo(this);
						writeInfo.WriteInfo_TM(fileName, allInfo);
						TreeSet<CourseObject> TreeSetNew = GetArrayList
								.getArrayList();
						writeInfo.WriteInfo(fileName_02, TreeSetNew);
						
						myHandler.sendEmptyMessage(1);
					}else{
						
						myHandler.sendEmptyMessage(2);
					}
				} else {
//					System.out.println("allInfo not empty");
					allInfo = readInfo.ReadInfo_TM(fileName);
					
					myHandler.sendEmptyMessage(3);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(this, "获取用户信息", 0).show();
		}
		

	}

	public boolean getTreeMap() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			String filePath = Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/classtable.html";
			File htmlFile = new File(filePath);
			if (htmlFile.exists()) {
				try {
					ParseHtml parseHtml = new ParseHtml(filePath);
					allInfo.put("className", parseHtml.getClassName());
					allInfo.put("STE", parseHtml.getSTE());
					allInfo.put("courseKind", parseHtml.getClassKind());
					allInfo.put("teachKind", parseHtml.getTeachKind());
					allInfo.put("examKind", parseHtml.getExamKind());
					allInfo.put("teacher", parseHtml.getTeacher());
					allInfo.put("classweek", parseHtml.getClassWeek());
					allInfo.put("classday", parseHtml.getClassDay());
					allInfo.put("classroom", parseHtml.getPlace());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return true;
		} else
			return false;
	}

}
