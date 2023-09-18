package view;

/**
 * @author wwq
 * 功能类，实现文本框内默认显示指定文本的功能
 */

import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class JTextFieldHintListener implements FocusListener {
    private String hintText;
    private JTextField textField;

    public JTextFieldHintListener(JTextField jTextField,String hintText) {
        this.textField = jTextField;
        this.hintText = hintText;
        jTextField.setText(hintText);  //默认直接显示
        jTextField.setForeground(Color.GRAY);

    }

    @Override
    public void focusGained(FocusEvent e) {
        //获取焦点时，清空提示内容
        String temp = textField.getText();
        if(temp.equals(hintText)) {
            textField.setText("");
            textField.setForeground(Color.BLACK);
            if(textField instanceof JPasswordField){
                ((JPasswordField) textField).setEchoChar('*');
            }
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        //失去焦点时，没有输入内容，显示提示内容
        String temp = textField.getText();
        if(StringUtils.isBlank(temp)) {
            if(textField instanceof JPasswordField){
                ((JPasswordField) textField).setEchoChar((char)0);
            }
            textField.setForeground(Color.GRAY);
            textField.setText(hintText);
        }
    }
}
