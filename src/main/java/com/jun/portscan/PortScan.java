package com.jun.portscan;

import java.net.*;
/**
 * 扫描端口
 * @author 笨蛋
 *
 */


public class PortScan {
	String ip;
	Socket socket;
	int starPort=0;
	int endPort=65535;
	
	public PortScan(){
		this.ip="127.0.0.1";
		this.endPort=8000;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public int getStarPort() {
		return starPort;
	}

	public void setStarPort(int starPort) {
		this.starPort = starPort;
	}

	public int getEndPort() {
		return endPort;
	}

	public void setEndPort(int endPort) {
		this.endPort = endPort;
	}


	
}
