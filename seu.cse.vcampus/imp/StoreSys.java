package imp;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import api.Store;
import vo.StoreItem;
import vo.StuAcc;
import dao.StoreItemDao;
import dao.StuAccDao;
import vo.User;
import dao.UserDao;
import vo.TcAcc;
import dao.TcAccDao;

public class StoreSys implements Store {

	/**
	 * 显示所有商品 调用权限：ST/TC/AD
	 * 
	 * @param id
	 * @return 查询结果（json字符串）
	 */
	public String queryAllStoreItem(String id) {
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
		if (usrRole.equals("ST") || usrRole.equals("TC") || usrRole.equals("AD")) {
			ArrayList<StoreItem> allStoreItemList = StoreItemDao.queryAllStoreItems();
			if (allStoreItemList == null) {
				jsonObject.put("code", "1");
				jsonObject.put("message", "未找到商品");
			} else {
				String data = impHelper.itemListToStr(',', '|', allStoreItemList);
				jsonObject.put("code", "0");
				jsonObject.put("message", "查找成功");
				jsonObject.put("data", data);
			}
		} else {
			jsonObject.put("code", "2");
			jsonObject.put("message", "权限不足");
		}

		return jsonObject.toString();
	}

	/**
	 * 获取商品信息 调用权限：ST/TC/AD
	 * 
	 * @param id       用户名
	 * @param itemName 商品名
	 * @return 查找结果（json字符串）
	 */
	public String queryItem(String id, String itemName) {
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
		if (usrRole.equals("ST") || usrRole.equals("TC") || usrRole.equals("AD")) {
			// 查询商品
			List<StoreItem> itemList = StoreItemDao.queryStoreItemByName(itemName);
			if (itemList == null) {
				jsonObject.put("code", "1");
				jsonObject.put("message", "未找到商品");
			} else {
				String data = impHelper.itemListToStr(';', '|', itemList);
				jsonObject.put("code", "0");
				jsonObject.put("message", "查找成功");
				jsonObject.put("data", data);
			}
		} else {
			jsonObject.put("code", "2");
			jsonObject.put("message", "权限不足");
		}

		return jsonObject.toString();
	}

	/**
	 * 购买商品 调用权限：ST/TC
	 * 
	 * @param id
	 * @param itemID
	 * @param buyCnt
	 * @return 购买结果（json字符串）
	 */
	public String buyItem(String id, String itemID, Short buyCnt) {
		// 创建json对象
		JSONObject jsonObject = new JSONObject();

		// 检查登录情况
		User usr = UserDao.queryUser(id);
		if (usr == null) {
			jsonObject.put("code", "5");
			jsonObject.put("message", "用户不存在");
			return jsonObject.toString();
		} else if (!usr.getSta()) {
			jsonObject.put("code", "-2");
			jsonObject.put("message", "登录异常");
			return jsonObject.toString();
		}

		// 验证数据合法性
		if (buyCnt <= 0) {
			jsonObject.put("code", "-3");
			jsonObject.put("message", "非法数据");
			return jsonObject.toString();
		}

		// 判断权限
		String usrRole = usr.getRole();
		if (usrRole.equals("ST")) {
			// 购买商品
			StoreItem item = StoreItemDao.queryStoreItemById(itemID);
			StuAcc stu = StuAccDao.queryStuAcc(id);
			if (stu == null) {
				jsonObject.put("code", "5");
				jsonObject.put("message", "用户不存在");
				return jsonObject.toString();
			}
			if (item == null) {
				jsonObject.put("code", "1");
				jsonObject.put("message", "未找到商品");
			} else if (item.get_itemCnt() < buyCnt) {
				jsonObject.put("code", "3");
				jsonObject.put("message", "库存量不足");
			} else if (stu.get_stuAccBal() < (double) item.get_itemPri() * buyCnt) {
				jsonObject.put("code", "4");
				jsonObject.put("message", "账户余额不足");
			} else {
				// 修改商品数据库
				Short cnt = item.get_itemCnt();
				Short cntBackup = cnt;
				cnt = (short) (cnt - buyCnt);
				item.set_itemCnt(cnt);
				Byte res1 = StoreItemDao.updateStoreItem(item);
				if (res1 == 1) {
					// 修改学生类
					Double stuBal = stu.get_stuAccBal();
					stuBal = stuBal - (double) item.get_itemPri() * buyCnt;
					stu.set_stuAccBal(stuBal);
					Byte res2 = StuAccDao.updateStuAcc(stu);
					if (res2 == 0) {
						jsonObject.put("code", "0");
						jsonObject.put("message", "购买成功");
					} else {
						jsonObject.put("code", "-1");
						jsonObject.put("message", "系统错误");
						item.set_itemCnt(cntBackup);
						StoreItemDao.updateStoreItem(item);
					}
				} else {
					jsonObject.put("code", "-1");
					jsonObject.put("message", "系统错误");
				}
			}
		} else if (usrRole.equals("TC")) {
			// 购买商品
			StoreItem item = StoreItemDao.queryStoreItemById(itemID);
			TcAcc tc = TcAccDao.queryTcAcc(id);
			if (tc == null) {
				jsonObject.put("code", "5");
				jsonObject.put("message", "用户不存在");
				return jsonObject.toString();
			}
			if (item == null) {
				jsonObject.put("code", "1");
				jsonObject.put("message", "未找到商品");
			} else if (item.get_itemCnt() < buyCnt) {
				jsonObject.put("code", "3");
				jsonObject.put("message", "库存量不足");
			} else if (tc.get_tcAccBal() < (double) item.get_itemPri() * buyCnt) {
				jsonObject.put("code", "4");
				jsonObject.put("message", "账户余额不足");
			} else {
				// 修改商品数据库
				Short cnt = item.get_itemCnt();
				Short cntBackup = cnt;
				cnt = (short) (cnt - buyCnt);
				item.set_itemCnt(cnt);
				Byte res1 = StoreItemDao.updateStoreItem(item);
				if (res1 == 1) {
					// 修改老师类
					Double tcBal = tc.get_tcAccBal();
					tcBal = tcBal - (double) item.get_itemPri() * buyCnt;
					tc.set_tcAccBal(tcBal);
					Byte res2 = TcAccDao.updateTcAcc(tc);
					if (res2 == 0) {
						jsonObject.put("code", "0");
						jsonObject.put("message", "购买成功");
					} else {
						jsonObject.put("code", "-1");
						jsonObject.put("message", "系统错误");
						item.set_itemCnt(cntBackup);
						StoreItemDao.updateStoreItem(item);
					}
				} else {
					jsonObject.put("code", "-1");
					jsonObject.put("message", "系统错误");
				}
			}
		} else {
			jsonObject.put("code", "2");
			jsonObject.put("message", "权限不足");
		}

		return jsonObject.toString();

	}

