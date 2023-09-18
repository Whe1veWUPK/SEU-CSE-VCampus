package dao;

import db.DbHelper;
import vo.Course;
import vo.CourseTime;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * 类{@code CourseDao}CourseDao，负责课程信息的增删改查
 *
 * @author Ruijin Zhang
 * @author Hanlong Liu
 *
 * @since 2023/9/2
 */
public class CourseDao {
    /**
     * 向数据库中新增课程信息的方法
     * @param course 待增加的课程信息
     * @return -1 课程在数据库中已经存在 0 添加成功 1 程序异常（严重）
     */
    public static Byte addCourse(Course course){
        CourseTimeDao.addCourseTime(course.get_courTime());
        String cId= course.get_courID();
        if(queryCourse(cId)!=null){
            return -1;
        }
        String cName= course.get_courName();
        String cTchr= course.get_courTchr();
        Short  cCap= course.get_courCap();
        String ctID=course.get_courTime().get_ctID();
        String cStuList= course.get_courStuList();
        String sqlString="insert into tblCourse(cId,cName,cTchr,cTime,cCap,cStuList) values('"+cId+"','"+cName+"','"+cTchr+"','"+ctID+"','"+cCap+"','"+cStuList+"')";
        try{
            DbHelper.executeNonQuery(sqlString);

            return 0;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * 根据传入的 课程 id 删除数据库中的课程的 方法
     * @param courseID 传入的待删除的课程 id
     * @return -1 待删除的课程不存在于数据库中 0 删除成功 1 程序异常(严重)
     */
    public static Byte delCourse(String courseID){
        Course course=queryCourse(courseID);
        if(course==null){
            return -1;
        }

        String sqlString="delete * from tblCourse where cId='"+courseID+"'";
        String courseTimeID=course.get_courTime().get_ctID();

        try{
            CourseTimeDao.delCourseTime(courseTimeID);
            DbHelper.executeNonQuery(sqlString);

            return 0;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * 更新数据库中课程信息的方法
     * @param course 待更新的课程信息
     * @return -1 待更新的课程不存在 0 更新成功 1 程序异常（严重）
     */
    public static Byte updateCourse(Course course){
        String cId=course.get_courID();
        if(queryCourse(cId)==null){
            return -1;
        }
        String cName= course.get_courName();
        String cTch= course.get_courTchr();
        String cStuList= course.get_courStuList();
        Short cCap=course.get_courCap();
        CourseTime courseTime=course.get_courTime();
        String sqlString="UPDATE tblCourse SET cName='"+cName+"',cTchr='"+cTch+"',cCap='"+cCap+"',cStuList='"+cStuList+"' where cId='"+cId+"'";
        try{
            CourseTimeDao.updateCourseTime(courseTime);
            DbHelper.executeNonQuery(sqlString);
            return 0;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 1;

    }

    /**
     * 查询数据库中课程信息的方法
     * @param courseID 待查询课程的编号
     * @return 查询到的 课程 Course 对象 可能为 null(不存在)
     */
    public static Course queryCourse(String courseID){
        String sqlString="select * from tblCourse";
        try{
            ResultSet rs=DbHelper.executeQuery(sqlString);
            while(rs.next()){
                String queryID=rs.getString("cId");
                if(queryID.equals(courseID)){
                    String courName=rs.getString("cName");
                    String courTchr=rs.getString("cTchr");
                    String ctID=rs.getString("cTime");
                    CourseTime courseTime=CourseTimeDao.queryCourseTime(ctID);
                    if(courseTime==null){
                        return null;
                    }
                    Short courCap=rs.getShort("cCap");
                    String courStuList=rs.getString("cStuList");
                    Course course=new Course(courseID,courName,courTchr,courseTime,courCap,courStuList);
                    return course;
                }
            }
            return null;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 获得全部课程的 方法
     * @return 包含全部课程的列表
     */
    public static ArrayList<Course> queryAllCourses(){
        String sqlString="select * from tblCourse";
        ArrayList<Course> courseArrayList=new ArrayList<>();
        try{
            ResultSet rs=DbHelper.executeQuery(sqlString);
            while(rs.next()){
                String cId=rs.getString("cId");
                Course course=queryCourse(cId);
                if(course!=null) {
                    courseArrayList.add(course);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        if(courseArrayList.isEmpty()){
            return null;
        }
        return courseArrayList;
    }



    /**
     * queryCourseByName方法根据名称查询
     *
     * @param name
     * @return 查询成功返回包含所有查询名称Course的List，若查询不到返回null.
     */
    public static List<Course> queryCourseByName(String name){
        String sqlCourse="select * from tblCourse";
        List<Course> courses=new ArrayList<Course>();
        try {
            ResultSet resultSet=DbHelper.executeQuery(sqlCourse);
            while (resultSet.next()){
                String tmpCourseName=resultSet.getString("cName");
                if (tmpCourseName.equals(name)){
                    String cid=resultSet.getString("cId");
                    Course tmpCourse=queryCourse(cid);
                    courses.add(tmpCourse);
                }
            }
            return courses;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
