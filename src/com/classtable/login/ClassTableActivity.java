package com.classtable.login;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import com.classtable.format2.ShowTimeTableActivity;
import com.classtable.setinfo.Switch_Activity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGestureListener;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ClassTableActivity extends Activity {

	private TextView textweek, beforenoon1cont, beforenoon2cont,
			afternoon1cont, afternoon2cont, night1cont, night2cont, welcome,
			textbeforenoon1, textbeforenoon2, textafternoon1, textafternoon2,
			textnight1, textnight2, textMon, textThue, textWend, textThir,
			textFri, textSat, textSun;
	private ImageButton imageButton_update, imageButton_menu;
	private PopupWindow popupWindow;
	private ParseHtml parseHtml;
	private ArrayList<String> dayClass, classweek, className, classroom,
			teacher, courseKind;
	int Mon = 0, Thue = 0, Wends = 0, Thir = 0, Fri = 0, Sat = 0, Sun = 0;
	private int dayOfWeek;
	private int curWeek;
	private String term;
	private boolean isShowWelcome = true;
	private boolean is_MenuVis = false;
	private int[] classContentFlag;
	private Intent stop = null;
	private long exitTime = 0;

	private ViewPager mPager;// ҳ������
	private List<View> listViews; // Tabҳ���б�
	private int preStateOfViewPager = 1;
	LayoutInflater mInflater;
	View view_classtable_00;
	View view_classtable_01;
	View view_classtable_02;
	View view_classtable_03;
	View view_classtable_04;
	View view_classtable_05;
	View view_classtable_06;
	View view_classtable_07;
	View view_classtable_08;

	// private int offset = 0;// ����ͼƬƫ����
	// private int bmpW;// ����ͼƬ���
	private int weekTitleColor = Color.argb(255, 0, 255, 0);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		stop = this.getIntent();
		// �ж��Ƿ������������
//		if (this != null
//				&& ((Intent.FLAG_ACTIVITY_CLEAR_TOP & stop.getFlags()) != 0)) {
//			this.finish();
//		} 
//		else {
			setContentView(R.layout.classtable_activity);
			mInflater = getLayoutInflater();
			getSubViewOfViewPager();
			findMainActyView();
			try {
				// ��ʼ������Ҫ������
				iniData();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			InitViewPager();

			// ����ˢ�£��˵���ť�¼�����
			setImageButtonOnclickEvent();
		}

//	}

	// private void InitImageView() {
	// bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.tag)
	// .getWidth();// ��ȡͼƬ���
	// DisplayMetrics dm = new DisplayMetrics();
	// getWindowManager().getDefaultDisplay().getMetrics(dm);
	// int screenW = dm.widthPixels;// ��ȡ�ֱ��ʿ��
	// offset = (screenW / 3 - bmpW) / 2;// ����ƫ����
	// Matrix matrix = new Matrix();
	// matrix.postTranslate(offset, 0);
	// cursor.setImageMatrix(matrix);// ���ö�����ʼλ��
	// }

	// ------------------------------
	public void getSubViewOfViewPager() {
		view_classtable_00 = mInflater.inflate(
				R.layout.viewpager_classtable_content, null);
		view_classtable_01 = mInflater.inflate(
				R.layout.viewpager_classtable_content, null);
		view_classtable_02 = mInflater.inflate(
				R.layout.viewpager_classtable_content, null);
		view_classtable_03 = mInflater.inflate(
				R.layout.viewpager_classtable_content, null);
		view_classtable_04 = mInflater.inflate(
				R.layout.viewpager_classtable_content, null);
		view_classtable_05 = mInflater.inflate(
				R.layout.viewpager_classtable_content, null);
		view_classtable_06 = mInflater.inflate(
				R.layout.viewpager_classtable_content, null);
		view_classtable_07 = mInflater.inflate(
				R.layout.viewpager_classtable_content, null);
		view_classtable_08 = mInflater.inflate(
				R.layout.viewpager_classtable_content, null);
	}

	private void InitViewPager() {
		mPager = (ViewPager) findViewById(R.id.classtable_viewpager);
		listViews = new ArrayList<View>();

		listViews.add(view_classtable_00);
		listViews.add(view_classtable_01);
		listViews.add(view_classtable_02);
		listViews.add(view_classtable_03);
		listViews.add(view_classtable_04);
		listViews.add(view_classtable_05);
		listViews.add(view_classtable_06);
		listViews.add(view_classtable_07);
		listViews.add(view_classtable_08);

		mPager.setAdapter(new MyPageAdapter(listViews));
		fillClassTable(dayOfWeek, curWeek);
		mPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	public void iniViewPagerEle(int dayOfWeek) {
		clearTitltWeeksBkg();
		switch (dayOfWeek) {
		case 0:
			textSun.setBackgroundColor(Color.argb(255, 0, 255, 0));
			findView_ViewPager(view_classtable_07);
			mPager.setCurrentItem(7);
			preStateOfViewPager = 7;
			break;
		case 1:
			textMon.setBackgroundColor(Color.argb(255, 0, 255, 0));
			findView_ViewPager(view_classtable_01);
			mPager.setCurrentItem(1);
			preStateOfViewPager = 1;
			break;
		case 2:
			textThue.setBackgroundColor(Color.argb(255, 0, 255, 0));
			findView_ViewPager(view_classtable_02);
			mPager.setCurrentItem(2);
			preStateOfViewPager = 2;
			break;
		case 3:
			textWend.setBackgroundColor(Color.argb(255, 0, 255, 0));
			findView_ViewPager(view_classtable_03);
			mPager.setCurrentItem(3);
			preStateOfViewPager = 3;
			break;
		case 4:
			textThir.setBackgroundColor(Color.argb(255, 0, 255, 0));
			findView_ViewPager(view_classtable_04);
			mPager.setCurrentItem(4);
			preStateOfViewPager = 4;
			break;
		case 5:
			textFri.setBackgroundColor(Color.argb(255, 0, 255, 0));
			findView_ViewPager(view_classtable_05);
			mPager.setCurrentItem(5);
			preStateOfViewPager = 5;
			break;
		case 6:
			textSat.setBackgroundColor(Color.argb(255, 0, 255, 0));
			findView_ViewPager(view_classtable_06);
			mPager.setCurrentItem(6);
			preStateOfViewPager = 6;
			break;
		}
		cleanTextContent();
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

	public void clearTitltWeeksBkg() {
		textMon.setBackgroundColor(Color.argb(47, 0, 255, 0));
		textThue.setBackgroundColor(Color.argb(47, 0, 255, 0));
		textWend.setBackgroundColor(Color.argb(47, 0, 255, 0));
		textThir.setBackgroundColor(Color.argb(47, 0, 255, 0));
		textFri.setBackgroundColor(Color.argb(47, 0, 255, 0));
		textSat.setBackgroundColor(Color.argb(47, 0, 255, 0));
		textSun.setBackgroundColor(Color.argb(47, 0, 255, 0));
	}

	public class MyOnPageChangeListener implements OnPageChangeListener {
		// int one = offset * 2 + bmpW;// ҳ��1 -> ҳ��2 ƫ����
		// int two = one * 2;// ҳ��1 -> ҳ��3 ƫ����
		// Animation animation = null;
		@Override
		public void onPageSelected(int arg0) {
			// findView_ViewPager(view_classtable_08);
			switch (arg0) {
			case 0:
				mPager.setCurrentItem(7);
				break;
			case 1:
				dayOfWeek = 1;
				if (preStateOfViewPager == 7)
					curWeek = Math.min(20, (curWeek + 1));
				fillClassTable(dayOfWeek, curWeek);
				break;
			case 2:
				dayOfWeek = 2;
				fillClassTable(dayOfWeek, curWeek);
				break;
			case 3:
				dayOfWeek = 3;
				fillClassTable(dayOfWeek, curWeek);
				break;
			case 4:
				dayOfWeek = 4;
				fillClassTable(dayOfWeek, curWeek);
				break;
			case 5:
				dayOfWeek = 5;
				fillClassTable(dayOfWeek, curWeek);
				break;
			case 6:
				dayOfWeek = 6;
				fillClassTable(dayOfWeek, curWeek);
				break;
			case 7:
				dayOfWeek = 0;
				if (preStateOfViewPager == 1)
					curWeek = Math.max(1, (curWeek - 1));
				fillClassTable(dayOfWeek, curWeek);
				break;
			case 8:
				mPager.setCurrentItem(1);
				break;
				default:
					Toast.makeText(ClassTableActivity.this,"��������",  3000);
					break;
			}
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}
	}

	// -------------------------------

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {

			if ((System.currentTimeMillis() - exitTime) > 2000) // System.currentTimeMillis()���ۺ�ʱ���ã��϶�����2000
			{
				Toast.makeText(getApplicationContext(), "�ٰ�һ���˳�����",
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				finish();
				System.exit(0);
			}

			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void iniData() throws IOException {
		// �����������һ�����пεĽ�����ArrayList�ж�Ӧ�����
		classContentFlag = new int[6];

		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			String filePath = Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/classtable.html";
			File file = new File(filePath);
			if (file.exists()) {
				parseHtml = new ParseHtml(filePath);
				dayOfWeek = parseHtml.getDayOfWeek();// ��ʾһ���еĵڼ���
				SharedPreferences preferences = getSharedPreferences(
						"userInfo", MODE_PRIVATE);
				String timeOfTerm = preferences.getString("timeOfTerm", "");
				String stuRealName = preferences.getString("stuRealName", "ͬѧ");
				curWeek = parseHtml.getCurWeek(timeOfTerm);// ��ʾ�ڼ���
				term = parseHtml.getTerm();// �͵ڼ���һ�����ǿ��ܱ仯�����Ա���ʱʱ��ȡ
				welcome.setText("��ӭ����" + stuRealName);
				// beforenoonyear.setText(parseHtml.getCurYear());
				// beforenoonyear.setText(term);
				// ��ʼ���α����������������
				dayClass = SplashActivity.allInfo.get("classday");
				classweek = SplashActivity.allInfo.get("classweek");
				className = SplashActivity.allInfo.get("className");
				classroom = SplashActivity.allInfo.get("classroom");
				teacher = SplashActivity.allInfo.get("teacher");
				courseKind = SplashActivity.allInfo.get("courseKind");
				calEachDayClass();
			}
		}
	}

	public void setImageButtonOnclickEvent() {
		imageButton_update.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent update = new Intent(ClassTableActivity.this,
						NetConnecting.class);
				SharedPreferences preferences = getSharedPreferences(
						"userInfo", MODE_PRIVATE);
				String userName = preferences.getString("userName", "");
				String password = preferences.getString("password", "");
				boolean isRememberPassword = preferences.getBoolean(
						"isRememberPassword", false);
				update.putExtra("userName", userName);
				update.putExtra("password", password);
				update.putExtra("isRememberPassword", isRememberPassword);
				if (!userName.equals("") && !password.equals("")) {
					startActivity(update);
					finish();
				} else {
					AlertDialog.Builder tip = new AlertDialog.Builder(
							ClassTableActivity.this);
					tip.setTitle("ϵͳû�м�⵽����ѧ�ź����룬�������µ�¼��");
					tip.setIcon(R.drawable.logo);
					tip.setPositiveButton("���µ�¼",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									Intent toLogin = new Intent(
											ClassTableActivity.this,
											LoginActivity.class);
									startActivity(toLogin);
									finish();
								}
							});
					tip.setNegativeButton("ȡ��",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
								}
							});
					tip.create().show();
				}
			}
		});

		imageButton_menu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getPopupWindow();
				popupWindow.showAsDropDown(v);
			}
		});
	}

	private void getPopupWindow() {
		if (null != popupWindow) {
			popupWindow.dismiss();
			return;
		} else {
			initPopuptWindow();
		}
	}

	protected void initPopuptWindow() {
		// TODO Auto-generated method stub
		ListView listView = (ListView) getLayoutInflater().inflate(
				R.layout.menu, null, false);
		ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> item;
		item = new HashMap<String, String>();
		item.put("setting", "�û�����");
		data.add(item);
		item = new HashMap<String, String>();
		item.put("setting", "��ת...");
		data.add(item);
		item = new HashMap<String, String>();
		item.put("setting", "ȫ���α�");
		data.add(item);
		item = new HashMap<String, String>();
		item.put("setting", "�л��û�");
		data.add(item);
		String[] from = { "setting" };
		int[] to = { R.id.textview_list_setting };
		SimpleAdapter simpleAdapter = new SimpleAdapter(
				ClassTableActivity.this, data, R.layout.menuliststyle, from, to);
		listView.setAdapter(simpleAdapter);
		// ����PopupWindowʵ��,200,150�ֱ��ǿ�Ⱥ͸߶�
		popupWindow = new PopupWindow(listView, 100, 150, true);
		popupWindow.setFocusable(true);
		// ���ö���Ч��
		popupWindow.setAnimationStyle(R.style.AnimationFade);
		// ��������ط���ʧ
		popupWindow.setBackgroundDrawable(this.getResources().getDrawable(
				R.drawable.bkg));
		popupWindow.setOutsideTouchable(true);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				System.out.println(arg2);
				switch (arg2) {
				case 0:// ��ת������Activity
					popupWindow.dismiss();
					Intent intent = new Intent(ClassTableActivity.this,
							Switch_Activity.class);
					startActivity(intent);
					break;
				case 1:// ��ת������һ���������α�
					popupWindow.dismiss();
					jumpToView();
					break;
				case 2:// ��ת��ȫ���α�Activity
					popupWindow.dismiss();
					Intent jumpToFormat2 = new Intent(ClassTableActivity.this,
							ShowTimeTableActivity.class);
					int[] idArray = null;
					jumpToFormat2.putExtra("idArray", idArray);
					startActivity(jumpToFormat2);
					break;
				case 3:// �л��û�
					popupWindow.dismiss();
					Intent changeUser = new Intent(ClassTableActivity.this,
							LoginActivity.class);
					startActivity(changeUser);
					ClassTableActivity.this.finish();

					break;
				}
			}
		});
	}

	public void setClassTag() {
		Calendar calendar = Calendar.getInstance();
		textbeforenoon1.setText("01\n02\n��");
		textbeforenoon2.setText("03\n04\n��");
		textafternoon1.setText("05\n06\n��");
		textafternoon2.setText("07\n08\n��");
		textnight1.setText("09\n10\n��");
		textnight2.setText("11\n12\n��");
		// nighttime.setText("��\n��\n��\n"
		// + String.valueOf(calendar.get(Calendar.MONTH) + 1) + "\n��\n"
		// + calendar.get(Calendar.DAY_OF_MONTH) + "\n��");

	}

	public void jumpToViewOrAddClass(final Context context, int pos) {
		if (classContentFlag[pos] != -1) {
			Intent viewCourseInfo = new Intent(context,
					ClassInfoViewActivity.class);
			viewCourseInfo.putExtra("pos", classContentFlag[pos]);
			viewCourseInfo.putExtra("isShowHomeWork", true);
			startActivity(viewCourseInfo);
		}
		// else if (classContentFlag[pos] == -1) {
		// AlertDialog.Builder addCourseDialog = new AlertDialog.Builder(
		// ClassTableActivity.this);
		// CharSequence[] items = { "��ӿγ�" };
		// addCourseDialog.setItems(items,
		// new DialogInterface.OnClickListener() {
		//
		// @Override
		// public void onClick(DialogInterface dialog, int which) {
		// // TODO Auto-generated method stub
		// Intent addCourseInfo = new Intent(context,
		// ClassInfoEditActivity.class);
		// startActivity(addCourseInfo);
		// }
		// });
		// addCourseDialog.create().show();
		// }
	}

	public void cleanTextContent() {
		beforenoon1cont.setText("");
		beforenoon2cont.setText("");
		afternoon1cont.setText("");
		afternoon2cont.setText("");
		night1cont.setText("");
		night2cont.setText("");
	}

	public void findView_ViewPager(View view) {
		beforenoon1cont = (TextView) view
				.findViewById(R.id.TextView_FirstClassText);
		beforenoon1cont.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				jumpToViewOrAddClass(ClassTableActivity.this, 0);
			}
		});
		beforenoon2cont = (TextView) view
				.findViewById(R.id.TextView_SecondClassText);
		beforenoon2cont.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				jumpToViewOrAddClass(ClassTableActivity.this, 1);
			}
		});
		afternoon1cont = (TextView) view
				.findViewById(R.id.TextView_ThridClassText);
		afternoon1cont.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				jumpToViewOrAddClass(ClassTableActivity.this, 2);
			}
		});
		afternoon2cont = (TextView) view
				.findViewById(R.id.TextView_FourthClassText);
		afternoon2cont.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				jumpToViewOrAddClass(ClassTableActivity.this, 3);
			}
		});
		night1cont = (TextView) view.findViewById(R.id.TextView_FifthClassText);
		night1cont.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				jumpToViewOrAddClass(ClassTableActivity.this, 4);
			}
		});
		night2cont = (TextView) view.findViewById(R.id.TextView_SixthClassText);
		night2cont.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				jumpToViewOrAddClass(ClassTableActivity.this, 5);
			}
		});

		textbeforenoon1 = (TextView) view
				.findViewById(R.id.TextView_FirstClassNo);
		textbeforenoon2 = (TextView) view
				.findViewById(R.id.TextView_SecondClassNo);
		textafternoon1 = (TextView) view
				.findViewById(R.id.TextView_ThridClassNo);
		textafternoon2 = (TextView) view
				.findViewById(R.id.TextView_FourthClassNo);
		textnight1 = (TextView) view.findViewById(R.id.TextView_FifthClassNo);
		textnight2 = (TextView) view.findViewById(R.id.TextView_SixthClassNo);
		// ���α��������磬����ȱ�־��Ϣ���̶����䣩
		setClassTag();
	}

	public void findMainActyView() {
		textweek = (TextView) findViewById(R.id.textWeek);
		textweek.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (isShowWelcome) {
					welcome.setVisibility(View.INVISIBLE);
					isShowWelcome = false;
				} else {
					welcome.setVisibility(View.VISIBLE);
					isShowWelcome = true;
				}
			}
		});
		welcome = (TextView) findViewById(R.id.textwelcome);
		welcome.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (isShowWelcome) {
					welcome.setVisibility(View.INVISIBLE);
					isShowWelcome = false;
				} else {
					welcome.setVisibility(View.VISIBLE);
					isShowWelcome = true;
				}
			}
		});
		imageButton_update = (ImageButton) findViewById(R.id.button_update);
		imageButton_menu = (ImageButton) findViewById(R.id.button_menu);
		textMon = (TextView) findViewById(R.id.Mon);
		textMon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dayOfWeek = 1;
				mPager.setCurrentItem(dayOfWeek);
			}
		});
		textThue = (TextView) findViewById(R.id.Tue);
		textThue.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dayOfWeek = 2;
				mPager.setCurrentItem(dayOfWeek);
			}
		});
		textWend = (TextView) findViewById(R.id.Wed);
		textWend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dayOfWeek = 3;
				mPager.setCurrentItem(dayOfWeek);
			}
		});
		textThir = (TextView) findViewById(R.id.Thu);
		textThir.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dayOfWeek = 4;
				mPager.setCurrentItem(dayOfWeek);
			}
		});
		textFri = (TextView) findViewById(R.id.Fri);
		textFri.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dayOfWeek = 5;
				mPager.setCurrentItem(dayOfWeek);
			}
		});
		textSat = (TextView) findViewById(R.id.Sta);
		textSat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dayOfWeek = 6;
				mPager.setCurrentItem(dayOfWeek);
			}
		});
		textSun = (TextView) findViewById(R.id.Sun);
		textSun.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dayOfWeek = 0;
				mPager.setCurrentItem(7);
			}
		});
	}

	// �жϽ��������ڼ������ڿα������뵱��Ŀγ�����ArrayList<String> dayClass
	// dayOfWeek��ʾ������һ�����ڵĵڼ���,curWeek��ʾ�ڼ���
	public void fillClassTable(int dayOfWeek, int curWeek) {
		iniViewPagerEle(dayOfWeek);
		for (int i = 0; i < classContentFlag.length; i++) {
			classContentFlag[i] = -1;
		}
		switch (dayOfWeek) {
		// ������Ŀγ�
		case 0:
			setOneDayClass(Sat, Sun, curWeek);
			break;
		// ����һ�Ŀγ�
		case 1:
			setOneDayClass(0, Mon, curWeek);
			break;
		// ���ڶ��Ŀγ�
		case 2:
			setOneDayClass(Mon, Thue, curWeek);
			break;
		// �������Ŀγ�
		case 3:
			setOneDayClass(Thue, Wends, curWeek);
			break;
		// �����ĵĿγ�
		case 4:
			setOneDayClass(Wends, Thir, curWeek);
			break;
		// ������Ŀγ�
		case 5:
			setOneDayClass(Thir, Fri, curWeek);
			break;
		// �������Ŀγ�
		case 6:
			setOneDayClass(Fri, Sat, curWeek);
			break;
		}
	}

	// ����һ�����ڵ�ÿ���ж��ٽڿ�
	public void calEachDayClass() {
		// Mon��Thue�ȵȷֱ��������һ�����ڶ��Ŀ���ArrayList�е�λ������+1
		// �������һ�м��ڿ�
		for (int i = 0; i < dayClass.size(); i++) {
			if (dayClass.get(i).substring(0, 1).matches("^��")) {
				Mon = i;
				break;
			}
		}
		// ������ڶ��м��ڿ�
		for (int i = 0; i < dayClass.size(); i++) {
			if (dayClass.get(i).substring(0, 1).matches("^��")) {
				Thue = i;
				break;
			}
		}
		// ����������м��ڿ�
		for (int i = 0; i < dayClass.size(); i++) {
			if (dayClass.get(i).substring(0, 1).matches("^��")) {
				Wends = i;
				break;
			}
		}
		// ����������м��ڿ�
		for (int i = 0; i < dayClass.size(); i++) {
			if (dayClass.get(i).substring(0, 1).matches("^��")) {
				Thir = i;
				break;
			}
		}
		// ����������м��ڿ�
		for (int i = 0; i < dayClass.size(); i++) {
			if (dayClass.get(i).substring(0, 1).matches("^��")) {
				Fri = i;
				break;
			}
		}
		// ����������м��ڿ�
		for (int i = 0; i < dayClass.size(); i++) {
			if (dayClass.get(i).substring(0, 1).matches("^��")) {
				Sat = i;
				break;
			}
		}
		// ����������м��ڿ�
		Sun = dayClass.size();
	}

	public int divideDayClass(String dayClass) {
		return Integer.parseInt(dayClass.substring(dayClass.indexOf("[") + 1,
				dayClass.indexOf("-")));
	}

	// �ڿα�������γ�����
	public void setOneDayClass(int startPos, int endPos, int curWeek) {
		// curWeek��ʾ�ڼ���
		if (curWeek == 0)
			textweek.setText("��    ��");
		else
			textweek.setText("��  " + curWeek + "  ��");// ����д�����
		// afternoonweek.setText(parseHtml.showWeek(dayOfWeek));
		for (int i = startPos; i < endPos; i++) {
			switch (divideDayClass(dayClass.get(i))) {
			case 1:// 1-2�ڿ�
				fillTextView(beforenoon1cont, curWeek, i, 1);
				break;
			case 3:// 3-4�ڿ�
				fillTextView(beforenoon2cont, curWeek, i, 3);
				break;
			case 5:// 5-6�ڿ�
				fillTextView(afternoon1cont, curWeek, i, 5);
				break;
			case 7:// 7-8�ڿ�
				fillTextView(afternoon2cont, curWeek, i, 7);
				break;
			case 9:// 9-10�ڿ�
				fillTextView(night1cont, curWeek, i, 9);
				break;
			case 11:// 11-12�ڿ�
				fillTextView(night2cont, curWeek, i, 11);
				break;
			}
		}
	}

	// ���textview���ݣ����γ�����
	public void fillTextView(TextView textView, int curWeek, int pos,
			int whichClass) {
		if (parseHtml.hasClassThisWeek(curWeek, (String) SplashActivity.allInfo
				.get("classweek").get(pos))) {
			textView.setText("�γ̣�"+(String) SplashActivity.allInfo.get("className")
					.get(pos)
					+ "\n"
					+ "���ң�"+(String) SplashActivity.allInfo.get("classroom").get(pos)
					+ "\n"
					+ "��ʦ��"+(String) SplashActivity.allInfo.get("teacher").get(pos)
					+ "\n"
					+ "���"+(String) SplashActivity.allInfo.get("courseKind")
							.get(pos));
			switch (whichClass) {
			case 1:
				classContentFlag[0] = pos;
				break;
			case 3:
				classContentFlag[1] = pos;
				break;
			case 5:
				classContentFlag[2] = pos;
				break;
			case 7:
				classContentFlag[3] = pos;
				break;
			case 9:
				classContentFlag[4] = pos;
				break;
			case 11:
				classContentFlag[5] = pos;
				break;
			}
		}
	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // TODO Auto-generated method stub
	// menu.add(0, 3, 0, "ȫ���α�");
	// menu.add(0, 0, 1, "����...");
	// menu.add(0, 1, 2, "�л��û�");
	// menu.add(0, 2, 3, "����");
	// return super.onCreateOptionsMenu(menu);
	// }

	public void jumpToView() {
		// final LayoutInflater layout = LayoutInflater
		// .from(ClassTableActivity.this);
		final View jumpDialog = View.inflate(ClassTableActivity.this,
				R.layout.jumpdialog, null);
		AlertDialog.Builder jump = new AlertDialog.Builder(
				ClassTableActivity.this);
		jump.setTitle("��ת...");
		jump.setView(jumpDialog);
		jump.setIcon(R.drawable.logo);
		jump.setPositiveButton("��ת", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				EditText edit1 = (EditText) jumpDialog
						.findViewById(R.id.jumpdialogedit1);
				EditText edit2 = (EditText) jumpDialog
						.findViewById(R.id.jumpdialogedit2);
				int jumpToWeek = Integer.parseInt(edit1.getText().toString());
				curWeek = jumpToWeek;
				int jumpToDay = Integer.parseInt(edit2.getText().toString());
				if(jumpToDay<=7&&jumpToDay>=1){
					if (jumpToDay == 7) {
						jumpToDay = 0;
					}
					dayOfWeek = jumpToDay;
					fillClassTable(dayOfWeek,curWeek);
				}else
					Toast.makeText(ClassTableActivity.this, "��������", 2000);
			}

		});
		jump.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

			}
		});
		jump.show();
	}

	// @Override
	// public boolean onOptionsItemSelected(MenuItem item) {
	// // TODO Auto-generated method stub
	// switch (item.getItemId()) {
	// case 0:// ��ת��ĳһ��
	// jumpToView();
	// break;
	// case 1:// �л��û�
	// Intent changeUser = new Intent(ClassTableActivity.this,
	// LoginActivity.class);
	// startActivity(changeUser);
	// ClassTableActivity.this.finish();
	// break;
	// case 2:// ����
	// final LayoutInflater layoutAbout = LayoutInflater
	// .from(ClassTableActivity.this);
	// final View aboutDialog = View.inflate(ClassTableActivity.this,
	// R.layout.about, null);
	// AlertDialog.Builder about = new AlertDialog.Builder(
	// ClassTableActivity.this);
	// about.setIcon(R.drawable.logo);
	// about.setTitle("��������");
	// about.setView(aboutDialog);
	// about.setPositiveButton("ȷ��",
	// new DialogInterface.OnClickListener() {
	//
	// @Override
	// public void onClick(DialogInterface dialog, int which) {
	// // TODO Auto-generated method stub
	//
	// }
	// });
	// about.create().show();
	// break;
	// case 3:// չʾȫ���α�
	// Intent jumpToFormat2 = new Intent(ClassTableActivity.this,
	// ShowTimeTableActivity.class);
	// int[] idArray = null;
	// jumpToFormat2.putExtra("idArray", idArray);
	// startActivity(jumpToFormat2);
	// break;
	// default:
	// break;
	// }
	// return super.onOptionsItemSelected(item);
	// }
}
