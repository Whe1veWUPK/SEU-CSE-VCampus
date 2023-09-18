package imp;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import api.Library;
import vo.Book;
import dao.BookDao;
import dao.UserDao;
import vo.StuAcc;
import vo.TcAcc;
import dao.StuAccDao;
import dao.TcAccDao;
import vo.User;

public class LibrarySys implements Library {

	/**
	 * 显示全部书籍信息（不包括借阅者） 调用权限：ST/TC/AD
	 *
	 * @param id
	 * @return 查询结果（json字符串）
	 */
	public String queryAllBook(String id) {
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
		if (usrRole.equals("AD") || usrRole.equals("ST") || usrRole.equals("TC")) {
			ArrayList<Book> allBookList = BookDao.queryAllBooks();
			if (allBookList == null) {
				jsonObject.put("code", "1");
				jsonObject.put("message", "未找到书籍");
			} else {
				String data = impHelper.bookListToStr(',', '|', allBookList, false);
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
	 * 查询书籍（ST/TC不包含借阅者，AD包含借阅者） 调用权限：ST/TC/AD
	 *
	 * @param id       用户名
	 * @param bookName 书名
	 * @return 查询结果（json字符串）
	 */
	public String queryBook(String id, String bookName) {
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
			// 查询书籍
			List<Book> bookList = BookDao.queryBookByName(bookName);
			if (bookList == null) {
				jsonObject.put("code", "1");
				jsonObject.put("message", "未找到书籍");
			} else {
				String data = impHelper.bookListToStr(',', '|', bookList, true);
				jsonObject.put("code", "0");
				jsonObject.put("message", "查找成功");
				jsonObject.put("data", data);
			}
		} else if (usrRole.equals("ST") || usrRole.equals("TC")) {
			// 查询书籍
			List<Book> bookList = BookDao.queryBookByName(bookName);
			if (bookList == null) {
				jsonObject.put("code", "1");
				jsonObject.put("message", "未找到书籍");
			} else {
				String data = impHelper.bookListToStr(',', '|', bookList, false);
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
	 * 借阅书籍 调用权限：ST/TC
	 *
	 * @param id     用户名
	 * @param bookID 书籍ID
	 * @return 借阅结果（json字符串）
	 */
	public String borrowBook(String id, String bookID, Short borrowCnt) {
		// 创建json对象
		JSONObject jsonObject = new JSONObject();

		// 检查登录情况
		User usr = UserDao.queryUser(id);
		if (usr == null) {
			jsonObject.put("code", "4");
			jsonObject.put("message", "用户不存在");
			return jsonObject.toString();
		} else if (!usr.getSta()) {
			jsonObject.put("code", "-2");
			jsonObject.put("message", "登录异常");
			return jsonObject.toString();
		}
		
		// 验证数据合法性
		if (borrowCnt <= 0) {
			jsonObject.put("code", "-3");
			jsonObject.put("message", "非法数据");
			return jsonObject.toString();
		}

		// 判断权限
		String usrRole = usr.getRole();
		if (usrRole.equals("ST")) {
			// 借阅书籍
			Book book = BookDao.queryBookById(bookID);
			if (book == null) {
				jsonObject.put("code", "1");
				jsonObject.put("message", "未找到书籍");
			} else if (book.get_bookCnt() < borrowCnt) {
				jsonObject.put("code", "3");
				jsonObject.put("message", "库存量不足");
			} else {
				// 修改书籍数据库
				String holder = book.get_bookHolder();
				String holderBackup = holder;
				for (int i = 0; i < borrowCnt; i++) {
					holder = holder + id + ";";
				}
				book.set_bookHolder(holder);
				Short cnt = book.get_bookCnt();
				Short cntBackup = cnt;
				cnt = (short) (cnt - borrowCnt);
				book.set_bookCnt(cnt);
				Byte res1 = BookDao.updateBook(book);
				if (res1 == 1) {
					// 修改学生类
					StuAcc stu = StuAccDao.queryStuAcc(id);
					if (stu == null) {
						jsonObject.put("code", "4");
						jsonObject.put("message", "用户不存在");
						book.set_bookHolder(holderBackup);
						book.set_bookCnt(cntBackup);
						BookDao.updateBook(book);
						return jsonObject.toString();
					}
					String borrowedBook = stu.get_stuAccBook();
					for (int i = 0; i < borrowCnt; i++) {
						borrowedBook = borrowedBook + book.get_bookID() + ";";
					}
					stu.set_stuAccBook(borrowedBook);
					Byte res2 = StuAccDao.updateStuAcc(stu);
					if (res2 == 0) {
						jsonObject.put("code", "0");
						jsonObject.put("message", "借阅成功");
					} else {
						jsonObject.put("code", "-1");
						jsonObject.put("message", "系统错误");
						book.set_bookHolder(holderBackup);
						book.set_bookCnt(cntBackup);
						BookDao.updateBook(book);
					}
				} else {
					jsonObject.put("code", "-1");
					jsonObject.put("message", "系统错误");
				}
			}
		} else if (usrRole.equals("TC")) {
			// 借阅书籍
			Book book = BookDao.queryBookById(bookID);
			if (book == null) {
				jsonObject.put("code", "1");
				jsonObject.put("message", "未找到书籍");
			} else if (book.get_bookCnt() < borrowCnt) {
				jsonObject.put("code", "3");
				jsonObject.put("message", "库存量不足");
			} else {
				// 修改书籍数据库
				String holder = book.get_bookHolder();
				String holderBackup = holder;
				for (int i = 0; i < borrowCnt; i++) {
					holder = holder + id + ";";
				}
				book.set_bookHolder(holder);
				Short cnt = book.get_bookCnt();
				Short cntBackup = cnt;
				cnt = (short) (cnt - borrowCnt);
				book.set_bookCnt(cnt);
				Byte res1 = BookDao.updateBook(book);
				if (res1 == 1) {
					// 修改老师类
					TcAcc tc = TcAccDao.queryTcAcc(id);
					if (tc == null) {
						jsonObject.put("code", "4");
						jsonObject.put("message", "用户不存在");
						book.set_bookHolder(holderBackup);
						book.set_bookCnt(cntBackup);
						BookDao.updateBook(book);
						return jsonObject.toString();
					}
					String borrowedBook = tc.get_tcAccBook();
					for (int i = 0; i < borrowCnt; i++) {
						borrowedBook = borrowedBook + book.get_bookID() + ";";
					}
					tc.set_tcAccBook(borrowedBook);
					Byte res2 = TcAccDao.updateTcAcc(tc);
					if (res2 == 0) {
						jsonObject.put("code", "0");
						jsonObject.put("message", "借阅成功");
					} else {
						jsonObject.put("code", "-1");
						jsonObject.put("message", "系统错误");
						book.set_bookHolder(holderBackup);
						book.set_bookCnt(cntBackup);
						BookDao.updateBook(book);
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
	 * 归还书籍 调用权限：ST/TC
	 *
	 * @param id     用户名
	 * @param bookID 书籍ID
	 * @return 归还结果（json字符串）
	 */
	public String returnBook(String id, String bookID, Short returnCnt) {
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
		if (returnCnt <= 0) {
			jsonObject.put("code", "-3");
			jsonObject.put("message", "非法数据");
			return jsonObject.toString();
		}

		// 判断权限
		String usrRole = usr.getRole();
		if (usrRole.equals("ST")) {
			// 归还书籍
			Book book = BookDao.queryBookById(bookID);
			if (book == null) {
				jsonObject.put("code", "1");
				jsonObject.put("message", "未找到书籍");
			} else {
				// 修改书籍数据库
				String holder = book.get_bookHolder();
				String[] holderArr = holder.split("\\;");
				Short num1 = 0;
				for (int i = 0; i < holderArr.length; i++) {
					if (holderArr[i].equals(id)) {
						num1++;
					}
				}
				if (returnCnt > num1) {
					jsonObject.put("code", "4");
					jsonObject.put("message", "无法归还");
					return jsonObject.toString();
				}
				String holderArrDel = "";
				Short cnt1 = returnCnt;
				for (int i = 0; i < holderArr.length; i++) {
					if (holderArr[i].equals(id) && cnt1 != 0) {
						cnt1--;
					} else if (!holderArr[i].equals(id) || (holderArr[i].equals(id) && cnt1 == 0)) {
						holderArrDel = holderArrDel + holderArr[i] + ";";
					}
				}
				book.set_bookHolder(holderArrDel);
				Short cnt = book.get_bookCnt();
				Short cntBackup = cnt;
				cnt = (short) (cnt + returnCnt);
				book.set_bookCnt(cnt);
				Byte res1 = BookDao.updateBook(book);
				if (res1 == 1) {
					// 修改学生类
					StuAcc stu = StuAccDao.queryStuAcc(id);
					if (stu == null) {
						jsonObject.put("code", "3");
						jsonObject.put("message", "用户不存在");
						book.set_bookHolder(holder);
						book.set_bookCnt(cntBackup);
						BookDao.updateBook(book);
						return jsonObject.toString();
					}
					String borrowedBook = stu.get_stuAccBook();
					String[] borrowedBookArr = borrowedBook.split("\\;");
					Short num2 = 0;
					for (int i = 0; i < borrowedBookArr.length; i++) {
						if (borrowedBookArr[i].equals(bookID)) {
							num2++;
						}
					}
					if (returnCnt > num2) {
						jsonObject.put("code", "4");
						jsonObject.put("message", "无法归还");
						book.set_bookHolder(holder);
						book.set_bookCnt(cntBackup);
						BookDao.updateBook(book);
						return jsonObject.toString();
					}
					String borrowedBookArrDel = "";
					Short cnt2 = returnCnt;
					for (int i = 0; i < borrowedBookArr.length; i++) {
						if (borrowedBookArr[i].equals(bookID) && cnt2 != 0) {
							cnt2--;
						} else if (!borrowedBookArr[i].equals(bookID) || (borrowedBookArr[i].equals(bookID) && cnt2 == 0)) {
							borrowedBookArrDel =borrowedBookArrDel + borrowedBookArr[i] + ";";
						}
					}
					stu.set_stuAccBook(borrowedBookArrDel);
					Byte res2 = StuAccDao.updateStuAcc(stu);
					if (res2 == 0) {
						jsonObject.put("code", "0");
						jsonObject.put("message", "归还成功");
					} else {
						jsonObject.put("code", "-1");
						jsonObject.put("message", "系统错误");
						book.set_bookHolder(holder);
						book.set_bookCnt(cntBackup);
						BookDao.updateBook(book);
					}
				} else {
					jsonObject.put("code", "-1");
					jsonObject.put("message", "系统错误");
				}
			}
		} else if (usrRole.equals("TC")) {
			// 归还书籍
			Book book = BookDao.queryBookById(bookID);
			if (book == null) {
				jsonObject.put("code", "1");
				jsonObject.put("message", "未找到书籍");
			} else {
				// 修改书籍数据库
				String holder = book.get_bookHolder();
				String[] holderArr = holder.split("\\;");
				Short num1 = 0;
				for (int i = 0; i < holderArr.length; i++) {
					if (holderArr[i].equals(id)) {
						num1++;
					}
				}
				if (returnCnt > num1) {
					jsonObject.put("code", "4");
					jsonObject.put("message", "无法归还");
					return jsonObject.toString();
				}
				String holderArrDel = "";
				Short cnt1 = returnCnt;
				for (int i = 0; i < holderArr.length; i++) {
					if (holderArr[i].equals(id) && cnt1 != 0) {
						cnt1--;
					} else if (!holderArr[i].equals(id) || (holderArr[i].equals(id) && cnt1 == 0)) {
						holderArrDel = holderArrDel + holderArr[i] + ";";
					}
				}
				book.set_bookHolder(holderArrDel);
				Short cnt = book.get_bookCnt();
				Short cntBackup = cnt;
				cnt = (short) (cnt + returnCnt);
				book.set_bookCnt(cnt);
				Byte res1 = BookDao.updateBook(book);
				if (res1 == 1) {
					// 修改老师类
					TcAcc tc = TcAccDao.queryTcAcc(id);
					if (tc == null) {
						jsonObject.put("code", "3");
						jsonObject.put("message", "用户不存在");
						book.set_bookHolder(holder);
						book.set_bookCnt(cntBackup);
						BookDao.updateBook(book);
						return jsonObject.toString();
					}
					String borrowedBook = tc.get_tcAccBook();
					String[] borrowedBookArr = borrowedBook.split("\\;");
					Short num2 = 0;
					for (int i = 0; i < borrowedBookArr.length; i++) {
						if (borrowedBookArr[i].equals(bookID)) {
							num2++;
						}
					}
					if (returnCnt > num2) {
						jsonObject.put("code", "4");
						jsonObject.put("message", "无法归还");
						book.set_bookHolder(holder);
						book.set_bookCnt(cntBackup);
						BookDao.updateBook(book);
						return jsonObject.toString();
					}
					String borrowedBookArrDel = "";
					Short cnt2 = returnCnt;
					for (int i = 0; i < borrowedBookArr.length; i++) {
						if (borrowedBookArr[i].equals(bookID) && cnt2 != 0) {
							cnt2--;
						} else if (!borrowedBookArr[i].equals(bookID) || (borrowedBookArr[i].equals(bookID) && cnt2 == 0)) {
							borrowedBookArrDel = borrowedBookArrDel + borrowedBookArr[i] + ";";
						}
					}
					tc.set_tcAccBook(borrowedBookArrDel);
					Byte res2 = TcAccDao.updateTcAcc(tc);
					if (res2 == 0) {
						jsonObject.put("code", "0");
						jsonObject.put("message", "归还成功");
					} else {
						jsonObject.put("code", "-1");
						jsonObject.put("message", "系统错误");
						book.set_bookHolder(holder);
						book.set_bookCnt(cntBackup);
						BookDao.updateBook(book);
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
	 * 添加书籍 调用权限：AD
	 *
	 * @param id       用户名
	 * @param bookID   书籍ID
	 * @param bookName 书名
	 * @param bookCnt  库存量
	 * @return
	 */
	public String addBook(String id, String bookID, String bookName, Short bookCnt) {
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
		if (bookCnt < 0) {
			jsonObject.put("code", "-3");
			jsonObject.put("message", "非法数据");
			return jsonObject.toString();
		}

		// 判断权限
		String usrRole = usr.getRole();
		if (usrRole.equals("AD")) {
			// 添加书籍
			Book book = new Book(bookID, bookName, bookCnt, "");
			Byte res = BookDao.addBook(book);
			if (res == 1) {
				jsonObject.put("code", "0");
				jsonObject.put("message", "添加成功");
			} else if (res == -1) {
				jsonObject.put("code", "1");
				jsonObject.put("message", "书籍已存在");
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
	 * 删除书籍 调用权限：AD
	 *
	 * @param id     用户名
	 * @param bookID 书籍ID
	 * @return
	 */
	public String delBook(String id, String bookID) {
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
			Book book = BookDao.queryBookById(bookID);
			if (book == null) {
				jsonObject.put("code", "1");
				jsonObject.put("message", "书籍不存在");
			} else if (!book.get_bookHolder().equals("")) {
				jsonObject.put("code", "4");
				jsonObject.put("message", "无法删除");
			} else {
				// 删除书籍
				Byte res = BookDao.delBook(bookID);
				if (res == 1) {
					jsonObject.put("code", "0");
					jsonObject.put("message", "删除成功");
				} else if (res == -1) {
					jsonObject.put("code", "1");
					jsonObject.put("message", "书籍不存在");
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
	 * 查询已借书籍 调用权限：ST/TC
	 *
	 * @param id
	 */
	public String queryBorrowedBook(String id) {
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
			// 查询查询已借书籍
			StuAcc stu = StuAccDao.queryStuAcc(id);
			if (stu == null) {
				jsonObject.put("code", "3");
				jsonObject.put("message", "用户不存在");
				return jsonObject.toString();
			}
			String borrowedBook = stu.get_stuAccBook();
			if (borrowedBook.equals("")) {
				jsonObject.put("code", "1");
				jsonObject.put("message", "未找到书籍");
				return jsonObject.toString();
			}
			String[] borrowedBookArr = borrowedBook.split("\\;");
			String data = "";
			for (int i = 0; i < borrowedBookArr.length; i++) {
				Book book = BookDao.queryBookById(borrowedBookArr[i]);
				if (book == null) {
					jsonObject.put("code", "1");
					jsonObject.put("message", "未找到书籍");
					return jsonObject.toString();
				} else {
					data = data + book.get_bookID() + "," + book.get_bookName() + ",|";
				}
			}
			jsonObject.put("code", "0");
			jsonObject.put("message", "查询成功");
			jsonObject.put("data", data);
		} else if (usrRole.equals("TC")) {
			// 查询查询已借书籍
			TcAcc tc = TcAccDao.queryTcAcc(id);
			if (tc == null) {
				jsonObject.put("code", "3");
				jsonObject.put("message", "用户不存在");
				return jsonObject.toString();
			}
			String borrowedBook = tc.get_tcAccBook();
			if (borrowedBook.equals("")) {
				jsonObject.put("code", "1");
				jsonObject.put("message", "未找到书籍");
				return jsonObject.toString();
			}
			String[] borrowedBookArr = borrowedBook.split("\\;");
			String data = "";
			for (int i = 0; i < borrowedBookArr.length; i++) {
				Book book = BookDao.queryBookById(borrowedBookArr[i]);
				if (book == null) {
					jsonObject.put("code", "-1");
					jsonObject.put("message", "系统错误");
					return jsonObject.toString();
				} else {
					data = data + book.get_bookID() + "," + book.get_bookName() + ",|";
				}
			}
			jsonObject.put("code", "0");
			jsonObject.put("message", "查询成功");
			jsonObject.put("data", data);
		} else {
			jsonObject.put("code", "2");
			jsonObject.put("message", "权限不足");
		}

		return jsonObject.toString();
	}

	/**
	 * 修改书籍name||cnt 调用权限：AD
	 *
	 * @param id
	 * @param bookid
	 * @param bookName
	 * @param bookCnt
	 * @param bookHolder
	 * @return 修改结果（json字符串）
	 */
	public String updateBookNameCnt(String id, String bookID, String bookName, Short bookCnt) {
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
		if (bookCnt < 0) {
			jsonObject.put("code", "-3");
			jsonObject.put("message", "非法数据");
			return jsonObject.toString();
		}

		// 判断权限
		String usrRole = usr.getRole();
		if (usrRole.equals("AD")) {
			// 修改书籍
			Book book = BookDao.queryBookById(bookID);
			if (book == null) {
				jsonObject.put("code", "1");
				jsonObject.put("message", "书籍不存在");
				return jsonObject.toString();
			}
			book.set_bookName(bookName);
			book.set_bookCnt(bookCnt);
			Byte res = BookDao.updateBook(book);
			if (res == 1) {
				jsonObject.put("code", "0");
				jsonObject.put("message", "修改成功");
			} else if (res == -1) {
				jsonObject.put("code", "1");
				jsonObject.put("message", "书籍不存在");
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
	 * 添加书籍借阅者 调用权限：AD
	 *
	 * @param id
	 * @param bookID
	 * @param addID
	 * @return 修改结果（json字符串）
	 */
	public String addBookHolder(String id, String bookID, String addID) {
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
			// 修改书籍
			Book book = BookDao.queryBookById(bookID);
			if (book == null) {
				jsonObject.put("code", "1");
				jsonObject.put("message", "书籍不存在");
				return jsonObject.toString();
			}
			String holder = book.get_bookHolder();
			String holderBackup = holder;
			holder = holder + addID + ";";
			book.set_bookHolder(holder);
			Short cnt = book.get_bookCnt();
			Short cntBackup = cnt;
			cnt--;
			book.set_bookCnt(cnt);
			Byte res1 = BookDao.updateBook(book);
			if (res1 == 1) {
				// 修改借阅者
				User user = UserDao.queryUser(addID);
				if (user == null) {
					jsonObject.put("code", "3");
					jsonObject.put("message", "用户不存在");
					book.set_bookHolder(holderBackup);
					book.set_bookCnt(cntBackup);
					BookDao.updateBook(book);
					return jsonObject.toString();
				}
				if (user.getRole().equals("TC")) {
					TcAcc tc = TcAccDao.queryTcAcc(addID);
					if (tc == null) {
						jsonObject.put("code", "3");
						jsonObject.put("message", "用户不存在");
						book.set_bookHolder(holderBackup);
						book.set_bookCnt(cntBackup);
						BookDao.updateBook(book);
						return jsonObject.toString();
					}
					String borrowedBook = tc.get_tcAccBook();
					borrowedBook = borrowedBook + bookID + ";";
					tc.set_tcAccBook(borrowedBook);
					Byte res2 = TcAccDao.updateTcAcc(tc);
					if (res2 == 0) {
						jsonObject.put("code", "0");
						jsonObject.put("message", "添加成功");
					} else {
						jsonObject.put("code", "-1");
						jsonObject.put("message", "系统错误");
						book.set_bookHolder(holderBackup);
						book.set_bookCnt(cntBackup);
						BookDao.updateBook(book);
					}
				} else if (user.getRole().equals("ST")) {
					StuAcc stu = StuAccDao.queryStuAcc(addID);
					if (stu == null) {
						jsonObject.put("code", "3");
						jsonObject.put("message", "用户不存在");
						book.set_bookHolder(holderBackup);
						book.set_bookCnt(cntBackup);
						BookDao.updateBook(book);
						return jsonObject.toString();
					}
					String borrowedBook = stu.get_stuAccBook();
					borrowedBook = borrowedBook + bookID + ";";
					stu.set_stuAccBook(borrowedBook);
					Byte res2 = StuAccDao.updateStuAcc(stu);
					if (res2 == 0) {
						jsonObject.put("code", "0");
						jsonObject.put("message", "添加成功");
					} else {
						jsonObject.put("code", "-1");
						jsonObject.put("message", "系统错误");
						book.set_bookHolder(holderBackup);
						book.set_bookCnt(cntBackup);
						BookDao.updateBook(book);
					}
				} else {
					jsonObject.put("code", "4");
					jsonObject.put("message", "无法添加");
					book.set_bookHolder(holderBackup);
					book.set_bookCnt(cntBackup);
					BookDao.updateBook(book);
				}
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
	 * 删除书籍借阅者 调用权限：AD
	 *
	 * @param id
	 * @param bookID
	 * @param delid
	 * @return 修改结果（json字符串）
	 */
	public String delBookHolder(String id, String bookID, String delid) {
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
			// 修改书籍
			Book book = BookDao.queryBookById(bookID);
			if (book == null) {
				jsonObject.put("code", "1");
				jsonObject.put("message", "书籍不存在");
				return jsonObject.toString();
			}
			String holder = book.get_bookHolder();
			String[] holderArr = holder.split("\\;");
			Boolean isExistHolder = false;
			for (int i = 0; i < holderArr.length; i++) {
				if (holderArr[i].equals(delid)) {
					isExistHolder = true;
				}
			}
			if (isExistHolder == false) {
				jsonObject.put("code", "5");
				jsonObject.put("message", "借阅者不存在");
				return jsonObject.toString();
			}
			String holderArrDel = "";
			Short cnt1 = 1;
			for (int i = 0; i < holderArr.length; i++) {
				if (holderArr[i].equals(id) && cnt1 != 0) {
					cnt1--;
				} else if (!holderArr[i].equals(id) || (holderArr[i].equals(id) && cnt1 == 0)) {
					holderArrDel = holderArrDel + holderArr[i] + ";";
				}
			}
			book.set_bookHolder(holderArrDel);
			Short cnt = book.get_bookCnt();
			Short cntBackup = cnt;
			cnt--;
			book.set_bookCnt(cnt);
			Byte res1 = BookDao.updateBook(book);
			if (res1 == 1) {
				// 修改借阅者
				User user = UserDao.queryUser(delid);
				if (user == null) {
					jsonObject.put("code", "3");
					jsonObject.put("message", "用户不存在");
					book.set_bookHolder(holder);
					book.set_bookCnt(cntBackup);
					BookDao.updateBook(book);
					return jsonObject.toString();
				}
				if (user.getRole().equals("TC")) {
					TcAcc tc = TcAccDao.queryTcAcc(delid);
					if (tc == null) {
						jsonObject.put("code", "3");
						jsonObject.put("message", "用户不存在");
						book.set_bookHolder(holder);
						book.set_bookCnt(cntBackup);
						BookDao.updateBook(book);
						return jsonObject.toString();
					}
					String borrowedBook = tc.get_tcAccBook();
					String[] borrowedBookArr = borrowedBook.split("\\;");
					Boolean isExistBook = false;
					for (int i = 0; i < borrowedBookArr.length; i++) {
						if (holderArr[i].equals(delid)) {
							isExistBook = true;
						}
					}
					if (isExistBook == false) {
						jsonObject.put("code", "0");
						jsonObject.put("message", "删除成功");
						return jsonObject.toString();
					}
					String borrowedBookDel = "";
					Short cnt2 = 1;
					for (int i = 0; i < borrowedBookArr.length; i++) {
						if (borrowedBookArr[i].equals(bookID) && cnt2 != 0) {
							cnt2--;
						} else if (!borrowedBookArr[i].equals(bookID)
								|| (borrowedBookArr[i].equals(bookID) && cnt2 == 0)) {
							borrowedBookDel = borrowedBookDel + borrowedBookArr[i] + ";";
						}
					}
					tc.set_tcAccBook(borrowedBookDel);
					Byte res2 = TcAccDao.updateTcAcc(tc);
					if (res2 == 0) {
						jsonObject.put("code", "0");
						jsonObject.put("message", "删除成功");
					} else {
						jsonObject.put("code", "-1");
						jsonObject.put("message", "系统错误");
						book.set_bookHolder(holder);
						book.set_bookCnt(cntBackup);
						BookDao.updateBook(book);
					}
				} else if (user.getRole().equals("ST")) {
					StuAcc stu = StuAccDao.queryStuAcc(delid);
					if (stu == null) {
						jsonObject.put("code", "3");
						jsonObject.put("message", "用户不存在");
						book.set_bookHolder(holder);
						book.set_bookCnt(cntBackup);
						BookDao.updateBook(book);
						return jsonObject.toString();
					}
					String borrowedBook = stu.get_stuAccBook();
					String[] borrowedBookArr = borrowedBook.split("\\;");
					Boolean isExistBook = false;
					for (int i = 0; i < borrowedBookArr.length; i++) {
						if (holderArr[i].equals(delid)) {
							isExistBook = true;
						}
					}
					if (isExistBook == false) {
						jsonObject.put("code", "0");
						jsonObject.put("message", "删除成功");
						return jsonObject.toString();
					}
					String borrowedBookDel = "";
					Short cnt2 = 1;
					for (int i = 0; i < borrowedBookArr.length; i++) {
						if (borrowedBookArr[i].equals(bookID) && cnt2 != 0) {
							cnt2--;
						} else if (!borrowedBookArr[i].equals(bookID)
								|| (borrowedBookArr[i].equals(bookID) && cnt2 == 0)) {
							borrowedBookDel = borrowedBookDel + borrowedBookArr[i] + ";";
						}
					}
					stu.set_stuAccBook(borrowedBookDel);
					Byte res2 = StuAccDao.updateStuAcc(stu);
					if (res2 == 0) {
						jsonObject.put("code", "0");
						jsonObject.put("message", "删除成功");
					} else {
						jsonObject.put("code", "-1");
						jsonObject.put("message", "系统错误");
						book.set_bookHolder(holder);
						book.set_bookCnt(cntBackup);
						BookDao.updateBook(book);
					}
				} else {
					jsonObject.put("code", "4");
					jsonObject.put("message", "无法删除");
					book.set_bookHolder(holder);
					book.set_bookCnt(cntBackup);
					BookDao.updateBook(book);
				}
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

}
