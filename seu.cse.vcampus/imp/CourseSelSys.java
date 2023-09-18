package imp;

import org.json.JSONObject;
import api.CourseSel;
import dao.*;
import vo.*;

import java.util.ArrayList;
import java.util.List;

public class CourseSelSys implements CourseSel {
    /**
     * 学生选课 调用权限：ST
     * @param usrID			用户登录名
     * @param courID		课程编号
     * @return				选课结果（json字符串）
     */
    public String stuSelectCourse(String usrID, String courID) {
        User usr=UserDao.queryUser(usrID);

        JSONObject jsonObject = new JSONObject();//创建json对象

        //检查登录情况和权限
        if(usr==null) {
            jsonObject.put("code","1");
            jsonObject.put("message", "用户不存在");
            return jsonObject.toString();
        }
        else if(!usr.getSta()) {
            jsonObject.put("code","-2");
            jsonObject.put("message", "登录异常");
            return jsonObject.toString();
        }
        else if(!usr.getRole().equals("ST")) {
            jsonObject.put("code","2");
            jsonObject.put("message", "权限不足");
            return jsonObject.toString();
        }

        //获取学籍信息
        Student stu = StudentDao.queryStudent(usrID);
        if(stu==null) {
            jsonObject.put("code","3");
            jsonObject.put("message", "学生不存在");
            return jsonObject.toString();
        }

        //获取学生课表
        StuAcc acc = StuAccDao.queryStuAcc(usr.getId());
        if(acc==null) {
            jsonObject.put("code","4");
            jsonObject.put("message", "账号信息异常，请重新登录");
            return jsonObject.toString();
        }

        //获取课程信息
        Course cour = CourseDao.queryCourse(courID);
        if(cour==null) {
            jsonObject.put("code","5");
            jsonObject.put("message", "课程不存在");
            return jsonObject.toString();
        }
        else if(impHelper.isSelected(acc, cour)) {
            jsonObject.put("code","6");
            jsonObject.put("message", "您已经选择过这门课");
            return jsonObject.toString();
        }
        else if(impHelper.getStuCnt(cour)>=cour.get_courCap()) {
            jsonObject.put("code","7");
            jsonObject.put("message", "选课人数已满");
            return jsonObject.toString();
        }
        else if(!impHelper.checkCourTime(acc,cour)) {
            jsonObject.put("code","8");
            jsonObject.put("message", "课程冲突");
            return jsonObject.toString();
        }
        else {
            //备份数据防止系统错误
            StuAcc backupAcc = new StuAcc(acc.get_stuAccID(),acc.get_stuAccCour(),acc.get_stuAccBook(),acc.get_stuAccDom(),acc.get_stuAccRes(),acc.get_stuAccBal());
            Course backupCour = new Course(cour.get_courID(),cour.get_courName(),cour.get_courTchr(),cour.get_courTime(),cour.get_courCap(),cour.get_courStuList());

            //向选课名单和学生课表添加课程
            acc.set_stuAccCour(acc.get_stuAccCour()+cour.get_courID()+';');
            cour.set_courStuList(cour.get_courStuList()+stu.getStuNum()+',');

            System.out.println(acc.get_stuAccCour());
            System.out.println(cour.get_courStuList());

            Byte res1 = StuAccDao.updateStuAcc(acc);
            Byte res2 = CourseDao.updateCourse(cour);
            if(res1==0&&res2==0) {
                jsonObject.put("code","0");
                jsonObject.put("message", "选课成功");
                return jsonObject.toString();
            }
            else {
                //rollback
                StuAccDao.updateStuAcc(backupAcc);
                CourseDao.updateCourse(backupCour);

                jsonObject.put("code","-1");
                jsonObject.put("message", "系统错误");
                return jsonObject.toString();
            }
        }
    }

