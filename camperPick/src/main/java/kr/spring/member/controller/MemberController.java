package kr.spring.member.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.scribejava.core.model.OAuth2AccessToken;

import kr.spring.member.service.MemberService;
import kr.spring.member.vo.MemberVO;
import kr.spring.naver.NaverLoginBO;
import kr.spring.reservation.service.ReservationService;
import kr.spring.reservation.vo.ReserveNotificationVO;
import kr.spring.util.AuthCheckException;
import kr.spring.util.PagingUtil;

@Controller
public class MemberController {

	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	private MemberService memberService;
	@Autowired
	private ReservationService reservationService;
	
	private NaverLoginBO naverLoginBO;
	private String apiResult = null; 
	
	private int rowCount = 20;
	private int pageCount = 10;
	
	@Autowired 
	private void setNaverLoginBO(NaverLoginBO naverLoginBO) {
		this.naverLoginBO = naverLoginBO; 
	} 
	
	//자바빈(VO)초기화
	@ModelAttribute
	public MemberVO initCommand() {
		return new MemberVO();
	}
	
	//회원가입 - 아이디 중복 체크(Ajax)
	@RequestMapping("/member/confirmEmail.do")
	@ResponseBody
	public Map<String,String> processId(@RequestParam String email){
		
		logger.debug("<<email>> : " + email);
		
		Map<String,String> map = new HashMap<String,String>();
		
		MemberVO member = memberService.selectCheckMember(email);
		if(member!=null) {
			//아이디 중복
			map.put("result", "idDuplicated");
		}else {
			if(!Pattern.matches("\\w+@\\w+\\.\\w+(\\.\\w+)?", email)) {
				//패턴 불일치
				map.put("result", "notMatchPattern");
			}else{
				//아이디 미중복
				map.put("result", "idNotFound");
			}
		}
		
		return map;
	}
	
	//회원가입 - 비밀번호 체크(Ajax)
	@RequestMapping("/member/confirmPasswd.do")
	@ResponseBody
	public Map<String,String> processPasswd(@RequestParam String passwd){
			
		logger.debug("<<passwd>> : " + passwd);
			
		Map<String,String> map = new HashMap<String,String>();

		if(!Pattern.matches("^[A-Za-z0-9]{4,12}$", passwd)) {
			//패턴 불일치
			map.put("result", "notMatchPattern");
		}else {
			map.put("result", "good");
		}
		return map;
	}
	
	//회원가입 - 전화번호 체크(Ajax)
		@RequestMapping("/member/confirmPhone.do")
		@ResponseBody
		public Map<String,String> processPhone(@RequestParam String phone){
				
			logger.debug("<<phone>> : " + phone);
				
			Map<String,String> map = new HashMap<String,String>();

			MemberVO member = memberService.selectCheckMember2(phone);
			
			if(member != null) {
				map.put("result", "phoneDuplicated");
			}else {
				if(!Pattern.matches("^\\d{3}-\\d{3,4}-\\d{4}$", phone)) {
					//패턴 불일치
					map.put("result", "notMatchPattern");
				}else{
					//이메일 미중복
					map.put("result", "good");
				}
			}
			return map;
		}

	
	//회원가입 - 회원가입 폼 호출
	@GetMapping("/member/registerUser.do")
	public String form() {
		
		logger.debug("<<회원가입 폼 호출>>");
		
		return "memberRegister";
	}
	
	//회원가입 - 회원가입 처리
	@PostMapping("/member/registerUser.do")
	public String submit(@Valid MemberVO memberVO, BindingResult result) {
		
		logger.debug("<<회원 정보>> : " + memberVO);
		
		//유효성 검사 결과 오류가 있으면 폼 호출
		if(result.hasErrors()) {
			return form();
		}
		
		memberVO.setAuth(2);
		
		//회원가입
		memberService.insertMember(memberVO);
		
		return "redirect:/main/main.do";
	}
	
