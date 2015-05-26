package com.classtable.setinfo;



import java.util.Calendar;

import com.classtable.login.ClassTableActivity;
import com.classtable.login.R;
import com.classtable.login.SplashActivity;
import com.classtable.update.Checkupdate;

import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Im;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class Switch_Activity extends Activity {

	private SwitchButton SwitchUpdate=null;
	private SwitchButton SwitchWarn=null;
	private SwitchButton SwitchLogIn=null;
	private Intent intent=null;
	private TextView Tb=null;
	private TextView Deleteb=null;
	private SharedPreferences timeData=null;
	
	private ImageButton back=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_switch_);
		
		this.timeData=this.getSharedPreferences("userInfo", MODE_PRIVATE);
		
		this.SwitchUpdate=new SwitchButton("更新",this, 
				this.timeData.getBoolean("更新", true));
		this.SwitchLogIn=new SwitchButton("isRememberPassword",this,
				this.timeData.getBoolean("isRememberPassword", true));
		this.SwitchWarn=new SwitchButton("提醒",this,
				this.timeData.getBoolean("提醒", true));
		this.SwitchUpdate.CreateViewByID(R.id.myswitch);
		this.SwitchWarn.CreateViewByID(R.id.my_warn_switch);
		this.SwitchLogIn.CreateViewByID(R.id.my_self_login);
		//intent=new Intent(this,timeset.class);
		//this.SwitchUpdate.startIntent(intent);
	    this.Tb=(TextView)this.findViewById(R.id.mytime_set);
	    
	    this.setTextToTV(Tb,this.timeData.getString("更新时间", "8:00"));//设置textView的显示内容
	    this.Tb.setOnClickListener(new settime());
	   
	    this.Deleteb=(TextView)super.findViewById(R.id.delete_set);
	    this.setTextToTV(this.Deleteb);
	    this.Deleteb.setOnClickListener(new deleteListener());
	    
	    this.back=(ImageButton) super.findViewById(R.id.back_to_home);
	    this.back.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Switch_Activity.this.finish();
			}
	    	
	    });
		
	}
	
	
	public void setTextToTV(TextView tv ,String time){
		Spannable WordtoSpan = new SpannableString("自定义更新时间\n手动输入每日更新的时间                                  "+time);  
		WordtoSpan.setSpan(new AbsoluteSizeSpan(25), 0, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);   
		WordtoSpan.setSpan(new AbsoluteSizeSpan(20), 7, 19, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);   
		tv.setText(WordtoSpan);
	}
	
	public void setTextToTV(TextView tv){
		Spannable WordtoSpan = new SpannableString("注销登录\n删除所有的记录并退出程序");  
		WordtoSpan.setSpan(new AbsoluteSizeSpan(25), 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);   
		WordtoSpan.setSpan(new AbsoluteSizeSpan(20), 4, 16, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);   
		tv.setText(WordtoSpan);
	}
	
	
  private class settime implements OnClickListener{

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Dialog dialog=new TimePickerDialog(Switch_Activity.this,
				new TimePickerDialog.OnTimeSetListener(){
                    private Calendar calendar=Calendar.getInstance();
                    private AlarmManager alarm=null;
                    private int HOUR_SET=0;
                    private int MINUTE_SET=0;
					@Override
					public void onTimeSet(TimePicker view, int hourOfDay,
							int minute) {
						// TODO Auto-generated method stub
						TextView b=(TextView)findViewById(R.id.mytime_set);
						String text="";
						this.HOUR_SET=hourOfDay;
						this.MINUTE_SET=minute;
						if(minute<10)
						   text=hourOfDay+":0"+minute;
						else
							text=hourOfDay+":"+minute;
						Editor editor=timeData.edit();
						editor.putString("更新时间",text);
						editor.commit();
						setTextToTV(b ,text);
						this.setAlarm(Switch_Activity.this);
					}
					
					private void setAlarm(Context context){
						   Intent intent=new Intent(context,Checkupdate.class);
						   //create the Intent between activity and broadcast
							
						   
						   this.alarm=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
						    //get the alarm service
						   this.calendar.set(Calendar.HOUR_OF_DAY,this.HOUR_SET);
						   //set the hour of the calendar to the value that we want 
						   this.calendar.set(Calendar.MINUTE, this.MINUTE_SET);
						   //set the minute of the calendar to 0
						   this.calendar.set(Calendar.SECOND, 0);
						   //set the minute of the calendar to 0
						   this.calendar.set(Calendar.MILLISECOND,0);
						   //set the millsecond of the calendar to 0
						 
						  
						   intent.setAction("org.long.action.setalarm");//define the action of intent
						   PendingIntent sender=PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
						   //define the PendingIntent
						   this.alarm.setRepeating(AlarmManager.RTC_WAKEUP, this.calendar.getTimeInMillis(),
								   1000*60*60*24,sender);//set the properties of alarm
						                                       //send broatcast at the same time
						}

			        
		},8,00,true);
		dialog.show();
	}
	  
  }
  
  private class deleteListener implements OnClickListener{

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Dialog sure=new AlertDialog.Builder(Switch_Activity.this)
		.setTitle("确定注销")
		.setMessage("确定要注销用户的所有信息吗？")
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			//确定按钮
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				SharedPreferences datainfo=Switch_Activity.this.getSharedPreferences("userInfo", MODE_PRIVATE);
				Editor editor=datainfo.edit();
				editor.clear();
				editor.commit();
				Intent intentStop=new Intent(Switch_Activity.this,ClassTableActivity.class);
				intentStop.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  //注意本行的FLAG设置
				Switch_Activity.this.startActivity(intentStop);
				
			}
		}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
			//取消按钮
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		}).create();
		sure.show();
	}
	  
  }
}
