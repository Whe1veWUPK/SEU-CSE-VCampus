package dao;

import db.DbHelper;
import vo.TcAcc;

import java.sql.ResultSet;

/**
 * 类{@code TcAccDao}TcAccDao，负责老师账户信息的增删改查
 *
 * @author Ruijin Zhang
 * @author Hanlong Liu
 *
 * @since 2023/9/3
 */
public class TcAccDao {
    /**
     * 向数据库中增加老师账户信息的 方法
     * @param tcAcc 待添加的老师账户
     * @return -1 数据已经存在  0 添加成功  1 程序异常
     */
    public static Byte addTcAcc(TcAcc tcAcc){
        String taID=tcAcc.get_tcAccID();
        if(queryTcAcc(taID)!=null){
            return -1;
        }
        String taCour= tcAcc.get_tcAccCour();
        String taBook=tcAcc.get_tcAccBook();
        String taRes= tcAcc.get_tcAccRes();
        Double taBal= tcAcc.get_tcAccBal();
        String taName= tcAcc.get_tcAccName();
        String sqlString="insert into tblTcAcc(taId,taCour,taBook,taRes,taBal,taName) values('"+taID+"','"+taCour+"','"+taBook+"','"+taRes+"','"+taBal+"','"+taName+"')";
        try{
            DbHelper.executeNonQuery(sqlString);
            return 0;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * 删除数据库中老师账户信息的 方法
     * @param taID  传入的待删除的老师账户的登录名
     * @return -1 数据不存在 0 删除成功 1 程序异常
     */
    public static Byte delTcAcc(String taID){
        if(queryTcAcc(taID)==null){
            return -1;
        }
        String sqlString="delete * from tblTcAcc where taId='"+taID+"'";
        try{
            DbHelper.executeNonQuery(sqlString);
            return 0;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * 更新数据库中老师账户信息的方法
     * @param tcAcc 传入的待更新的老师账户信息
     * @return -1 数据不存在 0 更新成功 1 程序异常
     */
    public static Byte updateTcAcc(TcAcc tcAcc){
        String taID=tcAcc.get_tcAccID();
        if(queryTcAcc(taID)==null){
            return -1;
        }
        String taCour= tcAcc.get_tcAccCour();
        String taBook= tcAcc.get_tcAccBook();
        String taRes= tcAcc.get_tcAccRes();
        Double taBal= tcAcc.get_tcAccBal();
        String taName= tcAcc.get_tcAccName();
        String sqlString= "update tblTcAcc set taCour='"+taCour+"',taBook='"+taBook+"',taRes='"+taRes+"',taBal='"+taBal+"',taName='"+taName+"' where taId='"+taID+"'";
        try{
            DbHelper.executeNonQuery(sqlString);
            return 0;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * 查询数据库中老师账户信息的方法
     * @param taID 传入的待查询老师账户的登录名
     * @return 查询到的老师账户信息 可能为 null(不存在)
     */
    public static TcAcc queryTcAcc(String taID){
        String sqlString="select * from tblTcAcc";
        try{
            ResultSet rs=DbHelper.executeQuery(sqlString);
            while(rs.next()){
                String queryID=rs.getString("taId");
                if(queryID.equals(taID)){
                    String taCour=rs.getString("taCour");
                    String taRes=rs.getString("taRes");
                    String taBook=rs.getString("taBook");
                    Double taBal=rs.getDouble("taBal");
                    String taName=rs.getString("taName");
                    TcAcc tcAcc=new TcAcc(taID,taCour,taBook,taRes,taBal,taName);
                    return tcAcc;
                }
            }
            return null;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
