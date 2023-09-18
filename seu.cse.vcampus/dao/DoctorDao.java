package dao;

import db.DbHelper;
import vo.Doctor;

import java.util.ArrayList;
import java.sql.ResultSet;
/**
 * 类{@code DoctorDao}DoctorDao，负责医生信息的增删改查
 *
 * @author Ruijin Zhang
 * @author Hanlong Liu
 *
 * @since 2023/9/3
 */
public class DoctorDao {
    /**
     * 向数据库添加医生信息的方法
     * @param doctor 待添加的医生信息
     * @return -1 数据已存在 0 添加成功 1 程序异常
     */
    public static Byte addDoctor(Doctor doctor){
        String drID=doctor.get_drID();
        if(queryDoctor(drID)!=null){
            return -1;
        }
        String drName=doctor.get_drName();
        String drSpare= doctor.get_drSpare();
        String sqlString="insert into tblDoctor(dId,dName,dSpare) values('"+drID+"','"+drName+"','"+drSpare+"')";
        try{
            DbHelper.executeNonQuery(sqlString);
            return 0;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * 删除数据库中医生信息的方法
     * @param drID 待删除的医生的医生编号
     * @return -1 数据不存在 0 删除成功 1 程序异常
     */
    public static Byte delDoctor(String drID){
        if(queryDoctor(drID)==null){
            return -1;
        }
        String sqlString="delete * from tblDoctor where dId='"+drID+"'";
        try{
            DbHelper.executeNonQuery(sqlString);
            return 0;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * 更新数据库中医生信息的方法
     * @param doctor 待更新的医生信息
     * @return -1 数据不存在 0 更新成功 1 程序异常
     */
    public static Byte updateDoctor(Doctor doctor){
        String drID= doctor.get_drID();
        if(queryDoctor(drID)==null){
            return -1;
        }
        String drName=doctor.get_drName();
        String drSpare= doctor.get_drSpare();
        String sqlString="update tblDoctor set dSpare='"+drSpare+"',dName='"+drName+"' where dId='"+drID+"'";
        try{
            DbHelper.executeNonQuery(sqlString);
            return 0;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * 查询数据库中医生信息的方法
     * @param drID 传入的待查询医生的医生编号
     * @return 返回查询到的医生 可能为null(数据不存在)
     */
    public static Doctor queryDoctor(String drID){
        String sqlString="select*from tblDoctor";
        try{
            ResultSet rs= DbHelper.executeQuery(sqlString);
            while(rs.next()){
                String queryID=rs.getString("dId");
                if(queryID.equals(drID)){
                    String drName=rs.getString("dName");
                    String drSpare=rs.getString("dSpare");
                    return new Doctor(drID,drName,drSpare);

                }
            }
            return null;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
     /**
     * 查询全部的医生信息的 方法
     * @return 查询到的全部医生信息
     */
    public static ArrayList<Doctor> queryAllDoctors(){
        String sqlString="select*from tblDoctor";
        ArrayList<Doctor> doctorArrayList=new ArrayList<>();
        try{
            ResultSet rs=DbHelper.executeQuery(sqlString);
            while(rs.next()){
                String dId=rs.getString("dId");
                Doctor doctor=queryDoctor(dId);
                if(doctor!=null) {
                    doctorArrayList.add(doctor);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        if(doctorArrayList.isEmpty()){
            return null;
        }
        return doctorArrayList;

    }

}
