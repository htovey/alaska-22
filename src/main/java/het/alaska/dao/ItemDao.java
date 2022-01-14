package het.alaska.dao;

import java.sql.SQLException;
import java.util.List;

import het.alaska.model.Item;

public interface ItemDao {

	void persistItem(Item item);
	  
	Item findItemById(Integer id);
	
	int findItemByName(String name);
	  
	void updateItem(Item item);
	  
	void deleteItem(Integer id);
	
	public List<Item> findAll();
}