package view;

import org.json.JSONObject;
import client.ClientSocket;

import javax.swing.*;
import java.awt.*;

public class CourseDelCourseDlg extends JFrame {
    private ClientSocket clientSocket = new ClientSocket();
    private static final String HOST = "localhost";// 服务器地址配置
    private static final int PORT = 11451;// 服务器端口配置
    private JSONObject jsonObject = new JSONObject();
    private String _clientID;

    public CourseDelCourseDlg(String userId) {
        _clientID = userId;
        init();
    }

    void init() {
        JFrame InfoInput = new JFrame("信息删除");

        // 界面大小
        InfoInput.setSize(400, 200);
        // 界面布局先设为null
        InfoInput.setLayout(null);

        // 标签和文本框
        JLabel labelCourId = new JLabel("课程ID：");
        labelCourId.setFont(new Font("华文中宋", Font.PLAIN, 17));
        labelCourId.setBounds(70, 40, 90, 30);
        InfoInput.add(labelCourId);
        JTextField textCourId = new JTextField();
        textCourId.setBounds(140, 40, 180, 30);
        InfoInput.add(textCourId);

        // 设置底下按钮
        JButton btnControl = new JButton("删除");
        btnControl.setForeground(new Color(0x0F866E));
        btnControl.setFont(new Font("华文中宋", Font.PLAIN, 18));
        btnControl.setBorderPainted(false);
        btnControl.setBounds(120, 100, 150, 30);
        btnControl.setHorizontalTextPosition(SwingConstants.CENTER);
        btnControl.setVerticalTextPosition(SwingConstants.CENTER);
        btnControl.setOpaque(false);
        btnControl.setCursor(new Cursor(Cursor.HAND_CURSOR));
        InfoInput.add(btnControl);

        // 按钮响应
        btnControl.addActionListener(e -> {
            try {
                jsonObject.put("request", "delcour");
                jsonObject.put("usrid", _clientID);
                jsonObject.put("courid", textCourId.getText());
                JSONObject res = clientSocket.connect(HOST, PORT, jsonObject);

                switch (res.getString("code")) {
                    case "0" :
                        JOptionPane.showMessageDialog(this, "删除成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                        InfoInput.dispose();
                        break;
                    case "1":
                        JOptionPane.showMessageDialog(this, "用户不存在", "错误", JOptionPane.ERROR_MESSAGE);
                        break;
                    case "2":
                        JOptionPane.showMessageDialog(this, "权限不足", "错误", JOptionPane.ERROR_MESSAGE);
                        break;
                    case "3":
                        JOptionPane.showMessageDialog(this, "课程不存在", "错误", JOptionPane.ERROR_MESSAGE);
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
