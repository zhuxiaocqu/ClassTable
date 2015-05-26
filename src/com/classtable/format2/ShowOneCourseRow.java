package com.classtable.format2;

import android.R.color;
import java.awt.*;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.classtable.login.R;

public class ShowOneCourseRow extends LinearLayout{

	private Context context;
	private CourseObject co;

	//定义13个属性TextView控件
	private TextView textView_Id;
	private TextView textView_Name;
	private TextView textView_Weeks;
	private TextView textView_Classes;
	private TextView textView_Classroom;
	//以下属性点击扩展键后启动
	private TextView textView_Credit;
	private TextView textView_Teacher;
	private TextView textView_ClassHour;
	private TextView textView_TeachHour;
	private TextView textView_ExperimentHour;
	private TextView textView_CourseKind;
	private TextView textView_TeachKind;
	private TextView textView_ExamKind;

	private LinearLayout tableRow;

	public ShowOneCourseRow(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context=context;
	}
	public void ShowOneRow(final CourseObject co,boolean kind,boolean showAll,boolean isUpdating,int[] idArr){
		//kind 为真时显示课表信息，为假时显示Title
		((Activity) getContext()).getLayoutInflater().inflate(R.layout.table_row, this);

//		textView_Id.setBackgroundColor(Color.YELLOW);
//		textView_Id.setBackgroundColor(Color.YELLOW);
//		textView_Id.setBackgroundColor(Color.YELLOW);
//		textView_Id.setBackgroundColor(Color.YELLOW);
//		textView_Id.setBackgroundColor(Color.YELLOW);
//		textView_Id.setBackgroundColor(Color.YELLOW);
//		textView_Id.setBackgroundColor(Color.YELLOW);
//		textView_Id.setBackgroundColor(Color.YELLOW);
//		textView_Id.setBackgroundColor(Color.YELLOW);
//		textView_Id.setBackgroundColor(Color.YELLOW);
//		textView_Id.setBackgroundColor(Color.YELLOW);
		boolean showYellow=false;
		if(kind){
			if(isUpdating){			
				if(idArr[0]!=0){
					for(int i=0;i<100;i++){
						if(co.getId()==idArr[i]){
							showYellow=true;
							break;
						}
					}
				}
				
			}
			textView_Id=(TextView)findViewById(R.id.textView_Id);
			textView_Id.setText(String.valueOf(co.getId()));
			
			textView_Name=(TextView)findViewById(R.id.textView_Name);
			textView_Name.setText(co.getName());

			textView_Weeks=(TextView)findViewById(R.id.textView_Weeks);
			textView_Weeks.setText(co.getWeeks());

			textView_Classes=(TextView)findViewById(R.id.textView_Classes);
			textView_Classes.setText(co.getClasses());

			textView_Classroom=(TextView)findViewById(R.id.textView_Classroom);
			textView_Classroom.setText(co.getClassroom());
			//#00ff90
			if(co.getId()%2==1){
				textView_Id.setBackgroundColor(Color.argb(47, 0, 255, 144));
				textView_Name.setBackgroundColor(Color.argb(47, 0, 255, 144));
				textView_Weeks.setBackgroundColor(Color.argb(47, 0, 255, 144));
				textView_Classes.setBackgroundColor(Color.argb(47, 0, 255, 144));
				textView_Classroom.setBackgroundColor(Color.argb(47, 0, 255, 144));
			}//#2fd0ff90
			else{
				textView_Id.setBackgroundColor(Color.argb(47, 208, 255, 144));
				textView_Name.setBackgroundColor(Color.argb(47, 208, 255, 144));
				textView_Weeks.setBackgroundColor(Color.argb(47, 208, 255, 144));
				textView_Classes.setBackgroundColor(Color.argb(47, 208, 255, 144));
				textView_Classroom.setBackgroundColor(Color.argb(47, 208, 255, 144));
			}
			
			if(showYellow){
//				tableRow=(LinearLayout)findViewById(R.layout.table_row);
//				tableRow.setBackgroundColor(Color.YELLOW);
				textView_Id.setBackgroundColor((Color.argb(255,67,110,238)));
			}
			if(showAll){
				textView_Credit=(TextView)findViewById(R.id.textView_Credit);
				textView_Credit.setVisibility(View.VISIBLE);
				textView_Credit.setText(co.getCredit());

				textView_Teacher=(TextView)findViewById(R.id.textView_Teacher);
				textView_Teacher.setVisibility(View.VISIBLE);
				textView_Teacher.setText(co.getTeacher());

				textView_ClassHour=(TextView)findViewById(R.id.textView_ClassHour);
				textView_ClassHour.setVisibility(View.VISIBLE);
				textView_ClassHour.setText(co.getClassHour());

				textView_TeachHour=(TextView)findViewById(R.id.textView_TeachHour);
				textView_TeachHour.setVisibility(View.VISIBLE);
				textView_TeachHour.setText(co.getTeachHour());

				textView_ExperimentHour=(TextView)findViewById(R.id.textView_ExperimentHour);
				textView_ExperimentHour.setVisibility(View.VISIBLE);
				textView_ExperimentHour.setText(co.getExperimentHour());
				
				textView_CourseKind=(TextView)findViewById(R.id.textView_CourseKind);
				textView_CourseKind.setVisibility(View.VISIBLE);
				textView_CourseKind.setText(co.getCourseKind());

				textView_TeachKind=(TextView)findViewById(R.id.textView_TeachKind);
				textView_TeachKind.setVisibility(View.VISIBLE);
				textView_TeachKind.setText(co.getTeachKind());

				textView_ExamKind=(TextView)findViewById(R.id.textView_ExamKind);
				textView_ExamKind.setVisibility(View.VISIBLE);
				textView_ExamKind.setText(co.getExamKind());
				
				if(co.getId()%2==1){
					textView_Credit.setBackgroundColor(Color.argb(47, 0, 255, 144));
					textView_Teacher.setBackgroundColor(Color.argb(47, 0, 255, 144));
					textView_ClassHour.setBackgroundColor(Color.argb(47, 0, 255, 144));
					textView_TeachHour.setBackgroundColor(Color.argb(47, 0, 255, 144));
					textView_ExperimentHour.setBackgroundColor(Color.argb(47, 0, 255, 144));
					textView_CourseKind.setBackgroundColor(Color.argb(47, 0, 255, 144));
					textView_TeachKind.setBackgroundColor(Color.argb(47, 0, 255, 144));
					textView_ExamKind.setBackgroundColor(Color.argb(47, 0, 255, 144));
				}
				else{
					textView_Credit.setBackgroundColor(Color.argb(47, 208, 255, 144));
					textView_Teacher.setBackgroundColor(Color.argb(47, 208, 255, 144));
					textView_ClassHour.setBackgroundColor(Color.argb(47, 208, 255, 144));
					textView_TeachHour.setBackgroundColor(Color.argb(47, 208, 255, 144));
					textView_ExperimentHour.setBackgroundColor(Color.argb(47, 208, 255, 144));
					textView_CourseKind.setBackgroundColor(Color.argb(47, 208, 255, 144));
					textView_TeachKind.setBackgroundColor(Color.argb(47, 208, 255, 144));
					textView_ExamKind.setBackgroundColor(Color.argb(47, 208, 255, 144));
				}
			}
		}
		else{
			textView_Id=(TextView)findViewById(R.id.textView_Id);
			textView_Id.setText("序号");

			textView_Name=(TextView)findViewById(R.id.textView_Name);
			textView_Name.setText("课程");

			textView_Weeks=(TextView)findViewById(R.id.textView_Weeks);
			textView_Weeks.setText("周次");

			textView_Classes=(TextView)findViewById(R.id.textView_Classes);
			textView_Classes.setText("节次");

			textView_Classroom=(TextView)findViewById(R.id.textView_Classroom);
			textView_Classroom.setText("教室");
			if(showAll){
				textView_Credit=(TextView)findViewById(R.id.textView_Credit);
				textView_Credit.setVisibility(View.VISIBLE);
				textView_Credit.setText("学分");

				textView_Teacher=(TextView)findViewById(R.id.textView_Teacher);
				textView_Teacher.setVisibility(View.VISIBLE);
				textView_Teacher.setText("授课教师");

				textView_ClassHour=(TextView)findViewById(R.id.textView_ClassHour);
				textView_ClassHour.setVisibility(View.VISIBLE);
				textView_ClassHour.setText("总学时");

				textView_TeachHour=(TextView)findViewById(R.id.textView_TeachHour);
				textView_TeachHour.setVisibility(View.VISIBLE);
				textView_TeachHour.setText("讲授学时");

				textView_ExperimentHour=(TextView)findViewById(R.id.textView_ExperimentHour);
				textView_ExperimentHour.setVisibility(View.VISIBLE);
				textView_ExperimentHour.setText("上机学时");

				textView_CourseKind=(TextView)findViewById(R.id.textView_CourseKind);
				textView_CourseKind.setVisibility(View.VISIBLE);
				textView_CourseKind.setText("类别");

				textView_TeachKind=(TextView)findViewById(R.id.textView_TeachKind);
				textView_TeachKind.setVisibility(View.VISIBLE);
				textView_TeachKind.setText("授课方式");

				textView_ExamKind=(TextView)findViewById(R.id.textView_ExamKind);
				textView_ExamKind.setVisibility(View.VISIBLE);
				textView_ExamKind.setText("考核方式");
			}
		}
	}

