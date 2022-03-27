package het.alaska.order;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import het.alaska.order.OrderDao;
import het.alaska.order.Order;

@Service("orderService")
public class OrderServiceImpl implements OrderService {
	@Autowired
	OrderDao orderDao;

	
	public OrderServiceImpl(OrderDao orderDao) {
		this.orderDao = orderDao;
	}
	
	public OrderServiceImpl() {}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	@Override
	public Order create(Order order) {
		order.setDate(new Date(System.currentTimeMillis()));
		order.setPaymentId(order.getPaymentId());
		Order newOrder = orderDao.saveOrder(order);
		return newOrder;
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	@Override
	public Order findByOrderId(String orderId) {
		return orderDao.findOrderById(orderId);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	@Override
	public Order findByPaymentId(String paymentId) {
		return orderDao.findOrderByPaymentId(paymentId);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	@Override
	public void update(Order order) {
		order.setDate(new Date(System.currentTimeMillis()));
		orderDao.updateOrder(order);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	@Override
	public void delete(String orderId) {
		orderDao.deleteOrder(orderId);
	}

	@Override
	public List<Order> findOrdersByOrderNumber(String orderNumber) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<Order>findAll() {
	 List<Order> orderList = orderDao.findAll();
	 return orderList;
	}

}
