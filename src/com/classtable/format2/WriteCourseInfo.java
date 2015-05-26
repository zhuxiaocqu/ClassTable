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
	//���������Activity�£������Context,���������������޷�ִ��
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
			//�Ը���ģʽ���ļ������   ׷��ģʽΪContext.MODE_APPEND 
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