package view;

/**
 * @author wwq
 * 注册界面，需要用户名和密码并确认密码，可返回登录大厅
 */

import org.json.JSONObject;
import client.ClientSocket;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class RegisterDlg extends JFrame {
    private ClientSocket _clientSocket = new ClientSocket();
    private static final String HOST = "localhost";// 服务器地址配置
    private static final int PORT = 11451;// 服务器端口配置
    private JSONObject _jsonObject = new JSONObject();

    public RegisterDlg() {
        init();
    }

    void init() {
        JFrame RegisterDialog = new JFrame("注册");

        // 界面大小
        RegisterDialog.setSize(800, 600);
        // 界面布局先设为null
        RegisterDialog.setLayout(null);

        // 添加标签 [注册]
        JLabel textRegister = new JLabel("欢迎注册虚拟校园");
        textRegister.setForeground(new Color(0x0F866E)); // 0x0F866E
        textRegister.setFont(new Font("微软雅黑", Font.BOLD, 36));
        textRegister.setBounds(460, 70, 320, 40);
        textRegister.setHorizontalTextPosition(SwingConstants.CENTER);
        textRegister.setVerticalTextPosition(SwingConstants.CENTER);
        RegisterDialog.add(textRegister);

        // 添加输入框 [邮箱输入框]
        JTextField mail = new JTextField(20);
        MatteBorder mailBorder = new MatteBorder(0, 0, 0, 0, new Color(0x0F866E));
        mail.setBorder(mailBorder);
        mail.setFont(new Font("华文中宋", Font.PLAIN, 16));
        mail.setSelectedTextColor(new Color(0xFF0000));
        mail.setBounds(520, 150, 250, 40);
        mail.addFocusListener(new JTextFieldHintListener(mail, "邮箱"));
        RegisterDialog.add(mail);

        // 用户图标
        ImageIcon mailIcon = new ImageIcon("image/Mail.ico");
        JLabel mailImage = new JLabel(mailIcon);
        //label.setSize(400, 600);
        mailImage.setBounds(470, 150, 250, 50);
        RegisterDialog.add(mailImage);

        // 添加输入框 [用户名输入框]
        JTextField user = new JTextField(20);
        MatteBorder userBorder = new MatteBorder(0, 0, 0, 0, new Color(0x0F866E));
        user.setBorder(userBorder);
        user.setFont(new Font("华文中宋", Font.PLAIN, 16));
        user.setSelectedTextColor(new Color(0xFF0000));
        user.setBounds(520, 210, 250, 40);
        user.addFocusListener(new JTextFieldHintListener(user, "账号"));
        RegisterDialog.add(user);

        // 用户图标
        ImageIcon userIcon = new ImageIcon("image/user1.ico");
        JLabel userImage = new JLabel(userIcon);
        //label.setSize(400, 600);
        userImage.setBounds(470, 210, 250, 50);
        RegisterDialog.add(userImage);

        // 添加输入框 [密码输入框]
        JPasswordField password = new JPasswordField(20);
        MatteBorder pwdBorder = new MatteBorder(0, 0, 0, 0, new Color(0x0F866E));
        password.setBorder(pwdBorder);
        password.setFont(new Font("华文中宋", Font.PLAIN, 16));
        password.setSelectedTextColor(new Color(0x0F866E));
        password.setBounds(520, 270, 250, 40);
        password.addFocusListener(new JTextFieldHintListener(password, "密码"));
        password.setEchoChar((char)0);
        RegisterDialog.add(password);

        // 密码图标
        ImageIcon pwdIcon = new ImageIcon("image/Password1.ico");
        JLabel pwdImage = new JLabel(pwdIcon);
        //label.setSize(400, 600);
        pwdImage.setBounds(470, 270, 250, 50);
        RegisterDialog.add(pwdImage);

        // 添加输入框 [确认密码输入框]
        JPasswordField confirmPassword = new JPasswordField(20);
        MatteBorder cPwdBorder = new MatteBorder(0, 0, 0, 0, new Color(0x0F866E));
        confirmPassword.setBorder(cPwdBorder);
        confirmPassword.setFont(new Font("华文中宋", Font.PLAIN, 16));
        confirmPassword.setSelectedTextColor(new Color(0x0F866E));
        confirmPassword.setBounds(520, 330, 250, 40);
        confirmPassword.addFocusListener(new JTextFieldHintListener(confirmPassword, "确认密码"));
        confirmPassword.setEchoChar((char)0);
        RegisterDialog.add(confirmPassword);

        // 密码图标
        ImageIcon pwdIcon1 = new ImageIcon("image/Password1.ico");
        JLabel pwdImage1 = new JLabel(pwdIcon1);
        pwdImage1.setBounds(470, 330, 250, 50);
        RegisterDialog.add(pwdImage1);

        // 单选 [选择身份 ST/TC/AD]
        JPanel identity = new JPanel();
        identity.setLayout(new GridLayout(1, 3));
        MatteBorder idBorder = new MatteBorder(0, 0, 0, 0, new Color(0x0F866E));
        identity.setBorder(idBorder);
        JRadioButton rb1 = new JRadioButton("学生");
        JRadioButton rb2 = new JRadioButton("教师");
        JRadioButton rb3 = new JRadioButton("管理员");
        rb1.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        rb2.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        rb3.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        rb1.setBackground(new Color(0xFFFFFF));
        rb2.setBackground(new Color(0xFFFFFF));
        rb3.setBackground(new Color(0xFFFFFF));
        // 放入按钮组中，实现单选
        ButtonGroup group = new ButtonGroup();
        group.add(rb1);
        group.add(rb2);
        group.add(rb3);
        identity.add(rb1);
        identity.add(rb2);
        identity.add(rb3);
        rb1.setSelected(true); // 默认设置为选择学生
        _jsonObject.put("role", "ST"); // 默认传一个学生
        identity.setBounds(495, 380, 210, 40);
        RegisterDialog.add(identity);

        // 添加按钮 [注册]
        ImageIcon icon = new ImageIcon("image/ButtonBackground.ico");
        JButton btnRegister = new JButton("注册", icon);
        btnRegister.setForeground(new Color(0x0F866E));
        btnRegister.setFont(new Font("华文中宋", Font.PLAIN, 25));
        btnRegister.setBorderPainted(false);
        btnRegister.setBounds(470, 440, 250, 50);
        btnRegister.setHorizontalTextPosition(SwingConstants.CENTER);
        btnRegister.setVerticalTextPosition(SwingConstants.CENTER);
        btnRegister.setOpaque(false);
        btnRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));
        RegisterDialog.add(btnRegister);

        // 添加按钮 [返回]
        ImageIcon iconBack = new ImageIcon("image/Back.ico");
        JButton btnBack = new JButton("", iconBack);
        btnBack.setForeground(new Color(0x0F866E));
        btnBack.setBorderPainted(false);
        btnBack.setBounds(730, 500, 50, 50);
        btnBack.setHorizontalTextPosition(SwingConstants.CENTER);
        btnBack.setVerticalTextPosition(SwingConstants.CENTER);
        btnBack.setOpaque(false);
        RegisterDialog.add(btnBack);

        // 图片
        ImageIcon imageIcon = new ImageIcon("image/Homepage2.ico");
        JLabel textImage = new JLabel(imageIcon);
        textImage.setBounds(0, 0, 800, 600);
        RegisterDialog.add(textImage);

        rb1.addActionListener(e -> _jsonObject.put("role", "ST"));
        rb2.addActionListener(e -> _jsonObject.put("role", "TC"));
        rb3.addActionListener(e -> _jsonObject.put("role", "AD"));

        // 注册按钮响应
        btnRegister.addActionListener((e -> {
            try {
                if(user.getText().equals("账号") || mail.getText().equals("邮箱")) {
                    JOptionPane.showMessageDialog(this, "用户名/邮箱不可为空", "错误", JOptionPane.ERROR_MESSAGE);
                }else {
                    _jsonObject.put("request", "register");
                    _jsonObject.put("mail", mail.getText());
                    _jsonObject.put("id", user.getText());
                    String tmpPassword = new String(password.getPassword());
                    _jsonObject.put("password", tmpPassword);
                    JSONObject res = _clientSocket.connect(HOST, PORT, _jsonObject);

                    switch (res.getString("code")) {
                        case "0":
                            JOptionPane.showMessageDialog(this, "验证码发送成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                            String tmpId = user.getText();
                            new VerificationDlg(tmpId);
                            mail.setText("");
                            user.setText("");
                            password.setText("");
                            confirmPassword.setText("");
                            break;
                        case "1":
                            JOptionPane.showMessageDialog(this, "用户名已存在", "错误", JOptionPane.ERROR_MESSAGE);
                            user.setText("");
                            password.setText("");
                            confirmPassword.setText("");
                            break;
                        case "2":
                            JOptionPane.showMessageDialog(this, "邮箱已被注册", "错误", JOptionPane.ERROR_MESSAGE);
                            mail.setText("");
                            user.setText("");
                            password.setText("");
                            confirmPassword.setText("");
                            break;
                        case "-1":
                            JOptionPane.showMessageDialog(this, "系统错误", "错误", JOptionPane.ERROR_MESSAGE);
                            mail.setText("");
                            user.setText("");
                            password.setText("");
                            confirmPassword.setText("");
                            break;
                    }
                }
            } catch (IllegalStateException | IOException e1) {
                JOptionPane.showMessageDialog(this, e1, "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }));

        // 返回按钮响应
        btnBack.addActionListener((e -> {
            try { // 处理方法存疑？
                RegisterDialog.dispose();
                new LoginHomeFrame();
            } catch (IllegalStateException e1) {
                JOptionPane.showMessageDialog(this, e1, "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }));

        // 设置相对位置：屏幕中间
        RegisterDialog.setLocationRelativeTo(null);
        // 确保使用窗口关闭按钮，能够正常退出，结束进程
        RegisterDialog.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 禁止对窗口大小进行缩放
        RegisterDialog.setResizable(false);
        // 设置窗口可见
        RegisterDialog.setVisible(true);
    }
}
