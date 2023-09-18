package view;

import org.json.JSONException;
import org.json.JSONObject;
import client.ClientSocket;

import javax.swing.*;
import java.awt.*;

public class BalanceDlg extends JFrame {
    private ClientSocket _clientSocket = new ClientSocket();
    private static final String HOST = "localhost";// 服务器地址配置
    private static final int PORT = 11451;// 服务器端口配置
    private JSONObject _jsonObject = new JSONObject();
    public BalanceDlg(JSONObject jsonObject) throws JSONException {
        this._jsonObject=jsonObject;
        init();
    }
    public void init() throws JSONException {
        JFrame balanceFrame=new JFrame("余额");
        balanceFrame.setSize(960,600);

        //添加 标语
        JLabel welcomeLable=new JLabel("Welcome! "+this._jsonObject.getString("username"));
        welcomeLable.setBounds(600,50,800,100);
        welcomeLable.setForeground(new Color(0x199570));
        welcomeLable.setFont(new Font("宋体", Font.BOLD, 20));
        balanceFrame.add(welcomeLable);

        //添加 标语
        JLabel balLable=new JLabel("你的余额为 "+this._jsonObject.getDouble("bal"));
        balLable.setBounds(600,200,800,100);
        balLable.setForeground(new Color(0x199570));
        balLable.setFont(new Font("宋体", Font.BOLD, 20));
        balanceFrame.add(balLable);

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
        balanceFrame.add(btnBack);

        ImageIcon iconImage = new ImageIcon("image/StoreBackGround.ico");
        JLabel textImage=new JLabel(iconImage);
        textImage.setBounds(0,0,960,600);
        balanceFrame.add(textImage);

        btnBack.addActionListener(e -> {
            balanceFrame.dispose();
            JSONObject jsonObject=new JSONObject();
            try {
                jsonObject.put("username",this._jsonObject.getString("username"));
                jsonObject.put("userrole",this._jsonObject.getString("userrole"));
                new WalletDlg(jsonObject);
            } catch (JSONException ex) {
                throw new RuntimeException(ex);
            }

        });

        // 设置相对位置：屏幕中间
        balanceFrame.setLocationRelativeTo(null);
        // 禁止对窗口大小进行缩放
        balanceFrame.setResizable(false);
        // 设置窗口可见
        balanceFrame.setVisible(true);

    }
}

