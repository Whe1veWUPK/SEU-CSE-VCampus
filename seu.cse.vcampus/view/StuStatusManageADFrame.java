package view;

/**
 * @author wwq
 * 学籍管理界面对管理员开放的界面，有四种功能和一个公告板块
 */

import org.json.JSONObject;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class StuStatusManageADFrame extends JFrame {
    private String _clientID;

    public StuStatusManageADFrame(JSONObject json) {
        _clientID = json.getString("userId");
        init();
    }

    void init() {
        JFrame StuStaMaDialog = new JFrame("学籍管理");

        // 界面大小
        StuStaMaDialog.setSize(1000, 600);
        // 界面布局先设为null
        StuStaMaDialog.setLayout(null);

        // 板块管理
        JPanel leftManage = new JPanel();
        leftManage.setBounds(0, 0, 250, 600);
        leftManage.setBackground(new Color( 0x89C997)); // 0x89C997

        // 板块管理
        JPanel downManage = new JPanel();
        downManage.setBounds(250, 70, 750, 530);
        downManage.setBackground(new Color( 0xFFFFFF)); // 0xE5E5E5 灰色

        // 板块管理
        JPanel upManage = new JPanel();
        upManage.setBounds(250, 0, 750, 50);
        upManage.setBackground(new Color( 0x89C997)); // 0x89C997

        // 板块管理
        JPanel midManage = new JPanel();
        midManage.setBounds(250, 50, 750, 30);
        midManage.setBackground(new Color( 0xE5E5E5)); // 0x89C997

        // 添加标签 []
        JLabel textMid = new JLabel("首页/学籍管理/");
        textMid.setForeground(new Color(0xFFFFFF));
        textMid.setFont(new Font("微软雅黑", Font.BOLD, 18));
        textMid.setHorizontalTextPosition(SwingConstants.LEFT);
        textMid.setBounds(255, 48, 150, 30);
        StuStaMaDialog.add(textMid);

        // 添加标签 [虚拟校园]
        JLabel textUserManage = new JLabel("虚拟校园");
        textUserManage.setForeground(new Color(0xFFFFFF));
        textUserManage.setFont(new Font("微软雅黑", Font.BOLD, 30));
        textUserManage.setBounds(50, 0, 120, 40);
        StuStaMaDialog.add(textUserManage);

        // 添加按钮 [查看学籍]
        ImageIcon icon = new ImageIcon("image/ButtonBackground1.ico");
        JButton btnQuery = new JButton("查看学籍", icon);
        btnQuery.setForeground(new Color(0x0F866E));
        btnQuery.setFont(new Font("华文中宋", Font.PLAIN, 25));
        btnQuery.setBorderPainted(false);
        btnQuery.setBounds(0, 100, 250, 50);
        btnQuery.setHorizontalTextPosition(SwingConstants.CENTER);
        btnQuery.setVerticalTextPosition(SwingConstants.CENTER);
        btnQuery.setOpaque(false);
        btnQuery.setCursor(new Cursor(Cursor.HAND_CURSOR));
        StuStaMaDialog.add(btnQuery);

        // 添加按钮 [修改学籍]
        ImageIcon icon1 = new ImageIcon("image/ButtonBackground1.ico");
        JButton btnUpdate = new JButton("修改学籍", icon1);
        btnUpdate.setForeground(new Color(0x0F866E));
        btnUpdate.setFont(new Font("华文中宋", Font.PLAIN, 25));
        btnUpdate.setBorderPainted(false);
        btnUpdate.setBounds(0, 150, 250, 50);
        btnUpdate.setHorizontalTextPosition(SwingConstants.CENTER);
        btnUpdate.setVerticalTextPosition(SwingConstants.CENTER);
        btnUpdate.setOpaque(false);
        btnUpdate.setCursor(new Cursor(Cursor.HAND_CURSOR));
        StuStaMaDialog.add(btnUpdate);

        // 添加按钮 [删除学籍]
        ImageIcon icon2 = new ImageIcon("image/ButtonBackground1.ico");
        JButton btnDelete = new JButton("删除学籍", icon2);
        btnDelete.setForeground(new Color(0x0F866E));
        btnDelete.setFont(new Font("华文中宋", Font.PLAIN, 25));
        btnDelete.setBorderPainted(false);
        btnDelete.setBounds(0, 200, 250, 50);
        btnDelete.setHorizontalTextPosition(SwingConstants.CENTER);
        btnDelete.setVerticalTextPosition(SwingConstants.CENTER);
        btnDelete.setOpaque(false);
        btnDelete.setCursor(new Cursor(Cursor.HAND_CURSOR));
        StuStaMaDialog.add(btnDelete);

        // 添加按钮 [添加学籍]
        ImageIcon icon3 = new ImageIcon("image/ButtonBackground1.ico");
        JButton btnAdd = new JButton("添加学籍", icon3);
        btnAdd.setForeground(new Color(0x0F866E));
        btnAdd.setFont(new Font("华文中宋", Font.PLAIN, 25));
        btnAdd.setBorderPainted(false);
        btnAdd.setBounds(0, 250, 250, 50);
        btnAdd.setHorizontalTextPosition(SwingConstants.CENTER);
        btnAdd.setVerticalTextPosition(SwingConstants.CENTER);
        btnAdd.setOpaque(false);
        btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        StuStaMaDialog.add(btnAdd);

        // 添加按钮 [返回]
        ImageIcon iconBack = new ImageIcon("image/Back.ico");
        JButton btnBack = new JButton("",iconBack);
        btnBack.setForeground(new Color(0x0F866E));
        btnBack.setBorderPainted(false);
        btnBack.setBounds(0, 505, 50, 50);
        btnBack.setHorizontalTextPosition(SwingConstants.CENTER);
        btnBack.setVerticalTextPosition(SwingConstants.CENTER);
        btnBack.setOpaque(false);
        StuStaMaDialog.add(btnBack);

        // 添加多行文本框 [虚拟校园]
        JTextArea textNotice = new JTextArea();
//        textNotice.setText(" 震惊！张god一边说自己不是二次元一边用二次元壁纸，尽显god风范！\n" +
//                " 喜报！张god与刘少总正式组成家庭，开启幸福人生！\n" +
//                " 张god进入前端？对IT业界究竟是福是祸？\n" +
//                " 张god数据库功成身退，临别前说自己会回来的，几分可信？\n" +
//                " 张god植物大战僵尸无尽模式超越1437！PVZ届最后的希望！！\n" +
//                " 0x0F866E\n" +
//                " god张？张god？g张od？此人究竟为何方神圣？\n" +
//                " 南京市惊现神云伴身之人，此人竟另大旱十年的南京落雨！\n" +
//                " 崩坏出现，南京市损失惨重，预计进入旱季，请各居民做好准备.\n" +
//                " 聪明的张god总有办法！\n" +
//                " 我去！我喜欢镜流！\n" +
//                " 我去！我喜欢仇白！\n" +
//                " 我去！我喜欢大姐姐！");
                textNotice.setText("各位2022级新生：\n" +
                        "根据教育部《关于印发高等学校学生学籍学历电子注册办法的通知》（教学[2014]11号）的要求，本科、第二学士学位新生应登陆学信网实名注册并查询、核实本人身份信息和学籍信息。为确保学籍注册结果查询核对工作顺利完成，现将有关事项通知如下：\n" +
                        "一、面向对象\n" +
                        "每位2022级新入学在校本科学生（含往年保留入学资格重新入学，不含复学或降级至2022级学生，不含来华留学本科生）\n" +
                        "二、时间安排\n" +
                        "即日起至2022年11月30日前\n" +
                        "三、查询途径\n" +
                        "中国高等教育学生信息网（学信网）（http://www.chsi.com.cn），查询的具体操作流程与常见问题详见附件。\n");
        textNotice.setForeground(new Color(0x0F866E));
        MatteBorder noticeBorder = new MatteBorder(2, 2, 2, 2, new Color(0xE5E5E5));
        textNotice.setBorder(noticeBorder);
        textNotice.setFont(new Font("微软雅黑", Font.BOLD, 20));
        textNotice.setBackground(new Color(0xE5E5E5));
        textNotice.setBounds(270, 150, 680, 380);
        StuStaMaDialog.add(textNotice);

//        // 背景图
//        ImageIcon bkgIcon = new ImageIcon("image/StuSysManaBackground.ico");
//        JLabel bkgImage = new JLabel(bkgIcon);
//        //label.setSize(400, 600);
//        bkgImage.setBounds(280, 170, 500, 263);
//        StuStaMaDialog.add(bkgImage);

        // 查询学籍按钮响应
        btnQuery.addActionListener((e -> {
            try {
                JSONObject temp = new JSONObject();
                temp.put("userId", _clientID);
                new StuStatusManageInfoQueryDlg(temp);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }));

        // 修改学籍按钮响应
        btnUpdate.addActionListener((e -> {
            try {
                JSONObject temp = new JSONObject();
                temp.put("userId", _clientID);
                new StuStatusManageInfoUpdateDlg(temp);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }));

        // 删除学籍按钮响应
        btnDelete.addActionListener((e -> {
            try {
                JSONObject temp = new JSONObject();
                temp.put("userId", _clientID);
                new StuStatusManageInfoDelDlg(temp);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }));

        // 添加学籍按钮响应
        btnAdd.addActionListener((e -> {
            try {
                JSONObject temp = new JSONObject();
                temp.put("userId", _clientID);
                new StuStatusManageInfoAddDlg(temp);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }));

        // 返回按钮响应
        btnBack.addActionListener((e -> {
            try { // 处理方法存疑？

                StuStaMaDialog.dispose();
            } catch (IllegalStateException e1) {
                JOptionPane.showMessageDialog(this, e1, "错误", JOptionPane.ERROR_MESSAGE);
            }
        }));

        StuStaMaDialog.add(upManage);
        StuStaMaDialog.add(midManage);
        StuStaMaDialog.add(downManage);
        StuStaMaDialog.add(leftManage);

        // 设置相对位置：屏幕中间
        StuStaMaDialog.setLocationRelativeTo(null);
        // 禁止对窗口大小进行缩放
        StuStaMaDialog.setResizable(false);
        // 设置窗口可见
        StuStaMaDialog.setVisible(true);
    }

    //public static void main(String[] args) { new StuStatusManageFrame();}
}
