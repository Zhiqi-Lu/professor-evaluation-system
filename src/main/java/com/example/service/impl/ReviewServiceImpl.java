package com.example.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.example.pojo.CourseRank;
import com.example.pojo.ProfessorRank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dao.ReviewDao;
import com.example.pojo.Review;
import com.example.pojo.ReviewFront;
import com.example.service.ReviewService;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {
	
	@Autowired
	private ReviewDao reviewDao = null;
	
	@Override
	public List<ReviewFront> queryall() {
		return reviewDao.queryall();
	}
	
	@Override
	public int saveRev(Review reviewVo) {
		reviewVo.setState(3);
		return reviewDao.saveRev(reviewVo);
	}
	@Override
	public int upvote(int id_review) {
		return reviewDao.upvote(id_review);
	}

	@Override
	public int downvote(int id_review) {
		return reviewDao.downvote(id_review);
	}

	@Override
	public List<ReviewFront> queryByStu(String sno) {
		return reviewDao.queryByStu(sno);
	}

	/**
	 * @param id_review 需要更改状态的评价的id
	 * @return 如果评价已被举报则返回false，如果评价可以被申请删除则返回true
	 */
	@Override
	public boolean tryDele(int id_review) {
		Review rev1 = reviewDao.getRev(id_review);
		if(rev1.getState() > 2){ //如果评价已经被举报，则返回0
			return false;
		}
		else{
			reviewDao.modState(id_review, 2);
			return true;
		}
	}

	@Override
	public Review getRev(int id_review) {
		return reviewDao.getRev(id_review);
	}
	
	@Override
	public List<ReviewFront> queryreview(String PCname)  {
		return reviewDao.queryreview( PCname);
	}

	/**
	 *
	 * @param id_review 被举报的评价的id
	 * @return 是否举报成功
	 */
	@Override
	public boolean reportRev(int id_review) {
		Review rev1 = reviewDao.getRev(id_review);
		if(rev1.getState() == 1){ //如果评价处于有效状态则更改其状态为被举报
			reviewDao.modState(id_review, 3);
			return true;
		}
		else{
			return false;
		}
	}

	@Override
	public List<Review> getReport() {
		return reviewDao.getReport();
	}

	@Override
	public void approveRev(int id_review) {
		reviewDao.modState(id_review, 1);
	}

	@Override
	public int deleRev(int id_review) {
		return reviewDao.deleRev(id_review);
	}

	@Override
	public List<CourseRank> getCourseRank(){
		List<CourseRank> rank10 = reviewDao.getCourseRank();
		if (rank10.size() > 10) {
			return rank10.subList(0, 10);
		}
		return new ArrayList<>();
	}

	@Override
	public List<ProfessorRank> getProfRank() {
		List<ProfessorRank> rank10 = reviewDao.getProfRank();
		if (rank10.size() > 10) {
			return rank10.subList(0, 10);
		}
		return new ArrayList<>();
	}
}