    /**
     * 学生查询已选课程信息 调用权限：ST
     * @param usrID			用户登录名
     * @return				查询结果（json字符串）
     */
    public String stuQuerySelectedCourse(String usrID) {
        User usr=UserDao.queryUser(usrID);

        JSONObject jsonObject = new JSONObject();//创建json对象

        //检查登录情况和权限
        if(usr==null) {
            jsonObject.put("code","1");
            jsonObject.put("message", "用户不存在");
            return jsonObject.toString();
        }
        else if(!usr.getSta()) {
            jsonObject.put("code","-2");
            jsonObject.put("message", "登录异常");
            return jsonObject.toString();
        }
        else if(!usr.getRole().equals("ST")) {
            jsonObject.put("code","2");
            jsonObject.put("message", "权限不足");
            return jsonObject.toString();
        }

        //获取学生课表
        StuAcc acc = StuAccDao.queryStuAcc(usrID);
        if(acc==null) {
            jsonObject.put("code","4");
            jsonObject.put("message", "账号信息异常，请重新登录");
            return jsonObject.toString();
        }

        //获取课程名和教师信息
        String[] courIdList = acc.get_stuAccCour().split("\\;");
        String courList="";
        for(String courID:courIdList) {
            //查找课程
            Course cur = CourseDao.queryCourse(courID);
            if(cur!=null) {
                courList = courList + ';' + courID + ';' +
                        cur.get_courName() + ';' + cur.get_courTchr() + ';' +
                        impHelper.courseTimeToStr(cur.get_courTime()) + ";|";
            }
        }

        jsonObject.put("code","0");
        jsonObject.put("message", "查找成功");
        jsonObject.put("courselist", courList);
        return jsonObject.toString();
    }

    /**
     * 老师查询任教课程信息 调用权限：TC
     * @param usrID			用户登录名
     * @return				查询结果（json字符串）
     */
    public String tcQuerySelectedCourse(String usrID) {
        User usr=UserDao.queryUser(usrID);

        JSONObject jsonObject = new JSONObject();//创建json对象

        //检查登录情况和权限
        if(usr==null) {
            jsonObject.put("code","1");
            jsonObject.put("message", "用户不存在");
            return jsonObject.toString();
        }
        else if(!usr.getSta()) {
            jsonObject.put("code","-2");
            jsonObject.put("message", "登录异常");
            return jsonObject.toString();
        }
        else if(!usr.getRole().equals("TC")) {
            jsonObject.put("code","2");
            jsonObject.put("message", "权限不足");
            return jsonObject.toString();
        }

        //获取老师任课表
        TcAcc acc = TcAccDao.queryTcAcc(usrID);
        if(acc==null) {
            jsonObject.put("code","4");
            jsonObject.put("message", "账号信息异常，请重新登录");
            return jsonObject.toString();
        }
        String[] courIdList = acc.get_tcAccCour().split("\\;");

        String courList="";
        for(String courID:courIdList) {
            //查找课程
            Course cur = CourseDao.queryCourse(courID);
            if(cur!=null) {
                courList = courList + ';' + courID + ';' +
                        cur.get_courName() + ';' + impHelper.courseTimeToStr(cur.get_courTime()) + ';';
                String stuList = cur.get_courStuList();
                if(stuList.equals("")) {
                    courList += " ;|";
                }
                else {
                    courList += stuList + ";|";
                }
            }
        }

        jsonObject.put("code","0");
        jsonObject.put("message", "查找成功");
        jsonObject.put("courselist", courList);
        return jsonObject.toString();
    }

    /**
     * 添加课程 调用权限：AD
     * @param usrID			用户登录名
     * @param courID		课程编号
     * @param courName		课程名
     * @param courTchr		课程任课教师
     * @param courTime		课程时间
     * @param courCap		课程容量
     * @return				添加结果
     */
    public String adAddCourse(String usrID, String courID, String courName, String courTchr, String courTime, Short courCap) {
        User usr=UserDao.queryUser(usrID);

        JSONObject jsonObject = new JSONObject();//创建json对象

        //检查登录情况和权限
        if(usr==null) {
            jsonObject.put("code","1");
            jsonObject.put("message", "用户不存在");
            return jsonObject.toString();
        }
        else if(!usr.getSta()) {
            jsonObject.put("code","-2");
            jsonObject.put("message", "登录异常");
            return jsonObject.toString();
        }
        else if(!usr.getRole().equals("AD")) {
            jsonObject.put("code","2");
            jsonObject.put("message", "权限不足");
            return jsonObject.toString();
        }

        Course cour = new Course(courID,courName,courTchr,impHelper.strToCourseTime(courTime),courCap,"");

        //添加课程
        Byte res = CourseDao.addCourse(cour);
        if(res==-1) {
            jsonObject.put("code","3");
            jsonObject.put("message", "课程已存在");
        }
        else if(res==0) {
            //向老师的任课表中添加课程
            TcAcc acc = TcAccDao.queryTcAcc(courTchr);
            String tcAccCour = acc.get_tcAccCour();
            acc.set_tcAccCour(tcAccCour+courID+';');
            Byte res2 = TcAccDao.updateTcAcc(acc);
            if(res2==0) {
                jsonObject.put("code","0");
                jsonObject.put("message", "添加成功");
            }
            else {
                //rollback
                CourseDao.delCourse(courID);
                jsonObject.put("code","-1");
                jsonObject.put("message", "系统错误");
            }
        }
        else {
            jsonObject.put("code","-1");
            jsonObject.put("message", "系统错误");
        }
        return jsonObject.toString();
    }

