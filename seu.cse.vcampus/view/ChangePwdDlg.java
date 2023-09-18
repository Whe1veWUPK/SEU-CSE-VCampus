package view;
/**
 * @author wwq
 * 修改密码界面，需要用户名，密码和新密码
 */

import org.json.JSONObject;
import client.ClientSocket;

import javax.swing.*;
import java.awt.*;

public class ChangePwdDlg extends JFrame {
    private ClientSocket clientSocket = new ClientSocket();
    private static final String HOST = "localhost";// 服务器地址配置
    private static final int PORT = 11451;// 服务器端口配置
    private JSONObject jsonObject = new JSONObject();

    public ChangePwdDlg() {
        init();
    }

    void init() {
        JFrame InfoInput = new JFrame("修改密码");

        // 界面大小
        InfoInput.setSize(400, 350);
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

        // 标签和文本框
        JLabel labelPwd = new JLabel("密码：");
        labelPwd.setFont(new Font("华文中宋", Font.PLAIN, 17));
        labelPwd.setBounds(70, 100, 90, 30);
        InfoInput.add(labelPwd);
        JTextField textPwd = new JTextField();
        textPwd.setBounds(140, 100, 180, 30);
        InfoInput.add(textPwd);

        // 标签和文本框
        JLabel labelNewPwd = new JLabel("新密码：");
        labelNewPwd.setFont(new Font("华文中宋", Font.PLAIN, 17));
        labelNewPwd.setBounds(70, 160, 90, 30);
        InfoInput.add(labelNewPwd);
        JTextField textNewPwd = new JTextField();
        textNewPwd.setBounds(140, 160, 180, 30);
        InfoInput.add(textNewPwd);

        // 设置底下按钮
        JButton btnControl = new JButton("修改");
        btnControl.setForeground(new Color(0x0F866E));
        btnControl.setFont(new Font("华文中宋", Font.PLAIN, 18));
        btnControl.setBorderPainted(false);
        btnControl.setBounds(120, 220, 150, 30);
        btnControl.setHorizontalTextPosition(SwingConstants.CENTER);
        btnControl.setVerticalTextPosition(SwingConstants.CENTER);
        btnControl.setOpaque(false);
        btnControl.setCursor(new Cursor(Cursor.HAND_CURSOR));
        InfoInput.add(btnControl);

        // 按钮响应
        btnControl.addActionListener(e -> {
            try {
                jsonObject.put("request", "changepwd");
                jsonObject.put("id", textNum.getText());
                jsonObject.put("password", textPwd.getText());
                jsonObject.put("newpwd", textNewPwd.getText());
                JSONObject res = clientSocket.connect(HOST, PORT, jsonObject);

                switch (res.getString("code")) {
                    case "0" :
                        JOptionPane.showMessageDialog(this, "修改成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                        InfoInput.dispose();
                        break;
                    case "1":
                        JOptionPane.showMessageDialog(this, "找不到用户", "错误", JOptionPane.ERROR_MESSAGE);
                        textNum.setText("");
                        textPwd.setText("");
                        textNewPwd.setText("");
                        break;
                    case "2":
                        JOptionPane.showMessageDialog(this, "密码错误", "错误", JOptionPane.ERROR_MESSAGE); // ??
                        break;
                    case "3":
                        JOptionPane.showMessageDialog(this, "新密码不能与旧密码相同", "错误", JOptionPane.ERROR_MESSAGE);
                        textPwd.setText("");
                        textNewPwd.setText("");
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

//    public static void main(String[] args) {
//        new ChangePwdDlg();
//    }
}
