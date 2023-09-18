package seu.cse.vcampus.dao;

import seu.cse.vcampus.db.DbHelper;
import seu.cse.vcampus.vo.StuAcc;


import java.sql.ResultSet;

/**
 * 类{@code StuAccDao}StuAccDao，负责学生账户信息的增删改查
 *
 * @author Ruijin Zhang
 * @author Hanlong Liu
 *
 * @since 2023/9/2
 */
public class StuAccDao {
    /**
     * 向数据库中增加学生账户的 方法
     * @param stuAcc 传入的待添加的学生账户信息
     * @return -1 数据已存在 0 添加成功 1 程序异常
     */
    public static Byte addStuAcc(StuAcc stuAcc){
        String saID= stuAcc.get_stuAccID();
        if(queryStuAcc(saID)!=null){
            return -1;
        }
        String saCour=stuAcc.get_stuAccCour();
        String saBook=stuAcc.get_stuAccBook();
        String saDom=stuAcc.get_stuAccDom();
        String saRes=stuAcc.get_stuAccRes();
        double saBal=stuAcc.get_stuAccBal();
        String sqlString="insert into tblStuAcc(saId,saCour,saBook,saDom,saRes,saBal) values('"+saID+"','"+saCour+"','"+saBook+"','"+saDom+"','"+saRes+"','"+saBal+"')";
        try{
            DbHelper.executeNonQuery(sqlString);
            return 0;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * 向数据库中删除学生账户的 方法
     * @param saID 传入的待删除的学生账户登录名
     * @return -1 数据不存在 0 删除成功 1 程序异常
     */
    public static Byte delStuAcc(String saID){
        if(queryStuAcc(saID)==null){
            return -1;
        }
        String sqlString="delete * from tblStuAcc where saId='"+saID+"'";
        try{
            DbHelper.executeNonQuery(sqlString);
            return 0;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * 更新数据库中学生账户信息的方法
     * @param stuAcc 传入的待更新的学生账户
     * @return -1 学生账户不存在 0 更新成功 1 程序异常
     */
    public static Byte updateStuAcc(StuAcc stuAcc){
        String saID=stuAcc.get_stuAccID();
        if(queryStuAcc(saID)==null){
            return -1;
        }
        String saCour=stuAcc.get_stuAccCour();
        String saBook=stuAcc.get_stuAccBook();
        String saDom=stuAcc.get_stuAccDom();
        String saRes=stuAcc.get_stuAccRes();
        double saBal=stuAcc.get_stuAccBal();
        String sqlString="update tblStuAcc set saBook='"+saBook+"',saCour='"+saCour+"',saDom='"+saDom+"',saRes='"+saRes+"',saBal='"+saBal+"' where saId='"+saID+"'";
        try{
            DbHelper.executeNonQuery(sqlString);
            return 0;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * 访问数据库中学生账户的 方法
     * @param stuAccID 传入的待访问的学生账户登录名
     * @return 查询到的学生账户 可能为 null(不存在)
     */
    public static StuAcc queryStuAcc(String stuAccID){
       String sqlString="select * from tblStuAcc";
       try{
           ResultSet rs=DbHelper.executeQuery(sqlString);
           while(rs.next()){
               String curID=rs.getString("saId");
               if(curID.equals(stuAccID)){
                   String stuAccCour=rs.getString("saCour");
                   String stuAccBook=rs.getString("saBook");
                   String stuAccDom=rs.getString("saDom");
                   String stuAccRes=rs.getString("saRes");
                   double stuAccBal=rs.getDouble("saBal");
                   StuAcc stuAcc=new StuAcc(stuAccID,stuAccCour,stuAccBook,stuAccDom,stuAccRes,stuAccBal);
                   return stuAcc;
               }
           }
           return null;
       }catch(Exception e){
           e.printStackTrace();
       }
       return null;
    }

}
