package com.zhou.controlclient;

public class Util {

	//int×ªbyteÊý×é
	public static byte[] intToBytes(int value) {
		byte[] src = new byte[2];
		for (int i = 0; i <= 1; i++) {
			src[1 - i] = (byte) ((value >> 8 * i) & 0xFF);
		}
		return src;
	}
	
}
