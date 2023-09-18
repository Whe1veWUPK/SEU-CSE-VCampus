package imp;

import org.json.JSONObject;
import api.StuStat;
import vo.Student;
import dao.UserDao;
import dao.StudentDao;
import vo.User;

public class StuStatSys implements StuStat{
	/**
	 * 获取学生学籍 调用权限：ST/AD
	 * @param usrID		用户登录名
	 * @param stuNum	查找学生的学号
	 * @return			查找结果、学籍信息（json字符串）
	 */
	public String getStat(String usrID, String stuNum) {
		User usr=UserDao.queryUser(usrID);
		
		//创建json对象
		JSONObject jsonObject = new JSONObject();
		
		//检查登录情况
		if(usr==null) {
			jsonObject.put("code","1");
			jsonObject.put("message", "用户不存在");
			return jsonObject.toString();
		}
		else if(!usr.getSta()) {
			jsonObject.put("code","-2");
			jsonObject.put("message", "登录异常");
			return jsonObject.toString();
		}
		
		String usrRole=usr.getRole();
		//检查权限
		if((usrRole.equals("ST")&&usrID.equals(stuNum))
			||usrRole.equals("AD")) {
			Student stu=StudentDao.queryStudent(stuNum);
    	
			if(stu!=null) {
				jsonObject.put("code","0");
				jsonObject.put("message", "查找完成");
				jsonObject.put("stunum",stu.getStuNum());
				jsonObject.put("stuname",stu.getStuName());
				jsonObject.put("stuage",stu.getStuAge().toString());
				jsonObject.put("stugend",stu.getStuGend().toString());
				jsonObject.put("stuid",stu.getStuID());
				jsonObject.put("stusch",stu.getStuSch());
				jsonObject.put("stumajor",stu.getStuMajor());
				jsonObject.put("stuaddr",stu.getStuAddr());
				jsonObject.put("stuintake",stu.getStuIntake());
			}
			else {
				jsonObject.put("code","3");
				jsonObject.put("message", "找不到学生");
			}
		}
		else {
			jsonObject.put("code","2");
			jsonObject.put("message", "权限不足");
		}	
		
		//返回json字符串
		return jsonObject.toString();
	}
	
	/**
	 * 修改学生学籍 调用权限：AD
	 * @param usrID		用户登录名
	 * @param stu		新的学生信息
	 * @return			修改结果（json字符串）
	 */
	public String setStat(String usrID, Student stu) {
		User usr=UserDao.queryUser(usrID);
		
		//创建json对象
		JSONObject jsonObject = new JSONObject();
		
		//检查登录情况
		if(usr==null) {
			jsonObject.put("code","1");
			jsonObject.put("message", "用户不存在");
			return jsonObject.toString();
		}
		else if(!usr.getSta()) {
			jsonObject.put("code","-2");
			jsonObject.put("message", "登录异常");
			return jsonObject.toString();
		}
		
		String usrRole=usr.getRole();
		
		if(usrRole.equals("AD")) {
			Byte res = StudentDao.updateStudent(stu);
			
			if(res==0) {
				jsonObject.put("code","0");
				jsonObject.put("message", "修改成功");
			}
			else if(res==-1) {
				jsonObject.put("code","3");
				jsonObject.put("message", "学生不存在");
			}
			else {
				jsonObject.put("code","-1");
				jsonObject.put("message", "系统错误");
			}
		}
		else {
			jsonObject.put("code","2");
			jsonObject.put("message", "权限不足");
		}

		//返回json字符串
		return jsonObject.toString();
	}
	
	/**
	 * 删除学生学籍信息 调用权限：AD
	 * @param usrID		用户登录名
	 * @param stuNum	需要删除的学生学号
	 * @return			删除结果（json字符串）
	 */
	public String delStat(String usrID, String stuNum) {
		User usr=UserDao.queryUser(usrID);
		
		//创建json对象
		JSONObject jsonObject = new JSONObject();
		
		//检查登录情况
		if(usr==null) {
			jsonObject.put("code","1");
			jsonObject.put("message", "用户不存在");
			return jsonObject.toString();
		}
		else if(!usr.getSta()) {
			jsonObject.put("code","-2");
			jsonObject.put("message", "登录异常");
			return jsonObject.toString();
		}
		
		String usrRole=usr.getRole();
		
		if(usrRole.equals("AD")) {
			Byte res = StudentDao.delStudent(stuNum);
			
			if(res==0) {
				jsonObject.put("code","0");
				jsonObject.put("message", "删除成功");
			}
			else if(res==-1) {
				jsonObject.put("code","3");
				jsonObject.put("message", "学生不存在");
			}
			else {
				jsonObject.put("code","-1");
				jsonObject.put("message", "系统错误");
			}
		}
		else {
			jsonObject.put("code","2");
			jsonObject.put("message", "权限不足");
		}

		//返回json字符串
		return jsonObject.toString();
	}
	
	/**
	 * 添加学生学籍信息 调用权限：AD
	 * @param usrID		用户登录名
	 * @param stu		新的学生信息
	 * @return			添加结果（json字符串）
	 */
	public String addStat(String usrID, Student stu) {
		User usr=UserDao.queryUser(usrID);
		
		//创建json对象
		JSONObject jsonObject = new JSONObject();
		
		//检查登录情况
		if(usr==null) {
			jsonObject.put("code","1");
			jsonObject.put("message", "用户不存在");
			return jsonObject.toString();
		}
		else if(!usr.getSta()) {
			jsonObject.put("code","-2");
			jsonObject.put("message", "登录异常");
			return jsonObject.toString();
		}
		
		String usrRole=usr.getRole();
		
		if(usrRole.equals("AD")) {
			Byte res = StudentDao.addStudent(stu);
			
			if(res==0) {
				jsonObject.put("code","0");
				jsonObject.put("message", "添加成功");
			}
			else if(res==-1) {
				jsonObject.put("code","3");
				jsonObject.put("message", "学生已存在");
			}
			else {
				jsonObject.put("code","-1");
				jsonObject.put("message", "系统错误");
			}
		}
		else {
			jsonObject.put("code","2");
			jsonObject.put("message", "权限不足");
		}

		//返回json字符串
		return jsonObject.toString();
	}
}
