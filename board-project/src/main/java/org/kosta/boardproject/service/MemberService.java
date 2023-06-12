package org.kosta.boardproject.service;

import java.util.List;
import java.util.Map;

import org.kosta.boardproject.model.vo.Authority;
import org.kosta.boardproject.model.vo.MemberVO;

public interface MemberService {
	
	MemberVO findMemberById(String id);

	List<String> getAddressList();

	List<MemberVO> findMemberListByAddress(String address);

	int getMemberCount();

	void updateMember(MemberVO vo);

	void registerMember(MemberVO vo);

	String idcheck(String id);
	
	List<Authority> findAuthorityById(String id);
	
	String passwordcheck(MemberVO memberVO, String  password);

	void deleteMemberAction(MemberVO memberVO);

	String findMemberId(MemberVO memberVO);

	String findMemberPassword(MemberVO memberVO);

	Map<String, Object> findMemberListByIdPagination(String pageNo, String id);


}
