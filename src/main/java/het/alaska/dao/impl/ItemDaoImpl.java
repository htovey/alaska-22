/**
 * 
 */
package het.alaska.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;

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
	
	public void persistItem(Item note) {
		getSession().persist(note);
	}

	public Item findItemById(Integer itemId) {
		return (Item) getSession().get(Item.class, itemId);
	}

	public void updateItem(Item item) {
		//getSession().update("Item", note);
		getSession().saveOrUpdate(item);
	}
	public void deleteItem(Integer itemId) {
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
	
	private Session getSession() {
		return manager.unwrap(Session.class);
	}

	@Override
	public List<Item> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int findItemByName(String name) {
		// TODO Auto-generated method stub
		return 0;
	}

}
