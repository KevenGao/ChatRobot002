package com.chatRobot.serialPort;
import java.awt.Color;
import java.awt.Font;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;


/**
 * @author bh
 * 如果运行过程中抛出 java.lang.UnsatisfiedLinkError 错误，
 * 请将rxtx解压包中的 rxtxParallel.dll，rxtxSerial.dll 这两个文件复制到 C:\Windows\System32 目录下即可解决该错误。
 */
public class WellcomView {

    /** 程序界面宽度*/
    public static final int WIDTH = 800;
    /** 程序界面高度*/
    public static final int HEIGHT = 620;
    /** 程序界面出现位置（横坐标） */
    public static final int LOC_X = 200;
    /** 程序界面出现位置（纵坐标）*/
    public static final int LOC_Y = 70;

    /**主方法
     * @param args  //
     */
    public static void main(String[] args) {
        new SerialTest();
        //new SerialView() ;
    }


}
