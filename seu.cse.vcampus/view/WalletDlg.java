package view;

import org.json.JSONException;
import org.json.JSONObject;
import client.ClientSocket;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class WalletDlg extends JFrame {
    private ClientSocket _clientSocket = new ClientSocket();
    private static final String HOST = "localhost";// 服务器地址配置
    private static final int PORT = 11451;// 服务器端口配置
    private JSONObject _jsonObject = new JSONObject();

    public WalletDlg(JSONObject jsonObject) throws JSONException {
        this._jsonObject=jsonObject;
        init();
    }
    public void init() throws JSONException {
        JFrame walletFrame=new JFrame("我的钱包");
        walletFrame.setSize(960,600);

        //添加 标语
        JLabel welcomeLable=new JLabel("Welcome! "+this._jsonObject.getString("username"));
        welcomeLable.setBounds(600,50,800,100);
        welcomeLable.setForeground(new Color(0x199570));
        welcomeLable.setFont(new Font("宋体", Font.BOLD, 20));
        walletFrame.add(welcomeLable);


        //添加按钮 [查询余额]
        ImageIcon iconButton=new ImageIcon("image/ButtonBackground.ico");
        JButton btnQuery = new JButton("查询余额", iconButton);
        btnQuery.setForeground(new Color(0x0F866E));
        btnQuery.setFont(new Font("华文中宋", Font.PLAIN, 25));
        btnQuery.setBorderPainted(false);
        btnQuery.setBounds(570, 180, 250, 50);
        btnQuery.setHorizontalTextPosition(SwingConstants.CENTER);
        btnQuery.setVerticalTextPosition(SwingConstants.CENTER);
        btnQuery.setOpaque(false);
        btnQuery.setCursor(new Cursor(Cursor.HAND_CURSOR));
        walletFrame.add(btnQuery);

        //添加按钮[充值]
        ImageIcon iconButton2=new ImageIcon("image/ButtonBackground.ico");
        JButton btnRecharge = new JButton("充值", iconButton);
        btnRecharge.setForeground(new Color(0x0F866E));
        btnRecharge.setFont(new Font("华文中宋", Font.PLAIN, 25));
        btnRecharge.setBorderPainted(false);
        btnRecharge.setBounds(570, 250, 250, 50);
        btnRecharge.setHorizontalTextPosition(SwingConstants.CENTER);
        btnRecharge.setVerticalTextPosition(SwingConstants.CENTER);
        btnRecharge.setOpaque(false);
        btnRecharge.setCursor(new Cursor(Cursor.HAND_CURSOR));
        walletFrame.add(btnRecharge);

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
        walletFrame.add(btnBack);

        ImageIcon iconImage = new ImageIcon("image/StoreBackGround.ico");
        JLabel textImage=new JLabel(iconImage);
        textImage.setBounds(0,0,960,600);
        walletFrame.add(textImage);

        btnQuery.addActionListener((e->{
            JSONObject jsonObject=new JSONObject();

            try {
                jsonObject.put("request","querybal");
                jsonObject.put("id",this._jsonObject.getString("username"));
                JSONObject res=_clientSocket.connect(HOST,PORT,jsonObject);
                String sta=res.getString("code");
                this._jsonObject.put("bal",res.getDouble("bal"));
                //String getSta=res.getString("code");
                if(sta.equals("0")){
                    new BalanceDlg(this._jsonObject);
                }

            } catch (JSONException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }));
        btnRecharge.addActionListener(e->{
            walletFrame.dispose();
            JSONObject jsonObject=new JSONObject();

            try {
                jsonObject.put("username",this._jsonObject.getString("username"));
                jsonObject.put("userrole",this._jsonObject.getString("userrole"));
                new RechargeDlg(jsonObject);
            } catch (JSONException ex) {
                throw new RuntimeException(ex);
            }


        });
        btnBack.addActionListener((e->{
            walletFrame.dispose();
            //new CustomerStoreFrame(this._jsonObject);
        }));
        // 设置相对位置：屏幕中间
        walletFrame.setLocationRelativeTo(null);
        // 禁止对窗口大小进行缩放
        walletFrame.setResizable(false);
        // 设置窗口可见
        walletFrame.setVisible(true);
    }
}

