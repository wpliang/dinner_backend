package com.dinner.service.user;


/**
 * 
* @ClassName: UserEnterpriseBaseService
* @Description: 用户企业相关基础服务接口
* @author wp.liang
* @date 2017年7月26日 下午3:02:59
*
 */
public interface UserEnterpriseBaseService {

	boolean isUserEmailExist(String email, Long excludeUserId);
}
