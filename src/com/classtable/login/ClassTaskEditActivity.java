package com.classtable.login;

import java.io.IOException;
import java.util.TreeSet;

import com.classtable.format2.GetArrayList;
import com.classtable.format2.ReadCourseInfo;
import com.classtable.format2.WriteCourseInfo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ClassTaskEditActivity extends Activity {

	private Button btn_edit_finish, btn_back;
	private EditText edt_homework, edt_learn;
	private TextView textview_title;
	private String str_homework_old, str_learn_old, str_homework_new,
			str_learn_new;
	private String courseName;
	private int coursePos;
	private TreeSet<TaskObject> treeSet_TaskObject = new TreeSet<TaskObject>();
	private String fileName = "task.bin";
	private TaskObject oldTask = null;
	private boolean isTaskExist = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewpager_task_edit);
		findView_MainAty();
		iniData();
		textview_title.setText(courseName);
		setOnclickListener();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		// super.onBackPressed();
		exitCheckSave();
	}

	public void findView_MainAty() {
		btn_edit_finish = (Button) findViewById(R.id.task_edit_finish);
		btn_back = (Button) findViewById(R.id.task_edit_btn_back);
		edt_homework = (EditText) findViewById(R.id.task_edit_homework);
		edt_learn = (EditText) findViewById(R.id.task_edit_learn);
		textview_title=(TextView)findViewById(R.id.task_txv_title);
	}

	public void iniData() {
		Intent getData = getIntent();
		courseName = getData.getStringExtra("courseName");
		coursePos = getData.getIntExtra("coursePos", 0);
		// System.out.println("编辑接受到的coursePos"+coursePos);
		// System.out.println("编辑接受到的courseName->"+courseName);
		// 得到存在文件中的包含了所有task信息的treeset
		ReadCourseInfo readInfo = new ReadCourseInfo(this);
		treeSet_TaskObject = readInfo.ReadTaskInfo(fileName);
		// System.out.println("tasktreeset->"+treeSet_TaskObject+"   "+treeSet_TaskObject.size());
		// 判断当前课程中是否已经建立了task
		for (TaskObject task : treeSet_TaskObject) {
			if (task.getCourseName().equals(courseName)) {
				oldTask = task;
				isTaskExist = true;
			}
		}
		// 得到task中的homework以及learn的内容
		if (isTaskExist) {
			str_homework_old = oldTask.getStrHomeWork();
			str_learn_old = oldTask.getStrLearn();
		} else {
			str_homework_old = "";
			str_learn_old = "";
		}
		edt_homework.setText(str_homework_old);
		edt_learn.setText(str_learn_old);
	}

	public void getEditCont() {
		str_homework_new = edt_homework.getText().toString();
		str_learn_new = edt_learn.getText().toString();
	}

	public void exitCheckSave() {
		getEditCont();
		if (!str_homework_old.equals(str_homework_new)
				|| !str_learn_old.equals(str_learn_new)) {
			AlertDialog.Builder tip = new AlertDialog.Builder(
					ClassTaskEditActivity.this);
			tip.setTitle("您还没保存确定要退出");
			tip.setPositiveButton("确定", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Intent backIntent = new Intent(ClassTaskEditActivity.this,
							ClassInfoViewActivity.class);
					backIntent.putExtra("isShowCourseInfo", false);
					backIntent.putExtra("pos", coursePos);
					startActivity(backIntent);
					finish();
				}

			});
			tip.setNegativeButton("取消", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
				}
			});
			tip.create().show();
		} else {
			Intent backIntent = new Intent(ClassTaskEditActivity.this,
					ClassInfoViewActivity.class);
			backIntent.putExtra("isShowCourseInfo", false);
			backIntent.putExtra("pos", coursePos);
			startActivity(backIntent);
			finish();
		}
	}

	public void setOnclickListener() {
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				exitCheckSave();
			}
		});

		btn_edit_finish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getEditCont();
				// System.out.println("taskEdit-> btnfinish");
				// System.out.println("taskEdit home->"+str_homework);
				// System.out.println("taskEdit strlean->"+str_learn);

				TaskObject newTask = new TaskObject(courseName,
						str_homework_new, str_learn_new);

				// 当前课程任务已经存在于treeSet中
				if (isTaskExist) {
					if (!str_homework_old.equals(str_homework_new)
							|| !str_learn_old.equals(str_learn_new)) {
						// System.out.println("新建任务已经在treeset中");
						WriteCourseInfo writeInfo = new WriteCourseInfo(
								ClassTaskEditActivity.this);
						treeSet_TaskObject.remove(oldTask);
						treeSet_TaskObject.add(newTask);
						writeInfo.WriteTaskInfo(fileName, treeSet_TaskObject);
					}
				} else {// 当前课程任务没有存在于treeSet中
					// System.out.println("新建任务不存在与treeset中");
					if (!str_homework_old.equals(str_homework_new)
							|| !str_learn_old.equals(str_learn_new)) {
						WriteCourseInfo writeInfo = new WriteCourseInfo(
								ClassTaskEditActivity.this);
						treeSet_TaskObject.add(newTask);
						writeInfo.WriteTaskInfo(fileName, treeSet_TaskObject);
					}
				}
				// System.out.println("编辑完成后treeset的大小"+treeSet_TaskObject.size());
				Intent finishIntent = new Intent(ClassTaskEditActivity.this,
						ClassInfoViewActivity.class);
				finishIntent.putExtra("isShowCourseInfo", false);
				finishIntent.putExtra("pos", coursePos);
				startActivity(finishIntent);
				finish();
			}
		});
	}

}
