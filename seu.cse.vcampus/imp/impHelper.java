package imp;

import java.util.ArrayList;
import java.util.List;

import dao.CourseDao;
import vo.Book;
import vo.Course;
import vo.StuAcc;
import vo.TcAcc;
import vo.CourseTime;
import vo.Dormitory;
import vo.StoreItem;

/**
 * 类 {@code impHelper}提供业务功能实现中需要的工具函数
 * 
 * @author yfyou
 * @author wwb
 * 
 * @since 2023/08/30
 */

public class impHelper {
	/**
	 * 将字符串数组转化为字符串
	 * 
	 * @param split 分隔符
	 * @param arr   需要转换的字符串数组
	 * @return 转换结果
	 * @throws NullPointerException arr为空抛出异常
	 */
	public static String arrToStr(Character split, String... arr) throws NullPointerException {
		StringBuilder sb = new StringBuilder();

		int len = arr.length;

		for (int i = 0; i < len; ++i) {
			sb.append(arr[i]).append(split);
		}

		return sb.toString();
	}

	/**
	 * 将bookList转化为字符串
	 * 
	 * @param split1    分隔符1
	 * @param split2    分隔符2
	 * @param bookList  需要转换的List
	 * @param holder    是否包括借阅者
	 * @return 转换结果
	 */
	public static String bookListToStr(Character split1, Character split2, List<Book> bookList, Boolean holder) {

		StringBuilder sb = new StringBuilder();

		if (holder == true) {
			for (Book item : bookList) {
				sb.append(item.get_bookID()).append(split1);
				sb.append(item.get_bookName()).append(split1);
				sb.append(item.get_bookCnt()).append(split1);
				sb.append(item.get_bookHolder()).append(split1).append(split2);
			}
		} else {
			for (Book item : bookList) {
				sb.append(item.get_bookID()).append(split1);
				sb.append(item.get_bookName()).append(split1);
				sb.append(item.get_bookCnt()).append(split1).append(split2);
			}
		}

		return sb.toString();
	}
	
	/**
	 * 将itemList转化为字符串
	 * 
	 * @param split1    分隔符1
	 * @param split2    分隔符2
	 * @param itemList  需要转换的List
	 * @return 转换结果
	 */
	public static String itemListToStr(Character split1, Character split2, List<StoreItem> itemList) {

		StringBuilder sb = new StringBuilder();

		for (StoreItem item : itemList) {
			sb.append(item.get_itemID()).append(split1);
			sb.append(item.get_itemName()).append(split1);
			sb.append(item.get_itemPri()).append(split1);
			sb.append(item.get_itemCnt()).append(split1).append(split2);
		}

		return sb.toString();
	}
	
	/**
	 * 将dormitoryList转化为字符串
	 * 
	 * @param split1    分隔符1
	 * @param split2    分隔符2
	 * @param itemList  需要转换的List
	 * @return 转换结果
	 */
	public static String dormitoryListToStr(Character split1, Character split2, List<Dormitory> dormitoryList,Boolean member) {

		StringBuilder sb = new StringBuilder();

		if(member == true) {
			for (Dormitory item : dormitoryList) {
				sb.append(item.get_domID()).append(split1);
				sb.append(item.get_domCnt()).append(split1);
				sb.append(item.get_domMem()).append(split1).append(split2);
			}
		}
		else {
			for (Dormitory item : dormitoryList) {
				sb.append(item.get_domID()).append(split1);
				sb.append(item.get_domCnt()).append(split1).append(split2);
			}
		}

		return sb.toString();
	}
	
	/**
	 * 将课程时间实例转换为String用于数据库存储
	 * @param ct		课程时间
	 * @return			字符串格式的课程
	 */
	public static String courseTimeToStr(CourseTime ct) {
		Byte day = ct.get_day();
		Byte startHour = ct.get_startHour();
		Byte startMin = ct.get_startMin();
		Byte endHour = ct.get_endHour();
		Byte endMin = ct.get_endMin();
		String ctID = ct.get_ctID();
		
		return ctID+','+day.toString()+','+startHour.toString()+','+startMin.toString()+','+endHour.toString()+','+endMin.toString()+',';
	}
	
