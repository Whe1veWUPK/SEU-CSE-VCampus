package imp;

import java.util.ArrayList;

import org.json.JSONObject;

import dao.DormitoryDao;
import dao.StuAccDao;
import dao.UserDao;
import vo.User;
import vo.Dormitory;
import vo.StuAcc;
import api.DormitoryAPI;

public class DormitorySys implements DormitoryAPI {

	/**
	 * 显示所有宿舍 调用权限：AD
	 * 
	 * @param id
	 * @return 显示结果（json字符串）
	 */
	public String queryAllDormitory(String id) {
		// 创建json对象
		JSONObject jsonObject = new JSONObject();

		// 检查登录情况
		User usr = UserDao.queryUser(id);
		if (usr == null) {
			jsonObject.put("code", "3");
			jsonObject.put("message", "用户不存在");
			return jsonObject.toString();
		} else if (!usr.getSta()) {
			jsonObject.put("code", "-2");
			jsonObject.put("message", "登录异常");
			return jsonObject.toString();
		}

		// 判断权限
		String usrRole = usr.getRole();
		if (usrRole.equals("AD")) {
			ArrayList<Dormitory> allDormitoryList = DormitoryDao.queryAllDormitories();
			if (allDormitoryList == null) {
				jsonObject.put("code", "1");
				jsonObject.put("message", "未找到宿舍");
			} else {
				String data = impHelper.dormitoryListToStr(',', '|', allDormitoryList, false);
				jsonObject.put("code", "0");
				jsonObject.put("message", "查询成功");
				jsonObject.put("data", data);
			}
		} else {
			jsonObject.put("code", "2");
			jsonObject.put("message", "权限不足");
		}

		return jsonObject.toString();
	}

	/**
	 * 学生获取自己宿舍信息 调用权限：ST
	 * 
	 * @param id
	 * @return 查询结果（json字符串）
	 */
	public String stuQueryDormitory(String id) {
		// 创建json对象
		JSONObject jsonObject = new JSONObject();

		// 检查登录情况
		User usr = UserDao.queryUser(id);
		if (usr == null) {
			jsonObject.put("code", "3");
			jsonObject.put("message", "用户不存在");
			return jsonObject.toString();
		} else if (!usr.getSta()) {
			jsonObject.put("code", "-2");
			jsonObject.put("message", "登录异常");
			return jsonObject.toString();
		}

		// 判断权限
		String usrRole = usr.getRole();
		if (usrRole.equals("ST")) {
			StuAcc stu = StuAccDao.queryStuAcc(id);
			if (stu == null) {
				jsonObject.put("code", "3");
				jsonObject.put("message", "用户不存在");
				return jsonObject.toString();
			} else if (stu.get_stuAccDom().equals("")) {
				jsonObject.put("code", "1");
				jsonObject.put("message", "未找到宿舍");
			} else {
				String domID = stu.get_stuAccDom();
				Dormitory dom = DormitoryDao.queryDormitory(domID);
				if (dom == null) {
					jsonObject.put("code", "1");
					jsonObject.put("message", "未找到宿舍");
				} else {
					String data = dom.get_domID() + "," + dom.get_domCnt() + "," + dom.get_domMem() + ",";
					jsonObject.put("code", "0");
					jsonObject.put("message", "查询成功");
					jsonObject.put("data", data);
				}
			}
		} else {
			jsonObject.put("code", "2");
			jsonObject.put("message", "权限不足");
		}

		return jsonObject.toString();
	}

	/**
	 * 管理员获取某个宿舍信息 调用权限：AD
	 * 
	 * @param id
	 * @param domID
	 * @return 查询结果（json字符串）
	 */
	public String adQueryDormitory(String id, String domID) {
		// 创建json对象
		JSONObject jsonObject = new JSONObject();

		// 检查登录情况
		User usr = UserDao.queryUser(id);
		if (usr == null) {
			jsonObject.put("code", "3");
			jsonObject.put("message", "用户不存在");
			return jsonObject.toString();
		} else if (!usr.getSta()) {
			jsonObject.put("code", "-2");
			jsonObject.put("message", "登录异常");
			return jsonObject.toString();
		}

		// 判断权限
		String usrRole = usr.getRole();
		if (usrRole.equals("AD")) {
			Dormitory dom = DormitoryDao.queryDormitory(domID);
			if (dom == null) {
				jsonObject.put("code", "1");
				jsonObject.put("message", "未找到宿舍");
			} else {
				String data = dom.get_domID() + "," + dom.get_domCnt() + "," + dom.get_domMem() + ",";
				jsonObject.put("code", "0");
				jsonObject.put("message", "查询成功");
				jsonObject.put("data", data);
			}
		} else {
			jsonObject.put("code", "2");
			jsonObject.put("message", "权限不足");
		}

		return jsonObject.toString();
	}

