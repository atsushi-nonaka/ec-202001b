package com.example.service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.domain.OrderTopping;
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
	
	@Autowired
	private HttpSession session;

	/**
	 * 注文情報の更新を行う.
	 * 
	 * @param order 注文情報
	 */
	public void orderFinish(BuyOrderForm form) {
		Order order = (Order) session.getAttribute("order");
		BeanUtils.copyProperties(form, order);
		// Timestamp型に変更（DataBaseに合わせる）
		Timestamp timestamp = Timestamp.valueOf(toLocalDateTime(form));
		//現時刻
		LocalDate nowDate = LocalDate.now();
		// クレジット払いと現金払いで分岐
		order.setUserId(form.getIntUserId());
		order.setDeliveryTime(timestamp);
		//LocalDate->Dateに変換しセット
		order.setOrderDate(Date.from(nowDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
		if (form.getPaymentMethod().equals("credit")) {
			order.setPaymentMethod(1);
			order.setStatus(2);
		} else {
			order.setPaymentMethod(2);
			order.setStatus(1);
		}
		orderRepository.update(order);
		sendMail(form);
	}
	
     /**
	 * 配達日時をLocalDateTime型に変換する.
	 * 
	 * @param form BuyOrderForm
	 * @return 配達日時(LocalDateTime型)
	 */
	public LocalDateTime toLocalDateTime(BuyOrderForm form) {
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
		return localDateTime ;
	}

	/**
	 * 注文確定後、メールを送信する.
	 */
	public void sendMail(BuyOrderForm form) {
		SimpleMailMessage mailmsg = new SimpleMailMessage();
		String mailText = mailText(form);
		mailmsg.setFrom(mailFrom);
		mailmsg.setTo(form.getDestinationEmail());// メールの宛先
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
	public String mailText(BuyOrderForm form) {
		StringBuilder mailText = new StringBuilder();
		Order order = (Order)session.getAttribute("order");
		mailText.append(order.getDestinationName() + "　様" + "\r\n" + "\r\n");
		mailText.append("郵便番号：" + order.getDestinationZipcode() + "\r\n");
		mailText.append("住所：" + order.getDestinationAddress() + "\r\n");
		mailText.append("電話番号：" + order.getDestinationTel() + "\r\n");
		mailText.append("配達日時：" + order.getDeliveryTime() + "\r\n");

		for(OrderItem orderItem : order.getOrderItemList()) {
			mailText.append("\r\n");
			mailText.append("ーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーー\r\n");
			mailText.append("商品名：" + orderItem.getItem().getName() + "\r\n個数：" + orderItem.getQuantity() + "\r\nサイズ：" + orderItem.getSize());	
			mailText.append("\r\n");
			if(form.getOrderToppingId() != null) {
				mailText.append("トッピング名：");
				for(OrderTopping orderTopping : orderItem.getOrderToppingList()) {
					mailText.append(orderTopping.getTopping().getName() + " ");								
				}
			}
		}
		mailText.append("\r\nーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーー");
		mailText.append("\r\n合計金額：" + order.getTotalPrice() + "円");
		return mailText.toString();
	}

	/**
	 * 	 * ユーザIDと状態から注文済みの情報を取得します. 
	 * 注文情報に含まれている、注文商品リスト、注文トッピングリストも取得します。
	 * statusは0以外を指定しています。
	 * 
	 * @param userId ユーザID
	 * @return 注文リスト
	 */
	public List<Order> findOrderHistory(Integer userId){
		return orderRepository.findOrderHistory(userId);
	}
}
