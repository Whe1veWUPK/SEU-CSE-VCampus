package view;

import org.json.JSONObject;
import client.ClientSocket;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class CourseADFrame extends JFrame {
    String _clientID;
    private ClientSocket _clientSocket = new ClientSocket();
    private static final String HOST = "localhost";// 服务器地址配置
    private static final int PORT = 11451;// 服务器端口配置
    private JSONObject _jsonObject = new JSONObject();

    public CourseADFrame(String id) {
        _clientID = id;
        init();
    }

    void init() {
        JFrame CoStFrame = new JFrame("选课系统");

        // 界面大小
        CoStFrame.setSize(1600, 1000);
        // 界面布局先设为null
        CoStFrame.setLayout(null);

        // 添加标签 [欢迎]
        JLabel textWelcome = new JLabel("欢迎, ");
        textWelcome.setForeground(new Color(0x3BA981)); // 0x0F866E
        textWelcome.setFont(new Font("微软雅黑", Font.BOLD, 80));
        textWelcome.setBounds(240, 150, 800, 100);
        CoStFrame.add(textWelcome);

        // 添加标签 [用户名]
        JLabel textClientId = new JLabel(_clientID);
        textClientId.setForeground(new Color(0x3BA981)); // 0x0F866E
        textClientId.setFont(new Font("微软雅黑", Font.BOLD, 80));
        textClientId.setBounds(350, 270, 800, 100);
        CoStFrame.add(textClientId);

        // 添加按钮 [增加课程]
        JButton btnAddCourse = new JButton("增加课程");
        btnAddCourse.setForeground(new Color(0x3BA981));
        btnAddCourse.setFont(new Font("华文中宋", Font.PLAIN, 25));
        btnAddCourse.setBackground(new Color(0xDDE9E0));
        btnAddCourse.setBorderPainted(false);
        btnAddCourse.setBounds(180, 500, 250, 100);
        btnAddCourse.setHorizontalTextPosition(SwingConstants.CENTER);
        btnAddCourse.setVerticalTextPosition(SwingConstants.CENTER);
        btnAddCourse.setOpaque(true);
        btnAddCourse.setCursor(new Cursor(Cursor.HAND_CURSOR));
        CoStFrame.add(btnAddCourse);

        // 添加按钮 [删除课程]
        JButton btnDelCourse = new JButton("删除课程");
        btnDelCourse.setForeground(new Color(0x3BA981));
        btnDelCourse.setFont(new Font("华文中宋", Font.PLAIN, 25));
        btnDelCourse.setBackground(new Color(0xDDE9E0));
        btnDelCourse.setBorderPainted(false);
        btnDelCourse.setBounds(480, 500, 250, 100);
        btnDelCourse.setHorizontalTextPosition(SwingConstants.CENTER);
        btnDelCourse.setVerticalTextPosition(SwingConstants.CENTER);
        btnDelCourse.setOpaque(true);
        btnDelCourse.setCursor(new Cursor(Cursor.HAND_CURSOR));
        CoStFrame.add(btnDelCourse);

        // 添加按钮 [修改课程]
        JButton btnUpdateCourse = new JButton("修改课程");
        btnUpdateCourse.setForeground(new Color(0x3BA981));
        btnUpdateCourse.setFont(new Font("华文中宋", Font.PLAIN, 25));
        btnUpdateCourse.setBackground(new Color(0xDDE9E0));
        btnUpdateCourse.setBorderPainted(false);
        btnUpdateCourse.setBounds(180, 650, 250, 100);
        btnUpdateCourse.setHorizontalTextPosition(SwingConstants.CENTER);
        btnUpdateCourse.setVerticalTextPosition(SwingConstants.CENTER);
        btnUpdateCourse.setOpaque(true);
        btnUpdateCourse.setCursor(new Cursor(Cursor.HAND_CURSOR));
        CoStFrame.add(btnUpdateCourse);

        // 添加按钮 [查询课程/全部课程]
        JButton btnAllCourse = new JButton("查询课程/全部课程");
        btnAllCourse.setForeground(new Color(0x3BA981));
        btnAllCourse.setFont(new Font("华文中宋", Font.PLAIN, 25));
        btnAllCourse.setBackground(new Color(0xDDE9E0));
        btnAllCourse.setBorderPainted(false);
        btnAllCourse.setBounds(480, 650, 250, 100);
        btnAllCourse.setHorizontalTextPosition(SwingConstants.CENTER);
        btnAllCourse.setVerticalTextPosition(SwingConstants.CENTER);
        btnAllCourse.setOpaque(true);
        btnAllCourse.setCursor(new Cursor(Cursor.HAND_CURSOR));
        CoStFrame.add(btnAllCourse);

        // 添加按钮 [更改任课老师]
        JButton btnChangeTchr = new JButton("更改任课老师");
        btnChangeTchr.setForeground(new Color(0x3BA981));
        btnChangeTchr.setFont(new Font("华文中宋", Font.PLAIN, 20));
        btnChangeTchr.setBackground(new Color(0xDDE9E0));
        btnChangeTchr.setBorderPainted(false);
        btnChangeTchr.setBounds(170, 800, 180, 80);
        btnChangeTchr.setHorizontalTextPosition(SwingConstants.CENTER);
        btnChangeTchr.setVerticalTextPosition(SwingConstants.CENTER);
        btnChangeTchr.setOpaque(true);
        btnChangeTchr.setCursor(new Cursor(Cursor.HAND_CURSOR));
        CoStFrame.add(btnChangeTchr);

        // 添加按钮 [手动添加学生至选课名单]
        JButton btnAddStu = new JButton("添加学生");
        btnAddStu.setForeground(new Color(0x3BA981));
        btnAddStu.setFont(new Font("华文中宋", Font.PLAIN, 20));
        btnAddStu.setBackground(new Color(0xDDE9E0));
        btnAddStu.setBorderPainted(false);
        btnAddStu.setBounds(370, 800, 180, 80);
        btnAddStu.setHorizontalTextPosition(SwingConstants.CENTER);
        btnAddStu.setVerticalTextPosition(SwingConstants.CENTER);
        btnAddStu.setOpaque(true);
        btnAddStu.setCursor(new Cursor(Cursor.HAND_CURSOR));
        CoStFrame.add(btnAddStu);

        // 添加按钮 [删除学生选课]
        JButton btnDelStu = new JButton("删除学生选课");
        btnDelStu.setForeground(new Color(0x3BA981));
        btnDelStu.setFont(new Font("华文中宋", Font.PLAIN, 20));
        btnDelStu.setBackground(new Color(0xDDE9E0));
        btnDelStu.setBorderPainted(false);
        btnDelStu.setBounds(570, 800, 180, 80);
        btnDelStu.setHorizontalTextPosition(SwingConstants.CENTER);
        btnDelStu.setVerticalTextPosition(SwingConstants.CENTER);
        btnDelStu.setOpaque(true);
        btnDelStu.setCursor(new Cursor(Cursor.HAND_CURSOR));
        CoStFrame.add(btnDelStu);

        // 添加按钮 [返回]
        ImageIcon iconBack = new ImageIcon("image/Back.ico");
        JButton btnBack = new JButton("",iconBack);
        btnBack.setForeground(new Color(0x0F866E));
        btnBack.setBorderPainted(false);
        btnBack.setBounds(0, 900, 50, 50);
        btnBack.setHorizontalTextPosition(SwingConstants.CENTER);
        btnBack.setVerticalTextPosition(SwingConstants.CENTER);
        btnBack.setOpaque(false);
        CoStFrame.add(btnBack);

        // 图片
        ImageIcon imageIcon = new ImageIcon("image/CourseBackground.ico");
        JLabel textImage = new JLabel(imageIcon);
        //label.setSize(400, 600);
        textImage.setBounds(0, 0, 1600, 1000);
        CoStFrame.add(textImage);

        // 增加课程按钮响应
        btnAddCourse.addActionListener((e -> {
            try {
                new CourseAddCourseDlg(_clientID);
            } catch (IllegalStateException | InterruptedException e1) {
                JOptionPane.showMessageDialog(this, e1, "错误", JOptionPane.ERROR_MESSAGE);
            }
        }));

        // 删除课程按钮响应
        btnDelCourse.addActionListener(e -> {
            try {
                new CourseDelCourseDlg(_clientID);
            } catch (IllegalStateException e1) {
                JOptionPane.showMessageDialog(this, e1, "错误", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 修改课程按钮响应
        btnUpdateCourse.addActionListener(e -> {
            try {
                new CourseUpdateCourseDlg(_clientID);
            } catch (IllegalStateException e1) {
                JOptionPane.showMessageDialog(this, e1, "错误", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 查询课程/全部课程按钮响应
        btnAllCourse.addActionListener(e -> {
            try {
                _jsonObject.put("request", "queryallcour");
                _jsonObject.put("usrid", _clientID);
                JSONObject res = _clientSocket.connect(HOST, PORT, _jsonObject);

                switch (res.getString("code")) {
                    case "0":
                        String data = res.getString("data");
                        new CourseAllAndSearchDlg(data, _clientID);
                        break;
                    case "1":
                        JOptionPane.showMessageDialog(this, "用户不存在", "错误", JOptionPane.ERROR_MESSAGE);
                        break;
                    case "2":
                        JOptionPane.showMessageDialog(this, "权限不足", "错误", JOptionPane.ERROR_MESSAGE);
                        break;
                    case "3":
                        JOptionPane.showMessageDialog(this, "没有课程信息", "错误", JOptionPane.ERROR_MESSAGE);
                        break;
                    case "-2":
                        JOptionPane.showMessageDialog(this, "登陆异常", "错误", JOptionPane.ERROR_MESSAGE);
                        break;
                }

            } catch (IllegalStateException | IOException e1) {
                JOptionPane.showMessageDialog(this, e1, "错误", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 更改任课老师按钮响应
        btnChangeTchr.addActionListener(e -> {
            try {
                new CourseChangeTchrDlg(_clientID);
            } catch (IllegalStateException e1) {
                JOptionPane.showMessageDialog(this, e1, "错误", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 添加学生选课按钮响应（手动添加学生）
        btnAddStu.addActionListener(e -> {
            try {
                new CourseAddStuDlg(_clientID);
            } catch (IllegalStateException e1) {
                JOptionPane.showMessageDialog(this, e1, "错误", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 删除学生按钮响应
        btnDelStu.addActionListener(e -> {
            try {
                new CourseDelStuDlg(_clientID);
            } catch (IllegalStateException e1) {
                JOptionPane.showMessageDialog(this, e1, "错误", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 返回按钮响应
        btnBack.addActionListener((e -> {
            try { // 处理方法存疑？
                CoStFrame.dispose();
            } catch (IllegalStateException e1) {
                JOptionPane.showMessageDialog(this, e1, "错误", JOptionPane.ERROR_MESSAGE);
            }
        }));

        // 设置相对位置：屏幕中间
        CoStFrame.setLocationRelativeTo(null);
        // 禁止对窗口大小进行缩放
        CoStFrame.setResizable(false);
        // 设置窗口可见
        CoStFrame.setVisible(true);
    }

//    public static void main(String[] args) {
//        new CourseADFrame("090211");
//    }
}
