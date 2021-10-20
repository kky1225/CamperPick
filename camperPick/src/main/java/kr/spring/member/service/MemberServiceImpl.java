package kr.spring.member.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.member.dao.MemberMapper;
import kr.spring.member.vo.MemberVO;

@Service
@Transactional
public class MemberServiceImpl implements MemberService{

	@Autowired
	private MemberMapper memberMapper;
	
	@Override
	public void insertMember(MemberVO member) {
		//회원번호 셋팅
		member.setMem_num(memberMapper.selectMem_num());
		System.out.println(member);
		memberMapper.insertMember(member);
		System.out.println(member);
		memberMapper.insertMember_detail(member);
	}

	@Override
	public MemberVO selectCheckMember(String email) {
		return memberMapper.selectCheckMember(email);
	}
	
	@Override
	public MemberVO selectCheckMember2(String phone) {
		return memberMapper.selectCheckMember2(phone);
	}

	@Override
	public MemberVO selectMember(Integer mem_num) {
		return memberMapper.selectMember(mem_num);
	}

	@Override
	public void updateMember(MemberVO member) {
		memberMapper.updateMember(member);
	}

	@Override
	public void updatepassword(MemberVO member) {
		memberMapper.updatePassword(member);
	}

	@Override
	public void deleteMember(Integer mem_num) {
		memberMapper.deleteMember(mem_num);
		memberMapper.deleteMember_detail(mem_num);
	}

	@Override
	public void updateAddress(MemberVO member) {
		memberMapper.updateAddress(member);
	}
	
	@Override
	public int getMemberCount(Map<String, Object> map) {
		return memberMapper.getMemberCount(map);
	}

	@Override
	public List<MemberVO> getMemberList(Map<String, Object> map) {
		return memberMapper.getMemberList(map);
	}

	@Override
	public void updateAuth(MemberVO memberVO) {
		memberMapper.updateAuth(memberVO);
		
	}

	@Override
	public MemberVO getMember(Integer mem_num) {
		return memberMapper.getMember(mem_num);
	}

}
