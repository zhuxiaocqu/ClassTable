package com.classtable.format2;


import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;

import com.classtable.login.TaskObject;

import android.app.Activity;
import android.content.Context;


public class WriteCourseInfo extends Activity{
	//如果不是在Activity下，必须加Context,与程序关联，否则无法执行
	private Context context;
	
	public WriteCourseInfo(Context context){
		this.context=context;
	}
	public void WriteInfo(String fileName,TreeSet<CourseObject> CourseObjectArrayList){
		ObjectOutputStream oos=null;
		try{
			FileOutputStream fos=context.openFileOutput(fileName,MODE_PRIVATE);
			oos=new ObjectOutputStream(fos);
			for(CourseObject co:CourseObjectArrayList){
				oos.writeObject(co);
			}			
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				oos.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public void WriteTaskInfo(String fileName,TreeSet<TaskObject> TaskObjectTreeSet){
		ObjectOutputStream oos=null;
		try{
			//以覆盖模式打开文件输出流   追加模式为Context.MODE_APPEND 
			FileOutputStream fos=context.openFileOutput(fileName,MODE_PRIVATE);
			oos=new ObjectOutputStream(fos);
			oos.writeObject(TaskObjectTreeSet);
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				oos.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public void WriteInfo_TM(String fileName,TreeMap<String, ArrayList> allInfo){
		ObjectOutputStream oos=null;
		try{
			FileOutputStream fos=context.openFileOutput(fileName,MODE_PRIVATE);
			oos=new ObjectOutputStream(fos);
			oos.writeObject(allInfo);
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				oos.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}