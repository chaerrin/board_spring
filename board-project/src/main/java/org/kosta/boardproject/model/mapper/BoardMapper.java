package org.kosta.boardproject.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.kosta.boardproject.model.vo.BoardVO;
import org.kosta.boardproject.model.vo.Pagination;

@Mapper
public interface BoardMapper {

	List<BoardVO> findBoardAllList(Pagination pagination);

	BoardVO boardDetail(int No);

	void boardUpdate(BoardVO vo);

	void registerBoard(BoardVO boardVO);

	void deleteBoard(int no);

	int getTotalBoardCount();

	void boardHitsCount(int no);

}