	/**
	 * 修改商品信息 调用权限：AD
	 * 
	 * @param id       用户名
	 * @param itemID   商品ID
	 * @param itemName 商品名
	 * @param itemPri  商品价格
	 * @param itemCnt  商品数量
	 * @return 修改结果（json字符串）
	 */
	public String updateItem(String id, String itemID, String itemName, Float itemPri, Short itemCnt) {
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

		// 验证数据合法性
		if (itemPri <= 0 || itemCnt < 0) {
			jsonObject.put("code", "-3");
			jsonObject.put("message", "非法数据");
			return jsonObject.toString();
		}

		// 判断权限
		String usrRole = usr.getRole();
		if (usrRole.equals("AD")) {
			// 修改商品
			StoreItem item = new StoreItem(itemID, itemName, itemPri, itemCnt);
			Byte res = StoreItemDao.updateStoreItem(item);
			if (res == 1) {
				jsonObject.put("code", "0");
				jsonObject.put("message", "修改成功");
			} else if (res == -1) {
				jsonObject.put("code", "1");
				jsonObject.put("message", "商品不存在");
			} else {
				jsonObject.put("code", "-1");
				jsonObject.put("message", "系统错误");
			}
		} else {
			jsonObject.put("code", "2");
			jsonObject.put("message", "权限不足");
		}

		return jsonObject.toString();

	}

	/**
	 * 添加商品 调用权限：AD
	 * 
	 * @param id       用户名
	 * @param itemID   商品ID
	 * @param itemName 商品名
	 * @param itemPri  商品价格
	 * @param itemCnt  商品数量
	 * @return 添加结果（json字符串）
	 */
	public String addItem(String id, String itemID, String itemName, Float itemPri, Short itemCnt) {
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

		// 验证数据合法性
		if (itemPri <= 0 || itemCnt < 0) {
			jsonObject.put("code", "-3");
			jsonObject.put("message", "非法数据");
			return jsonObject.toString();
		}

		// 判断权限
		String usrRole = usr.getRole();
		if (usrRole.equals("AD")) {
			// 添加商品
			StoreItem item = new StoreItem(itemID, itemName, itemPri, itemCnt);
			Byte res = StoreItemDao.addStoreItem(item);
			if (res == 1) {
				jsonObject.put("code", "0");
				jsonObject.put("message", "添加成功");
			} else if (res == -1) {
				jsonObject.put("code", "1");
				jsonObject.put("message", "商品已存在");
			} else {
				jsonObject.put("code", "-1");
				jsonObject.put("message", "系统错误");
			}
		} else {
			jsonObject.put("code", "2");
			jsonObject.put("message", "权限不足");
		}

		return jsonObject.toString();
	}

