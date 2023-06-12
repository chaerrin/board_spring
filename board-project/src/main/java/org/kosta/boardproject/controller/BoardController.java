package org.kosta.boardproject.controller;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.kosta.boardproject.model.vo.BoardVO;
import org.kosta.boardproject.model.vo.MemberVO;
import org.kosta.boardproject.service.BoardService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BoardController {
	private final BoardService boardService;

	@RequestMapping(value = {"/","/board","home"})   
	public String board(Model model, String pageNo){
		Map<String, Object> paging = boardService.findboardAllList(pageNo);
		model.addAttribute("boardAllList", paging.get("LIST"));
		model.addAttribute("pagination", paging.get("PAGINATION"));
		return "board/board-list";
	}
	
	@RequestMapping("boardResult")
	public String boardResult() {
		return "board/board-detail";
	}
	
	@RequestMapping("boardDetail")
	public String boardDetail(Model model, @RequestParam("No") int No, @AuthenticationPrincipal MemberVO memberVO,HttpServletRequest request){
		if(memberVO==null) {
			model.addAttribute("boardDetail",boardService.boardDetail(No));
			return "board/board-detail";			
		}else {
			boardService.boardHitsCount(No);	
			model.addAttribute("boardDetail", boardService.boardDetail(No));
			return "board/board-detail";	
		}
	}
	
	@RequestMapping("/boardUpdateForm")
	public String boardUpdateForm(Model model,int No) {
		model.addAttribute("boardUpdate",boardService.boardDetail(No));
		return "board/update-board-form";
	}
	
	@PostMapping("boardUpdate")
	public String updateBoard(BoardVO boardVO){
		boardService.updateBoard(boardVO);
		return "redirect:updateBoardResult";
	}
	
	@RequestMapping("updateBoardResult")
	public String updateBoardResult() {
		return "board/update-board-result";
	}

	@RequestMapping("boardWriteForm")
	public String boardWrite() {
		return "board/write-board";
	}
	
	@PostMapping("boardWriteForm")
	public String boardWrite(@AuthenticationPrincipal MemberVO memberVO, Model model, BoardVO boardVO) {
		boardService.registerBoard(memberVO, boardVO);
		return "redirect:boardWriteResult";
	}
	
	@RequestMapping("boardWriteResult")
	public String boardWriteResult() {
		return "board/register-board-result.html";
	}
	
	@PostMapping("boardDelete")
	public String boardDelete(Model model, int boardNo) {
		boardService.deleteBoard(boardNo);
		return "redirect:deleteBoardResult";
	}
	
	@RequestMapping("deleteBoardResult")
	public String deleteBoardResult() {
		return "board/delete-board-reuslt";
	}
	
}