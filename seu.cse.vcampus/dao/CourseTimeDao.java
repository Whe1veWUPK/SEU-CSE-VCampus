package dao;

import db.DbHelper;
import vo.CourseTime;

import java.sql.ResultSet;

/**
 * 类{@code CourseTimeDao}CourseTimeDao，负责课程时间类的增删改查
 *
 * @author Ruijin Zhang
 * @author Hanlong Liu
 *
 * @since 2023/9/2
 */
public class CourseTimeDao {
    /**
     * 向数据库中增加课程时间的方法
     * @param courseTime 待增加的封装的课程时间信息
     * @return -1 待插入的课程时间已经存在于数据库中 0 插入成功 1 程序严重异常
     */
    public static Byte addCourseTime(CourseTime courseTime){
        String courseID=courseTime.get_ctID();
        if(queryCourseTime(courseID)!=null){
            return -1;
        }
        String startHour=courseTime.get_startHour().toString();
        String startMin=courseTime.get_startMin().toString();
        String endHour=courseTime.get_endHour().toString();
        String endMin=courseTime.get_endMin().toString();
        String day=courseTime.get_day().toString();
        String sqlString="insert into tblCourseTime(ctDay,ctStartHour,ctStartMin,ctEndHour,ctEndMin,ctID) values('"+day+"','"+startHour+"','"+startMin+"','"+endHour+"','"+endMin+"','"+courseID+"')";

        try{
            DbHelper.executeNonQuery(sqlString);
            return 0;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * 在数据库中删除给定课程时间信息的方法
     * @param courseID 待删除的课程ID
     * @return -1 待删除的课程不存在于数据库中 0 删除成功 1 程序严重异常
     */
    public static Byte delCourseTime(String courseID){

        if(queryCourseTime(courseID)==null){
            return -1;
        }
        String sqlString="delete * from tblCourseTime where ctID ='"+courseID+"'";
        try{
            DbHelper.executeNonQuery(sqlString);
            return 0;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 1;

    }

    /**
     * 向数据库中更新存放的课程信息的方法
     * @param courseTime 待更新的封装好的课程时间信息
     * @return -1 待更新的课程时间并没有存放在数据库中 0 更新成功 1 程序严重异常
     */
    public static Byte updateCourseTime(CourseTime courseTime){
        String courseID=courseTime.get_ctID();
        if(queryCourseTime(courseID)==null){
            return -1;
        }
        String day=courseTime.get_day().toString();
        String startHour=courseTime.get_startHour().toString();
        String startMin=courseTime.get_startMin().toString();
        String endHour=courseTime.get_endHour().toString();
        String endMin=courseTime.get_endMin().toString();
        String sqlString="UPDATE tblCourseTime SET ctDay= '"+day+"',ctStartHour= '"+startHour+"',ctStartMin='"+startMin+"',ctEndHour='"+endHour+"',ctEndMin='"+endMin+"' WHERE ctID='"+courseID+"'";
        try{
            DbHelper.executeNonQuery(sqlString);
            return 0;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * 根据输入的课程时间编号向数据库中进行查询的方法
     * @param ctID 传入的待查询的课程id
     * @return 返回查询到的课程时间信息 可能为 null(此时课程时间并未存放于数据库中)
     */
    public static CourseTime queryCourseTime(String ctID){
        String sqlString="select * from tblCourseTime";
        try{
            ResultSet rs= DbHelper.executeQuery(sqlString);
            while(rs.next()){
                String queryID=rs.getString("ctID");
                if(queryID.equals(ctID)){
                    Byte ctDay=rs.getByte("ctDay");
                    Byte ctStartHour=rs.getByte("ctStartHour");
                    Byte ctStartMin=rs.getByte("ctStartMin");
                    Byte ctEndHour=rs.getByte("ctEndHour");
                    Byte ctEndMin=rs.getByte("ctEndMin");
                    CourseTime queryAns=new CourseTime(ctDay,ctStartHour,ctStartMin,ctEndHour,ctEndMin,queryID);
                    return queryAns;
                }
            }
            return null;

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}

