/*
 * Created by JFormDesigner on Thu May 11 10:16:46 CST 2023
 */

package cn.sciuridae564.frame;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.*;

import cn.sciuridae564.pojo.Role;
import cn.sciuridae564.pojo.Save;
import cn.sciuridae564.util.getUrlImage;
import cn.sciuridae564.util.sqLite.DB;
import net.miginfocom.swing.*;
import org.apache.commons.io.FileUtils;

import static cn.sciuridae564.util.getUrlImage.file_dir;
import static cn.sciuridae564.util.getUrlImage.file_out;

/**
 * @author 1
 */
public class main extends JPanel {
    public Boolean ready_url = true;
    //图片下标
    private int imagenum;
    //原文件
    private ArrayList<File> fileList = new ArrayList<>();
    //领袖列表
    ArrayList<String> leaderList = new ArrayList<>();
    //映射列表
    Map<String,List<String>> mapperMap = new HashMap<>();

    public main() {
        initComponents();
        Save save = DB.getInstance().getSave();
        if(save == null){
            file_dir = System.getProperty("user.dir") + "\\al_icon\\";
            file_out = System.getProperty("user.dir") + "\\al_icon_out\\";
            DB.getInstance().insertsave(file_dir,file_out);
        }
        WebUrlIn.setText(file_dir);
        OutUrlIn.setText(file_out);
        WebUrlIn.setEditable(false);
        OutUrlIn.setEditable(false);
        initList();
        getFileImageList();
        if(fileList.size()>0){
            setImage(fileList.get(0).getAbsolutePath(),leaderMapper);
            webImageName.setText(fileList.get(0).getName());
            this.setWebLabel("");
        }else {
            this.setWebLabel("标记完了");
            webUrl.setIcon(null);
        }
        if(fileList.size() != 0){
            process.setText(imagenum +1+"/"+fileList.size());
        }else {
            process.setText("0/0");
        }

    }

    private void webMouseClicked(MouseEvent e) {
        if(ready_url){ ready_url = false; getUrlImage.get(this); this.setWebLabel("正在爬"); }
    }

    private void subMouseClicked(MouseEvent e) {
        //getFileImageList();
        if(fileList.size() == 0){
            setWebLabel("标记完了");
            webUrl.setIcon(null);
            return;
        }
        if(imagenum < 0||imagenum >= fileList.size()){
            imagenum = 0;
        }
        imagenum = (imagenum-1+fileList.size())%fileList.size();
        setImage(fileList.get(imagenum).getAbsolutePath(),leaderMapper);
        webImageName.setText(fileList.get(imagenum).getName());
        if(fileList.size() != 0){
            process.setText(imagenum +1+"/"+fileList.size());
        }else {
            process.setText("0/0");
        }
    }

    private void addMouseClicked(MouseEvent e) {
        if(fileList.size() == 0){
            setWebLabel("标记完了");
            webUrl.setIcon(null);
            return;
        }
        if(imagenum < 0||imagenum >= fileList.size()){
            imagenum = 0;
        }
        imagenum = (imagenum+1)%fileList.size();
        setImage(fileList.get(imagenum).getAbsolutePath(),leaderMapper);
        webImageName.setText(fileList.get(imagenum).getName());
        this.setWebLabel("");
        if(fileList.size() != 0){
            process.setText(imagenum +1+"/"+fileList.size());
        }else {
            process.setText("0/0");
        }
    }

    private void PinMouseClicked(MouseEvent e) {
        if(leaderListUi.getSelectedValue() != null){
            String fileName = fileList.get(imagenum).getAbsolutePath();
            fileName = fileName.substring(fileName.indexOf("CharacterJob"));
            //添加数据库存储
            DB.getInstance().insertfile_nameMapping((String) leaderListUi.getSelectedValue(),fileName);
            //更新映射列表
            mapperMap.get(leaderListUi.getSelectedValue()).add(fileName);

            setImage(file_dir+ fileName,mapperLabel);
            mapperListUi.setListData(mapperMap.get(leaderList.get(leaderListUi.getSelectedIndex())).toArray());
            mapperListUi.setSelectedIndex(mapperMap.get(leaderListUi.getSelectedValue()).size()-1);
            //删除当前图片,换新图
            fileList.remove(imagenum);
            getFileImageList();
            if(fileList.size() == 0){
                this.setWebLabel("标记完了");
                leaderMapper.setIcon(null);
                webImageName.setText("");
                process.setText("0/0");
                return;
            }
            if(imagenum == fileList.size()){
                imagenum = fileList.size()-1;
            }
            setImage(fileList.get(imagenum).getAbsolutePath(),leaderMapper);
            this.setWebLabel("");
            webImageName.setText(fileList.get(imagenum).getName());
            if(fileList.size() != 0){
                process.setText(imagenum +1+"/"+fileList.size());
            }else {
                process.setText("0/0");
            }
        }
    }

