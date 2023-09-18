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

public class StoreAddDlg extends JFrame {
    private ClientSocket clientSocket = new ClientSocket();
    private static final String HOST = "localhost";// 服务器地址配置
    private static final int PORT = 11451;// 服务器端口配置
    private JSONObject _jsonObject = new JSONObject();

    private JPanel _contentPane;
    private JTable _table;

    public StoreAddDlg(JSONObject jsonObject) {
        this._jsonObject=jsonObject;
        init();
    }

    void init (){
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

        //添加增加库存按钮和对应功能
        JButton buttonAddCnt=new JButton("增加库存");
        buttonAddCnt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                do_buttonAddCnt_actionPerformed(e);
            }
        });
        panel.add(buttonAddCnt);

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
                JOptionPane.showMessageDialog(this, "程序错误", "错误", JOptionPane.ERROR_MESSAGE);
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

    protected void do_buttonAddCnt_actionPerformed(ActionEvent e){
        DefaultTableModel model=(DefaultTableModel) _table.getModel();
        int[] selectedRows=_table.getSelectedRows();
        _table.setModel(model);
        try{
            String tmpId=_table.getValueAt(selectedRows[0],0).toString();
            String tmpName=_table.getValueAt(selectedRows[0],1).toString();
            String tmpPrice=_table.getValueAt(selectedRows[0],2).toString();
            String tmpCnt=_table.getValueAt(selectedRows[0],3).toString();
            dispose();
            new StoreAddCntDlg(this._jsonObject,tmpId,tmpName,tmpPrice,tmpCnt);
        } catch (JSONException ex) {
            throw new RuntimeException(ex);
        }
    }

}

