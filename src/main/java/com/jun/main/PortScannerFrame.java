package com.jun.main;

import java.awt.BorderLayout;
import java.awt.Color;  
import java.awt.GridLayout; 
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException; 
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;  
import javax.swing.*;   
public class PortScannerFrame extends JFrame implements ActionListener{ 
	/**
	 * 
	 */
	private static final long serialVersionUID = 2478209191933410503L;
	JTabbedPane jtp = new JTabbedPane();//卡片布局  
JPanel ipPanel = new JPanel();
JPanel portPanel = new JPanel();   
JTextField ipField = new JTextField("172.18.60.1",20);

JLabel label1 = new JLabel("请输入起始ip");  
JTextField ipField2= new JTextField("172.18.60.100",20);  
JLabel label2 = new JLabel("请输入终止ip");  
JButton startButton = new JButton("开始");  
JTextArea jta0 = new JTextArea("在该网段下使用的ip地址:\n");  
JTextArea jta1 = new JTextArea("在该网段下未使用的ip地址：\n");//ip扫描结果    
//端口扫描部分 
JLabel label3 = new JLabel("请输入要扫描的主机的ip地址：");
JTextField ipFieldForPortScan = new JTextField("127.0.0.1",20);  
JButton startPortScan = new JButton("开始端口扫描");    
JTextArea jta2 = new JTextArea("目标主机开放的端口:\n");//显示端口扫描结果，此处显 示开放的端口  
JTextArea jta3 = new JTextArea("目标主机未开放的端口:\n");  
JLabel label4 = new JLabel("请输入开始端口：");  
JLabel label5 = new JLabel("请输入终止端口：");   
JTextField startPort = new JTextField("8050",10);  
JTextField endPort = new JTextField("8080",10);   
IPScan ipscan = new IPScan();//扫描ip地址是否在该网段下使用  
PortScan portscan = new PortScan();        

public PortScannerFrame(){ 
	init();  
	ipScaninit();  
	portScanInit();  
	}  
public synchronized IPScan plusIP(){   
	IPScan ipscan = new IPScan();  
	String[] ipcodes = this.ipscan.startIP.split("[.]"); 
	int temp = Integer.parseInt(ipcodes[3]);   
	temp ++;    
	this.ipscan.startIP = ipcodes[0] + "." + ipcodes[1] + "." + ipcodes[2] + "." + temp;   //System.out.println(startIP);    
ipscan.setStartIP(this.ipscan.getStartIP());     
return ipscan;   
}    
private void portScanInit(){    
	jtp.add(portPanel,"port扫描");  
	JPanel portPanelNorth = new JPanel();   
	JPanel portPanelCenter = new JPanel();    
	JScrollPane jsp1 = new JScrollPane(jta2);

JScrollPane jsp2 = new JScrollPane(jta3);       
portPanelCenter.setLayout(new GridLayout(0,2));      
portPanelNorth.setLayout(new GridLayout(0,2));   
portPanel.setLayout(new BorderLayout());   
portPanelNorth.add(label3);   
portPanelNorth.add(ipFieldForPortScan); 
portPanelNorth.add(label4); 
portPanelNorth.add(startPort); 
portPanelNorth.add(label5); 
portPanelNorth.add(endPort);    
portPanelNorth.add(startPortScan);    
portPanel.add(portPanelNorth,BorderLayout.NORTH);  

portPanelCenter.add(jsp1);    portPanelCenter.add(jsp2);       
portPanel.add(portPanelCenter);    
startPortScan.addActionListener(this);   
startPort.addActionListener(this);   
}      
private void ipScaninit(){  
	jtp.add(ipPanel,"ip扫描");  
	ipPanel.setLayout(new BorderLayout());      
	JPanel panelTop = new JPanel();    
	JPanel panelCenter = new JPanel();    
	panelCenter.setLayout(new GridLayout(0,2));  
	panelTop.add(label1);   
	panelTop.add(ipField); 
	panelTop.add(label2);  
	
	panelTop.add(ipField2);  
panelTop.add(startButton);      
JScrollPane jsp1 = new JScrollPane(jta0);   
JScrollPane jsp2 = new JScrollPane(jta1);       
panelCenter.add(jsp1);   
panelCenter.add(jsp2);    
ipPanel.add(panelTop,BorderLayout.NORTH);
ipPanel.add(panelCenter,BorderLayout.CENTER); 

startButton.addActionListener(this); 
}    
private void init(){   //
	ipPanel.setLayout(new GridLayout(0,2));     
	this.add(jtp);    
	this.setResizable(false);
	this.setVisible(true);   
	this.setSize(800, 600);    
	this.setTitle("端口扫描器");    
	this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);  
	this.setBackground(Color.cyan);  
	this.setLocationRelativeTo(null); 
 }  
