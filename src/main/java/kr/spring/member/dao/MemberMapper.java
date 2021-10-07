package kr.spring.member.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import kr.spring.member.vo.MemberVO;

public interface MemberMapper {
	
	@Select("SELECT spmember_sq.nextval FROM dual")
	public int selectMem_num();
	
	@Insert("INSERT INTO spmember (mem_num, id) VALUES (#{mem_num}, #{id})")
	public void insertMember(MemberVO memberVO);
	
	@Insert("INSERT INTO spmember_detail (mem_num, name, passwd, phone, email, zipcode, address1, address2) VALUES (#{mem_num}, #{name}, #{passwd}, #{phone}, #{email}, #{zipcode}, #{address1}, #{address2})")
	public void insertMember_detail(MemberVO memberVO);
	
	@Select("SELECT m.mem_num, m.id, m.auth, d.passwd, d.photo, d.email FROM spmember m, spmember_detail d WHERE m.mem_num=d.mem_num AND m.id=#{id}")
	public MemberVO selectCheckMember(String id);
	public MemberVO selectMember(Integer mem_num);
	public void updateMember(MemberVO memberVO);
	public void updatePassword(MemberVO memberVO);
	public void deleteMember(Integer mem_num);
	public void deleteMember_detail(Integer mem_num);
	public void updateProfile(MemberVO memberVO);
}
