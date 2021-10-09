package kr.spring.member.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import kr.spring.member.vo.MemberVO;

public interface MemberMapper {
	@Select("SELECT cmember_seq.nextval FROM dual")
	public int selectMem_num();
	
	@Insert("INSERT INTO cmember (mem_num, email, auth) VALUES (#{mem_num}, #{email}, #{auth})")
	public void insertMember(MemberVO member);
	
	@Insert("INSERT INTO cmember_detail (mem_num, name, passwd, phone, zipcode, address1, address2) VALUES (#{mem_num}, #{name}, #{passwd}, #{phone}, #{zipcode, jdbcType=VARCHAR}, #{address1, jdbcType=VARCHAR}, #{address2, jdbcType=VARCHAR})")
	public void insertMember_detail(MemberVO member);
	
	@Select("SELECT m.mem_num,m.auth, m.email, d.passwd FROM cmember m, cmember_detail d WHERE m.mem_num=d.mem_num AND m.email=#{email}")
	public MemberVO selectCheckMember(String email);
	
	@Select("SELECT * FROM spmember m, spmember_detail d WHERE m.mem_num = d.mem_num AND m.mem_num=#{mem_num}")
	public MemberVO selectMember(Integer mem_num);
}
