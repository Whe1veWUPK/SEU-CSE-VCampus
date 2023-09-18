package api;

import vo.Student;

/**
 * 接口 {@code StuStat}学籍管理接口，提供学籍管理系统的方法名
 *
 * @author yfyou
 * @author wwb
 *
 * @since 2023/08/30
 */

public interface StuStat {

    /**
     * 调用权限：ST/AD
     * @param usrID		用户登录名
     * @param stuNum	查找学生的学号
     * @return			查找结果、学籍信息（json字符串）
     */
    public String getStat(String usrID, String stuNum);

    /**
     * 调用权限：AD
     * @param usrID		用户登录名
     * @param stu		新的学生信息
     * @return			修改结果（json字符串）
     */
    public String setStat(String usrID, Student stu);

    /**
     * 调用权限：AD
     * @param usrID		用户登录名
     * @param stuNum	需要删除的学生学号
     * @return			删除结果（json字符串）
     */
    public String delStat(String usrID, String stuNum);

    /**
     * 调用权限：AD
     * @param usrID		用户登录名
     * @param stu		新的学生信息
     * @return			添加结果（json字符串）
     */
    public String addStat(String usrID, Student stu);

}
