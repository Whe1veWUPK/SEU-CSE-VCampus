package imp;

/**
 * 类 {@code AccountSys}提供用户登录系统业务功能的实现
 *
 * @author yfyou
 * @author wwb
 *
 * @since 2023/08/21
 */

import org.json.JSONObject;
import api.Account;
import dao.StuAccDao;
import dao.TcAccDao;
import dao.UserDao;
import server.Server;
import vo.StuAcc;
import vo.TcAcc;
import vo.User;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.util.Random;


public class AccountSys implements Account {
    private static final int VERIFY_LEN = 6;//验证码长度

    /**
     * 用户注册，数据合法发送一封验证邮件
     * @param id		登录名
     * @param pwd		密码
     * @param role		角色
     * @param mail		邮箱
     * @return			注册结果（json字符串）
     */
    public String register(String id, String pwd, String role, String mail){
        //创建json对象
        JSONObject jsonObject = new JSONObject();

        //检查邮箱和用户名
        User checkMail = UserDao.queryUserByMail(mail);
        User checkID = UserDao.queryUser(id);
        if(checkID!=null) {
            jsonObject.put("code", "1");
            jsonObject.put("message", "用户名已存在");
            return jsonObject.toString();
        }
        else if(checkMail!=null) {
            jsonObject.put("code", "2");
            jsonObject.put("message", "邮箱已被注册");
            return jsonObject.toString();
        }

        //发送验证邮件至相应邮箱
        //随机生成验证码
        String verify = generateRandomCode(VERIFY_LEN);

        //发送邮件
        // 邮件配置信息
        String host = "smtp.yeah.net"; // SMTP服务器地址
        String username = "vcampus_retpwd@yeah.net"; // 发件人邮箱用户名
        String appPassword = "ITIDFWOVMVKDFNGD"; // 授权密码
        String toAddress = mail; // 收件人邮箱地址

        // 创建邮件会话
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "25");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, appPassword);
            }
        });

        try {
            // 创建邮件对象
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));
            message.setSubject("VCampus verify");//标题

            // 内容
            String htmlContent = "<html><body>"
                    + "<h3>Welcome to VCampus!</h3>"
                    + "<p>Your verification code is:</p>"
                    + "<h1 style=\"color:rgba(25, 150, 22, 0.8)\">"
                    + verify
                    + "</h1></body></html>";
            message.setContent(htmlContent, "text/html");

            // 发送邮件
            Transport.send(message);
            System.out.println("[Server]Mail sent to "+toAddress+".");

            //暂存用户数据并创建定时功能
            DataPack d = new DataPack(pwd,role,mail,verify);
            Server.registerTempData.put(id, d);
            VerifyTimer timer = new VerifyTimer();
            timer.registerCountdown(id);

            jsonObject.put("code","0");
            jsonObject.put("message", "验证码发送成功");
            return jsonObject.toString();
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("[Server]Mail was not sent.");
            jsonObject.put("code","-3");
            jsonObject.put("message", "网络异常");
            return jsonObject.toString();
        }
    }

    /**
     * 登录
     * @param id		登录名
     * @param pwd		密码
     * @param role		角色
     * @return			登录结果（json字符串）
     */
    public String logIn(String id, String pwd, String role) {
        //查找用户
        User usr= UserDao.queryUser(id);

        //创建json对象
        JSONObject jsonObject = new JSONObject();

        if(usr==null||!usr.getRole().equals(role)) {
            jsonObject.put("code","1");
            jsonObject.put("message", "找不到用户");
        }
        else if(!usr.getPwd().equals(pwd)) {
            jsonObject.put("code","2");
            jsonObject.put("message", "密码错误");
        }
        else if(usr.getSta()) {
            jsonObject.put("code","3");
            jsonObject.put("message", "重复登录，请稍后再试");
        }
        else {
            usr.setSta(true);
            Byte res = UserDao.updateUser(usr);
            if(res==1) {
                jsonObject.put("code","0");
                jsonObject.put("message", "登录成功");
            }
            else {
                jsonObject.put("code","-1");
                jsonObject.put("message", "系统错误");
            }
        }

        return jsonObject.toString();
    }

    /**
     * 登出
     * @param id		登录名
     * @return			登出结果（json字符串）
     */
    public String logOut(String id) {
        //查找用户
        User usr=UserDao.queryUser(id);

        //创建json对象
        JSONObject jsonObject = new JSONObject();

        if(usr!=null&&usr.getSta()) {
            //更新登录状态
            usr.setSta(false);
            UserDao.updateUser(usr);

            jsonObject.put("code","0");
            jsonObject.put("message", "登出成功");
        }
        else {
            jsonObject.put("code","-1");
            jsonObject.put("message", "登出失败");
        }

        return jsonObject.toString();
    }

    /**
     * 修改密码
     * @param id		登录名
     * @param pwd		密码
     * @param newPwd	新密码
     * @return			修改结果（json字符串）
     */
    public String changePwd(String id, String pwd, String newPwd) {
        //查找用户
        User usr=UserDao.queryUser(id);

        //创建json对象
        JSONObject jsonObject = new JSONObject();

        if(usr==null) {
            jsonObject.put("code","1");
            jsonObject.put("message", "找不到用户");
        }
        else if(!usr.getPwd().equals(pwd)) {
            jsonObject.put("code","2");
            jsonObject.put("message", "密码错误");
        }
        else if(usr.getPwd().equals(newPwd)) {
            jsonObject.put("code","3");
            jsonObject.put("message", "新密码不能与旧密码相同");
        }
        else {
            usr.setPwd(newPwd);
            UserDao.updateUser(usr);

            jsonObject.put("code","0");
            jsonObject.put("message", "修改成功");
        }

        return jsonObject.toString();
    }

    /**
     * 发送验证码
     * @param id	登录名
     * @return		结果
     */
    public String sendVerificationCode(String id) {
        //查找用户
        User usr=UserDao.queryUser(id);
        //创建json对象
        JSONObject jsonObject = new JSONObject();
        if(usr==null) {
            jsonObject.put("code","1");
            jsonObject.put("message", "找不到用户");
            return jsonObject.toString();
        }
        else if(usr.getSta()) {
            jsonObject.put("code","-2");
            jsonObject.put("message", "请先退出登录");
            return jsonObject.toString();
        }

        //随机生成验证码
        String verify = generateRandomCode(VERIFY_LEN);

        //发送邮件
        // 邮件配置信息
        String host = "smtp.yeah.net"; // SMTP服务器地址
        String username = "vcampus_retpwd@yeah.net"; // 发件人邮箱用户名
        String appPassword = "ITIDFWOVMVKDFNGD"; // 授权密码
        String toAddress = usr.getMail(); // 收件人邮箱地址

        // 创建邮件会话
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "25");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, appPassword);
            }
        });

        try {
            // 创建邮件对象
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));
            message.setSubject("VCampus verify");//标题

            // 内容
            String htmlContent = "<html><body>"
                    + "<h3>Welcome to VCampus!</h3>"
                    + "<p>Your verification code is:</p>"
                    + "<h1 style=\"color:rgba(25, 150, 22, 0.8)\">"
                    + verify
                    + "</h1></body></html>";
            message.setContent(htmlContent, "text/html");

            // 发送邮件
            Transport.send(message);
            System.out.println("[Server]Mail sent to "+toAddress+".");

            //存储验证码并创建定时功能
            Server.verifyCodes.put(id, verify);
            VerifyTimer timer = new VerifyTimer();
            timer.verificationCountdown(id);

            jsonObject.put("code","0");
            jsonObject.put("message", "验证码发送成功");
            return jsonObject.toString();
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("[Server]Mail was not sent.");
            jsonObject.put("code","-3");
            jsonObject.put("message", "网络异常");
            return jsonObject.toString();
        }
    }

    private static String generateRandomCode(int length) {
        // 随机数字字符集
        String digits = "0123456789";
        Random random = new Random();
        StringBuilder codeBuilder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(digits.length());
            char randomDigit = digits.charAt(index);
            codeBuilder.append(randomDigit);
        }

        return codeBuilder.toString();
    }

    /**
     * 找回密码
     * @param id		登录名
     * @param verify	用户传来的验证码
     * @param newPwd	新密码
     * @return			结果
     */
    public String retPwd(String id, String verify, String newPwd) {
        //查找用户
        User usr=UserDao.queryUser(id);
        //创建json对象
        JSONObject jsonObject = new JSONObject();
        if(usr==null) {
            jsonObject.put("code","1");
            jsonObject.put("message", "找不到用户");
            return jsonObject.toString();
        }
        else if(usr.getSta()) {
            jsonObject.put("code","-2");
            jsonObject.put("message", "请先退出登录");
            return jsonObject.toString();
        }

        //查找验证码
        if(verify.equals(Server.verifyCodes.get(id))) {
            if(usr.getPwd().equals(newPwd)) {
                jsonObject.put("code","3");
                jsonObject.put("message", "新密码不能与旧密码相同");
                return jsonObject.toString();
            }

            usr.setPwd(newPwd);
            Byte res = UserDao.updateUser(usr);

            if(res==1) {
                Server.verifyCodes.remove(id);//使验证码立即失效
                jsonObject.put("code","0");
                jsonObject.put("message", "修改成功");
                return jsonObject.toString();
            }
            else {
                jsonObject.put("code","-1");
                jsonObject.put("message", "系统错误");
                return jsonObject.toString();
            }
        }
        else {
            jsonObject.put("code","2");
            jsonObject.put("message", "验证码错误或已超时");
            return jsonObject.toString();
        }
    }

    /**
     * 注册验证邮箱
     * @param id		用户注册的用户名
     * @param verify	用户输入的验证码
     * @return			结果
     */
    public String verifyMail(String id, String verify) {
        //创建json对象
        JSONObject jsonObject = new JSONObject();

        //检查数据有效性
        DataPack d = Server.registerTempData.get(id);
        if(d==null) {
            jsonObject.put("code","-2");
            jsonObject.put("message", "注册信息丢失，请重新注册");
            return jsonObject.toString();
        }

        //检查验证码
        if(!verify.equals(d.getVerify())) {
            jsonObject.put("code","2");
            jsonObject.put("message", "验证码错误");
            return jsonObject.toString();
        }
        else{
            //添加用户
            User usr = new User(id,d.getPwd(),false,d.getRole(),d.getMail());

            Byte res = UserDao.addUser(usr);
            if(res==1) {
                //为注册的ST和TC账户创建个人信息
                Byte accRes = 0;
                if(usr.getRole().equals("ST")&&
                        StuAccDao.queryStuAcc(usr.getId())==null) {

                    System.out.println("[Server]"+usr.getId()+": Creating account...");

                    accRes = StuAccDao.addStuAcc(new StuAcc(usr.getId(),"","","","",0.0));
                }
                else if(usr.getRole().equals("TC")&&
                        TcAccDao.queryTcAcc(usr.getId())==null) {

                    System.out.println("[Server]"+usr.getId()+": Creating account...");

                    accRes = TcAccDao.addTcAcc(new TcAcc(usr.getId(),"","","",0.0,""));
                }
                //检查添加账户结果
                if(accRes!=0) {
                    jsonObject.put("code","-3");
                    jsonObject.put("message", "创建账户失败");
                    return jsonObject.toString();
                }
                else {
                    Server.registerTempData.remove(id);//删除暂存数据
                    jsonObject.put("code", "0");
                    jsonObject.put("message", "注册成功");
                    return jsonObject.toString();
                }
            }
            else {
                jsonObject.put("code", "-1");
                jsonObject.put("message", "系统错误");
                return jsonObject.toString();
            }
        }
    }
}
