package com.dinner.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dinner.common.base.filter.Filter;
import com.dinner.common.base.service.BaseService;
import com.dinner.db.dao.user.EnterpriseDao;
import com.dinner.db.dao.user.OrganizationDao;
import com.dinner.db.dao.user.UserDao;
import com.dinner.db.dto.user.UserDto;

import lombok.Getter;


/**
 * 
* @ClassName: UserEnterpriseBaseServiceImpl
* @Description: 用户企业相关基础服务实现类
* @author wp.liang
* @date 2017年7月26日 下午3:05:23
*
 */
@Repository("userEnterpriseBaseService")
public class UserEnterpriseBaseServiceImpl extends BaseService implements UserEnterpriseBaseService {

	@Autowired @Getter
	private UserDao userDao;
	@Autowired @Getter
	private EnterpriseDao enterpriseDao;
	@Autowired @Getter
	private OrganizationDao organizationDao;
	
	@Override
	@Transactional
	public boolean isUserEmailExist(String email, Long excludeUserId) {
		Filter filter = new Filter(UserDto.class);
		filter.addEqualTo("email", email.toLowerCase().trim());
		if (excludeUserId != null) {
			filter.addNotEqualTo("id", excludeUserId);
		}
		return userDao.countByFilter(filter)>0;
	}

}
