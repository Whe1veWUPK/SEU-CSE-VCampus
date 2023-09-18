package seu.cse.vcampus.dao;
/**
 * 类 {@code UserDao}提供对用户信息的增、删、改、查功能.
 *
 * @author Wenqia Wu
 * @author Ruijin Zhang
 * @author Hanlong Liu
 *
 * @since 2023/8/25
 */

import seu.cse.vcampus.db.DbHelper;
import seu.cse.vcampus.vo.User;

import java.sql.ResultSet;

public class UserDao {
    public UserDao() {}

    /**
     * 方法addUser增加用户信息.
     *
     * @param user
     * @return 当用户id存在时返回值为-1，邮箱已经被注册时返回值为-2，添加成功为1，其他原因失败为0
     */
    public static Byte addUser(User user) {
        String pwd = user.getPwd();
        String uid = user.getId();
        Boolean sta = user.getSta();
        String role = user.getRole();
        String mail=user.getMail();
        String sqlString = "insert into tblUser(uId,  uPwd, uRole, uSta,uMail) values('"+uid+"','"+pwd+"','"+role+"','"+sta+"','"+mail+"')";
        if(queryUser(uid)!=null){
            //用户名称已存在
            return -1;
        }
        if(queryUserByMail(mail)!=null){
            //邮箱已经注册
            return -2;
        }
        try {
            DbHelper.executeNonQuery(sqlString);
            //插入成功
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        //插入失败
        return 0;
    }

    /**
     * 方法delUser删除用户信息.
     *
     * @param user
     * @return 数据不存在时返回-1，数据存在删除成功返回1，不成功返回0
     */
    public static Byte delUser(User user) {
        String uid = user.getId();
        String sqlString = "delete * from tblUser where uId = '" + uid + "'";

        try {
            if(queryUser(uid) != null) { // 数据存在
                DbHelper.executeNonQuery(sqlString); // 删除
                return 1;
            } else {
                //System.out.println("数据不存在，无法删除");
                return -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * 方法updateUser更改用户信息.
     *
     * @param user
     * @return 用户不存在时返回-1,用户存在修改成功返回1，修改失败返回0
     */
    public static Byte updateUser(User user) {
        String pwd = user.getPwd();
        String uid = user.getId();
        Boolean sta = user.getSta();
        String role = user.getRole();
        String mail=user.getMail();
        String sqlString = "UPDATE tblUser SET uPwd='"+pwd+"',uRole='"+role+"',uSta='"+sta+"',uMail='"+mail+"' WHERE uId='"+uid+"'";
        if(queryUser(uid)==null){
            //用户不存在，无法修改
            return -1;
        }

        try {
            DbHelper.executeNonQuery(sqlString);
            //修改成功
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        //修改失败
        return 0;
    }

    /**
     * 方法queryUser通过uid查询用户.
     *
     * @param uid
     * @return 查询成功返回user否则null
     */
    public static User queryUser(String uid) {
        String sqlString = "select * from tblUser";

        try {
            ResultSet rs = DbHelper.executeQuery(sqlString);
            while(rs.next()) {
                String tmpUid = rs.getString("uId");
                if(uid.equals(tmpUid)) {
                    String pwd = rs.getString("uPwd");
                    Boolean sta = rs.getBoolean("uSta");
                    String role = rs.getString("uRole");
                    String mail = rs.getString("uMail");
                    User user= new User(tmpUid, pwd,  sta, role,mail);
                    return user;
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 方法queryUserByMail通过mail查询用户.
     *
     * @param mail
     * @return 查询成功返回user否则null
     */
    public static User queryUserByMail(String mail){
        String sqlString="select * from tblUser";
        try{
            ResultSet rs=DbHelper.executeQuery(sqlString);
            while(rs.next()){
                String tmpMail=rs.getString("uMail");
                if(mail.equals(tmpMail)){
                    String pwd = rs.getString("uPwd");
                    Boolean sta = rs.getBoolean("uSta");
                    String role = rs.getString("uRole");
                    String uid = rs.getString("uId");
                    User user= new User(uid, pwd,  sta, role,tmpMail);
                    return user;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

  

}