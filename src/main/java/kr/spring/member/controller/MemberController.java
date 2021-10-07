package kr.spring.member.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.spring.member.service.MemberService;
import kr.spring.member.vo.MemberVO;
import kr.spring.util.AuthCheckException;

@Controller
public class MemberController {
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	private MemberService memberService;
	
	@ModelAttribute
	public MemberVO initCommand() {
		return new MemberVO();
	}
	
	@RequestMapping("/member/confirmId.do")
	@ResponseBody
	public Map<String, String> process(@RequestParam String id){
		
		logger.debug("<<id>> : " + id);
		
		Map<String, String> map = new HashMap<String, String>();
		
		MemberVO memberVO = memberService.selectCheckMember(id);
		if(memberVO != null) {
			map.put("result", "idDuplicated");
		}else {
			if(!Pattern.matches("^[A-Za-z0-9]{4,12}$", id)) {
				map.put("result", "idMatchPattern");
			}else {
				map.put("result", "idNotFound");
			}
		}
		
		return map;
	}
	
	@GetMapping("/member/registerUser.do")
	public String form() {
		
		logger.debug("<<회원가입 폼 호출>>");
		
		return "memberRegister";
	}
	
	@PostMapping("/member/registerUser.do")
	public String submit(@Valid MemberVO memberVO, BindingResult result) {
		
		logger.debug("<<회원정보>> : " + memberVO);
		
		if(result.hasErrors()) {
			return form();
		}
		
		memberService.insertMember(memberVO);
		
		return "redirect:/main/main.do";
	}
	
	@GetMapping("/member/login.do")
	public String formLogin() {
		return "memberLogin";
	}
	
	@PostMapping("/member/login.do")
	public String submitLogin(@Valid MemberVO memberVO, BindingResult result, HttpSession session) throws AuthCheckException {
		
		logger.debug("<<로그인>> : " + memberVO);
		
		if(result.hasFieldErrors("id") || result.hasFieldErrors("passwd")) {
			return formLogin();
		}
		
		try {
			MemberVO member = memberService.selectCheckMember(memberVO.getId());
			boolean check = false;
			
			if(member != null) {
				check = member.isCheckedPassword(memberVO.getPasswd());
			}
			
			if(check) {
				session.setAttribute("user_num", member.getMem_num());
				session.setAttribute("user_id", member.getId());
				session.setAttribute("user_auth", member.getAuth());
				
				return "redirect:/main/main.do";
			}else {
				throw new AuthCheckException();
			}
		}catch(AuthCheckException e) {
			result.reject("invalidIdOrPassword");
			return formLogin();
		}
	}
	
	@RequestMapping("/member/logout.do")
	public String processLogout(HttpSession session) {
		
		session.invalidate();
		
		return "redirect:/main/main.do";
	}
}
