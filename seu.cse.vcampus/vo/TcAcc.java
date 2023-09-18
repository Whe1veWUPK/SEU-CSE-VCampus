package vo;
/**
 * 类 {@code TcAcc}教师账户信息类，封装着老师账户的相关信息
 *
 * @author Ruijin Zhang
 * @author Hanlong Liu

 * @since 2023/8/30
 */
public class TcAcc {
    /**
     * 下面是 TcAcc 的相关属性
     */
    String _tcAccID;
    String _tcAccCour;
    String _tcAccRes;
    String _tcAccBook;
    Double _tcAccBal;
    String _tcAccName;

    /**
     * TcAcc 的 构造方法
     * @param id 传入的老师账户的登录名
     * @param cour 传入的老师的教授课程
     * @param book 传入的老师的借书单
     * @param res  传入的老师的医院预约时间
     * @param bal  传入的老师的余额
     * @param name 传入的老师的名字
     */
    public TcAcc(String id,String cour,String book,String res,Double bal,String name){
        set_tcAccBal(bal);
        set_tcAccCour(cour);
        set_tcAccID(id);
        set_tcAccRes(res);
        set_tcAccBook(book);
        set_tcAccName(name);
    }

    /**
     * _tcAccID 的 get 方法
     * @return 老师账户的登录名 _tcAccID
     */
    public String get_tcAccID() {
        return _tcAccID;
    }

    /**
     * _tcAccBal 的 get 方法
     * @return 老师账户的余额 _tcAccBal
     */
    public Double get_tcAccBal() {
        return _tcAccBal;
    }

    /**
     * _tcAccCour 的 get 方法
     * @return 老师的教授课程 _tcAccCour
     */
    public String get_tcAccCour() {
        return _tcAccCour;
    }

    /**
     * _tcAccRes 的 get 方法
     * @return 老师的医院预约时间 _tcAccRes
     */
    public String get_tcAccRes() {
        return _tcAccRes;
    }

    /**
     * _tcAccBook 的 get 方法
     * @return 老师的借书单 _tcAccBook
     */
    public String get_tcAccBook(){
        return _tcAccBook;
    }

    /**
     * _tcAccName 的 get 方法
     * @return 老师的姓名 _tcAccName
     */
    public String get_tcAccName(){
        return _tcAccName;
    }

    /**
     * _tcAccBal 的 set 方法
     * @param _tcAccBal 待赋值的老师的账户余额
     */
    public void set_tcAccBal(Double _tcAccBal) {
        this._tcAccBal = _tcAccBal;
    }

    /**
     * _tcAccID 的 set 方法
     * @param _tcAccID 待赋值的老师的登录名
     */
    public void set_tcAccID(String _tcAccID) {
        this._tcAccID = _tcAccID;
    }

    /**
     * _tcAccCour 的 set 方法
     * @param _tcAccCour 待赋值的老师的教授课程
     */
    public void set_tcAccCour(String _tcAccCour) {
        this._tcAccCour = _tcAccCour;
    }

    /**
     * _tcAccRes 的 set 方法
     * @param _tcAccRes 待赋值的老师的医院预约时间
     */
    public void set_tcAccRes(String _tcAccRes) {
        this._tcAccRes = _tcAccRes;
    }

    /**
     * _tcAccBook 的 set 方法
     * @param _tcAccBook 待赋值的老师的借书单
     */
    public void set_tcAccBook(String _tcAccBook) {
        this._tcAccBook = _tcAccBook;
    }

    /**
     * _tcAccName 的 set 方法
     * @param _tcAccName 待赋值的老师的姓名
     */
    public void set_tcAccName(String _tcAccName){
        this._tcAccName=_tcAccName;
    }
}