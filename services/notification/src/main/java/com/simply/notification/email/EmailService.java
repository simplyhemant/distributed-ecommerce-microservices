package com.simply.notification.email;

import com.simply.notification.kafka.order.Product;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring6.SpringTemplateEngine;

import org.thymeleaf.context.Context;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.simply.notification.email.EmailTemplates.ORDER_CONFIRMATION;
import static com.simply.notification.email.EmailTemplates.PAYMENT_CONFIRMATION;
import static java.nio.charset.StandardCharsets.UTF_8;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendPaymentSuccessEmail(
            String destinationEmail,
            String customerName,
            BigDecimal amount,
            String orderReference
    ) {

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper =
                    new MimeMessageHelper(
                            mimeMessage,
                            MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                            UTF_8.name()
                    );

            messageHelper.setFrom("hs2312hemant@gmail.com");
            messageHelper.setTo(destinationEmail);
            messageHelper.setSubject(PAYMENT_CONFIRMATION.getSubject());

            Map<String, Object> variables = new HashMap<>();
            variables.put("customerName", customerName);
            variables.put("amount", amount);
            variables.put("orderReference", orderReference);

            Context context = new Context();
            context.setVariables(variables);

            String html = templateEngine.process(
                    PAYMENT_CONFIRMATION.getTemplate(),
                    context
            );

            messageHelper.setText(html, true);
            mailSender.send(mimeMessage);

            log.info("Email sent to {}", destinationEmail);

        } catch (Exception e) {
            log.warn("Cannot send email to {}", destinationEmail, e);
        }
    }

    @Async
    public void sendOrderConfirmationEmail(
            String destinationEmail,
            String customerName,
            BigDecimal amount,
            String orderReference,
            List<Product> products
    ) {

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper =
                    new MimeMessageHelper(
                            mimeMessage,
                            MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                            UTF_8.name()
                    );

            messageHelper.setFrom("hs2312hemant@gmail.com");
            messageHelper.setTo(destinationEmail);
            messageHelper.setSubject(ORDER_CONFIRMATION.getSubject());

            Map<String, Object> variables = new HashMap<>();
            variables.put("customerName", customerName);
            variables.put("totalAmount", amount);
            variables.put("orderReference", orderReference);
            variables.put("products", products);

            Context context = new Context();
            context.setVariables(variables);

            String html = templateEngine.process(
                    ORDER_CONFIRMATION.getTemplate(),
                    context
            );

            messageHelper.setText(html, true);
            mailSender.send(mimeMessage);

            log.info("Email sent to {}", destinationEmail);

        } catch (Exception e) {
            log.warn("Cannot send email to {}", destinationEmail, e);
        }
    }
}
