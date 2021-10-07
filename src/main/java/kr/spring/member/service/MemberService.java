package kr.spring.member.service;

import kr.spring.member.vo.MemberVO;

public interface MemberService {
	public void insertMember(MemberVO memberVO);
	public MemberVO selectCheckMember(String id);
	public MemberVO selectMember(Integer mem_num);
	public void updateMember(MemberVO memberVO);
	public void updatePassword(MemberVO memberVO);
	public void deleteMember(Integer mem_num);
	public void updateProfile(MemberVO memberVO);
}
