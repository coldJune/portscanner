package com.jun.frame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOError;
import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.jun.ipscan.IPScan;
import com.jun.portscan.PortScan;

public class PortScannerFrame extends JFrame implements ActionListener{
	JTabbedPane jtp = new JTabbedPane();
	JPanel ipJPanel = new JPanel();
	JPanel portJpanel = new JPanel();
	JTextField ipField = new JTextField("192.168.1.100",20);
	JLabel label1 = new JLabel("请输入起始ip");
	JTextField ipField2 = new JTextField("192.168.1.130",20);
	JLabel label2 = new JLabel("请输入终止ip");
	JButton startButton = new JButton("开始");
	JTextArea jta0 = new JTextArea("在该网段下使用的ip地址:\n");
	JTextArea jta1 = new JTextArea("在该网段下未使用的ip地址:\n");//ip扫描结果
	
	//端口扫描部分
	JLabel label3 = new JLabel("请输入要扫描的主机的ip地址:");
	JTextField ipFiledForPortScan = new JTextField("127.0.0.1",20);
	JButton startPortScan = new JButton("开始扫描端口");
	JTextArea jta2 = new JTextArea("目标主机开放的端口");
	JTextArea jta3 = new JTextArea("目标主机未开放的端口");
	JLabel label4 = new JLabel("请输入开始端口:");
	JLabel label5 = new JLabel("请输入终止端口:");
	JTextField startPort = new JTextField("8000",10);
	JTextField endPort = new JTextField("8080",10);
	IPScan ipScan = new IPScan();
	PortScan portScan = new PortScan();
	
	public PortScannerFrame(){
		init();
		ipScanInit();
		portScanInit();
	}
	
	public synchronized IPScan plusIP(){
		IPScan ipscan = new IPScan();
		String[] ipcodes = this.ipScan.getStartIP().split("[.]");
		int temp = Integer.parseInt(ipcodes[3]);
		temp++;
		String tmp;
		tmp=ipcodes[0]+"."+ipcodes[1]+"."+ipcodes[2]+"."+temp;
		ipscan.setStartIP(tmp);
		return ipscan;
	}
	
	private void portScanInit(){
		jtp.add(portJpanel, "port扫描");
		JPanel portPanelNorth = new JPanel();
		JPanel portPanelCenter = new JPanel();
		JScrollPane jsp1=new JScrollPane(jta2);
		JScrollPane jsp2 = new JScrollPane(jta3);
		
		portPanelCenter.setLayout(new GridLayout(0, 2));
		
		portPanelNorth.setLayout(new GridLayout(0, 2));
		
		portJpanel.setLayout(new BorderLayout());
		
		portPanelNorth.add(label3);
		portPanelNorth.add(ipFiledForPortScan);
		portPanelNorth.add(label4);
		portPanelNorth.add(startPort);
		portPanelNorth.add(label5);
		portPanelNorth.add(endPort);
		portPanelNorth.add(startPortScan);
		portJpanel.add(portPanelNorth,BorderLayout.NORTH);
		
		portJpanel.add(portPanelCenter);
		startPortScan.addActionListener(this);
		startPort.addActionListener(this);
			
			
		}
	private void ipScanInit(){
		jtp.add(ipJPanel,"ip扫描");
		ipJPanel.setLayout(new BorderLayout());
		
		JPanel panelTop = new JPanel();
		JPanel panelCenter = new JPanel();
		panelCenter.setLayout(new GridLayout(0, 2));
		panelTop.add(label1);
		panelTop.add(ipField);
		panelTop.add(label2);
		panelTop.add(ipField2);
		panelTop.add(startButton);
		
		JScrollPane jsp1 = new JScrollPane(jta0);
		JScrollPane jsp2 =new JScrollPane(jta1);
		
		panelCenter.add(jsp1);
		panelCenter.add(jsp2);
		
		ipJPanel.add(panelTop,BorderLayout.NORTH);
		ipJPanel.add(panelCenter, BorderLayout.CENTER);
		
		startButton.addActionListener(this);
	}
	
	private void init(){
		this.add(jtp);
		this.setResizable(false);
		this.setVisible(true);
		this.setSize(800, 600);
		this.setTitle("端口扫描");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setBackground(Color.pink);
		this.setLocationRelativeTo(null);
	}
	
	public int calculateIPCount(){
		String[] ipCodes = this.ipScan.getStartIP().split("[.]");
		int temp = Integer.parseInt(ipCodes[3]);
		String[] ipCode = this.ipScan.getEndIP().split("[.]");
		int temp1 = Integer.parseInt(ipCode[3]);
		return temp1-temp;
	}
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==startButton){
			int ipNum=50;
			
			ipScan.setStartIP(ipField.getText());
			ipScan.setEndIP(ipField2.getText());
			
			ipNum=this.calculateIPCount();
			
			ExecutorService executor = Executors.newFixedThreadPool(5);
			for(int i=0;i<ipNum;i++){
				executor.execute(new IsScanThread());
			}
			if(e.getSource()==this.startPortScan){
				portScan.setIp(ipFiledForPortScan.getText().trim());
				portScan.setStarPort(Integer.parseInt(startPort.getText()));
				portScan.setEndPort(Integer.parseInt(endPort.getText()));
				new Thread(new PortScanThread()).start();
			}
			if(e.getSource()==this.startPort){
				System.exit(0);
			}
		}
		
		
	}
	
	class PortScanThread implements Runnable{

		public void run() {
			// TODO Auto-generated method stub
			startScan();
		}
		
		public void startScan() {
			while(portScan.getStarPort()<=portScan.getEndPort()){
				try {
					Socket socket = new Socket(portScan.getIp(), portScan.getStarPort());
					if(socket.isConnected()){
						jta2.append(portScan.getStarPort()+"\n");
					}
				} catch (UnknownHostException e) {
					// TODO: handle exception
					e.printStackTrace();
				}catch (IOException e) {
					// TODO: handle exception
					jta3.append(portScan.getStarPort()+"\n");
				}
				portScan.setStarPort(portScan.getStarPort()+1);
			}
		}
	}
	class IsScanThread implements Runnable{
		IPScan ipscan;
		public IsScanThread() {
			ipscan=plusIP();
		}
		public void run() {
			// TODO Auto-generated method stub
			int port;
			Socket socket = new Socket();
			for(int i=0;i<ipscan.getPortArray().size();i++){
				port=ipscan.getPortArray().get(i);
					try {
						socket.close();
						socket=new Socket(ipscan.getStartIP(), port);
						if(socket.isConnected()){
							jta0.append(""+ipscan.getStartIP()+"\n");
							return;
						}
					} catch (UnknownHostException e) {
						// TODO: handle exception
						e.printStackTrace();
					}catch (IOException e) {
						// TODO: handle exception
						
					}
					jta1.append(""+ipscan.getStartIP()+"\n");
			}
		}
		
	}
}
