package view;

import org.json.JSONObject;
import client.ClientSocket;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class LibrarySTDlg extends JFrame {
    private JScrollPane scpDemo;
    private JTableHeader jth;
    private JTable tabDemo;
    private JButton btnShow;
    private JButton btnHaveBorrowed;
    private JButton bt1;
    private JButton bt2;
    private JButton btnBorrow;
    private JButton btnSearch ;
    private JButton btnReturn;
    private JTextField BooKName = new JTextField(20);
    private JTextField BooKID = new JTextField(20);
    private JTextField ReturnBooKNum = new JTextField(20);
    private JTextField ReturnBooKID = new JTextField(20);
    private JTextField BooKNum = new JTextField(20);
    private ClientSocket clientSocket = new ClientSocket();
    private String UserID;

    private static final String HOST = "localhost";// 服务器地址配置
    private static final int PORT = 11451;// 服务器端口配置
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

    public LibrarySTDlg(String id) {
        super("显示所有图书");        //JFrame的标题名称
        this.setSize(800, 600);        //控制窗体大小
        this.setLayout(null);        //自定义布局
        this.setLocation(400, 100);    //点击运行以后，窗体在屏幕的位置
        this.scpDemo = new JScrollPane();
        this.bt1 = new JButton("确定");
        this.bt2 = new JButton("取消");
        this.btnShow = new JButton("显示数据");
        btnSearch= new JButton("搜索");
        this.btnSearch.setBounds(460, 10, 100, 30);    //设置按钮// 添加按钮 [搜索]
        this.btnHaveBorrowed= new JButton("查询已借图书");
        this.btnHaveBorrowed.setBounds(570, 10, 150, 30);    //设置按钮// 添加按钮 [搜索]
        this.bt1.setBounds(100, 480, 100, 30);
        this.bt2.setBounds(380, 480, 100, 30);
        this.scpDemo.setBounds(10, 50, 580, 400);    //设置滚动框大小
        this.btnShow.setBounds(10, 10, 120, 30);    //设置按钮
        this.btnBorrow = new JButton("借阅图书");//借阅图书
        this.btnBorrow.setBounds(600,80,100,30);
        this.btnReturn = new JButton("归还图书");//归还图书
        this.btnReturn.setBounds(600,310,100,30);
        this.UserID=id;

        // 添加输入框 [书名输入框]
        MatteBorder userBorder = new MatteBorder(0, 0, 0, 0, new Color(0x0F866E));
        BooKName.setBorder(userBorder);
        BooKName.setSelectedTextColor(new Color(0x0F866E));
        BooKName.setBounds(200, 10, 250, 30);
        BooKName.addFocusListener(new JTextFieldHintListener(BooKName, "书名"));

        // 添加输入框 [图书id输入框]
        MatteBorder IDBorder = new MatteBorder(0, 0, 0, 0, new Color(0x0F866E));
        BooKID.setBorder(IDBorder);
        BooKID.setSelectedTextColor(new Color(0x0F866E));
        BooKID.setBounds(600, 120, 150, 30);
        BooKID.addFocusListener(new JTextFieldHintListener(BooKID, "借阅图书id"));

        // 添加输入框 [借阅数目输入框]
        MatteBorder NumBorder = new MatteBorder(0, 0, 0, 0, new Color(0x0F866E));
        BooKNum.setBorder(NumBorder);
        BooKNum.setSelectedTextColor(new Color(0x0F866E));
        BooKNum.setBounds(600, 160, 150, 30);
        BooKNum.addFocusListener(new JTextFieldHintListener(BooKNum, "借阅数目"));

        // 添加输入框 [图书id输入框]
        MatteBorder ReturnIDBorder = new MatteBorder(0, 0, 0, 0, new Color(0x0F866E));
        ReturnBooKID.setBorder(ReturnIDBorder);
        ReturnBooKID.setSelectedTextColor(new Color(0x0F866E));
        ReturnBooKID.setBounds(600, 230, 150, 30);
        ReturnBooKID.addFocusListener(new JTextFieldHintListener(ReturnBooKID, "归还图书id"));

        // 添加输入框 [借阅数目输入框]
        MatteBorder ReturnNumBorder = new MatteBorder(0, 0, 0, 0, new Color(0x0F866E));
        ReturnBooKNum.setBorder(ReturnNumBorder);
        ReturnBooKNum.setSelectedTextColor(new Color(0x0F866E));
        ReturnBooKNum.setBounds(600, 270, 150, 30);
        ReturnBooKNum.addFocusListener(new JTextFieldHintListener(ReturnBooKNum, "归还数目"));


        this.btnShow.addActionListener(new ActionListener()    //给“显示数据”按钮添加事件响应。
        {
            public void actionPerformed(ActionEvent ae) {
                btnShow_ActionPerformed(ae);
            }
        });

        this.btnHaveBorrowed.addActionListener(new ActionListener()    //给“显示数据”按钮添加事件响应。
        {
            @Override
            public void actionPerformed(ActionEvent ae) {
                btnHaveBorrowed_ActionPerformed(ae);
            }
        });

        this.btnSearch.addActionListener(new ActionListener()    //给“搜索数据”按钮添加事件响应。
        {
            @Override
            public void actionPerformed(ActionEvent ae) {
                btnSearch_ActionPerformed(ae);
            }
        });

        this.btnBorrow.addActionListener(new ActionListener()    //给“搜索数据”按钮添加事件响应。
        {
            @Override
            public void actionPerformed(ActionEvent ae) {
                btnBorrow_ActionPerformed(ae);
            }
        });

        this.btnReturn.addActionListener(new ActionListener()    //给“搜索数据”按钮添加事件响应。
        {
            @Override
            public void actionPerformed(ActionEvent ae) {
                btnReturn_ActionPerformed(ae);
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
        add(this.btnHaveBorrowed);
        add(this.BooKName);
        add(this.BooKNum);
        add(this.BooKID);
        add(this.ReturnBooKID);
        add(this.ReturnBooKNum);
        add(this.btnBorrow);
        add(this.btnReturn);
        this.setVisible(true);
    }

//    public static void main(String[] args) {
//        new LibrarySTDlg("0");
//    }

    public void btnHaveBorrowed_ActionPerformed(ActionEvent ae){
        try {
            jsonObject.put("request","queryborrowedbook");
            jsonObject.put("id",this.UserID);
            JSONObject res = clientSocket.connect(HOST, PORT, jsonObject);
            switch (res.getString("code")) {
                case "0":
                    String[] strs = res.getString("data").split(",");//根据，切分字符串
                    for (int i = 0; i < strs.length; i++) {
                        System.out.println(strs[i]);
                    }
                    // 将查询获得的记录数据，转换成适合生成JTable的数据形式
                    Object[][] info = new String[100][100];
                    String[] title = {"BookID", "BookName"};

                    System.out.println(strs.length);
                    int count1 = 0;
                    int count2 = 0;
                    int cnt = strs.length-1;
                    while (cnt!=0) {
                        info[count1][0] = strs[count2];
                        info[count1][1] = strs[count2+1];
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
                    JOptionPane.showMessageDialog(this, "查询失败", "错误", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }
    public void btnSearch_ActionPerformed(ActionEvent ae){
        try {
            jsonObject.put("request","querybook");
            jsonObject.put("id",this.UserID);
            jsonObject.put("bookname",BooKName.getText());
            JSONObject res = clientSocket.connect(HOST, PORT, jsonObject);
            switch (res.getString("code")) {
                case "0":
                    String[] strs = res.getString("data").split(",");//根据，切分字符串
                    for (int i = 0; i < strs.length; i++) {
                        System.out.println(strs[i]);
                    }
                    // 将查询获得的记录数据，转换成适合生成JTable的数据形式
                    Object[][] info = new String[100][100];
                    String[] title = {"BookID", "BookName", " BookNum"};

                    System.out.println(strs.length);
                    int count1 = 0;
                    int count2 = 0;
                    int cnt = strs.length-1;
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
                    JOptionPane.showMessageDialog(this, "查询失败", "错误", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }
    /***连接数据库并显示到表格中***/
    public void btnShow_ActionPerformed(ActionEvent ae) {
        try {
            jsonObject.put("request","queryallbook");
            jsonObject.put("id",this.UserID);
            JSONObject res = clientSocket.connect(HOST, PORT, jsonObject);
            switch (res.getString("code")) {
                case "0":
                    String[] strs1 = res.getString("data").split(",");//根据，切分字符串
                    // 将查询获得的记录数据，转换成适合生成JTable的数据形式
                    Object[][] info = new String[100][100];
                    String[] title = {"BookID", "BookName", " BookNum"};

                    System.out.println(strs1.length);
                    int count1 = 0;
                    int count2 = 0;
                    int cnt = strs1.length-1;
                    while (cnt!=0) {
                        info[count1][0] = strs1[count2];
                        info[count1][1] = strs1[count2+1];
                        info[count1][2] = strs1[count2+2];
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

    public void btnBorrow_ActionPerformed(ActionEvent ae) {
        try {
            jsonObject.put("request","borrowbook");
            jsonObject.put("id",this.UserID);
            jsonObject.put("bookid",BooKID.getText());
            jsonObject.put("borrowcnt",BooKNum.getText());

            JSONObject res = clientSocket.connect(HOST, PORT, jsonObject);

            switch (res.getString("code")) {
                case "0":
                    JOptionPane.showMessageDialog(this, "借阅成功", "正确", JOptionPane.INFORMATION_MESSAGE);
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "借阅失败", "错误", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnReturn_ActionPerformed(ActionEvent ae) {
        try {
            jsonObject.put("request","returnbook");
            jsonObject.put("id",this.UserID);
            jsonObject.put("bookid",ReturnBooKID.getText());
            jsonObject.put("returncnt",ReturnBooKNum.getText());

            JSONObject res = clientSocket.connect(HOST, PORT, jsonObject);

            switch (res.getString("code")) {
                case "0":
                    JOptionPane.showMessageDialog(this, "借阅成功", "正确", JOptionPane.INFORMATION_MESSAGE);
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "借阅失败", "错误", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


