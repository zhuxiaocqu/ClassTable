package com.classtable.format2;
public class CourseInfo implements Comparable<CourseInfo>{
	int Id;
	String CourseName;
	String Weeks;
	String Classroom;
	CourseInfo(int Id,String CourseName,String Weeks,String Classroom){
		this.Id=Id;
		this.CourseName=CourseName;
		this.Weeks=Weeks;
		this.Classroom=Classroom;
	}
	@Override
	public int compareTo(CourseInfo o) {
		// TODO Auto-generated method stub
		CourseInfo s=(CourseInfo)o;
		if(this.Weeks.charAt(0)<s.Weeks.charAt(0))
			return -1; 
		else if(this.Weeks.charAt(0)>s.Weeks.charAt(0))
			return 1;
		else 
			return 1;
	}
}
