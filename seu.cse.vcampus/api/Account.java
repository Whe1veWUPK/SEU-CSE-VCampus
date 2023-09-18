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
	 * 
	 * @param id		登录名
	 * @param pwd		密码
	 * @param role		角色
	 * @param mail		邮箱
	 * @return			注册结果（json字符串）
	 */
	public String register(String id, String pwd, String role, String mail);
	
	/**
	 * 
	 * @param id		登录名
	 * @param pwd		密码
	 * @param role		角色
	 * @return			登录结果（json字符串）
	 */
	public String logIn(String id, String pwd, String role);
	
	/**
	 * 
	 * @param id		登录名
	 * @return			登出结果（json字符串）
	 */
	public String logOut(String id);
	
	/**
	 * 
	 * @param id		登录名
	 * @param pwd		密码
	 * @param newPwd	新密码
	 * @return			修改结果（json字符串）
	 */
	public String changePwd(String id, String pwd, String newPwd);
	
	//public String retPwd(String id, )
}

