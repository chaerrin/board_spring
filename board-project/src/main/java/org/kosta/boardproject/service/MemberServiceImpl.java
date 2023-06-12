package org.kosta.boardproject.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.kosta.boardproject.model.mapper.MemberMapper;
import org.kosta.boardproject.model.vo.Authority;
import org.kosta.boardproject.model.vo.MemberVO;
import org.kosta.boardproject.model.vo.Pagination;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	private final MemberMapper memberMapper;
	/*
	 * 비밀번호 암호화처리를 위한 객체를 주입받는다 
	 * org.kosta.config.security.WebSecurityConfig 에 @Bean 설정 되어 있음 
	 */
	private final BCryptPasswordEncoder passwordEncoder;

	/*
	회원가입시 반드시 권한까지 부여되도록 트랜잭션 처리한다
	*/
	@Transactional	// AOP
	@Override
	public void registerMember(MemberVO memberVO) {
		// 비밀번호를 bcrypt 알고리즘으로 암호화하여 DB에 저장한다
		String encodedPwd = passwordEncoder.encode(memberVO.getPassword());
		memberVO.setPassword(encodedPwd);
		memberMapper.registerMember(memberVO);
		// 회원 가입시 반드시 권한이 등록되도록 트랜잭션처리를 한다
		Authority authority = new Authority(memberVO.getId(), "ROLE_MEMBER");
		memberMapper.registerRole(authority);
	}

	@Override
	public void updateMember(MemberVO memberVO) {
		// 변경할 비밀번호를 암호화한다
		String encodePassword = passwordEncoder.encode(memberVO.getPassword());
		memberVO.setPassword(encodePassword);
		memberMapper.updateMember(memberVO);
	}

	@Override
	public MemberVO findMemberById(String id) {
		return memberMapper.findMemberById(id);
	}

	@Override
	public List<String> getAddressList() {
		return memberMapper.getAddressList();
	}

	@Override
	public List<MemberVO> findMemberListByAddress(String address) {
		return memberMapper.findMemberListByAddress(address);
	}

	@Override
	public int getMemberCount() {
		return memberMapper.getMemberCount();
	}

	@Override
	public String idcheck(String id) {
		int count = memberMapper.idcheck(id);
		return (count == 0) ? "ok" : "fail";
	}

	@Override
	public List<Authority> findAuthorityById(String id) {
		return memberMapper.findAuthorityById(id);
	}

	@Override
	public String passwordcheck(MemberVO memberVO , String password) {
		return (passwordEncoder.matches(password, memberVO.getPassword())) ? "ok" : "fail";
	}
	
	@Override
	public void deleteMemberAction(MemberVO memberVO) {
        memberMapper.deleteMember(memberVO.getId());
	}
	
	////////////////////////////////////////////////////////////////////////////
	@Override
	public Map<String, Object> findMemberListByIdPagination(String pageNo, String id) {
		int totalMember = memberMapper.getTotalMemberById(id);
		Pagination pagination = null;
		
		if(pageNo==null)
			pagination = new Pagination(totalMember);
		else
			pagination = new Pagination(Integer.parseInt(pageNo), totalMember);
		
		Map<String, Object> map = new HashMap<>();
		map.put("ID", id);
		map.put("PAGINATION", pagination);

		Map<String, Object> paging = new HashMap<>();
		paging.put("LIST", memberMapper.findMemberListByIdPagination(map));
		
		if(totalMember==0)
			pagination = null;
		
		paging.put("PAGINATION", pagination);

		return paging;
	}
	////////////////////////////////////////////////////////////////////////////

	@Override
	public String findMemberId(MemberVO memberVO) {
		return memberMapper.findMemberId(memberVO);
	}

	@Override
	public String findMemberPassword(MemberVO memberVO) {
		// 회원정보에 해당하는 회원 존재유무 확인
		int result = memberMapper.findMemberPassword(memberVO);
		if(result<1) {	// 회원존재 X
			return null;
		}else {	// 회원존재 O
			MemberVO tempMemberVO = findMemberById(memberVO.getId());
			String tempPassword = new Random().nextInt(5000)+1000+"";	// 임시 비밀번호 발급
			tempMemberVO.setPassword(tempPassword);
			updateMember(tempMemberVO);	// MemberServiceImpl 의 updateMember()
			return tempPassword;
		}
	}
}
