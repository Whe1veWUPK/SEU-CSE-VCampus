package api;

import vo.Doctor;

/**
 * 接口 {@code Hospital}医院系统接口，提供方法名
 * 
 * @author yfyou
 * @author wwb
 * 
 * @since 2023/08/30
 */

public interface Hospital {
	/**
	 * 预约挂号 调用权限：ST/TC
	 * @param usrID		用户登录名
	 * @param drID		预约的医生ID
	 * @param date		预约的日期（1=明天 2=后天 3=大后天）
	 * @param hour		预约的时间
	 * @return			预约结果（json字符串）
	 */
	public String reserve(String usrID, String drID, Byte date, Byte hour);
	
	/**
	 * 添加医生 调用权限：AD
	 * @param usrID		用户登录名
	 * @param dr		添加的医生
	 * @return			添加结果（json字符串）
	 */
	public String addDr(String usrID, Doctor dr);
	
	/**
	 * 删除医生 调用权限：AD
	 * @param usrID		用户登录名
	 * @param drID		删除的医生
	 * @return			删除结果（json字符串）
	 */
	public String delDr(String usrID, String drID);
	
	/**
	 * 修改医生信息 调用权限：AD
	 * @param usrID		用户登录名
	 * @param dr		修改的医生
	 * @return			修改结果
	 */
	public String setDr(Doctor dr);
	
	/**
	 * 切换天数
	 * @return			切换结果
	 */
	public Byte dayPass();
}
