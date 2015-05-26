package com.classtable.setinfo;


import com.classtable.login.R;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
public class SwitchButton {
    private String NAME;
    private Context context;
    private ImageButton sbutton=null;
    private boolean STATE_ON=false;
    private RelativeLayout relayout=null;
    private TextView update=null;
    
    
    public SwitchButton(String NAME, Context context ,boolean STATE_ON){//���캯��
    	this.NAME=NAME;//����״̬�Ĵ�ȡ
    	this.context=context;
    	this.STATE_ON=STATE_ON;//���������ʼ��ʱ�򿪹ص�״̬

    }
    
    public void setState(boolean STATE_ON){
    	this.STATE_ON=STATE_ON;
    	if(STATE_ON){
    		this.sbutton.setImageResource(R.drawable.on);
    		if("����".equals(this.NAME)){//���Զ����¿��ص�ʱ��Ը���һ���Ĳ���
    		      this.relayout.setBackgroundColor(Color.WHITE);
    		      this.update.setBackgroundResource(R.drawable.time_selector);   
    		      this.update.setClickable(true);
    		}
    		
    	}else{
    		this.sbutton.setImageResource(R.drawable.off);
    		if("����".equals(this.NAME)){//�ر��Զ����¿��ص�ʱ��Ը���һ���Ĳ���
  		         this.relayout.setBackgroundColor(Color.GRAY);//���ñ���Ϊ��ɫ
  		         this.update.setBackgroundColor(Color.GRAY);   //���ð�ť�ı���Ϊ��ɫ
  		         this.update.setClickable(false);//���ð�ť���ɱ����
  		     }	
    	}
    }
    
    public boolean getState(){
    	return this.STATE_ON;//���ص�ǰ��״̬
    }
	
    private class click implements OnClickListener{//���õ�ǰ�İ�ť��״̬
    	private SharedPreferences timedatat=null;
        private Editor editor=null; 
		
        @Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
        	this.timedatat=context.getSharedPreferences("userInfo", 
        			context.MODE_PRIVATE);
        	editor=this.timedatat.edit();//��ȡsharedpreference��editor����������в���
			ImageButton Switch=(ImageButton)v;
			
			if(STATE_ON){//��ǰ��״̬ʱ���ŵģ����ڽ��йر�
			   Switch.setImageResource(R.drawable.off);
			   STATE_ON=false;
			   
			   System.out.println(NAME+"---�ر�");
			   if("����".equals(NAME)){//�ر��Զ����¿��ص�ʱ��Ը���һ���Ĳ���
	  		         relayout.setBackgroundColor(Color.GRAY);//���ñ���Ϊ��ɫ
	  		         update.setBackgroundColor(Color.GRAY);   //���ð�ť�ı���Ϊ��ɫ
	  		         update.setClickable(false);//���ð�ť���ɱ����
	  		     }	
			}else{//��ǰ��״̬ʱ���ŵģ����ڽ��д�
				Switch.setImageResource(R.drawable.on);
			   STATE_ON=true;
			   System.out.println(NAME+"---��");
			   if("����".equals(NAME)){//���Զ����¿��ص�ʱ��Ը���һ���Ĳ���
	    		      relayout.setBackgroundColor(Color.WHITE);
	    		      update.setBackgroundResource(R.drawable.time_selector);   
	    		      update.setClickable(true);
	    		}
			}
			editor.putBoolean(NAME, STATE_ON);//����ǰ�Ŀ��ص�״̬д���ļ�
		    editor.commit();
		}
		
		
	}

	public void CreateViewByID(int ID) {
		// TODO Auto-generated method stub
		this.sbutton=(ImageButton) ((Activity) this.context).findViewById(ID);//��ȡ��Ӧ�Ŀؼ�
    	this.sbutton.setOnClickListener(new click());//���ü���
    	
    	this.relayout=(RelativeLayout) ((Activity) this.context).findViewById(R.id.self_update_state2);
        this.update=(TextView)((Activity)this.context).findViewById(R.id.mytime_set);
        this.setState(this.STATE_ON);
	}
	
	

	public void startIntent(Intent intent) {
		// TODO Auto-generated method stub
		this.context.startActivity(intent);
	}
	 
}
