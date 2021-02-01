package com.bytecode.mail;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import java.io.File;
import java.util.Date;
import java.util.UUID;

@SpringBootApplication
public class App implements CommandLineRunner {
	private Log logger = LogFactory.getLog(getClass());
	@Autowired
	private JavaMailSender emailSender;

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		fileTextMessage();
		simpleTextMessage();
		logger.info("El correo se envio de manera exitosa!!");
	}

	public void simpleTextMessage(){
		String bodyMessage = "Este es un ejemplo de correo ID=" + UUID.randomUUID().toString();

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo("davidbriones1012@outlook.com");
		message.setFrom("aprendefacil1020@gmail.com");
		message.setSubject("Mensaje de Correo Electronico");
		message.setText(bodyMessage);

		emailSender.send(message);
	}

	public void fileTextMessage() throws MessagingException {
		String bodyMessage = "Este es un ejemplo de correo ID=" + UUID.randomUUID().toString();
		MimeMessage mimeMessage = emailSender.createMimeMessage();
		MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");

		message.setTo("davidbriones1012@outlook.com");
		message.setFrom("aprendefacil1020@gmail.com");
		message.addAttachment("image.jpg", new File("E:\\Stream\\spring-boot-mail\\image.jpg"));
		message.setSubject("Mensaje de Correo Electronico");
		message.setSentDate(new Date());
		message.setText("<h1>" + bodyMessage + "</h1>", true);

		this.emailSender.send(mimeMessage);
	}
}
