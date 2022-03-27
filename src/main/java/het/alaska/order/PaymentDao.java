package het.alaska.order;

import java.util.List;

import het.alaska.order.Payment;

public interface PaymentDao {

	void updatePayment(Payment payment);

	Payment getPayment(String paymentId);

	void deletePayment(String paymentId);

	Payment savePayment(Payment payment);

	List<Payment> findPaymentsByType(String type);

}
