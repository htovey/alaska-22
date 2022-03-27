package het.alaska.order;

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

import het.alaska.order.Order;
import het.alaska.order.Payment;
import het.alaska.order.OrderService;
import het.alaska.order.PaymentService;

@RestController
@RequestMapping(path="/order")
public class OrderController {

	@Autowired
	OrderService orderService;
	
	@Autowired
	PaymentService paymentService;
	
	public final Log log = LogFactory.getLog(OrderController.class);
	
	@RequestMapping(path = "/create", method = RequestMethod.POST, consumes="application/json", produces="application/json")
	ResponseEntity <String> createOrder(@RequestBody Order order, HttpServletRequest request) {
		String msg = "success";
		HttpStatus status = HttpStatus.CREATED;
		
		try {
			Order newOrder = orderService.create(order);	
			msg = msg + " order number "+newOrder.getId()+" created.";
		} catch(Exception ex) {
			status = HttpStatus.NOT_ACCEPTABLE;
			msg = "Attempt to create order failed.  Please try again.";
		}
		
		return new ResponseEntity<String>(msg, status);				
	}
	
	@RequestMapping(path = "/createOrderPayment", method = RequestMethod.POST, consumes="application/json", produces="application/json")
	ResponseEntity <String> createPayment(@RequestBody Payment payment, HttpServletRequest request) {
		String msg = "success";
		HttpStatus status = HttpStatus.CREATED;
		
		try {
			Payment newPayment = paymentService.create(payment);	
			msg = msg + " order number "+newPayment.getId()+" created.";
		} catch(Exception ex) {
			status = HttpStatus.NOT_ACCEPTABLE;
			msg = "Attempt to create order failed.  Please try again.";
		}
		
		return new ResponseEntity<String>(msg, status);				
	}
	
	
	
	@RequestMapping(path = "/update", method = RequestMethod.POST, consumes="application/json", produces="application/json")
	ResponseEntity <String> updateOrder(@RequestBody Order order, HttpServletRequest request) {
		String msg = "success";
		HttpStatus status = HttpStatus.OK;
		
		try {
			orderService.update(order);
		} catch(Exception ex) {
			status = HttpStatus.NOT_ACCEPTABLE;
			msg = "Attempt to update order failed.  Please try again.";
		}
		
		return new ResponseEntity<String>(msg, status);				
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	ResponseEntity <String> delete(@RequestBody String [] orderArray) {
		String msg = "success";
		HttpStatus status = HttpStatus.OK;
		try {
			for (String orderId : orderArray) {
				orderService.delete(orderId);
			}
		} catch(Exception ex) {
			status = HttpStatus.BAD_REQUEST;
			msg = "delete operation failed.";
		}
		return new ResponseEntity<String>(msg, status);	
	}

	@RequestMapping(value = "/orderList", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody List<Map<String, String>>orderList(HttpServletRequest request) {
		//String userId = getUserName(request);
		//log.info("getting notes for "+userId);
		List<Order> orderListFromDb = orderService.findAll();
		
		List<Map<String,String>> orderList = new ArrayList<Map<String,String>>();
		
		for (Order order : orderListFromDb) {
			Map<String, String> orderMap = new HashMap<String, String>();
			orderMap.put("id", String.valueOf(order.getId()));
			orderMap.put("date", order.getDate().toString());
			orderMap.put("paymentId", order.getPaymentId());
			orderList.add(orderMap);
		}
		
		return orderList;
	}
	
	@RequestMapping(method = RequestMethod.GET, consumes="application/json", produces="application/json")
	ResponseEntity<String> getOrder(@RequestParam String id) throws JSONException {
		String msg = "success";
		HttpStatus status = HttpStatus.OK;
		Order order = orderService.findByOrderId(id);
		if (order == null) {
			msg = "Error - order not found";
			status = HttpStatus.NOT_ACCEPTABLE;
		} else {
			JSONObject jsonOrder = new JSONObject();	
			jsonOrder.put("id", String.valueOf(order.getId()));
			jsonOrder.put("date", order.getDate().toString());
			jsonOrder.put("paymentId", order.getPaymentId());
			msg = jsonOrder.toString();
		}
		
		return new ResponseEntity<String>(msg, status);	
	}
}
