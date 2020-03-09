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

//	@Autowired
//	private RestTemplate restTemplate;
//	
//	/** クレジット決済のAPIURL */
//	private final String URL = "http://153.126.174.131:8080/sample-credit-card-web-api/credit-card/payment";

	/**
	 * 注文情報の更新を行う.
	 * 
	 * @param order 注文情報
	 */
	public void orderFinish(BuyOrderForm form) {
		Order order = new Order();
		BeanUtils.copyProperties(form, order);
		order.setUserId(form.getIntUserId());
		// String型の配達日付を取得
		String shippingDate = form.getDeliveryDate();
		// Integer型の配達時間を取得
		Integer shippingHour = Integer.parseInt(form.getDeliveryTime());
		// 配達日付をLocalDate型に変更
		LocalDate localDate = LocalDate.parse(shippingDate);
		// 配達時間をLocalTime型に変更（分,秒は0とする）
		LocalTime localTime = LocalTime.of(shippingHour, 0, 0);
		// 配達日付と時間を結合
		LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
		// Timestamp型に変更（DataBaseに合わせる）
		Timestamp timestamp = Timestamp.valueOf(localDateTime);
		order.setDeliveryTime(timestamp);

		// クレジット払いと現金払いで分岐
		if (form.getPaymentMethod().equals("credit")) {
			order.setPaymentMethod(1);
			order.setStatus(2);
		} else {
			order.setPaymentMethod(2);
			order.setStatus(1);
		}
		orderRepository.update(order);
		sendMail(order);
	}
	
//	/**
//	 * 配達日時をLocalDateTime型に変換する.
//	 * 
//	 * @param form BuyOrderForm
//	 * @return 配達日時(LocalDateTime型)
//	 */
//	public LocalDateTime toLocalDateTime(BuyOrderForm form) {
//		return localDateTime ;
//	}

	/**
	 * 注文確定後、メールを送信する.
	 */
	public void sendMail(Order order) {
		SimpleMailMessage mailmsg = new SimpleMailMessage();
		String mailText = mailText(order);
		mailmsg.setFrom(mailFrom);
		mailmsg.setTo(order.getDestinationEmail());// メールの宛先
		mailmsg.setSubject("ご注文品についての詳細");// タイトルの設定
		mailmsg.setText(mailText);
		mailSender.send(mailmsg);
	}

	/**
	 * 注文後に送られるメール内容.
	 * 
	 * @param order 注文情報
	 * @return メールテキスト
	 */
	public String mailText(Order order) {
		StringBuilder mailText = new StringBuilder();
		mailText.append(order.getDestinationName() + "様" + "\r\n" + "\r\n");
		mailText.append("郵便番号：" + order.getDestinationZipcode() + "\r\n");
		mailText.append("住所：" + order.getDestinationAddress() + "\r\n");
		mailText.append("電話番号：" + order.getDestinationTel() + "\r\n");
		mailText.append("配達日時：" + order.getDeliveryTime() + "\r\n");
		mailText.append("ーーーーーーーーーーーーーーーーーーーーーーー" + "\r\n");
		mailText.append("ご注文品："  + "\r\n");
		mailText.append("ご不明点等ございましたら、お手数ですが当アドレスまで返信の程よろしくお願い致します。");
		mailText.append("ご注文ありがとうございます。" + "\r\n");
		mailText.append("ご不明点等ございましたら、お手数ですが当アドレスまで返信の程よろしくお願い致します。");
		return mailText.toString();
	}
	
	

}
