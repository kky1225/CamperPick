package kr.spring.room.controller;

import java.util.HashMap;
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
import org.springframework.web.servlet.ModelAndView;


import kr.spring.room.service.RoomService;
import kr.spring.room.vo.RoomVO;
import kr.spring.util.StringUtil;


@Controller
public class RoomController {
	
	private static final Logger logger = LoggerFactory.getLogger(RoomController.class);
	
	@Autowired
	private RoomService roomService; 

	//자바빈 초기화
	@ModelAttribute					
	public RoomVO initCommand() {
		return new RoomVO();
	}

	//객실 등록 - 폼 호출
	@GetMapping("/room/writeRoom.do")
	public String writeForm(@RequestParam Integer camping_num, Model model) {
		
		model.addAttribute("camping_num", camping_num);
		
		return "roomWrite";
	}
	//객실 등록 - 전송
	@PostMapping("/room/writeRoom.do")
	public String submitForm(@Valid RoomVO roomVO, BindingResult result, Model model, HttpServletRequest request) {
		
		if(result.hasErrors()) {
			return "roomWrite";
		}
		//등록
		roomService.insertRoom(roomVO);
		
		//view에 메시지 표시
		model.addAttribute("message", "등록 완료");
		model.addAttribute("url", request.getContextPath() + "/camping/detail.do?camping_num="+roomVO.getCamping_num());		//공통으로 사용하니까 상위폴더부터 적시
		return "common/resultView";		//jsp경로 지정
		
	}
	//객실 상세 정보
	@RequestMapping("/room/detailRoom.do")
	public ModelAndView process(@RequestParam int room_num) {
		
		RoomVO room = roomService.getRoom(room_num);
		
		//HTML 태그 불허
		room.setRoom_name(StringUtil.useNoHtml(room.getRoom_name()));
		//HTML 태그 불허, 줄바꿈 허용
		room.setInfo(StringUtil.useBrNoHtml(room.getInfo()));
		
		return new ModelAndView("roomDetail","room",room);
	}
	//객실 사진
		@RequestMapping("/room/imageView.do")
		public ModelAndView viewImage(@RequestParam int room_num) {
			
			RoomVO room = roomService.getRoom(room_num);
			
			ModelAndView mav = new ModelAndView();
			
			mav.setViewName("imageView");
							//속성명			속성값(byte[] 타입의 데이터)
			mav.addObject("imageFile",room.getUploadfile());
			mav.addObject("filename", room.getFilename());
			
			return mav;
		}
			
	
	//객실 수정 - 폼 호출
		@GetMapping("/room/updateRoom.do")
		public String roomUpdate(@RequestParam int room_num,Model model) {
			
			RoomVO roomVO = roomService.getRoom(room_num);
			
			model.addAttribute("roomVO", roomVO);
			
			return "roomUpdate";
		}

	//객실 수정 - 전송
	@PostMapping("/room/updateRoom.do")
	public String submitRoomUpdate(@Valid RoomVO roomVO, BindingResult result, Model model, HttpServletRequest request) {
		logger.debug("<<글 수정>> : " + roomVO);
		if(result.hasErrors()) {
			return "roomUpdate";
		}
		
		//수정
		roomService.updateRoom(roomVO);
		
		//view에 메시지 표시
		model.addAttribute("message", "수정 완료");
		model.addAttribute("url", request.getContextPath() + "/camping/detail.do?camping_num="+roomVO.getCamping_num());
		return "common/resultView";		
	}
	//캠핑장 수정 - 파일 삭제
	@RequestMapping("/room/deleteFile.do")
	@ResponseBody
	public Map<String, String> removeFile(int room_num, HttpSession session){
		
		Map<String,String> map = new HashMap<String, String>();
		
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) {
			map.put("result", "logout");
		}else {
			roomService.deleteFile(room_num);
			map.put("result", "success");
		}
		
		return map;
	}
	
	//객실 삭제
	@RequestMapping("/room/deleteRoom.do")
	public String submitDeleteRoom(@RequestParam int room_num,Model model, HttpServletRequest request) {
		
		logger.debug("<<객실 삭제>> : "+room_num);
		RoomVO roomVO=roomService.getRoom(room_num);
		//삭제
		roomService.deleteRoom(room_num);
		
		//view에 메시지 표시
		model.addAttribute("message", "삭제 완료");
		model.addAttribute("url", request.getContextPath() + "/camping/detail.do?camping_num="+roomVO.getCamping_num());	
		return "common/resultView";		
	}
	
}
