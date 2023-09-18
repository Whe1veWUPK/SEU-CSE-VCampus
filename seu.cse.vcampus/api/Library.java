package api;

/**
 * 接口 {@code Library}图书馆接口，提供用户图书馆的方法名
 * 
 * @author yfyou
 * @author wwb
 * 
 * @since 2023/08/21
 */

public interface Library {
	/**
	 * 显示全部书籍信息（不包括借阅者） 调用权限：ST/TC/AD
	 *
	 * @param id
	 * @return 查询结果（json字符串）
	 */
	public String queryAllBook(String id);

	/**
	 * 查询书籍（ST/TC不包含借阅者，AD包含借阅者） 调用权限：ST/TC/AD
	 *
	 * @param id       用户名
	 * @param bookName 书名
	 * @return 查询结果（json字符串）
	 */
	public String queryBook(String id, String bookName);

	/**
	 * 借阅书籍 调用权限：ST/TC
	 *
	 * @param id     用户名
	 * @param bookID 书籍ID
	 * @return 借阅结果（json字符串）
	 */
	public String borrowBook(String id, String bookID, Short borrowCnt);

	/**
	 * 归还书籍 调用权限：ST/TC
	 *
	 * @param id     用户名
	 * @param bookID 书籍ID
	 * @return 归还结果（json字符串）
	 */
	public String returnBook(String id, String bookID, Short returnCnt);

	/**
	 * 添加书籍 调用权限：AD
	 *
	 * @param id       用户名
	 * @param bookID   书籍ID
	 * @param bookName 书名
	 * @param bookCnt  库存量
	 * @return
	 */
	public String addBook(String id, String bookID, String bookName, Short bookCnt);
	
	/**
	 * 删除书籍 调用权限：AD
	 *
	 * @param id     用户名
	 * @param bookID 书籍ID
	 * @return
	 */
	public String delBook(String id, String bookID);
	
	/**
	 * 查询已借书籍 调用权限：ST/TC
	 *
	 * @param id
	 */
	public String queryBorrowedBook(String id);
	
	/**
	 * 修改书籍name||cnt 调用权限：AD
	 *
	 * @param id
	 * @param bookid
	 * @param bookName
	 * @param bookCnt
	 * @param bookHolder
	 * @return 修改结果（json字符串）
	 */
	public String updateBookNameCnt(String id, String bookID, String bookName, Short bookCnt);
	
	/**
	 * 添加书籍借阅者 调用权限：AD
	 *
	 * @param id
	 * @param bookID
	 * @param addID
	 * @return 修改结果（json字符串）
	 */
	public String addBookHolder(String id, String bookID, String addID) ;
	
	/**
	 * 删除书籍借阅者 调用权限：AD
	 *
	 * @param id
	 * @param bookID
	 * @param delid
	 * @return 修改结果（json字符串）
	 */
	public String delBookHolder(String id, String bookID, String delid);
	
}
