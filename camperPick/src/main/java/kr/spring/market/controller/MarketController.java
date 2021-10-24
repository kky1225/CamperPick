package kr.spring.market.controller;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import kr.spring.market.service.MarketService;
import kr.spring.market.vo.MarketReReplyVO;
import kr.spring.market.vo.MarketReplyVO;
import kr.spring.market.vo.MarketVO;
import kr.spring.util.PagingUtil;
import kr.spring.util.StringUtil;

// 거래게시판 관련 Controller
@Controller
public class MarketController {
	
	@Autowired
	private MarketService marketService;
	
	// 로그 처리(로그 대상 지정)
	private static final Logger log = LoggerFactory.getLogger(MarketController.class);
	
	private int rowCount = 20;
	private int pageCount = 10;
	
	// 자바빈 초기화
	@ModelAttribute
	public MarketVO initCommand() {
		return new MarketVO();
	}
	
	// 거래게시판 호출
	@RequestMapping("/market/marketList.do")
	public ModelAndView market(@RequestParam(value="pageNum",defaultValue = "1") int currentPage,
								@RequestParam(value="keyfield",defaultValue = "") String keyfield,
								@RequestParam(value="keyword",defaultValue = "1") String keyword) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("keyfield", keyfield);
		map.put("keyword", keyword);
		
		// 총 레코드 수
		int count = marketService.getMarketCount(map);
		
		log.debug("<<count>> : " + count);
		
		// 페이지 처리
		PagingUtil page = new PagingUtil(keyfield,keyword,currentPage, count, rowCount, pageCount, "marketList.do");
		
		map.put("start", page.getStartCount());
		map.put("end", page.getEndCount());
		
