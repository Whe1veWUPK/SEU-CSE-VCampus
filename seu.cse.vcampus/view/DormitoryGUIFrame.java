package view;

import org.json.JSONObject;
import client.ClientSocket;
import javax.swing.*;
import java.awt.*;
public class DormitoryGUIFrame extends JFrame{

    private ClientSocket clientSocket = new ClientSocket();
    private static final String HOST = "localhost";// 服务器地址配置
    private static final int PORT = 11451;// 服务器端口配置
    private JSONObject jsonObject = new JSONObject();
    private String userID = null;
    private String userIdentity = null;
    public DormitoryGUIFrame(String id, String identity) {
        this.userID=id;
        this.userIdentity=identity;
        init();
    }

    void init() {
        JFrame LibraryFrame = new JFrame("图书馆");
        Container c1=LibraryFrame.getContentPane();
        c1.setBackground(new Color(0xFFFFFF));

        // 界面大小
        LibraryFrame.setSize(1000, 650);
        // 界面布局先设为null
        LibraryFrame.setLayout(null);

        // 添加按钮 [学生登录]
        JButton btnLogin = new JButton("学生登录");
        btnLogin.setForeground(new Color(0x0F866E));
        btnLogin.setBorderPainted(false);
        btnLogin.setBounds(540, 440,  150, 50);
        btnLogin.setHorizontalTextPosition(SwingConstants.CENTER);
        btnLogin.setVerticalTextPosition(SwingConstants.CENTER);
        btnLogin.setOpaque(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        c1.add(btnLogin);

        // 添加按钮 [管理员登录]
        JButton btnLogin2 = new JButton("管理员登录");
        btnLogin2.setForeground(new Color(0x0F866E));
        btnLogin2.setBorderPainted(false);
        btnLogin2.setBounds(750, 440,  150, 50);
        btnLogin2.setHorizontalTextPosition(SwingConstants.CENTER);
        btnLogin2.setVerticalTextPosition(SwingConstants.CENTER);
        btnLogin2.setOpaque(false);
        btnLogin2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        c1.add(btnLogin2);

        // 个人信息图片
        ImageIcon imageIcon2 = new ImageIcon("image/dormitory4.png");
        JLabel LibraryImage2 = new JLabel(imageIcon2);
        LibraryImage2.setBounds(200, -50, 1000, 650);
        c1.add(LibraryImage2);

        // 图书馆图片
        ImageIcon imageIcon1 = new ImageIcon("image/dormitory3.png");
        JLabel LibraryImage = new JLabel(imageIcon1);
        //label.setSize(400, 600);
        LibraryImage.setBounds(0, 0, 1000, 650);
        c1.add(LibraryImage);

        // 学生登陆按钮响应
        btnLogin.addActionListener((e -> {
            switch (this.userIdentity) {
                default:
                    new DormitoryGUIST(this.userID);
                    break;
                case "AD":
                    JOptionPane.showMessageDialog(this, "权限不足", "错误", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        }));

        // 管理员登录按钮响应
        btnLogin2.addActionListener((e -> {
            switch (this.userIdentity) {
                case "AD":
                    new DormitoryGUIAD(this.userID);
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "权限不足", "错误", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        }));

        // 设置相对位置：屏幕中间
        LibraryFrame.setLocationRelativeTo(null);
        // 禁止对窗口大小进行缩放
        LibraryFrame.setResizable(false);
        // 设置窗口可见
        LibraryFrame.setVisible(true);
    }
}
