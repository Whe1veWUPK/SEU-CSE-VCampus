package view;

import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class CustomerStoreFrame extends JFrame{

    private JSONObject _jsonObject;
    public CustomerStoreFrame(JSONObject jsonObject) {
        this._jsonObject=jsonObject;
        init();

    }

    void init(){
        JFrame userStoreFrame=new JFrame("商店首页");
        userStoreFrame.setSize(960,600);
        // 界面布局先设为null
        userStoreFrame.setLayout(null);

        // 添加按钮 [购物]
        ImageIcon iconShopping = new ImageIcon("image/Shopping.ico");
        JButton btnShopping = new JButton("", iconShopping);
        btnShopping.setForeground(new Color(0x0F866E));
        btnShopping.setBackground(new Color(0xFFFFFF));
        btnShopping.setFont(new Font("华文中宋", Font.PLAIN, 25));
        btnShopping.setBorderPainted(true);
        btnShopping.setBounds(600, 50, 150, 150);
        btnShopping.setHorizontalTextPosition(SwingConstants.CENTER);
        btnShopping.setVerticalTextPosition(SwingConstants.CENTER);

        btnShopping.setCursor(new Cursor(Cursor.HAND_CURSOR));
        userStoreFrame.add(btnShopping);

        // 添加按钮 [钱包]
        ImageIcon iconWallet = new ImageIcon("image/Wallet.ico");
        JButton btnWallet = new JButton("", iconWallet);
        btnWallet.setForeground(new Color(0x0F866E));
        btnWallet.setBackground(new Color(0xFFFFFF));
        btnWallet.setFont(new Font("华文中宋", Font.PLAIN, 25));
        btnWallet.setBorderPainted(true);
        btnWallet.setBounds(600, 250, 150, 150);
        btnWallet.setHorizontalTextPosition(SwingConstants.CENTER);
        btnWallet.setVerticalTextPosition(SwingConstants.CENTER);

        btnWallet.setCursor(new Cursor(Cursor.HAND_CURSOR));
        userStoreFrame.add(btnWallet);

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

        ImageIcon iconImage = new ImageIcon("image/StoreBackGround.ico");
        JLabel textImage=new JLabel(iconImage);
        textImage.setBounds(0,0,960,600);
        userStoreFrame.add(textImage);
        btnShopping.addActionListener(e->{
            userStoreFrame.dispose();
            try{
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("username",this._jsonObject.getString("username"));
                jsonObject.put("userrole",this._jsonObject.getString("userrole"));
                new ShoppingDlg(jsonObject);
            }catch (Exception ex){
                ex.printStackTrace();
            }
        });
        btnWallet.addActionListener((e->{
            userStoreFrame.dispose();
            try {
                new WalletDlg(this._jsonObject);
            } catch (JSONException ex) {
                throw new RuntimeException(ex);
            }
        }));

        btnBack.addActionListener((e->{
            try{
                userStoreFrame.dispose();
                //new VCampusHomeFrame(this._jsonObject);
            } catch (JSONException ex) {
                throw new RuntimeException(ex);
            }
        }));


        // 设置相对位置：屏幕中间
        userStoreFrame.setLocationRelativeTo(null);
        // 禁止对窗口大小进行缩放
        userStoreFrame.setResizable(false);
        // 设置窗口可见
        userStoreFrame.setVisible(true);
    }
}

