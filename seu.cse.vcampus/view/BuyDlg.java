package view;

import org.json.JSONObject;
import client.ClientSocket;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class    BuyDlg extends JFrame {
    private ClientSocket clientSocket = new ClientSocket();
    private static final String HOST = "localhost";// 服务器地址配置
    private static final int PORT = 11451;// 服务器端口配置
    private JSONObject _jsonObject = new JSONObject();

    private JPanel _contentPane;
    private JTable _table;



    public JTable _jTable;
    public Object[] _buyItems;
    public BuyDlg(JSONObject jsonObject,Object[] buyItems) {
        this._jsonObject=jsonObject;

        this._buyItems=buyItems;
        init();
    }

    void init() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                do_this_windowActivated(e);
            }
        });
        setTitle("购买界面");
        setSize(960,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);



        _contentPane=new JPanel();
        _contentPane.setBorder(new EmptyBorder(5,5,5,5));
        _contentPane.setLayout(new BorderLayout(0,0));
        setContentPane(_contentPane);

        JPanel panel=new JPanel();
        _contentPane.add(panel,BorderLayout.SOUTH);


        JButton btnBack=new JButton("返回");
        btnBack.addActionListener(e -> {
            JSONObject jsonObject=new JSONObject();
            try{
                jsonObject.put("username",this._jsonObject.getString("username"));
                jsonObject.put("userrole",this._jsonObject.getString("userrole"));
                dispose();
                new ShoppingDlg(jsonObject);
            }catch (Exception ex){
                ex.printStackTrace();
            }
        });
        panel.add(btnBack);

        //添加滚动面板组件
        JScrollPane scrollPane=new JScrollPane();
        _contentPane.add(scrollPane,BorderLayout.CENTER);
        _table=new JTable();
        _table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        DefaultTableModel tableModel=new DefaultTableModel(){
            public boolean isCellEditable(int row, int column) {
                //Only the fifth column
                return column == 4;
            }
        };
        _table.setModel(tableModel);
        scrollPane.setViewportView(_table);
        setVisible(true);


        JButton btnSubmit=new JButton("提交");
        btnSubmit.addActionListener(e -> {
            _table.repaint();
            Object[] objects=new Object[tableModel.getRowCount()];
            for(int i=0;i<objects.length;++i){
                objects[i]=tableModel.getValueAt(i,4);
            }
            try {
                JSONObject ans = null;
                for (int i = 0; i < objects.length; ++i) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("request", "buyitem");
                    jsonObject.put("id", this._jsonObject.getString("username"));
                    String itemID = tableModel.getValueAt(i, 0).toString();

                    String finalID = itemID.substring(1);
                    jsonObject.put("itemid", finalID);
                    jsonObject.put("buycnt", objects[i]);
                    ans = clientSocket.connect(HOST, PORT, jsonObject);
                    if (ans.getString("code").equals("0")) {
                        JOptionPane.showMessageDialog(this, "购买成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else if(ans.getString("code").equals("1")){
                        JOptionPane.showMessageDialog(this, "未找到商品", "警告", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else if(ans.getString("code").equals("2")){
                        JOptionPane.showMessageDialog(this, "权限不足", "警告", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else if(ans.getString("code").equals("3")){
                        JOptionPane.showMessageDialog(this, "库存量不足", "警告", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else if(ans.getString("code").equals("4")){
                        JOptionPane.showMessageDialog(this, "余额不足", "警告", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else if(ans.getString("code").equals("5")){
                        JOptionPane.showMessageDialog(this, "用户不存在", "警告", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else{
                        JOptionPane.showMessageDialog(this, "系统错误", "警告", JOptionPane.INFORMATION_MESSAGE);
                    }
                }

            }catch (Exception ex){
                ex.printStackTrace();
            }
        });
        panel.add(btnSubmit);

    }

    protected void do_this_windowActivated(WindowEvent e){
        DefaultTableModel tableModel=(DefaultTableModel) _table.getModel();
        tableModel.setRowCount(0);

        //设置表头
        tableModel.setColumnIdentifiers(new Object[]{"商品ID","商品名称","商品价格","商品库存","购买数量"});


        try{
            for(int i=0;i<this._buyItems.length;i=i+4){
                tableModel.addRow(new Object[]{this._buyItems[i],this._buyItems[i+1],this._buyItems[i+2],this._buyItems[i+3],"1"});
            }


        }catch (Exception ex){
            ex.printStackTrace();
        }
        _table.setRowHeight(30);
        _table.setModel(tableModel);
    }




}

