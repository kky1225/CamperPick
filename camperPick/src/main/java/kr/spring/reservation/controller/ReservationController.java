package kr.spring.reservation.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.spring.camping.service.CampingService;
import kr.spring.iamport.Iamport;
import kr.spring.payment.service.PaymentService;
import kr.spring.payment.vo.PaymentVO;
import kr.spring.reservation.service.ReservationService;
import kr.spring.reservation.vo.ReservationVO;
import kr.spring.reservation.vo.ReserveNotificationVO;
import kr.spring.room.service.RoomService;
import kr.spring.room.vo.RoomVO;
import kr.spring.util.PagingUtil;




@Controller
public class ReservationController {
	
	private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);
	
	private int rowCount=10;		//한 화면에 10개의 목록
	private int pageCount = 10;		//10개의 페이지 번호
	
	@Autowired
	private ReservationService reservationService;
	@Autowired
	private RoomService roomService;
	@Autowired
	private CampingService campingService;
	@Autowired
	private PaymentService paymentService;

	//자바빈 초기화
	@ModelAttribute					
	public ReservationVO initCommand() {
		return new ReservationVO();
	}
	
	//자바빈 초기화
	@ModelAttribute					
	public PaymentVO initCommand2() {
		return new PaymentVO();
	}
	
	//자바빈 초기화
	@ModelAttribute					
	public ReserveNotificationVO initCommand3() {
		return new ReserveNotificationVO();
	}
	
	//예약 등록 - 폼 호출
	@GetMapping("/reservation/reserve.do")
	public String reservationForm(@RequestParam int camping_num, @RequestParam int room_num, int mem_num, Model model) {
		
		model.addAttribute("camping_num", camping_num);
		model.addAttribute("room_num", room_num);
		model.addAttribute("mem_num",mem_num);
		
		return "insertReservation";
	}
	//예약 등록 - 처리
	@PostMapping("/reservation/reserve.do")
	public String submitReservation(@Valid ReservationVO reservationVO, BindingResult result, HttpSession session, Model model) {
		
		RoomVO room = roomService.getRoom(reservationVO.getRoom_num());
		
		if(result.hasErrors()) {
			return "insertReservation";
		}
		
		//예약 인원이 정원 넘는지 검사
		if(reservationVO.getHeadcount()-room.getPeople()>0) {	//예약인원이 정원수보다 클 때
			result.rejectValue("headcount", "InvalidHeadcount");
			return "insertReservation";
		}
		
		
		//날짜 선택 유효성 검사
		Date today = new Date();
		String format = new SimpleDateFormat("yyyy-MM-dd").format(today);
			// 입실날짜가 오늘 이전이면 반환
		if(getDay(reservationVO.getRes_start(),format)<=0) {
			logger.debug("<<입실>> : " + getDay(reservationVO.getRes_start(),format));
			result.rejectValue("res_start", "InvalidStart");
			return "insertReservation";
		}
			//퇴실 날짜가 오늘 이전이면 반환
		if(getDay(reservationVO.getRes_end(),format)<=0) {
			logger.debug("<<퇴실>> : " + getDay(reservationVO.getRes_end(),format));
			result.rejectValue("res_end", "InvalidEnd");
			return "insertReservation";
		}
			//퇴실 날짜가 오늘 이전이면 반환
		if(getDay(reservationVO.getRes_start(),reservationVO.getRes_end())>0) {
			logger.debug("<<퇴실입실차이>> : " + getDay(reservationVO.getRes_start(),reservationVO.getRes_end()));
			result.rejectValue("res_end", "InvalidReserveDay");
			return "insertReservation";
			}
		
		Integer user_num = (Integer)session.getAttribute("user_num");
		reservationVO.setMem_num(user_num);
		
		//결제가격 계산	
		
		int period = (int)getDay(reservationVO.getRes_end(),reservationVO.getRes_start());
		
		reservationVO.setRes_price(room.getPrice() * period);
		
		reservationVO.setRoom_name(roomService.getRoom(reservationVO.getRoom_num()).getRoom_name());
		reservationVO.setCamp_name(campingService.selectCamping(reservationVO.getCamping_num()).getCamp_name());
		
		logger.debug("<<예약 등록>> : " + reservationVO + "/" + period);
		
		//등록
		int res_num=reservationService.insertReservation(reservationVO);
		
		reservationVO.setRes_num(res_num);
		
		ReserveNotificationVO reserveNotificationVO = new ReserveNotificationVO();
		reserveNotificationVO.setMessage("[" + reservationVO.getCamp_name() + "] 예약이 완료되었습니다");
		reserveNotificationVO.setMem_num(user_num);
		reserveNotificationVO.setRes_num(res_num);
		
		int not_num=reservationService.insertReserveNotification(reserveNotificationVO);
		
		logger.debug("<<예약 알림 번호>> : " + not_num);
		
		logger.debug("<<예약 등록2>> : " + reservationVO );
		
		model.addAttribute("reservationVO", reservationVO);

		return "reserve";
	}
	
	//예약 검색 - 폼 호출
	@RequestMapping("/reservation/checkReservation.do")
	public String searchReservation() {
		
		return "reservationSearch";
	}

	//예약 검색 결과 -이름, 전화번호로 검색
	@RequestMapping("/reservation/getReservationList.do")
	public ModelAndView getReservation(@RequestParam(value="pageNum",defaultValue="1") int currentPage,@RequestParam String email,HttpSession session) {
		
		Map<String,Object> map = new HashMap<String, Object>();
		//count
		int count = reservationService.getReservationCount(email);
		
		//페이지
		//page
		PagingUtil page = new PagingUtil(currentPage,count, rowCount, pageCount,"getReservationList.do");
		map.put("start", page.getStartCount());
		map.put("end", page.getEndCount());
	
		map.put("email", email);
		logger.debug("<<예약검색>> : " + count);
		//list
		List<ReservationVO> list = null;
		if(count>0) {
			list = reservationService.getReservationList(map);
		}
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("reservationList");
		mav.addObject("count",count);
		mav.addObject("list",list);
		mav.addObject("pagingHtml", page.getPagingHtml());
		return mav;
	}
	
	//예약일 중간 확인
	@RequestMapping("/reservation/checkDate.do")
	@ResponseBody
	public Map<String,String> checkBetweenDate(@RequestParam int room_num, @RequestParam String res_start,@RequestParam String res_end){
		
		logger.debug("<<room_num>> : " + room_num);
		logger.debug("<<res_end>> : " + res_end);
		logger.debug("<<res_start>> : " + res_start);
		
		Map<String,String> ajaxmap = new HashMap<String,String>();
		
		int scount = 0;
		int ecount = 0;
		int bcount = 0;
		//객실번호로 예약 start 와 end 가져와서 선택한 날짜와 비교
		List<ReservationVO> list = reservationService.getReservationByRoom(room_num);		
		Date r_start;
	     Date r_end;
	     try {
	    	 r_start = new SimpleDateFormat("yyyy-MM-dd").parse(res_start);
	    	 r_end = new SimpleDateFormat("yyyy-MM-dd").parse(res_end);
	    	 
	    	 for(int i=0;i<list.size();i++) {
	    		 ReservationVO reservation = list.get(i);
	    		 Date get_start = new SimpleDateFormat("yyyy-MM-dd").parse(reservation.getRes_start());		//서버에서 가져온 start
	    		 Date get_end =  new SimpleDateFormat("yyyy-MM-dd").parse(reservation.getRes_end());		//서버에서 가져온 end
				 
	    		 logger.debug("<<db내용>> : " + get_start + "/" + get_end);
	    		 if(get_start.getTime() <= r_start.getTime() && r_start.getTime() <= get_end.getTime()) {
					 scount+=1;
				 }else if(get_start.getTime() <= r_end.getTime() && r_end.getTime() <= get_end.getTime()) {
					 ecount+=1;
				 }else if((get_start.getTime()>=r_start.getTime() && get_start.getTime()<=r_end.getTime()) ||
						 ( get_end.getTime()>=r_start.getTime() && get_end.getTime()<=r_end.getTime())) {
					 bcount+=1;
				 }
	    	 }
	    		logger.debug("<<count>> : " + scount + "/" + ecount + "/" + bcount);
	    	 if(scount>0) {//선택한 입실에 예약이 이미 존재
	    		 ajaxmap.put("result", "StartAlreadyReserved");
	    	 }else {
	    		 if(ecount>0) {//선택한 퇴실에 예약이 이미 존재
		    		 ajaxmap.put("result", "EndAlreadyReserved");
	    		 }else {
	    			 if(bcount>0) {//선택한 입실과 퇴실 사이에 예약이 이미 존재
	    				 ajaxmap.put("result", "BetweenAlreadyReserved");
	    			 }
	    		 }
	    	 
	    	 }
	     }catch(Exception e) {
	    	  e.printStackTrace();
	     }
		return ajaxmap;
	}
	
	//예약 상세
	@RequestMapping("/reservation/detailReservation.do")
	public String getReservation(@RequestParam int res_num, Model model) {
		
		reservationService.updateReserveNotfication(res_num);
		
		ReservationVO reservation = reservationService.getReservation(res_num);
		
		model.addAttribute("reservation", reservation);
		
		logger.debug("<<예약상세>> : " + reservation);
		
		return "reservationDetail";
	}

	//예약 수정 - 폼 호출
	@GetMapping("/reservation/updateReservation.do")
	public String updateReserve(@RequestParam int res_num, Model model) {
		
		ReservationVO reservationVO = reservationService.getReservation(res_num);
		
		model.addAttribute("reservationVO", reservationVO);
		
		return "updateReservation";
	}
	//예약 수정 - 처리
	@PostMapping("/reservation/updateReservation.do")
	public String submitUpdateReservation(@Valid ReservationVO reservationVO, BindingResult result,Model model, HttpServletRequest request) {
		RoomVO room = roomService.getRoom(reservationVO.getRoom_num());
		
		logger.debug("<<예약수정>> : " + reservationVO);
		if(result.hasErrors()) {
			return "updateReservation";
		}
		

		//예약 인원이 정원 넘는지 검사
		if(reservationVO.getHeadcount()-room.getPeople()>0) {	//예약인원이 정원수보다 클 때
			result.rejectValue("headcount", "InvalidHeadcount");
			return "updateReservation";
		}
		
		
		//날짜 선택 유효성 검사
		Date today = new Date();
		String format = new SimpleDateFormat("yyyy-MM-dd").format(today);
		if(getDay(reservationVO.getRes_start(),format)<=0) {
			logger.debug("<<날짜>> : " + getDay(reservationVO.getRes_start(),format));
			result.rejectValue("res_start", "InvalidStart");
			return "updateReservation";
		}
			//퇴실 날짜가 오늘 이전이면 반환
		if(getDay(reservationVO.getRes_end(),format)<=0) {
			logger.debug("<<날짜>> : " + getDay(reservationVO.getRes_end(),format));
			result.rejectValue("res_end", "InvalidEnd");
			return "updateReservation";
		}
			//퇴실 날짜가 오늘 이전이면 반환
		if(getDay(reservationVO.getRes_start(),reservationVO.getRes_end())>0) {
			logger.debug("<<날짜>> : " + getDay(reservationVO.getRes_start(),reservationVO.getRes_end()));
			result.rejectValue("res_end", "InvalidReserveDay");
			return "updateReservation";
			}
		
		//결제가격 계산	
		int period = (int)getDay(reservationVO.getRes_end(),reservationVO.getRes_start());
				
		reservationVO.setRes_price(room.getPrice() * period);
				
		logger.debug("<<예약 등록>> : " + reservationVO + "/" + period);
				
		//수정
		reservationService.updateReservation(reservationVO);
		
		//view에 메시지 표시
		model.addAttribute("message", "수정 완료");
		model.addAttribute("url", request.getContextPath() + "/reservation/checkReservation.do");
		return "common/resultView";	
		
		
		
	}
	
	//예약 취소 - 예약시 mem_num과 로그인 mem_num이 같은지 확인하고 삭제 진행
	@RequestMapping("/reservation/deleteReservation.do")
	public String deleteReserve(@RequestParam int res_num, String camp_name, Model model,HttpServletRequest request, HttpSession session) {
		
		logger.debug("<<예약취소>> : " + res_num);
		
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		reservationService.deleteReserveNotfication(res_num);
		
		reservationService.changeState2(res_num);
		
		ReserveNotificationVO reserveNotificationVO = new ReserveNotificationVO();
		reserveNotificationVO.setMessage("[" + camp_name + "] 예약 및 결제가 취소되었습니다");
		reserveNotificationVO.setMem_num(user_num);
		reserveNotificationVO.setRes_num(res_num);
		
		reservationService.insertReserveNotification(reserveNotificationVO);
		
		//view에 메시지 표시
		model.addAttribute("message", "취소 완료");
		model.addAttribute("url", request.getContextPath() + "/reservation/checkReservation.do");
		return "common/resultView";
	}
	
	//가장 최근 예약 불러오기	
	@RequestMapping("/reservation/myReservation.do")
	public String getMyReservation(@RequestParam int mem_num, Model model,HttpSession session) {
		
		ReservationVO reservation = reservationService.getRecentReservation(mem_num);
		
		model.addAttribute("reservation", reservation);
		model.addAttribute("user_email", (String)session.getAttribute("user_email"));
		
		logger.debug("<<예약상세>> : " + reservation);
		
		return "myReservationDetail";
	}
	
	
	//날짜 계산
	public long getDay(String date1, String date2) {

		long diffDays = 0;	 //일자수 차이
		try {
			Date res_day1 = new SimpleDateFormat("yyyy-MM-dd").parse(date1);
	        Date res_day2 = new SimpleDateFormat("yyyy-MM-dd").parse(date2);
	        
	        long diffSec = (res_day1.getTime() - res_day2.getTime()) / 1000; //초 차이
			diffDays = diffSec / (24*60*60);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return diffDays;
	}
	
	//결제
	@RequestMapping("/reservation/payment.do")
	@ResponseBody
	public Map<String, String> payment(@RequestBody PaymentVO paymentVO, BindingResult result) {
			
		System.out.println(paymentVO);
			
		Map<String,String> map = new HashMap<String,String>();
			
		paymentService.payment(paymentVO);
		reservationService.changeState(paymentVO.getRes_num());
			
		PaymentVO payment = paymentService.getPayment(paymentVO.getRes_num());
		
		ReserveNotificationVO reserveNotificationVO = new ReserveNotificationVO();
		reserveNotificationVO.setMessage("[" + paymentVO.getCamp_name() + "] 결제가 완료되었습니다");
		reserveNotificationVO.setMem_num(paymentVO.getMem_num());
		reserveNotificationVO.setRes_num(paymentVO.getRes_num());
		
		reservationService.insertReserveNotification(reserveNotificationVO);
			
		if(payment != null) {
			map.put("result", "success");
		}else {
			map.put("result", "fail");
		}
			
		return map;
	}
		
	//결제 취소
	@RequestMapping("/reservation/cancelPay.do")
	@ResponseBody
	public String cancelPay(@RequestParam int res_num, @RequestParam String biz_email) throws ParseException {
			
		Iamport iamport = new Iamport();
		String token = iamport.getToken();
			
		PaymentVO paymentVO = paymentService.getPayment(res_num);
		
		String pay = "0";
		
		if(paymentVO != null) {
			pay = Integer.toString(paymentVO.getAmount());
		}
			
		if(paymentVO == null) {
			return "Pay : 0";
		}else {
			String merchant_uid = paymentVO.getMerchant_uid();
				
			int check = iamport.cancelPayment(token, merchant_uid);
				
			  if(check == 1) { 
				  paymentService.cancelPayment(merchant_uid);
				  return "Pay : " + pay;
				 }else if(check == -1) { 
				  return "failure";
				 }else { 
				  return "error"; 
			  }
		}
			 
	}
	
}
