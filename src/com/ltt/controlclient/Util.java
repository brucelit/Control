package com.zhou.controlclient;


public class Util {

	//intתbyte����
	public static byte[] intToBytes(int value) {
		byte[] src = new byte[1];
		for (int i = 0; i <= 1; i++) {
			src[1 - i] = (byte) ((value >> 8 * i) & 0xFF);
		}
		return src;
	}
	
	//intתbyte
	public static byte intToByte(int value) {
		byte src = (byte) (value&0xFF);
		return src;
	}
	
	//����ʱ��װ֡
	public static byte[] packageToSend(byte data) {
		byte[] buffer = new byte[5];
		//0 1 λΪ��ʼλ
		buffer[0] = (byte) (0xAA);
		buffer[1] = (byte) (0xBB);
		//2 3 λΪ������
		buffer[2] = (byte)(0x01);
		buffer[3] = data;
		buffer[4] = 0x00;
		for(int count=2;count<4;count++){
			buffer[4] = (byte) (buffer[4]^buffer[count]);
		}
		return buffer;
	}
	
	//����ʱ��֡����
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
	
	//��byteת��Ϊint��
	public static int ByteToInt(byte src){
		int value = src&0xFF;
		return value;
	}
	
}
