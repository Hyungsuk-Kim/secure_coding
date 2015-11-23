package sku.secure.business.service;

import java.util.Map;

import sku.secure.business.domain.Member;
import sku.secure.helper.DataDuplicatedException;
import sku.secure.helper.DataNotFoundException;

public interface MemberService {
	public abstract Member getMemberByEmail(String email) throws DataNotFoundException;
	public abstract Member getMemberByName(String name) throws DataNotFoundException;
	public abstract Member[] getAllMembers();
	public abstract int register(Member member) throws DataDuplicatedException;
	public abstract int updateProfile(Member member) throws DataNotFoundException;
	public abstract int removeMember(Member member) throws DataNotFoundException;
	public abstract Member[] getMemberList(Map<String, Object> searchInfo);
	public abstract int getMemberCount(Map<String, Object> searchInfo);
	public abstract Member checkMember(String memberID, String password) throws DataNotFoundException;
}
