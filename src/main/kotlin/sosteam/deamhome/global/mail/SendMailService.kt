package sosteam.deamhome.global.mail

import jakarta.mail.internet.MimeMessage
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class SendMailService(private val mailSender: JavaMailSender) {
    @Value("\${spring.mail.username}")
    val from: String? = null

    @Value("\${spring.mail.default-encoding}")
    val decode: String = "UTF-8"

    fun sendTestEmail(to: String, subject: String, text: String) {
        val mimeMessage: MimeMessage = mailSender.createMimeMessage()
        val messageHelper = MimeMessageHelper(mimeMessage, decode)

        messageHelper.setFrom(from!!)
        messageHelper.setTo(to)
        messageHelper.setSubject(subject)
        messageHelper.setText(text)
        mailSender.send(mimeMessage)
    }
}