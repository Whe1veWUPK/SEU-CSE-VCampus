package view;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class CourseMyTeachingCourseDlg {
    private String _data;

    public CourseMyTeachingCourseDlg(String d) {
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
        String courseStuList;
        String courseTime;
        String[][] infoTable = new String[40][4];
        for(int i = 0; i < course.length; ++i) {
            String[] courseInfo = course[i].split(";");
            courseId = courseInfo[1];
            infoTable[i][0] = courseId;

            courseName = courseInfo[2];
            infoTable[i][1] = courseName;

            courseTime = inceptionOfCourseTime(courseInfo[3]);
            infoTable[i][2] = courseTime;

            courseStuList = courseInfo[4];
            infoTable[i][3] = courseStuList;
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
        JFrame myTeachingCourseDlg = new JFrame("我的任教课程");

        // 窗口大小
        myTeachingCourseDlg.setSize(1600, 1000);
        // 布局先设为null
        myTeachingCourseDlg.setLayout(null);

        // 添加滚轮条
        JScrollPane scp = new JScrollPane();
        scp.setBounds(0, 0, 1600, 1000);
        myTeachingCourseDlg.add(scp);

        // 创建表格
        Object[][] info = inception();
        String[] title = {"课程编号", "课程名称", "上课时间", "选课名单"};
        JTable tableCourse = new JTable(info, title);
        tableCourse.setFont(new Font("华文中宋", Font.PLAIN, 25));
        tableCourse.setRowHeight(40);
        JTableHeader jth = tableCourse.getTableHeader();
        jth.setFont(new Font("华文中宋", Font.PLAIN, 22));
        scp.getViewport().add(tableCourse);

        // 设置相对位置：屏幕中间
        myTeachingCourseDlg.setLocationRelativeTo(null);
        // 禁止对窗口大小进行缩放
        myTeachingCourseDlg.setResizable(false);
        // 设置窗口可见
        myTeachingCourseDlg.setVisible(true);
    }

//    public static void main(String[] args) {
//        new CourseMyTeachingCourseDlg(";2;test3;2,1,15,0,17,0,; ;|;3;test3;3,1,17,0,19,0,; ;|;0;test;0,1,13,0,15,0,;123,456,789,222,33;|");
//    }
}
