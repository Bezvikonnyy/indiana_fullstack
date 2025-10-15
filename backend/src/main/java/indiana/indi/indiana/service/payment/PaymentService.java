package indiana.indi.indiana.service.payment;

import indiana.indi.indiana.dto.cartAndPay.PaymentMethodDto;
import indiana.indi.indiana.dto.cartAndPay.PaymentRequestDto;
import indiana.indi.indiana.repository.cartAndPay.OrderRepository;
import indiana.indi.indiana.repository.cartAndPay.PaymentRepository;
import indiana.indi.indiana.service.payment.LigPayStrategy.LiqPayService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

}