    /**
     * 管理员删除课程信息 调用权限：AD
     * @param usrID			用户登录名
     * @param courID		课程编号
     * @return				删除结果（json字符串）
     */
    public String adDelCourse(String usrID, String courID) {
        User usr=UserDao.queryUser(usrID);

        JSONObject jsonObject = new JSONObject();//创建json对象

        //检查登录情况和权限
        if(usr==null) {
            jsonObject.put("code","1");
            jsonObject.put("message", "用户不存在");
            return jsonObject.toString();
        }
        else if(!usr.getSta()) {
            jsonObject.put("code","-2");
            jsonObject.put("message", "登录异常");
            return jsonObject.toString();
        }
        else if(!usr.getRole().equals("AD")) {
            jsonObject.put("code","2");
            jsonObject.put("message", "权限不足");
            return jsonObject.toString();
        }

        //获取课程
        Course cour = CourseDao.queryCourse(courID);
        if(cour==null) {
            jsonObject.put("code","3");
            jsonObject.put("message", "课程不存在");
            return jsonObject.toString();
        }

        //删除课程
        Byte res = CourseDao.delCourse(courID);
        if(res==0) {
            //删除学生账户中的相关信息
            String[] stuList = cour.get_courStuList().split("\\,");
            for(String id:stuList) {
                StuAcc tmp = StuAccDao.queryStuAcc(id);
                if(tmp!=null) {
                    impHelper.delStuAccCourseData(courID, tmp);
                    StuAccDao.updateStuAcc(tmp);
                }
            }
            //删除老师账户中的相关信息
            String tchrID = cour.get_courTchr();
            TcAcc tmp = TcAccDao.queryTcAcc(tchrID);
            if(tmp!=null) {
                impHelper.delTcAccCourseData(courID, tmp);
                TcAccDao.updateTcAcc(tmp);
            }

            jsonObject.put("code","0");
            jsonObject.put("message", "删除成功");
        }
        else {
            jsonObject.put("code","-1");
            jsonObject.put("message", "系统错误");
        }
        return jsonObject.toString();
    }

    /**
     * 修改课程信息（不包含任课老师、学生） 调用权限：AD
     * @param usrID			用户登录名
     * @param courID		课程编号
     * @param courName		课程名
     * @param courTchr		课程任课教师
     * @param courTime		课程时间
     * @param courCap		课容量
     * @param courStuList	选课名单
     * @return				修改结果（json字符串）
     */
    public String adUpdateCourse(String usrID, String courID, String courName, String courTchr, String courTime, Short courCap,
                                 String courStuList) {
        User usr=UserDao.queryUser(usrID);

        JSONObject jsonObject = new JSONObject();//创建json对象

        //检查登录情况和权限
        if(usr==null) {
            jsonObject.put("code","1");
            jsonObject.put("message", "用户不存在");
            return jsonObject.toString();
        }
        else if(!usr.getSta()) {
            jsonObject.put("code","-2");
            jsonObject.put("message", "登录异常");
            return jsonObject.toString();
        }
        else if(!usr.getRole().equals("AD")) {
            jsonObject.put("code","2");
            jsonObject.put("message", "权限不足");
            return jsonObject.toString();
        }

        Course update = new Course(courID,courName,courTchr,impHelper.strToCourseTime(courTime),courCap,courStuList);

        //修改课程
        Byte res = CourseDao.updateCourse(update);
        if(res==0) {
            jsonObject.put("code","0");
            jsonObject.put("message", "修改成功");
        }
        else {
            jsonObject.put("code","-1");
            jsonObject.put("message", "系统错误");
        }
        return jsonObject.toString();
    }

