package sosteam.deamhome.global.provider

import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class SendMailProvider(
    private val mailSender: JavaMailSender,
    @Value("\${spring.mail.username}") private val username: String
) {
    suspend fun sendEmail(to: String, subject: String, text: String) {
        val mimeMessage = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(mimeMessage, false, "UTF-8")

        try {
            helper.setFrom(username)
            helper.setTo(to)
            helper.setSubject(subject)
            helper.setText(text, false)

            mailSender.send(mimeMessage)
        } catch (e: Exception) {
            println(e.message)
        }
    }
}