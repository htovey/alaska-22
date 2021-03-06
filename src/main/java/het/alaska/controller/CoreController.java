package het.alaska.controller;

import het.alaska.model.Person;
import het.alaska.model.User;
import het.alaska.security.JwtTokenUtil;
import het.alaska.service.ItemService;
import het.alaska.service.PersonService;
import het.alaska.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CoreController {

	private ItemService itemService;
	private PersonService personService;
	private UserService userService;
	private JwtTokenUtil jwtTokenUtil;

	public final Log log = LogFactory.getLog(CoreController.class);
	@Autowired
	public CoreController(
			ItemService itemService, 
			PersonService personService, 
			UserService userService,
			JwtTokenUtil jwtTokenUtil
			) {
		this.itemService = itemService;
		this.personService = personService;
		this.userService = userService;
		this.jwtTokenUtil = jwtTokenUtil;
	}
	
	@GetMapping(value = "/requestReset", produces="application/json")
    public ResponseEntity <String> requestPasswordReset(
    		@RequestParam String userName, 
    		HttpServletRequest request) throws JSONException {
		HttpStatus status = HttpStatus.OK;
		String respString = "";
		User user = userService.findByUserName(userName);
		log.info("Reset Password request for user "+userName);
		if (user == null) {
			respString =  "error";
			status = HttpStatus.FORBIDDEN;
		} else {
			JSONObject jsonUser = new JSONObject();
			jsonUser.put("userName", userName);
			jsonUser.put("roleId", user.getRole());
			jsonUser.put("userId", user.getId());
			respString = jsonUser.toString();
		}
		
		return new ResponseEntity<String>(respString, status);
	}
	
	@PostMapping(value = "/reset", produces="application/json")
	public ResponseEntity <String> resetPassword(@RequestBody User user, HttpServletRequest request) {
		log.info("Password reset for user "+user.getUserName());
		String respStatus = "success";
		HttpStatus status = HttpStatus.OK;
		if (StringUtils.isEmpty(user.getUserName())) {
			respStatus = "error - no username in request.";
			status = HttpStatus.BAD_REQUEST;
		} else {
			userService.resetPassword(user);
		}
		
		return new ResponseEntity<String>(respStatus, status);
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET, produces="application/json")
    public ResponseEntity <String> login(HttpServletRequest request) throws JSONException {
		String userName = getUserName(request);
		log.info("Login request for user "+userName);
		String respStatus = "success";
		MultiValueMap<String, String> headers = new HttpHeaders();
		HttpStatus status = HttpStatus.OK;
		JSONObject json = new JSONObject();
		Person person = null;
		
		if (userName == null) {
			respStatus =  "error - user not found.";
			status = HttpStatus.FORBIDDEN;
		} else {
			User user = userService.findByUserName(userName);
			try {
				person = personService.findByUserName(user.getUserName());
			} catch(Exception ex) {
				//noop
			}
			
 			headers.add(HttpHeaders.AUTHORIZATION,
                    jwtTokenUtil.generateAccessToken(user));
			JSONObject jsonUser = new JSONObject();
			JSONObject jsonPerson = new JSONObject();
			
			if (user != null) {
				jsonUser.put("userName", userName);
				jsonUser.put("role", user.getRole());
				json.put("user", jsonUser);
			}
			
			if (person != null) {
				jsonPerson.put("id", person.getId());
				jsonPerson.put("fName", person.getFname());
				jsonPerson.put("lName", person.getLname());
				jsonPerson.put("userId", person.getUserName());
				json.put("person", jsonPerson);
			}
		}
		
		json.put("responseStatus", respStatus);
		
		return new ResponseEntity<String>(json.toString(), headers, status);		
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST, produces="application/json")
    public ResponseEntity <String> register(@RequestBody User user) throws JSONException {
		log.info("Register request for user "+user.getUserName());
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
	
	
	private String getUserName(HttpServletRequest request) {
		Principal principal = request.getUserPrincipal();
		String userName = principal.getName();
		return userName;
	}
}