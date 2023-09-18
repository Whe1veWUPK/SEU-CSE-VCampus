package dao;

import db.DbHelper;
import vo.Book;
import vo.StoreItem;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * 类 {@code BookDao}提供对书籍信息的增、删、改、查功能.
 *
 * @author Ruijin Zhang
 * @author Hanlong Liu
 *
 * @since 2023/9/1
 */
public class BookDao {
    public BookDao(){}

    /**
     * queryBookId方法根据id进行查询.
     *
     * @param id
     * @return 查询成功返回Book，失败返回null.
     */
    public static Book queryBookById(String id){
        String sqlString="select * from tblBook";
        try{
            ResultSet resultSet= DbHelper.executeQuery(sqlString);
            while (resultSet.next()){
                if(id.equals(resultSet.getString("bId"))){
                    String bid=resultSet.getString("bId");
                    String bname=resultSet.getString("bName");
                    Short bcnt=resultSet.getShort("bCnt");
                    String bholder=resultSet.getString("bHolder");
                    return new Book(bid,bname,bcnt,bholder);
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * queryBookByName方法根据名称进行查询.
     *
     * @param name
     * @return 查询成功返回包含所有查询名称Book的List，查询失败返回null.
     */
    public static List<Book>queryBookByName(String name){
        String sqlString="select * from tblBook";
        List<Book>books=new ArrayList<Book>();
        try{
            ResultSet resultSet=DbHelper.executeQuery(sqlString);
            while (resultSet.next()){
                String tmpName=resultSet.getString("bName");
                if(name.equals(tmpName)){
                    String bid=resultSet.getString("bId");
                    short bcnt=resultSet.getShort("bCnt");
                    String bholder=resultSet.getString("bHolder");
                    Book tmpBook=new Book(bid,tmpName,bcnt,bholder);
                    if(tmpBook!=null){
                        books.add(tmpBook);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        if(books.isEmpty()){
            return null;
        }
        return books;
    }

    /**
     * addBook方法增加书籍.
     *
     * @param book
     * @return 书籍插入成功返回1，因存在插入失败返回-1，其他原因返回0.
     */
    public static Byte addBook(Book book){
        String bid= book.get_bookID();
        String bname=book.get_bookName();
        Short bcnt=book.get_bookCnt();
        String bholder=book.get_bookHolder();
        String sqlString="insert into tblBook(bId,bName,bCnt,bHolder) values('"+bid+"','"+bname+"','"+bcnt+"','"+bholder+"')";
        if(queryBookById(bid)!=null){
            //书籍已存在
            return -1;
        }
        else {
            try{
                DbHelper.executeNonQuery(sqlString);
                //插入成功
                return 1;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return 0;
    }

    /**
     * delBook方法删除书籍.
     * @param bid
     * @return 书籍删除成功返回1，不存在删除失败返回-1，其他原因失败返回0.
     */
    public static Byte delBook(String bid){

        String sqlString="delete * from tblBook where bId ='"+bid+"' ";
        try{
            if(queryBookById(bid)!=null){
                //查询存在商品，执行删除功能
                DbHelper.executeNonQuery(sqlString);
                return 1;
            }else{
                //数据不存在删除失败
                return -1;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * updateBook方法更新书籍信息.
     *
     * @param book
     * @return 书籍修改成功返回1，因不存在修改失败返回-1，其他原因修改失败返回0.
     */
    public static Byte updateBook(Book book){
        String bid=book.get_bookID();
        String bname=book.get_bookName();
        Short bcnt=book.get_bookCnt();
        String bholder=book.get_bookHolder();
        String sqlString="UPDATE tblBook SET bId='"+bid+"',bName='"+bname+"',bCnt='"+bcnt+"',bHolder='"+bholder+"' WHERE bId='"+bid+"'";
        try{
            if(queryBookById(bid)!=null){
                //修改成功
                DbHelper.executeNonQuery(sqlString);
                return 1;
            }
            else return -1;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
    /**
     * 返回全部书籍的方法
     * @return 存放在数据库中的全部书籍
     */
    public static ArrayList<Book> queryAllBooks(){
        String sqlString="select*from tblBook";
        ArrayList<Book> bookArrayList=new ArrayList<>();
        try{
            ResultSet rs=DbHelper.executeQuery(sqlString);
            while(rs.next()){
                String bId=rs.getString("bId");
                Book book=queryBookById(bId);
                if(book!=null){
                    bookArrayList.add(book);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        if(bookArrayList.isEmpty()){
            return null;
        }
        return bookArrayList;

    }
/*    public static void main(String[] args) {
        Short cnt=100;
        Book book1=new Book("1","计算机组成原理",cnt,"张瑞晋;");
        Book book2=new Book("2","数据结构",cnt,"张瑞晋;");
        Book book3=new Book("3","计算机系统结构",cnt,"张瑞晋;");
        updateBook(book3);
        //delBook(book1);
    }*/
}
