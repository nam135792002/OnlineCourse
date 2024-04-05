package com.springboot.courses.utils;

import com.springboot.courses.entity.User;
import com.springboot.courses.exception.BlogApiException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class Utils {
    public static String removeVietnameseAccents(String input) {
        String result = input;

        result = result.replaceAll("[àáạảãâầấậẩẫăằắặẳẵ]", "a");
        result = result.replaceAll("[èéẹẻẽêềếệểễ]", "e");
        result = result.replaceAll("[ìíịỉĩ]", "i");
        result = result.replaceAll("[òóọỏõôồốộổỗơờớợởỡ]", "o");
        result = result.replaceAll("[ùúụủũưừứựửữ]", "u");
        result = result.replaceAll("[ỳýỵỷỹ]", "y");
        result = result.replaceAll("đ", "d");

        String slug = result.trim().toLowerCase();

        slug = slug.replaceAll("\\s+", "-");

        return slug;
    }

    public static String getEmailOfAuthenticatedCustomer(HttpServletRequest request){
        Object principal = request.getUserPrincipal();

        if(principal == null)
            return null;
        else
            return request.getUserPrincipal().getName();
    }

    public static void sendEmail(String url, String subject, String content, User user){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("tech.courses.895@gmail.com");
        mailSender.setPassword("giug qtcr yjag occm");
        mailSender.setDefaultEncoding("utf-8");

        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth","true");
        properties.setProperty("mail.smtp.starttls.enable","true");
        mailSender.setJavaMailProperties(properties);

        String toAddress = user.getEmail();

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setFrom("tech.courses.895@gmail.com","Tech Courses");
            helper.setTo(toAddress);
            helper.setSubject(subject);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Send email failed!");
        }

        content = content.replace("[[name]]",user.getFullName());


        content = content.replace("[[URL]]",url);
        try {
            helper.setText(content,true);
        } catch (MessagingException e) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Send email failed!");
        }
        mailSender.send(message);
    }
}
