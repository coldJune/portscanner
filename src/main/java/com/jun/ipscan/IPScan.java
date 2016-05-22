package com.jun.ipscan;

import java.io.*;

import java.net.*;

import java.util.*;
/**
 * 负责扫描IP地址
 * @author jun
 *@date 2016/5/15
 */
public class IPScan {
	Socket socket;
	String startIP;
	String endIP;
	ArrayList<Integer> portArray = new ArrayList<Integer>();
	/*
	 * 添加构造函数
	 */
	public IPScan(){
		
	}
	
	public IPScan(String startIP,String edString,int port){
		this.startIP=startIP;
		this.endIP=endIP;
		
	}
	public IPScan(String startIP){
		this.startIP=startIP;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public String getStartIP() {
		return startIP;
	}

	public void setStartIP(String startIP) {
		this.startIP = startIP;
	}

	public String getEndIP() {
		return endIP;
	}

	public void setEndIP(String endIP) {
		this.endIP = endIP;
	}

	public ArrayList<Integer> getPortArray() {
		return portArray;
	}

	public void setPortArray(ArrayList<Integer> portArray) {
		portArray.add(80);//探测80端口
		portArray.add(8080);
	}
	
	public synchronized void plusIP(){
		String[] ipcodes = startIP.split("[.]");
		int temp = Integer.parseInt(ipcodes[3]);
		temp++;
		startIP=ipcodes[0]+"."+ipcodes[1]+"."+ipcodes[2]+"."+temp;
		System.out.println(startIP);
	}
	
}
