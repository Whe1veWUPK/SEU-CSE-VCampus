package view;

import org.json.JSONObject;
import api.CourseSel;
import client.ClientSocket;

import javax.swing.*;
import java.awt.*;

public class CourseSelAndDelDlg extends JFrame {
    private String _clientID;

    public CourseSelAndDelDlg(String userId) {
        _clientID = userId;
        init();
    }

    void init() {
        JFrame InfoInput = new JFrame("选课/退课");

        // 界面大小
        InfoInput.setSize(400, 150);
        // 界面布局先设为null
        InfoInput.setLayout(null);

        // 设置选课按钮
        JButton btnSelCourse = new JButton("选课");
        btnSelCourse.setForeground(new Color(0x0F866E));
        btnSelCourse.setFont(new Font("华文中宋", Font.PLAIN, 18));
        btnSelCourse.setBorderPainted(false);
        btnSelCourse.setBounds(30, 40, 150, 30);
        btnSelCourse.setHorizontalTextPosition(SwingConstants.CENTER);
        btnSelCourse.setVerticalTextPosition(SwingConstants.CENTER);
        btnSelCourse.setOpaque(false);
        btnSelCourse.setCursor(new Cursor(Cursor.HAND_CURSOR));
        InfoInput.add(btnSelCourse);

        // 设置退课按钮
        JButton btnDelCourse = new JButton("退课");
        btnDelCourse.setForeground(new Color(0x0F866E));
        btnDelCourse.setFont(new Font("华文中宋", Font.PLAIN, 18));
        btnDelCourse.setBorderPainted(false);
        btnDelCourse.setBounds(220, 40, 150, 30);
        btnDelCourse.setHorizontalTextPosition(SwingConstants.CENTER);
        btnDelCourse.setVerticalTextPosition(SwingConstants.CENTER);
        btnDelCourse.setOpaque(false);
        btnDelCourse.setCursor(new Cursor(Cursor.HAND_CURSOR));
        InfoInput.add(btnDelCourse);

        // 退课按钮响应
        btnDelCourse.addActionListener(e -> {
            try {
                new CourseDelDlg(_clientID);
                InfoInput.dispose();
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(this, e1, "错误", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 选课按钮响应
        btnSelCourse.addActionListener(e -> {
            try {
                new CourseSelDlg(_clientID);
                InfoInput.dispose();
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(this, e1, "错误", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 设置相对位置：屏幕中间
        InfoInput.setLocationRelativeTo(null);
        // 禁止对窗口大小进行缩放
        InfoInput.setResizable(false);
        // 设置窗口可见
        InfoInput.setVisible(true);
    }
}
