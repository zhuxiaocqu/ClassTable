package com.classtable.login;

import java.io.Serializable;

import com.classtable.format2.CourseObject;

public class TaskObject implements Serializable,Comparable<TaskObject>{
	private String courseName;
	private String strHomeWork;
	private String strLearn;
	public TaskObject(String courseName ,String strHomeWork,String strLearn){
		this.courseName=courseName;
		this.strHomeWork=strHomeWork;
		this.strLearn=strLearn;
	}
	public void setStrHomeWork(String strHomeWork){
		this.strHomeWork=strHomeWork;
	}
	public void setStrLearn(String strLearn){
		this.strLearn=strLearn;
	}
	public String getCourseName(){
		return courseName;
	}
	public String getStrHomeWork(){
		return strHomeWork;
	}
	public String getStrLearn(){
		return strLearn;
	}
	@Override
	public int compareTo(TaskObject another) {
		// TODO Auto-generated method stub
//		if(this.getCourseName()>another.getCourseName())
//			return 1;
		return 1;
	}

}
