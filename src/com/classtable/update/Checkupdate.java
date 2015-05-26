package com.classtable.update;

import java.util.Calendar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

public class Checkupdate extends BroadcastReceiver {
	private Calendar calendar = null;
	private int HourOfDay = 0;
	private int MinuteOfDay = 0;
	
	private int hour=0;
	private int minute=0;
    private SharedPreferences timeData=null;
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		this.timeData=context.getSharedPreferences("userInfo", context.MODE_PRIVATE);
		String []text=this.timeData.getString("更新时间", "8:00").split(":");
		this.hour=Integer.parseInt(text[0]);
		this.minute=Integer.parseInt(text[1]);
		calendar = Calendar.getInstance();
		this.HourOfDay = this.calendar.get(Calendar.HOUR_OF_DAY);
		this.MinuteOfDay = this.calendar.get(Calendar.MINUTE);
		
		System.out.println("++++++++++++++++asd"+this.hour+":"+this.minute+this.timeData.getBoolean("更新", true)
				+this.HourOfDay+" "+this.MinuteOfDay);
		if(this.timeData.getBoolean("更新", true)&&(this.hour==this.HourOfDay)
				&&(this.minute==this.MinuteOfDay)){
			System.out.println("-------------------asdasda");
			
			//		System.out.println("current time----" + this.HourOfDay);
			//		Toast.makeText(context, this.HourOfDay + "", Toast.LENGTH_SHORT).show();

			if ("org.long.action.setalarm".equals(intent.getAction())) {
				
					// receive the brocast that we defined
					//				Toast.makeText(context, "接收到闹钟广播", Toast.LENGTH_SHORT).show();
					// the function that we should finsh background
					Intent intentToService = new Intent(context, checkService.class);
					context.startService(intentToService);
			}
		}
	}
}