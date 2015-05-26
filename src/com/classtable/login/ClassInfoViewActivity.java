package com.classtable.login;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.classtable.*;
import com.classtable.format2.CourseObject;
import com.classtable.format2.GetArrayList;
import com.classtable.format2.ReadCourseInfo;
import com.classtable.format2.WriteCourseInfo;

public class ClassInfoViewActivity extends Activity {
	private Button button_back, button_edit;
	private TextView textview_classname, textview_teacher, textview_classroom,
			textview_kind, textview_score, textview_teachtime,
			textview_exptime, textview_exam, textview_classtime,
			textview_classweek, textview_title, textview_homework,
			textview_learn;
	private int coursePos = 0;
	private String fileName = "young.bin";
	private TreeSet<CourseObject> CourseObjectTreeSet = new TreeSet<CourseObject>();
	private CourseObject courseObject;
	private ViewPager mPager;// ҳ������
	private List<View> listViews; // Tabҳ���б�
	private RelativeLayout rlty_classinfo, rlty_task;// ҳ��ͷ��
	private int offset = 0;// ����ͼƬƫ����
	private boolean isShowCourseInfo = true;
	private String courseName;
	private TreeSet<TaskObject> treeSet_TaskObject = new TreeSet<TaskObject>();
	private String fileNameTask = "task.bin";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_classinfoview);
		System.out.println("in viewaty oncreate");
		findView();
		iniData();
		setButtonOnclickListener();
		InitViewPager();
		fillCourseInfo();
		fillTaskContent();
	}

	public void iniData() {
		offset = calScreenWidth() / 2;
//		System.out.println("offset in create->" + offset);
		Intent getData = getIntent();
		coursePos = getData.getIntExtra("pos", 0);
		isShowCourseInfo = getData.getBooleanExtra("isShowCourseInfo", true);
//		System.out.println("coursePos->" + coursePos);
		ReadCourseInfo readInfo = new ReadCourseInfo(this);
		if ((CourseObjectTreeSet = readInfo.ReadInfo(fileName)).isEmpty()) {
//			System.out.println("format02 empty");
			try {
				CourseObjectTreeSet = GetArrayList.getArrayList();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			WriteCourseInfo writeInfo = new WriteCourseInfo(this);
			writeInfo.WriteInfo(fileName, CourseObjectTreeSet);
		} else {
//			System.out.println("format02 not empty");
			CourseObjectTreeSet = readInfo.ReadInfo(fileName);
		}
		int flag = 0;
		for (CourseObject cursObj : CourseObjectTreeSet) {
			if (flag == coursePos) {
				courseObject = cursObj;
				break;
			}
			flag++;
		}
		// System.out.println(courseObject.getClasses());
		courseName = courseObject.getName();
//		System.out.println("getcoursenamefromcourObj->"+courseName);
		// �õ��洢task��treeset
		ReadCourseInfo readInfoTask = new ReadCourseInfo(this);
		treeSet_TaskObject = readInfoTask.ReadTaskInfo(fileNameTask);
	}

	public int calScreenWidth() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;// ��ȡ�ֱ��ʿ��
		return screenW;
	}

	public void findView() {
		button_back = (Button) findViewById(R.id.detail_course_btn_back);
		button_edit = (Button) findViewById(R.id.detail_course_btn_edit);
		rlty_classinfo = (RelativeLayout) findViewById(R.id.detail_course_rlyt_table_course_info);
		rlty_task = (RelativeLayout) findViewById(R.id.detail_course_rlyt_table_task);
		mPager = (ViewPager) findViewById(R.id.detail_course_vpgr_view);
		textview_title = (TextView) findViewById(R.id.detail_course_txv_title);
	}

	public void findView_ViewPager_course(View view) {
		textview_classname = (TextView) view
				.findViewById(R.id.view_course_courseName);
		textview_classroom = (TextView) view
				.findViewById(R.id.view_course_classroom);
		textview_teacher = (TextView) view
				.findViewById(R.id.view_course_teacher);
		textview_kind = (TextView) view.findViewById(R.id.view_course_kind);
		textview_score = (TextView) view.findViewById(R.id.view_course_score);
		textview_teachtime = (TextView) view
				.findViewById(R.id.view_course_teachtime);
		textview_exptime = (TextView) view
				.findViewById(R.id.view_course_exptime);
		textview_exam = (TextView) view.findViewById(R.id.view_course_exam);
		textview_classtime = (TextView) view
				.findViewById(R.id.view_course_classTime);
		textview_classweek = (TextView) view
				.findViewById(R.id.view_course_week);
		textview_classname.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(ClassInfoViewActivity.this, "work", 3000).show();
			}
		});
	}

	public void findView_ViewPager_task(View view) {
		textview_homework = (TextView) view
				.findViewById(R.id.task_view_homework);
		textview_learn = (TextView) view.findViewById(R.id.task_view_learn);
	}

	public void fillCourseInfo() {
		textview_classname.setText(courseName);
		textview_teacher.setText(courseObject.getTeacher());
		textview_classroom.setText(courseObject.getClassroom());
		textview_kind.setText(courseObject.getCourseKind());
		textview_score.setText(courseObject.getCredit() + "��");
		textview_teachtime.setText(courseObject.getTeachHour() + "Сʱ");
		textview_exptime.setText(courseObject.getExperimentHour() + "Сʱ");
		textview_exam.setText(courseObject.getExamKind());
		textview_classtime.setText(courseObject.getClasses());
		textview_classweek.setText(courseObject.getWeeks());
		// ���ñ�����
		textview_title.setText(courseName);
	}

	public void fillTaskContent() {
//		System.out.println("viewaty ��treeSet_TaskObject�ǿյ���->"+treeSet_TaskObject.isEmpty());
		if (!treeSet_TaskObject.isEmpty()) {
//			System.out.println("viewaty��treeSet_TaskObject�Ĵ�С->"+treeSet_TaskObject.size());
//			System.out.println("��ǰ�γ�name->"+courseName);
			for (TaskObject task : treeSet_TaskObject) {
//				System.out.println("treeset���Ѵ��ڿγ�name->"+task.getCourseName());
				if (task.getCourseName().equals(courseName)) {
//					System.out.println(task.getStrHomeWork());
//					System.out.println(task.getStrLearn());
					textview_homework.setText(task.getStrHomeWork());
					textview_learn.setText(task.getStrLearn());
				}
			}
		}
	}

	public void setButtonOnclickListener() {
		button_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ClassInfoViewActivity.this, ClassTableActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
			}
		});
		button_edit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				jumpActivityWithData(ClassInfoViewActivity.this,
						ClassTaskEditActivity.class, courseName,coursePos);
				finish();
			}
		});
		rlty_classinfo.setOnClickListener(new MyOnClickListener(0));
		rlty_task.setOnClickListener(new MyOnClickListener(1));
	}

	private void InitViewPager() {
		listViews = new ArrayList<View>();
		LayoutInflater mInflater = getLayoutInflater();
		View view_curseInfo = mInflater.inflate(R.layout.viewpager_classinfo,
				null);
		View view_task = mInflater.inflate(R.layout.viewpager_task_view, null);
		listViews.add(view_curseInfo);
		listViews.add(view_task);
		mPager.setAdapter(new MyPageAdapter(listViews));
		if (isShowCourseInfo) {
			mPager.setCurrentItem(0);
			rlty_classinfo.setSelected(true);
			rlty_task.setSelected(false);
			button_edit.setVisibility(View.INVISIBLE);
		} else {
			mPager.setCurrentItem(1);
			rlty_classinfo.setSelected(false);
			rlty_task.setSelected(true);
			button_edit.setVisibility(View.VISIBLE);
		}
		mPager.setOnPageChangeListener(new MyOnPageChangeListener());
		findView_ViewPager_course(view_curseInfo);
		findView_ViewPager_task(view_task);
	}

	private void InitImageView() {
		// cursor = (ImageView) findViewById(R.id.cursor);
		// bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.a)
		// .getWidth();// ��ȡͼƬ���
		// DisplayMetrics dm = new DisplayMetrics();
		// getWindowManager().getDefaultDisplay().getMetrics(dm);
		// int screenW = dm.widthPixels;// ��ȡ�ֱ��ʿ��
		// offset = (screenW / 3 - bmpW) / 2;// ����ƫ����
		// Matrix matrix = new Matrix();
		// matrix.postTranslate(offset, 0);
		// cursor.setImageMatrix(matrix);// ���ö�����ʼλ��
	}


	public void jumpActivityWithData(Context context, Class cla,
			String courseName,int coursePos) {
		Intent intent = new Intent(context, cla);
		intent.putExtra("courseName", courseName);
		intent.putExtra("coursePos", coursePos);
//		System.out.println("�����༭��coursePos"+coursePos);
		startActivity(intent);
	}

	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			mPager.setCurrentItem(index);
		}
	};

	public class MyOnPageChangeListener implements OnPageChangeListener {
		// int one = offset * 2 + bmpW;// ҳ��1 -> ҳ��2 ƫ����
		// int two = one * 2;// ҳ��1 -> ҳ��3 ƫ����

		@Override
		public void onPageSelected(int arg0) {
			Animation animation = null;
			System.out.println("arg0->��" + arg0);
//			System.out.println("offset in listen->" + offset);
			switch (arg0) {
			case 0:
				
				rlty_classinfo.setSelected(true);
				rlty_task.setSelected(false);
				button_edit.setVisibility(View.INVISIBLE);
				// fillCourseInfo();
				break;
			case 1:
				rlty_classinfo.setSelected(false);
				rlty_task.setSelected(true);
				button_edit.setVisibility(View.VISIBLE);
//				 fillTaskContent();
				break;
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}

}
