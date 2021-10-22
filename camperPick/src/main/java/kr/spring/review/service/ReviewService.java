package kr.spring.review.service;

import java.util.List;
import java.util.Map;

import kr.spring.member.vo.MemberVO;
import kr.spring.review.vo.ReviewReplyVO;
import kr.spring.review.vo.ReviewVO;

public interface ReviewService {
	public int getReviewCount(Map<String, Object> map);
	public List<ReviewVO> getReviewList(Map<String,Object> map);
	public ReviewVO getReview(Integer review_num);
	public void insertReview(ReviewVO review);
	public void updateReview(ReviewVO review);
	public void deleteReview(Integer review_num);
	public void deleteReviewByCampingNum(Integer review_num);
	public void updateFile(ReviewVO review);
	
	
	//대댓글(후기의 댓글-상세페이지- 댓글)
	
	public int getReReviewCount(Map<String, Object> map);
	public List<ReviewReplyVO> getReReviewList(Map<String,Object> map);
	public ReviewReplyVO getReReview(Integer rre_num);
	public void insertReReview(ReviewReplyVO reviewReply);
		/*
		 * public void updateReReview(ReviewReplyVO reviewReply); public void
		 * deleteReReview(Integer rre_num); public void
		 * deleteReReviewByCampingNum(Integer rre_num);
		 */
	
}