	//로그인 - 로그인 폼 호출
	@GetMapping("/member/login.do")
	public String formLogin(Model model, HttpSession session) {
		/* 네이버아이디로 인증 URL을 생성하기 위하여 naverLoginBO클래스의 getAuthorizationUrl메소드 호출 */ 
		String naverAuthUrl = naverLoginBO.getAuthorizationUrl(session); 
		
		//https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=sE***************& 
		//redirect_uri=http%3A%2F%2F211.63.89.90%3A8090%2Flogin_project%2Fcallback&state=e68c269c-5ba9-4c31-85da-54c16c658125 
		System.out.println("네이버:" + naverAuthUrl); 
		//네이버 
		model.addAttribute("url", naverAuthUrl); 
		
		return "memberLogin";//타일스 식별자
	}
	
	//로그인 첫 화면 요청 메소드 
		@RequestMapping(value = "/member/naverLogin.do", method = { RequestMethod.GET, RequestMethod.POST }) 
		public String login(Model model, @RequestParam(value="code", required=false) String code, @RequestParam(value="state", required=false) String state, HttpSession session) throws IOException, ParseException { 
			
			if(model != null && code != null && state != null) {
				OAuth2AccessToken oauthToken; 
				oauthToken = naverLoginBO.getAccessToken(session, code, state); 
				
				//1. 로그인 사용자 정보를 읽어온다. 
				apiResult = naverLoginBO.getUserProfile(oauthToken); 
				//String형식의 json데이터 
				/** apiResult json 구조 {"resultcode":"00", "message":"success", "response":{"id":"33666449","nickname":"shinn****","age":"20-29","gender":"M","email":"sh@naver.com","name":"\uc2e0\ubc94\ud638"}} **/ 
				//2. String형식인 apiResult를 json형태로 바꿈
				JSONParser parser = new JSONParser(); 
				Object obj = parser.parse(apiResult); 
				JSONObject jsonObj = (JSONObject) obj; 
				
				//3. 데이터 파싱 
				//Top레벨 단계 _response 파싱 
				JSONObject response_obj = (JSONObject)jsonObj.get("response"); 
				
				//response의 nickname값 파싱 
				String email = (String)response_obj.get("email");
				String mobile = (String)response_obj.get("mobile");
				String name = (String)response_obj.get("name");
				
				MemberVO memberVO = memberService.selectCheckMember(email);
				  
				if(memberVO == null) { 
					memberVO = new MemberVO(); 
					memberVO.setAuth(3);
					memberVO.setEmail(email);
					memberVO.setPhone(mobile);
					memberVO.setName(name);
					memberVO.setPasswd("naverlogin");
				  
					//회원가입 
					memberService.insertMember(memberVO);
				  
					MemberVO member = memberService.selectCheckMember(email);
				  
					//4.파싱 닉네임 세션으로 저장 
					session.setAttribute("user_email", member.getEmail());
					session.setAttribute("user_num", member.getMem_num());
					session.setAttribute("user_auth", member.getAuth()); 
				}else { //4.파싱 닉네임 세션으로
				  session.setAttribute("user_email", memberVO.getEmail());
				  session.setAttribute("user_num", memberVO.getMem_num());
				  session.setAttribute("user_auth", memberVO.getAuth()); 
				}

				//세션 생성 
				model.addAttribute("result", apiResult);
				
			}
			
			return "main"; 
		}
	
	//로그인 - 로그인 데이터 처리
	@PostMapping("/member/login.do")
	public String submitLogin(@Valid MemberVO memberVO,BindingResult result, 
			                  HttpSession session) {
		
		logger.debug("<<회원 로그인>> : " + memberVO);
		
		//로그인 체크(id,비밀번호 일치 여부 체크)
		try {
			MemberVO member = memberService.selectCheckMember(memberVO.getEmail());
			boolean check = false;
			
			if(member!=null) {//아이디 일치
				//비밀번호 일치 여부 체크               사용자가 입력한 비밀번호
				check = member.isCheckedPassword(memberVO.getPasswd());
			}
			if(check) {
				//인증 성공, 로그인 처리
				session.setAttribute("user_num", member.getMem_num());
				session.setAttribute("user_email", member.getEmail());
				session.setAttribute("user_auth", member.getAuth());
				
				return "redirect:/main/main.do";
			}else {
				//인증 실패
				throw new AuthCheckException();
			}
		}catch(AuthCheckException e) {
			//인증 실패로 메시지 생성 및 로그인 폼 호출
			result.reject("invalidIdOrPassword");
			
			return "memberLogin";
		}
	}
	
