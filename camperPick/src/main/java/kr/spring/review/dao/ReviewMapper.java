package kr.spring.review.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.spring.review.vo.ReviewReplyVO;
import kr.spring.review.vo.ReviewVO;

public interface ReviewMapper {
	public int getReviewCount(Map<String, Object> map);
	public List<ReviewVO> getReviewList(Map<String,Object> map);
	@Select("SELECT * FROM creview WHERE review_num=#{review_num}")
	public ReviewVO getReview(Integer review_num);
	@Insert("INSERT INTO creview (review_num,title,content,ip,mem_num,uploadfile,filename,res_num,camping_num) VALUES (creview_seq.nextval,#{title},#{content},#{ip},#{mem_num},#{uploadfile},#{filename},#{res_num},#{camping_num})")
	public void insertReview(ReviewVO review);
	@Update ("UPDATE creview SET content=#{content},ip=#{ip},modify_date=SYSDATE WHERE review_num=#{review_num}")
	public void updateReview(ReviewVO review);
	@Delete("DELETE FROM creview WHERE review_num=#{review_num}")
	public void deleteReview(Integer review_num);
	//캠핑상세페이지 삭제시 댓글이 존재하면 부모글 삭제전 댓글 삭제
	@Delete("DELETE FROM creview WHERE camping_num=#{camping_num}")
	public void deleteReviewByCampingNum(Integer review_num);
		
	//후기 이미지 업데이트
	@Update("UPDATE creview SET uploadfile=#{uploadfile},filename=#{filename} WHERE mem_num=#{mem_num}")
	public void updateFile(ReviewVO review);

	
	
	//대댓글
	
		public int getReReviewCount(Map<String, Object> map);
		public List<ReviewReplyVO> getReReviewList(Map<String,Object> map);
		@Select("SELECT * FROM creview_reply WHERE rre_num=#{rre_num}")
		public ReviewReplyVO getReReview(Integer rre_num);
		@Insert("INSERT INTO creview_reply (rre_num,re_content,re_ip,re_date,re_modifydate,mem_num,review_num,res_num) VALUES (creview_reply_seq.nextval,#{re_content},#{re_ip},SYSDATE,SYSDATE,#{mem_num},#{review_num},#{res_num})")
		public void insertReReview(ReviewReplyVO reviewReply);
		@Update("UPDATE creview_reply SET re_content=#{re_content},re_ip=#{re_ip},re_modifydate=SYSDATE WHERE rre_num=#{rre_num}") 
	    public void updateReReview(ReviewReplyVO reviewReply);
	    @Delete("DELETE FROM creview_reply WHERE rre_num=#{rre_num}")
	    public void deleteReReview(Integer rre_num); 
	    //캠핑상세페이지 삭제시 대댓글이 존재하면 부모글 삭제전 댓글 삭제
	    @Delete("DELETE FROM creview_reply WHERE camping_num=#{camping_num}") 
	    public void deleteReReviewByCampingNum(Integer rre_num);
	

	

}
