package api;

/**
 * 接口 {@code Store}商店系统接口，提供方法名
 * 
 * @author yfyou
 * @author wwb
 * 
 * @since 2023/08/30
 */

public interface Store {
	/**
	 * 显示所有商品 调用权限：ST/TC/AD
	 * 
	 * @param  id
	 * @return 查询结果（json字符串）
	 */
	public String queryAllStoreItem(String id);

	/**
	 * 获取商品信息 调用权限：ST/TC/AD
	 * 
	 * @param id       用户名
	 * @param itemName 商品名
	 * @return 查找结果（json字符串）
	 */
	public String queryItem(String id, String itemName);

	/**
	 * 购买商品 调用权限：ST/TC
	 * 
	 * @param id
	 * @param itemID 
	 * @param buyCnt 
	 * @return 购买结果（json字符串）
	 */
	public String buyItem(String id, String itemID, Short buyCnt);

	/**
	 * 修改商品信息 调用权限：AD
	 * 
	 * @param id         用户名
	 * @param itemID     商品ID 
	 * @param itemName   商品名
	 * @param itemPri    商品价格
	 * @param itemCnt    商品数量
	 * @return 修改结果（json字符串）
	 */
	public String updateItem(String id, String itemID, String itemName, Float itemPri, Short itemCnt);

	/**
	 * 添加商品 调用权限：AD
	 * 
	 * @param id         用户名
	 * @param itemID     商品ID 
	 * @param itemName   商品名
	 * @param itemPri    商品价格
	 * @param itemCnt    商品数量
	 * @return 添加结果（json字符串）
	 */
	public String addItem(String id, String itemID, String itemName, Float itemPri, Short itemCnt);

	/**
	 * 删除商品 调用权限：AD
	 * 
	 * @param id         用户名
	 * @param itemID     商品ID 
	 * @return 删除结果（json字符串）
	 */
	public String delItem(String id, String itemID);

	/**
	 * 余额充值 调用权限：ST/TC
	 * 
	 * @param id  用户名
	 * @param bal 充值金额
	 * @return 充值结果（json字符串）
	 */
	public String recharge(String id, Double bal);

	/**
	 * 余额查询 调用权限：ST/TC
	 * 
	 * @param id  用户名
	 * @return 查询结果（json字符串）
	 */
	public String queryBal(String id);
}
