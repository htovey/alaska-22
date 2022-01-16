package het.alaska.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import het.alaska.model.Person;
import het.alaska.model.User;
import het.alaska.service.PersonService;
import het.alaska.service.UserService;

@RestController
@RequestMapping(path="/person")
public class PersonController {
	@Autowired
	PersonService personService;
	@Autowired
	UserService userService;

	public final Log log = LogFactory.getLog(PersonController.class);
	
	@RequestMapping(path = "/create", method = RequestMethod.POST, produces="application/json", consumes="application/json")
    ResponseEntity<String> createPerson(@RequestBody Person person, HttpServletRequest request) {
		String msg = "success";
		HttpStatus status = HttpStatus.CREATED;
		log.info("attempting to create "+person.getFname());
		User user = userService.findByUserName(person.getUserName());
		if (user == null) {
			msg = "Error - no user exists for user name.";
			status = HttpStatus.NOT_ACCEPTABLE;
		} else {
			person.setUserName(user.getUserName());
			personService.create(person);
		}
		return new ResponseEntity<String>(msg, status);
		
	}	

	@RequestMapping(path = "/update", method = RequestMethod.POST, produces="application/json", consumes="application/json")
	public @ResponseBody String updatePerson(@RequestBody Person person, HttpServletRequest request) {
		log.info("%%%%%%%%%%%     attempting to update person: "+person.getLname());
		personService.update(person);
		log.info("%%%%%%%%%%%%  DONE UPDATING %%%%%%%%%%%%");
		return "success";
	}
	
	@RequestMapping(value = "/persons", method = RequestMethod.GET, produces="application/json")
    public List<Map<String, String>> getPersonList(@RequestParam int bizId, @RequestParam ArrayList<String> roleTypes, HttpServletRequest request) throws JSONException {
		log.info("Person List request for bizId "+bizId);
		
		List<User> userList = new ArrayList<User>();
		
//		for(String role:  roleTypes) {
//			userList.addAll(userService.findUsersByBizIdRoleType(bizId, role));
//		}
//		
		List<Person> personListFromDb = userList.stream()
				.map(user -> personService.findByUserName(user.getUserName()))
				.collect(Collectors.toList());			
		
		List<Map<String,String>> personList = new ArrayList<Map<String,String>>();
		
		for (Person person : personListFromDb) {
			Map<String, String> personMap = new HashMap<String, String>();
			personMap.put("id", String.valueOf(person.getId()));
			personMap.put("fName", person.getFname());
			personMap.put("lName", person.getLname());
			personMap.put("userName", person.getUserName());
			personMap.put("saveDate", person.getSaveDt().toString());
			personList.add(personMap);
		}
		
		return personList;		
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST, produces="application/json")
	public @ResponseBody String delete(@RequestBody String [] personArray) {
		for (String userName : personArray) {
			log.info("deleting person with user name: "+userName);
			Person person = personService.findByUserName(userName);
			personService.delete(person);
			userService.delete(userName);
		}
		//TODO - Error handling
		return "success";
	}
	
	@RequestMapping(method = RequestMethod.GET, produces="application/json")
	ResponseEntity<String> getPerson(@RequestParam String userName) throws JSONException {
		log.info("GET request for user: "+userName);
		String msg = "success";
		HttpStatus status = HttpStatus.CREATED;
		Person person = personService.findByUserName(userName);
		if (person == null) {
			msg = "Error - person not found";
			status = HttpStatus.NOT_ACCEPTABLE;
		} else {
			JSONObject jsonPerson = new JSONObject();	
			jsonPerson.put("userName", userName);
			jsonPerson.put("fName", person.getFname());
			jsonPerson.put("lName", person.getLname());
			jsonPerson.put("id", person.getId());
			msg = jsonPerson.toString();
		}
		
		return new ResponseEntity<String>(msg, status);	

	}
}
