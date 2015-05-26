package com.classtable.format2;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;

import com.classtable.login.TaskObject;

import android.app.Activity;
import android.content.Context;

public class ReadCourseInfo extends Activity {
	private Context context;

	public ReadCourseInfo(Context context) {
		this.context = context;
	}

	public TreeSet<CourseObject> ReadInfo(String fileName) {
		TreeSet<CourseObject> CourseObjectTreeSet = new TreeSet<CourseObject>();
		ObjectInputStream ois = null;
		CourseObject co = null;
		try {
			FileInputStream fis = context.openFileInput(fileName);
			ois = new ObjectInputStream(fis);
			while ((co = (CourseObject) ois.readObject()) != null) {
				CourseObjectTreeSet.add(co);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ois.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return CourseObjectTreeSet;
	}
	
	public TreeSet<TaskObject> ReadTaskInfo(String fileName) {
		TreeSet<TaskObject> taskObjectTreeSet = new TreeSet<TaskObject>();
		ObjectInputStream ois = null;
		try {
			FileInputStream fis = context.openFileInput(fileName);
			ois = new ObjectInputStream(fis);
			taskObjectTreeSet=(TreeSet<TaskObject>) ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ois.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return taskObjectTreeSet;
	}
	
	public TreeMap<String, ArrayList> ReadInfo_TM(String fileName) {
		TreeMap<String, ArrayList> allInfo = new TreeMap<String, ArrayList>();
		ObjectInputStream ois = null;
		try {
			FileInputStream fis = context.openFileInput(fileName);
			ois = new ObjectInputStream(fis);
			allInfo =(TreeMap<String, ArrayList>) ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ois.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return allInfo;
	}
}