    private void leaderListUiMouseClicked(MouseEvent e) {
        mapperListUi.setListData(mapperMap.get(leaderList.get(leaderListUi.getSelectedIndex())).toArray());

        if(mapperMap.get(leaderList.get(leaderListUi.getSelectedIndex())).size()>0){
            mapperListUi.setSelectedIndex(0);
            setImage(file_dir +"\\" + mapperMap.get(leaderList.get(leaderListUi.getSelectedIndex())).get(0),mapperLabel);
        }else {
            mapperLabel.setIcon(null);
        }
    }

    private void mapperListUiMouseClicked(MouseEvent e) {
        ImageIcon icon=new ImageIcon();
        try {
            BufferedImage read = ImageIO.read(new File(file_dir+ mapperListUi.getSelectedValue()));
            Image resultingImage = read.getScaledInstance(300,450,Image.SCALE_AREA_AVERAGING);
            icon.setImage(resultingImage);
        } catch (IOException e1) {
            throw new RuntimeException(e1);
        }
        mapperLabel.setIcon(icon);
    }

    private void deleteMapperMouseClicked(MouseEvent e) {
        if(mapperListUi.getSelectedValue() != null){
            String selectedValue = (String)mapperListUi.getSelectedValue();
            DB.getInstance().clearfile_nameMapping(selectedValue);
            mapperMap.get(leaderListUi.getSelectedValue()).remove(mapperListUi.getSelectedValue());
            mapperListUi.setListData(mapperMap.get(leaderListUi.getSelectedValue()).toArray());
            if(mapperMap.get(leaderListUi.getSelectedValue()).size() > 0){
                int i = mapperListUi.getSelectedIndex();
                if(i >= mapperMap.get(leaderListUi.getSelectedValue()).size()){
                    i = mapperMap.get(leaderListUi.getSelectedValue()).size() -1;
                }
                mapperListUi.setSelectedIndex(i);
                setImage(file_dir+"\"" + mapperMap.get(leaderListUi.getSelectedValue()).get(i) ,mapperLabel);
            }else {
                mapperLabel.setIcon(null);
            }

            refrashWebImage();
            if(webUrl.getIcon() == null && fileList.size() > 0){
                setImage(file_dir+"\"" + fileList.get(0),webUrl);
                process.setText("1/" + fileList.size());
                imagenum = 0;
                webImageName.setText(fileList.get(imagenum).getName());
            }
        }
    }

