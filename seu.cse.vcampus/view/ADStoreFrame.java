package view;

import org.json.JSONException;
import org.json.JSONObject;
import client.ClientSocket;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ADStoreFrame extends JFrame{
    private ClientSocket clientSocket = new ClientSocket();
    private static final String HOST = "localhost";// 服务器地址配置
    private static final int PORT = 11451;// 服务器端口配置
    private JSONObject _jsonObject;
    public ADStoreFrame(JSONObject jsonObject) {
        this._jsonObject=jsonObject;
        init();
    }

    void init(){
        JFrame userStoreFrame=new JFrame("商店首页");
        userStoreFrame.setSize(960,600);
        // 界面布局先设为null
        userStoreFrame.setLayout(null);

        // 添加按钮 [修改商品信息]
        ImageIcon iconMod = new ImageIcon("image/StoreMod.ico");
        JButton btnMod = new JButton("", iconMod);
        btnMod.setForeground(new Color(0x0F866E));
        btnMod.setBackground(new Color(0xFFFFFF));
        btnMod.setFont(new Font("华文中宋", Font.PLAIN, 25));
        btnMod.setBorderPainted(true);
        btnMod.setBounds(500, 10, 300, 270);
        btnMod.setHorizontalTextPosition(SwingConstants.CENTER);
        btnMod.setVerticalTextPosition(SwingConstants.CENTER);
        btnMod.setCursor(new Cursor(Cursor.HAND_CURSOR));
        userStoreFrame.add(btnMod);
        btnMod.addActionListener((e->{
            //userStoreFrame.dispose();
            try {
                new StoreModDlg(this._jsonObject);
            } catch (JSONException ex) {
                throw new RuntimeException(ex);
            }
        }));


        // 添加按钮 [增加商品库存]
        ImageIcon iconAdd = new ImageIcon("image/StoreAdd.ico");
        JButton btnAdd = new JButton("", iconAdd);
        btnAdd.setForeground(new Color(0x0F866E));
        btnAdd.setBackground(new Color(0xFFFFFF));
        btnAdd.setFont(new Font("华文中宋", Font.PLAIN, 25));
        btnAdd.setBorderPainted(true);
        btnAdd.setBounds(500, 290, 300, 270);
        btnAdd.setHorizontalTextPosition(SwingConstants.CENTER);
        btnAdd.setVerticalTextPosition(SwingConstants.CENTER);
        btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        userStoreFrame.add(btnAdd);
        btnAdd.addActionListener((e->{
            //userStoreFrame.dispose();
            try {
                new StoreAddDlg(this._jsonObject);
            } catch (JSONException ex) {
                throw new RuntimeException(ex);
            }
        }));


        // 添加按钮 [返回]
        ImageIcon iconBack = new ImageIcon("image/Back.ico");
        JButton btnBack = new JButton("",iconBack);
        btnBack.setForeground(new Color(0x0F866E));
        btnBack.setBorderPainted(false);
        btnBack.setBounds(900, 510, 50, 50);
        btnBack.setHorizontalTextPosition(SwingConstants.CENTER);
        btnBack.setVerticalTextPosition(SwingConstants.CENTER);
        btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBack.setOpaque(false);
        userStoreFrame.add(btnBack);
        btnBack.addActionListener((e->{
            try{
                userStoreFrame.dispose();
                //new VCampusHomeFrame(this._jsonObject);
            } catch (JSONException ex) {
                throw new RuntimeException(ex);
            }
        }));

        //背景图设置
        ImageIcon iconImage = new ImageIcon("image/StoreBackGround.ico");
        JLabel textImage=new JLabel(iconImage);
        textImage.setBounds(0,0,960,600);
        userStoreFrame.add(textImage);

        // 设置相对位置：屏幕中间
        userStoreFrame.setLocationRelativeTo(null);
        // 禁止对窗口大小进行缩放
        userStoreFrame.setResizable(false);
        // 设置窗口可见
        userStoreFrame.setVisible(true);
    }
//    public static void main(String[] args) throws JSONException {
//        JSONObject jsonObject=new JSONObject();
//        jsonObject.put("username","ADtest1");
//        jsonObject.put("userrole","AD");
//        new ADStoreFrame(jsonObject);
//    }
}

