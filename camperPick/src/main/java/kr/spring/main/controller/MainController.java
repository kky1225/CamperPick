package kr.spring.main.controller;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.scribejava.core.model.OAuth2AccessToken;

import kr.spring.member.service.MemberService;
import kr.spring.member.vo.MemberVO;
import kr.spring.naver.NaverLoginBO;

@Controller
public class MainController{
	
	@Autowired
	private MemberService memberService;
	
	private NaverLoginBO naverLoginBO;
	private String apiResult = null; 
	
	@Autowired private void setNaverLoginBO(NaverLoginBO naverLoginBO) {
		this.naverLoginBO = naverLoginBO; 
	} 
	
	//로그인 첫 화면 요청 메소드 
	@RequestMapping(value = "/main/main.do", method = { RequestMethod.GET, RequestMethod.POST }) 
	public String login(Model model, @RequestParam(value="code", required=false) String code, @RequestParam(value="state", required=false) String state, HttpSession session) throws IOException, ParseException { 
		
		if(model != null && code != null && state != null) {
			System.out.println("여기는 callback");
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
			System.out.println(email);
			String mobile = (String)response_obj.get("mobile");
			System.out.println(mobile); 
			String name = (String)response_obj.get("name");
			System.out.println(name); 
			
			
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

}
