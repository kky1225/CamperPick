package kr.spring.main.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.spring.reservation.service.ReservationService;

@Controller
public class MainController{
	
	@Autowired
	private ReservationService reservationService;
	
	@RequestMapping("/main/main.do")
	public String main() {
		return "main";//타일스 식별자
	}
	
	//알림 수
	@RequestMapping("/main/notificationCount.do")
	@ResponseBody
	public Map<String, String> processNotification(HttpSession session){
				
		Integer user_num = (Integer)session.getAttribute("user_num");
			
		Map<String,String> map = new HashMap<String,String>();
				
		if(user_num != 0) {
			int notificationCount = reservationService.getReserveNotificationCount(user_num);
			String count = Integer.toString(notificationCount);
					
			System.out.println("notificationCount : " + notificationCount);
					
			if(notificationCount <= 10 && notificationCount >= 1) {
				map.put("result", count);
			}else if(notificationCount == 0) {
				map.put("result", count);
			}
					
			return map;
		}
				
		return map;
	}
	
}
