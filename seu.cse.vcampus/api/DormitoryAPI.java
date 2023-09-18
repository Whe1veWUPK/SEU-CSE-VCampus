package api;

/**
 * 接口 {@code Dormitory}宿舍系统接口，提供方法名
 * 
 * @author yfyou
 * @author wwb
 * 
 * @since 2023/08/30
 */

public interface DormitoryAPI {
	/**
	 * 显示所有宿舍 调用权限：AD
	 * 
	 * @param  id
	 * @return 显示结果（json字符串）
	 */
	public String queryAllDormitory(String id);
	
	/**
	 * 学生获取自己宿舍信息 调用权限：ST
	 * 
	 * @param id
	 * @return 查询结果（json字符串）
	 */
	public String stuQueryDormitory(String id);
	
	/**
	 * 管理员获取某个宿舍信息 调用权限：AD
	 * 
	 * @param id
	 * @param domID
	 * @return 查询结果（json字符串）
	 */
	public String adQueryDormitory(String id, String domID);
	
	/**
	 * 宿舍添加学生 调用权限：AD
	 * 
	 * @param id
	 * @param addID
	 * @param domID
	 * @return 添加结果（json字符串）
	 */
	public String addMember(String id, String addID, String domID);
	
	/**
	 * 宿舍删除学生 调用权限：AD
	 * 
	 * @param id
	 * @param delid
	 * @return 删除结果（json字符串）
	 */
	public String delMember(String id, String delid);

}
