package het.alaska.service;

import het.alaska.model.Item;

import java.util.List;

import org.springframework.stereotype.Service;

public interface ItemService {
	public Item findByItemId(String itemId);
	
	public void create(Item item);
	
	public void update(Item item);
	
	public void delete(String itemId);
	
	public List<Item>findAll();
	
	public List<Item>findItemsByOrderNumber(String orderNumber);

	List<Item> findItemsByCategory(String category);
}
