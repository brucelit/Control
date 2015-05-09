package com.ltt.controlclient;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.zhou.controlclient.R;

public class MainActivity extends Activity {

	public int send_flat,equipNum=1;
	private boolean longClicked;
	private ViewPager viewPager;
	private ActionBar mActionBar;
	private List<Tab> tabList = new ArrayList<ActionBar.Tab>();
	private TextView textView_hostIP,textView_equipNum;
	private Button button_stop,button_forward,
	               button_right,button_left,button_backward;
	private EditText  editText_actuator_max,editText_actuator_min,
	                 editText_actuator_P_min, editText_actuator_P_max,
	                 editText_actuator_I_min, editText_actuator_I_max,
	                 editText_actuator_D_min, editText_actuator_D_max,
	                 editText_motor_max,editText_motor_min,
	                 editText_motor_P_min, editText_motor_P_max,
	                 editText_motor_I_min, editText_motor_I_max,
	                 editText_motor_D_min, editText_motor_D_max,
	                 editText_equipNum;
	private SeekBar seekBar_actuator,seekBar_actuator_P,
	                seekBar_actuator_I, seekBar_actuator_D,
	                seekBar_motor,seekBar_motor_P,
	                seekBar_motor_I,seekBar_motor_D;
    private TextView textView_actuator,textView_actuator_parameter,textView_actuator_1,
                     textView_actuator_progress, textView_actuator_P, textView_actuator_I, textView_actuator_D, 
                     textView_actuator_P_progress,textView_actuator_I_progress,textView_actuator_D_progress,
                     textView_actuator_P_1,textView_actuator_I_1,textView_actuator_D_1,
                     textView_motor,textView_motor_parameter,textView_motor_1,textView_motor_progress,
                     textView_motor_P, textView_motor_I,textView_motor_D,
                     textView_motor_P_progress, textView_motor_I_progress, textView_motor_D_progress,
                     textView_motor_P_1,textView_motor_I_1,textView_motor_D_1,
                     textView_actuator_2,textView_actuator_P_2,textView_actuator_I_2,textView_actuator_D_2,
                     textView_motor_2,textView_motor_P_2,textView_motor_I_2,textView_motor_D_2;
  
    

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
		initData();
		//strictmode设置不允许在这个thread上执行的操作
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
		}
	
	//打开程序调入保存的数据
	 public void initData(){     
	        SharedPreferences preferences = getSharedPreferences("information", MODE_PRIVATE);
	       int equipNum = preferences.getInt("equipNum", 1),
	       actuator_D_max = preferences.getInt("actuator_D_max", 100),
	    		 motor_parameter = preferences.getInt("motor_parameter", 0), 
	    			motor_parameter_min = preferences.getInt("motor_parameter_min", 0), 
	    			motor_parameter_max = preferences.getInt("motor_parameter_max", 100),
	    			motor_P = preferences.getInt("motor_P", 0),
	    					motor_P_min = preferences.getInt("motor_P_min", 0),
	    					motor_P_max = preferences.getInt("motor_P_max", 100),
	    					motor_I = preferences.getInt("motor_I", 0),
	    					motor_I_min = preferences.getInt("motor_I_min", 0), 
	    					motor_I_max = preferences.getInt("motor_I_max", 100), 
	    					motor_D = preferences.getInt("motor_D", 0),
	    					motor_D_min = preferences.getInt("motor_D_min", 0),
	    					motor_D_max = preferences.getInt("motor_D_max", 100), 
	    					actuator_parameter = preferences.getInt("actuator_parameter", 0), 
	    					actuator_parameter_min = preferences.getInt("actuator_parameter_min", 0), 
	    					actuator_parameter_max = preferences.getInt("actuator_parameter_max", 100), 
	    					actuator_P = preferences.getInt("actuator_P", 0), 
	    					actuator_P_min = preferences.getInt("actuator_P_min", 0),
	    					actuator_P_max = preferences.getInt("actuator_P_max", 100),
	    					actuator_I = preferences.getInt("actuator_I", 0),
	    					actuator_I_min = preferences.getInt("actuator_I_min", 0),
	    					actuator_I_max = preferences.getInt("actuator_I_max", 100),
	    					actuator_D = preferences.getInt("actuator_D", 0),
	    					actuator_D_min = preferences.getInt("actuator_D_min", 0);
	    					
	    			editText_motor_max.setText(motor_parameter_max + "");
	    			editText_motor_min.setText(motor_parameter_min + "");
	    			seekBar_motor.setProgress(motor_parameter
	    					- Integer.parseInt(editText_motor_min.getText()
	    							.toString().trim()));
	    			editText_motor_P_max.setText(motor_P_max + "");
	    			editText_motor_P_min.setText(motor_P_min + "");
	    			
	    			seekBar_motor_P.setProgress(motor_P
	    					- Integer.parseInt(editText_motor_P_min.getText()
	    							.toString().trim()));
	    			editText_motor_I_max.setText(motor_I_max + "");
	    			editText_motor_I_min.setText(motor_I_min + "");
	    			seekBar_motor_I.setProgress(motor_I
	    					- Integer.parseInt(editText_motor_I_min.getText()
	    							.toString().trim()));
	    			editText_motor_D_max.setText(motor_D_max + "");
	    			editText_motor_D_min.setText(motor_D_min + "");
	    			seekBar_motor_D.setProgress(motor_D
	    					- Integer.parseInt(editText_motor_D_min.getText()
	    							.toString().trim()));
	    			editText_actuator_max
	    					.setText(actuator_parameter_max + "");
	    			editText_actuator_min
	    					.setText(actuator_parameter_min + "");
	    			seekBar_actuator.setProgress(actuator_parameter
	    					- Integer.parseInt(editText_actuator_min
	    							.getText().toString().trim()));
	    			editText_actuator_P_max.setText(actuator_P_max + "");
	    			editText_actuator_P_min.setText(actuator_P_min + "");
	    			seekBar_actuator_P.setProgress(actuator_P
	    					- Integer.parseInt(editText_actuator_P_min.getText()
	    							.toString().trim()));
	    			editText_actuator_I_max.setText(actuator_I_max + "");
	    			editText_actuator_I_min.setText(actuator_I_min + "");
	    			seekBar_actuator_I.setProgress(actuator_I
	    					- Integer.parseInt(editText_actuator_I_min.getText()
	    							.toString().trim()));
	    			
	    			editText_actuator_D_min.setText(actuator_D_min + "");
	    			seekBar_actuator_D.setProgress(actuator_D
	    					- Integer.parseInt(editText_actuator_D_min.getText()
	    							.toString().trim()));
	    			editText_actuator_D_max.setText(actuator_D_max + "");
	    			editText_equipNum.setText(equipNum+"");
	    			Toast.makeText(MainActivity.this, "已读出数据", 5000).show();
	    }

	 //退出前保存数据
	    public void onStop(){
	    	super.onStop();
	    	SharedPreferences preferences = getSharedPreferences("information", MODE_PRIVATE);
	    	SharedPreferences.Editor editor = getSharedPreferences("information", MODE_PRIVATE).edit();
				editor.putInt(
						"motor_parameter",
						Integer.parseInt(textView_motor_progress.getText()
								.toString().trim()));
				editor.putInt(
						"motor_parameter_min",
						Integer.parseInt(editText_motor_min.getText()
								.toString().trim()));
				editor.putInt(
						"motor_parameter_max",
						Integer.parseInt(editText_motor_max.getText()
								.toString().trim()));
				editor.putInt(
						"motor_P",
						Integer.parseInt(textView_motor_P_progress.getText().toString()
								.trim()));
				editor.putInt(
						"motor_P_min",
						Integer.parseInt(editText_motor_P_min.getText().toString()
								.trim()));
				editor.putInt(
						"motor_P_max",
						Integer.parseInt(editText_motor_P_max.getText().toString()
								.trim()));
				editor.putInt(
						"motor_I",
						Integer.parseInt(textView_motor_I_progress.getText().toString()
								.trim()));
				editor.putInt(
						"motor_I_min",
						Integer.parseInt(editText_motor_I_min.getText().toString()
								.trim()));
				editor.putInt(
						"motor_I_max",
						Integer.parseInt(editText_motor_I_max.getText().toString()
								.trim()));
				editor.putInt(
						"motor_D",
						Integer.parseInt(textView_motor_D_progress.getText().toString()
								.trim()));
				editor.putInt(
						"motor_D_min",
						Integer.parseInt(editText_motor_D_min.getText().toString()
								.trim()));
				editor.putInt(
						"motor_D_max",
						Integer.parseInt(editText_motor_D_max.getText().toString()
								.trim()));
				editor.putInt(
						"actuator_parameter",
						Integer.parseInt(textView_actuator_progress.getText()
								.toString().trim()));
				editor.putInt(
						"actuator_parameter_min",
						Integer.parseInt(editText_actuator_min.getText()
								.toString().trim()));
				editor.putInt(
						"actuator_parameter_max",
						Integer.parseInt(editText_actuator_max.getText()
								.toString().trim()));
				editor.putInt(
						"actuator_P",
						Integer.parseInt(textView_actuator_P_progress.getText().toString()
								.trim()));
				editor.putInt(
						"actuator_P_min",
						Integer.parseInt(editText_actuator_P_min.getText()
								.toString().trim()));
				editor.putInt(
						"actuator_P_max",
						Integer.parseInt(editText_actuator_P_max.getText()
								.toString().trim()));
				editor.putInt(
						"actuator_I",
						Integer.parseInt(textView_actuator_I_progress.getText().toString()
								.trim()));
				editor.putInt(
						"actuator_I_min",
						Integer.parseInt(editText_actuator_I_min.getText()
								.toString().trim()));
				editor.putInt(
						"actuator_I_max",
						Integer.parseInt(editText_actuator_I_max.getText()
								.toString().trim()));
				editor.putInt(
						"actuator_D",
						Integer.parseInt(textView_actuator_D_progress.getText().toString()
								.trim()));
				editor.putInt(
						"actuator_D_min",
						Integer.parseInt(editText_actuator_D_min.getText()
								.toString().trim()));
				editor.putInt(
						"actuator_D_max",
						Integer.parseInt(editText_actuator_D_max.getText()
								.toString().trim()));
				editor.putInt("equipNum", Integer.parseInt(editText_equipNum.getText().toString().trim()));
				// System.out.println(editText_actuator_I_max.getText().toString()
				// .trim());
				editor.commit();
				Toast.makeText(MainActivity.this, "已存入数据", 5000).show();
	    }
	
	
	
	//页面初始化
	public void init() {
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		LayoutInflater Flater = LayoutInflater.from(this);
		View view1 = Flater.inflate(R.layout.tab1, null);
		View view2 = Flater.inflate(R.layout.tab2, null);
		View view3 = Flater.inflate(R.layout.tab3, null);
		final ArrayList<View> Views = new ArrayList<View>();
		final ArrayList<String> Strs = new ArrayList<String>();
		Views.add(view1);
		Views.add(view2);
		Views.add(view3);
		Strs.add("遥控器");
		Strs.add("舵机调节");
		Strs.add("电机调节");
		viewPager.setAdapter(new PagerViewAdapter(Views));
		viewPager.setCurrentItem(1);
		mActionBar = getActionBar();
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		mActionBar.setDisplayShowTitleEnabled(false);
		mActionBar.setDisplayShowHomeEnabled(false);
		for (int count = 0; count < Strs.size(); count++) {
			tabList.add(mActionBar.newTab().setText(Strs.get(count))
					.setTabListener(mTabListener));
			mActionBar.addTab(tabList.get(count));
		}
		viewPager.setOnPageChangeListener(new onPageChangeListner());
		//以下为第一页
		textView_hostIP= (TextView) view1.findViewById(R.id.textView_hostIP);
	    textView_hostIP.setText(getwifi());
		editText_equipNum =(EditText) view1.findViewById(R.id.editText_equipNum);
		textView_equipNum=(TextView) view1.findViewById(R.id.textView_equipNum);
		editText_equipNum.setText(equipNum+"");
		button_forward = (Button) view1.findViewById(R.id.button_forward);
		button_forward.setOnClickListener(new MotorButtonListener());
		button_stop = (Button) view1.findViewById(R.id.button_stop);
		button_stop.setOnClickListener(new MotorButtonListener());
		button_left = (Button)view1.findViewById(R.id.button_left);
		button_left.setOnClickListener(new ActuatorButtonListener());
		button_right = (Button)view1. findViewById(R.id.button_right);
		button_right.setOnClickListener(new ActuatorButtonListener());
		button_backward = (Button) view1.findViewById(R.id.button_backward);
		button_backward.setOnClickListener(new MotorButtonListener());
		seekBar_actuator= (SeekBar) view1.findViewById(R.id.seekBar_actuator);
		 seekBar_actuator.setOnSeekBarChangeListener(new Actuator_SeekBarChangeListener());
		 textView_actuator_parameter=(TextView) view1.findViewById(R.id.textView_actuator_parameter);
		 textView_actuator_1=(TextView) view1.findViewById(R.id.textView_actuator_1);
		 editText_actuator_min=(EditText) view1.findViewById(R.id.editText_actuator_min);
		 editText_actuator_max=(EditText) view1.findViewById(R.id.editText_actuator_max);
		 editText_actuator_min.setText("0");
		 editText_actuator_max.setText("100");
		 textView_actuator_2=(TextView) view1.findViewById(R.id.textView_actuator_2);
		 textView_actuator_progress= (TextView) view1.findViewById(R.id.textView_actuator_progress);
		 seekBar_motor= (SeekBar) view1.findViewById(R.id.seekBar_motor);
		 seekBar_motor.setOnSeekBarChangeListener(new Motor_SeekBarChangeListener());
		 textView_motor_parameter=(TextView) view1.findViewById(R.id.textView_motor_parameter);
		 textView_motor_1=(TextView) view1.findViewById(R.id.textView_motor_1);
		 editText_motor_min=(EditText) view1.findViewById(R.id.editText_motor_min);
		 editText_motor_max=(EditText) view1.findViewById(R.id.editText_motor_max);
		 editText_motor_min.setText("0");
		 editText_motor_max.setText("100");
		 textView_motor_2=(TextView) view1.findViewById(R.id.textView_motor_2);
		 textView_motor_progress= (TextView) view1.findViewById(R.id.textView_motor_progress);
		 //以下为第二页
		 
		 seekBar_actuator_P= (SeekBar) view2.findViewById(R.id.seekBar_actuator_P);
		 seekBar_actuator_P.setOnSeekBarChangeListener(new Actuator_P_SeekBarChangeListener());
		 textView_actuator_P=(TextView) view2.findViewById(R.id.textView_actuator_P);
		 textView_actuator_P_1=(TextView) view2.findViewById(R.id.textView_actuator_P_1);
		 editText_actuator_P_min=(EditText) view2.findViewById(R.id.editText_actuator_P_min);
		 editText_actuator_P_max=(EditText) view2.findViewById(R.id.editText_actuator_P_max);
		 editText_actuator_P_min.setText("0");
		 editText_actuator_P_max.setText("100");
		 textView_actuator_P_2=(TextView) view2.findViewById(R.id.textView_actuator_P_2);
		 textView_actuator_P_progress= (TextView) view2.findViewById(R.id.textView_actuator_P_progress);
		 seekBar_actuator_I= (SeekBar) view2.findViewById(R.id.seekBar_actuator_I);
		 seekBar_actuator_I.setOnSeekBarChangeListener(new Actuator_I_SeekBarChangeListener());
		 textView_actuator_I=(TextView) view2.findViewById(R.id.textView_actuator_I);
		 textView_actuator_I_1=(TextView) view2.findViewById(R.id.textView_actuator_I_1);
		 editText_actuator_I_min=(EditText) view2.findViewById(R.id.editText_actuator_I_min);
		 editText_actuator_I_max=(EditText) view2.findViewById(R.id.editText_actuator_I_max);
		 editText_actuator_I_min.setText("0");
		 editText_actuator_I_max.setText("100");
		 textView_actuator_I_2=(TextView) view2.findViewById(R.id.textView_actuator_I_2);
		 textView_actuator_I_progress= (TextView) view2.findViewById(R.id.textView_actuator_I_progress); 
		 seekBar_actuator_D= (SeekBar) view2.findViewById(R.id.seekBar_actuator_D);
		 seekBar_actuator_D.setOnSeekBarChangeListener(new Actuator_D_SeekBarChangeListener());
		 textView_actuator_D=(TextView) view2.findViewById(R.id.textView_actuator_D);
		 textView_actuator_D_1=(TextView) view2.findViewById(R.id.textView_actuator_D_1);
		 editText_actuator_D_min=(EditText) view2.findViewById(R.id.editText_actuator_D_min);
		 editText_actuator_D_max=(EditText) view2.findViewById(R.id.editText_actuator_D_max);
		 editText_actuator_D_min.setText("0");
		 editText_actuator_D_max.setText("100");
		 textView_actuator_D_2=(TextView) view2.findViewById(R.id.textView_actuator_D_2);
		 textView_actuator_D_progress= (TextView) view2.findViewById(R.id.textView_actuator_D_progress);
	     //以下为第三页
		 
		 seekBar_motor_P= (SeekBar) view3.findViewById(R.id.seekBar_motor_P);
		 seekBar_motor_P.setOnSeekBarChangeListener(new Motor_P_SeekBarChangeListener());
		 textView_motor_P=(TextView) view3.findViewById(R.id.textView_motor_P);
		 textView_motor_P_1=(TextView) view3.findViewById(R.id.textView_motor_P_1);
		 editText_motor_P_min=(EditText) view3.findViewById(R.id.editText_motor_P_min);
		 editText_motor_P_max=(EditText) view3.findViewById(R.id.editText_motor_P_max);
		 editText_motor_P_min.setText("0");
		 editText_motor_P_max.setText("100");
		 textView_motor_P_2=(TextView) view3.findViewById(R.id.textView_motor_P_2);
		 textView_motor_P_progress= (TextView) view3.findViewById(R.id.textView_motor_P_progress);
		 seekBar_motor_I= (SeekBar) view3.findViewById(R.id.seekBar_motor_I);
		 seekBar_motor_I.setOnSeekBarChangeListener(new Motor_I_SeekBarChangeListener());
		 textView_motor_I=(TextView) view3.findViewById(R.id.textView_motor_I);
		 textView_motor_I_1=(TextView) view3.findViewById(R.id.textView_motor_I_1);
		 editText_motor_I_min=(EditText) view3.findViewById(R.id.editText_motor_I_min);
		 editText_motor_I_max=(EditText) view3.findViewById(R.id.editText_motor_I_max);
		 editText_motor_I_min.setText("0");
		 editText_motor_I_max.setText("100");
		 textView_motor_I_2=(TextView) view3.findViewById(R.id.textView_motor_I_2);
		 textView_motor_I_progress= (TextView) view3.findViewById(R.id.textView_motor_I_progress); 
		 seekBar_motor_D= (SeekBar) view3.findViewById(R.id.seekBar_motor_D);
		 seekBar_motor_D.setOnSeekBarChangeListener(new Motor_D_SeekBarChangeListener());
		 textView_motor_D=(TextView) view3.findViewById(R.id.textView_motor_D);
		 textView_motor_D_1=(TextView) view3.findViewById(R.id.textView_motor_D_1);
		 editText_motor_D_min=(EditText) view3.findViewById(R.id.editText_motor_D_min);
		 editText_motor_D_max=(EditText) view3.findViewById(R.id.editText_motor_D_max);
		 editText_motor_D_min.setText("0");
		 editText_motor_D_max.setText("100");
		 textView_motor_D_2=(TextView) view3.findViewById(R.id.textView_motor_D_2);
		 textView_motor_D_progress= (TextView) view3.findViewById(R.id.textView_motor_D_progress);
	}
	
	
	
	public class onPageChangeListner implements OnPageChangeListener{

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub	
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			mActionBar.setSelectedNavigationItem(arg0);
		}
		
	}
	
	//按钮的调节
	 public class ActuatorTouchListener implements OnTouchListener {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				final int button_data=1;
			    if(v==button_left)
					send_flat=10;
				else if(v==button_right)
					send_flat=11;
				equipNum=Integer.parseInt(editText_equipNum.getText().toString());
				 if (event.getAction() == MotionEvent.ACTION_DOWN) {
			    	 Log.e("1", "true");
			     longClicked = true;
			      Thread t = new Thread() {
			        @Override
			        public void run() {
			          super.run();
			          while (longClicked) {
			            // 发送命令
			        	  ServerRunnable serverRunnable =new ServerRunnable();
			  			serverRunnable.run(equipNum,button_data,send_flat);
			        	  new Thread(serverRunnable).start();
			            Log.e("2","true");
			            try {
			              Thread.sleep(250);
			            } catch (InterruptedException e) {
			              e.printStackTrace();
			            }
			          }
			        }
			      };
			      t.start();
			    }
			    
			    else if (event.getAction() == MotionEvent.ACTION_UP) {
			      Log.e("2", "true");
			      longClicked = false;
			      Log.e("3","true");
			    }
			    return true;
			}
		}
	 

	//以下为舵机参数P、I、D的调节
	 public class Actuator_SeekBarChangeListener implements OnSeekBarChangeListener{

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				 seekBar_actuator.setMax(Integer.parseInt(editText_actuator_max.getText().toString())-Integer.parseInt(editText_actuator_min.getText().toString()));
				 progress = progress+ Integer.parseInt(editText_actuator_min.getText().toString().trim());
				 send_flat=1;
				 textView_actuator_progress.setText(""+progress);
				 ServerRunnable serverRunnable =new ServerRunnable();
				 equipNum=Integer.parseInt(editText_equipNum.getText().toString());
				 serverRunnable.run(equipNum,progress,send_flat);
				 new Thread(serverRunnable).start();
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
	    	 
			}
	
     public class Actuator_P_SeekBarChangeListener implements OnSeekBarChangeListener{

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			// TODO Auto-generated method stub
			seekBar_actuator_P.setMax(Integer.parseInt(editText_actuator_P_max.getText().toString())-Integer.parseInt(editText_actuator_P_min.getText().toString()));
			 progress = progress+ Integer.parseInt(editText_actuator_P_min.getText().toString().trim());
			 send_flat=2;
			 textView_actuator_P_progress.setText(""+progress);
			 ServerRunnable serverRunnable =new ServerRunnable();
			 equipNum=Integer.parseInt(editText_equipNum.getText().toString());
			 serverRunnable.run(equipNum,progress,send_flat);
			 new Thread(serverRunnable).start();
		}
		
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}
    	 
		}
		
		public class Actuator_I_SeekBarChangeListener implements OnSeekBarChangeListener{

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				seekBar_actuator_I.setMax(Integer.parseInt(editText_actuator_I_max.getText().toString())-Integer.parseInt(editText_actuator_I_min.getText().toString()));
				 progress = progress+ Integer.parseInt(editText_actuator_I_min.getText().toString().trim());
				 textView_actuator_I_progress.setText(""+progress);
				 send_flat=3;
				 ServerRunnable serverRunnable =new ServerRunnable();
				 equipNum=Integer.parseInt(editText_equipNum.getText().toString());
				 serverRunnable.run(equipNum,progress,send_flat);
			     new Thread(serverRunnable).start();
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
		}
			
         public class Actuator_D_SeekBarChangeListener implements OnSeekBarChangeListener{

				@Override
				public void onProgressChanged(SeekBar seekBar, int progress,
						boolean fromUser) {
					// TODO Auto-generated method stub
					 seekBar_actuator_D.setMax(Integer.parseInt(editText_actuator_D_max.getText().toString())-Integer.parseInt(editText_actuator_D_min.getText().toString()));
					 progress = progress+ Integer.parseInt(editText_actuator_D_min.getText().toString().trim());
					 textView_actuator_D_progress.setText(""+progress);
					 send_flat=4;
					 ServerRunnable serverRunnable =new ServerRunnable();
					 equipNum=Integer.parseInt(editText_equipNum.getText().toString());
					 serverRunnable.run(equipNum,progress,send_flat);
				     new Thread(serverRunnable).start();
				}

				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub
					
				}
		    	
    }
       
