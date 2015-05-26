package com.classtable.format2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.TreeSet;

import com.classtable.login.ClassTableActivity;
import com.classtable.login.R;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ShowTimeTableActivity extends Activity {
	private String filePath = "/data/data/com.classtable.login/files/young.bin";
	private String fileName = "young.bin";
	private TreeSet<CourseObject> CourseObjectTreeSet = new TreeSet<CourseObject>();
	// 定义扩展按钮的状态，当为true时显示next,false显示previous
	private boolean expandTable = true;
	ImageView ExpandImageView;
	LinearLayout mainLinearLayout;
	ShowOneCourseRow subView;
	
	boolean idUpdating=false;//是否是刷新后显示
	int[] IdArr=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_time_table);

		Intent getIdArr=getIntent();
		IdArr=getIdArr.getIntArrayExtra("idArray");
		if(IdArr!=null)
			idUpdating=true;
		
		ReadCourseInfo readInfo = new ReadCourseInfo(this);
		if ((CourseObjectTreeSet = readInfo.ReadInfo(fileName)).isEmpty()) {
			System.out.println("format02 empty");
			try {
				CourseObjectTreeSet = GetArrayList.getArrayList();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			WriteCourseInfo writeInfo = new WriteCourseInfo(this);
			writeInfo.WriteInfo(fileName, CourseObjectTreeSet);
			ShowTable(CourseObjectTreeSet, false);
		}else{
			System.out.println("format02 not empty");
			CourseObjectTreeSet = readInfo.ReadInfo(fileName);
	
			ShowTable(CourseObjectTreeSet, false);
		}

		// 为扩展按钮绑定事件
		ExpandImageView = (ImageView) findViewById(R.id.imageView_Expand);
		ExpandImageView.setImageResource(R.drawable.next_blue);
		ExpandImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub

				mainLinearLayout.removeAllViews();
				if (expandTable) {
					ShowTable(CourseObjectTreeSet, true);
					ExpandImageView.setImageResource(R.drawable.blue_left);
				} else {
					ShowTable(CourseObjectTreeSet, false);
					ExpandImageView.setImageResource(R.drawable.next_blue);
				}
				updateExpandStatus();

			}
		});
		// File file=new File(filePath);
		// file.delete();

	}

	public void updateExpandStatus() {
		expandTable = !expandTable;
	}

	public void ShowTable(TreeSet<CourseObject> CourseObjectTreeSet,
			boolean showAll) {
		// ------------------------------布局
		// 定义显示布局的类

		// 首先加载一行Title
		CourseObject c = null;
		mainLinearLayout = (LinearLayout) findViewById(R.id.showTableMainBox);
		// 实例化一个子View
		subView = new ShowOneCourseRow(this);
		subView.ShowOneRow(c, false, showAll,idUpdating,IdArr);
		// 添加到容器
		mainLinearLayout.addView(subView);

		for (CourseObject co : CourseObjectTreeSet) {
			mainLinearLayout = (LinearLayout) findViewById(R.id.showTableMainBox);
			// 实例化一个子View
			subView = new ShowOneCourseRow(this);
			subView.ShowOneRow(co, true, showAll,idUpdating,IdArr);
			// 添加到容器
			mainLinearLayout.addView(subView);
		}
		// ------------------------------布局
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add(0, 0, 0, "今日课表");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case 0:
			Intent jumpToFormat1 = new Intent(ShowTimeTableActivity.this,
					ClassTableActivity.class);
			startActivity(jumpToFormat1);
			ShowTimeTableActivity.this.finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}

// <LinearLayout
// android:layout_width="fill_parent"
// android:layout_height="50dp"
// android:orientation="horizontal" >
//
// <TextView
// android:id="@+id/textView_Title_Id"
// android:layout_width="20dp"
// android:layout_height="wrap_content"
// android:gravity="center"
// android:text="序号" />
//
// <TextView
// android:id="@+id/textView_Title_Name"
// android:layout_width="100dp"
// android:layout_height="wrap_content"
// android:gravity="center"
// android:text="课程" />
//
// <TextView
// android:id="@+id/textView_Title_Weeks"
// android:layout_width="50dp"
// android:layout_height="wrap_content"
// android:gravity="center"
// android:text="周次" />
//
// <TextView
// android:id="@+id/textView_Title_Classes"
// android:layout_width="80dp"
// android:layout_height="wrap_content"
// android:gravity="center"
// android:text="节次" />
//
// <TextView
// android:id="@+id/textView_Title_Classroom"
// android:layout_width="40dp"
// android:layout_height="wrap_content"
// android:gravity="center"
// android:text="教室" />
//
// <ImageView
// android:id="@+id/imageView_Title_More"
// android:layout_width="40dp"
// android:layout_height="wrap_content"
// android:src="@drawable/expand" />

//
// </LinearLayout>