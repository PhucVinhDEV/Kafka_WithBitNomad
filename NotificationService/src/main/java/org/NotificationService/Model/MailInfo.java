package org.NotificationService.Model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MailInfo {
    @NotBlank(message = "Vui lòng nhập Email gửi")
    @Email
    String from;

    @NotBlank(message = "Vui lòng nhập Email nhận")
    @Email
    String to;

    String[] cc;

    String[] bcc;

    @NotBlank(message = "Vui lòng nhập tiêu đề")
    String subject;

    @NotBlank(message = "Vui lòng nhập tin nhắn")
    String body;


    public MailInfo(String to, String subject, String body) {
        this.from = "artdevk18@gmail.com";
        this.to = to;
        this.subject = subject;
        this.body = body;
    }
}
