package server;

/**
 * 类 {@code LoginTimer}实现定时检测连接情况
 *
 * @author yfyou
 * @author wwb
 *
 * @since 2023/08/30
 */

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import api.Account;
import imp.AccountSys;

class LoginTimer {
    private static final int DELAY = 8000;//间隔时间
    public Boolean check=false;//登录状态检查
    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    /**
     * 运行定时检测
     * @param id	用户登录名
     */
    public void runTimer(String id) {
        System.out.println("User ID: "+id+" timer started.");
        executor.scheduleAtFixedRate(()->checkConnection(id), DELAY, DELAY, TimeUnit.MILLISECONDS);
    }

    /**
     * 连接检测
     * @param id	用户登录名
     */
    public void checkConnection(String id) {
        if(check) {//pass
            check = false;
            System.out.println("User ID: "+id+" responded.");
        }
        else {//终止连接
            Account a=new AccountSys();
            a.logOut(id);
            shutdownExec();
            System.out.println("User ID: "+id+" lost connection.");
        }
    }

    //关闭定时器
    public void shutdownExec() {
        if(executor!=null) {
            executor.shutdown();
        }
    }
}
