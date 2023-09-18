package view;

/**
 * @author wwq
 * 定时器任务类，可每隔10秒向服务器发送一条指定消息，表示在线，
 * 服务器每30秒检查一次，若得不到消息，服务器可自行登出该账号
 */

import org.json.JSONObject;
import client.ClientSocketTimer;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MyTimerTask {
    private static final int DELAY = 4000;//间隔时间
    private String _clientID;
    private ClientSocketTimer clientSocketTimer = new ClientSocketTimer();
    private static final String HOST = "localhost";// 服务器地址配置
    private static final int PORT = 11451;// 服务器端口配置
    private JSONObject jsonObject = new JSONObject();
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public MyTimerTask(String id) {
        _clientID = id;
    }

    public void timeControler() {
        jsonObject.put("request", "logincheck");
        jsonObject.put("id", _clientID);

        Runnable task = () -> {
            try {
                clientSocketTimer.connect(HOST, PORT, jsonObject);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        scheduler.scheduleAtFixedRate(task, DELAY, DELAY, TimeUnit.MILLISECONDS);
    }

    //关闭定时器
    public void shutdownExec() {
        if(scheduler!=null) {
            scheduler.shutdown();
        }
    }

    public void start() {
        timeControler();
    }
}
