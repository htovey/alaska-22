package het.alaska.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import het.alaska.model.User;
import het.alaska.service.UserService;

@RestController
@RequestMapping(path="/user")
public class UserController {

	@Autowired
	UserService userService;
	
	public final Log log = LogFactory.getLog(UserController.class);
	
	@RequestMapping(path = "/create", method = RequestMethod.POST, consumes="application/json", produces="application/json")
	ResponseEntity<String> createUser(@RequestBody User user, HttpServletRequest request) {
		log.info("Create request for user "+user.getUserName());
		String msg = "success";
		HttpStatus status = HttpStatus.CREATED;
		if (userService.findByUserName(user.getUserName()) != null) {
			msg = "Error - user name already exists";
			status = HttpStatus.CONFLICT;
		} else {
			userService.create(user);
		}
		
		return new ResponseEntity<String>(msg, status);				
	}
	
	@RequestMapping(path = "/update", method = RequestMethod.POST, consumes="application/json", produces="application/json")
	ResponseEntity<String> updateUser(@RequestBody User user, HttpServletRequest request) {
		log.info("Update request for user: "+user.getUserName());
		String msg = "success";
		HttpStatus status = HttpStatus.OK;
		try {
			userService.update(user);
		} catch(HibernateException ex) {
			msg = "ERROR: "+ex.getCause();
			status = HttpStatus.BAD_REQUEST;
		}
		
		return new ResponseEntity<String>(msg, status);				
	}
	
	@RequestMapping(method = RequestMethod.GET, consumes="application/json", produces="application/json")
	ResponseEntity<String> getUser(@RequestParam String userName) throws JSONException {
		log.info("GET request for user: "+userName);
		String msg = "success";
		HttpStatus status = HttpStatus.CREATED;
		User user = userService.findByUserName(userName);
		if (user == null) {
			msg = "Error - user not found";
			status = HttpStatus.NOT_ACCEPTABLE;
		} else {
			JSONObject jsonUser = new JSONObject();	
			jsonUser.put("userName", userName);
			jsonUser.put("roleType", user.getRole());
			msg = jsonUser.toString();
		}
		
		return new ResponseEntity<String>(msg, status);	
	}
}
