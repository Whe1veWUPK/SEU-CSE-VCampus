package view;

import org.json.JSONObject;
import client.ClientSocket;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class CourseTCFrame extends JFrame {
    String _clientID;
    private ClientSocket _clientSocket = new ClientSocket();
    private static final String HOST = "localhost";// 服务器地址配置
    private static final int PORT = 11451;// 服务器端口配置
    private JSONObject _jsonObject = new JSONObject();

    public CourseTCFrame(String id) {
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

        // 添加按钮 [查询课程]
        JButton btnSearchCourse = new JButton("查询课程");
        btnSearchCourse.setForeground(new Color(0x3BA981));
        btnSearchCourse.setFont(new Font("华文中宋", Font.PLAIN, 25));
        btnSearchCourse.setBackground(new Color(0xDDE9E0));
        btnSearchCourse.setBorderPainted(false);
        btnSearchCourse.setBounds(180, 500, 250, 150);
        btnSearchCourse.setHorizontalTextPosition(SwingConstants.CENTER);
        btnSearchCourse.setVerticalTextPosition(SwingConstants.CENTER);
        btnSearchCourse.setOpaque(true);
        btnSearchCourse.setCursor(new Cursor(Cursor.HAND_CURSOR));
        CoStFrame.add(btnSearchCourse);

        // 添加按钮 [查询任教课程]
        JButton btnTeachingCourses = new JButton("我的任教课程");
        btnTeachingCourses.setForeground(new Color(0x3BA981));
        btnTeachingCourses.setFont(new Font("华文中宋", Font.PLAIN, 25));
        btnTeachingCourses.setBackground(new Color(0xDDE9E0));
        btnTeachingCourses.setBorderPainted(false);
        btnTeachingCourses.setBounds(480, 500, 250, 150);
        btnTeachingCourses.setHorizontalTextPosition(SwingConstants.CENTER);
        btnTeachingCourses.setVerticalTextPosition(SwingConstants.CENTER);
        btnTeachingCourses.setOpaque(true);
        btnTeachingCourses.setCursor(new Cursor(Cursor.HAND_CURSOR));
        CoStFrame.add(btnTeachingCourses);

        // 添加按钮 [显示全部课程]
        JButton btnAllCourse = new JButton("全部课程");
        btnAllCourse.setForeground(new Color(0x3BA981));
        btnAllCourse.setFont(new Font("华文中宋", Font.PLAIN, 25));
        btnAllCourse.setBackground(new Color(0xDDE9E0));
        btnAllCourse.setBorderPainted(false);
        btnAllCourse.setBounds(180, 700, 250, 150);
        btnAllCourse.setHorizontalTextPosition(SwingConstants.CENTER);
        btnAllCourse.setVerticalTextPosition(SwingConstants.CENTER);
        btnAllCourse.setOpaque(true);
        btnAllCourse.setCursor(new Cursor(Cursor.HAND_CURSOR));
        CoStFrame.add(btnAllCourse);

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

        // 我的任教课程按钮响应
        btnTeachingCourses.addActionListener((e -> {
            try {
                _jsonObject.put("request", "tcquerysel");
                _jsonObject.put("usrid", _clientID);
                JSONObject res = _clientSocket.connect(HOST, PORT, _jsonObject);

                switch (res.getString("code")) {
                    case "0":
                        String data = res.getString("courselist");
                        new CourseMyCurriculumDlg(data);
                        break;
                    case "1":
                        JOptionPane.showMessageDialog(this, "用户不存在", "错误", JOptionPane.ERROR_MESSAGE);
                        break;
                    case "2":
                        JOptionPane.showMessageDialog(this, "权限不足", "错误", JOptionPane.ERROR_MESSAGE);
                        break;
                    case "4":
                        JOptionPane.showMessageDialog(this, "账号信息异常，请重新登录", "错误", JOptionPane.ERROR_MESSAGE);
                        break;
                    case "-2":
                        JOptionPane.showMessageDialog(this, "登陆异常", "错误", JOptionPane.ERROR_MESSAGE);
                        break;
                }
            } catch (IllegalStateException | IOException e1) {
                JOptionPane.showMessageDialog(this, e1, "错误", JOptionPane.ERROR_MESSAGE);
            }
        }));

        // 查询课程按钮响应
        btnSearchCourse.addActionListener((e -> {
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
                return;
            }
        }));

        // 显示全部课程按钮响应
        btnAllCourse.addActionListener((e -> {
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
        }));

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
        // 确保使用窗口关闭按钮，能够正常退出，结束进程
        CoStFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 禁止对窗口大小进行缩放
        CoStFrame.setResizable(false);
        // 设置窗口可见
        CoStFrame.setVisible(true);
    }

//    public static void main(String[] args) {
//        new CourseTCFrame("090211");
//    }
}
