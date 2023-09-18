package imp;

import server.Server;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class VerifyTimer {
    private static final int DELAY = 180000;//验证码有效时间
    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    /**
     * 验证码定时清除
     * @param id	用户登录名
     */
    public void verificationCountdown(String id) {
        // 从主进程的map中删除验证码使其失效
        Runnable task = new Runnable() {
            @Override
            public void run() {
                Server.verifyCodes.remove(id);
                System.out.println("[Server]Verification code for ID: "+id+" has been removed.");
                shutdownExec();
            }
        };

        executor.schedule(task, DELAY, TimeUnit.MILLISECONDS);
    }

    public void registerCountdown(String id) {
        // 从主进程的map中删除数据使其失效
        Runnable task = new Runnable() {
            @Override
            public void run() {
                Server.registerTempData.remove(id);
                System.out.println("[Server]Temp data for ID: "+id+" has been removed.");
                shutdownExec();
            }
        };

        executor.schedule(task, DELAY, TimeUnit.MILLISECONDS);
    }

    //关闭定时器
    public void shutdownExec() {
        if(executor!=null) {
            executor.shutdown();
        }
    }
}

