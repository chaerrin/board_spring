package org.kosta.boardproject.service;

import java.util.Map;

import org.kosta.boardproject.model.vo.BoardVO;
import org.kosta.boardproject.model.vo.MemberVO;

public interface BoardService {
	Map<String, Object> findboardAllList(String pagNo);
	
	void registerBoard(MemberVO memberVO, BoardVO boardVO);
	
	void updateBoard(BoardVO boardVO);
	
	void deleteBoard(int boardNo);
	
	BoardVO boardDetail(int No);
	
	void boardHitsCount(int no);
	
}