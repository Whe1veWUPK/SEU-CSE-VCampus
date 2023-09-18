package view;

/**
 * @author wwq
 * 登录大厅界面，包含登录和注册两个按钮，对应两个界面
 */

import javax.swing.*;
import java.awt.*;

public class LoginHomeFrame extends JFrame {

    public LoginHomeFrame() {
        init();
    }

    {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void init() {
        JFrame HomepageFrame = new JFrame("首页");

        // 界面大小
        HomepageFrame.setSize(800, 600);
        // 界面布局先设为null
        HomepageFrame.setLayout(null);

        // 添加标签 [用户管理系统]
        JLabel textUserManage = new JLabel("虚拟校园系统");
        textUserManage.setForeground(new Color(0x0F866E)); // 0x0F866E
        textUserManage.setFont(new Font("微软雅黑", Font.BOLD, 40));
        textUserManage.setBounds(470, 50, 800, 100);
        HomepageFrame.add(textUserManage);

        // 添加按钮 [登录]
        ImageIcon icon = new ImageIcon("image/ButtonBackground.ico");
        JButton btnLogin = new JButton("登录", icon);
        btnLogin.setForeground(new Color(0x0F866E));
        btnLogin.setFont(new Font("华文中宋", Font.PLAIN, 25));
        btnLogin.setBorderPainted(false);
        btnLogin.setBounds(470, 250, 250, 50);
        btnLogin.setHorizontalTextPosition(SwingConstants.CENTER);
        btnLogin.setVerticalTextPosition(SwingConstants.CENTER);
        btnLogin.setOpaque(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        HomepageFrame.add(btnLogin);

        // 添加按钮 [注册]
        ImageIcon icon1 = new ImageIcon("image/ButtonBackground.ico");
        JButton btnRegister = new JButton("注册", icon1);
        btnRegister.setForeground(new Color(0x0F866E));
        btnRegister.setFont(new Font("华文中宋", Font.PLAIN, 25));
        btnRegister.setBorderPainted(false);
        btnRegister.setBounds(470, 350, 250, 50);
        btnRegister.setHorizontalTextPosition(SwingConstants.CENTER);
        btnRegister.setVerticalTextPosition(SwingConstants.CENTER);
        btnRegister.setOpaque(false);
        btnRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));
        HomepageFrame.add(btnRegister);

        // 图片
        ImageIcon imageIcon = new ImageIcon("image/Homepage2.ico");
        JLabel textImage = new JLabel(imageIcon);
        //label.setSize(400, 600);
        textImage.setBounds(0, 0, 800, 600);
        HomepageFrame.add(textImage);

        // 登录按钮响应
        btnLogin.addActionListener((e -> {
            try {
                HomepageFrame.dispose();
                new LoginDlg();
            } catch (IllegalStateException e1) {
                JOptionPane.showMessageDialog(this, e1, "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }));

        // 注册按钮响应
        btnRegister.addActionListener((e -> {
            try {
                HomepageFrame.setVisible(false);
                new RegisterDlg();
            } catch (IllegalStateException e1) {
                JOptionPane.showMessageDialog(this, e1, "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }));

        // 设置相对位置：屏幕中间
        HomepageFrame.setLocationRelativeTo(null);
        // 确保使用窗口关闭按钮，能够正常退出，结束进程
        HomepageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 禁止对窗口大小进行缩放
        HomepageFrame.setResizable(false);
        // 设置窗口可见
        HomepageFrame.setVisible(true);
    }

    public static void main(String[] args) {
        new LoginHomeFrame();
    }
}
