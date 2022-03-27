package het.alaska.order;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import het.alaska.order.PaymentDao;
import het.alaska.order.Payment;

@Repository("paymentDao")
public class PaymentDaoImpl implements PaymentDao {

    @Autowired(required = true)
    private EntityManager manager;
    
    private Session getSession() {
    	return manager.unwrap(Session.class);
    }

	@Override
	public Payment savePayment(Payment payment) {
		return (Payment) getSession().save(payment);
	}

	@Override
	public void updatePayment(Payment payment) {
		getSession().saveOrUpdate(payment);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly=false)
	public void deletePayment(String paymentId) {
		//TODO this op will fail with Person table key constraint
		Payment payment = getPayment(paymentId);
		getSession().delete(payment);
	}

	@Override
	public List<Payment> findPaymentsByType(String type) {
		Query query = getSession().getNamedQuery("Payment.findPaymentsByType");
	    query.setParameter("type", type);
	    List<Payment> paymentList = query.list();
	    return paymentList;
	}

	@Override
	public Payment getPayment(String id) {
		return getSession().find(Payment.class, id);
	}
}

