package het.alaska.order;

import java.util.List;

public interface OrderService {

	List<Order> findOrdersByOrderNumber(String orderNumber);

	List<Order> findAll();

	Order create(Order order);

	Order findByOrderId(String orderId);

	void update(Order order);

	void delete(String orderId);

	Order findByPaymentId(String paymentId);

}
