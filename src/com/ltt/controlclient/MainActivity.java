package com.zhou.controlclient;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.graphics.Color;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class MainActivity extends Activity {

	private ViewPager viewPager;
	private ActionBar mActionBar;
	private List<Tab> tabList = new ArrayList<ActionBar.Tab>();
	private TextView tv_hostIPAddress, tv_receive;
	private Button btn_receive, btn_close;
	private DatagramSocket udpSocket = null;
	private Button btn_send;
	private EditText et_sendMessage, et_sendIP;
	// achartengine
	/** The main dataset that includes all the series that go into a chart. */
	private XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();
	/** The main renderer that includes all the renderers customizing a chart. */
	private XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
	/** The most recently added series. */
	private XYSeries mCurrentSeries;
	/** The most recently created renderer, customizing the current series. */
	private XYSeriesRenderer mCurrentRenderer;
	/** The chart view that displays the data. */
	private GraphicalView mChartView;
	// ��ͼx,y��
	private int x = 0, y = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}

	private void init() {
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		LayoutInflater Flater = LayoutInflater.from(this);
		View view1 = Flater.inflate(R.layout.activity_receive, null);
		View view2 = Flater.inflate(R.layout.activity_send, null);
		final ArrayList<View> Views = new ArrayList<View>();
		final ArrayList<String> Strs = new ArrayList<String>();
		Views.add(view1);
		Views.add(view2);
		Strs.add("����ҳ��");
		Strs.add("����ҳ��");
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
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageSelected(int position) {
				mActionBar.setSelectedNavigationItem(position);
			}
		});
		tv_hostIPAddress = (TextView) view1.findViewById(R.id.tv_hostIPAddress);
		tv_receive = (TextView) view1.findViewById(R.id.tv_receive);
		btn_receive = (Button) view1.findViewById(R.id.btn_receive);
		btn_close = (Button) view1.findViewById(R.id.btn_close);
		btn_send = (Button) view2.findViewById(R.id.btn_send);
		et_sendIP = (EditText) view2.findViewById(R.id.et_sendIP);
		et_sendMessage = (EditText) view2.findViewById(R.id.et_sendMessage);
		btn_receive.setOnClickListener(new SocketListener());
		btn_close.setOnClickListener(new SocketListener());
		btn_send.setOnClickListener(new SocketListener());

		// set some properties on the main renderer
		// default background color is black
		mRenderer.setApplyBackgroundColor(true);
		// set background to black
		mRenderer.setBackgroundColor(Color.argb(100, 50, 50, 50));
		mRenderer.setAxisTitleTextSize(16);
		mRenderer.setChartTitleTextSize(20);
		mRenderer.setLabelsTextSize(15);
		mRenderer.setLegendTextSize(15);
		mRenderer.setMargins(new int[] { 20, 30, 15, 0 });
		mRenderer.setZoomButtonsVisible(true);
		mRenderer.setPointSize(5);
		String seriesTitle = "�¶�";
		// create a new series of data
		XYSeries series = new XYSeries(seriesTitle);
		mDataset.addSeries(series);
		mCurrentSeries = series;
		// create a new renderer for the new series
		XYSeriesRenderer renderer = new XYSeriesRenderer();
		mRenderer.addSeriesRenderer(renderer);
		// set some renderer properties
		renderer.setPointStyle(PointStyle.CIRCLE);
		renderer.setFillPoints(true);
		renderer.setDisplayChartValues(true);
		renderer.setDisplayChartValuesDistance(10);
		mCurrentRenderer = renderer;
		// mChartView.repaint();
		if (mChartView == null) {
			LinearLayout layout = (LinearLayout) view1.findViewById(R.id.chart);
			mChartView = ChartFactory.getLineChartView(this, mDataset,
					mRenderer);
			layout.addView(mChartView, new LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		} else {
			mChartView.repaint();
		}
	}

	public class ServerRunnable implements Runnable {
		int sendOrReceive;

		public ServerRunnable(int SendOrReceive) {
			// 1Ϊsend,0Ϊreceive
			sendOrReceive = SendOrReceive;
		}

		public void run() {
			try {
				// turn on socket in udp mode
				if (udpSocket == null)
					udpSocket = new DatagramSocket(4567);
				if (sendOrReceive == 0) {
					while (true) {
						byte buffer[] = new byte[5];
						DatagramPacket packet = new DatagramPacket(buffer,
								buffer.length);
						udpSocket.receive(packet);
						byte[] data = packet.getData();
						int dataInt = Util.DecodeToInt(data);
						Log.e("dataInt", "" + dataInt);
						Message msg = new Message();
						Bundle bundle = new Bundle();
						bundle.putString("data", "" + dataInt);
						msg.setData(bundle);
						handler.sendMessage(msg);
					}
				} else if (sendOrReceive == 1) {
					String IP = et_sendIP.getText().toString().trim();
					InetAddress IPAddress;
					IPAddress = InetAddress.getByName(IP);
					String Text = et_sendMessage.getText().toString().trim();
					Log.e("send_text", Text);
					byte data = Util.intToByte(Integer.parseInt(Text));
					byte buffer[] = Util.packageToSend(data);
					// byte data[] =
					for (int count = 0; count < buffer.length; count++) {
						Log.e("send_text_byte", "" + buffer[count]);
					}
					DatagramPacket packet = new DatagramPacket(buffer,
							buffer.length, IPAddress, 4567);
					udpSocket.send(packet);
					// udpSocket.close();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Bundle data = msg.getData();
			String result = data.getString("data");
			Log.e("result_handler", result);
			tv_receive.setText(result);
			y = Integer.parseInt(result);
			mCurrentSeries.add(x, y);
			x++;
			// repaint the chart such as the newly added point to be visible
			mChartView.repaint();
		}

	};

	class PagerViewAdapter extends PagerAdapter {

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

	class SocketListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (v == btn_receive) {
				// put IP address on TextView
				WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
				WifiInfo wifiInfo = wifiManager.getConnectionInfo();
				int ipAddress = wifiInfo.getIpAddress();
				Log.e("ipAddress", "" + ipAddress);
				String ip = "����ip��ַΪ��"
						+ String.format("%d.%d.%d.%d", (ipAddress & 0xff),
								(ipAddress >> 8 & 0xff),
								(ipAddress >> 16 & 0xff),
								(ipAddress >> 24 & 0xff));
				if (ipAddress == 0)
					ip = "δ����wifi����";
				tv_hostIPAddress.setText(ip);
				// ������������²Ŵ򿪼����߳�
				if (ipAddress != 0) {
					ServerRunnable receveRunnable = new ServerRunnable(0);
					new Thread(receveRunnable).start();
					btn_receive.setText("�����߳��Ѵ�");
					btn_close.setText("�رռ����߳�");
				}
			} else if (v == btn_send) {
				ServerRunnable sendRunnable = new ServerRunnable(1);
				new Thread(sendRunnable).start();
			}
		}

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
