package com.chatRobot.Socket;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.Map;

import com.chatRobot.serialPort.SerialTool;
import gnu.io.SerialPort;


public class Sv  extends Thread {
    private SerialPort serialPort;
    private Map<String,String> backMap;

    public Sv(SerialPort serialPort,Map<String,String> backMap)
    {
        this.serialPort = serialPort;
        this.backMap=backMap;
    }

    @Override
    public void run() {
        InetAddress clientAddress=null;
        int clientPort =0;
        DatagramPacket packet;
        DatagramSocket socket;
        while(true) {
            try {
                // InetAddress address = InetAddress.getLocalHost();
                int port = 9999;

                //创建DatagramSocket对象
                socket = new DatagramSocket(port);
                System.out.println("----建立服务器----");
                byte[] buf = new byte[1024];  //定义byte数组
                packet = new DatagramPacket(buf, buf.length);  //创建DatagramPacket对象
                System.out.println("等待客户端连接....");
                socket.receive(packet);  //通过套接字接收数据
                String getMsg = new String(buf, 0, packet.getLength());
                System.out.println("客户端发送的数据为：" + getMsg);
                char ch[]=getMsg.toCharArray();
                switch(ch[0]) {
                    case '0':
                        SerialTool.sendMsg(serialPort,"0");
                        break;
                    case '1':
                        SerialTool.sendMsg(serialPort,"1");
                        break;
                    //case '2':
                    //SerialTool.sendMsg(serialPort,"2");
                    //break;
                }
                Thread.sleep(500);
                //从服务器返回给客户端数据

                clientAddress = packet.getAddress(); //获得客户端的IP地址
                clientPort = packet.getPort(); //获得客户端的端口号
                SocketAddress sendAddress = packet.getSocketAddress();
                String backMsg = backMap.get("light")+"-"+backMap.get("temp")+"-"+backMap.get("shidu")+"-"+backMap.get("Rain")+"-"+backMap.get("gas");
                byte[] backbuf = backMsg.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(backbuf, backbuf.length, sendAddress); //封装返回给客户端的数据
                socket.send(sendPacket);  //通过套接字反馈服务器数据
                socket.close();  //关闭套接字




            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch(IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
