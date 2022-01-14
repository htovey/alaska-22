package het.alaska.service.impl;

import het.alaska.model.Item;
import het.alaska.dao.ItemDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.internal.util.EntityPrinter;

import java.util.Date;
import java.util.List;

import het.alaska.model.Item;
import het.alaska.service.ItemService;

@Service("itemService")
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	ItemDao itemDao;

	
	public ItemServiceImpl(ItemDao itemDao) {
		this.itemDao = itemDao;
	}
	
	public ItemServiceImpl() {}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void create(Item item) {
		item.setCreateDt(new Date(System.currentTimeMillis()));
		item.setSaveDt(new Date(System.currentTimeMillis()));
		item.setCategory(item.getCategory());
		item.setDescription(item.getDescription());
		itemDao.persistItem(item);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public Item findByItemId(Integer itemId) {
		return itemDao.findItemById(itemId);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void update(Item item) {
		item.setSaveDt(new Date(System.currentTimeMillis()));
		itemDao.updateItem(item);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void delete(Integer itemId) {
		itemDao.deleteItem(itemId);
	}


	@Override
	public List<Item> findItemsByOrderNumber(String orderNumber) {
		// TODO Auto-generated method stub
		return null;
	}

}
