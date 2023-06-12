package org.kosta.boardproject.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.kosta.boardproject.model.vo.Authority;
import org.kosta.boardproject.model.vo.MemberVO;
@Mapper
public interface MemberMapper {

	MemberVO findMemberById(String id);

	List<String> getAddressList();

	List<MemberVO> findMemberListByAddress(String address);	

	int getMemberCount();

	void updateMember(MemberVO vo);

	void registerMember(MemberVO vo);

	int idcheck(String id);

	List<Authority> findAuthorityById(String id);

	void registerRole(Authority authority);

	void deleteMember(String id);
	
	List<Map<String, Object>> findMemberListByIdPagination(Map<String, Object> map);
	
	int getTotalMemberById(String id);
	
	String findMemberId(MemberVO memberVO);

	int findMemberPassword(MemberVO memberVO);

}