	/**
	 * 删除商品 调用权限：AD
	 * 
	 * @param id     用户名
	 * @param itemID 商品ID
	 * @return 删除结果（json字符串）
	 */
	public String delItem(String id, String itemID) {
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
			// 删除商品
			Byte res = StoreItemDao.delStoreItem(itemID);
			if (res == 1) {
				jsonObject.put("code", "0");
				jsonObject.put("message", "删除成功");
			} else if (res == -1) {
				jsonObject.put("code", "1");
				jsonObject.put("message", "商品不存在");
			} else {
				jsonObject.put("code", "-1");
				jsonObject.put("message", "系统错误");
			}
		} else {
			jsonObject.put("code", "2");
			jsonObject.put("message", "权限不足");
		}

		return jsonObject.toString();
	}

	/**
	 * 余额充值 调用权限：ST/TC
	 * 
	 * @param id  用户名
	 * @param bal 充值金额
	 * @return 充值结果（json字符串）
	 */
	public String recharge(String id, Double bal) {
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

		// 验证数据合法性
		if (bal <= 0) {
			jsonObject.put("code", "-3");
			jsonObject.put("message", "非法数据");
			return jsonObject.toString();
		}

		// 判断权限
		String usrRole = usr.getRole();
		if (usrRole.equals("ST")) {
			// 修改余额
			StuAcc stu = StuAccDao.queryStuAcc(id);
			if (stu == null) {
				jsonObject.put("code", "3");
				jsonObject.put("message", "用户不存在");
				return jsonObject.toString();
			}
			Double stuBal = stu.get_stuAccBal();
			stuBal = stuBal + bal;
			stu.set_stuAccBal(stuBal);
			Byte res = StuAccDao.updateStuAcc(stu);
			if (res == 0) {
				jsonObject.put("code", "0");
				jsonObject.put("message", "充值成功");
			} else if (res == -1) {
				jsonObject.put("code", "3");
				jsonObject.put("message", "用户不存在");
			} else {
				jsonObject.put("code", "-1");
				jsonObject.put("message", "系统错误");
			}
		} else if (usrRole.equals("TC")) {
			// 修改余额
			TcAcc tc = TcAccDao.queryTcAcc(id);
			if (tc == null) {
				jsonObject.put("code", "3");
				jsonObject.put("message", "用户不存在");
				return jsonObject.toString();
			}
			Double tcBal = tc.get_tcAccBal();
			tcBal = tcBal + bal;
			tc.set_tcAccBal(tcBal);
			Byte res = TcAccDao.updateTcAcc(tc);
			if (res == 0) {
				jsonObject.put("code", "0");
				jsonObject.put("message", "充值成功");
			} else if (res == -1) {
				jsonObject.put("code", "3");
				jsonObject.put("message", "用户不存在");
			} else {
				jsonObject.put("code", "-1");
				jsonObject.put("message", "系统错误");
			}
		} else {
			jsonObject.put("code", "2");
			jsonObject.put("message", "权限不足");
		}

		return jsonObject.toString();
	}

	/**
	 * 余额查询 调用权限：ST/TC
	 * 
	 * @param id 用户名
	 * @return 查询结果（json字符串）
	 */
	public String queryBal(String id) {
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
			// 查询余额
			StuAcc stu = StuAccDao.queryStuAcc(id);
			if (stu == null) {
				jsonObject.put("code", "3");
				jsonObject.put("message", "用户不存在");
				return jsonObject.toString();
			}
			Double stuBal = stu.get_stuAccBal();
			jsonObject.put("code", "0");
			jsonObject.put("message", "查询成功");
			jsonObject.put("bal", stuBal);
		} else if (usrRole.equals("TC")) {
			// 查询余额
			TcAcc tc = TcAccDao.queryTcAcc(id);
			if (tc == null) {
				jsonObject.put("code", "3");
				jsonObject.put("message", "用户不存在");
				return jsonObject.toString();
			}
			Double tcBal = tc.get_tcAccBal();
			jsonObject.put("code", "0");
			jsonObject.put("message", "查询成功");
			jsonObject.put("bal", tcBal);
		} else {
			jsonObject.put("code", "2");
			jsonObject.put("message", "权限不足");
		}

		return jsonObject.toString();
	}

}
