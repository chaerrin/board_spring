package org.kosta.boardproject.controller;

import java.util.Map;

import org.kosta.boardproject.model.vo.MemberVO;
import org.kosta.boardproject.service.MemberService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;
	
	//비인증 상태에서도 접근 가능하도록 /guest/ 이하로 url 등록 
	//org.kosta.myproject.config.security.WebSecurityConfig 설정되어 있음 
	@RequestMapping("/guest/findMemberById")
	public String findMemberById(String id,Model model) {		
		MemberVO vo = memberService.findMemberById(id);
		if (vo == null)
			return "member/findMemberById_fail";
		else {
			model.addAttribute("memberVO",vo);
			return "member/findMemberById_success" ;
		}
	}
	
	@RequestMapping("loginForm")
	public String loginForm() {
		return "member/login-form";
	}
	
	//WebSecurityConfig에 등록되어 있음 ( failureUrl("/login_fail") )
	@RequestMapping("login_fail")
	public String loginFail() {
		return "member/login_fail";
	}
	
    //spring security에서 로그인 , 로그아웃 처리를 하므로 login , logout 관련 메서드는 필요없다  
	//guest/ 가 아닌 모든 컨트롤러는 인증이 되어야 한다. 비인증 상태에서 접근할 경우 로그인 폼이 있는 home으로 redirect 됨 
	
	@RequestMapping("guest/idcheckAjax")
	@ResponseBody
	public String idcheckAjax(String id) {
		return memberService.idcheck(id);
	}
	
	@GetMapping("getMemberTotalCount")	
	@ResponseBody
	public int getMemberTotalCount() {
		return memberService.getMemberCount();
	}
	
	@RequestMapping("updateForm")
	//@AuthenticationPrincipal : Spring Security를 통해 로그인한 인증정보를 받아오는 어노테이션 
	public String updateForm(@AuthenticationPrincipal MemberVO memberVO,Model model) {
		model.addAttribute("member", memberVO);
		return "member/updateForm";
	}

	@PostMapping("updateMemberAction")
	//첫번째 매개변수 Authentication : Spring Security 인증 정보 , 두번째 매개변수 memberVO : 수정폼에서 전달받는 데이터 
	public String updateMemberAction(Authentication authentication, MemberVO memberVO) {
		MemberVO vo = (MemberVO)authentication.getPrincipal();			
		memberService.updateMember(memberVO);//service에서 변경될 비밀번호를 암호화한다 
		// 수정한 회원정보로 Spring Security 회원정보를 업데이트한다
		vo.setPassword(memberVO.getPassword());
		vo.setName(memberVO.getName());
		vo.setAddress(memberVO.getAddress());	
		return "redirect:updateResult";
	}
	
	@GetMapping("updateResult")	
	public String updateResult(@AuthenticationPrincipal MemberVO memberVO, String pageNo, Model model){
		Map<String, Object> paging = memberService.findMemberListByIdPagination(pageNo, memberVO.getId());
		model.addAttribute("memberList", paging.get("LIST"));
		model.addAttribute("pagination", paging.get("PAGINATION"));
		return "member/mypage";
	}
	
	@RequestMapping("guest/registerForm")
	public String registerForm() {
		return "member/registerForm";
	}
	
	@PostMapping("guest/registerMember")
	public String register(MemberVO memberVO) {
		memberService.registerMember(memberVO);//등록시 service에서 비밀번호를 암호화 한다 
		return "redirect:/guest/registerResult";
	}

	@GetMapping("guest/registerResult")
	public String registerResultView() {
		return "member/login-form";
	}
	
	@RequestMapping("mypage")
	public String mypage(@AuthenticationPrincipal MemberVO memberVO, String pageNo, Model model) {
		Map<String, Object> paging = memberService.findMemberListByIdPagination(pageNo, memberVO.getId());
		model.addAttribute("memberList", paging.get("LIST"));
		model.addAttribute("pagination", paging.get("PAGINATION"));
		return "member/mypage";
	}
	
	@RequestMapping("deleteMemberForm")
	public String deleteMemberForm() {
		return "member/deleteForm";
	}
	
	@RequestMapping("passwordcheckAjax")
	@ResponseBody
	public String passwordcheckAjax(@AuthenticationPrincipal MemberVO memberVO, String password) {
		return memberService.passwordcheck(memberVO, password);
	}
	
	@PostMapping("deleteMemberAction")
	public String deleteMemberAction(@AuthenticationPrincipal MemberVO memberVO) {
		memberService.deleteMemberAction(memberVO);
		return "redirect:deleteMemberResult";
	}
	
	@GetMapping("deleteMemberResult")
	public String deleteMemberResult() {
		return "member/delete-result";
	}
	
	@RequestMapping("guest/findMemberIdAndPasswordForm")
	public String findMemberIdForm() {
		return "member/find-id-and-password-form";
	}
	
	@RequestMapping("guest/findMemberIdAjax")
	@ResponseBody
	public String findMemberId(MemberVO memberVO) {
		return memberService.findMemberId(memberVO);
	}
	
	@RequestMapping("guest/findMemberPasswordAjax")
	@ResponseBody
	public String findMemberPassword(MemberVO memberVO) {
		return memberService.findMemberPassword(memberVO);
	}

}
