package kr.spring.review.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.camping.dao.CampingMapper;
import kr.spring.review.dao.ReviewMapper;
import kr.spring.review.vo.ReviewReplyVO;
import kr.spring.review.vo.ReviewVO;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService{

	@Autowired
	private ReviewMapper reviewMapper;
	private CampingMapper campingMapper;

	@Override
	public int getReviewCount(Map<String, Object> map) {
		return reviewMapper.getReviewCount(map);
	}

	@Override
	public List<ReviewVO> getReviewList(Map<String, Object> map) {
		return reviewMapper.getReviewList(map);
	}

	@Override
	public void insertReview(ReviewVO review) {
		reviewMapper.insertReview(review);
		
	}

	@Override
	public void updateReview(ReviewVO review) {
		reviewMapper.updateReview(review);
		
	}

	@Override
	public void deleteReview(Integer review_num) {
		reviewMapper.deleteReview(review_num);
		
	}

	@Override
	public void updateFile(ReviewVO review) {
		reviewMapper.updateFile(review);
		
	}

	@Override
	public void deleteReviewByCampingNum(Integer review_num) {
		//댓글이 존재하면 댓글을 우선 삭제하고 부모글을 삭제
		reviewMapper.deleteReviewByCampingNum(review_num);
		//campingMapper.deleteCamping(camping_num);
	}

	@Override
	public ReviewVO getReview(Integer review_num) {
		return reviewMapper.getReview(review_num);
	}
	
//-------------------대댓글---------------------------------------------
	
	@Override
	public void insertReReview(ReviewReplyVO reviewReply) {
		reviewMapper.insertReReview(reviewReply);
		
	}

	@Override
	public int getReReviewCount(Map<String, Object> map) {
		return reviewMapper.getReReviewCount(map);
	}

	@Override
	public List<ReviewReplyVO> getReReviewList(Map<String, Object> map) {
		return reviewMapper.getReReviewList(map);
	}

	@Override
	public ReviewReplyVO getReReview(Integer rre_num) {
		return reviewMapper.getReReview(rre_num);
	}

	@Override
	public void updateReReview(ReviewReplyVO reviewReply) {
		reviewMapper.updateReReview(reviewReply);
		
	}

	@Override
	public void deleteReReview(Integer rre_num) {
		reviewMapper.deleteReReview(rre_num);
	}

	@Override
	public void deleteReReviewByCampingNum(Integer rre_num) {
		reviewMapper.deleteReviewByCampingNum(rre_num);
		
	}

	

	

}
