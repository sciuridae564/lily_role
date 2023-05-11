/*
 * Created by JFormDesigner on Thu May 11 13:08:12 CST 2023
 */

package cn.sciuridae564.frame;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import cn.sciuridae564.pojo.Role;
import cn.sciuridae564.util.sqLite.DB;
import net.miginfocom.swing.*;

/**
 * @author 1
 */
public class leader extends JFrame {
    public leader() {
        initComponents();

        List<Role> allleader = DB.getInstance().getAllleader();
        String[][] name = new String[allleader.size()][2];
        int i = 0;
        for (Role role : allleader) {
            String[] s = new String[2];
            s[0] = role.getChina_name();
            s[1] = role.getName();
            name[i] = s; i++;
        }
        String[] columnNames= {"中文名", "英文代码"};
        TableModel model = new DefaultTableModel(name,columnNames);
        table1.setModel(model);
    }

    //增加
    private void button1MouseClicked(MouseEvent e) {
        int col = table1.getSelectedRow();
        DB.getInstance().insertnameMapping(textField1.getText(), textField2.getText());
        DefaultTableModel model = (DefaultTableModel)table1.getModel();
        model.setValueAt(textField1.getText(),col,0);
        model.setValueAt(textField2.getText(),col,1);

    }

    //删除
    private void button2MouseClicked(MouseEvent e) {
        int col = table1.getSelectedRow();
        TableModel model = table1.getModel();
        DB.getInstance().clearnameMapping((String) model.getValueAt(col,0));
        ((DefaultTableModel)model).removeRow(col);
        if(col == table1.getRowCount()){
            col = table1.getRowCount() - 1;
        }
        table1.setRowSelectionInterval(col,col);
    }

    private void table1MouseClicked(MouseEvent e) {
        int col = table1.getSelectedRow();
        textField1.setText((String) table1.getValueAt(col,0));
        textField2.setText((String) table1.getValueAt(col,1));
    }

    private void thisWindowClosing(WindowEvent e) {
        main.setHasLeader(true);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        button1 = new JButton();
        button2 = new JButton();
        textField1 = new JTextField();
        textField2 = new JTextField();
        scrollPane1 = new JScrollPane();
        table1 = new JTable();

        //======== this ========
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                thisWindowClosing(e);
            }
        });
        Container contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
            "fill,hidemode 3,align center center",
            // columns
            "[]" +
            "[]",
            // rows
            "[24!]" +
            "[24!]" +
            "[]"));

        //---- button1 ----
        button1.setText("\u589e\u52a0/\u66f4\u65b0/\u4fdd\u5b58");
        button1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                button1MouseClicked(e);
            }
        });
        contentPane.add(button1, "cell 0 0,grow");

        //---- button2 ----
        button2.setText("\u5220\u9664");
        button2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                button2MouseClicked(e);
            }
        });
        contentPane.add(button2, "cell 1 0,grow");

        //---- textField1 ----
        textField1.setToolTipText("\u4e2d\u6587\u540d");
        contentPane.add(textField1, "cell 0 1,grow");

        //---- textField2 ----
        textField2.setToolTipText("\u82f1\u6587\u4ee3\u7801\u540d");
        contentPane.add(textField2, "cell 1 1,grow");

        //======== scrollPane1 ========
        {

            //---- table1 ----
            table1.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    table1MouseClicked(e);
                }
            });
            scrollPane1.setViewportView(table1);
        }
        contentPane.add(scrollPane1, "cell 0 2 2 1,grow");
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JButton button1;
    private JButton button2;
    private JTextField textField1;
    private JTextField textField2;
    private JScrollPane scrollPane1;
    private JTable table1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