    private void generateMouseClicked(MouseEvent e) {
        file_dir = WebUrlIn.getText() + "\\";
        file_out = OutUrlIn.getText() + "\\";
        File dir = new File(file_out);
        if (!dir.exists()){
            dir.mkdirs();
        }
        DB.getInstance().insertsave(WebUrlIn.getText(),OutUrlIn.getText());
        for (Map.Entry<String, List<String>> stringListEntry : mapperMap.entrySet()) {
            if(stringListEntry.getValue() != null && stringListEntry.getValue().size() > 0){
                int i = 0;
                for (String s : stringListEntry.getValue()) {
                    File in = new File(file_dir + s);
                    File file = new File(file_out+"\\"+stringListEntry.getKey()+"\\" + s);
                    if(!file.exists()){
                        try {
                            FileUtils.copyFile(in,file);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
        }

    }
    private static boolean hasLeader = true;
    private void LeaderupdateMouseClicked(MouseEvent e) {
        if(hasLeader){
            leader frame = new leader();
            //调整窗口大小
            frame.setSize(600,700);
            frame.setLocation(260,40);
            //显示窗口（放到程序后面，否则窗口可能显示不出来）
            frame.setVisible(true);
            hasLeader = false;
        }
    }

    public static void setHasLeader(boolean hasLeader) {
        main.hasLeader = hasLeader;
    }

    private void button1MouseClicked(MouseEvent e) {
        WebUrlIn.setEditable(!WebUrlIn.isEditable());
        OutUrlIn.setEditable(!OutUrlIn.isEditable());
    }

    private void refrashMouseClicked(MouseEvent e) {
        refrashWebImage();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        web = new JButton();
        webText = new JLabel();
        refrash = new JButton();
        generate = new JButton();
        webUrl = new JLabel();
        WebUrlIn = new JTextField();
        button1 = new JButton();
        OutUrl = new JLabel();
        OutUrlIn = new JTextField();
        Leaderupdate = new JButton();
        webImageName = new JLabel();
        textArea4 = new JLabel();
        textArea5 = new JLabel();
        deleteMapper = new JButton();
        leaderMapper = new JLabel();
        Pin = new JButton();
        scrollPane2 = new JScrollPane();
        leaderListUi = new JList();
        scrollPane3 = new JScrollPane();
        mapperListUi = new JList();
        mapperLabel = new JLabel();
        sub = new JButton();
        add = new JButton();
        process = new JLabel();

        //======== this ========
        setPreferredSize(new Dimension(1400, 850));
        setLayout(new MigLayout(
            "hidemode 3",
            // columns
            "[fill]" +
            "[160]" +
            "[160]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]",
            // rows
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]"));

        //---- web ----
        web.setText("\u4ece\u7f51\u7edc\u83b7\u53d6\u56fe\u7247");
        web.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                webMouseClicked(e);
            }
        });
        add(web, "cell 0 0");

        //---- webText ----
        webText.setText("...");
        add(webText, "cell 1 0");

        //---- refrash ----
        refrash.setText("\u5237\u65b0");
        refrash.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                refrashMouseClicked(e);
            }
        });
        add(refrash, "cell 2 0");

