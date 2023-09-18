package server;

/**
 * 类 {@code Server}服务器主进程
 * 
 * @author yfyou
 * @author wwb
 * 
 * @since 2023/08/23
 */

import java.io.*;
import java.net.*;
import org.json.JSONObject;
import org.json.JSONException;
import api.*;
import dao.DormitoryDao;
import imp.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import vo.Student;

public class Server {
	private static final int PORT = 11451;// 指定监听的端口号
	private static final int THREAD_CNT = 24;//线程数
    private static Map<String, LoginTimer> loginTimers = new ConcurrentHashMap<>();//登录状态计时器map
	
    public static void main(String[] args) {
    	// 创建线程池
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_CNT);

        //服务器启动时初始化宿舍（在数据库中添加10个宿舍，数据库中已有则不会重复添加）
        DormitoryDao.initialDormitory();
        
        
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("[Server]Listening on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("[Client]Connected: " + clientSocket.getInetAddress());

                // 取一个线程来处理每个连接
                executor.execute(() -> handleClient(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
        	String response;//服务器返回的数据
        	try {
        		System.out.println("[Server]Start handling...");
        		
        		JSONObject obj = new JSONObject(reader.readLine());//客户端发来的数据
        		String request = obj.getString("request");//客户端请求
        		
        		System.out.println("[Client]Request:" + request);
        		System.out.println("[Client]Data:" + obj.toString());
                    
            	if(request.equals("login")) {
            		Account api = new AccountSys();
            		//解析json字符串
            		String id = obj.getString("id");
            		String pwd = obj.getString("password");
            		String role = obj.getString("role");
            		
            		//返回结果
                	response = api.logIn(id, pwd, role);
                	
                	
                	JSONObject res = new JSONObject(response);
                	String resCode = res.getString("code");
                	if(resCode.equals("0")) {
                		//创建登录检测定时器
                		loginTimers.put(id, new LoginTimer());
                		loginTimers.get(id).runTimer(id);
                	}
            	}
            	else if(request.equals("logout")) {
            		Account api = new AccountSys();
            		//解析json字符串
                	String id = obj.getString("id");
                	
                	//返回结果
                	response = api.logOut(id);
                	
                	JSONObject res = new JSONObject(response);
                	String resCode = res.getString("code");
                	if(resCode == "0") {
                		//删除计时器
                		loginTimers.remove(id).shutdownExec();
                	}
            	}
            	else if(request.equals("register")) {
            		Account api = new AccountSys();
            		//解析json字符串
                	String id = obj.getString("id");
                	String pwd = obj.getString("password");
                	String role = obj.getString("role");
                	String mail = obj.getString("mail");
                	
                	//返回结果
                	response = api.register(id, pwd, role, mail);
            	}
            	else if(request.equals("changepwd")) {
            		Account api = new AccountSys();
            		//解析json字符串
            		String id = obj.getString("id");
            		String pwd = obj.getString("password");
            		String newPwd = obj.getString("newpwd");
                	
            		//返回结果
            		response = api.changePwd(id, pwd, newPwd);
            	}
            	else if(request.equals("logincheck")) {
            		String id = obj.getString("id");
            		loginTimers.get(id).check = true;//更新状态
            		response = "doNotResponse";
            	}
            	else if(request.equals("getstat")) {
            		StuStat statApi = new StuStatSys();
            		//解析json字符串
            		String usrID = obj.getString("usrid");
            		String stuNum = obj.getString("stunum");
            		
            		//返回结果
            		response = statApi.getStat(usrID, stuNum);
            	}
            	else if(request.equals("setstat")) {
            		StuStat statApi = new StuStatSys();
            		//解析json字符串
            		String usrID = obj.getString("usrid");

            		Student stu = new Student(obj.getString("stunum"),obj.getString("stuname"),
            					(byte)obj.getInt("stuage"),obj.getBoolean("stugend"),
            					obj.getString("stuid"),obj.getString("stusch"),
            					obj.getString("stumajor"),obj.getString("stuaddr"),
            					obj.getString("stuintake"));
            		
            		//返回结果
            		response = statApi.setStat(usrID, stu);
            	}
            	else if(request.equals("delstat")) {
            		StuStat statApi = new StuStatSys();
            		//解析json字符串
            		String usrID = obj.getString("usrid");
            		String stuNum = obj.getString("stunum");

            		//返回结果
            		response = statApi.delStat(usrID, stuNum);
            	}
            	else if(request.equals("addstat")) {
            		StuStat statApi = new StuStatSys();
            		//解析json字符串
            		String usrID = obj.getString("usrid");

            		Student stu = new Student(obj.getString("stunum"),obj.getString("stuname"),
            					(byte)obj.getInt("stuage"),obj.getBoolean("stugend"),
            					obj.getString("stuid"),obj.getString("stusch"),
            					obj.getString("stumajor"),obj.getString("stuaddr"),
            					obj.getString("stuintake"));
            		
            		//返回结果
            		response = statApi.addStat(usrID, stu);
            	}
            	else if(request.equals("stusel")) {
            		CourseSel api = new CourseSelSys();
            		//解析json字符串
                	String usrID = obj.getString("usrid");
                	String courID = obj.getString("courid");
                	
                	//返回结果
                	response = api.stuSelectCourse(usrID, courID);
            	}
            	else if(request.equals("stuquerysel")) {
            		CourseSel api = new CourseSelSys();
            		//解析json字符串
                	String usrID = obj.getString("usrid");
                	
                	//返回结果
                	response = api.stuQuerySelectedCourse(usrID);
            	}
            	else if(request.equals("tcquerysel")) {
            		CourseSel api = new CourseSelSys();
            		//解析json字符串
                	String usrID = obj.getString("usrid");
                	
                	//返回结果
                	response = api.tcQuerySelectedCourse(usrID);
            	}
            	else if(request.equals("addcour")) {
            		CourseSel api = new CourseSelSys();
            		//解析json字符串
                	String usrID = obj.getString("usrid");
                	String courID = obj.getString("courid");
                	String courName = obj.getString("courname");
                	String courTchr = obj.getString("courtchr");
                	String courTime = obj.getString("courtime");
                	Short courCap = (short)obj.getInt("courcap");
                	
                	//返回结果
                	response = api.adAddCourse(usrID, courID, courName, courTchr, courTime, courCap);
            	}
            	else if(request.equals("delcour")) {
            		CourseSel api = new CourseSelSys();
            		//解析json字符串
                	String usrID = obj.getString("usrid");
                	String courID = obj.getString("courid");
                	
                	response = api.adDelCourse(usrID, courID);
            	}
            	else if(request.equals("updatecour")) {
            		CourseSel api = new CourseSelSys();
            		//解析json字符串
                	String usrID = obj.getString("usrid");
                	String courID = obj.getString("courid");
                	String courName = obj.getString("courname");
                	String courTchr = obj.getString("courtchr");
                	String courTime = obj.getString("courtime");
                	Short courCap = (short)obj.getInt("courcap");
                	String courStuList = obj.getString("courstulist");
                	
                	response = api.adUpdateCourse(usrID, courID, courName, courTchr, courTime, courCap, courStuList);
            	}
            	else if(request.equals("queryallcour")) {
            		CourseSel api = new CourseSelSys();
            		//解析json字符串
                	String usrID = obj.getString("usrid");
                	
                	response = api.queryAllCourse(usrID);
            	}
            	else if(request.equals("querycour")) {
            		CourseSel api = new CourseSelSys();
            		//解析json字符串
                	String usrID = obj.getString("usrid");
                	String courID = obj.getString("courid");
                	
                	response = api.queryCourse(usrID, courID);
            	}
            	else if(request.equals("querycourbyname")) {
            		CourseSel api = new CourseSelSys();
            		//解析json字符串
                	String usrID = obj.getString("usrid");
                	String courName = obj.getString("courname");
                	
                	response = api.queryCourseByName(usrID, courName);
            	}
            	else if(request.equals("updatecourtchr")) {
            		CourseSel api = new CourseSelSys();
            		//解析json字符串
                	String usrID = obj.getString("usrid");
                	String courID = obj.getString("courid");
                	String newTchr = obj.getString("newtchr");
                	
                	response = api.updateCourTchr(usrID, courID, newTchr);
            	}
            	else if(request.equals("addstutocour")) {
            		CourseSel api = new CourseSelSys();
            		//解析json字符串
                	String usrID = obj.getString("usrid");
                	String courID = obj.getString("courid");
                	String stuNum = obj.getString("stunum");
                	
                	response = api.addStuToCour(usrID, courID, stuNum);
            	}
            	else if(request.equals("delstufromcour")) {
            		CourseSel api = new CourseSelSys();
            		//解析json字符串
                	String usrID = obj.getString("usrid");
                	String courID = obj.getString("courid");
                	String stuNum = obj.getString("stunum");
                	
                	response = api.delStuFromCour(usrID, courID, stuNum);
            	}
            	else if(request.equals("queryallstoreitem")) {
            		Store api = new StoreSys();
            		//解析json字符串
            		String id = obj.getString("id");
            		
            		//返回结果
                	response = api.queryAllStoreItem(id);
            	}
            	else if(request.equals("queryitem")) {
            		Store api = new StoreSys();
            		//解析json字符串
            		String id = obj.getString("id");
            		String itemName = obj.getString("itemname");
            		
            		//返回结果
                	response = api.queryItem(id,itemName);
            	}
            	else if(request.equals("buyitem")) {
            		Store api = new StoreSys();
            		//解析json字符串
            		String id = obj.getString("id");
            		String itemID = obj.getString("itemid");
            		Short buyCnt =(short)obj.getInt("buycnt");
            		
            		//返回结果
                	response = api.buyItem(id,itemID,buyCnt);
            	}
            	else if(request.equals("updateitem")) {
            		Store api = new StoreSys();
            		//解析json字符串
            		String id = obj.getString("id");
            		String itemID = obj.getString("itemid");
            		String itemName = obj.getString("itemname");
            		Float itemPri = obj.getFloat("itempri");
            		Short itemCnt =(short)obj.getInt("itemcnt");
            		
            		//返回结果
                	response = api.updateItem(id,itemID,itemName,itemPri,itemCnt);
            	}
            	else if(request.equals("additem")) {
            		Store api = new StoreSys();
            		//解析json字符串
            		String id = obj.getString("id");
            		String itemID = obj.getString("itemid");
            		String itemName = obj.getString("itemname");
            		Float itemPri = obj.getFloat("itempri");
            		Short itemCnt =(short)obj.getInt("itemcnt");
            		
            		//返回结果
                	response = api.addItem(id,itemID,itemName,itemPri,itemCnt);
            	}
            	else if(request.equals("delitem")) {
            		Store api = new StoreSys();
            		//解析json字符串
            		String id = obj.getString("id");
            		String itemID = obj.getString("itemid");
            		
            		//返回结果
                	response = api.delItem(id,itemID);
            	}
            	else if(request.equals("recharge")) {
            		Store api = new StoreSys();
            		//解析json字符串
            		String id = obj.getString("id");
            		Double bal = obj.getDouble("bal");
            		
            		//返回结果
                	response = api.recharge(id,bal);
            	}
            	else if(request.equals("querybal")) {
            		Store api = new StoreSys();
            		//解析json字符串
            		String id = obj.getString("id");
            		
            		//返回结果
                	response = api.queryBal(id);
            	}
            	else if(request.equals("queryalldormitory")) {
            		DormitoryAPI api = new DormitorySys();
            		//解析json字符串
            		String id = obj.getString("id");
            		
            		//返回结果
                	response = api.queryAllDormitory(id);
            	}
            	else if(request.equals("stuquerydormitory")) {
            		DormitoryAPI api = new DormitorySys();
            		//解析json字符串
            		String id = obj.getString("id");
            		
            		//返回结果
                	response = api.stuQueryDormitory(id);
            	}
            	else if(request.equals("adquerydormitory")) {
            		DormitoryAPI api = new DormitorySys();
            		//解析json字符串
            		String id = obj.getString("id");
            		String domID = obj.getString("domid");
            		
            		//返回结果
                	response = api.adQueryDormitory(id,domID);
            	}
            	else if(request.equals("addmember")) {
            		DormitoryAPI api = new DormitorySys();
            		//解析json字符串
            		String id = obj.getString("id");
            		String addID = obj.getString("addid");
            		String domID = obj.getString("domid");
            		
            		//返回结果
                	response = api.addMember(id,addID,domID);
            	}
            	else if(request.equals("delmember")) {
            		DormitoryAPI api = new DormitorySys();
            		//解析json字符串
            		String id = obj.getString("id");
            		String delid = obj.getString("delid");
            		
            		//返回结果
                	response = api.delMember(id,delid);
            	}
            	else if(request.equals("queryallbook")) {
            		Library api = new LibrarySys();
            		//解析json字符串
            		String id = obj.getString("id");
            		
            		//返回结果
                	response = api.queryAllBook(id);
            	}
            	else if(request.equals("querybook")) {
            		Library api = new LibrarySys();
            		//解析json字符串
            		String id = obj.getString("id");
            		String bookName = obj.getString("bookname");
            		
            		//返回结果
                	response = api.queryBook(id,bookName);
            	}
            	else if(request.equals("borrowbook")) {
            		Library api = new LibrarySys();
            		//解析json字符串
            		String id = obj.getString("id");
            		String bookID = obj.getString("bookid");
            		Short borrowCnt = (short)obj.getInt("borrowcnt");
            		
            		//返回结果
                	response = api.borrowBook(id,bookID,borrowCnt);
            	}
            	else if(request.equals("returnbook")) {
            		Library api = new LibrarySys();
            		//解析json字符串
            		String id = obj.getString("id");
            		String bookID = obj.getString("bookid");
            		Short returnCnt = (short)obj.getInt("returncnt");
            		
            		//返回结果
                	response = api.returnBook(id,bookID,returnCnt);
            	}
            	else if(request.equals("addbook")) {
            		Library api = new LibrarySys();
            		//解析json字符串
            		String id = obj.getString("id");
            		String bookID = obj.getString("bookid");
            		String bookName = obj.getString("bookname");
            		Short bookCnt = (short)obj.getInt("bookcnt");
            		
            		//返回结果
                	response = api.addBook(id,bookID,bookName,bookCnt);
            	}
            	else if(request.equals("delbook")) {
            		Library api = new LibrarySys();
            		//解析json字符串
            		String id = obj.getString("id");
            		String bookID = obj.getString("bookid");
            		
            		//返回结果
                	response = api.delBook(id,bookID);
            	}
            	else if(request.equals("queryborrowedbook")) {
            		Library api = new LibrarySys();
            		//解析json字符串
            		String id = obj.getString("id");
            		
            		//返回结果
                	response = api.queryBorrowedBook(id);
            	}
            	else if(request.equals("updatebooknamecnt")) {
            		Library api = new LibrarySys();
            		//解析json字符串
            		String id = obj.getString("id");
            		String bookID = obj.getString("bookid");
            		String bookName = obj.getString("bookname");
            		Short bookCnt = (short)obj.getInt("bookcnt");
            		
            		//返回结果
                	response = api.updateBookNameCnt(id,bookID,bookName,bookCnt);
            	}
            	else if(request.equals("addbookholder")) {
            		Library api = new LibrarySys();
            		//解析json字符串
            		String id = obj.getString("id");
            		String bookID = obj.getString("bookid");
            		String addID = obj.getString("addid");
            		
            		//返回结果
                	response = api.addBookHolder(id,bookID,addID);
            	}
            	else if(request.equals("delbookholder")) {
            		Library api = new LibrarySys();
            		//解析json字符串
            		String id = obj.getString("id");
            		String bookID = obj.getString("bookid");
            		String delid = obj.getString("delid");
            		
            		//返回结果
                	response = api.delBookHolder(id,bookID,delid);
            	}
            	else {
            		response = null;
            	}
            }
            catch(JSONException e) {
            	e.printStackTrace();
        		response = null;
            }
        	
        	//数据返回给客户端
        	if(response!="doNotResponse") {
        		System.out.println("[Server]Response:"+response);
            
        		writer.println(response);
        	}
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
