package api;

/**
 * 接口 {@code Account}账户登录接口，提供用户登录系统的方法名
 *
 * @author yfyou
 * @author wwb
 *
 * @since 2023/08/21
 */

public interface Account {
    /**
     * 用户注册
     * @param id		登录名
     * @param pwd		密码
     * @param role		角色
     * @param mail		邮箱
     * @return			注册结果（json字符串）
     */
    public String register(String id, String pwd, String role, String mail);

    /**
     * 用户登录
     * @param id		登录名
     * @param pwd		密码
     * @param role		角色
     * @return			登录结果（json字符串）
     */
    public String logIn(String id, String pwd, String role);

    /**
     * 用户登出
     * @param id		登录名
     * @return			登出结果（json字符串）
     */
    public String logOut(String id);

    /**
     * 修改密码
     * @param id		登录名
     * @param pwd		密码
     * @param newPwd	新密码
     * @return			修改结果（json字符串）
     */
    public String changePwd(String id, String pwd, String newPwd);

    /**
     * 发送验证码
     * @param id		登录名
     * @return			结果
     */
    public String sendVerificationCode(String id);

    /**
     * 找回密码
     * @param id		登录名
     * @param verify	用户传来的验证码
     * @param newPwd	新密码
     * @return			结果
     */
    public String retPwd(String id, String verify,String newPwd);

    /**
     * 注册验证邮箱
     * @param id		用户注册的用户名
     * @param verify	用户输入的验证码
     * @return			结果
     */
    public String verifyMail(String id, String verify);
}