        //---- generate ----
        generate.setText("\u751f\u6210\u6807\u8bb0\u540e\u56fe\u7247");
        generate.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                generateMouseClicked(e);
            }
        });
        add(generate, "cell 4 0 2 1");

        //---- webUrl ----
        webUrl.setText("\u7f51\u7edc\u56fe\u8def\u5f84");
        add(webUrl, "cell 0 2");
        add(WebUrlIn, "cell 1 2 2 1,grow");

        //---- button1 ----
        button1.setText("\u4fee\u6539\u8def\u5f84\u5f00\u5173");
        button1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                button1MouseClicked(e);
            }
        });
        add(button1, "cell 3 2");

        //---- OutUrl ----
        OutUrl.setText("\u6620\u5c04\u56fe\u8def\u5f84");
        add(OutUrl, "cell 0 3");
        add(OutUrlIn, "cell 1 3 2 1,grow");

        //---- Leaderupdate ----
        Leaderupdate.setText("\u9886\u8896\u540d\u4fee\u6539\u754c\u9762");
        Leaderupdate.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                LeaderupdateMouseClicked(e);
            }
        });
        add(Leaderupdate, "cell 4 3 2 1");
        add(webImageName, "cell 1 4 2 1,grow");

        //---- textArea4 ----
        textArea4.setText("\u9886\u8896\u540d");
        add(textArea4, "cell 4 4");

        //---- textArea5 ----
        textArea5.setText("\u5305\u542b\u7684\u6587\u4ef6");
        add(textArea5, "cell 5 4");

        //---- deleteMapper ----
        deleteMapper.setText("\u5220\u9664\u8fd9\u4e2a\u6620\u5c04");
        deleteMapper.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                deleteMapperMouseClicked(e);
            }
        });
        add(deleteMapper, "cell 7 4");
        add(leaderMapper, "cell 1 5 2 1,wmin 300,hmin 450");

        //---- Pin ----
        Pin.setText("\u6807\u8bb0");
        Pin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                PinMouseClicked(e);
            }
        });
        add(Pin, "cell 3 5,grow");

        //======== scrollPane2 ========
        {

            //---- leaderListUi ----
            leaderListUi.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    leaderListUiMouseClicked(e);
                }
            });
            scrollPane2.setViewportView(leaderListUi);
        }
        add(scrollPane2, "cell 4 5,grow");

        //======== scrollPane3 ========
        {

            //---- mapperListUi ----
            mapperListUi.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    mapperListUiMouseClicked(e);
                }
            });
            scrollPane3.setViewportView(mapperListUi);
        }
        add(scrollPane3, "cell 5 5,grow");
        add(mapperLabel, "cell 7 5,wmin 300,hmin 450");

        //---- sub ----
        sub.setText("<");
        sub.setHorizontalAlignment(SwingConstants.RIGHT);
        sub.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                subMouseClicked(e);
            }
        });
        add(sub, "cell 1 6");

        //---- add ----
        add.setText(">");
        add.setHorizontalAlignment(SwingConstants.LEFT);
        add.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addMouseClicked(e);
            }
        });
        add(add, "cell 2 6");

        //---- process ----
        process.setText("0/0");
        add(process, "cell 1 7 2 1,grow");
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JButton web;
    private JLabel webText;
    private JButton refrash;
    private JButton generate;
    private JLabel webUrl;
    private JTextField WebUrlIn;
    private JButton button1;
    private JLabel OutUrl;
    private JTextField OutUrlIn;
    private JButton Leaderupdate;
    private JLabel webImageName;
    private JLabel textArea4;
    private JLabel textArea5;
    private JButton deleteMapper;
    private JLabel leaderMapper;
    private JButton Pin;
    private JScrollPane scrollPane2;
    private JList leaderListUi;
    private JScrollPane scrollPane3;
    private JList mapperListUi;
    private JLabel mapperLabel;
    private JButton sub;
    private JButton add;
    private JLabel process;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    public void setWebLabel(String s){
        webText.setText(s);
    }

    private void setImage(String url,JLabel label) {
        ImageIcon icon=new ImageIcon();
        try {
            BufferedImage read = ImageIO.read(new File(url));
            Image resultingImage = read.getScaledInstance(300,450,Image.SCALE_AREA_AVERAGING);
            icon.setImage(resultingImage);
        } catch (IOException e) {
            //throw new RuntimeException(e);
        }
        label.setIcon(icon);
    }

    private void initList() {
        List<Role> allleader = DB.getInstance().getAllleader();
        List<cn.sciuridae564.pojo.Image> allimage = DB.getInstance().getAllimage();
        for (Role role : allleader) {
            leaderList.add(role.getChina_name());
            ArrayList<String> list = new ArrayList<>();
            mapperMap.put(role.getChina_name(),list);
        }
        for (int i = 0; i < allimage.size(); i++) {
            if(mapperMap.get(allimage.get(i).getRole_name())!=null){
                mapperMap.get(allimage.get(i).getRole_name()).add(allimage.get(i).getSource_file_name());
            }
        }

        leaderListUi.setListData(leaderList.toArray());
        leaderListUi.setSelectedIndex(0);
        mapperListUi.setListData(mapperMap.get(leaderList.get(0)).toArray());
        if(mapperMap.get(leaderList.get(0)).size()>0){
            mapperListUi.setSelectedIndex(0);
            setImage(file_dir +"\\" + mapperMap.get(leaderList.get(0)).get(0),mapperLabel);
        }

    }

    public void getFileImageList() {
        File file = new File(file_dir);
        fileList = new ArrayList<>();
        if(file.exists() && file.listFiles() != null && file.listFiles().length > 0){
            for (File listFile : file.listFiles()) {
                if (listFile.getName().contains("CharacterJob")&& DB.getInstance().searchnameByfile(listFile.getName()) == null){
                    fileList.add(listFile);
                }
            }
        }

    }

    public void refrashWebImage(){
        getFileImageList();
        if(fileList.size() == 0){
            setWebLabel("标记完了");
            leaderMapper.setIcon(null);webImageName.setText("");
            return;
        }
        if(imagenum >= fileList.size()){
            imagenum = fileList.size()-1;
        }
        if(fileList.size() != 0){
            process.setText(imagenum +1+"/"+fileList.size());
        }else {
            process.setText("0/0");
        }

        setImage(fileList.get(imagenum).getAbsolutePath(),leaderMapper);this.setWebLabel("");
        webImageName.setText(fileList.get(imagenum).getName());
    }
}
