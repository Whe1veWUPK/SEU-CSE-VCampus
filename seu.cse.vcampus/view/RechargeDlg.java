package view;

import org.json.JSONException;
import org.json.JSONObject;
import client.ClientSocket;

import javax.swing.*;
import java.awt.*;

public class RechargeDlg extends JFrame {
    private ClientSocket _clientSocket = new ClientSocket();
    private static final String HOST = "localhost";// 服务器地址配置
    private static final int PORT = 11451;// 服务器端口配置
    private JSONObject _jsonObject = new JSONObject();
    public RechargeDlg(JSONObject jsonObject) throws JSONException {
        this._jsonObject=jsonObject;
        init();
    }
    public void init() throws JSONException {
        JFrame rechargeFrame=new JFrame("充值界面");
        rechargeFrame.setSize(960,600);


        //添加 标语
        JLabel welcomeLable=new JLabel("Welcome! "+this._jsonObject.getString("username"));
        welcomeLable.setBounds(600,50,800,100);
        welcomeLable.setForeground(new Color(0x199570));
        welcomeLable.setFont(new Font("宋体", Font.BOLD, 20));
        rechargeFrame.add(welcomeLable);


        //添加 标语
        JLabel helpLable=new JLabel("请在下方输入你要充值的金额");
        helpLable.setBounds(600,80,800,100);
        helpLable.setForeground(new Color(0x199570));
        helpLable.setFont(new Font("宋体", Font.BOLD, 20));
        rechargeFrame.add(helpLable);

        // 创建充值栏
        ImageIcon iconRecharge = new ImageIcon("image/Search.ico");
        JTextField rechargeText = new JTextField();
        rechargeText.setForeground(new Color(0x199570)); // 0x0F866E
        rechargeText.setFont(new Font("宋体", Font.BOLD, 20));
        rechargeText.setBounds(600, 170, 200, 40);
        rechargeText.addFocusListener(new JTextFieldHintListener(rechargeText, "请输入充值金额"));
        rechargeFrame.add(rechargeText);

        //添加按钮[充值]
        ImageIcon iconButton2=new ImageIcon("image/ButtonBackground.ico");
        JButton btnRecharge = new JButton("充值", iconButton2);
        btnRecharge.setForeground(new Color(0x0F866E));
        btnRecharge.setFont(new Font("华文中宋", Font.PLAIN, 25));
        btnRecharge.setBorderPainted(false);
        btnRecharge.setBounds(600, 220, 250, 50);
        btnRecharge.setHorizontalTextPosition(SwingConstants.CENTER);
        btnRecharge.setVerticalTextPosition(SwingConstants.CENTER);
        btnRecharge.setOpaque(false);
        btnRecharge.setCursor(new Cursor(Cursor.HAND_CURSOR));
        rechargeFrame.add(btnRecharge);

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
        rechargeFrame.add(btnBack);

        ImageIcon iconImage = new ImageIcon("image/StoreBackGround.ico");
        JLabel textImage=new JLabel(iconImage);
        textImage.setBounds(0,0,960,600);
        rechargeFrame.add(textImage);
        btnRecharge.addActionListener(e->{
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("request", "recharge");
                jsonObject.put("id",this._jsonObject.getString("username"));
                jsonObject.put("bal",rechargeText.getText());
                JSONObject ans=_clientSocket.connect(HOST,PORT,jsonObject);
                String sta=ans.getString("code");
                if(sta.equals("0")){
                    JOptionPane.showMessageDialog(this, "充值成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                }
                else{
                    JOptionPane.showMessageDialog(this, "充值失败", "提示", JOptionPane.INFORMATION_MESSAGE);
                }
                rechargeText.setText("");

            }catch (Exception ex){
                ex.printStackTrace();
            }
        });
        btnBack.addActionListener(e->{
            rechargeFrame.dispose();
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
        rechargeFrame.setLocationRelativeTo(null);
        // 禁止对窗口大小进行缩放
        rechargeFrame.setResizable(false);
        // 设置窗口可见
        rechargeFrame.setVisible(true);
    }
}

