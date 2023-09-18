package view;

import client.ClientSocket;
import org.json.JSONObject;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class DormitoryGUIAD extends JFrame{

    private JScrollPane scpDemo;
    private JTableHeader jth;
    private JTable tabDemo;
    private JButton btnShow;
    private JButton bt1;
    private JButton bt2;
    private JButton btnSearch ;
    private JButton btnAddMember;
    private JButton btnDeleteMember;
    private JTextField booKName = new JTextField(20);
    private JTextField addBooKName = new JTextField(20);
    private JTextField booKID = new JTextField(20);
    private JTextField returnBooKNum = new JTextField(20);
    private JTextField booKNum = new JTextField(20);
    private JTextField addBookid = new JTextField(20);
    private JTextField addReaderid = new JTextField(20);
    private ClientSocket clientSocket = new ClientSocket();

    private static final String HOST = "localhost";// 服务器地址配置
    private static final int PORT = 11451;// 服务器端口配置
    private JSONObject jsonObject = new JSONObject();
    private String UserID = null;


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

    public DormitoryGUIAD(String id) {
        super("图书馆——管理员");        //JFrame的标题名称
        this.setSize(800, 600);        //控制窗体大小
        this.setLayout(null);        //自定义布局
        this.setLocation(400, 100);    //点击运行以后，窗体在屏幕的位置
        this.scpDemo = new JScrollPane();
        this.bt1 = new JButton("确定");
        this.bt2 = new JButton("取消");
        this.btnShow = new JButton("显示全部宿舍信息");
        btnSearch= new JButton("搜索");
        this.btnSearch.setBounds(460, 10, 100, 30);    //设置按钮// 添加按钮 [搜索]
        this.bt1.setBounds(100, 480, 100, 30);
        this.bt2.setBounds(380, 480, 100, 30);
        this.scpDemo.setBounds(10, 50, 580, 400);    //设置滚动框大小
        this.btnShow.setBounds(10, 10, 140, 30);    //设置按钮
        this.btnAddMember = new JButton("添加宿舍成员");//归还图书
        this.btnAddMember.setBounds(600,50,100,30);
        this.btnDeleteMember = new JButton("删除宿舍成员");//归还图书
        this.btnDeleteMember.setBounds(600,90,100,30);
        this.UserID=id;

        // 添加输入框 [书名输入框]
        MatteBorder userBorder = new MatteBorder(0, 0, 0, 0, new Color(0x0F866E));
        booKName.setBorder(userBorder);
        booKName.setSelectedTextColor(new Color(0x0F866E));
        booKName.setBounds(200, 10, 250, 30);
        booKName.addFocusListener(new JTextFieldHintListener(booKName, "宿舍号"));

        // 添加借阅者输入框 [书籍id输入框]
        MatteBorder AddbookidBorder = new MatteBorder(0, 0, 0, 0, new Color(0x0F866E));
        addBookid.setBorder(AddbookidBorder);
        addBookid.setSelectedTextColor(new Color(0x0F866E));
        addBookid.setBounds(600, 140, 150, 30);
        addBookid.addFocusListener(new JTextFieldHintListener(addBookid, "宿舍id"));

        // 添加输入框 [借阅者输入框]
        MatteBorder AddReaderBorder = new MatteBorder(0, 0, 0, 0, new Color(0x0F866E));
        addReaderid.setBorder(AddReaderBorder);
        addReaderid.setSelectedTextColor(new Color(0x0F866E));
        addReaderid.setBounds(600, 180, 150, 30);
        addReaderid.addFocusListener(new JTextFieldHintListener(addReaderid, "添加（删除）宿舍成员的id"));

        this.btnShow.addActionListener(new ActionListener()    //给“显示数据”按钮添加事件响应。
        {
            public void actionPerformed(ActionEvent ae) {
                btnShow_ActionPerformed(ae);
            }
        });

        this.btnSearch.addActionListener(new ActionListener()    //给“搜索数据”按钮添加事件响应。
        {
            @Override
            public void actionPerformed(ActionEvent ae) {
                btnSearch_ActionPerformed(ae);
            }
        });

        this.btnAddMember.addActionListener(new ActionListener()    //给“搜索数据”按钮添加事件响应。
        {
            @Override
            public void actionPerformed(ActionEvent ae) {
                btnAddMember_ActionPerformed(ae);
            }
        });

        this.btnDeleteMember.addActionListener(new ActionListener()    //给“搜索数据”按钮添加事件响应。
        {
            @Override
            public void actionPerformed(ActionEvent ae) {
                btnDeleteMember_ActionPerformed(ae);
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
        add(this.btnSearch);
        add(this.scpDemo);
        add(this.btnShow);
        add(this.bt1);
        add(this.bt2);
        add(this.booKName);
        add(this.addBooKName);
        add(this.booKNum);
        add(this.booKID);
        add(this.returnBooKNum);
        add(this.addBookid);
        add(this.addReaderid);
        add(this.btnAddMember);
        add(this.btnDeleteMember);
        this.setVisible(true);
    }

//    public static void main(String[] args) {
//        new DormitoryGUIAD("00");
//    }
    public void btnSearch_ActionPerformed(ActionEvent ae){
        try {
            jsonObject.put("request","adquerydormitory");
            jsonObject.put("id",this.UserID);
            jsonObject.put("domid",booKName.getText());
            JSONObject res = clientSocket.connect(HOST, PORT, jsonObject);
            System.out.println(res.toString());
            switch (res.getString("code")) {
                case "0":
                    String[] strs = res.getString("data").split(",");//根据，切分字符串
                    for (int i = 0; i < strs.length; i++) {
                        System.out.println(strs[i]);
                    }
                    // 将查询获得的记录数据，转换成适合生成JTable的数据形式
                    Object[][] info = new String[100][100];
                    String[] title = {"DormitoryID", "MemberNum", " MemberName"};

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
                    JOptionPane.showMessageDialog(this, "查阅失败", "错误", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }
    /***连接数据库并显示到表格中***/
    public void btnShow_ActionPerformed(ActionEvent ae) {
        try {
            jsonObject.put("request","queryalldormitory");
            jsonObject.put("id",this.UserID);
            JSONObject res = clientSocket.connect(HOST, PORT, jsonObject);
            switch (res.getString("code")) {
                case "0":
                    String[] strs1 = res.getString("data").split(",");//根据，切分字符串

                    // 将查询获得的记录数据，转换成适合生成JTable的数据形式
                    Object[][] info = new String[100][100];
                    String[] title = {"DormitoryID", "MemberNum"};

                    System.out.println(strs1.length);
                    int count1 = 0;
                    int count2 = 0;
                    int cnt = strs1.length-1;
                    while (cnt!=0) {
                        info[count1][0] = strs1[count2];
                        info[count1][1] = strs1[count2+1];
                        cnt-=2;
                        count1++;
                        count2+=2;
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

    public void btnAddMember_ActionPerformed(ActionEvent ae) {
        try {
            jsonObject.put("request","addmember");
            jsonObject.put("id",this.UserID);
            jsonObject.put("domid",addBookid.getText());
            jsonObject.put("addid",addReaderid.getText());

            JSONObject res = clientSocket.connect(HOST, PORT, jsonObject);

            switch (res.getString("code")) {
                case "0":
                    JOptionPane.showMessageDialog(this, "添加成功", "正确", JOptionPane.INFORMATION_MESSAGE);
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "添加失败", "错误", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void btnDeleteMember_ActionPerformed(ActionEvent ae) {
        try {
            jsonObject.put("request","delmember");
            jsonObject.put("id",this.UserID);
            jsonObject.put("domid",addBookid.getText());
            jsonObject.put("delid",addReaderid.getText());

            System.out.println(jsonObject.toString());
            JSONObject res = clientSocket.connect(HOST, PORT, jsonObject);

            switch (res.getString("code")) {
                case "0":
                    JOptionPane.showMessageDialog(this, "删除成功", "正确", JOptionPane.INFORMATION_MESSAGE);
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "删除失败", "错误", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
