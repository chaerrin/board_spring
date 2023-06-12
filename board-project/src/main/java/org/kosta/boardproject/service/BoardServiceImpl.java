package org.kosta.boardproject.service;

import java.util.HashMap;
import java.util.Map;

import org.kosta.boardproject.model.mapper.BoardMapper;
import org.kosta.boardproject.model.vo.BoardVO;
import org.kosta.boardproject.model.vo.MemberVO;
import org.kosta.boardproject.model.vo.Pagination;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
	private final BoardMapper boardMapper;
	
	@Override
	public Map<String, Object> findboardAllList(String pageNo) {
		int totalBoardCount = boardMapper.getTotalBoardCount();
		Pagination pagination = null;
		if(pageNo==null)
			pagination = new Pagination(totalBoardCount);
		else
			pagination = new Pagination(Integer.parseInt(pageNo), totalBoardCount);
		
		pagination.setPostCountPerPage(10);
		
		Map<String, Object> paging = new HashMap<>();
		paging.put("LIST", boardMapper.findBoardAllList(pagination));
		
		if(totalBoardCount==0)
			pagination = null;
		
		paging.put("PAGINATION",pagination);
		return paging;
	}

	@Override
	public void registerBoard(MemberVO memberVO, BoardVO boardVO) {
		boardVO.setCsId(memberVO.getId());
		boardMapper.registerBoard(boardVO);
	}

	@Override
	public void updateBoard(BoardVO boardVO) {
		boardMapper.boardUpdate(boardVO);
	}

	@Override
	public void deleteBoard(int boardNo) {
		boardMapper.deleteBoard(boardNo);
	}

	@Override
	public BoardVO boardDetail(int No) {
		return boardMapper.boardDetail(No);
	}

	@Override
	public void boardHitsCount(int no) {
		boardMapper.boardHitsCount(no);
	}

}
