package het.alaska.order;

public interface PaymentService {

	void delete(String paymentId);

	Payment getPayment(String paymentId);
	
	Payment create(Payment payment);

}
