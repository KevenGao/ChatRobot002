package com.chatRobot.serialPort;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.TooManyListenersException;

import com.chatRobot.Socket.SendSV;
import com.chatRobot.Socket.Sv;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;




public class SerialTest implements SerialPortEventListener   {
    private static final String DEMONAME = "串口测试";
    private Map<String, String> map = new HashMap<String, String>();
    /**
     * 检测系统中可用的端口
     */
    private CommPortIdentifier portId=null;

    //输入输出流
    private static InputStream inputStream;
    private static OutputStream outputStream;

    //设置串行口
    private SerialPort serialPort;

    // 地址
    //public Map<String, String> dataAll = new HashMap<String, String>();

    public SerialTest() {
        init();
    }

    /**
     * 初始化串口
     * @param baudRate 波特率
     */
    public void init() {

        try {
            portId=CommPortIdentifier.getPortIdentifier("COM3");
            System.out.println("打开端口："+portId.getName());
            serialPort = (SerialPort) portId.open(DEMONAME,2000);
            //设置串口监听
            serialPort.addEventListener(this);
            //设置开启监听
            serialPort.notifyOnDataAvailable(true);
            //设置波特率、数据位、停止位、检验位
            serialPort.setSerialPortParams(115200,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
            //获取输入流
            inputStream = serialPort.getInputStream();
            outputStream=serialPort.getOutputStream();
            Thread thread1 =new Sv(serialPort,map);//启动服务器线程
            thread1.start();
            Thread thread2=new SendSV(serialPort);
            thread2.start();
        } catch (PortInUseException e) {
            System.out.println("端口被占用");
            e.printStackTrace();
        } catch (TooManyListenersException e) {
            System.out.println("串口监听类数量过多！添加操作失败！");
            e.printStackTrace();
        } catch (UnsupportedCommOperationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("输入出错");
            e.printStackTrace();
        } catch (NoSuchPortException e) {
            System.out.println("没有该端口");
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    /**
     * 监听函数
     */

    public void serialEvent(SerialPortEvent serialPortEvent) {
        switch (serialPortEvent.getEventType()) {
            case SerialPortEvent.BI: // 通讯中断
            case SerialPortEvent.OE: // 溢位错误
            case SerialPortEvent.FE: // 帧错误
            case SerialPortEvent.PE: // 奇偶校验错误
            case SerialPortEvent.CD: // 载波检测
            case SerialPortEvent.CTS: // 清除发送
            case SerialPortEvent.DSR: // 数据设备准备好
            case SerialPortEvent.RI: // 响铃侦测
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
                break;
            //获取到有效信息
            case SerialPortEvent.DATA_AVAILABLE :
                byte[] data = null;//FE0400030001D5C5
                //readPort();
                try {
                    data = SerialTool.readFromPort(serialPort);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                testRead(printHexString(data).replaceAll(" ", ""));
                break;

            default:
                break;
        }
    }

    /**字节数组转16进制
     * @param b
     * @return
     */
    private String printHexString(byte[] b) {

        StringBuffer sbf=new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sbf.append(hex.toUpperCase()+"  ");
        }
        return sbf.toString().trim();
    }
    /**一个解析16进制的测试函数
     */
    private void testRead(String sljz) {


        String rain="";

        String light="";

        String temp="";

        String shidu="";

        String control="";

        String Gas="";

        String x=sljz.substring(0, 10);

        String y=sljz.substring(10,14);

        if(x.equals("02071800F1")&y.equals("855A")){

            rain=sljz.substring(16,20);
            if(rain.equals("0030")) {

                System.out.println("管道情况情况良好");
                map.put("Rain","ganzao");
            }else if(rain.equals("0131")) {
                System.out.println("出现漏水");
                map.put("Rain","loushui");
                SerialTool.sendMsg(serialPort,"2");
            }

        }

        if(x.equals("02071800F1")&y.equals("3ED3")){

            control=sljz.substring(16,18);
            map.put("control",control);
            System.out.println("control:"+control);

        }

        if(x.equals("02081800F1")&&y.equals("7A0B")){

            light=sljz.substring(16,20);
            map.put("light",String.valueOf(exchange(light)));
            System.out.println("light:"+exchange(light));

        }

        if(x.equals("02081800F1")&&y.equals("ABBA")){

            if(sljz.substring(14, 16).equals("01")){

                temp=sljz.substring(16,20);

                System.out.println("temp:"+exchange(temp));
                map.put("temp",String.valueOf(exchange(temp)));

            }

            if(sljz.substring(14, 16).equals("02")){

                shidu=sljz.substring(16,20);
                map.put("shidu",String.valueOf(exchange(shidu)));
                System.out.println("humi:"+exchange(shidu));
            }
        }
        if(x.equals("02071800F1")&y.equals("D23D")){

            Gas=sljz.substring(16,18);
            map.put("gas",Gas);
            if(Gas.equals("01")) {
                System.out.println("燃气泄漏");
                SerialTool.sendMsg(serialPort,"4");
                map.put("gas","Warnning!find Gas");
            }

            System.out.println("Gas:"+"Warnning!find Gas");

        }
    }
    /**
     * 交换温湿度
     **/
    public double exchange(String string){

        String a=string.substring(0,2);

        String b=string.substring(2,4);

        String temp="";

        temp=a;

        a=b;

        b=temp;

        return Integer.parseInt(a+b,16)/100.00;

    }
    // 关闭串口
    public void closeSerialPort() {
        if (serialPort != null) {

            serialPort.notifyOnDataAvailable(false);
            serialPort.removeEventListener();

            if (inputStream != null) {
                try {
                    inputStream.close();
                    inputStream = null;

                }
                catch (IOException e) {
                    System.out.println("hhh");
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                    outputStream = null;
                }
                catch (IOException e) {}
            }
            serialPort.close();
            serialPort = null;
        }
    }
    public Map<String,String> mapPrint() {

        System.out.println("修改值温度"+map.get("temp")+"修改值光照"+map.get("light"));
        return null;
    }




}


