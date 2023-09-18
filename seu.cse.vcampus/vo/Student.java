package vo;


/**
 * 类 {@code Student}学生信息类，封装着学生的相关信息
 *
 * @author Ruijin Zhang
 * @author Hanlong Liu

 * @since 2023/8/30
 */
public class Student {
    /**
     * 下面是Student学生类的相关属性
     */
    String _stuNum;
    String _stuName;
    Byte _stuAge;
    Boolean _stuGend;
    String _stuID;
    String _stuSch;
    String _stuMajor;
    String _stuAddr;
    String _stuIntake;

    /**
     * 学生类的构造方法
     * @param num 传入的学号
     * @param name 传入的学生姓名
     * @param age  传入的学生年龄
     * @param gend 传入的学生性别
     * @param id   传入的学生的身份证号
     * @param sch  传入的学生的学校
     * @param major 传入的学生的专业
     * @param addr  传入的学生的家庭住址
     * @param intake 传入的学生的入学时间
     */
    public Student(String num,String name,Byte age,Boolean gend,String id,String sch,String major,String addr,String intake){
        setStuNum(num);
        setStuName(name);
        setStuAge(age);
        setStuGend(gend);
        setStuID(id);
        setStuSch(sch);
        setStuMajor(major);
        setStuAddr(addr);
        setStuIntake(intake);
    }

    /**
     * _stuNum 的 get 方法 返回 _stuNum
     * @return 学生的学号 _stuNum
     */
    public String getStuNum(){
        return this._stuNum;
    }

    /**
     * _stuName 的 get 方法 返回 _stuName
     * @return 学生的姓名 _stuName
     */
    public String getStuName(){
        return this._stuName;
    }

    /**
     * _stuAge 的 get 方法 返回 _stuAge
     * @return 学生的年龄 _stuAge
     */
    public Byte getStuAge(){
        return this._stuAge;
    }

    /**
     * _stuGend 的 get 方法 返回 _stuGend
     * @return 学生的性别 _stuGend
     */
    public Boolean getStuGend(){
        return this._stuGend;
    }

    /**
     * _stuID 的 get 方法 返回 _stuID
     * @return 学生的身份证号 _stuID
     */
    public String getStuID(){
        return this._stuID;
    }

    /**
     * _stuSch 的 get 方法 返回 _stuSch
     * @return 学生的学校 _stuSch
     */
    public String getStuSch(){
        return this._stuSch;
    }

    /**
     * _stuMajor 的 get 方法 返回 _stuMajor
     * @return 学生的专业 _stuMajor
     */
    public String getStuMajor(){
        return this._stuMajor;
    }

    /**
     * _stuAddr 的 get 方法 返回 _stuAddr
     * @return 学生的家庭住址 _stuAddr
     */
    public String getStuAddr(){
        return this._stuAddr;
    }

    /**
     * _stuIntake 的 get 方法 返回 _stuIntake
     * @return 学生的入学时间 _stuIntake
     */
    public String getStuIntake(){
        return this._stuIntake;
    }

    /**
     * _stuNum 的 set 方法
     * @param num 待赋值的学号
     */
    public void setStuNum(String num){
        this._stuNum=num;
    }

    /**
     * _stuName 的 set 方法
     * @param name 待赋值的姓名
     */
    public void setStuName(String name){
        this._stuName=name;
    }

    /**
     * _stuAge 的 set 方法
     * @param age 待赋值的年龄
     */
    public void setStuAge(Byte age){
        this._stuAge=age;
    }

    /**
     * _stuGend 的 set 方法
     * @param gend 待赋值的性别
     */
    public void setStuGend(Boolean gend){
        this._stuGend=gend;
    }

    /**
     * _stuID 的 set 方法
     * @param id 待赋值的身份证号
     */
    public void setStuID(String id){
        this._stuID=id;
    }

    /**
     * _stuSch 的 set 方法
     * @param sch 待赋值的所在学校
     */
    public void setStuSch(String sch){
        this._stuSch=sch;
    }

    /**
     * _stuMajor 的 set 方法
     * @param major 待赋值的专业
     */
    public void setStuMajor(String major){
        this._stuMajor=major;
    }

    /**
     * _stuAddr 的 set 方法
     * @param addr 待赋值的家庭住址
     */
    public void setStuAddr(String addr){
        this._stuAddr=addr;
    }

    /**
     * _stuIntake 的 set 方法
     * @param intake 待赋值的入学时间
     */
    public void setStuIntake(String intake){
        this._stuIntake=intake;
    }
}