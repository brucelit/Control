package com.zhou.controlclient;


public class Util {

	//int转byte数组
	public static byte[] intToBytes(int value) {
		byte[] src = new byte[1];
		for (int i = 0; i <= 1; i++) {
			src[1 - i] = (byte) ((value >> 8 * i) & 0xFF);
		}
		return src;
	}
	
	//int转byte
	public static byte intToByte(int value) {
		byte src = (byte) (value&0xFF);
		return src;
	}
	
	//发送时封装帧
	public static byte[] packageToSend(byte data) {
		byte[] buffer = new byte[5];
		//0 1 位为起始位
		buffer[0] = (byte) (0xAA);
		buffer[1] = (byte) (0xBB);
		//2 3 位为数据域
		buffer[2] = (byte)(0x01);
		buffer[3] = data;
		buffer[4] = 0x00;
		for(int count=2;count<4;count++){
			buffer[4] = (byte) (buffer[4]^buffer[count]);
		}
		return buffer;
	}
	
	//接收时将帧解码
	public static int DecodeToInt(byte[] data){
		int dataInt=0;
		if(data[0]==(byte)0xAA && data[1]==(byte)0xBB){
			if((data[2]^data[3])==data[4]){
				if(data[2]==0x01){
					dataInt = Util.ByteToInt(data[3]);
				}
			}
		}
		return dataInt;
	}
	
	//将byte转换为int型
	public static int ByteToInt(byte src){
		int value = src&0xFF;
		return value;
	}
	
}
