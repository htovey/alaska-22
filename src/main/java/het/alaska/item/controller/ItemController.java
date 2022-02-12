package het.alaska.item.controller;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import het.alaska.model.Item;
import het.alaska.model.User;
import het.alaska.service.ItemService;

@RestController
@RequestMapping(path="/item")
public class ItemController {

	@Autowired
	ItemService itemService;
	
	public final Log log = LogFactory.getLog(ItemService.class);
	
	@RequestMapping(path = "/create", method = RequestMethod.POST, consumes="application/json", produces="application/json")
	ResponseEntity <String> createItem(@RequestBody Item item, HttpServletRequest request) {
		String msg = "success";
		HttpStatus status = HttpStatus.CREATED;
	
		itemService.create(item);	
		
		return new ResponseEntity<String>(msg, status);				
	}
	
	@RequestMapping(path = "/update", method = RequestMethod.POST, consumes="application/json", produces="application/json")
	ResponseEntity <String> updateItem(@RequestBody Item item, HttpServletRequest request) {
		String msg = "success";
		HttpStatus status = HttpStatus.OK;
		
		try {
			itemService.update(item);
		} catch(Exception ex) {
			status = HttpStatus.NOT_ACCEPTABLE;
			msg = "Attempt to update Item failed.  Please try again.";
		}
		
		return new ResponseEntity<String>(msg, status);				
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	ResponseEntity <String> delete(@RequestBody String [] itemArray) {
		String msg = "success";
		HttpStatus status = HttpStatus.OK;
		try {
			for (String itemId : itemArray) {
				itemService.delete(itemId);
			}
		} catch(Exception ex) {
			status = HttpStatus.BAD_REQUEST;
			msg = "delete operation failed.";
		}
		return new ResponseEntity<String>(msg, status);	
	}

	@RequestMapping(value = "/itemList", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody List<Map<String, String>>itemList(HttpServletRequest request) {
		//String userId = getUserName(request);
		//log.info("getting notes for "+userId);
		List<Item> itemListFromDb = itemService.findAll();
		
		List<Map<String,String>> itemList = new ArrayList<Map<String,String>>();
		
		for (Item item : itemListFromDb) {
			Map<String, String> itemMap = new HashMap<String, String>();
			itemMap.put("id", String.valueOf(item.getId()));
			itemMap.put("name", item.getName());
			itemMap.put("category", item.getCategory());
			itemMap.put("description", item.getDescription());
			itemMap.put("saveDate", item.getSaveDt().toString());
			itemMap.put("imageUrl", item.getImageUrl());
			itemList.add(itemMap);
		}
		
		return itemList;
	}
	
	@RequestMapping(value = "/orderItemList", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody List<Map<String, String>>orderItemList(@RequestBody String orderNumber, HttpServletRequest request) {
		//String userId = getUserName(request);
		//log.info("getting notes for "+userId);
		List<Item> itemListFromDb = itemService.findItemsByOrderNumber(orderNumber);
		
		List<Map<String,String>> itemList = new ArrayList<Map<String,String>>();
		
		for (Item item : itemListFromDb) {
			Map<String, String> itemMap = new HashMap<String, String>();
			itemMap.put("id", String.valueOf(item.getId()));
			itemMap.put("name", item.getName());
			itemMap.put("category", item.getCategory());
			itemMap.put("description", item.getDescription());
			itemMap.put("img_url", item.getImageUrl());
			itemMap.put("saveDate", item.getSaveDt().toString());
			itemList.add(itemMap);
		}
		
		return itemList;
	}
	
	@RequestMapping(method = RequestMethod.GET, consumes="application/json", produces="application/json")
	ResponseEntity<String> getItem(@RequestParam String id) throws JSONException {
		String msg = "success";
		HttpStatus status = HttpStatus.OK;
		Item item = itemService.findByItemId(id);
		if (item == null) {
			msg = "Error - item not found";
			status = HttpStatus.NOT_ACCEPTABLE;
		} else {
			JSONObject jsonItem = new JSONObject();	
			jsonItem.put("itemName", item.getName());
			jsonItem.put("itemCategory", item.getCategory());
			jsonItem.put("itemDescription", item.getDescription());
			jsonItem.put("itemId", item.getId());
			jsonItem.put("itemImageUrl", item.getImageUrl());
			msg = jsonItem.toString();
		}
		
		return new ResponseEntity<String>(msg, status);	
	}
}
