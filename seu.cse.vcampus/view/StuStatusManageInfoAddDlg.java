package view;

/**
 * @author wwq
 * 学籍管理系统 “添加学生”功能对应界面
 */

import org.json.JSONObject;
import client.ClientSocket;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class StuStatusManageInfoAddDlg extends JFrame {
    private ClientSocket clientSocket = new ClientSocket();
    private static final String HOST = "localhost";// 服务器地址配置
    private static final int PORT = 11451;// 服务器端口配置
    private JSONObject jsonObject = new JSONObject();
    private String _clientID;

    public StuStatusManageInfoAddDlg(JSONObject json) {
        _clientID = json.getString("userId");
        init();
    }

    void init() {
        JFrame InfoInput = new JFrame("信息添加");

        // 界面大小
        InfoInput.setSize(450, 670);
        // 界面布局先设为null
        InfoInput.setLayout(null);

        JLabel labelNum = new JLabel("学号：");
        labelNum.setFont(new Font("华文中宋", Font.PLAIN, 17));
        labelNum.setBounds(90, 40, 90, 30);
        InfoInput.add(labelNum);
        JTextField textNum = new JTextField();
        textNum.setBounds(180, 40, 180, 30);
        InfoInput.add(textNum);

        JLabel labelName = new JLabel("姓名：");
        labelName.setFont(new Font("华文中宋", Font.PLAIN, 17));
        labelName.setBounds(90, 100, 90, 30);
        InfoInput.add(labelName);
        JTextField textName = new JTextField();
        textName.setBounds(180, 100, 180, 30);
        InfoInput.add(textName);

        JLabel labelAge = new JLabel("年龄：");
        labelAge.setFont(new Font("华文中宋", Font.PLAIN, 17));
        labelAge.setBounds(90, 160, 90, 30);
        InfoInput.add(labelAge);
        JTextField textAge = new JTextField();
        textAge.setBounds(180, 160, 180, 30);
        InfoInput.add(textAge);

        JLabel labelGend = new JLabel("性别：");
        labelGend.setFont(new Font("华文中宋", Font.PLAIN, 17));
        labelGend.setBounds(90, 220, 90, 30);
        InfoInput.add(labelGend);
        JPanel gender = new JPanel(); // 单选板块
        gender.setLayout(new GridLayout(1, 2));
        MatteBorder gendBorder = new MatteBorder(0, 0, 0, 0, new Color(0x0F866E));
        gender.setBorder(gendBorder);
        JRadioButton rb1 = new JRadioButton("男");
        JRadioButton rb2 = new JRadioButton("女");
        rb1.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        rb2.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        rb1.setBackground(new Color(0xE5E5E5));
        rb2.setBackground(new Color(0xE5E5E5));
        // 放入按钮组中，实现单选
        ButtonGroup group = new ButtonGroup();
        group.add(rb1);
        group.add(rb2);
        gender.add(rb1);
        gender.add(rb2);
        rb1.setSelected(true); // 默认设置为选择学生
        jsonObject.put("stugend", "true");
        gender.setBounds(180, 220, 180, 30);
        InfoInput.add(gender);

        JLabel labelId = new JLabel("身份证号：");
        labelId.setFont(new Font("华文中宋", Font.PLAIN, 17));
        labelId.setBounds(90, 280, 90, 30);
        InfoInput.add(labelId);
        JTextField textId = new JTextField();
        textId.setBounds(180, 280, 180, 30);
        InfoInput.add(textId);

        JLabel labelSch = new JLabel("学校：");
        labelSch.setFont(new Font("华文中宋", Font.PLAIN, 17));
        labelSch.setBounds(90, 340, 90, 30);
        InfoInput.add(labelSch);
        JTextField textSch = new JTextField();
        textSch.setBounds(180, 340, 180, 30);
        InfoInput.add(textSch);

        JLabel labelMajor = new JLabel("专业：");
        labelMajor.setFont(new Font("华文中宋", Font.PLAIN, 17));
        labelMajor.setBounds(90, 400, 90, 30);
        InfoInput.add(labelMajor);
        JTextField textMajor = new JTextField();
        textMajor.setBounds(180, 400, 180, 30);
        InfoInput.add(textMajor);

        JLabel labelAddr = new JLabel("家庭住址：");
        labelAddr.setFont(new Font("华文中宋", Font.PLAIN, 17));
        labelAddr.setBounds(90, 460, 90, 30);
        InfoInput.add(labelAddr);
        JTextField textAddr = new JTextField();
        textAddr.setBounds(180, 460, 180, 30);
        InfoInput.add(textAddr);

        JLabel labelIntake = new JLabel("入学年份：");
        labelIntake.setFont(new Font("华文中宋", Font.PLAIN, 17));
        labelIntake.setBounds(90, 520, 90, 30);
        InfoInput.add(labelIntake);
        JTextField textIntake = new JTextField();
        textIntake.setBounds(180, 520, 180, 30);
        InfoInput.add(textIntake);

        // 设置底下按钮
        JButton btnControl = new JButton("添加");
        btnControl.setForeground(new Color(0x0F866E));
        btnControl.setFont(new Font("华文中宋", Font.PLAIN, 18));
        btnControl.setBorderPainted(false);
        btnControl.setBounds(150, 570, 150, 30);
        btnControl.setHorizontalTextPosition(SwingConstants.CENTER);
        btnControl.setVerticalTextPosition(SwingConstants.CENTER);
        btnControl.setOpaque(false);
        btnControl.setCursor(new Cursor(Cursor.HAND_CURSOR));
        InfoInput.add(btnControl);

        rb1.addActionListener(e -> jsonObject.put("stugend", "true"));
        rb2.addActionListener(e -> jsonObject.put("stugend", "false"));

        // 按钮响应
        btnControl.addActionListener(e -> {
            try {
                jsonObject.put("request", "addstat");
                jsonObject.put("usrid", _clientID);
                jsonObject.put("stunum", textNum.getText());
                jsonObject.put("stuname", textName.getText());
                jsonObject.put("stuage", textAge.getText());
                jsonObject.put("stuid", textId.getText());
                jsonObject.put("stusch", textSch.getText());
                jsonObject.put("stumajor", textMajor.getText());
                jsonObject.put("stuaddr", textAddr.getText());
                jsonObject.put("stuintake", textIntake.getText());
                JSONObject res = clientSocket.connect(HOST, PORT, jsonObject);

                switch (res.getString("code")) {
                    case "0" :
                        JOptionPane.showMessageDialog(this, "添加成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                        InfoInput.dispose();
                        break;
                    case "1":
                        JOptionPane.showMessageDialog(this, "用户不存在", "错误", JOptionPane.ERROR_MESSAGE);
                        break;
                    case "2":
                        JOptionPane.showMessageDialog(this, "权限不足", "错误", JOptionPane.ERROR_MESSAGE);
                        break;
                    case "3":
                        JOptionPane.showMessageDialog(this, "学生已存在", "错误", JOptionPane.ERROR_MESSAGE);
                        break;
                    case "-1":
                        JOptionPane.showMessageDialog(this, "系统错误", "错误", JOptionPane.ERROR_MESSAGE);
                        break;
                    case "-2":
                        JOptionPane.showMessageDialog(this, "登录异常", "错误", JOptionPane.ERROR_MESSAGE);
                        break;
                }
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(this, e1, "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
        });

        // 设置相对位置：屏幕中间
        InfoInput.setLocationRelativeTo(null);
        // 禁止对窗口大小进行缩放
        InfoInput.setResizable(false);
        // 设置窗口可见
        InfoInput.setVisible(true);
    }
}
