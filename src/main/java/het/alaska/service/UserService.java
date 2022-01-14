package het.alaska.service;

import java.util.List;

import het.alaska.model.User;
import het.alaska.types.RoleType;

public interface UserService {
	public User findByUserName(String userName);
	
	public User getUser(String userId);
	
	public void create(User user);
	
	public void update(User user);
	
	public void delete(String userName);
	
	//public List<User>findUsersByAdminId(String adminId);

	//List<User> findUsersByBizIdRoleType(int bizId, String roleType);

	void resetPassword(User user);
}
