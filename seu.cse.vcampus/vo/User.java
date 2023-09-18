package seu.cse.vcampus.vo;


/**
 * 类 {@code User}用户类，封装着登录用户的相关信息
 *
 * @author Ruijin Zhang
 * @author Hanlong Liu

 * @since 2023/8/21
 */
public class User {
    /**
     * 下面是用户相关的属性
     */
    String _id;
    String _pwd;
    Boolean _sta;
    String  _role;
    String _mail;

    /**
     * User 的 构造方法
     * @param id  传入的用户名
     * @param pwd  传入的登录密码
     * @param sta  传入的登录状态 用于判断是否 重复登录
     * @param role  传入的用户身份
     * @param mail  传入的邮箱地址（用于找回密码）
     */
    public User(String id,String pwd,Boolean sta,String role,String mail){
        setId(id);
        setPwd(pwd);
        setSta(sta);
        setRole(role);
        setMail(mail);
    }

    /**
     * _id 的 set 方法
     * @param s 待赋值的 用户名
     */
    public void setId(String s){
        this._id=s;
    }

    /**
     * _pwd 的 set 方法
     * @param p 待赋值的 登录密码
     */
    public void setPwd(String p){
        this._pwd=p;
    }

    /**
     * _role 的 set 方法
     * @param r 待赋值的 身份信息
     */
    public void setRole(String r){
        this._role=r;
    }

    /**
     * _sta的 set 方法
     * @param s 待赋值的 登录状态
     */
    public void setSta(Boolean s){
        this._sta=s;
    }

    /**
     * _mail的 set 方法
     * @param m 待赋值的 注册邮箱
     */
    public void setMail(String m){
        this._mail=m;
    }

    /**
     * _id 的 get 方法
     * @return 用户的登录名 _id
     */
    public String getId(){
        return this._id;
    }

    /**
     * _pwd 的 get 方法
     * @return 用户的登录密码 _pwd
     */
    public String getPwd(){
        return this._pwd;
    }

    /**
     * _role 的 get 方法
     * @return 用户的身份 _role
     */
    public String getRole(){
        return this._role;
    }

    /**
     * _sta 的 get 方法
     * @return 用户的登录状态 _sta
     */
    public Boolean getSta(){
        return this._sta;
    }

    /**
     * _mail 的 get 方法
     * @return 用户的注册邮箱 _mail
     */
    public String getMail(){
        return this._mail;
    }
}
