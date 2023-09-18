package vo;
/**
 * 类 {@code CourseTime}课程时间类，封装着课程时间的相关信息
 *
 * @author Ruijin Zhang
 * @author Hanlong Liu

 * @since 2023/8/30
 */
public class CourseTime {
    /**
     * 下面是 CourseTime 类的 相关属性
     */
    Byte _day;
    Byte _startHour;
    Byte _startMin;
    Byte _endHour;
    Byte _endMin;
    String _ctID;

    /**
     * CourseTime 类的 构造方法
     * @param day 周几
     * @param startHour 传入的课程的开始时间(小时)
     * @param startMin  传入的课程的开始时间(分钟)
     * @param endHour   传入的课程的结束时间(小时)
     * @param endMin    传入的课程的结束时间(分钟)
     */
    public CourseTime(Byte day,Byte startHour,Byte startMin,Byte endHour,Byte endMin,String ctID){
        set_day(day);
        set_startHour(startHour);
        set_startMin(startMin);
        set_endHour(endHour);
        set_endMin(endMin);
        set_ctID(ctID);
    }

    /**
     * _ctID 的 get 的方法
     * @return 唯一的课程查询id _ctID
     */
    public String get_ctID() {
        return _ctID;
    }

    /**
     * _day 的 get 方法
     * @return 周几 _day
     */
    public Byte get_day() {
        return _day;
    }

    /**
     * _endHour 的 get 方法
     * @return 课程的结束时间(小时) _endHour
     */
    public Byte get_endHour() {
        return _endHour;
    }

    /**
     * _endMin 的 get 方法
     * @return 课程的结束时间(分钟) _endMin
     */
    public Byte get_endMin() {
        return _endMin;
    }

    /**
     * _startHour 的 get 方法
     * @return 课程的开始时间(Hour) _startHour
     */
    public Byte get_startHour() {
        return _startHour;
    }

    /**
     * _startMin 的 get 方法
     * @return 课程的开始时间(Min) _startMin
     */
    public Byte get_startMin() {
        return _startMin;
    }

    /**
     * _day 的 set 方法
     * @param _day 待赋值的周几
     */
    public void set_day(Byte _day) {
        this._day = _day;
    }

    /**
     * _endHour 的 set 方法
     * @param _endHour 待赋值的结束时间(Hour)
     */
    public void set_endHour(Byte _endHour) {
        this._endHour = _endHour;
    }

    /**
     * _endMin  的 set 方法
     * @param _endMin 待赋值的结束时间(Min)
     */
    public void set_endMin(Byte _endMin) {
        this._endMin = _endMin;
    }

    /**
     * _startHour 的 set 方法
     * @param _startHour 待赋值的开始时间(Hour)
     */
    public void set_startHour(Byte _startHour) {
        this._startHour = _startHour;
    }

    /**
     * _startMin 的 set 方法
     * @param _startMin 待赋值的开始时间(Min)
     */
    public void set_startMin(Byte _startMin) {
        this._startMin = _startMin;
    }

    /**
     * _ctID 的 set 方法
     * @param _ctID 待赋值课程时间编号(具有唯一性)
     */
    public void set_ctID(String _ctID) {
        this._ctID = _ctID;
    }

}
