package kr.spring.member.service;

import java.util.List;
import java.util.Map;

import kr.spring.member.vo.MemberVO;

public interface MemberService {
	public void insertMember(MemberVO member);
	public MemberVO selectCheckMember(String email);
	public MemberVO selectMember(Integer mem_num);
	public MemberVO selectCheckMember2(String phone);
	public void updateMember(MemberVO member);
	public void updatepassword(MemberVO member);
	public void deleteMember(Integer mem_num);
	public void updateAddress(MemberVO member);
	
	public int getMemberCount(Map<String, Object> map);
	public List<MemberVO> getMemberList(Map<String,Object> map);
	public MemberVO getMember(Integer mem_num);
	public void updateAuth(MemberVO memberVO);
}
