package het.alaska.dao;

import java.sql.SQLException;
import java.util.List;

import het.alaska.model.Item;

public interface ItemDao {

	void persistItem(Item item);
	  
	Item findItemById(String id);
	
	int findItemByName(String name);
	  
	void updateItem(Item item);
	  
	void deleteItem(String id);
	
	public List<Item> findAll();

	List<Item> findItemsByCategory(String category);
}