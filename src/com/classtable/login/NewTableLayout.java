package com.classtable.login;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TableLayout;
import android.widget.Toast;

public class NewTableLayout extends TableLayout {
	private float startX = 0;
	public static int MIN_FLING_DISTANCE = 20;
	public static final String TAG = "newtablelayout";
	public NewTableLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public NewTableLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {

		Log.w(TAG, "onInterceptTouchEvent");
		Log.w(TAG,
				"super.onInterceptTouchEvent(ev) = "
						+ super.onInterceptTouchEvent(ev));
		int what = ev.getAction();
		switch (what) {
		case MotionEvent.ACTION_DOWN:
			startX = ev.getX();
			System.out.println(startX);
			break;
		case MotionEvent.ACTION_MOVE:
			Log.w(TAG, "action_move");
			break;
		case MotionEvent.ACTION_UP:
			Log.w(TAG, "action_up");
			break;
		case MotionEvent.ACTION_CANCEL:
			Log.w(TAG, "action_cancel");
			break;
		default:
			break;
		}
		final float x = ev.getX();
		System.out.println(x);
		// 如果X方向滑动的距离大于指定距离则拦截手势动作，执行本View的OnTouch事件
		if (Math.abs(x - startX) > MIN_FLING_DISTANCE) {
			return true;
		}
		return super.onInterceptTouchEvent(ev);
	}

//	@Override
//	public boolean onTouchEvent(MotionEvent event) {
//		int action = event.getAction();
//		switch (action) {
//		case MotionEvent.ACTION_CANCEL:
//			System.out.println("new table --ontouchevent-- cancel");
////			break;
//		case MotionEvent.ACTION_UP:
//			System.out.println("new table --ontouchevent-- up");
////			break;
//		case MotionEvent.ACTION_DOWN:
//			System.out.println("new table --ontouchevent-- down");
////			break;
//			float x = event.getX();
//			if (x > startX) {
//				Toast.makeText(getContext(), "MainViewOnTouch LEFT",
//						Toast.LENGTH_SHORT).show();
//			} else {
//				Toast.makeText(getContext(), "MainViewOnTouch RIGHT",
//						Toast.LENGTH_SHORT).show();
//			}
//			break;
//
//		default:
//			break;
//		}
//		System.out.println("new table super.onTouchEvent(ev)"+super.onTouchEvent(event));
//		return false;
//	}

}
