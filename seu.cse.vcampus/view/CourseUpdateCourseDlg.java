package view;

import org.json.JSONObject;
import client.ClientSocket;

import javax.swing.*;
import java.awt.*;

public class CourseUpdateCourseDlg extends JFrame {
    private ClientSocket clientSocket = new ClientSocket();
    private static final String HOST = "localhost";// 服务器地址配置
    private static final int PORT = 11451;// 服务器端口配置
    private JSONObject jsonObject = new JSONObject();
    private String _clientID;

    public CourseUpdateCourseDlg(String userId) {
        _clientID = userId;
        init();
    }

    public String dayMatch(String day) {
        String temp = "";
        switch (day) {
            case "星期一":
                temp = "1";
                break;
            case "星期二":
                temp = "2";
                break;
            case "星期三":
                temp = "3";
                break;
            case "星期四":
                temp = "4";
                break;
            case "星期五":
                temp = "5";
                break;
            case "星期六":
                temp = "6";
                break;
            case "星期日":
                temp = "7";
                break;
        }
        return temp;
    }

    public String dealHourAndMin(String t) {
        String tmp;
        if(t.length() == 2) {
            tmp = t.substring(0, 1);
        } else {
            tmp = t.substring(0, 2);
        }
        return tmp;
    }

    public String dealCourseTime(String day,String startHour,String startMin,String endHour,String endMin) {
        String tmp = ','+dayMatch(day)+','+dealHourAndMin(startHour)+','+dealHourAndMin(startMin)+','+
                dealHourAndMin(endHour)+','+dealHourAndMin(endMin)+',';

        return tmp;
    }

