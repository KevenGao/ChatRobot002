package com.chatRobot.Socket;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.UnknownHostException;


import com.chatRobot.serialPort.SerialTool;
import gnu.io.SerialPort;


public class SendSV extends Thread{
    private SerialPort serialPort;


    public SendSV(SerialPort serialPort)
    {
        this.serialPort = serialPort;

    }

    @Override
    public void run() {
        DatagramPacket packet;
        DatagramSocket socket;
        while(true) {
            try {
                // InetAddress address = InetAddress.getLocalHost();
                int port = 9998;
                Thread.sleep(50);
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