	/**
	 * 宿舍添加学生 调用权限：AD
	 * 
	 * @param id
	 * @param addID
	 * @param domID
	 * @return 添加结果（json字符串）
	 */
	public String addMember(String id, String addID, String domID) {
		// 创建json对象
		JSONObject jsonObject = new JSONObject();

		// 检查登录情况
		User usr = UserDao.queryUser(id);
		if (usr == null) {
			jsonObject.put("code", "3");
			jsonObject.put("message", "用户不存在");
			return jsonObject.toString();
		} else if (!usr.getSta()) {
			jsonObject.put("code", "-2");
			jsonObject.put("message", "登录异常");
			return jsonObject.toString();
		}

		// 判断权限
		String usrRole = usr.getRole();
		if (usrRole.equals("AD")) {
			User user = UserDao.queryUser(addID);
			if (user == null) {
				jsonObject.put("code", "3");
				jsonObject.put("message", "用户不存在");
				return jsonObject.toString();
			} else if (user.getRole().equals("ST")) {
				StuAcc stu = StuAccDao.queryStuAcc(addID);
				if (stu == null) {
					jsonObject.put("code", "3");
					jsonObject.put("message", "用户不存在");
				} else if (!stu.get_stuAccDom().equals("")) {
					jsonObject.put("code", "4");
					jsonObject.put("message", "无法添加");
				} else {
					// StuAcc添加宿舍
					stu.set_stuAccDom(domID);
					Byte res1 = StuAccDao.updateStuAcc(stu);
					if (res1 == 0) {
						// Dormitory添加学生
						Dormitory dom = DormitoryDao.queryDormitory(domID);
						if (dom == null) {
							jsonObject.put("code", "5");
							jsonObject.put("message", "宿舍不存在");
							stu.set_stuAccDom("");
							StuAccDao.updateStuAcc(stu);
						} else if (dom.get_domCnt() >= 4) {
							jsonObject.put("code", "1");
							jsonObject.put("message", "宿舍已满");
							stu.set_stuAccDom("");
							StuAccDao.updateStuAcc(stu);
						} else {
							String mem = dom.get_domMem();
							String[] memArr = mem.split("\\;");
							for (int i = 0; i < memArr.length; i++) {
								if (memArr[i].equals(addID)) {
									jsonObject.put("code", "0");
									jsonObject.put("message", "添加成功");
									return jsonObject.toString();
								}
							}
							mem = mem + addID + ";";
							dom.set_domMem(mem);
							Byte domCnt = dom.get_domCnt();
							domCnt++;
							dom.set_domCnt(domCnt);
							Byte res2 = DormitoryDao.updateDormitory(dom);
							if (res2 == 0) {
								jsonObject.put("code", "0");
								jsonObject.put("message", "添加成功");
							} else {
								jsonObject.put("code", "-1");
								jsonObject.put("message", "系统错误");
								stu.set_stuAccDom("");
								StuAccDao.updateStuAcc(stu);
							}
						}
					} else {
						jsonObject.put("code", "-1");
						jsonObject.put("message", "系统错误");
					}
				}
			} else {
				jsonObject.put("code", "4");
				jsonObject.put("message", "无法添加");
			}
		} else {
			jsonObject.put("code", "2");
			jsonObject.put("message", "权限不足");
		}

		return jsonObject.toString();
	}

	/**
	 * 宿舍删除学生 调用权限：AD
	 * 
	 * @param id
	 * @param delid
	 * @return 删除结果（json字符串）
	 */
	public String delMember(String id, String delid) {
		// 创建json对象
		JSONObject jsonObject = new JSONObject();

		// 检查登录情况
		User usr = UserDao.queryUser(id);
		if (usr == null) {
			jsonObject.put("code", "3");
			jsonObject.put("message", "用户不存在");
			return jsonObject.toString();
		} else if (!usr.getSta()) {
			jsonObject.put("code", "-2");
			jsonObject.put("message", "登录异常");
			return jsonObject.toString();
		}

		// 判断权限
		String usrRole = usr.getRole();
		if (usrRole.equals("AD")) {
			User user = UserDao.queryUser(delid);
			if (user == null) {
				jsonObject.put("code", "3");
				jsonObject.put("message", "用户不存在");
				return jsonObject.toString();
			} else if (user.getRole().equals("ST")) {
				StuAcc stu = StuAccDao.queryStuAcc(delid);
				if (stu == null) {
					jsonObject.put("code", "3");
					jsonObject.put("message", "用户不存在");
				} else if (stu.get_stuAccDom().equals("")) {
					jsonObject.put("code", "4");
					jsonObject.put("message", "无法删除");
				} else {
					// StuAcc删除宿舍
					String domID = stu.get_stuAccDom();
					stu.set_stuAccDom("");
					Byte res1 = StuAccDao.updateStuAcc(stu);
					if (res1 == 0) {
						// Dormitory删除学生
						Dormitory dom = DormitoryDao.queryDormitory(domID);
						if (dom == null) {
							jsonObject.put("code", "5");
							jsonObject.put("message", "宿舍不存在");
							stu.set_stuAccDom(domID);
							StuAccDao.updateStuAcc(stu);
						} else {
							String mem = dom.get_domMem();
							String[] memArr = mem.split(";");
							Boolean isExistStu = false;
							for (int i = 0; i < memArr.length; i++) {
								if (memArr[i].equals(delid)) {
									isExistStu = true;
								}
							}
							if (isExistStu == false) {
								jsonObject.put("code", "0");
								jsonObject.put("message", "删除成功");
								return jsonObject.toString();
							}
							String memArrDel = "";
							for (int i = 0; i < memArr.length; i++) {
								if (!memArr[i].equals(delid)) {
									memArrDel = memArrDel + memArr[i] + ";";
								}
							}
							dom.set_domMem(memArrDel);
							Byte domCnt = dom.get_domCnt();
							domCnt--;
							dom.set_domCnt(domCnt);
							Byte res2 = DormitoryDao.updateDormitory(dom);
							if (res2 == 0) {
								jsonObject.put("code", "0");
								jsonObject.put("message", "删除成功");
							} else {
								jsonObject.put("code", "-1");
								jsonObject.put("message", "系统错误");
								stu.set_stuAccDom(domID);
								StuAccDao.updateStuAcc(stu);
							}
						}
					} else {
						jsonObject.put("code", "-1");
						jsonObject.put("message", "系统错误");
					}
				}
			} else {
				jsonObject.put("code", "4");
				jsonObject.put("message", "无法删除");
			}
		} else {
			jsonObject.put("code", "2");
			jsonObject.put("message", "权限不足");
		}

		return jsonObject.toString();
	}

}
