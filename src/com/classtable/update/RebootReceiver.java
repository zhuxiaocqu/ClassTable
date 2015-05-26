package com.classtable.update;
import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;


public class RebootReceiver extends BroadcastReceiver {
	private AlarmManager alarm=null;
	private Calendar calendar=Calendar.getInstance();//Calendar是一类可以将时间转化成绝对时间
	                                                 //毫秒数的一个类
    private  int HourOfDay=0;//定时更新的小时
    
    private int Minute=0;
    
    private SharedPreferences timeData=null;
    
    private final int TIME_INTERVAL=1000*60*60*24;//set the interval of the alarm repeating

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		this.timeData=context.getSharedPreferences("userInfo", context.MODE_PRIVATE);
		if(this.timeData.getBoolean("更新时间", true)){
		String text=this.timeData.getString("更新时间","8:00");
		String []text_time=text.split(":");
		this.HourOfDay=Integer.parseInt(text_time[0]);
		this.Minute=Integer.parseInt(text_time[1]);
		this.setAlarm(context);//send broadcast to Checkupdate 
		}
        
	}
	
	private void setAlarm(Context context){
		   Intent intent=new Intent(context,Checkupdate.class);
		   //create the Intent between activity and broadcast
			
		   
		   this.alarm=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		    //get the alarm service
		   this.calendar.set(Calendar.HOUR_OF_DAY,this.HourOfDay);
		   //set the hour of the calendar to the value that we want 
		   this.calendar.set(Calendar.MINUTE, this.Minute);
		   //set the minute of the calendar to 0
		   this.calendar.set(Calendar.SECOND, 0);
		   //set the minute of the calendar to 0
		   this.calendar.set(Calendar.MILLISECOND,0);
		   //set the millsecond of the calendar to 0
		 
		  
		   intent.setAction("org.long.action.setalarm");//define the action of intent
		   PendingIntent sender=PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		   //define the PendingIntent
		   this.alarm.setRepeating(AlarmManager.RTC_WAKEUP, this.calendar.getTimeInMillis(),
				   this.TIME_INTERVAL,sender);//set the properties of alarm
		                                       //send broatcast at the same time
		}

}