    /**
     * 查询所有课程信息 对管理员显示选课名单 调用权限：ST/TC/AD
     * @param usrID			用户登录名
     * @return				查询结果（json字符串）
     */
    public String queryAllCourse(String usrID) {
        User usr=UserDao.queryUser(usrID);

        JSONObject jsonObject = new JSONObject();//创建json对象

        //检查登录情况
        if(usr==null) {
            jsonObject.put("code","1");
            jsonObject.put("message", "用户不存在");
            return jsonObject.toString();
        }
        else if(!usr.getSta()) {
            jsonObject.put("code","-2");
            jsonObject.put("message", "登录异常");
            return jsonObject.toString();
        }

        //获取课程列表
        ArrayList<Course> courList = CourseDao.queryAllCourses();
        if(courList==null) {
            jsonObject.put("code","3");
            jsonObject.put("message", "没有课程信息");
            return jsonObject.toString();
        }

        //按照权限返回结果
        if(usr.getRole().equals("ST")||
                usr.getRole().equals("TC")) {
            jsonObject.put("code","0");
            jsonObject.put("message", "查找成功");
            jsonObject.put("data", impHelper.stuCourListToStr(courList));
        }
        else if(usr.getRole().equals("AD")) {
            jsonObject.put("code","0");
            jsonObject.put("message", "查找成功");
            jsonObject.put("data", impHelper.adCourListToStr(courList));
        }
        else {
            jsonObject.put("code","2");
            jsonObject.put("message", "权限不足");
        }
        return jsonObject.toString();
    }

    /**
     * 查询课程信息 对管理员显示选课名单 调用权限：ST/TC/AD
     * @param usrID
     * @param courID
     * @return
     */
    public String queryCourse(String usrID, String courID) {
        User usr=UserDao.queryUser(usrID);

        JSONObject jsonObject = new JSONObject();//创建json对象

        //检查登录情况和权限
        if(usr==null) {
            jsonObject.put("code","1");
            jsonObject.put("message", "用户不存在");
            return jsonObject.toString();
        }
        else if(!usr.getSta()) {
            jsonObject.put("code","-2");
            jsonObject.put("message", "登录异常");
            return jsonObject.toString();
        }

        Course cour = CourseDao.queryCourse(courID);

        if(cour==null) {
            jsonObject.put("code","3");
            jsonObject.put("message", "课程不存在");
            return jsonObject.toString();
        }

        //根据账户不同显示不同信息
        if(usr.getRole().equals("ST")||
                usr.getRole().equals("TC")) {//不显示学生名单
            jsonObject.put("code","0");
            jsonObject.put("message", "查找成功");
            jsonObject.put("courid", cour.get_courID());
            jsonObject.put("courname", cour.get_courName());
            jsonObject.put("courtchr", cour.get_courTchr());
            jsonObject.put("courtime", impHelper.courseTimeToStr(cour.get_courTime()));
            jsonObject.put("courcap", cour.get_courCap().toString());
        }
        else if(usr.getRole().equals("AD")) {//显示学生名单
            jsonObject.put("code","0");
            jsonObject.put("message", "查找成功");
            jsonObject.put("courid", cour.get_courID());
            jsonObject.put("courname", cour.get_courName());
            jsonObject.put("courtchr", cour.get_courTchr());
            jsonObject.put("courtime", impHelper.courseTimeToStr(cour.get_courTime()));
            jsonObject.put("courcap", cour.get_courCap().toString());
            //获取学生姓名
            String[] stuIdList = cour.get_courStuList().split("\\,");
            String stuList = "";
            for(String id:stuIdList) {
                Student stu = StudentDao.queryStudent(id);
                if(stu!=null) {
                    String stuName = stu.getStuName();
                    stuList = stuList + id + ',' + stuName + ",;";
                }
            }

            jsonObject.put("courstulist", stuList);
        }
        else {
            jsonObject.put("code","2");
            jsonObject.put("message", "权限不足");
        }
        return jsonObject.toString();
    }

