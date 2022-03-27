package het.alaska.order;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import het.alaska.order.PaymentDao;
import het.alaska.order.Payment;
import het.alaska.order.PaymentService;

@Service("paymentService")
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	PaymentDao paymentDao;
	//id type amount note date
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	@Override
	public Payment create(Payment payment) {
		payment.setDate(new Date(System.currentTimeMillis()));
		payment.setId(null);
		Payment newPayment = paymentDao.savePayment(payment);
		return newPayment;
	}

	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void update(Payment payment) {
		paymentDao.updatePayment(payment);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void delete(String paymentId) {
		paymentDao.deletePayment(paymentId);
	}
//
//	@Override
//	public List<Payment> findPaymentsByAdminId(String adminId) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<Payment> findPaymentsByBizIdRoleType(int bizId, String roleType) {
//		return paymentDao.findPaymentsByBizIdRoleType(bizId, roleType);
//	}

	@Override
	@Transactional
	public Payment getPayment(String paymentId) {
		return paymentDao.getPayment(paymentId);
	}
}