	/**
	 * 将String转换成课程时间
	 * @param str		要转换的字符串
	 * @return			课程时间实例
	 */
	public static CourseTime strToCourseTime(String str) {
		String[] tmp = str.split("\\,");
		
		try {
			String ctID = tmp[0];
			Byte day = (byte)Integer.parseInt(tmp[1]);
			Byte startHour=(byte)Integer.parseInt(tmp[2]);
			Byte startMin=(byte)Integer.parseInt(tmp[3]);
			Byte endHour=(byte)Integer.parseInt(tmp[4]);
			Byte endMin=(byte)Integer.parseInt(tmp[5]);
			
			return new CourseTime(day,startHour,startMin,endHour,endMin,ctID);
		}
		catch(NumberFormatException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 将课程列表转为字符串（不包括选课名单）
	 * @param courList		需要转换的课程表
	 * @return				课程表的字符串
	 */
	public static String stuCourListToStr(ArrayList<Course> courList) {
		String res ="";
		
		for(Course c:courList) {
			res = res +
					c.get_courID() + ';' + c.get_courName() + ';' +
					c.get_courTchr() + ';' + courseTimeToStr(c.get_courTime()) + ';' +
					c.get_courCap().toString() + ";|";
		}
		
		return res;
	}
	
	/**
	 * 将课程表转为字符串（包括选课名单）
	 * @param courList		需要转换的课程表
	 * @return				课程表的字符串
	 */
	public static String adCourListToStr(ArrayList<Course> courList) {
		String res ="";
		
		for(Course c:courList) {
			res = res +
					c.get_courID() + ';' + c.get_courName() + ';' +
					c.get_courTchr() + ';' + courseTimeToStr(c.get_courTime()) + ';' +
					c.get_courCap().toString() + ';' + c.get_courStuList() + ";|";
		}
		
		return res;
	}
	
	/**
	 * 将课程表转为字符串（不包括选课名单）
	 * @param courList		需要转换的课程表
	 * @return				课程表的字符串
	 */
	public static String stuCourListToStr(List<Course> courList) {
		String res ="";
		
		for(Course c:courList) {
			res = res +
					c.get_courID() + ';' + c.get_courName() + ';' +
					c.get_courTchr() + ';' + courseTimeToStr(c.get_courTime()) + ';' +
					c.get_courCap().toString() + ";|";
		}
		
		return res;
	}
	
	/**
	 * 将课程表转为字符串（包括选课名单）
	 * @param courList		需要转换的课程表
	 * @return				课程表的字符串
	 */
	public static String adCourListToStr(List<Course> courList) {
		String res ="";
		
		for(Course c:courList) {
			res = res +
					c.get_courID() + ';' + c.get_courName() + ';' +
					c.get_courTchr() + ';' + courseTimeToStr(c.get_courTime()) + ';' +
					c.get_courCap().toString() + ';' + c.get_courStuList() + ";|";
		}
		
		return res;
	}
	
	/**
	 * 获取选课人数
	 * @param cour		指定的课程
	 * @return			人数
	 */
	public static Short getStuCnt(Course cour) {
		String stuList = cour.get_courStuList();
		if(stuList==null) {
			return -1;
		}
		else if(stuList.equals("")) {
			return 0;
		}
		return (short)stuList.split("\\,").length;
	}
	
	/**
	 * 检查上课时间是否冲突
	 * @param acc		选课学生的账户信息
	 * @param cour		选择的课程
	 * @return			检查结果
	 */
	public static Boolean checkCourTime(StuAcc acc, Course cour) {
		if(acc==null||cour==null) {
			return false;
		}
		
		//获取课表（id）
		String[] courList = acc.get_stuAccCour().split("\\;");
		CourseTime toSelect = cour.get_courTime();
		
		//检查时间
		for(String courID:courList) {
			//从数据库获取课程信息
			Course cur = CourseDao.queryCourse(courID);
			
			if(cur==null) {
				continue;
			}
			
			//test output
			System.out.println(courseTimeToStr(cur.get_courTime()));
			System.out.println(courseTimeToStr(toSelect));
			
			CourseTime selected = cur.get_courTime();
			
			if(!isCompatible(toSelect,selected)) {
				return false;
			}
		}
		return true;
	}
	
	private static Boolean isCompatible(CourseTime a, CourseTime b) {
		return (a.get_day()!=b.get_day()||
				a.get_endHour()<b.get_startHour()||
				(a.get_endHour()==b.get_startHour()&&a.get_endMin()<=b.get_startMin())||
				a.get_startHour()>b.get_endHour()||
				(a.get_startHour()==b.get_endHour()&&a.get_startMin()>=b.get_endMin()));
	}
	
	/**
	 * 检查课程是否已被选择
	 * @param acc		学生账户信息
	 * @param cour		要选择的课程
	 * @return			检查结果
	 */
	public static Boolean isSelected(StuAcc acc, Course cour) {
		if(acc==null||cour==null) {
			return true;
		}
		//获取课表（id）
		String[] courList = acc.get_stuAccCour().split("\\;");
		String toSelect = cour.get_courID();
		
		//检查是否选过这门课
		for(String curID:courList) {
			if(toSelect.equals(curID)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 删除学生账户中对应课程的信息
	 * @param courID		课程编号
	 * @param stuAcc		学生账户
	 */
	public static void delStuAccCourseData(String courID, StuAcc stuAcc) {
		String stuAccCour = stuAcc.get_stuAccCour();
		String[] cours = stuAccCour.split("\\;");//分割得到每个课程
		String res = "";
		
		for(String id:cours) {
			if(!id.equals(courID)) {//删除courID
				res = res + id + ';';
			}
		}
		
		//修改学生账户信息
		stuAcc.set_stuAccCour(res);
	}
	
	/**
	 * 删除老师账户中对应课程的信息
	 * @param courID		课程编号
	 * @param tcAcc			老师账户
	 */
	public static void delTcAccCourseData(String courID, TcAcc tcAcc) {
		String tcAccCour = tcAcc.get_tcAccCour();
		String[] cours = tcAccCour.split("\\;");//分割得到每个课程
		String res = "";
		
		for(String id:cours) {
			if(!id.equals(courID)) {//删除courID
				res = res + id + ';';
			}
		}
		
		//修改老师账户信息
		tcAcc.set_tcAccCour(res);
	}
	
	/**
	 * 从给定课程选课名单中删除学生
	 * @param cour			需要操作的课程
	 * @param stuNum		需要删除的学生学号
	 */
	public static void delStuFromCour(Course cour, String stuNum) {
		String[] stuList = cour.get_courStuList().split("\\,");
		String res = "";
		
		for(String stu:stuList) {
			if(!stu.equals(stuNum)) {//删除stuNum
				res = res + stu + ',';
			}
		}
		
		//修改选课名单
		cour.set_courStuList(res);
	}
	
	
//	public static void main(String[] args) {
//		Course a = new Course("courID","courName","courTchr",null,(short)0,"n1,n2,n3,n4,n5,");
//		delStuFromCour(a,"n2");
//		System.out.println(a.get_courStuList());
//	}
//	public static void main(String[] args) {
//		CourseTime ct = new CourseTime((byte)1,(byte)2,(byte)3,(byte)4,(byte)5,"aaaa");
//		String str = courseTimeToStr(ct);
//		System.out.println(str);
//		CourseTime ct2 = strToCourseTime(str);
//		System.out.println(ct2.get_ctID());
//		System.out.println(ct2.get_day());
//		System.out.println(ct2.get_startHour());
//		System.out.println(ct2.get_startMin());
//		System.out.println(ct2.get_endHour());
//		System.out.println(ct2.get_endMin());
//	}
//	public static void main(String[] args) {
//		CourseTime ct = new CourseTime((byte)1,(byte)15,(byte)30,(byte)17,(byte)0,"aaaa");
//		StuAcc acc = new StuAcc(null,"courID;courName;ctid,1,13,30,15,30,;courTchr;|courID2;courName2;ctid2,1,17,00,18,30,;courTchr2;|",null,null,null,0.0);
//		Course cour = new Course(null,null,null,ct,(short)0,null);
//		System.out.println(checkCourTime(acc,cour));
//	}
//	public static void main(String[] args) {
//		CourseTime ct = new CourseTime((byte)1,(byte)15,(byte)30,(byte)17,(byte)0,"aaaa");
//		Course a = new Course("courID","courName","courTchr",ct,(short)0,"stuNum,stuName,&stuNum2,stuName2,&");
//		Course b = new Course("courID2","courName2","courTchr2",ct,(short)1,"stuNum,stuName,&stuNum2,stuName2,&");
//		List<Course> courList = new ArrayList<>();
//		
//		courList.add(a);
//		courList.add(b);
//		
//		System.out.println(adCourListToStr(courList));
//	}
//	public static void main(String[] args) {
//		StuAcc stuAcc = new StuAcc(null,"0;1;",null,null,null,0.0);
//		TcAcc tcAcc = new TcAcc(null,"0;1;",null,null,0.0,null);
//		
//		delStuAccCourseData("0",stuAcc);
//		delTcAccCourseData("0",tcAcc);
//		
//		System.out.println(stuAcc.get_stuAccCour());
//		System.out.println(tcAcc.get_tcAccCour());
//	}
//	public static void main(String[] args) {
//		StuAcc acc = new StuAcc(null,"courID;courID2;",null,null,null,0.0);
//		Course a = new Course("courID","courName","courTchr",null,(short)0,"n1,n2,n3,n4,n5,");
//		Course b = new Course("courID3","courName","courTchr",null,(short)0,"");
//		System.out.println(isSelected(acc,a));
//		System.out.println(isSelected(acc,b));
//	}
}
