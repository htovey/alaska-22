/**
 * 
 */
package het.alaska.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import het.alaska.dao.ItemDao;
import het.alaska.model.Item;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


/**
 * @author heather
 *
 */

@Repository("itemDao")
public class ItemDaoImpl implements ItemDao {

	@Autowired
	private EntityManager manager;
	
	public void persistItem(Item item) {
		getSession().persist(item);
	}

	public Item findItemById(String itemId) {
		return (Item) getSession().get(Item.class, itemId);
	}

	public void updateItem(Item item) {
		//getSession().update("Item", note);
		getSession().saveOrUpdate(item);
	}
	public void deleteItem(String itemId) {
		Item item = findItemById(itemId);
		getSession().delete(item);

	}
	
	//TODO: research this further

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Item> findItemsByPerson(String userId) {
		Session session = getSession();
		Query query = session.getNamedQuery("Item.findItemsByPerson");
		query.setParameter("USER_ID", userId);
		List<Item> itemList = query.list();
		return itemList;
	}
	
	
	@Override
	public List<Item> findItemsByCategory(String category) {
		
		CriteriaBuilder builder = getSession().getCriteriaBuilder();

		//Create CriteriaQuery
		CriteriaQuery<Item> criteria = builder.createQuery(Item.class);
		
		 // Specify criteria root
        Root<Item> root = criteria.from(Item.class);
        
        criteria.select(root).where(builder.equal(root.get("category"), category));


        // Execute query
        List<Item> itemList = getSession().createQuery(criteria).getResultList();
        
        return itemList;
	}
	
	private Session getSession() {
		return manager.unwrap(Session.class);
	}

	@Override
	public List<Item> findAll() {
		//Create CriteriaBuilder
		CriteriaBuilder builder = getSession().getCriteriaBuilder();

		//Create CriteriaQuery
		CriteriaQuery<Item> criteria = builder.createQuery(Item.class);
		
		 // Specify criteria root
        criteria.from(Item.class);

        // Execute query
        List<Item> itemList = getSession().createQuery(criteria).getResultList();
        
        return itemList;
	}

	@Override
	public int findItemByName(String name) {
		// TODO Auto-generated method stub
		return 0;
	}

}