    /**
     * 按名称查询课程信息 对管理员显示选课名单 调用权限：ST/TC/AD
     * @param usrID			用户登录名
     * @param courName		课程名
     * @return				查询结果（json字符串）
     */
    public String queryCourseByName(String usrID, String courName) {
        User usr=UserDao.queryUser(usrID);

        JSONObject jsonObject = new JSONObject();//创建json对象

        //检查登录情况和权限
        if(usr==null) {
            jsonObject.put("code","1");
            jsonObject.put("message", "用户不存在");
            return jsonObject.toString();
        }
        else if(!usr.getSta()) {
            jsonObject.put("code","-2");
            jsonObject.put("message", "登录异常");
            return jsonObject.toString();
        }

        //获取课程
        List<Course> courList = CourseDao.queryCourseByName(courName);

        if(courList==null) {
            jsonObject.put("code","3");
            jsonObject.put("message", "没有课程信息");
            return jsonObject.toString();
        }

        //按照权限返回结果
        if(usr.getRole().equals("ST")||
                usr.getRole().equals("TC")) {
            jsonObject.put("code","0");
            jsonObject.put("message", "查找成功");
            jsonObject.put("data", impHelper.stuCourListToStr(courList));
        }
        else if(usr.getRole().equals("AD")) {
            jsonObject.put("code","0");
            jsonObject.put("message", "查找成功");
            jsonObject.put("data", impHelper.adCourListToStr(courList));
        }
        else {
            jsonObject.put("code","2");
            jsonObject.put("message", "权限不足");
        }
        return jsonObject.toString();
    }

    /**
     * 修改任课老师 调用权限：AD
     * @param usrID			用户登录名
     * @param courID		课程ID
     * @param newTchr		新的任课老师
     * @return				修改结果（json字符串）
     */
    public String updateCourTchr(String usrID, String courID, String newTchr) {
        User usr=UserDao.queryUser(usrID);

        JSONObject jsonObject = new JSONObject();//创建json对象

        //检查登录情况和权限
        if(usr==null) {
            jsonObject.put("code","1");
            jsonObject.put("message", "用户不存在");
            return jsonObject.toString();
        }
        else if(!usr.getSta()) {
            jsonObject.put("code","-2");
            jsonObject.put("message", "登录异常");
            return jsonObject.toString();
        }
        else if(!usr.getRole().equals("AD")) {
            jsonObject.put("code","2");
            jsonObject.put("message", "权限不足");
            return jsonObject.toString();
        }

        //获取课程信息
        Course cour = CourseDao.queryCourse(courID);
        if(cour==null) {
            jsonObject.put("code","3");
            jsonObject.put("message", "课程不存在");
            return jsonObject.toString();
        }

        //获取老师信息
        TcAcc newAcc = TcAccDao.queryTcAcc(newTchr);
        TcAcc oldAcc = TcAccDao.queryTcAcc(cour.get_courTchr());
        if(newAcc==null) {
            jsonObject.put("code","4");
            jsonObject.put("message", "老师不存在");
            return jsonObject.toString();
        }

        //backup
        String oldCourTchr = cour.get_courTchr();
        String oldAccCour = oldAcc.get_tcAccCour();
        String newAccCour = newAcc.get_tcAccCour();

        //更新任课老师
        cour.set_courTchr(newTchr);
        impHelper.delTcAccCourseData(courID, oldAcc);
        newAcc.set_tcAccCour(newAccCour+courID+';');
        Byte res1 = CourseDao.updateCourse(cour);
        Byte res2 = TcAccDao.updateTcAcc(oldAcc);
        Byte res3 = TcAccDao.updateTcAcc(newAcc);
        if(res1==0&&res2==0&&res3==0) {
            jsonObject.put("code","0");
            jsonObject.put("message", "修改成功");
        }
        else {
            //rollback
            cour.set_courTchr(oldCourTchr);
            oldAcc.set_tcAccCour(oldAccCour);
            newAcc.set_tcAccCour(newAccCour);
            CourseDao.updateCourse(cour);
            TcAccDao.updateTcAcc(oldAcc);
            TcAccDao.updateTcAcc(newAcc);

            jsonObject.put("code","-1");
            jsonObject.put("message", "系统错误");
        }
        return jsonObject.toString();
    }

