package sku.secure.dataaccess;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import sku.secure.business.domain.Member;
import sku.secure.helper.DataDuplicatedException;
import sku.secure.helper.DataNotFoundException;

public class MemberDAOStatement implements MemberDAO {

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
		String sql = "INSERT INTO member (email, name, password, age, gender, tel, zipcode, address, role) VALUES "
				+ "('"+member.getEmail()+"', '"
				+ member.getName() + "', '" + member.getPassword() + "', "
				+ member.getAge() + ", '" + member.getGender()+"', '"
				+ member.getTel() + "', '" + member.getZipcode() + "', '"
				+ member.getAddress() + "', " + member.getRole() + ")";
		System.out.println("MemberDAOStatement insertMember() query : " + sql);
		
		Connection connection = null;
		Statement stmt = null;
		
		try {
			connection = this.obtainConnection();
			stmt = connection.createStatement();
			affectedRows = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			throw new DataDuplicatedException(e);
		} finally {
			this.closeResources(connection, stmt);
		}
		return affectedRows;
	}

	@Override
	public int updateMember(Member member) throws DataNotFoundException {
		int affectedRows = 0;
		String sql = "UPDATE member SET name='" + member.getName() + "', password='" + member.getPassword() + "', "
				+ " age=" + member.getAge() + ", gender='" + member.getGender() + "', tel='" + member.getTel() + "', "
				+ " zipcode='" + member.getZipcode() + "', address='" + member.getAddress() + "', "
				+ "role=" + member.getRole() + " WHERE email='" + member.getEmail() + "'";
		System.out.println("MemberDAOStatement updateMember() query : " + sql);
		
		Connection connection = null;
		Statement stmt = null;
		
		try {
			connection = this.obtainConnection();
			stmt = connection.createStatement();
			affectedRows = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataNotFoundException(e);
		} finally {
			this.closeResources(connection, stmt);
		}
		return affectedRows;
	}

	@Override
	public int deleteMember(Member member) throws DataNotFoundException {
		int affectedRows = 0;
		String sql = "DELETE FROM member WHERE email='" + member.getEmail() + "'";
		System.out.println("MemberDAOStatement deleteMember() query : " + sql);
		
		Connection connection = null;
		Statement stmt = null;
		
		try {
			connection = this.obtainConnection();
			stmt = connection.createStatement();
			affectedRows = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataNotFoundException(e);
		} finally {
			this.closeResources(connection, stmt);
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
		String sql = "SELECT * FROM member WHERE email='" + email + "'";
		System.out.println("MemberDAOStatement selectMemberByEmail() query : " + sql);
		
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			connection = this.obtainConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);
			
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
			this.closeResources(connection, stmt, rs);;
		}
		return member;
	}

	@Override
	public Member selectMemberByName(String name) throws DataNotFoundException {
		Member member = null;
		String sql = "SELECT * FROM member WHERE name='" + name + "'";
		System.out.println("MemberDAOStatement selectMemberByName() query : " + sql);
		
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			connection = this.obtainConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);
			
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
			this.closeResources(connection, stmt, rs);;
		}
		return member;
	}

	@Override
	public List<Member> selectAllMembers() {
		List<Member> memberList = new ArrayList<Member>();
		Member member = null;
		String sql = "SELECT * FROM member";
		System.out.println("MemberDAOStatement selectAllMembers() query : " + sql);
		
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			connection = this.obtainConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);
			
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
			this.closeResources(connection, stmt, rs);;
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
			
			if (searchText != null) {
				searchText.trim();
				String searchTextTemp1 = searchText.replace("@", "@@");
				String searchTextTemp2 = searchTextTemp1.replace("_", "@_");
				String searchTextTemp3 = searchTextTemp2.replace("%", "@%");
				searchTextKeyword = "%" + searchTextTemp3.replace(' ', '%') + "%";
			}
			
			if (searchType != null) {
				if (searchType.equals("all")) {
					whereClause = " WHERE LOWER(name) LIKE LOWER('" + searchTextKeyword + "') OR LOWER(email) LIKE LOWER('" + searchTextKeyword + "') ESCAPE '@'";
				} else if (searchType.equals("name")) {
					whereClause = " WHERE LOWER(name) LIKE LOWER('" + searchTextKeyword + "') ESCAPE '@'";
				} else if (searchType.equals("email")) {
					whereClause = " WHERE LOWER(email) LIKE LOWER('" + searchTextKeyword + "') ESCAPE '@'";
				}
			}
			
			if(searchInfo.containsKey("role")) {
				role = (Integer) searchInfo.get("role");
				whereClause = " WHERE role = " + role;
			}
		}
		
		String sql = "SELECT * FROM member " + whereClause;
		System.out.println("MemberDAOStatement selectMemberList() query : " + sql);
		
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			connection = this.obtainConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);
			
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
			this.closeResources(connection, stmt, rs);
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
			
			if (searchText != null) {
				searchText.trim();
				String searchTextTemp1 = searchText.replace("@", "@@");
				String searchTextTemp2 = searchTextTemp1.replace("_", "@_");
				String searchTextTemp3 = searchTextTemp2.replace("%", "@%");
				searchTextKeyword = "%" + searchTextTemp3.replace(' ', '%') + "%";
			}
			
			if (searchType.equals("all")) {
				whereClause = " WHERE LOWER(name) LIKE LOWER('" + searchTextKeyword + "') OR LOWER(email) LIKE LOWER('" + searchTextKeyword + "') ESCAPE '@'";
			} else if (searchType.equals("name")) {
				whereClause = " WHERE LOWER(name) LIKE LOWER('" + searchTextKeyword + "') ESCAPE '@'";
			} else if (searchType.equals("email")) {
				whereClause = " WHERE LOWER(email) LIKE LOWER('" + searchTextKeyword + "') ESCAPE '@'";
			}
			
			if(searchInfo.containsKey("role")) {
				role = (Integer) searchInfo.get("role");
				whereClause = " WHERE role = " + role;
			}
		}
		
		String sql = "SELECT COUNT(email) FROM member " + whereClause;
		System.out.println("MemberDAOStatement selectMemberCount() query : " + sql);
		
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			connection = this.obtainConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeResources(connection, stmt, rs);
		}
		return count;
	}

}
