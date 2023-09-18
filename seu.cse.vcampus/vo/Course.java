package seu.cse.vcampus.vo;

/**
 * 类 {@code Course}课程信息类，封装着课程的相关信息
 *
 * @author Ruijin Zhang
 * @author Hanlong Liu

 * @since 2023/8/30
 */
public class Course {
    /**
     * 下面是 Course 类的相关属性
     */
    String _courID;
    String _courName;
    String _courTchr;
    CourseTime _courTime;
    Short _courCap;
    String _courStuList;

    /**
     * Course 课程类的 构造方法
     *
     * @param id      传入的课程编号
     * @param name    传入的课程名称
     * @param tchr    传入的课程授课教师
     * @param time    传入的课程时间
     * @param cap     传入的课程容量
     * @param stuList 传入的选课名单
     */
    public Course(String id, String name, String tchr, CourseTime time, Short cap, String stuList) {
        set_courID(id);
        set_courCap(cap);
        set_courName(name);
        set_courStuList(stuList);
        set_courTchr(tchr);
        set_courTime(time);
    }

    /**
     * _courTime 的 get 方法
     *
     * @return 上课时间 _courTime
     */
    public CourseTime get_courTime() {
        return _courTime;
    }

    /**
     * _courCap 的 get 方法
     *
     * @return 课程容量 _courCap
     */
    public Short get_courCap() {
        return _courCap;
    }

    /**
     * _courID 的 get 方法
     *
     * @return 课程编号 _courID
     */
    public String get_courID() {
        return _courID;
    }

    /**
     * _courName 的 get 方法
     *
     * @return 课程名称 _courName
     */
    public String get_courName() {
        return _courName;
    }

    /**
     * _courTchr 的 get 方法
     *
     * @return 课程任课老师 _courTchr
     */
    public String get_courTchr() {
        return _courTchr;
    }

    /**
     * _courStuList 的 get 方法
     *
     * @return 课程选课名单 _courStuList
     */
    public String get_courStuList() {
        return _courStuList;
    }

    /**
     * _courCap 的 set 方法
     *
     * @param _courCap 待赋值的课程容量
     */
    public void set_courCap(Short _courCap) {
        this._courCap = _courCap;
    }

    /**
     * _courID 的 set 方法
     *
     * @param _courID 待赋值的课程编号
     */
    public void set_courID(String _courID) {
        this._courID = _courID;
    }

    /**
     * _courName 的 set 方法
     *
     * @param _courName 待赋值的课程名称
     */
    public void set_courName(String _courName) {
        this._courName = _courName;
    }

    /**
     * _courStuList 的 set 方法
     *
     * @param _courStuList 待赋值的课程选课名单
     */
    public void set_courStuList(String _courStuList) {
        this._courStuList = _courStuList;
    }

    /**
     * _courTchr 的 set 方法
     *
     * @param _courTchr 待赋值的任课老师
     */
    public void set_courTchr(String _courTchr) {
        this._courTchr = _courTchr;
    }

    /**
     * _courTime 的 set 方法
     *
     * @param _courTime 待赋值的上课时间
     */
    public void set_courTime(CourseTime _courTime) {
        this._courTime = _courTime;
    }
}