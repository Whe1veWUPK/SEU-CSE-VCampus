package view;

/**
 * @author wwq
 * 学生身份的选课界面，包含选课、我的课表、查询课程和查询全部课程的功能
 */

import org.json.JSONObject;
import client.ClientSocket;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class CourseSTFrame extends JFrame {
    private String _clientID;
    private ClientSocket _clientSocket = new ClientSocket();
    private static final String HOST = "localhost";// 服务器地址配置
    private static final int PORT = 11451;// 服务器端口配置
    private JSONObject _jsonObject = new JSONObject();

    public CourseSTFrame(String id) {
        _clientID = id;
        init();
    }

    void init() {
        JFrame CoStFrame = new JFrame("学生选课");

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
        JLabel text_clientID = new JLabel(_clientID);
        text_clientID.setForeground(new Color(0x3BA981)); // 0x0F866E
        text_clientID.setFont(new Font("微软雅黑", Font.BOLD, 80));
        text_clientID.setBounds(350, 270, 800, 100);
        CoStFrame.add(text_clientID);

        // 添加按钮 [选课]
        JButton btnCourseSel = new JButton("选课/退课");
        btnCourseSel.setForeground(new Color(0x3BA981));
        btnCourseSel.setFont(new Font("华文中宋", Font.PLAIN, 25));
        btnCourseSel.setBackground(new Color(0xDDE9E0));
        btnCourseSel.setBorderPainted(false);
        btnCourseSel.setBounds(180, 500, 250, 150);
        btnCourseSel.setHorizontalTextPosition(SwingConstants.CENTER);
        btnCourseSel.setVerticalTextPosition(SwingConstants.CENTER);
        btnCourseSel.setOpaque(true);
        btnCourseSel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        CoStFrame.add(btnCourseSel);

        // 添加按钮 [查询课表]
        JButton btnSearchCurriculum = new JButton("我的课表");
        btnSearchCurriculum.setForeground(new Color(0x3BA981));
        btnSearchCurriculum.setFont(new Font("华文中宋", Font.PLAIN, 25));
        btnSearchCurriculum.setBackground(new Color(0xDDE9E0));
        btnSearchCurriculum.setBorderPainted(false);
        btnSearchCurriculum.setBounds(480, 500, 250, 150);
        btnSearchCurriculum.setHorizontalTextPosition(SwingConstants.CENTER);
        btnSearchCurriculum.setVerticalTextPosition(SwingConstants.CENTER);
        btnSearchCurriculum.setOpaque(true);
        btnSearchCurriculum.setCursor(new Cursor(Cursor.HAND_CURSOR));
        CoStFrame.add(btnSearchCurriculum);

        // 添加按钮 [查询课程]
        JButton btnSearchCourse = new JButton("查询课程");
        btnSearchCourse.setForeground(new Color(0x3BA981));
        btnSearchCourse.setFont(new Font("华文中宋", Font.PLAIN, 25));
        btnSearchCourse.setBackground(new Color(0xDDE9E0));
        btnSearchCourse.setBorderPainted(false);
        btnSearchCourse.setBounds(180, 700, 250, 150);
        btnSearchCourse.setHorizontalTextPosition(SwingConstants.CENTER);
        btnSearchCourse.setVerticalTextPosition(SwingConstants.CENTER);
        btnSearchCourse.setOpaque(true);
        btnSearchCourse.setCursor(new Cursor(Cursor.HAND_CURSOR));
        CoStFrame.add(btnSearchCourse);

        // 添加按钮 [显示全部课程]
        JButton btnAllCourse = new JButton("全部课程");
        btnAllCourse.setForeground(new Color(0x3BA981));
        btnAllCourse.setFont(new Font("华文中宋", Font.PLAIN, 25));
        btnAllCourse.setBackground(new Color(0xDDE9E0));
        btnAllCourse.setBorderPainted(false);
        btnAllCourse.setBounds(480, 700, 250, 150);
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
        textImage.setBounds(0, 0, 1600, 1000);
        CoStFrame.add(textImage);

        // 选课按钮响应
        btnCourseSel.addActionListener((e -> {
            try {
                new CourseSelAndDelDlg(_clientID);
            } catch (IllegalStateException e1) {
                JOptionPane.showMessageDialog(this, e1, "错误", JOptionPane.ERROR_MESSAGE);
            }
        }));

        // 查询我的课表按钮响应
        btnSearchCurriculum.addActionListener((e -> {
            try {
                _jsonObject.put("request", "stuquerysel");
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
        // 禁止对窗口大小进行缩放
        CoStFrame.setResizable(false);
        // 设置窗口可见
        CoStFrame.setVisible(true);
    }
//    public static void main(String[] args) {
//        new CourseSTFrame("090211");
//    }
}
