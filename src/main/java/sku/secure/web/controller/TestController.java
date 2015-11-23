package sku.secure.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sku.secure.business.domain.Member;
import sku.secure.business.service.MemberService;
import sku.secure.business.service.MemberServiceImpl;
import sku.secure.helper.DataDuplicatedException;
import sku.secure.helper.DataNotFoundException;

public class TestController extends HttpServlet {
	private static final long serialVersionUID = 2492384603547166871L;

	private MemberService getMemberServiceImplementation() {
		return new MemberServiceImpl();
	}
	
	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		try {
			if (action.equals("register")) {
				this.register(request, response);
			} else if (action.equals("remove")) {
				this.remove(request, response);
			} else if (action.equals("update")) {
				this.update(request, response);
			} else if (action.equals("profile")) {
				this.profile(request, response);
			} else if (action.equals("list")) {
				this.listUp(request, response);
			} else if (action.equals("signin")) {
				this.signIn(request, response);
			} else if (action.equals("signout")) {
				this.signOut(request, response);
			} else if (action.equals("select")) {
				this.selectMember(request, response);
			}
		} catch (DataNotFoundException dne) {
			throw new ServletException(dne);
		} catch (DataDuplicatedException dde) {
			throw new ServletException(dde);
		}
	}
	
	private void selectMember(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
		String email = request.getParameter("email");
		Member selectedMember = this.getMemberServiceImplementation().getMemberByEmail(email);
		
		request.setAttribute("selectedMember", selectedMember);
		RequestDispatcher dispatcher = request.getRequestDispatcher("profile.jsp");
		dispatcher.forward(request, response);
	}

	private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
		HttpSession session = request.getSession(false);
    	if (session == null) {
    		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");
    		return;
    	}
    	Member member = (Member) session.getAttribute("loginMember");
    	if (member == null) {
    		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");
    		return;
    	}
    	
    	String email = request.getParameter("email");
    	String name = request.getParameter("name");
    	String password = request.getParameter("password");
    	int age = 0;
    	if (request.getParameter("age") != null) {
    		age = Integer.parseInt(request.getParameter("age"));
    	}
    	String gender = request.getParameter("gender");
    	String tel = request.getParameter("tel");
    	String zipcode = request.getParameter("zipcode");
    	String address = request.getParameter("address");
    	int role = 0;
    	if (request.getParameter("role") != null) {
    		role = Integer.parseInt(request.getParameter("role"));
    	}
    	
    	List<String> errorMsgs = new ArrayList<String>();
    	
    	if (password==null || password.trim().length()==0) {
    		errorMsgs.add("패스워드를 입력해 주세요.");
    	}
    	if (name==null || name.trim().length()==0) {
    		errorMsgs.add("이름을 입력해 주세요.");
    	}
    	if (email==null || email.trim().length()==0) {
    		errorMsgs.add("E-mail을 입력해 주세요.");
    	}
    	
    	if (!errorMsgs.isEmpty()) {
    		request.setAttribute("errorMsgs", errorMsgs);
    		RequestDispatcher dispatcher = request.getRequestDispatcher("userError.jsp");
        	dispatcher.forward(request, response);
    		return;
    	}
    	
    	Member updateMember = new Member(email, name, password, age, gender, tel, zipcode, address, role);
    	this.getMemberServiceImplementation().updateProfile(updateMember);
    	if (updateMember.getEmail().equals(member.getEmail())) {
    		session.setAttribute("loginMember", updateMember);
    	}
    	request.setAttribute("member", updateMember);
    	RequestDispatcher dispatcher = request.getRequestDispatcher("welcome.jsp");
    	dispatcher.forward(request, response);
	}

	private void signOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute("loginMember");
			session.invalidate();
		}
    	RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
		dispatcher.forward(request, response);
	}

	private void signIn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
		String email =  (String) request.getParameter("email");
		String password = (String) request.getParameter("password");
		
		Member checkedMember = this.getMemberServiceImplementation().checkMember(email, password);
		String loginErrorMsg = null;
		
		if (checkedMember.getCheck() == Member.VALID_MEMBER) {
			HttpSession session = request.getSession();
			session.setAttribute("loginMember", checkedMember);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("welcome.jsp");
			dispatcher.forward(request, response);
			return;
		} else if (checkedMember.getCheck() == Member.INVALID_ID) {
			loginErrorMsg =  "아이디가 존재하지 않습니다.";
		} else if (checkedMember.getCheck() == Member.INVALID_PASSWORD) {
			loginErrorMsg = "패스워드가 일치하지 않습니다.";
		}
		request.setAttribute("loginErrorMsg", loginErrorMsg);
		RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
		dispatcher.forward(request, response);
	}

	private void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException, DataDuplicatedException {
		String email = request.getParameter("email");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		
		List<String> errorMsgs = new ArrayList<String>();
    	
    	if (email==null || email.trim().length()==0) {
    		errorMsgs.add("E-mail을 입력해 주세요.");
    	}
    	if (password==null || password.trim().length()==0) {
    		errorMsgs.add("패스워드를 입력해 주세요.");
    	}
    	if (name==null || name.trim().length()==0) {
    		errorMsgs.add("이름을 입력해 주세요.");
    	}
    	
    	if (!errorMsgs.isEmpty()) {
    		request.setAttribute("errorMsgs", errorMsgs);
    		RequestDispatcher dispatcher = request.getRequestDispatcher("userError.jsp");
        	dispatcher.forward(request, response);
    		return;
    	}
		
		Member member = new Member(email, name, password);
		MemberService memberService = this.getMemberServiceImplementation();
		memberService.register(member);
		Member loginMember = memberService.getMemberByEmail(member.getEmail());
		
		HttpSession session = request.getSession();
		session.setAttribute("loginMember", loginMember);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("welcome.jsp");
		dispatcher.forward(request, response);
	}

	private void remove(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
		HttpSession session = request.getSession(false);
    	if (session == null) {
    		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");
    		return;
    	}
    	Member member = (Member) session.getAttribute("loginMember");
    	if (member == null) {
    		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");
    		return;
    	}
    	
    	this.getMemberServiceImplementation().removeMember(member);
    	session.removeAttribute("loginMember");
    	session.invalidate();
    	
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
	}

	private void profile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
    	if (session == null) {
    		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");
    		return;
    	}
    	Member member = (Member) session.getAttribute("loginMember");
    	if (member == null) {
    		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");
    		return;
    	}
    	
		request.setAttribute("selectedMember", session.getAttribute("loginMember"));
    	RequestDispatcher dispatcher = request.getRequestDispatcher("profile.jsp");
    	dispatcher.forward(request, response);
	}

	private void listUp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
    	if (session == null) {
    		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");
    		return;
    	}
    	Member member = (Member) session.getAttribute("loginMember");
    	if (member == null) {
    		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");
    		return;
    	}
    	
    	Map<String, Object> searchInfo = new HashMap<String, Object>();
    	
    	String searchType = request.getParameter("searchType");
    	String searchText = request.getParameter("searchText");
    	int role = 0;
    	if (request.getParameter("role") != null) {
    		role = Integer.parseInt(request.getParameter("role"));
    		searchInfo.put("role", role);
    	}
    	searchInfo.put("searchType", searchType);
    	searchInfo.put("searchText", searchText);
    	
    	Member[] memberList = this.getMemberServiceImplementation().getMemberList(searchInfo);
    	request.setAttribute("memberList", memberList);
    	
    	RequestDispatcher dispatcher = request.getRequestDispatcher("list.jsp");
    	dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.processRequest(request, response);
	}

}
