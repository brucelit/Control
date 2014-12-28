package com.zhou.controlclient;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import android.app.Activity;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView tv_hostIPAddress;
	private Button btn_receive;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}

	private void init() {
		tv_hostIPAddress = (TextView) findViewById(R.id.tv_hostIPAddress);
		btn_receive = (Button) findViewById(R.id.btn_receive);
		btn_receive.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new ServerThread().start();
			}
		});
	}

	public class ServerThread extends Thread {
		public void run() {
			try {
				//put IP address on TextView
				WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
				WifiInfo wifiInfo = wifiManager.getConnectionInfo();
				int ipAddress = wifiInfo.getIpAddress();
				Log.e("ipAddress", ""+ipAddress);
				String ip = "本机ip地址为："+String.format("%d.%d.%d.%d", (ipAddress & 0xff),
						(ipAddress >> 8 & 0xff), (ipAddress >> 16 & 0xff),
						(ipAddress >> 24 & 0xff));
				if(ipAddress==0)
					ip = "未接入wifi网络";
				tv_hostIPAddress.setText(ip);
				//turn on socket in udp mode
				DatagramSocket udpSocket = new DatagramSocket(4567);
				byte buffer[] = new byte[32];
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				while (true) {
					udpSocket.receive(packet);
					byte[] data = packet.getData();
					for(int count=0;count<data.length;count++){
						Log.e("data", ""+data[count]);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
