package com.ltt.controlclient;


import android.util.Log;

public class Util {

	// int转byte数组
	public static byte[] intToBytes(int value) {
		byte[] src = new byte[2];
		for (int i = 0; i < 2; i++) {
			src[1-i] = (byte) ((value >>8*i) & 0xFF);
		}
		return src;
	} 

	// int转byte
	public static byte intToByte(int value) {
		byte src = (byte) (value & 0xFF);
		return src;
	}

	// 发送时封装帧
	public byte[] packageToSend(int equipNum,int send_data,int send_flat) {
		byte[] c = new byte[11];
		switch (send_flat) {
		case 1:
			c[5] = (byte) (0x00);
			c[6] = (byte) (0x01);
			Log.e("seekBar_actuator","true");
			break;
		case 2:
			c[5] = (byte) (0x00);
			c[6] = (byte) (0x02);
			Log.e("seekBar_actuator_P","true");
			break;
		case 3: 
			c[5] = (byte) (0x00);
			c[6] = (byte) (0x03);
			Log.e("seekBar_actuator_I","true");
			break;
		case 4:
			c[5] = (byte) (0x00);
			c[6] = (byte) (0x04);
			Log.e("seekBar_actuator_D","true");
			break;
		case 5:
			c[5] = (byte) (0x00);
			c[6] = (byte) (0x05);
			Log.e("seekBar_motor","true");
			break;
		case 6:
			c[5] = (byte) (0x00);
			c[6] = (byte) (0x06);
			Log.e("seekBar_motor_P","true");
			break;
		case 7:
			c[5] = (byte) (0x00);
			c[6] = (byte) (0x07);
			Log.e("seekBar_motor_I","true");
			break;
		case 8:
			c[5] = (byte) (0x00);
			c[6] = (byte) (0x08);
			Log.e("seekBar_motor_D","true");
			break;
		case 9:
			c[5] = (byte) (0x00);
			c[6] = (byte) (0x05);
			Log.e("forward_button initiated","true");
			break;
		case 10:
			c[5] = (byte) (0x00);
			c[6] = (byte) (0x05);
			Log.e("backward_button initiated","true");
			break;
		case 11:
			c[5] = (byte) (0x00);
			c[6] = (byte) (0x05);
			Log.e("stop_button initiated","true");
			break;
		case 12:
			c[5] = (byte) (0x00);
			c[6] = (byte) (0x01);
			Log.e("left_button initiated","true");
			break;
		case 13:
			c[5] = (byte) (0x00);
			c[6] = (byte) (0x01);
			Log.e("right_button initiated","true");
			break;
		default:
			break;
		}
		// String message1 = textView1.getText().toString().trim();
		// System.out.println(message1);
		byte[] f = intToBytes(equipNum);
		c[0] = (byte) (0xAA);
		c[1] = (byte) (0xBB);
		c[2] = (byte) (0x08);
		c[3] = (byte) f[1];
		c[4] = (byte) (0x04);
		byte[] d = intToBytes(send_data);
		c[7] =  d[0];
		c[8] =  d[1];
		c[9] = (byte) (c[2] ^ c[3] ^ c[4] ^ c[5] ^ c[6] ^ c[7] ^ c[8]);
		return c;
}
	

	// 接收时将帧解码
	public static int[] DecodeToInt(byte[] data) {
		int i = 0;
		//若读到帧头则开始处理，否则
		while (!(data[i] == (byte) 0xAA && data[i + 1] == (byte) 0xBB)) {
			i++;
		}
		int[] dataInt = new int[2];
		if ((data[i + 2] ^ data[i + 3] ^ data[i + 4] ^ data[i + 5] ^ data[i + 6]) == data[i + 7]) {
			if (data[i + 2] == 0x02) {
				dataInt[0] = Util.ByteToInt(data[i + 3]);
				dataInt[1] = 100 * Util.ByteToInt(data[i + 4]) + 10
						* Util.ByteToInt(data[i + 5])
						+ Util.ByteToInt(data[i + 6]);
			}
		}
		return dataInt;
	}

	// 将byte转换为int型
	public static int ByteToInt(byte src) {
		int value = src & 0xFF;
		return value;
	}

}
