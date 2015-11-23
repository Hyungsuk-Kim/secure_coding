package sku.secure.dataaccess;

import java.util.List;
import java.util.Map;

import sku.secure.business.domain.Member;
import sku.secure.helper.DataDuplicatedException;
import sku.secure.helper.DataNotFoundException;

public interface MemberDAO {
	public abstract int insertMember(Member member) throws DataDuplicatedException;
	public abstract int updateMember(Member member) throws DataNotFoundException;
	public abstract int deleteMember(Member member) throws DataNotFoundException;
	public abstract boolean memberEmailExist(String email) throws DataNotFoundException;
	public abstract boolean memberNameExist(String name) throws DataNotFoundException;
	public abstract Member selectMemberByEmail(String email) throws DataNotFoundException;
	public abstract Member selectMemberByName(String name) throws DataNotFoundException;
	public abstract List<Member> selectAllMembers();
	public abstract List<Member> selectMemberList(Map<String, Object> searchInfo);
	public abstract int selectMemberCount(Map<String, Object> searchInfo);
}
