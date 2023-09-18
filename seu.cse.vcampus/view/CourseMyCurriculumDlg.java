package view;

/**
 * @author wwq
 * 我的课表界面，以表格形式展示，未编排时间
 */

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class CourseMyCurriculumDlg {
    private String _data;

    public CourseMyCurriculumDlg(String d) {
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
        String[][] infoTable = new String[40][4];
        for(int i = 0; i < course.length; ++i) {
            String[] courseInfo = course[i].split(";");
            courseId = courseInfo[1];
            infoTable[i][0] = courseId;

            courseName = courseInfo[2];
            infoTable[i][1] = courseName;

            courseTchr = courseInfo[3];
            infoTable[i][2] = courseTchr;

            courseTime = inceptionOfCourseTime(courseInfo[4]);
            infoTable[i][3] = courseTime;
        }

        return infoTable;
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
        JFrame myCurriculumDlg = new JFrame("我的课表");

        // 窗口大小
        myCurriculumDlg.setSize(1600, 1000);
        // 布局先设为null
        myCurriculumDlg.setLayout(null);

        // 添加滚轮条
        JScrollPane scp = new JScrollPane();
        scp.setBounds(0, 0, 1600, 1000);
        myCurriculumDlg.add(scp);

        // 创建表格
        Object[][] info = inception();
        String[] title = {"课程编号", "课程名称", "任课老师", "上课时间"};
        JTable tableCourse = new JTable(info, title);
        tableCourse.setFont(new Font("华文中宋", Font.PLAIN, 25));
        tableCourse.setRowHeight(40);
        JTableHeader jth = tableCourse.getTableHeader();
        jth.setFont(new Font("华文中宋", Font.PLAIN, 22));
        scp.getViewport().add(tableCourse);

        // 设置相对位置：屏幕中间
        myCurriculumDlg.setLocationRelativeTo(null);
        // 禁止对窗口大小进行缩放
        myCurriculumDlg.setResizable(false);
        // 设置窗口可见
        myCurriculumDlg.setVisible(true);
    }
}

