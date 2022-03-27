/**
 * 
 */
package het.alaska.order;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import het.alaska.order.OrderDao;
import het.alaska.order.Order;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


/**
 * @author heather
 *
 */

@Repository("orderDao")
public class OrderDaoImpl implements OrderDao {

	@Autowired
	private EntityManager manager;
	
	public Order saveOrder(Order order) {
		return (Order) getSession().save(order);
	}

	public Order findOrderById(String orderId) {
		return (Order) getSession().get(Order.class, orderId);
	}

	public void updateOrder(Order order) {
		//getSession().update("Order", note);
		getSession().saveOrUpdate(order);
	}
	public void deleteOrder(String orderId) {
		Order order = findOrderById(orderId);
		getSession().delete(order);

	}
	
	//TODO: research this further

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Order> findOrdersByPerson(String userId) {
		Session session = getSession();
		Query query = session.getNamedQuery("Order.findOrdersByPerson");
		query.setParameter("USER_ID", userId);
		List<Order> orderList = query.list();
		return orderList;
	}
	
	private Session getSession() {
		return manager.unwrap(Session.class);
	}

	@Override
	public List<Order> findAll() {
		//Create CriteriaBuilder
		CriteriaBuilder builder = getSession().getCriteriaBuilder();

		//Create CriteriaQuery
		CriteriaQuery<Order> criteria = builder.createQuery(Order.class);
		
		 // Specify criteria root
        criteria.from(Order.class);

        // Execute query
        List<Order> orderList = getSession().createQuery(criteria).getResultList();
        
        return orderList;
	}

	@Override
	public Order findOrderByPaymentId(String paymentId) {
		return (Order) getSession().get(Order.class, paymentId);
	}

}