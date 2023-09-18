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


public class LibrarianGUI extends JFrame{
    private String UserID;
    private JScrollPane scpDemo;
    private JTableHeader jth;
    private JTable tabDemo;
    private JButton btnShow;
    private JButton btnHaveBorrowed;
    private JButton bt1;
    private JButton bt2;
    private JButton btnAdd;
    private JButton btnChange;
    private JButton btnSearch ;
    private JButton btnDelete;
    private JButton btnAddReader;
    private JButton btnDeleteReader;
    private JTextField BooKName = new JTextField(20);
    private JTextField AddBooKName = new JTextField(20);
    private JTextField BooKID = new JTextField(20);
    private JTextField ReturnBooKNum = new JTextField(20);
    private JTextField DeleteBooKID = new JTextField(20);
    private JTextField BooKNum = new JTextField(20);
    private JTextField Addbookid = new JTextField(20);
    private JTextField Addreaderid = new JTextField(20);
    private JTextField SearchReaderid = new JTextField(20);
    private ClientSocket clientSocket = new ClientSocket();

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

    public LibrarianGUI(String id) {
        super("图书馆——管理员");        //JFrame的标题名称
        this.setSize(800, 600);        //控制窗体大小
        this.setLayout(null);        //自定义布局
        this.setLocation(400, 100);    //点击运行以后，窗体在屏幕的位置
        this.scpDemo = new JScrollPane();
        this.scpDemo.setBounds(10, 50, 580, 400);
        this.bt1 = new JButton("确定");
        this.bt2 = new JButton("取消");
        this.btnShow = new JButton("显示数据");
        this.btnSearch= new JButton("搜索");
        this.btnSearch.setBounds(440, 10, 100, 30);
        this.btnHaveBorrowed= new JButton("查询已借图书");
        this.btnHaveBorrowed.setBounds(560, 10, 100, 30);
        this.bt1.setBounds(100, 480, 100, 30);
        this.bt2.setBounds(380, 480, 100, 30);
        this.btnShow.setBounds(10, 10, 120, 30);
        this.btnAdd = new JButton("添加图书");//借阅图书
        this.btnAdd.setBounds(600,50,100,30);
        this.btnChange = new JButton("修改图书信息");//更改图书信息
        this.btnChange.setBounds(600,90,100,30);
        this.btnDelete = new JButton("删除图书");//归还图书
        this.btnDelete.setBounds(600,250,100,30);
        this.btnAddReader = new JButton("添加图书借阅者");//归还图书
        this.btnAddReader.setBounds(600,330,120,30);
        this.btnDeleteReader = new JButton("删除图书借阅者");//归还图书
        this.btnDeleteReader.setBounds(600,450,120,30);

        this.UserID=id;

        // 添加输入框 [书名输入框]
        MatteBorder userBorder = new MatteBorder(0, 0, 0, 0, new Color(0x0F866E));
        BooKName.setBorder(userBorder);
        BooKName.setSelectedTextColor(new Color(0x0F866E));
        BooKName.setBounds(170, 10, 250, 30);
        BooKName.addFocusListener(new JTextFieldHintListener(BooKName, "书名"));

        // 添加输入框 [图书id输入框]
        MatteBorder IDBorder = new MatteBorder(0, 0, 0, 0, new Color(0x0F866E));
        BooKID.setBorder(IDBorder);
        BooKID.setSelectedTextColor(new Color(0x0F866E));
        BooKID.setBounds(600, 130, 150, 30);
        BooKID.addFocusListener(new JTextFieldHintListener(BooKID, "添加(修改前)图书id"));

        // 添加输入框 [借阅数目输入框]
        MatteBorder NumBorder = new MatteBorder(0, 0, 0, 0, new Color(0x0F866E));
        BooKNum.setBorder(NumBorder);
        BooKNum.setSelectedTextColor(new Color(0x0F866E));
        BooKNum.setBounds(600, 170, 150, 30);
        BooKNum.addFocusListener(new JTextFieldHintListener(BooKNum, "添加（修改）借阅数目"));

        // 添加输入框 [图书名称输入框]
        MatteBorder NameBorder = new MatteBorder(0, 0, 0, 0, new Color(0x0F866E));
        AddBooKName.setBorder(NameBorder);
        AddBooKName.setSelectedTextColor(new Color(0x0F866E));
        AddBooKName.setBounds(600, 210, 150, 30);
        AddBooKName.addFocusListener(new JTextFieldHintListener(AddBooKName, "添加（修改）图书名称"));

        // 添加输入框 [删除图书id输入框]
        MatteBorder DeleteIDBorder = new MatteBorder(0, 0, 0, 0, new Color(0x0F866E));
        DeleteBooKID.setBorder(DeleteIDBorder);
        DeleteBooKID.setSelectedTextColor(new Color(0x0F866E));
        DeleteBooKID.setBounds(600, 290, 150, 30);
        DeleteBooKID.addFocusListener(new JTextFieldHintListener(DeleteBooKID, "删除图书id"));

        // 添加借阅者输入框 [书籍id输入框]
        MatteBorder AddbookidBorder = new MatteBorder(0, 0, 0, 0, new Color(0x0F866E));
        Addbookid.setBorder(AddbookidBorder);
        Addbookid.setSelectedTextColor(new Color(0x0F866E));
        Addbookid.setBounds(600, 370, 150, 30);
        Addbookid.addFocusListener(new JTextFieldHintListener(Addbookid, "图书id"));

        // 添加输入框 [借阅者输入框]
        MatteBorder AddReaderBorder = new MatteBorder(0, 0, 0, 0, new Color(0x0F866E));
        Addreaderid.setBorder(AddReaderBorder);
        Addreaderid.setSelectedTextColor(new Color(0x0F866E));
        Addreaderid.setBounds(600, 410, 150, 30);
        Addreaderid.addFocusListener(new JTextFieldHintListener(Addreaderid, "添加（删除）借阅者id"));

        // 添加输入框 [借阅者输入框2]
        MatteBorder SearchReaderBorder = new MatteBorder(0, 0, 0, 0, new Color(0x0F866E));
        SearchReaderid.setBorder(SearchReaderBorder);
        SearchReaderid.setSelectedTextColor(new Color(0x0F866E));
        SearchReaderid.setBounds(670, 10, 100, 30);
        SearchReaderid.addFocusListener(new JTextFieldHintListener(SearchReaderid, "查询借阅者id"));

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

        this.btnAdd.addActionListener(new ActionListener()    //给“搜索数据”按钮添加事件响应。
        {
            @Override
            public void actionPerformed(ActionEvent ae) {
                btnAdd_ActionPerformed(ae);
            }
        });

        this.btnDelete.addActionListener(new ActionListener()    //给“搜索数据”按钮添加事件响应。
        {
            @Override
            public void actionPerformed(ActionEvent ae) {
                btnDelete_ActionPerformed(ae);
            }
        });

        this.btnChange.addActionListener(new ActionListener()    //给“搜索数据”按钮添加事件响应。
        {
            @Override
            public void actionPerformed(ActionEvent ae) {
                btnChange_ActionPerformed(ae);
            }
        });

        this.btnAddReader.addActionListener(new ActionListener()    //给“搜索数据”按钮添加事件响应。
        {
            @Override
            public void actionPerformed(ActionEvent ae) {
                btnAddReader_ActionPerformed(ae);
            }
        });

        this.btnDeleteReader.addActionListener(new ActionListener()    //给“搜索数据”按钮添加事件响应。
        {
            @Override
            public void actionPerformed(ActionEvent ae) {
                btnDeleteReader_ActionPerformed(ae);
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
        add(this.AddBooKName);
        add(this.BooKNum);
        add(this.BooKID);
        add(this.DeleteBooKID);
        add(this.ReturnBooKNum);
        add(this.btnAdd);
        add(this.btnDelete);
        add(this.btnChange);
        add(this.Addbookid);
        add(this.Addreaderid);
        add(this.btnAddReader);
        add(this.btnDeleteReader);
        add(this.SearchReaderid);
        this.setVisible(true);
    }

//    public static void main(String[] args) {
//        new LibrarianGUI("00");
//    }

    public void btnHaveBorrowed_ActionPerformed(ActionEvent ae){
        try {
            jsonObject.put("request","queryborrowedbook");
            jsonObject.put("id",SearchReaderid.getText());
            JSONObject res = clientSocket.connect(HOST, PORT, jsonObject);

            switch (res.getString("code")) {
                case "0":
                    String[] strs = res.getString("data").split(",");//根据，切分字符串
                    for (int i = 0; i < strs.length; i++) {
                        System.out.println(strs[i]);
                    }
                    // 将查询获得的记录数据，转换成适合生成JTable的数据形式
                    Object[][] info = new String[100][100];
                    String[] title = {"Number", "BookID", " BookName"};

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
                    JOptionPane.showMessageDialog(this, "添加失败", "错误", JOptionPane.ERROR_MESSAGE);
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
                    String[] title = {"BookID", "BookName", "BookNum", "BookReader"};

                    System.out.println(strs.length);
                    int count1 = 0;
                    int count2 = 0;
                    int cnt = strs.length-1;
                    while (cnt!=0) {
                        info[count1][0] = strs[count2];
                        info[count1][1] = strs[count2+1];
                        info[count1][2] = strs[count2+2];
                        info[count1][3] = strs[count2+3];
                        cnt-=4;
                        count1++;
                        count2+=4;
                    }
                    // 创建JTable
                    this.tabDemo = new JTable(info, title);
                    // 显示表头
                    this.jth = this.tabDemo.getTableHeader();
                    // 将JTable加入到带滚动条的面板中
                    this.scpDemo.getViewport().add(tabDemo);
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "搜索失败", "错误", JOptionPane.ERROR_MESSAGE);
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

            String[] strs1 = res.getString("data").split(",");//根据，切分字符串

            // 将查询获得的记录数据，转换成适合生成JTable的数据形式
            Object[][] info = new String[100][100];
            String[] title = {"Number", "BookID", " BookName"};

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

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnAdd_ActionPerformed(ActionEvent ae) {
        try {
            jsonObject.put("request","addbook");
            jsonObject.put("id",this.UserID);
            jsonObject.put("bookid",BooKID.getText());
            jsonObject.put("bookname",AddBooKName.getText());
            jsonObject.put("bookcnt",BooKNum.getText());

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

    public void btnChange_ActionPerformed(ActionEvent ae) {
        try {
            jsonObject.put("request","updatebooknamecnt");
            jsonObject.put("id",this.UserID);
            jsonObject.put("bookid",BooKID.getText());
            jsonObject.put("bookname",AddBooKName.getText());
            jsonObject.put("bookcnt",BooKNum.getText());

            JSONObject res = clientSocket.connect(HOST, PORT, jsonObject);

            switch (res.getString("code")) {
                case "0":
                    JOptionPane.showMessageDialog(this, "修改成功", "正确", JOptionPane.INFORMATION_MESSAGE);
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "修改失败", "错误", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnDelete_ActionPerformed(ActionEvent ae) {
        try {
            jsonObject.put("request","delbook");
            jsonObject.put("id",this.UserID);
            jsonObject.put("bookid",DeleteBooKID.getText());

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

    public void btnAddReader_ActionPerformed(ActionEvent ae) {
        try {
            jsonObject.put("request","addbookholder");
            jsonObject.put("id",this.UserID);
            jsonObject.put("bookid",Addbookid.getText());
            jsonObject.put("addid",Addreaderid.getText());
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
    public void btnDeleteReader_ActionPerformed(ActionEvent ae) {
        try {
            jsonObject.put("request","delbookholder");
            jsonObject.put("id",this.UserID);
            jsonObject.put("bookid",Addbookid.getText());
            jsonObject.put("delid",Addreaderid.getText());

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