package client;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSocketTimer {
    public ClientSocketTimer() { }

    public void connect(String host, int port, JSONObject json) throws IOException {
        try(Socket socket = new Socket(host, port);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))){

            System.out.println("Connected to server");

            writer.println(json);
            // 发送数据到服务端

            // 不接收服务端的响应
        } catch(IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}
