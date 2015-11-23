package sku.secure.dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import sku.secure.business.domain.Member;
import sku.secure.helper.DataDuplicatedException;
import sku.secure.helper.DataNotFoundException;

public class MemberDAOImpl implements MemberDAO {

	private Connection obtainConnection() throws SQLException {
		return DatabaseUtil.getConnection();
	}
	
	private void closeResources(Connection connection, Statement stmt, ResultSet rs) {
		DatabaseUtil.close(connection, stmt, rs);
	}
	
	private void closeResources(Connection connection, Statement stmt) {
		DatabaseUtil.close(connection, stmt);
	}
	
	@Override
	public int insertMember(Member member) throws DataDuplicatedException {
		int affectedRows = 0;
		String sql = "INSERT INTO member (email, name, password, age, gender, tel, zipcode, address, role) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		System.out.println("MemberDAOImpl insertMember() query : " + sql);
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = this.obtainConnection();
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, member.getEmail());
			pstmt.setString(2, member.getName());
			pstmt.setString(3, member.getPassword());
			pstmt.setInt(4, member.getAge());
			pstmt.setString(5, member.getGender());
			pstmt.setString(6, member.getTel());
			pstmt.setString(7, member.getZipcode());
			pstmt.setString(8, member.getAddress());
			pstmt.setInt(9, member.getRole());
			affectedRows = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DataDuplicatedException(e);
		} finally {
			this.closeResources(connection, pstmt);
		}
		return affectedRows;
	}

	@Override
	public int updateMember(Member member) throws DataNotFoundException {
		int affectedRows = 0;
		String sql = "UPDATE member SET name=?, password=?, age=?, gender=?, tel=?, zipcode=?, address=?, role=? WHERE email=?";
		System.out.println("MemberDAOImpl updateMember() query : " + sql);
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = this.obtainConnection();
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, member.getName());
			pstmt.setString(2, member.getPassword());
			pstmt.setInt(3, member.getAge());
			pstmt.setString(4, member.getGender());
			pstmt.setString(5, member.getTel());
			pstmt.setString(6, member.getZipcode());
			pstmt.setString(7, member.getAddress());
			pstmt.setInt(8, member.getRole());
			pstmt.setString(9, member.getEmail());
			affectedRows = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataNotFoundException(e);
		} finally {
			this.closeResources(connection, pstmt);
		}
		return affectedRows;
	}

	@Override
	public int deleteMember(Member member) throws DataNotFoundException {
		int affectedRows = 0;
		String sql = "DELETE FROM member WHERE email=?";
		System.out.println("MemberDAOImpl deleteMember() query : " + sql);
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = this.obtainConnection();
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, member.getEmail());
			affectedRows = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataNotFoundException(e);
		} finally {
			this.closeResources(connection, pstmt);
		}
		return affectedRows;
	}

	@Override
	public boolean memberEmailExist(String email) throws DataNotFoundException {
		boolean result = false;
		if (this.selectMemberByEmail(email) != null) {
			result = true;
		}
		return result;
	}

	@Override
	public boolean memberNameExist(String name) throws DataNotFoundException {
		boolean result = false;
		if (this.selectMemberByName(name) != null) {
			result = true;
		}
		return result;
	}

	@Override
	public Member selectMemberByEmail(String email) throws DataNotFoundException {
		Member member = null;
		String sql = "SELECT * FROM member WHERE email=?";
		System.out.println("MemberDAOImpl selectMemberByEmail() query : " + sql);
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = this.obtainConnection();
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				member = new Member(rs.getString("email"), rs.getString("name"), 
						rs.getString("password"), rs.getInt("age"), rs.getString("gender"), 
						rs.getString("tel"), rs.getString("zipcode"), rs.getString("address"), 
						rs.getInt("role"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataNotFoundException(e);
		} finally {
			this.closeResources(connection, pstmt, rs);;
		}
		return member;
	}

	@Override
	public Member selectMemberByName(String name) throws DataNotFoundException {
		Member member = null;
		String sql = "SELECT * FROM member WHERE name=?";
		System.out.println("MemberDAOImpl selectMemberByName() query : " + sql);
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = this.obtainConnection();
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				member = new Member(rs.getString("email"), rs.getString("name"), 
						rs.getString("password"), rs.getInt("age"), rs.getString("gender"), 
						rs.getString("tel"), rs.getString("zipcode"), rs.getString("address"), 
						rs.getInt("role"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataNotFoundException(e);
		} finally {
			this.closeResources(connection, pstmt, rs);;
		}
		return member;
	}

	@Override
	public List<Member> selectAllMembers() {
		List<Member> memberList = new ArrayList<Member>();
		Member member = null;
		String sql = "SELECT * FROM member";
		System.out.println("MemberDAOImpl selectAllMembers() query : " + sql);
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = this.obtainConnection();
			pstmt = connection.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				member = new Member(rs.getString("email"), rs.getString("name"), 
						rs.getString("password"), rs.getInt("age"), rs.getString("gender"), 
						rs.getString("tel"), rs.getString("zipcode"), rs.getString("address"), 
						rs.getInt("role"));
				
				memberList.add(member);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeResources(connection, pstmt, rs);;
		}
		return memberList;
	}

	@Override
	public List<Member> selectMemberList(Map<String, Object> searchInfo) {
		List<Member> mList = new ArrayList<Member>();
		Member member = null;
		
		String searchType = null;
		String searchText = null;
		String searchTextKeyword = null;
		int role = 0;
		
		String whereClause = "";
		
		if (!(searchInfo.isEmpty())) {
			searchType = (String) searchInfo.get("searchType");
			searchText = (String) searchInfo.get("searchText");
			searchTextKeyword = null;
			
			if (searchType != null) {
				if (searchType.equals("all")) {
					whereClause = " WHERE LOWER(name) LIKE LOWER(?) OR LOWER(email) LIKE LOWER(?) ESCAPE '@'";
				} else if (searchType.equals("name")) {
					whereClause = " WHERE LOWER(name) LIKE LOWER(?) ESCAPE '@'";
				} else if (searchType.equals("email")) {
					whereClause = " WHERE LOWER(email) LIKE LOWER(?) ESCAPE '@'";
				}
			}
			
			if (searchText != null) {
				searchText.trim();
				String searchTextTemp1 = searchText.replace("@", "@@");
				String searchTextTemp2 = searchTextTemp1.replace("_", "@_");
				String searchTextTemp3 = searchTextTemp2.replace("%", "@%");
				searchTextKeyword = "%" + searchTextTemp3.replace(' ', '%') + "%";
			}
			
			if(searchInfo.containsKey("role")) {
				role = (Integer) searchInfo.get("role");
				whereClause = " WHERE role = " + role;
			}
		}
		
		String sql = "SELECT * FROM member " + whereClause;
		System.out.println("MemberDAOImpl selectMemberList() query : " + sql);
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = this.obtainConnection();
			pstmt = connection.prepareStatement(sql);
			
			if (searchType == null || searchType.trim().length() == 0) {
            } else if (searchType.equals("all")) {
				pstmt.setString(1, searchTextKeyword);
				pstmt.setString(2, searchTextKeyword);
			} else if (searchType.equals("name")) {
				pstmt.setString(1, searchTextKeyword);
			} else if (searchType.equals("email")) {
				pstmt.setString(1, searchTextKeyword);
			}
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				member = new Member(rs.getString("email"), rs.getString("name"), 
						rs.getString("password"), rs.getInt("age"), rs.getString("gender"), 
						rs.getString("tel"), rs.getString("zipcode"), rs.getString("address"), 
						rs.getInt("role"));
				mList.add(member);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeResources(connection, pstmt, rs);
		}
		
		return mList;
	}

	@Override
	public int selectMemberCount(Map<String, Object> searchInfo) {
		int count = 0;
		
		String searchType = null;
		String searchText = null;
		String searchTextKeyword = null;
		int role = 0;
		
		String whereClause = "";
		
		if (!(searchInfo.isEmpty())) {
			searchType = (String) searchInfo.get("searchType");
			searchText = (String) searchInfo.get("searchText");
			searchTextKeyword = null;
			
			if (searchType.equals("all")) {
				whereClause = " WHERE LOWER(name) LIKE LOWER(?) OR LOWER(email) LIKE LOWER(?) ESCAPE '@'";
			} else if (searchType.equals("name")) {
				whereClause = " WHERE LOWER(name) LIKE LOWER(?) ESCAPE '@'";
			} else if (searchType.equals("email")) {
				whereClause = " WHERE LOWER(email) LIKE LOWER(?) ESCAPE '@'";
			}
			
			if (searchText != null) {
				searchText.trim();
				String searchTextTemp1 = searchText.replace("@", "@@");
				String searchTextTemp2 = searchTextTemp1.replace("_", "@_");
				String searchTextTemp3 = searchTextTemp2.replace("%", "@%");
				searchTextKeyword = "%" + searchTextTemp3.replace(' ', '%') + "%";
			}
			
			if(searchInfo.containsKey("role")) {
				role = (Integer) searchInfo.get("role");
				whereClause = " WHERE role = " + role;
			}
		}
		
		String sql = "SELECT COUNT(email) FROM member " + whereClause;
		System.out.println("MemberDAOImpl selectMemberCount() query : " + sql);
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = this.obtainConnection();
			pstmt = connection.prepareStatement(sql);
			
			if (searchType == null || searchType.trim().length() == 0) {
            } else if (searchType.equals("all")) {
				pstmt.setString(1, searchTextKeyword);
				pstmt.setString(2, searchTextKeyword);
			} else if (searchType.equals("name")) {
				pstmt.setString(1, searchTextKeyword);
			} else if (searchType.equals("email")) {
				pstmt.setString(1, searchTextKeyword);
			}
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeResources(connection, pstmt, rs);
		}
		return count;
	}

}
