package view;

/**
 * @author wwq lzh
 * 虚拟校园主页，包含左上个人信息板块，左下登出按钮和右侧五个模块对应的进入按钮
 */

import org.json.JSONObject;
import client.ClientSocket;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class VCampusHomeFrame extends JFrame {
    private String _clientId;
    private String _identity;
    private ClientSocket _clientSocket = new ClientSocket();
    private static final String HOST = "localhost";// 服务器地址配置
    private static final int PORT = 11451;// 服务器端口配置
    private JSONObject _jsonObject = new JSONObject();

    public VCampusHomeFrame(JSONObject jsonObject) throws IOException, InterruptedException {
        this._clientId = jsonObject.getString("username");
        this._identity = jsonObject.getString("userrole");
        init();
    }

    void Information(Container c1) {
        try {
            // 添加标签 [用户名]
            JLabel textUserManage1 = new JLabel(_clientId);
            //textUserManage1.
            textUserManage1.setForeground(new Color(0xFFFFFF)); // 0x0F866E
            textUserManage1.setFont(new Font("宋体", Font.BOLD, 20));
            textUserManage1.setBounds(60, 190, 200, 50);
            textUserManage1.setVisible(true);
            c1.add(textUserManage1);

            // 添加标签 [身份]
            JLabel textUserManage2 = new JLabel(_identity);
            //textUserManage1.
            textUserManage2.setForeground(new Color(0xFFFFFF)); // 0x0F866E
            textUserManage2.setFont(new Font("宋体", Font.BOLD, 20));
            textUserManage2.setBounds(90, 230, 200, 50);
            textUserManage2.setVisible(true);
            c1.add(textUserManage2);

        } catch (IllegalStateException e1) {
            JOptionPane.showMessageDialog(this, e1, "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        //头像背景图片
        ImageIcon imageIcon = new ImageIcon("image/HeadImage2.png");
        JLabel textImage = new JLabel(imageIcon);
        //label.setSize(400, 600);
        textImage.setBounds(55, 85, 90, 90);
        c1.add(textImage);

        // 添加按钮 [修改密码]
        JButton btnChangePwd = new JButton("<HTML><U>修改密码</U></HTML>");
        btnChangePwd.setForeground(new Color(0xFFFFFF));
        btnChangePwd.setFont(new Font("华文中宋", Font.PLAIN, 15));
        btnChangePwd.setBackground(new Color(0x0F866E));
        btnChangePwd.setBorderPainted(false);
        btnChangePwd.setBounds(50, 270, 100, 50);
        btnChangePwd.setHorizontalTextPosition(SwingConstants.CENTER);
        btnChangePwd.setVerticalTextPosition(SwingConstants.CENTER);
        btnChangePwd.setOpaque(false);
        c1.add(btnChangePwd);

        //圆角区域设计
        RoundPanel p1 = new RoundPanel();
        p1.setBackground(new Color(0x199570));
        p1.setBounds(0,0,200,400);
        c1.add(p1);// 面板p_1放于容器c中

        // 修改密码按钮响应
        btnChangePwd.addActionListener((e -> {
            try {
                new ChangePwdDlg();

            } catch (IllegalStateException e1) {
                JOptionPane.showMessageDialog(this, e1, "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }));
    }

    void init() throws IOException, InterruptedException {
        //  创建定时器
        MyTimerTask task = new MyTimerTask(_clientId);
        task.start();

        JFrame HomepageFrame = new JFrame("主界面");

        // 界面大小
        HomepageFrame.setSize(800, 600);
        // 界面布局先设为null
        HomepageFrame.setLayout(null);

        Container c1=HomepageFrame.getContentPane();
        c1.setBackground(new Color(0xcbe6d0));

        Information(c1);

        /**
         面板p_2的属性
         */

        // 添加标签 [虚拟校园主页]
        JLabel textUserManage1 = new JLabel("虚拟校园");
        textUserManage1.setForeground(new Color(0xFFFFFF)); // 0x0F866E
        textUserManage1.setFont(new Font("宋体", Font.BOLD, 40));
        textUserManage1.setBounds(425, 50, 800, 100);
        c1.add(textUserManage1);

        // 添加标签 [虚拟校园主页]
        JLabel textUserManage2 = new JLabel("Vcampus");
        textUserManage2.setForeground(new Color(0xFFFFFF)); // 0x0F866E
        textUserManage2.setFont(new Font("宋体", Font.BOLD, 20));
        textUserManage2.setBounds(470, 90, 800, 100);
        c1.add(textUserManage2);

        // 创建搜索栏和按钮
        ImageIcon icon1 = new ImageIcon("image/Search.ico");
        JTextField searchBar = new JTextField();
        searchBar.setForeground(new Color(0x199570)); // 0x0F866E
        searchBar.setFont(new Font("宋体", Font.BOLD, 20));
        searchBar.setBounds(410, 170, 200, 40);
        searchBar.addFocusListener(new JTextFieldHintListener(searchBar, "search"));
        HomepageFrame.add(searchBar);

        JButton btnStuStat = new JButton("学籍管理系统");
        btnStuStat.setForeground(new Color(0xFFFFFF)); // 0x0F866E
        btnStuStat.setBackground(new Color(0x199570));
        btnStuStat.setFont(new Font("宋体", Font.BOLD, 20));
        btnStuStat.setBounds(315, 260, 270, 130);
        HomepageFrame.add(btnStuStat);

        JButton btnCourse = new JButton("选课系统");
        btnCourse.setForeground(new Color(0xFFFFFF)); // 0x0F866E
        btnCourse.setBackground(new Color(0x199570));
        btnCourse.setFont(new Font("宋体", Font.BOLD, 20));
        btnCourse.setBounds(595, 260, 130, 130);
        HomepageFrame.add(btnCourse);

        JButton btnStore = new JButton("商店");
        btnStore.setForeground(new Color(0xFFFFFF)); // 0x0F866E
        btnStore.setBackground(new Color(0x199570));
        btnStore.setFont(new Font("宋体", Font.BOLD, 20));
        btnStore.setBounds(315, 400, 130, 130);
        HomepageFrame.add(btnStore);

        JButton btnDormitory = new JButton("宿舍系统");
        btnDormitory.setForeground(new Color(0xFFFFFF)); // 0x0F866E
        btnDormitory.setBackground(new Color(0x199570));
        btnDormitory.setFont(new Font("宋体", Font.BOLD, 20));
        btnDormitory.setBounds(455, 400, 130, 130);
        HomepageFrame.add(btnDormitory);

        JButton btnLibrary = new JButton("图书馆");
        btnLibrary.setForeground(new Color(0xFFFFFF)); // 0x0F866E
        btnLibrary.setBackground(new Color(0x81c8b0));
        btnLibrary.setFont(new Font("宋体", Font.BOLD, 20));
        btnLibrary.setBounds(595, 400, 130, 130);
        HomepageFrame.add(btnLibrary);

        JPanel p_3=new JPanel();// 创建面板p_3
        p_3.setBackground(new Color(0xFFFFFF));// 设置背景颜色
        p_3.setBounds(250,240,550,520);
        c1.add(p_3);// 面板p_1放于容器c中

        RoundPanel p_2=new RoundPanel();// 创建面板p_2
        p_2.setBackground(new Color(0x199570));// 设置背景颜色
        p_2.setBounds(250,50,550,520);
        c1.add(p_2);// 面板p_1放于容器c中

        JButton btnLogout = new JButton("登出");
        btnLogout.setForeground(new Color(0xFFFFFF)); // 0x0F866E
        btnLogout.setBackground(new Color(0x81c8b0));
        btnLogout.setFont(new Font("宋体", Font.BOLD, 15));
        btnLogout.setBounds(70, 450, 70, 50);
        HomepageFrame.add(btnLogout);

        // 登出按钮
        btnLogout.addActionListener(e -> {
            _jsonObject.put("request", "logout");
            _jsonObject.put("id", _clientId);
            try {
                JSONObject res = _clientSocket.connect(HOST, PORT, _jsonObject);
                if(res.getString("code").equals("0")) {
                    JOptionPane.showMessageDialog(this, "登出成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                    HomepageFrame.dispose();
                    task.shutdownExec();
                    new LoginHomeFrame();
                } else {
                    JOptionPane.showMessageDialog(this, "登出失败", "错误", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        // 学籍管理按钮响应
        btnStuStat.addActionListener(e -> {
            //HomepageFrame.dispose();
            JSONObject temp = new JSONObject();
            temp.put("userId", _clientId);
            if(_identity.equals("AD")) {
                new StuStatusManageADFrame(temp);
            } else {
                new StuStatusManageSTFrame(temp);
            }
        });

        // 选课系统按钮响应
        btnCourse.addActionListener(e -> {
            //HomepageFrame.dispose();
            if(_identity.equals("AD")) {
                new CourseADFrame(_clientId);
            } else if(_identity.equals("TC")) {
                new CourseTCFrame(_clientId);
            } else {
                new CourseSTFrame(_clientId);
            }
        });

        // 商店 按钮响应
        btnStore.addActionListener(e -> {
            JSONObject temp = new JSONObject();
            temp.put("username", _clientId);
            temp.put("userrole", _identity);
            if(_identity.equals("AD")) {
                new ADStoreFrame(temp);
            } else {
                new CustomerStoreFrame(temp);
            }
        });

        // 图书馆 按钮响应
        btnLibrary.addActionListener(e -> {
            new LibraryFrame(_clientId,_identity);
        });

        // 宿舍 按钮响应
        btnDormitory.addActionListener(e -> {
            new DormitoryGUIFrame(_clientId, _identity);
        });

        // 设置相对位置：屏幕中间
        HomepageFrame.setLocationRelativeTo(null);
        // 确保使用窗口关闭按钮，能够正常退出，结束进程
        HomepageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 禁止对窗口大小进行缩放
        HomepageFrame.setResizable(false);
        // 设置窗口可见
        HomepageFrame.setVisible(true);
    }

//    public static void main(String[] args) throws IOException, InterruptedException {
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("username", "09021127");
//        jsonObject.put("userrole", "AD");
//        new VCampusHomeFrame(jsonObject);
//    }
}
