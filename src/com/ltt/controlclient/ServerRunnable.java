package com.ltt.controlclient;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class ServerRunnable implements Runnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	public void run(int equiNum,int send_data,int send_flat) {
		// TODO Auto-generated method stub
		try {   
			DatagramSocket socket = new DatagramSocket(5678);		
			String ip2="192.168.7.255";
			InetAddress serverAddress = InetAddress.getByName(ip2);
			Util utility=new Util();
			byte data[] =utility.packageToSend(equiNum,send_data,send_flat);			
			DatagramPacket packet = new DatagramPacket(data, data.length,
					serverAddress, 4567);
			Log.e("Runnable started","true");
			socket.send(packet);
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	 public void WifiAdmin(Context context)  
	    {  
	        //取得WifiManager对象  
	        WifiManager wifiManager= (WifiManager) context.getSystemService(Context.WIFI_SERVICE);  
	        //取得WifiInfo对象  
	        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
	    } 
}
