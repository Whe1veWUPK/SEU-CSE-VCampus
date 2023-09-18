package view;

import org.json.JSONObject;
import client.ClientSocket;


import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

/**
 * 验证码界面类，用户输入邮箱收到的验证码并发送确认消息
 * @author wwq
 */

public class VerificationDlg extends JFrame {
    private ClientSocket _clientSocket = new ClientSocket();
    private static final String HOST = "localhost";// 服务器地址配置
    private static final int PORT = 11451;// 服务器端口配置
    private JSONObject _jsonObject = new JSONObject();
    private String _clientId;

    public VerificationDlg(String id) {
        _clientId = id;
        init();
    }

    void init() {
        JFrame VerifyDialog = new JFrame("注册");

        // 界面大小
        VerifyDialog.setSize(400, 200);
        // 界面布局先设为null
        VerifyDialog.setLayout(null);

        // 添加标签 [输入验证码]
        JLabel textVerify = new JLabel("输入验证码：");
        textVerify.setForeground(new Color(0x0F866E)); // 0x0F866E
        textVerify.setFont(new Font("微软雅黑", Font.BOLD, 16));
        textVerify.setBounds(60, 40, 100, 30);
        textVerify.setHorizontalAlignment(SwingConstants.CENTER);
        textVerify.setVerticalAlignment(SwingConstants.CENTER);
        VerifyDialog.add(textVerify);

        // 添加输入框 [验证码输入框]
        JTextField verify = new JTextField(20);
        MatteBorder verifyBorder = new MatteBorder(2, 2, 2, 2, new Color(0x0F866E));
        verify.setBorder(verifyBorder);
        verify.setFont(new Font("华文中宋", Font.PLAIN, 16));
        verify.setSelectedTextColor(new Color(0xFF0000));
        verify.setBounds(180, 40, 180, 30);
        verify.addFocusListener(new JTextFieldHintListener(verify, "验证码"));
        VerifyDialog.add(verify);

        // 添加按钮 [确认]
        JButton btnConfirm = new JButton("确认");
        btnConfirm.setForeground(new Color(0x0F866E));
        btnConfirm.setFont(new Font("华文中宋", Font.PLAIN, 20));
        btnConfirm.setBorderPainted(false);
        btnConfirm.setBounds(150, 100, 100, 30);
        btnConfirm.setHorizontalAlignment(SwingConstants.CENTER);
        btnConfirm.setVerticalAlignment(SwingConstants.CENTER);
        btnConfirm.setCursor(new Cursor(Cursor.HAND_CURSOR));
        VerifyDialog.add(btnConfirm);

        // 按钮响应
        btnConfirm.addActionListener(e -> {
            try {
                _jsonObject.put("request", "verifymail");
                _jsonObject.put("id", _clientId);
                _jsonObject.put("verify", verify.getText());
                JSONObject res = _clientSocket.connect(HOST, PORT, _jsonObject);

                switch (res.getString("code")) {
                    case "0":
                        JOptionPane.showMessageDialog(this, "注册成功!", "提示", JOptionPane.INFORMATION_MESSAGE);
                        VerifyDialog.dispose();
                        break;
                    case "2":
                        JOptionPane.showMessageDialog(this, "验证码错误", "错误", JOptionPane.ERROR_MESSAGE);
                        textVerify.addFocusListener(new JTextFieldHintListener(verify, "验证码"));
                        break;
                    case "-1":
                        JOptionPane.showMessageDialog(this, "系统错误", "错误", JOptionPane.ERROR_MESSAGE);
                        textVerify.addFocusListener(new JTextFieldHintListener(verify, "验证码"));
                        break;
                    case "-2":
                        JOptionPane.showMessageDialog(this, "注册信息丢失，请重新注册", "错误", JOptionPane.ERROR_MESSAGE);
                        textVerify.addFocusListener(new JTextFieldHintListener(verify, "验证码"));
                        break;
                    case "-3":
                        JOptionPane.showMessageDialog(this, "创建账户失败", "错误", JOptionPane.ERROR_MESSAGE);
                        textVerify.addFocusListener(new JTextFieldHintListener(verify, "验证码"));
                        break;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        // 设置相对位置：屏幕中间
        VerifyDialog.setLocationRelativeTo(null);
        // 禁止对窗口大小进行缩放
        VerifyDialog.setResizable(false);
        // 设置窗口可见
        VerifyDialog.setVisible(true);
    }

//    public static void main(String[] args) {
//        new VerificationDlg("000");
//    }
}
