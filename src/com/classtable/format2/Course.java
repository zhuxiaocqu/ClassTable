package com.classtable.format2;

import java.util.TreeSet;



public class Course {
	TreeSet<CourseInfo> CourseSet;
	Course(){
		CourseSet=new TreeSet<CourseInfo>();
	}
	public void addCourseInfo(CourseInfo cf){
		//先检测是否课程名字相同，预防重复，若相同，将周次合并	
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