	//	public void ExpendTitleTextView(){
	//		 TextView textView_Credit=(TextView)findViewById(R.id.textView_Credit);
	//		 textView_Credit.setVisibility(View.VISIBLE);
	//		 textView_Credit.setText("学分");
	//		 
	//		 TextView textView_Teacher=(TextView)findViewById(R.id.textView_Teacher);
	//		 textView_Teacher.setVisibility(View.VISIBLE);
	//		 textView_Teacher.setText("授课教师");
	//		 
	//		 TextView textView_ClassHour=(TextView)findViewById(R.id.textView_ClassHour);
	//		 textView_ClassHour.setVisibility(View.VISIBLE);
	//		 textView_ClassHour.setText("总学时");
	//		 
	//		 TextView textView_TeachHour=(TextView)findViewById(R.id.textView_TeachHour);
	//		 textView_TeachHour.setVisibility(View.VISIBLE);
	//		 textView_TeachHour.setText("讲授学时");
	//		 
	//		 TextView textView_ExperimentHour=(TextView)findViewById(R.id.textView_ExperimentHour);
	//		 textView_ExperimentHour.setVisibility(View.VISIBLE);
	//		 textView_ExperimentHour.setText("上机学时");
	//		 
	//		 TextView textView_CourseKind=(TextView)findViewById(R.id.textView_CourseKind);
	//		 textView_CourseKind.setVisibility(View.VISIBLE);
	//		 textView_CourseKind.setText("类别");
	//		 
	//		 TextView textView_TeachKind=(TextView)findViewById(R.id.textView_TeachKind);
	//		 textView_TeachKind.setVisibility(View.VISIBLE);
	//		 textView_TeachKind.setText("授课方式");
	//		 
	//		 TextView textView_ExamKind=(TextView)findViewById(R.id.textView_ExamKind);
	//		 textView_ExamKind.setVisibility(View.VISIBLE);
	//		 textView_ExamKind.setText("考核方式");
	//	}
	//	
	//	public void ExpendTableInfoTextView(CourseObject co){
	//		 TextView textView_Credit=(TextView)findViewById(R.id.textView_Credit);
	//		 textView_Credit.setVisibility(View.VISIBLE);
	//		 textView_Credit.setText(co.getCredit());
	//		 
	//		 TextView textView_Teacher=(TextView)findViewById(R.id.textView_Teacher);
	//		 textView_Teacher.setVisibility(View.VISIBLE);
	//		 textView_Teacher.setText(co.getTeacher());
	//		 
	//		 TextView textView_ClassHour=(TextView)findViewById(R.id.textView_ClassHour);
	//		 textView_ClassHour.setVisibility(View.VISIBLE);
	//		 textView_ClassHour.setText(co.getClassHour());
	//		 
	//		 TextView textView_TeachHour=(TextView)findViewById(R.id.textView_TeachHour);
	//		 textView_TeachHour.setVisibility(View.VISIBLE);
	//		 textView_TeachHour.setText(co.getTeachHour());
	//		 
	//		 TextView textView_ExperimentHour=(TextView)findViewById(R.id.textView_ExperimentHour);
	//		 textView_ExperimentHour.setVisibility(View.VISIBLE);
	//		 textView_ExperimentHour.setText(co.getExperimentHour());
	//		 
	//		 TextView textView_CourseKind=(TextView)findViewById(R.id.textView_CourseKind);
	//		 textView_CourseKind.setVisibility(View.VISIBLE);
	//		 textView_CourseKind.setText(co.getCourseKind());
	//		 
	//		 TextView textView_TeachKind=(TextView)findViewById(R.id.textView_TeachKind);
	//		 textView_TeachKind.setVisibility(View.VISIBLE);
	//		 textView_TeachKind.setText(co.getTeachKind());
	//		 
	//		 TextView textView_ExamKind=(TextView)findViewById(R.id.textView_ExamKind);
	//		 textView_ExamKind.setVisibility(View.VISIBLE);
	//		 textView_ExamKind.setText(co.getExamKind());
	//	}

}
