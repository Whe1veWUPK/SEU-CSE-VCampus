package view;

import org.json.JSONObject;
import client.ClientSocket;
import javax.swing.*;
import java.awt.*;

public class LibraryFrame extends JFrame{

    private ClientSocket clientSocket = new ClientSocket();
    private static final String HOST = "localhost";// 服务器地址配置
    private static final int PORT = 11451;// 服务器端口配置
    private JSONObject jsonObject = new JSONObject();
    private String UserID = null;
    private String UserIdentity = null;
    public LibraryFrame(String id,String identity) {
        this.UserID=id;
        this.UserIdentity=identity;
        init();
    }

    void init() {
        JFrame LibraryFrame = new JFrame("图书馆");

        Container c1=LibraryFrame.getContentPane();
        c1.setBackground(new Color(0xFFFFFF));

        // 界面大小
        LibraryFrame.setSize(800, 600);
        // 界面布局先设为null
        LibraryFrame.setLayout(null);

        // 查阅书籍
        JLabel p0 = new JLabel("Virtual Campus Library System");
        p0.setBackground(new Color(0xcbe6d0));
        p0.setForeground(new Color(0xFFFFFF)); // 0x0F866E
        p0.setFont(new Font("宋体", Font.BOLD, 20));
        p0.setBounds(10,0,500,50);
        c1.add(p0);

        //边框
        JPanel p1 = new JPanel();
        p1.setBackground(new Color(0x81c8b0));
        p1.setBounds(0,0,800,50);
        c1.add(p1);

        // 圆角标签
        JPanel p3 = new RoundPanel();
        p3.setBackground(new Color(0xcbe6d0));
        p3.setBounds(540,90,180,300);
        c1.add(p3);

        // 图书馆图片
        ImageIcon imageIcon1 = new ImageIcon("image/Library2.png");
        JLabel LibraryImage = new JLabel(imageIcon1);
        //label.setSize(400, 600);
        LibraryImage.setBounds(50, 90, 520, 300);
        c1.add(LibraryImage);

        //图书馆标志
        ImageIcon imageIcon2 = new ImageIcon("image/LibrarySig3.png");
        JLabel LibrarySig = new JLabel(imageIcon2);
        //label.setSize(400, 600);
        LibrarySig.setBounds(350, 420, 80, 130);
        c1.add(LibrarySig);

        // 添加按钮 [馆内藏书]
        //ImageIcon icon = new ImageIcon("D:/东大文件/大二暑期学校/项目设计/v-campus-master/v-campus-master/image/ButtonBackground.ico");
        JButton btnAllBooks = new JButton();
        btnAllBooks.setForeground(new Color(0x0F866E));
        btnAllBooks.setBorderPainted(false);
        btnAllBooks.setBounds(150, 440, 120, 50);
        btnAllBooks.setHorizontalTextPosition(SwingConstants.CENTER);
        btnAllBooks.setVerticalTextPosition(SwingConstants.CENTER);
        btnAllBooks.setOpaque(false);
        btnAllBooks.setCursor(new Cursor(Cursor.HAND_CURSOR));
        c1.add(btnAllBooks);

        // 馆内藏书
        JLabel p6 = new JLabel("学生图书管理服务");
        p6.setBackground(new Color(0xcbe6d0));
        p6.setForeground(new Color(0x81c8b0)); // 0x0F866E
        p6.setFont(new Font("宋体", Font.BOLD, 20));
        p6.setBounds(130,500,200,50);
        c1.add(p6);

        // 添加按钮 [注意事项]
        //ImageIcon icon = new ImageIcon("D:/东大文件/大二暑期学校/项目设计/v-campus-master/v-campus-master/image/ButtonBackground.ico");
        JButton btnInfor = new JButton();
        btnInfor.setForeground(new Color(0x0F866E));
        btnInfor.setBorderPainted(false);
        btnInfor.setBounds(500, 440, 120, 50);
        btnInfor.setHorizontalTextPosition(SwingConstants.CENTER);
        btnInfor.setVerticalTextPosition(SwingConstants.CENTER);
        btnInfor.setOpaque(false);
        btnInfor.setCursor(new Cursor(Cursor.HAND_CURSOR));
        c1.add(btnInfor);

        // 注意事项
        JLabel p7 = new JLabel("管理员图书管理服务");
        p7.setBackground(new Color(0xcbe6d0));
        p7.setForeground(new Color(0x81c8b0)); // 0x0F866E
        p7.setFont(new Font("宋体", Font.BOLD, 20));
        p7.setBounds(470,500,200,50);
        c1.add(p7);

        // 馆内藏书按钮响应
        btnAllBooks.addActionListener((e -> {
            switch (this.UserIdentity) {
                default:
                    new LibrarySTDlg(this.UserID);
                    break;
                case "AD":
                    JOptionPane.showMessageDialog(this, "权限不足", "错误", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        }));

        btnInfor.addActionListener((e -> {
            switch (this.UserIdentity) {
                case "AD":
                    new LibrarianGUI(this.UserID);
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

//    public static void main(String[] args) {
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        }catch(Exception e) {
//            System.out.println(e);
//        }
//        JSONObject j1 = new JSONObject();
//        new LibraryFrame("0","ST");
//    }

}
