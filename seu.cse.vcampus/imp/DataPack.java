package imp;

/**
 * 类 {@code DataPack}暂存注册用户的输入信息
 *
 * @author yfyou
 * @author wwb
 *
 * @since 2023/9/12
 */

public class DataPack {
    private String _pwd;
    private String _role;
    private String _mail;
    private String _verify;

    /**
     * 构造方法
     * @param pwd
     * @param role
     * @param mail
     * @param verify
     */
    public DataPack(String pwd, String role, String mail, String verify){
        _pwd = pwd;
        _role = role;
        _mail = mail;
        _verify = verify;
    }

    public String getPwd() {
        return _pwd;
    }
    public String getRole() {
        return _role;
    }
    public String getMail() {
        return _mail;
    }
    public String getVerify() {
        return _verify;
    }
}

