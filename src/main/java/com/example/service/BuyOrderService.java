package com.example.service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Order;
import com.example.form.BuyOrderForm;
import com.example.repository.OrderRepository;

/**
 * 注文情報を操作するサービス.
 * 
 * @author nonaka
 *
 */
@Service
@Transactional
public class BuyOrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Value("${spring.mail.username}")
	private String mailFrom;
	
	@Autowired
	private MailSender mailSender;
	
	/**
	 * 注文情報の更新を行う.
	 * 
	 * @param order 注文情報
	 */
	public void orderFinish(BuyOrderForm form) {
		Order order = new Order();
		BeanUtils.copyProperties(form, order);
		//String型の配達日付を取得
		String shippingDate = form.getDeliveryDate();
		//Integer型の配達時間を取得
		Integer shippingHour = Integer.parseInt(form.getDeliveryTime());
		//配達日付をLocalDate型に変更
		LocalDate localDate = LocalDate.parse(shippingDate);
		//配達時間をLocalTime型に変更（分,秒は0とする）
		LocalTime localTime = LocalTime.of(shippingHour, 0, 0);
		//配達日付と時間を結合
		LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
		//Timestamp型に変更（DataBaseに合わせる）
		Timestamp timestamp = Timestamp.valueOf(localDateTime);
		order.setDeliveryTime(timestamp);
		
		//クレジット払いと現金払いで分岐
		if(form.getPaymentMethod().equals("credit")) {
			order.setPaymentMethod(1);	
			order.setStatus(2);
		}else {
			order.setPaymentMethod(2);
			order.setStatus(1);
		}

		orderRepository.update(order);
		sendMail();
	}
	
	/**
	 * 注文確定後、メールを送信する.
	 */
	public void sendMail(){
		SimpleMailMessage mailmsg = new SimpleMailMessage();    
	    mailmsg.setFrom(mailFrom);
	    mailmsg.setTo("test@test.co.jp");//メールの宛先
	    mailmsg.setSubject("テストメール");//タイトルの設定
	    mailmsg.setText("Spring Boot より本文送信"); //本文の設定
	    mailSender.send(mailmsg);
	}
}
