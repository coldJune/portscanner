package com.jun.main;


import java.net.Socket; 

import java.util.ArrayList;  
 
public class IPScan {  Socket socket;  String startIP; 
 String endIP; 
 ArrayList<Integer> portArray = new ArrayList<Integer>();  
 public IPScan(){   
	 this.setPortArray();  
	 } 
 public IPScan(String startIP,String endIP,int port){   
 
this.startIP = startIP; 
  this.endIP = endIP; 
  }   
 public IPScan(String startIP){    
this.startIP = startIP; 
 } 
  
 public ArrayList<Integer> getPortArray() {   return portArray;  }   
 public void setPortArray() { 
	 portArray.add(80);   
	 //portArray.add(135);   
	 //portArray.add(443);  
	 portArray.add(445);   
	 //portArray.add(843);  
	 //portArray.add(902);  
	 //portArray.add(912);   
portArray.add(8080); 
 }   
 
public synchronized void plusIP(){ 
  String[] ipcodes = startIP.split("[.]"); 
  int temp = Integer.parseInt(ipcodes[3]);   temp ++; 
  startIP = ipcodes[0] + "." + ipcodes[1] + "." + ipcodes[2] + "." + temp;  
  System.out.println(startIP); 
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
}  
