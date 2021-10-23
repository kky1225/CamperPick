package kr.spring.camping.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

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
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import kr.spring.camping.service.CampingService;
import kr.spring.camping.vo.CampingVO;
import kr.spring.room.service.RoomService;
import kr.spring.room.vo.RoomVO;
import kr.spring.util.PagingUtil;


@Controller
public class CampingController {
	
	private static final Logger logger = LoggerFactory.getLogger(CampingController.class);

	private int rowCount=10;		//한 화면에 10개의 목록
	private int pageCount = 10;		//10개의 페이지 번호
	
	@Autowired
	private CampingService campingService;
	@Autowired
	private RoomService roomService;
	
	//자바빈 초기화
	@ModelAttribute					
	public CampingVO initCommand() {
		return new CampingVO();
	}
	
	//검색 페이지
	@RequestMapping("/camping/search.do")
	public String searchCamping() {
		return "searchCamping";
	}
	//캠핑장 검색 - 검색한 캠핑장 list가져오기
	@RequestMapping("/camping/list.do")
	public ModelAndView getList(@RequestParam(value="pageNum",defaultValue="1") int currentPage, @RequestParam(value="keyfield",defaultValue="2")String keyfield, @RequestParam(value="keyword",defaultValue="") String keyword) {
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("keyword", keyword);
		map.put("keyfield", keyfield);
		//count
		int count = campingService.selectRowCount(map);
		//page
		PagingUtil page = new PagingUtil(keyfield,keyword,currentPage,count, rowCount, pageCount,"list.do");
		map.put("start", page.getStartCount());
		map.put("end", page.getEndCount());
		//list
		List<CampingVO> list = null;
		if(count>0) {
			list = campingService.selectList(map);
		}
		
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("campingList");
		mav.addObject("count",count);
		mav.addObject("list",list);
		mav.addObject("pagingHtml",page.getPagingHtml());
		
		
		return mav;
	}
	
	//캠핑장 상세 페이지
	@RequestMapping("/camping/detail.do")
	public ModelAndView detailCamping(@RequestParam int camping_num, @RequestParam(value="pageNum",defaultValue="1") int currentPage) {
		
		CampingVO camping = campingService.selectCamping(camping_num);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("detailCamping");
		mav.addObject("camping", camping);
		
		Map<String,Object> map = new HashMap<String, Object>();
		//객실 갯수
		int count = roomService.getRoomCount(camping_num);
		//객실 페이지
		PagingUtil page = new PagingUtil(currentPage,count, rowCount, pageCount,"detail.do");
		map.put("start", page.getStartCount());
		map.put("end", page.getEndCount());
		//객실 리스트
		map.put("camping_num", camping_num);
		List<RoomVO> list = null;
		if(count>0) {
			list = roomService.getRoomList(map);
		}
		mav.addObject("roomList", list);
		mav.addObject("roomCount",count);
		mav.addObject("pagingHtml", page.getPagingHtml());
		
		return mav;
	}
	//캠핑장 사진
		@RequestMapping("/camping/imageView.do")
		public ModelAndView viewImage(@RequestParam int camping_num) {
			
			CampingVO camping = campingService.selectCamping(camping_num);
			
			ModelAndView mav = new ModelAndView();
			
			mav.setViewName("imageView");
							//속성명			속성값(byte[] 타입의 데이터)
			mav.addObject("imageFile",camping.getUploadfile());
			mav.addObject("filename", camping.getFilename());
			
			return mav;
		}
	
	
	//캠핑장 등록 - 폼 호출
	@GetMapping("/camping/write.do")
	public String writeForm() {
		return "campingWrite";		//타일스 식별자
	}
	//캠핑장 등록 - 처리
	@PostMapping("/camping/write.do")
	public String submit(@Valid CampingVO campingVO, BindingResult result, Model model, HttpServletRequest request) {
		
		if(result.hasErrors()) {
			return writeForm();
		}
		//등록
		campingService.insertCamping(campingVO);
		
		//view에 메시지 표시
		model.addAttribute("message", "등록 완료");
		model.addAttribute("url", request.getContextPath() + "/camping/list.do");
		return "common/resultView";		
	}

