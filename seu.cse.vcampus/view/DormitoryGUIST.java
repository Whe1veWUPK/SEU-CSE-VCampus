package view;

import org.json.JSONObject;
import client.ClientSocket;
import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class DormitoryGUIST extends JFrame {
    private JScrollPane scpDemo;
    private JTableHeader jth;
    private JTable tabDemo;
    private JButton btnShow;
    private JButton bt1;
    private JButton bt2;
    private ClientSocket clientSocket = new ClientSocket();
    private static final String HOST = "localhost";// 服务器地址配置
    private static final int PORT = 11451;// 服务器端口配置
    private String userID = null;
    private JSONObject jsonObject = new JSONObject();

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

    public DormitoryGUIST(String id) {
        super("显示宿舍信息");        //JFrame的标题名称
        this.setSize(800, 600);        //控制窗体大小
        this.setLayout(null);        //自定义布局
        this.setLocation(400, 100);    //点击运行以后，窗体在屏幕的位置
        this.scpDemo = new JScrollPane();
        this.bt1 = new JButton("确定");
        this.bt2 = new JButton("取消");
        this.btnShow = new JButton("显示数据");
        this.bt1.setBounds(100, 480, 100, 30);
        this.bt2.setBounds(380, 480, 100, 30);
        this.scpDemo.setBounds(10, 50, 580, 400);    //设置滚动框大小
        this.btnShow.setBounds(10, 10, 120, 30);    //设置按钮
        this.userID=id;

        this.btnShow.addActionListener(new ActionListener()    //给“显示数据”按钮添加事件响应。
        {
            public void actionPerformed(ActionEvent ae) {
                btnShow_ActionPerformed(ae);
            }
        });
        /********按钮“确定”的响应*******/

        this.bt1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        /******按钮 “取消”的响应*****/

        this.bt2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        /******* 将组件加入到窗体中******/
        add(this.scpDemo);
        add(this.btnShow);
        add(this.bt1);
        add(this.bt2);
        this.setVisible(true);
    }

    /***连接数据库并显示到表格中***/
    public void btnShow_ActionPerformed(ActionEvent ae) {
        try {
            jsonObject.put("request","stuquerydormitory");
            jsonObject.put("id",this.userID);
            JSONObject res = clientSocket.connect(HOST, PORT, jsonObject);

            switch (res.getString("code")) {
                case "0":
                    String[] strs = res.getString("data").split(",");//根据，切分字符串
                    for(int i = 0;i < strs.length; i++){
                        System.out.println(strs[i]);
                    }
                    // 将查询获得的记录数据，转换成适合生成JTable的数据形式
                    Object[][] info = new String[100][100];
                    String[] title = {"DormitoryID", "MemberNum", " MemberID"};

                    System.out.println(strs.length);
                    int count1 = 0;
                    int count2 = 0;
                    int cnt = strs.length;
                    while (cnt!=0) {
                        info[count1][0] = strs[count2];
                        info[count1][1] = strs[count2+1];
                        info[count1][2] = strs[count2+2];
                        cnt-=3;
                        count1++;
                        count2+=3;
                    }
                    // 创建JTable
                    this.tabDemo = new JTable(info, title);
                    // 显示表头
                    this.jth = this.tabDemo.getTableHeader();
                    // 将JTable加入到带滚动条的面板中
                    this.scpDemo.getViewport().add(tabDemo);
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "显示失败", "错误", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}