public int calculateIPCount(){   
	String[] ipCodes = this.ipscan.getStartIP().split("[.]");   
	int temp = Integer.parseInt(ipCodes[3]);    
	String[] ipCodes1 = this.ipscan.getEndIP().split("[.]");  
	int temp1 = Integer.parseInt(ipCodes1[3]);  
	return temp1 - temp;   
	}    
public void actionPerformed(ActionEvent e) {   // TODO Auto-generated method stub   
		if(e.getSource() == startButton){    //点击开始扫描ip地址   
			int ipNum = 50;          
			ipscan.setStartIP(ipField.getText());   
			ipscan.setEndIP(ipField2.getText());        
			ipNum = this.calculateIPCount();         
			ExecutorService executor = Executors.newFixedThreadPool(5);    
			for(int i = 0;i < ipNum;i ++)      
				executor.execute(new IpScanThread());       
			}    
		if(e.getSource() == this.startPortScan){     
			portscan.setIp(ipFieldForPortScan.getText().trim());
			portscan.setPort(Integer.parseInt(startPort.getText()));//设置起始扫描端口    
			portscan.setEndPort(Integer.parseInt(endPort.getText()));    //portscan.start(jta2);     
			new Thread(new PortScanThread()).start();   
			}      
if(e.getSource() == this.startPort){    
	System.exit(0);    
	}  
}      
class PortScanThread implements Runnable{       
	public void run() {    // TODO Auto-generated method stub    
				startScan();   
				}      
public void startScan(){//调用该方法开始扫描   
while(portscan.getPort() <= portscan.getEndPort()){     
	try {     
		Socket socket = new Socket(portscan.getIp(),portscan.getPort());      
		if(socket.isConnected()){     
			jta2.append(portscan.getPort() + "\n");     
			}     
		} catch (UnknownHostException e) {      // TODO Auto-generated catch block      
			e.printStackTrace();     
			} catch (IOException e) {   
				// TODO Auto-generated catch block      
			}
				jta3.append(portscan.getPort() + "\n");   
				portscan.setPort(portscan.getPort() + 1);   
		}          
 
}   
}   
class IpScanThread implements Runnable{            
	IPScan ipscan;

			  public IpScanThread(){       
				  ipscan = plusIP();   
				  }         
			  public void run() {      
				  startScan();    
				  }     
			  private void startScan() {    // TODO Auto-generated method stub  
				  int port;   
				  Socket socket = new Socket();   
				  for(int i = 0;i < ipscan.portArray.size();i ++){  
					  port = ipscan.portArray.get(i);       
					  try {      
						  socket.close();     
						  socket = new Socket(ipscan.getStartIP(),port);   
						  if(socket.isConnected()){         
							  jta0.append("" + ipscan.getStartIP() + "\n");        
							  return;     
							  }           
						  } catch (UnknownHostException e) {      // TODO Auto-generated catch block       
					 
					  e.printStackTrace();     
					  } catch (IOException e) {      // TODO Auto-generated catch block      
						  }    
					  }    
				  jta1.append("" + ipscan.getStartIP() + "\n");   
				  }    
			  }    

}
			
			  