	//캠핑장 수정 - 폼 호출
	@GetMapping("/camping/update.do")
	public String updateForm(@RequestParam int camping_num, Model model) {

		CampingVO campingVO = campingService.selectCamping(camping_num);
		
		model.addAttribute("campingVO",campingVO);
		
		return "campingUpdate";
	}
	//캠핑장 수정 - 전송
	@PostMapping("/camping/update.do")
	public String submitUpdate(@Valid CampingVO campingVO, BindingResult result,Model model, HttpServletRequest request) {
		
		logger.debug("<<글 수정>> : " + campingVO);
		
		if(result.hasErrors()) {
			return "campingUpdate";
		}
		
		campingService.updateCamping(campingVO);
		
		//view에 메시지 표시
		model.addAttribute("message", "수정 완료");
		model.addAttribute("url", request.getContextPath() + "/camping/list.do");
		return "common/resultView";	
	}
	
	//캠핑장 수정 - 파일 삭제
	@RequestMapping("/camping/deleteFile.do")
	@ResponseBody
	public Map<String, String> removeFile(int camping_num, HttpSession session){
		
		Map<String,String> map = new HashMap<String, String>();
		
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) {
			map.put("result", "logout");
		}else {
			campingService.deleteFile(camping_num);
			map.put("result", "success");
		}
		
		return map;
	}
	//캠핑장 삭제
	@RequestMapping("/camping/delete.do")
	public String submitDelete(@RequestParam int camping_num,Model model, HttpServletRequest request) {
		
		campingService.deleteCamping(camping_num);
		
		//view에 메시지 표시
		model.addAttribute("message", "삭제 완료");
		model.addAttribute("url", request.getContextPath() + "/camping/list.do");
		return "common/resultView";
	}
	
	//캠핑장 데이터 받기		> sql의 설정 크기 늘려야 함.
	@RequestMapping("/camping/insertData.do")
	public String insertCamping(Model model, HttpServletRequest request) {
		
		List<CampingVO> list = new ArrayList<CampingVO>();
		String camp_name = "", camp_address = "", camp_phone = "", rcount = "";

		try {
			
			// XML 데이터를 읽어옴
			String path = request.getSession().getServletContext().getRealPath("/");
			String attach_path = "resources/downloads/getdata.xml";
			File fXmlFile = new File(path + attach_path);
			
			// XML 데이터를 읽어옴
			//File fXmlFile = new File("C:\\teacher\\java\\workspace_spring_origin\\camperPick_backup\\src\\main\\webapp\\resources\\downloads\\getdata.xml");

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();			//DocumentBuilder 객체를 만들기 위한 클래스
			DocumentBuilder db = factory.newDocumentBuilder();								//xml문서에 DOM객체를 만드는 Parser 객체를 추상화한 클래스.
			Document doc = db.parse(fXmlFile);												//생성된 DocumentBuilder객체의 parser()메소드에 파일 넘기면 Document 객체가 생성된다.

			Element el = doc.getDocumentElement();
			// <item> ~ </item>을 하나의 노드로 노드 리스트를 만듬
			NodeList itemList = el.getElementsByTagName("record");

			for (int i = 0; i < 600; i++) {

				// <item> ~ </item> 노드를 하나씩 읽어옴
				Node itemNode = itemList.item(i);
				// <item> ~ </item> 사이의 태그들로 노드 리스트를 만듬
				NodeList subList = itemNode.getChildNodes();

				// <item> ~ </item> 사이의 태그를 하나씩 읽어와 해당 태그와 일치할 경우 변수에 저장
				for (int j = 0; j < subList.getLength(); j++) {
					Node subNode = subList.item(j);
					if (subNode.getNodeName().equals("야영캠핑장명"))
						camp_name = subNode.getTextContent();
					if (subNode.getNodeName().equals("소재지도로명주소"))
						camp_address = subNode.getTextContent();
					if (subNode.getNodeName().equals("야영장전화번호"))
						camp_phone = subNode.getTextContent();
					if (subNode.getNodeName().equals("야영사이트수"))
						rcount = subNode.getTextContent();
				}
				//vo에 데이터 셋팅 후 list에 저장
				int roomc = Integer.parseInt(rcount);
				CampingVO vo = new CampingVO();
				if (roomc <= 10 && camp_address != "" && camp_address != null) {

					vo.setCamp_name(camp_name);
					vo.setCamp_address(camp_address);
					vo.setCamp_phone(camp_phone);
					vo.setRcount(roomc);
						
					list.add(vo);
				}

				camp_name = "";
				camp_address = "";
				camp_phone = "";
				rcount = "";

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
			//db에 저장 전송
		  for(int i=0;i<100;i++) { 
			  CampingVO campingVO = list.get(i); 
			   
			 campingService.insertCampingData(campingVO);
		  
		  }
		
		//view에 메시지 표시
		model.addAttribute("message", "전송 완료");
		model.addAttribute("url", request.getContextPath() + "/camping/list.do");
		return "common/resultView";	
	}
	
	
}
