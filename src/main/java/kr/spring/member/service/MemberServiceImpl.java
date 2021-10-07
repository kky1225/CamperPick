package kr.spring.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.member.dao.MemberMapper;
import kr.spring.member.vo.MemberVO;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberMapper memberMapper;
	
	@Override
	public void insertMember(MemberVO memberVO) {
		memberVO.setMem_num(memberMapper.selectMem_num());
		memberMapper.insertMember(memberVO);
		memberMapper.insertMember_detail(memberVO);
	}

	@Override
	public MemberVO selectCheckMember(String id) {
		return memberMapper.selectCheckMember(id);
	}

	@Override
	public MemberVO selectMember(Integer mem_num) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateMember(MemberVO memberVO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updatePassword(MemberVO memberVO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteMember(Integer mem_num) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateProfile(MemberVO memberVO) {
		// TODO Auto-generated method stub
		
	}

}
