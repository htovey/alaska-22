package het.alaska.order;

import java.util.List;

public interface OrderDao {

	Order saveOrder(Order order);

	Order findOrderById(String orderId);

	void updateOrder(Order order);

	void deleteOrder(String orderId);

	List<Order> findAll();

	Order findOrderByPaymentId(String paymentId);

}
