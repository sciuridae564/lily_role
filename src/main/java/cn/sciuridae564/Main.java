package cn.sciuridae564;

import cn.sciuridae564.frame.main;
import cn.sciuridae564.util.sqLite.DB;

import javax.swing.*;

public class Main {



    public static void main(String[] args) {
        DB.getInstance().init();

        JFrame frame = new JFrame();
        main plan=new main();
        frame.setContentPane(plan);
        //关闭窗口时退出程序
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //调整窗口大小
        frame.setSize(1400,890);
        frame.setLocation(50,5);
        //显示窗口（放到程序后面，否则窗口可能显示不出来）
        frame.setVisible(true);



    }





}