package com.classtable.format2;

import java.util.TreeSet;



public class Course {
	TreeSet<CourseInfo> CourseSet;
	Course(){
		CourseSet=new TreeSet<CourseInfo>();
	}
	public void addCourseInfo(CourseInfo cf){
		//�ȼ���Ƿ�γ�������ͬ��Ԥ���ظ�������ͬ�����ܴκϲ�	
		boolean flag=false;
		for(CourseInfo c:CourseSet){
			if(c.CourseName.equals(cf.CourseName)){
				c.Weeks=c.Weeks+","+cf.Weeks;
				flag=true;
				break;
			}
		}
		if(!flag)
			CourseSet.add(cf);
	}
	public TreeSet<CourseInfo> getCourseSet(){
		return CourseSet;
	}
}
