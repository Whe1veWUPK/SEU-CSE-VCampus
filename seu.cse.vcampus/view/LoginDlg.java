package view;

/**
 * @author wwq
 * 登录界面，需要用户名和密码，可修改密码，可返回登录大厅
 */

import org.json.JSONObject;
import client.ClientSocket;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.io.IOException;

public class LoginDlg extends JFrame {
    private ClientSocket _clientSocket = new ClientSocket();
    private static final String HOST = "localhost";// 服务器地址配置
    private static final int PORT = 11451;// 服务器端口配置
    private JSONObject _jsonObject = new JSONObject();

    public LoginDlg() {
        init();
    }

    void init() {
        JFrame LoginDialog = new JFrame("登录");

        // 界面大小
        LoginDialog.setSize(800, 600);
        // 界面布局先设为null
        LoginDialog.setLayout(null);

        // 添加标签 [登录]
        JLabel textUserManage = new JLabel("登录");
        textUserManage.setForeground(new Color(0x0F866E)); // 0x0F866E
        textUserManage.setFont(new Font("微软雅黑", Font.BOLD, 40));
        textUserManage.setBounds(555, 70, 800, 100);
        LoginDialog.add(textUserManage);

        // 添加输入框 [用户名输入框]
        JTextField user = new JTextField(20);
        MatteBorder userBorder = new MatteBorder(0, 0, 0, 0, new Color(0x0F866E));
        user.setBorder(userBorder);
        user.setFont(new Font("华文中宋", Font.PLAIN, 16));
        user.setSelectedTextColor(new Color(0x0F866E));
        user.setBounds(520, 210, 250, 40);
        user.addFocusListener(new JTextFieldHintListener(user, "账号"));
        LoginDialog.add(user);

        // 用户图标
        ImageIcon userIcon = new ImageIcon("image/user1.ico");
        JLabel userImage = new JLabel(userIcon);
        userImage.setBounds(470, 210, 250, 50);
        LoginDialog.add(userImage);

        // 添加输入框 [密码输入框]
        JPasswordField password = new JPasswordField(20);
        MatteBorder pwdBorder = new MatteBorder(0, 0, 0, 0, new Color(0x0F866E));
        password.setBorder(pwdBorder);
        password.setFont(new Font("华文中宋", Font.PLAIN, 16));
        password.setSelectedTextColor(new Color(0x0F866E));
        password.setBounds(520, 270, 250, 40);
        password.addFocusListener(new JTextFieldHintListener(password, "密码")); //
        password.setEchoChar((char)0);
        LoginDialog.add(password);

        // 密码图标
        ImageIcon pwdIcon = new ImageIcon("image/Password1.ico");
        JLabel pwdImage = new JLabel(pwdIcon);
        pwdImage.setBounds(470, 270, 250, 50);
        LoginDialog.add(pwdImage);

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
        identity.setBounds(495, 330, 210, 40);
        LoginDialog.add(identity);

        // 添加按钮 [登录]
        ImageIcon icon = new ImageIcon("image/ButtonBackground.ico");
        JButton btnLogin = new JButton("登录", icon);
        btnLogin.setForeground(new Color(0x0F866E));
        btnLogin.setFont(new Font("华文中宋", Font.PLAIN, 30));
        btnLogin.setBorderPainted(false);
        btnLogin.setBounds(470, 380, 250, 50);
        btnLogin.setHorizontalTextPosition(SwingConstants.CENTER);
        btnLogin.setVerticalTextPosition(SwingConstants.CENTER);
        btnLogin.setOpaque(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LoginDialog.add(btnLogin);

        // 添加按钮 [返回]
        ImageIcon iconBack = new ImageIcon("image/Back.ico");
        JButton btnBack = new JButton("",iconBack);
        btnBack.setForeground(new Color(0x0F866E));
        btnBack.setBorderPainted(false);
        btnBack.setBounds(730, 500, 50, 50);
        btnBack.setHorizontalTextPosition(SwingConstants.CENTER);
        btnBack.setVerticalTextPosition(SwingConstants.CENTER);
        btnBack.setOpaque(false);
        LoginDialog.add(btnBack);

        // 添加按钮 [找回密码]
        JButton btnFindPwd = new JButton("<HTML><U>找回密码</U></HTML>");
        btnFindPwd.setForeground(new Color(0x0F866E));
        btnFindPwd.setBackground(new Color(0xFFFFFF));
        btnFindPwd.setBorderPainted(false);
        btnFindPwd.setBounds(550, 450, 90, 50);
        btnFindPwd.setHorizontalTextPosition(SwingConstants.CENTER);
        btnFindPwd.setVerticalTextPosition(SwingConstants.CENTER);
        btnFindPwd.setOpaque(false);
        LoginDialog.add(btnFindPwd);

        // 图片
        ImageIcon imageIcon = new ImageIcon("image/Homepage2.ico");
        JLabel textImage = new JLabel(imageIcon);
        textImage.setBounds(0, 0, 800, 600);
        LoginDialog.add(textImage);

        rb1.addActionListener(e -> _jsonObject.put("role", "ST"));
        rb2.addActionListener(e -> _jsonObject.put("role", "TC"));
        rb3.addActionListener(e -> _jsonObject.put("role", "AD"));

        // 登录按钮响应
        btnLogin.addActionListener((e -> {
            try {
                _jsonObject.put("request", "login");
                _jsonObject.put("id", user.getText());
                String tmpPassword = new String(password.getPassword());
                _jsonObject.put("password", tmpPassword);
                JSONObject res = _clientSocket.connect(HOST, PORT, _jsonObject);

                switch (res.getString("code")) {
                    case "0":
                        LoginDialog.dispose();
                        JSONObject temp = new JSONObject();
                        temp.put("username", user.getText());
                        temp.put("userrole", _jsonObject.getString("role"));
                        new VCampusHomeFrame(temp);
                        break;
                    case "1":
                        JOptionPane.showMessageDialog(this, "找不到用户", "错误", JOptionPane.ERROR_MESSAGE);
                        user.setText("");
                        password.setText("");
                        break;
                    case "2":
                        JOptionPane.showMessageDialog(this, "密码错误", "错误", JOptionPane.ERROR_MESSAGE);
                        password.setText("");
                        break;
                    case "3":
                        JOptionPane.showMessageDialog(this, "重复登录", "错误", JOptionPane.ERROR_MESSAGE);
                        password.setText("");
                        break;
                    case "4":
                        JOptionPane.showMessageDialog(this, "账号信息异常，请重新登陆", "错误", JOptionPane.ERROR_MESSAGE);
                        user.setText("");
                        password.setText("");
                        break;
                    case "-1":
                        JOptionPane.showMessageDialog(this, "系统错误", "错误", JOptionPane.ERROR_MESSAGE);
                        user.setText("");
                        password.setText("");
                        break;
                }
            } catch (IllegalStateException | IOException | InterruptedException e1) {
                JOptionPane.showMessageDialog(this, e1, "错误", JOptionPane.ERROR_MESSAGE);
            }
        }));

        // 返回按钮响应
        btnBack.addActionListener((e -> {
            try { // 处理方法存疑？

                LoginDialog.dispose();
                new LoginHomeFrame();
            } catch (IllegalStateException e1) {
                JOptionPane.showMessageDialog(this, e1, "错误", JOptionPane.ERROR_MESSAGE);
            }
        }));

        // 找回密码按钮响应
        btnFindPwd.addActionListener((e -> {
            try {
                new FindPwdDlg();

            } catch (IllegalStateException e1) {
                JOptionPane.showMessageDialog(this, e1, "错误", JOptionPane.ERROR_MESSAGE);
            }
        }));

        // 设置相对位置：屏幕中间
        LoginDialog.setLocationRelativeTo(null);
        // 确保使用窗口关闭按钮，能够正常退出，结束进程
        LoginDialog.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 禁止对窗口大小进行缩放
        LoginDialog.setResizable(false);
        // 设置窗口可见
        LoginDialog.setVisible(true);
    }
}