    /**
     * 手动添加选课学生 调用权限：AD
     * @param usrID			用户登录名
     * @param courID		课程ID
     * @param stuNum		学生学号
     * @return				添加结果（json字符串）
     */
    public String addStuToCour(String usrID, String courID, String stuNum) {
        User usr=UserDao.queryUser(usrID);

        JSONObject jsonObject = new JSONObject();//创建json对象

        //检查登录情况和权限
        if(usr==null) {
            jsonObject.put("code","1");
            jsonObject.put("message", "用户不存在");
            return jsonObject.toString();
        }
        else if(!usr.getSta()) {
            jsonObject.put("code","-2");
            jsonObject.put("message", "登录异常");
            return jsonObject.toString();
        }
        else if(!usr.getRole().equals("AD")) {
            jsonObject.put("code","2");
            jsonObject.put("message", "权限不足");
            return jsonObject.toString();
        }

        //获取课程信息
        Course cour = CourseDao.queryCourse(courID);
        if(cour==null) {
            jsonObject.put("code","3");
            jsonObject.put("message", "课程不存在");
            return jsonObject.toString();
        }

        //获取学生信息
        StuAcc acc = StuAccDao.queryStuAcc(stuNum);
        if(acc==null) {
            jsonObject.put("code","4");
            jsonObject.put("message", "学生不存在");
            return jsonObject.toString();
        }
        else if(impHelper.isSelected(acc, cour)) {
            jsonObject.put("code","5");
            jsonObject.put("message", "该学生已选过这门课程");
            return jsonObject.toString();
        }

        //向选课名单添加学生
        String oldStuList = cour.get_courStuList();
        cour.set_courStuList(oldStuList+stuNum+',');
        Byte res1 = CourseDao.updateCourse(cour);
        //向学生课表中添加课程
        String oldStuAccCour = acc.get_stuAccCour();
        acc.set_stuAccCour(oldStuAccCour+courID+';');
        Byte res2 = StuAccDao.updateStuAcc(acc);

        if(res1==0&&res2==0) {
            jsonObject.put("code","0");
            jsonObject.put("message", "添加成功");
            return jsonObject.toString();
        }
        else {
            //rollback
            cour.set_courStuList(oldStuList);
            acc.set_stuAccCour(oldStuAccCour);
            CourseDao.updateCourse(cour);
            StuAccDao.updateStuAcc(acc);

            jsonObject.put("code","-1");
            jsonObject.put("message", "系统错误");
            return jsonObject.toString();
        }
    }

    /**
     * 学生自主退课/删除学生选课 调用权限：ST/AD
     * @param usrID			用户登录名
     * @param courID		课程ID
     * @param stuNum		学生学号
     * @return				删除结果（json字符串）
     */
    public String delStuFromCour(String usrID, String courID, String stuNum) {
        User usr=UserDao.queryUser(usrID);

        JSONObject jsonObject = new JSONObject();//创建json对象

        //检查登录情况和权限
        if(usr==null) {
            jsonObject.put("code","1");
            jsonObject.put("message", "用户不存在");
            return jsonObject.toString();
        }
        else if(!usr.getSta()) {
            jsonObject.put("code","-2");
            jsonObject.put("message", "登录异常");
            return jsonObject.toString();
        }
        else if(!usr.getRole().equals("AD")&&!usr.getRole().equals("ST")) {
            jsonObject.put("code","2");
            jsonObject.put("message", "权限不足");
            return jsonObject.toString();
        }

        //获取课程信息
        Course cour = CourseDao.queryCourse(courID);
        if(cour==null) {
            jsonObject.put("code","3");
            jsonObject.put("message", "课程不存在");
            return jsonObject.toString();
        }

        //获取学生信息
        StuAcc acc = StuAccDao.queryStuAcc(stuNum);
        if(acc==null) {
            jsonObject.put("code","4");
            jsonObject.put("message", "学生不存在");
            return jsonObject.toString();
        }
        else if(!impHelper.isSelected(acc, cour)) {
            jsonObject.put("code","5");
            jsonObject.put("message", "学生未选择这门课程");
            return jsonObject.toString();
        }

        //backup
        String oldCourStuList = cour.get_courStuList();
        String oldStuAccCour = acc.get_stuAccCour();

        //从选课名单删除学生
        impHelper.delStuFromCour(cour, stuNum);
        Byte res1 = CourseDao.updateCourse(cour);
        //从学生课表删除课程
        impHelper.delStuAccCourseData(courID, acc);
        Byte res2 = StuAccDao.updateStuAcc(acc);

        if(res1==0&&res2==0) {
            jsonObject.put("code","0");
            jsonObject.put("message", "退选成功");
            return jsonObject.toString();
        }
        else {
            //rollback
            cour.set_courStuList(oldCourStuList);
            acc.set_stuAccCour(oldStuAccCour);
            CourseDao.updateCourse(cour);
            StuAccDao.updateStuAcc(acc);

            jsonObject.put("code","-1");
            jsonObject.put("message", "系统错误");
            return jsonObject.toString();
        }
    }


}
