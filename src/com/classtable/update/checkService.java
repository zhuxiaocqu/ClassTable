package com.classtable.update;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.os.IBinder;

import com.classtable.format2.CourseObject;
import com.classtable.format2.GetArrayList;
import com.classtable.format2.ReadCourseInfo;
import com.classtable.format2.TimeTable;
import com.classtable.format2.WriteCourseInfo;
import com.classtable.login.ClassTableActivity;
import com.classtable.login.GetHtml;
import com.classtable.login.NetConnecting;
import com.classtable.login.NetHelper;
import com.classtable.login.ParseHtml;
import com.classtable.login.R;
import com.classtable.login.SplashActivity;

public class checkService extends Service {
	private String filePath = "/data/data/com.classtable.login/files/young.bin";
	private String fileName_TM = "classInfo_TM.bin";
	private Elements body;
	private String stuNo, passWord;
	private boolean isRememberPassword;
	// private TreeMap<String, ArrayList> allInfo = new TreeMap<String,
	// ArrayList>();
	private String fileName = "young.bin";
	int[] IdArr = null;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		System.out.println("in service");
		// ReadCourseInfo readInfo = new ReadCourseInfo(this);
		// allInfo = readInfo.ReadInfo_TM(fileName);
		SharedPreferences preferences = getSharedPreferences("userInfo",
				MODE_PRIVATE);
		stuNo = preferences.getString("userName", "");
		passWord = preferences.getString("password", "");
		isRememberPassword = preferences
				.getBoolean("isRememberPassword", false);
		if (!stuNo.equals("") && !passWord.equals("")) {
			boolean isU = false;
			IdArr = isUpdate();
			if (IdArr != null && IdArr[0] != 0)
				isU = true;
			System.out.println(isU);
			if (isU)// send notifiction when course table changed
				this.showInfo();
		}
		System.out.println("service ends");
		stopSelf();// stop the service itself
		return super.onStartCommand(intent, flags, startId);
	}

	private void showInfo() {
		NotificationManager manager = (NotificationManager) this
				.getSystemService(Context.NOTIFICATION_SERVICE);
		// get the service of the notification
		Notification notification = new Notification();
		// create a notification object
		notification.icon = R.drawable.logo;
		// set the icon of the notification
		notification.tickerText = "您的课表中有更新，点击查看";
		// set the text of the notification
		/***
		 * notification.contentIntent:一个PendingIntent对象，当用户点击了状态栏上的图标时，
		 * 该Intent会被触发 notification.contentView:我们可以不在状态栏放图标而是放一个view
		 * notification.deleteIntent 当当前notification被移除时执行的intent
		 * notification.vibrate 当手机震动时，震动周期设置
		 */
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		// canceled when clicked the notification
		notification.defaults = Notification.DEFAULT_SOUND;
		// add the default sound
		notification.audioStreamType = android.media.AudioManager.ADJUST_LOWER;
		// audioStreamType的值必须AudioManager中的值，代表着响铃的模式

		// 下边的两个方式可以添加音乐
		// notification.sound =
		// Uri.parse("file:///sdcard/notification/ringer.mp3");
		// notification.sound =
		// Uri.withAppendedPath(Audio.Media.INTERNAL_CONTENT_URI, "6");

		Intent intent = new Intent(this,
				com.classtable.format2.ShowTimeTableActivity.class);
		intent.putExtra("idArray", IdArr);
//		intent.putExtra("isRememberPassword", isRememberPassword);
		// the intent can open an activity when click the notification
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
				intent, PendingIntent.FLAG_ONE_SHOT);

		notification.setLatestEventInfo(this, "课表更新", "您的课表有更新，请点击查看",
				pendingIntent);

		// 点击状态栏的图标出现的提示信息设置
		manager.notify(1, notification);

		// update the former one when a new one appears
	}

	public int[] isUpdate() {
		int[] updateIdArr = null;
		try {
			if (NetHelper.IsHaveInternet(this)) {
				if (GetHtml.getHtml(stuNo, passWord, true)) {
					getTreeMap();
					
					TreeSet<CourseObject> TreeSetOld = new TreeSet<CourseObject>();
					ReadCourseInfo readInfo = new ReadCourseInfo(this);

					TreeSet<CourseObject> TreeSetNew = GetArrayList
							.getArrayList();
					WriteCourseInfo writeInfo = new WriteCourseInfo(this);
					
					
					TreeSetOld = readInfo.ReadInfo(fileName);
					TimeTable ttNew = new TimeTable(TreeSetNew);
					TimeTable ttOld = new TimeTable(TreeSetOld);
					updateIdArr = ttNew.checkTableData(ttNew.courseArr,
							ttOld.courseArr);
					writeInfo.WriteInfo_TM(fileName_TM, SplashActivity.allInfo);
					writeInfo.WriteInfo(fileName, TreeSetNew);
					for (int i = 0; i < 10; i++) {
						System.out.print(updateIdArr[i] + " ");
						// }
					}

				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return updateIdArr;
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
					SplashActivity.allInfo.put("className",
							parseHtml.getClassName());
					SplashActivity.allInfo.put("STE", parseHtml.getSTE());
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
