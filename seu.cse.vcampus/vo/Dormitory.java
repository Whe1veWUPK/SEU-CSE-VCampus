package vo;
/**
 * 类 {@code Dormitory}宿舍类，封装着宿舍的相关信息
 *
 * @author Ruijin Zhang
 * @author Hanlong Liu

 * @since 2023/8/30
 */
public class Dormitory {
    /**
     * 下面是寝室的相关信息
     */
    String _domID;
    Byte _domCnt;
    String _domMem;

    /**
     * Dormitory 的 构造方法
     * @param id   传入的宿舍号
     * @param domCnt 传入的宿舍人数
     * @param domMem 传入的宿舍成员
     */
    public Dormitory(String id,Byte domCnt,String domMem){
        set_domCnt(domCnt);
        set_domID(id);
        set_domMem(domMem);
    }

    /**
     * _domCnt 的 get 方法
     * @return 宿舍的人数 _domCnt
     */
    public Byte get_domCnt() {
        return _domCnt;
    }

    /**
     * _domID 的 get 方法
     * @return 宿舍号 _domID
     */
    public String get_domID() {
        return _domID;
    }

    /**
     * _domMem 的 get 方法
     * @return 宿舍成员 _domMem
     */
    public String get_domMem() {
        return _domMem;
    }

    /**
     * _domCnt 的 set 方法
     * @param _domCnt 待赋值的宿舍居住人数
     */
    public void set_domCnt(Byte _domCnt) {
        this._domCnt = _domCnt;
    }

    /**
     * _domID 的 set 方法
     * @param _domID 待赋值的宿舍号
     */
    public void set_domID(String _domID) {
        this._domID = _domID;
    }

    /**
     * _domMem 的 set 方法
     * @param _domMem 待赋值的宿舍成员
     */
    public void set_domMem(String _domMem) {
        this._domMem = _domMem;
    }
}
