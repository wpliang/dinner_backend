package com.dinner.service.user;

import com.dinner.db.vo.user.UserVo;

/**
 * 
* @ClassName: UserEnterpriseService
* @Description: 用户企业相关服务接口类
* @author wp.liang
* @date 2017年7月26日 下午2:44:21
*
 */
public interface UserEnterpriseService {
   
	UserVo registEnterprise(String entName, String userName, String email, String phone, String comment);
}