    void init() {
        JFrame InfoInput = new JFrame("课程修改");

        // 界面大小
        InfoInput.setSize(450, 520);
        // 界面布局先设为null
        InfoInput.setLayout(null);

        JLabel labelCourId = new JLabel("课程ID：");
        labelCourId.setFont(new Font("华文中宋", Font.PLAIN, 17));
        labelCourId.setBounds(90, 40, 90, 30);
        InfoInput.add(labelCourId);
        JTextField textCourId = new JTextField();
        textCourId.setBounds(180, 40, 180, 30);
        InfoInput.add(textCourId);

        JLabel labelCourName = new JLabel("课程名：");
        labelCourName.setFont(new Font("华文中宋", Font.PLAIN, 17));
        labelCourName.setBounds(90, 100, 90, 30);
        InfoInput.add(labelCourName);
        JTextField textCourName = new JTextField();
        textCourName.setBounds(180, 100, 180, 30);
        InfoInput.add(textCourName);

        JLabel labelCourTchr = new JLabel("任课老师：");
        labelCourTchr.setFont(new Font("华文中宋", Font.PLAIN, 17));
        labelCourTchr.setBounds(90, 160, 90, 30);
        InfoInput.add(labelCourTchr);
        JTextField textCourTchr = new JTextField();
        textCourTchr.setBounds(180, 160, 180, 30);
        InfoInput.add(textCourTchr);

        JLabel labelCourCap = new JLabel("课容量：");
        labelCourCap.setFont(new Font("华文中宋", Font.PLAIN, 17));
        labelCourCap.setBounds(90, 220, 90, 30);
        InfoInput.add(labelCourCap);
        JTextField textCourCap = new JTextField();
        textCourCap.setBounds(180, 220, 180, 30);
        InfoInput.add(textCourCap);

        JLabel labelCourStulist = new JLabel("选课名单：");
        labelCourStulist.setFont(new Font("华文中宋", Font.PLAIN, 17));
        labelCourStulist.setBounds(90, 280, 90, 30);
        InfoInput.add(labelCourStulist);
        JTextField textCourStulist = new JTextField();
        textCourStulist.setBounds(180, 280, 180, 30);
        InfoInput.add(textCourStulist);

        JLabel labelCourTime = new JLabel("上课时间: 从");
        labelCourTime.setFont(new Font("华文中宋", Font.PLAIN, 17));
        labelCourTime.setBounds(90, 340, 120, 30);
        InfoInput.add(labelCourTime);
        JPanel panel = new JPanel();
        panel.setBounds(160, 338, 280, 40);
        InfoInput.add(panel);
        // 创建星期下拉框
        String[] weekdays = {"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};
        JComboBox<String> dayComboBox = new JComboBox<>(weekdays);
        dayComboBox.setFont(new Font("华文中宋", Font.PLAIN, 12));
        panel.add(dayComboBox);
        // 创建小时下拉框
        String[] hours = new String[24];
        for (int i = 0; i < 24; i++) {
            hours[i] = String.format("%01d时", i);
        }
        JComboBox<String> hourComboBox = new JComboBox<>(hours);
        hourComboBox.setFont(new Font("华文中宋", Font.PLAIN, 12));
        panel.add(hourComboBox);
        // 创建分钟下拉框
        String[] minutes = new String[60];
        for (int i = 0; i < 60; i++) {
            minutes[i] = String.format("%01d分", i);
        }
        JComboBox<String> minuteComboBox = new JComboBox<>(minutes);
        minuteComboBox.setFont(new Font("华文中宋", Font.PLAIN, 12));
        panel.add(minuteComboBox);

        JLabel labelCourTimeFinish = new JLabel("至: ");
        labelCourTimeFinish.setFont(new Font("华文中宋", Font.PLAIN, 17));
        labelCourTimeFinish.setBounds(167, 380, 120, 30);
        InfoInput.add(labelCourTimeFinish);
        JPanel panelFinish = new JPanel();
        panelFinish.setBounds(195, 378, 280, 40);
        InfoInput.add(panelFinish);
        // 创建小时下拉框
        String[] hoursFinish = new String[24];
        for (int i = 0; i < 24; i++) {
            hoursFinish[i] = String.format("%01d时", i);
        }
        JComboBox<String> hourFinishComboBox = new JComboBox<>(hoursFinish);
        hourFinishComboBox.setFont(new Font("华文中宋", Font.PLAIN, 12));
        panelFinish.add(hourFinishComboBox);
        // 创建分钟下拉框
        String[] minutesFinish = new String[60];
        for (int i = 0; i < 60; i++) {
            minutesFinish[i] = String.format("%01d分", i);
        }
        JComboBox<String> minuteFinishComboBox = new JComboBox<>(minutesFinish);
        minuteFinishComboBox.setFont(new Font("华文中宋", Font.PLAIN, 12));
        panelFinish.add(minuteFinishComboBox);

        // 设置底下按钮
        JButton btnControl = new JButton("修改");
        btnControl.setForeground(new Color(0x0F866E));
        btnControl.setFont(new Font("华文中宋", Font.PLAIN, 18));
        btnControl.setBorderPainted(false);
        btnControl.setBounds(120, 420, 150, 30);
        btnControl.setHorizontalTextPosition(SwingConstants.CENTER);
        btnControl.setVerticalTextPosition(SwingConstants.CENTER);
        btnControl.setOpaque(false);
        btnControl.setCursor(new Cursor(Cursor.HAND_CURSOR));
        InfoInput.add(btnControl);

        // 按钮响应
        btnControl.addActionListener(e -> {
            try {
                jsonObject.put("request", "updatecour");
                jsonObject.put("usrid", _clientID);
                jsonObject.put("courid", textCourId.getText());
                jsonObject.put("courname", textCourName.getText());
                jsonObject.put("courtchr", textCourTchr.getText());
                String courseTime = textCourId.getText() + dealCourseTime(dayComboBox.getSelectedItem().toString(),
                        hourComboBox.getSelectedItem().toString(), minuteComboBox.getSelectedItem().toString(),
                        hourFinishComboBox.getSelectedItem().toString(), minuteFinishComboBox.getSelectedItem().toString());
                jsonObject.put("courtime", courseTime);
                jsonObject.put("courcap", textCourCap.getText());
                jsonObject.put("courstulist", textCourStulist.getText());
                JSONObject res = clientSocket.connect(HOST, PORT, jsonObject);

                switch (res.getString("code")) {
                    case "0" :
                        JOptionPane.showMessageDialog(this, "修改成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                        InfoInput.dispose();
                        break;
                    case "1":
                        JOptionPane.showMessageDialog(this, "用户不存在", "错误", JOptionPane.ERROR_MESSAGE);
                        break;
                    case "2":
                        JOptionPane.showMessageDialog(this, "权限不足", "错误", JOptionPane.ERROR_MESSAGE);
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
