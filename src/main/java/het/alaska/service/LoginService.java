package het.alaska.service;

import het.alaska.model.User;
import org.springframework.stereotype.Service;

public interface LoginService {
	public User login(String id, String password) throws Exception;
}
