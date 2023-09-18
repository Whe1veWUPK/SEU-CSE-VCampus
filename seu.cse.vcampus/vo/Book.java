package seu.cse.vcampus.vo;

/**
 * 类{@code Book}书籍类，封装着书籍信息
 *
 * @author Ruijin Zhang
 * @author Hanlong Liu
 *
 * @since 2023/8/30
 */

public class Book {
    String _bookID;
    String _bookName;
    Short _bookCnt;
    String _bookHolder;

    /**
     * Book类的构造方法
     * @param _bookID
     * @param _bookName
     * @param _bookCnt
     * @param _bookHolder
     */
    public Book(String _bookID,String _bookName,Short _bookCnt,String _bookHolder){
        set_bookCnt(_bookCnt);
        set_bookID(_bookID);
        set_bookName(_bookName);
        set_bookHolder(_bookHolder);
    }

    /**
     * _bookcnt的set方法
     * @param _bookCnt
     */
    public void set_bookCnt(Short _bookCnt) {
        this._bookCnt = _bookCnt;
    }

    /**
     * _bookID的set方法
     * @param _bookID
     */
    public void set_bookID(String _bookID) {
        this._bookID = _bookID;
    }

    /**
     * _bookName的set方法
     * @param _bookName
     */
    public void set_bookName(String _bookName) {
        this._bookName = _bookName;
    }

    /**
     * _bookHolder的set方法
     * @param bookHolder
     */
    public void set_bookHolder(String bookHolder) {
        this._bookHolder = bookHolder;
    }

    /**
     * _bookID的get方法
     * @return 返回String书号
     */
    public String get_bookID() {
        return _bookID;
    }

    /**
     * _bookCnt的get方法
     * @return 返回Short库存数
     */
    public Short get_bookCnt() {
        return _bookCnt;
    }

    /**
     * _bookName的get方法
     * @return 返回String书名
     */
    public String get_bookName() {
        return _bookName;
    }

    /**
     * _bookHolder的get方法
     * @return 返回String借阅者
     */
    public String get_bookHolder() {
        return _bookHolder;
    }
}
