package com.classtable.format2;

import java.io.Serializable;

public class CourseObject implements Serializable,Comparable<CourseObject>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//��ʮ��������
	private int id;                //������ʾ˳���id
	private String name;          //�γ�����
	private String credit;        //ѧ��
	private String classHour;     //��ѧʱ
	private String teachHour; //����ѧʱ
	private String experimentHour; //�ϼ�ѧʱ
	private String courseKind;   //�γ����
	private String teachKind;  //�ڿη�ʽ
	private String examKind; //���˷�ʽ
	private String teacher;  //�ον�ʦ
	private String weeks;    //�Ͽ��ܴ�
	private String classes;  //�Ͽνڴ�
	private String classroom;  //�ص�
	
	//���캯�����������в���
	CourseObject(int id,String name,String weeks,String classes,String classroom){
		this.id=id;
		this.name=name;
		this.weeks=weeks;
		this.classes=classes;
		this.classroom=classroom;	
	}
	CourseObject(int id,String name,String weeks,String classes,String classroom,String credit,String teacher,
			String classHour,String teachHour,String experimentHour,String courseKind,String teachKind,String examKind){
		this.id=id;
		this.name=name;
		this.credit=credit;
		this.classHour=classHour;
		this.teachHour=teachHour;
		this.experimentHour=experimentHour;
		this.courseKind=courseKind;
		this.teachKind=teachKind;
		this.examKind=examKind;
		this.teacher=teacher;
		this.weeks=weeks;
		this.classes=classes;
		this.classroom=classroom;	
	}
	//�������в�������������
	public void setId(int id){
		this.id=id;
	}
	public void setName(String name){
		this.name=name;
	}
	public void setCredit(String credit){
		this.credit=credit;
	}
	public void setClassHour(String classHour){
		this.classHour=classHour;
	}
	public void setTeachHour(String teachHour){
		this.teachHour=teachHour;
	}
	public void setExperimentHour(String experimentHour){
		this.experimentHour=experimentHour;
	}
	public void setCourseKind(String courseKind){
		this.courseKind=courseKind;
	}
	public void setTeachKind(String teachKind){
		this.teachKind=teachKind;
	}
	public void setExamKind(String examKind){
		this.examKind=examKind;
	}
	public void setTeacher(String teacher){
		this.teacher=teacher;
	}
	public void setWeeks(String weeks){
		this.weeks=weeks;
	}
	public void setClasses(String classes){
		this.classes=classes;
	}
	public void setClassroom(String classroom){
		this.classroom=classroom;
	}
	//�������в���
	public int getId(){
		return this.id;
	}
	public String getName(){
		return this.name;
	}
	public String getCredit(){
		return this.credit;
	}
	public String getClassHour(){
		return this.classHour;
	}
	public String getTeachHour(){
		return this.teachHour;
	}
	public String getExperimentHour(){
		return this.experimentHour;
	}
	public String getCourseKind(){
		return this.courseKind;
	}
	public String getTeachKind(){
		return this.teachKind;
	}
	public String getExamKind(){
		return this.examKind;
	}
	public String getTeacher(){
		return this.teacher;
	}
	public String getWeeks(){
		return this.weeks;
	}
	public String getClasses(){
		return this.classes;
	}
	public String getClassroom(){
		return this.classroom;
	}
	@Override
	public int compareTo(CourseObject o) {
		// TODO Auto-generated method stub
		if(this.id<o.id)
			return -1;
		else if(this.id>o.id)
			return 1;
		else
			return 0;
	}
}


//<TextView
//android:id="@+id/textView_Credit"
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
//android:text="TextView" />
//<TextView
//android:id="@+id/textView_Teacher"
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
//android:text="TextView" />
//<TextView
//android:id="@+id/textView_ClassHour"
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
//android:text="TextView" />
//<TextView
//android:id="@+id/textView_TeachHour"
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
//android:text="TextView" />
//<TextView
//android:id="@+id/textView_ExperimentHour"
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
//android:text="TextView" />
//<TextView
//android:id="@+id/textView��_CourseKind"
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
//android:text="TextView" />
//<TextView
//android:id="@+id/textView_TeachKind"
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
//android:text="TextView" />
//<TextView
//android:id="@+id/textView_ExamKind"
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
//android:text="TextView" />
