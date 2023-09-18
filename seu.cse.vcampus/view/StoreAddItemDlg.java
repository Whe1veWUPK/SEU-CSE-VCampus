package view;

import org.json.JSONObject;
import client.ClientSocket;

import javax.swing.*;
import java.awt.*;

public class StoreAddItemDlg extends JFrame {
    public static ClientSocket clientSocket=new ClientSocket();
    private static final String HOST="localhost";
    private static final int PORT=11451;
    private JSONObject _jsonObject=new JSONObject();



    public StoreAddItemDlg(JSONObject jsonObject){
        this._jsonObject=jsonObject;
        init();
    }

    void init(){
        JFrame InfoInput = new JFrame("增加商品");

        // 界面大小
        InfoInput.setSize(450, 670);
        // 界面布局先设为null
        InfoInput.setLayout(null);

        JLabel labelId = new JLabel("商品Id：");
        labelId.setFont(new Font("华文中宋", Font.PLAIN, 17));
        labelId.setBounds(90, 40, 90, 30);
        InfoInput.add(labelId);
        JTextField textId = new JTextField();
        textId.setBounds(180, 40, 180, 30);
        InfoInput.add(textId);

        JLabel labelName = new JLabel("商品名称：");
        labelName.setFont(new Font("华文中宋", Font.PLAIN, 17));
        labelName.setBounds(90, 100, 90, 30);
        InfoInput.add(labelName);
        JTextField textName = new JTextField();
        textName.setBounds(180, 100, 180, 30);
        InfoInput.add(textName);

        JLabel labelPrice = new JLabel("商品价格：");
        labelPrice.setFont(new Font("华文中宋", Font.PLAIN, 17));
        labelPrice.setBounds(90, 160, 90, 30);
        InfoInput.add(labelPrice);
        JTextField textPrice = new JTextField();
        textPrice.setBounds(180, 160, 180, 30);
        InfoInput.add(textPrice);

        JLabel labelCnt = new JLabel("商品库存：");
        labelCnt.setFont(new Font("华文中宋", Font.PLAIN, 17));
        labelCnt.setBounds(90, 220, 90, 30);
        InfoInput.add(labelCnt);
        JTextField textCnt = new JTextField();
        textCnt.setBounds(180, 220, 180, 30);
        InfoInput.add(textCnt);

        // 设置底下按钮
        JButton btnControl = new JButton("添加");
        btnControl.setForeground(new Color(0x0F866E));
        btnControl.setFont(new Font("华文中宋", Font.PLAIN, 18));
        btnControl.setBorderPainted(false);
        btnControl.setBounds(150, 570, 150, 30);
        btnControl.setHorizontalTextPosition(SwingConstants.CENTER);
        btnControl.setVerticalTextPosition(SwingConstants.CENTER);
        btnControl.setOpaque(false);
        btnControl.setCursor(new Cursor(Cursor.HAND_CURSOR));
        InfoInput.add(btnControl);

        btnControl.addActionListener(e -> {
            try {
                _jsonObject.put("request", "additem");
                _jsonObject.put("id", this._jsonObject.getString("username"));
                _jsonObject.put("itemid", textId.getText());
                _jsonObject.put("itemname", textName.getText());
                _jsonObject.put("itempri", textPrice.getText());
                _jsonObject.put("itemcnt", textCnt.getText());
                JSONObject res = clientSocket.connect(HOST, PORT, _jsonObject);
                if(res.getString("code").equals("0")) {
                    JOptionPane.showMessageDialog(this, "添加成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    JOptionPane.showMessageDialog(this,"错误信息","错误",JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(this, e1, "错误", JOptionPane.ERROR_MESSAGE);
                return;
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

