package view;

import org.json.JSONObject;
import client.ClientSocket;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class FindPwdDlg extends JFrame {
    private ClientSocket _clientSocket = new ClientSocket();
    private static final String HOST = "localhost";// 服务器地址配置
    private static final int PORT = 11451;// 服务器端口配置
    private JSONObject _jsonObject = new JSONObject();

    public FindPwdDlg() {
        init();
    }

    void init() {
        JFrame FindPwdDialog = new JFrame("找回密码");

        // 界面大小
        FindPwdDialog.setSize(400, 350);
        // 界面布局先设为null
        FindPwdDialog.setLayout(null);

        // 添加标签 [ID]
        JLabel textUsername = new JLabel("用户名：");
        textUsername.setForeground(new Color(0x0F866E)); // 0x0F866E
        textUsername.setFont(new Font("微软雅黑", Font.BOLD, 16));
        textUsername.setBounds(60, 40, 100, 30);
        textUsername.setHorizontalAlignment(SwingConstants.LEFT);
        textUsername.setVerticalAlignment(SwingConstants.CENTER);
        FindPwdDialog.add(textUsername);

        // 添加输入框 [Id输入框]
        JTextField username = new JTextField(20);
        MatteBorder usernameBorder = new MatteBorder(2, 2, 2, 2, new Color(0x0F866E));
        username.setBorder(usernameBorder);
        username.setFont(new Font("华文中宋", Font.PLAIN, 16));
        username.setSelectedTextColor(new Color(0xFF0000));
        username.setBounds(180, 40, 180, 30);
        username.addFocusListener(new JTextFieldHintListener(username, "用户名"));
        FindPwdDialog.add(username);

        // 添加标签 [输入新密码]
        JLabel textNewPwd = new JLabel("输入新密码：");
        textNewPwd.setForeground(new Color(0x0F866E)); // 0x0F866E
        textNewPwd.setFont(new Font("微软雅黑", Font.BOLD, 16));
        textNewPwd.setBounds(60, 100, 100, 30);
        textNewPwd.setHorizontalAlignment(SwingConstants.LEFT);
        textNewPwd.setVerticalAlignment(SwingConstants.CENTER);
        FindPwdDialog.add(textNewPwd);

        // 添加输入框 [新密码输入框]
        JPasswordField newPwd = new JPasswordField(20);
        MatteBorder newPwdBorder = new MatteBorder(2, 2, 2, 2, new Color(0x0F866E));
        newPwd.setBorder(newPwdBorder);
        newPwd.setFont(new Font("华文中宋", Font.PLAIN, 16));
        newPwd.setSelectedTextColor(new Color(0xFF0000));
        newPwd.setBounds(180, 100, 180, 30);
        newPwd.addFocusListener(new JTextFieldHintListener(newPwd, "新密码"));
        newPwd.setEchoChar((char)0);
        FindPwdDialog.add(newPwd);

        // 添加输入框 [验证码输入框]
        JTextField verify = new JTextField(20);
        MatteBorder verifyBorder = new MatteBorder(2, 2, 2, 2, new Color(0x0F866E));
        verify.setBorder(verifyBorder);
        verify.setFont(new Font("华文中宋", Font.PLAIN, 16));
        verify.setSelectedTextColor(new Color(0xFF0000));
        verify.setBounds(60, 160, 180, 30);
        verify.addFocusListener(new JTextFieldHintListener(verify, "验证码"));
        FindPwdDialog.add(verify);

        // 添加按钮 [发送验证码]
        JButton btnSendVerify = new JButton("发送验证码");
        btnSendVerify.setForeground(new Color(0x0F866E));
        btnSendVerify.setFont(new Font("华文中宋", Font.PLAIN, 13));
        btnSendVerify.setBorderPainted(false);
        btnSendVerify.setBounds(260, 160, 100, 30);
        btnSendVerify.setHorizontalAlignment(SwingConstants.CENTER);
        btnSendVerify.setVerticalAlignment(SwingConstants.CENTER);
        btnSendVerify.setCursor(new Cursor(Cursor.HAND_CURSOR));
        FindPwdDialog.add(btnSendVerify);

        // 添加按钮 [确认修改]
        JButton btnConfirm = new JButton("确认修改");
        btnConfirm.setForeground(new Color(0x0F866E));
        btnConfirm.setFont(new Font("华文中宋", Font.PLAIN, 16));
        btnConfirm.setBorderPainted(false);
        btnConfirm.setBounds(150, 220, 100, 30);
        btnConfirm.setHorizontalAlignment(SwingConstants.CENTER);
        btnConfirm.setVerticalAlignment(SwingConstants.CENTER);
        btnConfirm.setCursor(new Cursor(Cursor.HAND_CURSOR));
        FindPwdDialog.add(btnConfirm);

        // 发送验证码按钮响应
        btnSendVerify.addActionListener(e -> {
            try {
                _jsonObject.put("request", "sendverification");
                _jsonObject.put("id", username.getText());
                JSONObject res = _clientSocket.connect(HOST, PORT, _jsonObject);

                switch (res.getString("code")) {
                    case "0":
                        JOptionPane.showMessageDialog(this, "验证码发送成功!", "提示", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    case "1":
                        JOptionPane.showMessageDialog(this, "找不到用户", "错误", JOptionPane.ERROR_MESSAGE);
                        break;
                    case "-2":
                        JOptionPane.showMessageDialog(this, "请先退出登录", "错误", JOptionPane.ERROR_MESSAGE);
                        break;
                    case "-3":
                        JOptionPane.showMessageDialog(this, "网络异常", "错误", JOptionPane.ERROR_MESSAGE);
                        break;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        // 确认修改按钮响应
        btnConfirm.addActionListener(e -> {
            try {
                _jsonObject.put("request", "retrievepwd");
                _jsonObject.put("id", username.getText());
                String tmpPwd = new String(newPwd.getPassword());
                _jsonObject.put("newpwd", tmpPwd);
                _jsonObject.put("verify", verify.getText());
                JSONObject res = _clientSocket.connect(HOST, PORT, _jsonObject);

                switch (res.getString("code")) {
                    case "0":
                        JOptionPane.showMessageDialog(this, "修改成功!", "提示", JOptionPane.INFORMATION_MESSAGE);
                        FindPwdDialog.dispose();
                        break;
                    case "1":
                        JOptionPane.showMessageDialog(this, "找不到用户", "错误", JOptionPane.ERROR_MESSAGE);
                        username.setText("");
                        newPwd.setText("");
                        verify.setText("");
                        break;
                    case "2":
                        JOptionPane.showMessageDialog(this, "验证码错误或已超时", "错误", JOptionPane.ERROR_MESSAGE);
                        verify.setText("");
                        break;
                    case "3":
                        JOptionPane.showMessageDialog(this, "新密码不能与旧密码相同", "错误", JOptionPane.ERROR_MESSAGE);
                        newPwd.setText("");
                        break;
                    case "-2":
                        JOptionPane.showMessageDialog(this, "请先退出登录", "错误", JOptionPane.ERROR_MESSAGE);
                        break;
                    case "-3":
                        JOptionPane.showMessageDialog(this, "网络异常", "错误", JOptionPane.ERROR_MESSAGE);
                        break;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        // 设置相对位置：屏幕中间
        FindPwdDialog.setLocationRelativeTo(null);
        // 禁止对窗口大小进行缩放
        FindPwdDialog.setResizable(false);
        // 设置窗口可见
        FindPwdDialog.setVisible(true);
    }

//    public static void main(String[] args) {
//        new FindPwdDlg();
//    }
}
