package api;

/**
 * 接口 {@code CourseSel}课程管理接口，提供课程管理的方法名
 *
 * @author yfyou
 * @author wwb
 *
 * @since 2023/08/21
 */

public interface CourseSel {
    /**
     * 学生选课 调用权限：ST
     * @param usrID			用户登录名
     * @param courID		课程编号
     * @return				选课结果（json字符串）
     */
    public String stuSelectCourse(String usrID, String courID);

    /**
     * 学生查询已选课程信息 调用权限：ST
     * @param usrID			用户登录名
     * @return				查询结果（json字符串）
     */
    public String stuQuerySelectedCourse(String usrID);

    /**
     * 老师查询任教课程信息 调用权限：TC
     * @param usrID			用户登录名
     * @return				查询结果（json字符串）
     */
    public String tcQuerySelectedCourse(String usrID);

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
    public String adAddCourse(String usrID, String courID, String courName, String courTchr, String courTime, Short courCap);

    /**
     * 管理员删除课程信息 调用权限：AD
     * @param usrID			用户登录名
     * @param courID		课程编号
     * @return				删除结果（json字符串）
     */
    public String adDelCourse(String usrID, String courID);

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
                                 String courStuList);

    /**
     * 查询所有课程信息 对管理员显示选课名单 调用权限：ST/TC/AD
     * @param usrID			用户登录名
     * @return				查询结果（json字符串）
     */
    public String queryAllCourse(String usrID);

    /**
     * 查询课程信息 对管理员显示选课名单 调用权限：ST/TC/AD
     * @param usrID			用户登录名
     * @param courID		课程ID
     * @return				查询结果（json字符串）
     */
    public String queryCourse(String usrID, String courID);

    /**
     * 按名称查询课程信息 对管理员显示选课名单 调用权限：ST/TC/AD
     * @param usrID			用户登录名
     * @param courName		课程名
     * @return				查询结果（json字符串）
     */
    public String queryCourseByName(String usrID, String courName);

    /**
     * 修改任课老师 调用权限：AD
     * @param usrID			用户登录名
     * @param courID		课程ID
     * @param newTchr		新的任课老师
     * @return				修改结果（json字符串）
     */
    public String updateCourTchr(String usrID, String courID, String newTchr);

    /**
     * 手动添加选课学生 调用权限：AD
     * @param usrID			用户登录名
     * @param courID		课程ID
     * @param stuNum		学生学号
     * @return				添加结果（json字符串）
     */
    public String addStuToCour(String usrID, String courID, String stuNum);

    /**
     * 学生自主退课/删除学生选课 调用权限：ST/AD
     * @param usrID			用户登录名
     * @param courID		课程ID
     * @param stuNum		学生学号
     * @return				删除结果（json字符串）
     */
    public String delStuFromCour(String usrID, String courID, String stuNum);

}