package view;

import org.json.JSONException;
import org.json.JSONObject;
import client.ClientSocket;

import javax.swing.*;
import java.awt.*;

public class StoreAddCntDlg extends JFrame {
    public static ClientSocket clientSocket=new ClientSocket();
    private static final String HOST="localhost";
    private static final int PORT=11451;
    private JSONObject _jsonObject=new JSONObject();
    private String _itemId;
    private String _itemName;
    private String _itemPrice;
    private String _itemCnt;

    public StoreAddCntDlg(JSONObject jsonObject,String _itemId,String _itemName,String _itemPrice,String _itemCnt){
        this._jsonObject=jsonObject;
        this._itemId=_itemId;
        this._itemName=_itemName;
        this._itemPrice=_itemPrice;
        this._itemCnt=_itemCnt;
        init();
    }

    void init(){
        JFrame InfoInput = new JFrame("增加商品库存");

        // 界面大小
        InfoInput.setSize(450, 670);
        // 界面布局先设为null
        InfoInput.setLayout(null);

        JLabel labelCnt = new JLabel("需要增加");
        labelCnt.setFont(new Font("华文中宋", Font.PLAIN, 17));
        labelCnt.setBounds(90, 220, 90, 30);
        InfoInput.add(labelCnt);
        JTextField textCnt = new JTextField();
        textCnt.setBounds(180, 220, 180, 30);
        InfoInput.add(textCnt);

        // 设置添加按钮
        JButton btnAdd = new JButton("添加");
        btnAdd.setForeground(new Color(0x0F866E));
        btnAdd.setFont(new Font("华文中宋", Font.PLAIN, 18));
        btnAdd.setBorderPainted(false);
        btnAdd.setBounds(150, 510, 150, 30);
        btnAdd.setHorizontalTextPosition(SwingConstants.CENTER);
        btnAdd.setVerticalTextPosition(SwingConstants.CENTER);
        btnAdd.setOpaque(false);
        btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        InfoInput.add(btnAdd);
        btnAdd.addActionListener(e -> {
            try {
                int tmpCnt=Integer.parseInt(textCnt.getText())+Integer.parseInt(this._itemCnt);
                System.out.println(Double.parseDouble(textCnt.getText())+"+"+Double.parseDouble(this._itemCnt)+"="+tmpCnt);
                _jsonObject.put("request", "updateitem");
                _jsonObject.put("id", this._jsonObject.getString("username"));
                _jsonObject.put("itemid", this._itemId);
                _jsonObject.put("itemname", this._itemName);
                _jsonObject.put("itempri", this._itemPrice);
                _jsonObject.put("itemcnt", Integer.toString(tmpCnt));
                JSONObject res = clientSocket.connect(HOST, PORT, _jsonObject);
                if(res.getString("code").equals("0")) {
                    JOptionPane.showMessageDialog(this, "修改成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    JOptionPane.showMessageDialog(this,"错误信息","错误",JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(this, e1, "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
        });

        // 设置返回按钮
        JButton btnBack = new JButton("返回");
        btnBack.setForeground(new Color(0x0F866E));
        btnBack.setFont(new Font("华文中宋", Font.PLAIN, 18));
        btnBack.setBorderPainted(false);
        btnBack.setBounds(150, 550, 150, 30);
        btnBack.setHorizontalTextPosition(SwingConstants.CENTER);
        btnBack.setVerticalTextPosition(SwingConstants.CENTER);
        btnBack.setOpaque(false);
        btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
        InfoInput.add(btnBack);
        btnBack.addActionListener(e -> {
            try {
                InfoInput.dispose();
                new StoreAddDlg(this._jsonObject);
            }catch (JSONException ex) {
                throw new RuntimeException(ex);
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