		// 목록 호출
		List<MarketVO> list = null;
		if(count > 0) {
			list = marketService.getMarketList(map);
		}
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("market");
		mav.addObject("count", count);
		mav.addObject("list", list);
		mav.addObject("pagingHtml", page.getPagingHtml());
		return mav;	// 타일스 식별자
	}
	
	// 거래게시판 - 글쓰기 폼 호출
	@GetMapping("/market/marketWrite.do")
	public String writeMarketForm() {
		return "marketWrite";
	}
	
	// 거래게시판 글쓰기 처리
	@PostMapping("/market/marketWrite.do")
	public String submitWrite(@Valid MarketVO marketVO, BindingResult result, HttpServletRequest request) {
		
		log.debug("<<거래게시판 작성>> : " + marketVO);
		
		// 유효성 체크 결과 오류가 있으면 폼 호출
		if(result.hasErrors()) {
			return writeMarketForm();
		}
		
		// ip 세팅
		marketVO.setIp(request.getRemoteAddr());
		
		// 글쓰기
		marketService.insertMarket(marketVO);
		
		return "redirect:/market/marketList.do";
	}
	
	// 거래게시판 글 상세
	@RequestMapping("/market/marketDetail.do")
	public String detail(@RequestParam int market_num, Model model) {
		
		// 해당 공지사항 조회수 증가
		marketService.updateHit(market_num);
		
		MarketVO market = marketService.getMarket(market_num);
		
		log.debug("<<거래게시판 글 상세>> : " + market);
		
		// HTML 태그 불허
		market.setTitle(StringUtil.useNoHtml(market.getTitle()));
		// HTML 태그 불허, 줄바꿈 허용
		//market.setContent(StringUtil.useBrNoHtml(market.getContent()));
		
		model.addAttribute("market", market);
		
		return "marketDetail";
	}
	
	// 이미지 출력
	@RequestMapping("/market/imageView.do")
	public ModelAndView viewImage(@RequestParam int market_num) {
			
		MarketVO market = marketService.getMarket(market_num);
			
		ModelAndView mav = new ModelAndView();
		mav.setViewName("imageView");
		//				속성명			속성값(byte[]의 데이터)
		mav.addObject("imageFile", market.getUploadfile());
		mav.addObject("filename", market.getFilename());
				
		return mav;
	}
	
	// 거래게시판 수정 폼 호출
	@GetMapping("/market/marketUpdate.do")
	public String updateMarketForm(@RequestParam int market_num, Model model) {
		MarketVO marketVO = marketService.getMarket(market_num);
		
		// 데이터 저장
		model.addAttribute("marketVO", marketVO);
		
		log.debug("<<거래게시판 수정>>" + marketVO);
		
		return "marketUpdate";
	}
	
	// 거래게시판 수정
	@PostMapping("/market/marketUpdate.do")
	public String submitUpdate(@Valid MarketVO marketVO, BindingResult result,HttpServletRequest request) {
		
		// 유효성 체크 결과 오류가 있으면 폼 호출
		if(result.hasErrors()) {
			return "marketUpdate";
		}
		
		// 회원번호,비밀번호 일치 여부 체크
		// DB에 저장된 회원번호 구하기
		MarketVO dbMarket = marketService.getMarket(marketVO.getMarket_num());

		log.debug("<<DB 저장된 회원>> : " + dbMarket);
		log.debug("<<회원이 입력한 비밀번호>> : " + marketVO);
		
		if(dbMarket.getMem_num() != marketVO.getMem_num()) {
			result.reject("invalidMem_num");
			return "marketUpdate";
		}
		
		if(!dbMarket.getPasswd().equals(marketVO.getPasswd())) {
			result.reject("invalidPassword");
			return "marketUpdate";
		}
		
		// ip 세팅
		marketVO.setIp(request.getRemoteAddr());
		
		marketService.updateMarket(marketVO);
		
		return "redirect:/market/marketList.do";
	}
	
	// 글 수정 - 파일 삭제
	@RequestMapping("/market/deleteFile.do")
	@ResponseBody
	public Map<String, String> processFile(int market_num,HttpSession session){
			
		Map<String, String> map = new HashMap<String, String>();
			
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {
			map.put("result", "logout");
		}else {
			marketService.deleteFile(market_num);
			map.put("result", "success");
		}
		return map;
	}
	
	// ckeditor를 이용한 이미지 업로드
	@RequestMapping("/market/imageUploader.do")
	@ResponseBody
	public Map<String, Object> uploadImage(MultipartFile upload, HttpSession session, HttpServletRequest request) throws Exception{
			
		// 업로드 할 절대 경로 구하기
		String realFolder = session.getServletContext().getRealPath("/resources/image_upload");
			
		// 업로드 한 파일 이름
		String org_filename = upload.getOriginalFilename();
		String str_filename = System.currentTimeMillis() + org_filename;
			
		log.debug("<<원본 파일명>> : " + org_filename);
		log.debug("<<저장할 파일명>> : " + str_filename);
			
		Integer user_num = (Integer)session.getAttribute("user_num");
			
		String filepath = realFolder + "\\" + user_num + "\\" + str_filename;
		log.debug("<<파일 경로>> : " + filepath);
			
		File f = new File(filepath);
		if(!f.exists()) {
			f.mkdirs();
		}
			
		upload.transferTo(f);
			
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uploaded", true);
		map.put("url", request.getContextPath()+"/resources/image_upload/"+user_num+"/"+str_filename);
			
		return map;
	}
	
	// 공지사항 삭제 폼 호출
		@GetMapping("/market/marketDelete.do")
		public String deleteMarketForm(@RequestParam int market_num,Model model) {
			MarketVO marketVO = new MarketVO();
			marketVO.setMarket_num(market_num);
				
			// 데이터 저장
			model.addAttribute("marketVO", marketVO);
				
			return "marketDelete";
		}
		
		@PostMapping("/market/marketDelete.do")
		public String submitDelete(@Valid MarketVO marketVO, BindingResult result) {
			
			// 회원번호,비밀번호 일치 여부
			// DB에 저장된 회원번호 구하기
			MarketVO dbMarket = marketService.getMarket(marketVO.getMarket_num());
			
			log.debug("<<DB 저장된 회원>> : " + dbMarket);
			
			if(dbMarket.getMem_num() != marketVO.getMem_num()) {
				result.reject("invalidMem_num");
				return "marketDelete";
			}
		
			if(!dbMarket.getPasswd().equals(marketVO.getPasswd())) {
				result.reject("invalidPassword");
				return "marketDelete";
			}
			
			marketService.deleteMarket(marketVO.getMarket_num());
			
			return "redirect:/market/marketList.do";
		}
	
		
		// ################댓글#####################
		
		// 댓글 등록(ajax)
		@RequestMapping("/market/writeReply.do")
		@ResponseBody
		public Map<String, String> writeReply(MarketReplyVO marketReplyVO,
											  HttpSession session,
											  HttpServletRequest request){
			log.debug("<<댓글 등록>> : " + marketReplyVO);
				
			Map<String, String> map = new HashMap<String, String>();
				
			Integer user_num = (Integer)session.getAttribute("user_num");
			if(user_num == null) {
				// 로그인 안 됨
				map.put("result", "logout");
			}else {
				// 로그인 됨
				// ip 등록
				marketReplyVO.setRe_ip(request.getRemoteAddr());
				// 댓글 등록
				marketService.insertReply(marketReplyVO);
				map.put("result", "success");
			}
				
			return map;
		}
			
		// 댓글 목록  
		@RequestMapping("/market/listReply.do")
		@ResponseBody
		public Map<String,Object> getList(
							@RequestParam(value="pageNum",defaultValue="1") int currentPage,
							@RequestParam int market_num){
				
			log.debug("<<currentPage>> : " + currentPage);
			log.debug("<<market_num>> : " + market_num);
				
			Map<String,Object> map = new HashMap<String, Object>();
				
			map.put("market_num", market_num);
				
			// 총 글의 개수
			int count = marketService.selectRowCountReply(map);
				
			PagingUtil page = new PagingUtil(currentPage, count, rowCount, pageCount, null);
			map.put("start", page.getStartCount());
			map.put("end", page.getEndCount());
				
			List<MarketReplyVO> list = null;
			if(count > 0) {
				list = marketService.selectListReply(map);
			}else {
				list = Collections.emptyList();
			}
				
			Map<String,Object> mapJson = new HashMap<String, Object>();
			mapJson.put("count", count);
			mapJson.put("rowCount",rowCount);
			mapJson.put("list", list);
				
			return mapJson;
		}
			
		// 댓글 삭제
		@RequestMapping("/market/deleteReply.do")
		@ResponseBody
		public Map<String, String> deleteReply(@RequestParam int mre_num,@RequestParam int mem_num,HttpSession session){
				
			log.debug("<<mre_num>> : " + mre_num);
			log.debug("<<mem_num>> : " + mem_num);
				
			Map<String,String> map = new HashMap<String, String>();
				
			Integer user_num = (Integer)session.getAttribute("user_num");
			if(user_num == null) {
				// 로그인이 되어있지 않음
				map.put("result", "logout");
			}else if(user_num != null && user_num == mem_num){
				// 로그인이 되어있고 로그인 한 아이디와 작성자 아이디가 일치
				marketService.deleteReply(mre_num);
				map.put("result", "success");
			}else {
				// 로그인 아이디와 작성자 아이디 불일치
				map.put("result", "wrongAccess");
			}
				
			return map;
		}
			
		// 댓글 수정
		@RequestMapping("/market/updateReply.do")
		@ResponseBody
		public Map<String, String> modifyReply(MarketReplyVO marketReplyVO, HttpSession session,HttpServletRequest request){
			log.debug("<<댓글 수정>> : " + marketReplyVO);

			Map<String, String> map = new HashMap<String, String>();
				
			Integer user_num = (Integer)session.getAttribute("user_num");
			if(user_num == null) {
				// 로그인이 안 된 경우
				map.put("result", "logout");
			}else if(user_num != null && user_num == marketReplyVO.getMem_num()) {
				// 로그인 회원 번호와 작성자 회원 번호 일치
				// ip 등록
				marketReplyVO.setRe_ip(request.getRemoteAddr());
					
				// 댓글 수정
				marketService.updateReply(marketReplyVO);
				map.put("result", "success");
			}else {
				// 로그인 회원 번호와 작성자 회원 번호 불일치
				map.put("result", "wrongAccess");
			}
				
			return map;
		}
		
		// #############대댓글################
		
		//대댓글 등록(ajax)
		@RequestMapping("/market/writeReReply.do")
		@ResponseBody
		public Map<String,String> writeReReply(MarketReReplyVO marketReReplyVO,
				                             HttpSession session,
				                             HttpServletRequest request){
			log.debug("<<대댓글 등록>> : " + marketReReplyVO);
			
			Map<String,String> map = new HashMap<String,String>();
			
			Integer user_num = (Integer)session.getAttribute("user_num");
			if(user_num == null) {
				//로그인 안 됨
				map.put("result", "logout");
			}else {
				//로그인 됨
				//ip 등록
				marketReReplyVO.setRe_ip(request.getRemoteAddr());
				//댓글 등록
				marketService.insertReReply(marketReReplyVO);
				map.put("result", "success");
			}
			
			return map;
		}



		// 대댓글 목록(ajax)
		@RequestMapping("/market/rereplyList.do")
		@ResponseBody
		public Map<String,Object> getreList(
				      @RequestParam(value="pageNum",defaultValue="1") int currentPage,
				      @RequestParam int mre_num)	{
			
		 						  
		 				  
			log.debug("<<currentPage>> : " + currentPage);
			log.debug("<<mre_num>> : " + mre_num);
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("mre_num", mre_num);
			//총 글의 갯수
			int count = marketService.getReReplyCount(map);
			
			PagingUtil page = new PagingUtil(currentPage,count,rowCount,pageCount,
					                 null);
			map.put("start", page.getStartCount());
			map.put("end", page.getEndCount());
			
			List<MarketReReplyVO> list = null;
			if(count > 0) {
				list = marketService.getReReplyList(map);
				
				log.debug("<<대댓글 목록>> : " + list);
				
			}else {
				list = Collections.emptyList();
			}
			
			Map<String,Object> mapJson = new HashMap<String,Object>();
			mapJson.put("count", count);
			mapJson.put("rowCount", rowCount);
			mapJson.put("list", list);
			
			return mapJson;
		}
		

		//대댓글 삭제
		@RequestMapping("/market/deleteReReply.do")
		@ResponseBody
		public Map<String,String> deleteReReply(@RequestParam int mrre_num,
				                              @RequestParam int mem_num,
				                              HttpSession session){
			
			log.debug("<<mrre_num>> : " + mrre_num);
			log.debug("<<mem_num>> : " + mem_num);
			
			Map<String,String> map = new HashMap<String,String>();
			
			Integer user_num = (Integer)session.getAttribute("user_num");
			if(user_num == null) {
				//로그인이 되어있지 않음
				map.put("result", "logout");
			}else if(user_num != null && user_num==mem_num) {
				//로그인이 되어 있고 로그인한 아이디와 작성자 아이디가 일치
				marketService.deleteReReply(mrre_num);
				map.put("result", "success");
			}else {
				//로그인 아이디와 작성자 아이디 불일치
				map.put("result", "wrongAccess");
			}
			return map;
		}
		
		//대댓글 수정
		@RequestMapping("/market/updateReReply.do")
		@ResponseBody
		public Map<String,String> modifyReReply(MarketReReplyVO marketReReplyVO, HttpSession session, HttpServletRequest request){
			
			log.debug("<<대댓글 수정>>:" + marketReReplyVO);
			
			Map<String,String> map=new HashMap<String,String>();
			
			Integer user_num=(Integer)session.getAttribute("user_num");
			if(user_num==null) {
				//로그인이 안된 경우
				map.put("result", "logout");
			}else if(user_num!=null && user_num == marketReReplyVO.getMem_num()) {
				//로그인 회원번호와 작성자 회원번호 일치
				//ip 등록
				marketReReplyVO.setRe_ip(request.getRemoteAddr());
				
				//대댓글 수정
				marketService.updateReReply(marketReReplyVO);
				map.put("result", "success");
			}else {
				//로그인회원번호와 작성자 회원번호 불일치
				map.put("result", "wrongAccess");
				
			}
			
			return map;
		}	
}








