//以下为电机参数的调节
         
         public class Motor_SeekBarChangeListener implements OnSeekBarChangeListener{

				@Override
				public void onProgressChanged(SeekBar seekBar, int progress,
						boolean fromUser) {
					// TODO Auto-generated method stub
					 seekBar_motor.setMax(Integer.parseInt(editText_motor_max.getText().toString())-Integer.parseInt(editText_motor_min.getText().toString()));
					 progress = progress+ Integer.parseInt(editText_motor_min.getText().toString().trim());
					 textView_motor_progress.setText(""+progress);
					 send_flat=5;
					 ServerRunnable serverRunnable =new ServerRunnable();
					 equipNum=Integer.parseInt(editText_equipNum.getText().toString());
					 serverRunnable.run(equipNum,progress,send_flat);
				     new Thread(serverRunnable).start();
				}

				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub
					
				}
		    	
  }
         
         public class Motor_P_SeekBarChangeListener implements OnSeekBarChangeListener{

     		@Override
     		public void onProgressChanged(SeekBar seekBar, int progress,
     				boolean fromUser) {
     			// TODO Auto-generated method stub
     			 seekBar_motor_P.setMax(Integer.parseInt(editText_motor_P_max.getText().toString())-Integer.parseInt(editText_motor_P_min.getText().toString()));
     			 progress = progress+ Integer.parseInt(editText_motor_P_min.getText().toString().trim());
     			 send_flat=6;
     			 textView_motor_P_progress.setText(""+progress);
     			 ServerRunnable serverRunnable =new ServerRunnable();
     			 equipNum=Integer.parseInt(editText_equipNum.getText().toString());
     			 serverRunnable.run(equipNum,progress,send_flat);
     			 new Thread(serverRunnable).start();
     		}
     		
     		@Override
     		public void onStartTrackingTouch(SeekBar seekBar) {
     			// TODO Auto-generated method stub
     			
     		}

     		@Override
     		public void onStopTrackingTouch(SeekBar seekBar) {
     			// TODO Auto-generated method stub
     			
     		}
         	 
     		}
     		
     		public class Motor_I_SeekBarChangeListener implements OnSeekBarChangeListener{

     			@Override
     			public void onProgressChanged(SeekBar seekBar, int progress,
     					boolean fromUser) {
     				// TODO Auto-generated method stub
     				seekBar_motor_I.setMax(Integer.parseInt(editText_motor_I_max.getText().toString())-Integer.parseInt(editText_motor_I_min.getText().toString()));
     				 progress = progress+ Integer.parseInt(editText_motor_I_min.getText().toString().trim());
     				 textView_motor_I_progress.setText(""+progress);
     				 send_flat=7;
     				 ServerRunnable serverRunnable =new ServerRunnable();
     				 equipNum=Integer.parseInt(editText_equipNum.getText().toString());
     				 serverRunnable.run(equipNum,progress,send_flat);
     			     new Thread(serverRunnable).start();
     			}

     			@Override
     			public void onStartTrackingTouch(SeekBar seekBar) {
     				// TODO Auto-generated method stub
     				
     			}

     			@Override
     			public void onStopTrackingTouch(SeekBar seekBar) {
     				// TODO Auto-generated method stub
     				
     			}
     			
     		}
     			
              public class Motor_D_SeekBarChangeListener implements OnSeekBarChangeListener{

     				@Override
     				public void onProgressChanged(SeekBar seekBar, int progress,
     						boolean fromUser) {
     					// TODO Auto-generated method stub
     					 seekBar_motor_D.setMax(Integer.parseInt(editText_motor_D_max.getText().toString())-Integer.parseInt(editText_motor_P_min.getText().toString()));
     					 progress = progress+ Integer.parseInt(editText_motor_D_min.getText().toString().trim());
     					 textView_motor_D_progress.setText(""+progress);
     					 send_flat=8;
     					 ServerRunnable serverRunnable =new ServerRunnable();
     					 equipNum=Integer.parseInt(editText_equipNum.getText().toString());
     					 serverRunnable.run(equipNum,progress,send_flat);
     				     new Thread(serverRunnable).start();
     				}

     				@Override
     				public void onStartTrackingTouch(SeekBar seekBar) {
     					// TODO Auto-generated method stub
     					
     				}

     				@Override
     				public void onStopTrackingTouch(SeekBar seekBar) {
     					// TODO Auto-generated method stub
     					
     				}
     		    	
         }
	
	
	public class PagerViewAdapter extends PagerAdapter {

		private ArrayList<View> Views;

		public PagerViewAdapter(ArrayList<View> views) {
			Views = views;
		}

		@Override
		public int getCount() {
			return Views.size();
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((ViewPager) container).removeView(Views.get(position));
		}

		@Override
		public int getItemPosition(Object object) {
			return super.getItemPosition(object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			((ViewPager) container).addView(Views.get(position));
			return Views.get(position);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

	}
	
	//电机按钮调节
	public class MotorButtonListener implements OnClickListener {
		@Override
		public void onClick(View v) {
				int motor_button_data = 0;
				if(v==button_forward)
				{
					motor_button_data=40;
					send_flat=9;
				}
					else if(v==button_backward)
					{
						motor_button_data=-40;
						send_flat=10;
					}
				else if(v==button_stop)
					{
					send_flat=11;
					motor_button_data=0;
					}
				equipNum=Integer.parseInt(editText_equipNum.getText().toString());
				ServerRunnable serverRunnable =new ServerRunnable();
				serverRunnable.run(equipNum,motor_button_data,send_flat);
				new Thread(serverRunnable).start();
		}
	}
	
	//舵机按钮调节
	public class ActuatorButtonListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			int actuator_button_data = 3300;
			if(v==button_left)
					{
					send_flat=12;
				    actuator_button_data=2800;
					}
					
				else if(v==button_right)
				{
					send_flat=13;
					actuator_button_data=3800;
				}
				equipNum=Integer.parseInt(editText_equipNum.getText().toString());
				ServerRunnable serverRunnable =new ServerRunnable();
				serverRunnable.run(equipNum,actuator_button_data,send_flat);
				new Thread(serverRunnable).start();
		}
	}
	
	
	
	//得到本机ip地址
		public String getwifi(){
			WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
			WifiInfo wifiInfo = wifiManager.getConnectionInfo();
			int ipAddress = wifiInfo.getIpAddress();
			String ip = "本机ip地址为："
					+ String.format("%d.%d.%d.%d", (ipAddress & 0xff),
							(ipAddress >> 8 & 0xff),
							(ipAddress >> 16 & 0xff),
							(ipAddress >> 24 & 0xff));
			if (ipAddress == 0)
				ip = "未接入wifi网络";
			return ip;
		}

	
	TabListener mTabListener = new TabListener() {

		@Override
		public void onTabSelected(Tab tab, android.app.FragmentTransaction ft) {
			if (tab == tabList.get(0)) {
				viewPager.setCurrentItem(0);
			} else if (tab == tabList.get(1)) {
				viewPager.setCurrentItem(1);
			} else if (tab == tabList.get(2)) {
				viewPager.setCurrentItem(2);
			}
			else if (tab == tabList.get(3)) {
				viewPager.setCurrentItem(3);
			}

		}

		@Override
		public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onTabReselected(Tab tab, android.app.FragmentTransaction ft) {
			// TODO Auto-generated method stub
		}
	};
	
 }

 


		
