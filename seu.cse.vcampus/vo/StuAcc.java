package seu.cse.vcampus.vo;

/**
 * 类 {@code StuAcc}学生账户信息类，封装着学生账户的相关信息
 *
 * @author Ruijin Zhang
 * @author Hanlong Liu

 * @since 2023/8/30
 */
public class StuAcc {
    /**
     * 下面是 StuAcc 的 相关属性
     */
    String _stuAccID;
    String _stuAccCour;
    String _stuAccBook;
    String _stuAccDom;
    String _stuAccRes;
    double _stuAccBal;

    /**
     * StuAcc 的 构造方法
     * @param id  传入的登录名
     * @param cour 传入的课表
     * @param book 传入的借书单
     * @param dom  传入的宿舍
     * @param res  传入的医院预约时间
     * @param bal  传入的余额
     */
    public StuAcc(String id,String cour,String book,String dom,String res,double bal){
          set_stuAccBal(bal);
          set_stuAccBook(book);
          set_stuAccCour(cour);
          set_stuAccID(id);
          set_stuAccDom(dom);
          set_stuAccRes(res);

    }

    /**
     * _stuAccBook 的 get 方法
     * @return 学生的借书单 _stuAccBook
     */
    public String get_stuAccBook() {
        return _stuAccBook;
    }

    /**
     * _stuAccCour 的 get 方法
     * @return  学生的课表 _stuAccCour
     */
    public String get_stuAccCour() {
        return _stuAccCour;
    }

    /**
     * _stuAccDom 的 get 方法
     * @return  学生的宿舍 _stuAccDom
     */
    public String get_stuAccDom() {
        return _stuAccDom;
    }

    /**
     * _stuAccID 的 get 方法
     * @return 学生的登录名 _stuAccID
     */
    public String get_stuAccID() {
        return _stuAccID;
    }

    /**
     * _stuAccRes 的 get 方法
     * @return 学生的医院预约时间 _stuAccRes
     */
    public String get_stuAccRes() {
        return _stuAccRes;
    }

    /**
     * _stuAccBal 的 get 方法
     * @return 学生的账户余额 _stuAccBal
     */
    public double get_stuAccBal() {
        return _stuAccBal;
    }

    /**
     * _stuAccBook 的 set 方法
     * @param _stuAccBook 待赋值的借书表
     */
    public void set_stuAccBook(String _stuAccBook) {
        this._stuAccBook = _stuAccBook;
    }

    /**
     * _stuAccCour 的 set 方法
     * @param _stuAccCour 待赋值的课表
     */
    public void set_stuAccCour(String _stuAccCour) {
        this._stuAccCour = _stuAccCour;
    }

    /**
     * _stuAccDom 的 set 方法
     * @param _stuAccDom 待赋值的宿舍
     */
    public void set_stuAccDom(String _stuAccDom) {
        this._stuAccDom = _stuAccDom;
    }

    /**
     * _stuAccID 的 set 方法
     * @param _stuAccID 待赋值的登录名
     */
    public void set_stuAccID(String _stuAccID) {
        this._stuAccID = _stuAccID;
    }

    /**
     * _stuAccBal 的 set 方法
     * @param _stuAccBal 待赋值的学生账户余额
     */
    public void set_stuAccBal(double _stuAccBal) {
        this._stuAccBal = _stuAccBal;
    }

    /**
     * _stuAccRes 的 set 方法
     * @param _stuAccRes 待赋值的医院预约时间
     */
    public void set_stuAccRes(String _stuAccRes) {
        this._stuAccRes = _stuAccRes;
    }
}
