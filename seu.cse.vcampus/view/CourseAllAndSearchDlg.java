package view;

/**
 * @author wwq
 * 显示全部课程的界面，以表格形式呈现，包含课程的五个属性
 * 同时也是查询功能的界面
 */

import client.ClientSocket;
import org.json.JSONObject;


import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class CourseAllAndSearchDlg extends JFrame {
    private String _clientID;
    private ClientSocket _clientSocket = new ClientSocket();
    private static final String HOST = "localhost";// 服务器地址配置
    private static final int PORT = 11451;// 服务器端口配置
    private JSONObject _jsonObject = new JSONObject();
    private String _data;

    public CourseAllAndSearchDlg(String d, String id) {
        _clientID = id;
        _data = d;
        init();
    }

    public String dayMatch(String day) {
        String temp = "";
        switch (day) {
            case "1":
                temp = "一";
                break;
            case "2":
                temp = "二";
                break;
            case "3":
                temp = "三";
                break;
            case "4":
                temp = "四";
                break;
            case "5":
                temp = "五";
                break;
            case "6":
                temp = "六";
                break;
            case "7":
                temp = "日";
                break;
        }
        return temp;
    }

    public String inceptionOfCourseTime(String courseTime) {
        String[] tmp = courseTime.split(",");
        try {
            String day = tmp[1];
            String startHour = tmp[2];
            String startMin = Integer.parseInt(tmp[3]) <= 9 ? ('0'+tmp[3]) : tmp[3];
            String endHour = tmp[4];
            String endMin = Integer.parseInt(tmp[3]) <= 9 ? ('0'+tmp[5]) : tmp[5];
            String res = "星期"+dayMatch(day)+"   "+startHour+':'+startMin+" - "+endHour+':'+endMin;
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String[][] inception() {
        String[] course = _data.split("\\|");
        String courseId = "";
        String courseName;
        String courseTchr;
        String courseTime;
        String courseCapa;
        String[][] infoTable = new String[100][5];
        for(int i = 0; i < course.length; ++i) {
            String[] courseInfo = course[i].split(";");
            courseId = courseInfo[0];
            infoTable[i][0] = courseId;

            courseName = courseInfo[1];
            infoTable[i][1] = courseName;

            courseTchr = courseInfo[2];
            infoTable[i][2] = courseTchr;

            courseTime = inceptionOfCourseTime(courseInfo[3]);
            infoTable[i][3] = courseTime;

            courseCapa = courseInfo[4];
            infoTable[i][4] = courseCapa;
        }

        return infoTable;
    }

    void init() {
        SwingUtilities.invokeLater(() -> {
            JFrame courseAll = new JFrame("所有课程");

            // 窗口大小
            courseAll.setSize(1600, 1000);
            // 布局先设为null
            courseAll.setLayout(null);

            // 添加输入框 [按课程ID搜索]
            JTextField searchById = new JTextField(20);
            MatteBorder searchByIdBorder = new MatteBorder(2, 2, 2, 2, new Color(0x0F866E));
            searchById.setBorder(searchByIdBorder);
            searchById.setFont(new Font("华文中宋", Font.PLAIN, 20));
            searchById.setBackground(new Color(0xDDE9A9));
            searchById.setSelectedTextColor(new Color(0x0F866E));
            searchById.setBounds(100, 30, 400, 40);
            searchById.addFocusListener(new JTextFieldHintListener(searchById, "输入课程ID搜索"));
            courseAll.add(searchById);

            // 添加按钮 [查询by ID]
            JButton btnSearchById = new JButton("搜索");
            btnSearchById.setForeground(new Color(0x0F866E));
            btnSearchById.setBackground(new Color(0xDDE9A9));
            btnSearchById.setFont(new Font("华文中宋", Font.PLAIN, 30));
            btnSearchById.setBorderPainted(true);
            btnSearchById.setBounds(550, 30, 150, 40);
            btnSearchById.setHorizontalTextPosition(SwingConstants.CENTER);
            btnSearchById.setVerticalTextPosition(SwingConstants.CENTER);
            btnSearchById.setOpaque(true);
            btnSearchById.setCursor(new Cursor(Cursor.HAND_CURSOR));
            courseAll.add(btnSearchById);

            // 添加输入框 [按课程名称搜索]
            JTextField searchByName = new JTextField(20);
            MatteBorder searchByNameBorder = new MatteBorder(2, 2, 2, 2, new Color(0x0F866E));
            searchByName.setBorder(searchByNameBorder);
            searchByName.setFont(new Font("华文中宋", Font.PLAIN, 20));
            searchByName.setBackground(new Color(0xDDE9A9));
            searchByName.setSelectedTextColor(new Color(0x0F866E));
            searchByName.setBounds(900, 30, 400, 40);
            searchByName.addFocusListener(new JTextFieldHintListener(searchByName, "输入课程名称搜索"));
            courseAll.add(searchByName);

            // 添加按钮 [查询by Name]
            JButton btnSearchByName = new JButton("搜索");
            btnSearchByName.setForeground(new Color(0x0F866E));
            btnSearchByName.setBackground(new Color(0xDDE9A9));
            btnSearchByName.setFont(new Font("华文中宋", Font.PLAIN, 30));
            btnSearchByName.setBorderPainted(true);
            btnSearchByName.setBounds(1350, 30, 150, 40);
            btnSearchByName.setHorizontalTextPosition(SwingConstants.CENTER);
            btnSearchByName.setVerticalTextPosition(SwingConstants.CENTER);
            btnSearchByName.setOpaque(true);
            btnSearchByName.setCursor(new Cursor(Cursor.HAND_CURSOR));
            courseAll.add(btnSearchByName);

            // 添加滚轮条
            JScrollPane scp = new JScrollPane();
            scp.setBounds(0, 100, 1600, 900);
            courseAll.add(scp);

            // 创建表格
            Object[][] info = inception(); // 处理data
            String[] title = {"课程编号", "课程名称", "任课老师", "上课时间", "课容量"};

            DefaultTableModel tableModel = new DefaultTableModel();
            JTable tableCourse = new JTable(tableModel);
            tableModel.setDataVector(info, title);
            tableCourse.setFont(new Font("华文中宋", Font.PLAIN, 22));
            tableCourse.setRowHeight(40);
            JTableHeader jth = tableCourse.getTableHeader();
            jth.setFont(new Font("华文中宋", Font.PLAIN, 22));
            scp.getViewport().add(tableCourse);

            // 通过id找课程的按钮响应
            btnSearchById.addActionListener(e -> {
                String searchText = searchById.getText().toLowerCase();
                DefaultTableModel model = (DefaultTableModel) tableCourse.getModel();
                if(searchById.getText().equals("输入课程ID搜索")) {
                    tableModel.setDataVector(info, title);
                } else {
                    model.setRowCount(0); // 清空表格
                    for (int row = 0; row < info.length; ++row) {
                        String courseId = (String) info[row][0];
                        String courseName = (String) info[row][1];
                        String courseTchr = (String) info[row][2];
                        String courseTime = (String) info[row][3];
                        String courseCap = (String) info[row][4];

                        if (courseId.toLowerCase().contains(searchText)) {
                            model.addRow(new Object[]{courseId, courseName, courseTchr, courseTime, courseCap});
                        }
                    }
                }
            });

            // 通过名称寻找课程的按钮响应
            btnSearchByName.addActionListener(e -> {
                String searchText = searchByName.getText().toLowerCase();
                DefaultTableModel model = (DefaultTableModel) tableCourse.getModel();
                if(searchByName.getText().equals("输入课程名称搜索")) {
                    tableModel.setDataVector(info, title);
                } else {
                    model.setRowCount(0); // 清空表格
                    for (int row = 0; row < info.length; ++row) {
                        String courseId = (String) info[row][0];
                        String courseName = (String) info[row][1];
                        String courseTchr = (String) info[row][2];
                        String courseTime = (String) info[row][3];
                        String courseCap = (String) info[row][4];

                        if (courseName.toLowerCase().contains(searchText)) {
                            model.addRow(new Object[]{courseId, courseName, courseTchr, courseTime, courseCap});
                        }
                    }
                }
            });

            // 设置相对位置：屏幕中间
            courseAll.setLocationRelativeTo(null);
            // 禁止对窗口大小进行缩放
            courseAll.setResizable(false);
            // 设置窗口可见
            courseAll.setVisible(true);
        });
    }
//
//    public static void main(String[] args) {
//        new CourseAllAndSearchDlg("1;test3;K;1,1,15,0,17,0,;2;|2;test3;Yuki;2,1,15,0,17,0,;2;|3;test3;Yuki;3,1,17,0,19,0,;2;|0;test;Yuki;0,1,13,0,15,0,;1;|", "test1");
//    }
}
