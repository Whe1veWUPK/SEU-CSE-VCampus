package seu.cse.vcampus.vo;

/**
 * 类{@code Doctor}医生类，封装着医生信息
 *
 * @author Ruijin Zhang
 * @author Hanlong Liu
 *
 * @since 2023/8/30
 */
public class Doctor {
    String _drID;
    String _drName;
    String _drSpare;

    /**
     * Doctor类的构造方法
     * @param _drID
     * @param _drName
     * @param _drSpare
     */
    public Doctor(String _drID,String _drName,String _drSpare){
        set_drID(_drID);
        set_drName(_drName);
        set_drSpare(_drSpare);
    }

    /**
     * _drID的set方法
     * @param _drID
     */
    public void set_drID(String _drID) {
        this._drID = _drID;
    }


    /**
     * _drName的set方法
     * @param _drName
     */
    public void set_drName(String _drName) {
        this._drName = _drName;
    }

    /**
     * _drSpared的set方法
     * @param _drSpare
     */
    public void set_drSpare(String _drSpare) {
        this._drSpare = _drSpare;
    }

    /**
     * _drID的get方法
     * @return 返回String医生编号
     */
    public String get_drID() {
        return _drID;
    }

    /**
     * _drName的get方法
     * @return 返回String医生姓名
     */
    public String get_drName() {
        return _drName;
    }

    /**
     * _drSpace的get方法
     * @return 返回String空闲时间
     */
    public String get_drSpare() {
        return _drSpare;
    }
}
