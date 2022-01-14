package het.alaska.service.impl;

import het.alaska.dao.PersonDao;
import het.alaska.dao.impl.PersonDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import het.alaska.model.Person;
import het.alaska.service.PersonService;

@Service("personService")

public class PersonServiceImpl implements PersonService {

	@Autowired
	PersonDao personDao;
	
	
	public PersonServiceImpl(PersonDao personDao) {
		this.personDao = personDao;
	}
	
	public PersonServiceImpl() {}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void create(Person person) {
		person.setSaveDt(new Date(System.currentTimeMillis()));
		personDao.persistPerson(person);
	}
	
	public Person findByPersonId(String personId) {
		return personDao.findPersonById(personId);
	}
	
	public Person findByUserName(String uname) {
		return personDao.findPersonByUserName(uname);
	}
//	
//	public Person findByFirstName(String fname) {
//		return personDao.findByFirstName(fname);
//	}
//
//	public Person findByLastName(String lname) {
//		return personDao.findByLastName(lname);
//	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void update(Person person) {
		person.setSaveDt(new Date(System.currentTimeMillis()));
		personDao.updatePerson(person);
	}

	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void delete(Person person) {
		personDao.deletePerson(person);
	}

}
