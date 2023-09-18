package view;

/**
 * @author wwq
 * 学籍管理系统 “查找学生”功能对应界面
 */

import org.json.JSONObject;
import client.ClientSocket;

import javax.swing.*;
import java.awt.*;

public class StuStatusManageInfoQueryDlg extends JFrame {
    private ClientSocket clientSocket = new ClientSocket();
    private static final String HOST = "localhost";// 服务器地址配置
    private static final int PORT = 11451;// 服务器端口配置
    private JSONObject jsonObject = new JSONObject();
    private String _clientID;

    public StuStatusManageInfoQueryDlg(JSONObject json) {
        _clientID = json.getString("userId");
        init();
    }

    void init() {
        JFrame InfoInput = new JFrame("信息查询");

        // 界面大小
        InfoInput.setSize(400, 200);
        // 界面布局先设为null
        InfoInput.setLayout(null);

        // 标签和文本框
        JLabel labelNum = new JLabel("学号：");
        labelNum.setFont(new Font("华文中宋", Font.PLAIN, 17));
        labelNum.setBounds(70, 40, 90, 30);
        InfoInput.add(labelNum);
        JTextField textNum = new JTextField();
        textNum.setBounds(140, 40, 180, 30);
        InfoInput.add(textNum);

        // 设置底下按钮
        JButton btnControl = new JButton("查询");
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
                jsonObject.put("request", "getstat");
                jsonObject.put("usrid", _clientID);
                jsonObject.put("stunum", textNum.getText());
                JSONObject res = clientSocket.connect(HOST, PORT, jsonObject);

                switch (res.getString("code")) {
                    case "0" :
                        JOptionPane.showMessageDialog(this, "查询成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                        InfoInput.dispose();
                        new StuStatusManageInfoQuery1Dlg(res);
                        break;
                    case "1":
                        JOptionPane.showMessageDialog(this, "用户不存在", "错误", JOptionPane.ERROR_MESSAGE);
                        break;
                    case "2":
                        JOptionPane.showMessageDialog(this, "权限不足", "错误", JOptionPane.ERROR_MESSAGE);
                        break;
                    case "3":
                        JOptionPane.showMessageDialog(this, "找不到学生", "错误", JOptionPane.ERROR_MESSAGE);
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