	//로그아웃
	@RequestMapping("/member/logout.do")
	public String processLogout(HttpSession session) {
		//로그아웃
		session.invalidate();
		
		return "redirect:/main/main.do";
	}
	
	// 회원 상세 정보
		@RequestMapping("/member/myPage.do")
		public String process(HttpSession session, Model model) {
			Integer user_num = (Integer)session.getAttribute("user_num");
			
			MemberVO member = memberService.selectMember(user_num);
			
			logger.debug("<<회원상세정보>> : " + member);
			
			model.addAttribute("member", member);
			
			return "memberView";
		}
		
		// 회원정보 수정 - 수정 폼
		@GetMapping("/member/update.do")
		public String formUpdate(HttpSession session, Model model) {
			Integer user_num = (Integer)session.getAttribute("user_num");
			
			MemberVO memberVO = memberService.selectMember(user_num);
			
			model.addAttribute("memberVO", memberVO);
			
			return "memberModify";
		}
		
		// 회원정보 수정 - 수정 데이터 처리
		@PostMapping("/member/update.do")
		public String submitUpdate(@Valid MemberVO memberVO, BindingResult result, HttpSession session) {
			
			logger.debug("<<회원정보 수정>> : " + memberVO);
			
			if(result.hasFieldErrors("name") || result.hasFieldErrors("phone")) {
				
				return "memberModify";
			}
			
			Integer user_num = (Integer)session.getAttribute("user_num");
			memberVO.setMem_num(user_num);
			
			memberService.updateMember(memberVO);
			
			return "redirect:/member/myPage.do";
		}
		
		// 회원정보 주소 수정 - 수정 폼
		@GetMapping("/member/updateAddress.do")
		public String formUpdateAddress(HttpSession session, Model model) {
			Integer user_num = (Integer)session.getAttribute("user_num");
			
			MemberVO memberVO = memberService.selectMember(user_num);
			
			model.addAttribute("memberVO", memberVO);
			
			return "memberAddress";
		}

		// 회원정보 주소 수정 - 수정 데이터 처리
		@PostMapping("/member/updateAddress.do")
		public String submitUpdateAddress(@Valid MemberVO memberVO, BindingResult result, HttpSession session) {
			
			logger.debug("<<회원 주소 수정>> : " + memberVO);
			
			if(result.hasFieldErrors("zipcode") || result.hasFieldErrors("address1") || result.hasFieldErrors("address2")) {
				
				return "memberAddress";
			}
			
			Integer user_num = (Integer)session.getAttribute("user_num");
			memberVO.setMem_num(user_num);
			
			memberService.updateAddress(memberVO);
			
			return "redirect:/member/myPage.do";
		}
		
		// 비밀번호 수정 - 폼 호출
		@GetMapping("/member/changePassword.do")
		public String formChangePassword() {
			
			return "memberChangePassword";
		}
		
		// 비밀번호 수정 - 수정 데이터 처리
		@PostMapping("/member/changePassword.do")
		public String submitChangePassword(@Valid MemberVO memberVO, BindingResult result, HttpSession session) {
			
			logger.debug("<<비밀번호 수정>> : " + memberVO);
			
			if(result.hasFieldErrors("now_passwd") || result.hasFieldErrors("passwd")) {
				
				return formChangePassword();
			}
			
			Integer user_num = (Integer)session.getAttribute("user_num");
			MemberVO member = memberService.selectMember(user_num);
			
			if(!member.getPasswd().equals(memberVO.getNow_passwd())) {
				// 비밀번호 불일치
				result.rejectValue("now_passwd", "invalidPassword");
				
				return formChangePassword();
			}
			
			memberVO.setMem_num(user_num);
			memberService.updatepassword(memberVO);
			
			return "redirect:/member/myPage.do";
		}
		
