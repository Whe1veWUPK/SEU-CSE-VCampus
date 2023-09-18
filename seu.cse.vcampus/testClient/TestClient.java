package testClient;

import java.io.*;
import java.net.*;

public class TestClient {
	
    public static void main(String[] args) {
        String serverAddress = "127.0.0.1"; // 服务端的 IP 地址
        int port = 11451; // 服务端监听的端口号
        try(BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in))){
            	
        	while(true) {
        		try(Socket socket = new Socket(serverAddress, port);
        			PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
        			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))){
        			
        			System.out.println("Connected to server");
            	
        			System.out.print("Data:");
        			String data = consoleReader.readLine();
                
        			// 发送数据到服务端
        			writer.println(data);

        			// 接收服务端的响应
        			String response = reader.readLine();
        			System.out.println("Server response: " + response);
            	
        			//Thread.sleep(1000); // 暂停一秒
        		}
        		catch(IOException e) {
        			e.printStackTrace();
        		}
        	}
        	
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

}

/* test data
 * 
 * login
 * AD
 * {"request":"register","id":"Amia","password":"yoimiyamylove","role":"AD","mail":"yuanshen@mhy.com"}
 * {"request":"login","id":"Amia","password":"yoimiyamylove","role":"AD"}
 * {"request":"logout","id":"Amia"}
 * {"request":"changepwd","id":"Amia","password":"yoimiyamylove","newpwd":"focalosmylove"}
 * {"request":"logincheck","id":"Amia"}
 * ST
 * {"request":"register","id":"123","password":"yoimiyamylove","role":"ST","mail":"yuan@mhy.com"}
 * {"request":"login","id":"123","password":"yoimiyamylove","role":"ST"}
 * {"request":"logout","id":"123"}
 * {"request":"register","id":"456","password":"yoimiyamylove","role":"ST","mail":"sy@mhy.com"}
 * TC
 * {"request":"register","id":"Yuki","password":"yoimiyamylove","role":"TC","mail":"shen@mhy.com"}
 * {"request":"login","id":"Yuki","password":"yoimiyamylove","role":"TC"}
 * {"request":"logout","id":"Yuki"}
 * {"request":"register","id":"K","password":"yoimiyamylove","role":"TC","mail":"ys@mhy.com"}
 * 
 * student status
 * {"request":"getstat","usrid":"123","stunum":"123"}
 * {"request":"setstat","usrid":"Amia","stunum":"123","stuname":"Shinonome Ena","stuage":"17","stugend":"false","stuid":"11451419198100430","stusch":"kamiyama high school","stumajor":"painting","stuaddr":"sekai","stuintake":"2020-09"}
 * {"request":"delstat","usrid":"Amia","stunum":"123"}
 * {"request":"addstat","usrid":"Amia","stunum":"123","stuname":"Shinonome Ena","stuage":"17","stugend":"false","stuid":"11451419198100430","stusch":"kamiyama high school","stumajor":"painting","stuaddr":"sekai","stuintake":"1919-08"}
 * {"request":"addstat","usrid":"Amia","stunum":"456","stuname":"Yoisaki Kanade","stuage":"17","stugend":"false","stuid":"11451419198100430","stusch":"kamiyama high school","stumajor":"painting","stuaddr":"sekai","stuintake":"1919-08"}
 * 
 * course selection
 * {"request":"stusel","usrid":"123","courid":"0"}
 * {"request":"stuquerysel","usrid":"123"}
 * {"request":"tcquerysel","usrid":"Yuki"}
 * {"request":"addcour","usrid":"Amia","courid":"0","courname":"test","courtchr":"Yuki","courtime":"0,1,13,0,15,0,","courcap":"1"}
 * {"request":"addcour","usrid":"Amia","courid":"1","courname":"test2","courtchr":"Yuki","courtime":"1,1,14,0,16,0,","courcap":"2"}
 * {"request":"addcour","usrid":"Amia","courid":"2","courname":"test3","courtchr":"Yuki","courtime":"2,1,15,0,17,0,","courcap":"2"}
 * {"request":"addcour","usrid":"Amia","courid":"3","courname":"test3","courtchr":"Yuki","courtime":"3,1,17,0,19,0,","courcap":"2"}
 * {"request":"delcour","usrid":"Amia","courid":"0"}
 * {"request":"updatecour","usrid":"Amia","courid":"1","courname":"test3","courtchr":"Yuki","courtime":"1,1,15,0,17,0,","courcap":"2","courstulist":""}
 * -{"request":"queryallcour","usrid":"Amia"}
 * -{"request":"queryallcour","usrid":"123"}
 * -{"request":"querycour","usrid":"Amia","courid":"1"}
 * -{"request":"querycour","usrid":"123","courid":"1"}
 * -{"request":"querycourbyname","usrid":"Amia","courname":"test2"}
 * -{"request":"querycourbyname","usrid":"123","courname":"test2"}
 * -{"request":"querycourbyname","usrid":"Amia","courname":"test3"}
 * {"request":"updatecourtchr","usrid":"Amia","courid":"1","newtchr":"K"}
 * {"request":"addstutocour","usrid":"Amia","courid":"1","stunum":"123"}
 * {"request":"delstufromcour","usrid":"Amia","courid":"1","stunum":"123"}
 * {"request":"delstufromcour","usrid":"123","courid":"1","stunum":"123"}
 * 
 * 
 * StuAcc/TcAcc学生课表格式：courID;courID2;
 * 返回前端的格式：courID;courName;courTchr;courTime;|
 * 
 * Course所有课程格式：courID;courName;courTchr;CourTime;cap;courStuList;|
 * 
 * courTime=ctid,day,startHour,startMin,endHour,endMin,
 * 
 * Course.courStuList选课名单格式：stuNum,stuNum2,
 * 返回前端的格式：stuID,stuName,;stuID2,stuName2,;
 * 
 */
