package view;

import org.json.JSONException;
import org.json.JSONObject;
import client.ClientSocket;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class StoreModDlg extends JFrame {
    private ClientSocket clientSocket = new ClientSocket();
    private static final String HOST = "localhost";// 服务器地址配置
    private static final int PORT = 11451;// 服务器端口配置
    private JSONObject _jsonObject = new JSONObject();

    private JPanel _contentPane;
    private JTable _table;

    public StoreModDlg(JSONObject jsonObject) {
        this._jsonObject=jsonObject;
        init();
    }

    void init() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                do_this_windowActivated(e);
            }
        });
        setTitle("商品信息表");
        setLocationRelativeTo(null);
        setResizable(false);
        setBounds(470,50,800,500);

        _contentPane=new JPanel();
        _contentPane.setBorder(new EmptyBorder(5,5,5,5));
        _contentPane.setLayout(new BorderLayout(0,0));
        setContentPane(_contentPane);

        JPanel panel=new JPanel();
        _contentPane.add(panel,BorderLayout.SOUTH);

        //添加增加按钮和对应功能
        JButton buttonAdd=new JButton("增加");
        buttonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                do_buttonAdd_actionPerformed(e);
            }
        });
        panel.add(buttonAdd);

        //添加删除按钮和对应功能
        JButton buttonDel=new JButton("删除");
        buttonDel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                do_buttonDel_actionPerformed(e);
            }
        });
        panel.add(buttonDel);

        //添加修改按钮和对应功能
        JButton buttonMod=new JButton("修改");
        buttonMod.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                do_buttonMod_actionPerformed(e);
            }
        });
        panel.add(buttonMod);

        //添加返回按钮和对应功能
        JButton btnBack=new JButton("返回");
        btnBack.addActionListener(e -> {
            JSONObject jsonObject=new JSONObject();
            try{
                jsonObject.put("username",this._jsonObject.getString("username"));
                jsonObject.put("userrole",this._jsonObject.getString("userrole"));
                dispose();
                //new ADStoreFrame(jsonObject);
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
        scrollPane.setViewportView(_table);
        setVisible(true);
    }

    protected void do_this_windowActivated(WindowEvent e){
        DefaultTableModel tableModel=(DefaultTableModel) _table.getModel();
        tableModel.setRowCount(0);
        //设置表头
        tableModel.setColumnIdentifiers(new Object[]{"商品ID","商品名称","商品价格","商品库存"});
        /*从Json字符串中得到所有商品信息然后add*/
        try{
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("request","queryallstoreitem");
            jsonObject.put("id",this._jsonObject.getString("username"));
            JSONObject ans=clientSocket.connect(HOST,PORT,jsonObject);
            String sta=ans.getString("code");
            if(!sta.equals("0")){
                JOptionPane.showMessageDialog(this, "程序错误", "提示", JOptionPane.INFORMATION_MESSAGE);
            }
            String items=ans.getString("data");
            String finalString="|"+items;

            String[] result=finalString.split(",");
            for(int i=0;i< result.length-1;i=i+4){
                tableModel.addRow(new Object[]{result[i].substring(1),result[i+1],result[i+2],result[i+3]});
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
        _table.setRowHeight(30);
        _table.setModel(tableModel);
    }

    protected void do_buttonAdd_actionPerformed(ActionEvent e){
        try {
            new StoreAddItemDlg(this._jsonObject);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    protected void do_buttonDel_actionPerformed(ActionEvent e){
        DefaultTableModel model=(DefaultTableModel) _table.getModel();
        int[] selectedRows=_table.getSelectedRows();
        for(int i=0;i<selectedRows.length;i++){
            try{
                _jsonObject.put("request","delitem");
                _jsonObject.put("id",this._jsonObject.getString("username"));
                _jsonObject.put("itemid",(String)_table.getValueAt(selectedRows[i],0));
                JSONObject res=clientSocket.connect(HOST,PORT,_jsonObject);
                if(res.getString("code").equals("0")) {
                    JOptionPane.showMessageDialog(this, "删除成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    JOptionPane.showMessageDialog(this,"错误信息","错误",JOptionPane.ERROR_MESSAGE);
                }
            }catch (Exception e1){
                JOptionPane.showMessageDialog(this,e1,"错误",JOptionPane.ERROR_MESSAGE);
            }
            model.removeRow(selectedRows[0]);
        }
        _table.setModel(model);
    }


    protected void do_buttonMod_actionPerformed(ActionEvent e){
        DefaultTableModel model=(DefaultTableModel) _table.getModel();
        int[] selectedRows=_table.getSelectedRows();
        _table.setModel(model);
        try{
            String tmpid=_table.getValueAt(selectedRows[0],0).toString();
            new StoreModItemDlg(this._jsonObject, tmpid);
        } catch (JSONException ex) {
            throw new RuntimeException(ex);
        }
    }

}

