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
import api.*;
import dao.UserDao;
import vo.User;
import vo.StuAcc;
import vo.TcAcc;
import dao.StuAccDao;
import dao.TcAccDao;

public class AccountSys implements Account{
	/**
	 * 注册
	 * @param id		登录名
	 * @param pwd		密码
	 * @param role		角色
	 * @param mail		邮箱
	 * @return			注册结果（json字符串）
	 */
	public String register(String id, String pwd, String role, String mail){
		//创建json对象
    	JSONObject jsonObject = new JSONObject();
    	
    	//添加用户
    	User usr = new User(id,pwd,false,role,mail);
    	Byte res = UserDao.addUser(usr);
    	if(res==-1) {
    		jsonObject.put("code", "1");
    		jsonObject.put("message", "用户名已存在");
    	}
    	else if(res==-2) {
    		jsonObject.put("code", "2");
    		jsonObject.put("message", "邮箱已被注册");
    	}
    	else if(res==1) {
    		//为首次登录的ST和TC账户创建个人信息
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
    			jsonObject.put("code","4");
        		jsonObject.put("message", "账号信息异常，请重新登录");
    		}
    		else {
    			jsonObject.put("code", "0");
    			jsonObject.put("message", "注册成功");
    		}
    	}
    	else {
    		jsonObject.put("code", "-1");
    		jsonObject.put("message", "系统错误");
    	}
    	
    	return jsonObject.toString();
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
    	User usr=UserDao.queryUser(id);
    	
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
}
