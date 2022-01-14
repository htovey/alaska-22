package het.alaska.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import het.alaska.model.Person;
import het.alaska.service.LoginService;
import het.alaska.service.PersonService;
import het.alaska.dao.impl.*;
import het.alaska.model.User;
import het.alaska.dao.UserDao;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("loginService")
public class LoginServiceImpl implements LoginService {
	
    @Autowired
    private UserDao userDao;
    
    @Transactional(propagation=Propagation.REQUIRED, readOnly=false)
    public User login(String userId, String password) throws Exception {
       //return userDao.login(userId, password)
    	return null;
    }

}
