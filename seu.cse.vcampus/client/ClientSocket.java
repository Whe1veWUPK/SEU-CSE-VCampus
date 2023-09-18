package client;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;

public class ClientSocket {
    public ClientSocket() { }

    public JSONObject connect(String host, int port, JSONObject json) throws IOException {
        try(Socket socket = new Socket(host, port);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))){

            System.out.println("Connected to server");

            writer.println(json.toString());
            // 发送数据到服务端

            // 接收服务端的响应
            String response = reader.readLine();
            System.out.println("Server response: " + response);
            //System.out.println("222");
            JSONObject res = new JSONObject(response);


            //Thread.sleep(1000); // 暂停一秒
            return res;
        } catch(IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}

