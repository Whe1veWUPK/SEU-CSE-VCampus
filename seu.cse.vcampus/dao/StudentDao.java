package seu.cse.vcampus.dao;

import seu.cse.vcampus.db.DbHelper;
import seu.cse.vcampus.vo.*;

import java.sql.ResultSet;

/**
 * 类{@code StudentDao}StudentDao，负责学生信息的增删改查
 *
 * @author Ruijin Zhang
 * @author Hanlong Liu
 *
 * @since 2023/9/1
 */
public class StudentDao {
    /**
     * 向数据库中 添加 Student 的 方法
     * @param student 待添加的学生信息
     * @return -1 待添加的学生已经在数据库中存在 0 添加成功 1 程序异常（严重）
     */
    public static Byte addStudent(Student student){

        String stuNum= student.getStuNum();
        Student queryAns=queryStudent(stuNum);
        if(queryAns!=null){
            return -1;
        }
        String stuName=student.getStuName();
        String stuAge=student.getStuAge().toString();
        String stuGend=student.getStuGend().toString();
        String stuID=student.getStuID();
        String stuSch=student.getStuSch();
        String stuMajor=student.getStuMajor();
        String stuAddr=student.getStuAddr();
        String stuIntake=student.getStuIntake();
        String sqlString="insert into tblStudent(sNum,sName,sAge,sGend,sId,sSch,sMajor,sAddr,sIntake) values('"+stuNum+"','"+stuName+"','"+stuAge+"','"+stuGend+"','"+stuID+"','"+stuSch+"','"+stuMajor+"','"+stuAddr+"','"+stuIntake+"')";

        try {
            DbHelper.executeNonQuery(sqlString);
            return 0;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 1;

    }

    /**
     * 从数据库中 删除 Student 的 方法
     * @param stuNum 待删除的学生学号
     * @return -1 待删除的学生在数据库中不存在 0 删除成功 1 程序异常（严重）
     */
    public static Byte delStudent(String stuNum){



        Student queryAns=queryStudent(stuNum);

        if(queryAns==null){
            return -1;
        }
        String sqlString="delete * from tblStudent where sNum ='"+stuNum+"'";
        try {
            DbHelper.executeNonQuery(sqlString);
            return 0;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * 更新存储在数据库的 Student 的 信息
     * @param student 待更新的学生信息
     * @return -1 待更新的学生在数据库中不存在 0 更新成功 1 程序异常（严重）
     */
    public static Byte updateStudent(Student student){

        String num=student.getStuNum();
        if(queryStudent(num)==null){
            return -1;
        }
        String stuName=student.getStuName();
        String stuAge=student.getStuAge().toString();
        String stuGend=student.getStuGend().toString();
        String stuID=student.getStuID();
        String stuSch=student.getStuSch();
        String stuMajor=student.getStuMajor();
        String stuAddr=student.getStuAddr();
        String stuIntake=student.getStuIntake();
        String sqlString="UPDATE tblStudent SET sName='"+stuName+"',sAge='"+stuAge+"',sGend='"+stuGend+"',sId='"+stuID+"',sSch='"+stuSch+"',sMajor='"+stuMajor+"',sAddr='"+stuAddr+"',sIntake='"+stuIntake+"' WHERE sNum='"+num+"'";
        try{
            DbHelper.executeNonQuery(sqlString);
            return 0;
        }catch(Exception e){
            e.printStackTrace();
        }

        return 1;
    }

    /**
     * 向数据库中查询 学生 信息
     * @param stuNum 待查询的学生学号 具有唯一性
     * @return 查询到的学生 Student 不存在学生则返回 null
     */
    public static Student queryStudent(String stuNum){
        String sqlString="select * from tblStudent";
        try{
            ResultSet rs=DbHelper.executeQuery(sqlString);
            while(rs.next()){
               String num=rs.getString("sNum");
               if(num.equals(stuNum)) {
                   String name = rs.getString("sName");
                   Byte age = rs.getByte("sAge");
                   Boolean gend = rs.getBoolean("sGend");

                   String id = rs.getString("sId");
                   String major = rs.getString("sMajor");
                   String sch = rs.getString("sSch");
                   String addr = rs.getString("sAddr");
                   String intake = rs.getString("sIntake");
                   Student student = new Student(num, name, age, gend, id, sch, major, addr, intake);
                   return student;
               }
            }
            return null;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
