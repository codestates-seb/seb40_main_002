package main.project.server.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import javax.mail.internet.MimeMessage;

@Slf4j
public class TemplateEmailSendable implements EmailSendable{
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    private final Context context;

    public TemplateEmailSendable(JavaMailSender javaMailSender,
                                 TemplateEngine templateEngine,
                                 Context context) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
        this.context = context;
    }

    @Override
    public void send(String[] to, String subject, String message, String templateName) {
        try {
            context.setVariable("message", message);

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            String html = templateEngine.process(templateName, context);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(html, true);
            mimeMessageHelper.addInline("image", new ClassPathResource("templates/images/image-1.jpeg"));  // .html 이미지 삽입

            javaMailSender.send(mimeMessage);
            log.info("Sent Template email!");
        } catch (Exception e) {
            log.error("email send error: ", e);
        }

    }
}
