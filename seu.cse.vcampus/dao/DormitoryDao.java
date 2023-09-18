package dao;

import db.DbHelper;
import vo.Dormitory;

import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * 类{@code DormitoryDao}DormitoryDao，负责宿舍的增删改查
 *
 * @author Ruijin Zhang
 * @author Hanlong Liu
 *
 * @since 2023/9/3
 */
public class DormitoryDao {
    /**
     * 初始化 10个宿舍 的方法 id:01-10 cnt=0 Mem=""
     *  如果上述任意01-10 的宿舍已经存在 则不会重置 cnt 和 Mem
     *  该方法 理论上 有效 执行一次
     */
    public static void initialDormitory(){
        Byte cnt=0;
        addDormitory(new Dormitory("01", cnt,""));
        addDormitory(new Dormitory("02", cnt,""));
        addDormitory(new Dormitory("03", cnt,""));
        addDormitory(new Dormitory("04", cnt,""));
        addDormitory(new Dormitory("05", cnt,""));
        addDormitory(new Dormitory("06", cnt,""));
        addDormitory(new Dormitory("07", cnt,""));
        addDormitory(new Dormitory("08", cnt,""));
        addDormitory(new Dormitory("09", cnt,""));
        addDormitory(new Dormitory("10", cnt,""));
    }

    /**
     * 向数据库中 增加 宿舍的 方法
     * @param dormitory 待添加的宿舍信息
     * @return -1 数据已存在 0 添加成功 1 程序异常
     */
    public static Byte addDormitory(Dormitory dormitory){
        String dorID=dormitory.get_domID();
        if(queryDormitory(dorID)!=null){
            return -1;
        }
        Byte dorCnt=dormitory.get_domCnt();
        String dorMem=dormitory.get_domMem();
        String sqlString="insert into tblDormitory(tblId,tblCnt,tblMem) values('"+dorID+"','"+dorCnt+"','"+dorMem+"')";
        try{
            DbHelper.executeNonQuery(sqlString);
            return 0;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * 删除数据库中 宿舍信息的 方法
     * @param dorID 待删除的宿舍的宿舍号
     * @return -1 数据不存在 0 删除成功 1 程序异常
     */
    public static Byte delDormitory(String dorID){
        if(queryDormitory(dorID)==null){
            return -1;
        }
        String sqlString="delete * from tblDormitory where tblId='"+dorID+"'";
        try{
            DbHelper.executeNonQuery(sqlString);
            return 0;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * 更新数据库中 宿舍信息的 方法
     * @param dormitory 待更新的宿舍信息
     * @return -1 数据不存在 0 更新成功 1 程序异常
     */
    public static Byte updateDormitory(Dormitory dormitory){
        String dorID=dormitory.get_domID();
        if(queryDormitory(dorID)==null){
            return -1;
        }
        Byte dorCnt=dormitory.get_domCnt();
        String dorMem=dormitory.get_domMem();
        String sqlString="update tblDormitory set tblCnt='"+dorCnt+"',tblMem='"+dorMem+"' where tblId='"+dorID+"'";
        try{
            DbHelper.executeNonQuery(sqlString);
            return 0;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 1;

    }

    /**
     * 查询 数据库中 宿舍 信息的 方法
     * @param dorID 待查询宿舍的宿舍号
     * @return 查询到的宿舍信息 可能为 null(数据不存在)
     */
    public static Dormitory queryDormitory(String dorID){
        String sqlString="select*from tblDormitory";
        try{
            ResultSet rs=DbHelper.executeQuery(sqlString);
            while(rs.next()){
                String queryID=rs.getString("tblId");
                if(queryID.equals(dorID)){
                    Byte dorCnt=rs.getByte("tblCnt");
                    String dorMem=rs.getString("tblMem");
                    return new Dormitory(dorID,dorCnt,dorMem);
                }
            }
            return null;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 查询全部宿舍的信息
     * @return 查询到的全部宿舍的信息
     */
    public static ArrayList<Dormitory> queryAllDormitories(){
        String sqlString="select*from tblDormitory";
        ArrayList<Dormitory> dormitoryArrayList=new ArrayList<>();
        try{
            ResultSet rs=DbHelper.executeQuery(sqlString);
            while(rs.next()){
                String tblId=rs.getString("tblId");
                Dormitory dormitory=queryDormitory(tblId);
                if(dormitory!=null) {
                    dormitoryArrayList.add(dormitory);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        if(dormitoryArrayList.isEmpty()){
            return null;
        }
        return dormitoryArrayList;
    }

}
