package com.jun.main;


import java.net.*;    
 
public class PortScan { 
	String ip;   
	Socket socket;   
	int port =0,endPort = 65525;
	public PortScan(){   
		this.ip = "127.0.0.1";   
		this.port = 8000;   
		}    
 public String getIp() { 
	 return ip;  
	 }   
 public void setIp(String ip) { 
	 this.ip = ip; 
 }    
 public int getPort() {   
	 return port; 
	 }   
 public void setPort(int port) {   
	 this.port = port;  
	 }     
 public int getEndPort() { 
	 return endPort; 
	 }    
 public void setEndPort(int endPort) { 
	 this.endPort = endPort; 
	 }   
 }