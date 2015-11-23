package sku.secure.business.service;

import java.util.Map;

import sku.secure.business.domain.Member;
import sku.secure.dataaccess.MemberDAO;
import sku.secure.dataaccess.MemberDAOImpl;
import sku.secure.helper.DataDuplicatedException;
import sku.secure.helper.DataNotFoundException;

public class MemberServiceImpl implements MemberService {
	private MemberDAO memberDao;
	
	public MemberServiceImpl() {
		this.memberDao = new MemberDAOImpl();
	}

	@Override
	public Member getMemberByEmail(String email) throws DataNotFoundException {
		return memberDao.selectMemberByEmail(email);
	}

	@Override
	public Member getMemberByName(String name) throws DataNotFoundException {
		return memberDao.selectMemberByName(name);
	}

	@Override
	public Member[] getAllMembers() {
		return memberDao.selectAllMembers().toArray(new Member[0]);
	}

	@Override
	public int register(Member member) throws DataDuplicatedException {
		return memberDao.insertMember(member);
	}

	@Override
	public int updateProfile(Member member) throws DataNotFoundException {
		return memberDao.updateMember(member);
	}

	@Override
	public int removeMember(Member member) throws DataNotFoundException {
		return memberDao.deleteMember(member);
	}

	@Override
	public Member[] getMemberList(Map<String, Object> searchInfo) {
		return memberDao.selectMemberList(searchInfo).toArray(new Member[0]);
	}

	@Override
	public int getMemberCount(Map<String, Object> searchInfo) {
		return memberDao.selectMemberCount(searchInfo);
	}

	@Override
	public Member checkMember(String memberID, String password) throws DataNotFoundException {
		Member selectedMember = null;
		selectedMember = memberDao.selectMemberByEmail(memberID);
		if (selectedMember == null) {
			selectedMember = new Member(memberID, password);
			selectedMember.setCheck(Member.INVALID_ID);
		} else {
			if (selectedMember.getPassword().equals(password)) {
				selectedMember.setCheck(Member.VALID_MEMBER);
			} else if (!selectedMember.getPassword().equals(password)) {
				selectedMember.setCheck(Member.INVALID_PASSWORD);
			}
		}
		return selectedMember;
	}

}