		// 회원탈퇴 - 폼 호출
		@GetMapping("/member/delete.do")
		public String formDelete() {
			
			return "memberDelete";
		}
		
		// 회원탈퇴 - 데이터 처리
		@PostMapping("/member/delete.do")
		public String submitDelete(@Valid MemberVO memberVO, BindingResult result, HttpSession session) {
			
			logger.debug("<<회원탈퇴>> : " + memberVO);
			
			if(result.hasFieldErrors("email") || result.hasFieldErrors("passwd")) {
				
				return formDelete();
			}
			
			String user_email = (String)session.getAttribute("user_email");
			
			MemberVO dbMember = memberService.selectCheckMember(memberVO.getEmail());
			boolean check = false;
			
			// 사용자가 입력한 아이디가 db에 저장되어 있고, 세션에 저장된 id와 일치함
			if(dbMember != null && dbMember.getEmail().equals(user_email)) {
				// 비밀번호 일치 여부 체크
				check = dbMember.isCheckedPassword(memberVO.getPasswd());
			}
			if(check) {
				// 인증 성공, 회원정보 삭제
				memberVO.setMem_num((Integer)session.getAttribute("user_num"));
				memberService.deleteMember(memberVO.getMem_num());
				// 로그아웃
				session.invalidate();
				
				return "redirect:/main/main.do";
			}else {
				// 인증 실패
				result.reject("invalidIdOrPassword");
				
				return formDelete();
			}
			
		}
		
		// 예약정보
		
		// 알림 정보
		@RequestMapping("/member/notification.do")
		public ModelAndView notification(HttpSession session) {
					
			List<ReserveNotificationVO> list = null;
			
			Integer user_num = (Integer)session.getAttribute("user_num");
					
			list = reservationService.getReserveNotificationList(user_num);
						
			ModelAndView mav = new ModelAndView();
			mav.setViewName("notification");	// 타일스 식별자
			mav.addObject("list", list);
					
			return mav;	
		}
		
	// 회원 정보 리스트
	@RequestMapping("/member/managerPage.do")
	public ModelAndView managerView(@RequestParam(value="pageNum",defaultValue = "1") int currentPage,
							@RequestParam(value="keyfield",defaultValue = "") String keyfield,
							@RequestParam(value="keyword",defaultValue = "1") String keyword) {
				
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("keyfield", keyfield);
		map.put("keyword", keyword);
				
		// 총 레코드 수
		int count = memberService.getMemberCount(map);
				
		// 페이지 처리
		PagingUtil page = new PagingUtil(keyfield,keyword,currentPage, count, rowCount, pageCount, "managerPage.do");
				
		map.put("start", page.getStartCount());
		map.put("end", page.getEndCount());
				
		// 목록 호출
		List<MemberVO> list = null;
		if(count > 0) {
			list = memberService.getMemberList(map);
		}
				
		ModelAndView mav = new ModelAndView();
		mav.setViewName("managerView");
		mav.addObject("count", count);
		mav.addObject("list", list);
		mav.addObject("pagingHtml", page.getPagingHtml());
				
		return mav;	// 타일스 식별자
	}
				
	// 회원 등급 수정 폼 호출
	@GetMapping("/member/memberAuthUpdate.do")
	public String updateAuthForm(@RequestParam int mem_num, Model model) {
			
		MemberVO memberVO = memberService.getMember(mem_num);
				
		// 데이터 저장
		model.addAttribute("memberVO", memberVO);
			
		return "memberAuthUpdate";
	}
		
		
	// 회원 등급 수정
	@PostMapping("/member/memberAuthUpdate.do") 
	public String updateAuth(@Valid MemberVO memberVO, BindingResult result, HttpServletRequest request) {
			  
		memberService.updateAuth(memberVO);
			  
		return "redirect:/member/managerPage.do"; 
		  
	}
	
}











