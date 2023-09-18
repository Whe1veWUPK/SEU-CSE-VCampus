package seu.cse.vcampus.client;

import org.json.JSONObject;

import java.io.*;
import java.net.Socket;

public class ClientSocket {
    private Socket socket;
    private JSONObject res = null;

    public ClientSocket() {
        socket = null;
    }

    public JSONObject connect(String host, int port, JSONObject json) throws IOException {
        socket = new Socket(host, port);
        while (true) {
            try(Socket socket = new Socket(host, port);
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))){

                System.out.println("Connected to server");

                writer.println(json);
                // 发送数据到服务端

                // 接收服务端的响应
                String response = reader.readLine();
                System.out.println("Server response: " + response);
                res = new JSONObject(response);

                Thread.sleep(1000); // 暂停一秒
                return res;
            }
            catch(IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

