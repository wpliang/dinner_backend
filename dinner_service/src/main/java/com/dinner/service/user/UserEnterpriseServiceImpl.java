package com.dinner.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dinner.common.base.constant.Constants;
import com.dinner.common.base.service.BaseService;
import com.dinner.common.base.utils.MyUtils;
import com.dinner.common.base.utils.ValidatorUtil;
import com.dinner.db.dao.user.EnterpriseDao;
import com.dinner.db.dao.user.OrganizationDao;
import com.dinner.db.dao.user.UserDao;
import com.dinner.db.dto.user.EnterpriseDto;
import com.dinner.db.dto.user.OrganizationDto;
import com.dinner.db.dto.user.UserDto;
import com.dinner.db.vo.user.EnterpriseVo;
import com.dinner.db.vo.user.UserVo;
import com.dinner.service.cache.PropertyUtil;

import lombok.Getter;

/**
 * 
* @ClassName: UserEnterpriseServiceImpl
* @Description: 用户企业相关服务接口实现类
* @author wp.liang
* @date 2017年7月26日 下午2:51:49
*
 */
@Repository("userEnterpriseService")
public class UserEnterpriseServiceImpl extends BaseService implements UserEnterpriseService {

	@Autowired
	private UserEnterpriseBaseService userEnterpriseBaseService;
	@Autowired @Getter
	private UserDao userDao;
	@Autowired @Getter
	private EnterpriseDao enterpriseDao;
	@Autowired @Getter
	private OrganizationDao organizationDao;
	
	@Override
	public UserVo registEnterprise(String entName, String userName, String email, String phone, String comment) {
		
		UserVo retUserVo = new UserVo();
		if(!ValidatorUtil.validEmail(email)) {
			retUserVo.setErrorMsg(PropertyUtil.getProperty("userenterpriseservice.error.notvalidemail"));
			return retUserVo;
		}
		boolean blnEmailExist = userEnterpriseBaseService.isUserEmailExist(email, null);
		if(blnEmailExist) {
			retUserVo.setErrorMsg(PropertyUtil.getProperty("userenterpriseservice.error.emailisexist"));
			return retUserVo;
		}
		
		EnterpriseDto enterpriseDto = new EnterpriseDto();
		enterpriseDto.setEnterpriseName(entName);
		enterpriseDto.setRegisterUser(userName);
		enterpriseDto.setRegisterUserPhone(phone);
		enterpriseDto.setComment(comment);
		enterpriseDto.setMaxUsers(Constants.ENTERPRISE_USER_DEFAULT_NUMBER);
		enterpriseDto = enterpriseDao.save(enterpriseDto);
		
		OrganizationDto organizationDto = new OrganizationDto();
		organizationDto.setEnterpriseId(enterpriseDto.getId());
		organizationDto.setOrgName(entName);
		organizationDto = organizationDao.save(organizationDto);
		organizationDto.setSearchId(organizationDto.getId()); //set searchId
		organizationDao.update(organizationDto);
		
		String password = "123456"; //MyUtils.generateStrongPassword();
		UserDto userDto = new UserDto();
		userDto.setRole(Constants.DTO_USER_ADMIN_TRUE);
		userDto.setName(userName);
		userDto.setEnterpriseId(enterpriseDto.getId());
		userDto.setLoginName(email);
		userDto.setEmail(email);
		userDto.setOrganizationId(organizationDto.getId());
		userDto.setPassword(MyUtils.encryptPwd(password));
		userDto.setPhone(phone);
		userDto = userDao.save(userDto);
		
		retUserVo = getDozerBeanUtil().convert(userDto, UserVo.class);
		retUserVo.setPassword(password);
		retUserVo.setEnterpriseVo(getDozerBeanUtil().convert(enterpriseDto, EnterpriseVo.class));
		retUserVo.setCreated(null);
		retUserVo.setModified(null);
		retUserVo.getEnterpriseVo().setCreated(null);
		retUserVo.getEnterpriseVo().setModified(null);
		
//		PushVo pushVo = new PushVo(retUserVo, retUserVo.getEnterpriseVo(), 
//				PushTypeEnum.PUSH_TYPE_CREATE_ENTREPRISE.getType(), 
//				getWebBaseurl(), Constants.EMAIL_SUBJECT_CREATE_USER, null, null);
//		pushRestClient.pushMessage(JsonUtil.toJsonString(pushVo), Constants.PUSH_SEND_TYPE_EMAIL);

		return retUserVo;
	}

   
}
