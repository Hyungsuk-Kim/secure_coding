package sku.secure.business.domain;

import java.io.Serializable;

public class Member implements Serializable {
	private static final long serialVersionUID = -2904062437954716537L;

	private String email;
	private String name;
	private String password;
	private int age;
	private String gender;
	private String tel;
	private String zipcode;
	private String address;
	private int role;
	private int check;
	
	public static final String MALE = "male";
	public static final String FEMALE = "female";
	
	public static final int ADMIN = 1;
	public static final int NORMAL_USER = 0;
	
	public static final int VALID_MEMBER = 10;
	public static final int INVALID_ID = 20;
	public static final int INVALID_PASSWORD = 30;
	
	public Member() {}
	
	public Member(String email, String password) {
		this.email = email;
		this.password = password;
	}
	
	public Member(String email, String name, String password) {
		this(email, password);
		this.name = name;
		this.role = NORMAL_USER;
	}
	
	public Member(String email, String name, String password, int age, String gender, String tel, String zipcode, String address) {
		this(email, name, password);
		this.age = age;
		this.gender = gender;
		this.tel = tel;
		this.zipcode = zipcode;
		this.address = address;
		this.role = NORMAL_USER;
	}
	
	public Member(String email, String name, String password, int age, String gender, String tel, String zipcode, String address, int role) {
		this(email, name, password, age, gender, tel, zipcode, address);
		this.role = role;
	}

	@Override
	public String toString() {
		return "Member [email=" + email + ", name=" + name + ", password=" + password + ", age=" + age + ", gender="
				+ gender + ", tel=" + tel + ", zipcode=" + zipcode + ", address=" + address + "]";
	}

	// Getters
	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public int getAge() {
		return age;
	}

	public String getGender() {
		return gender;
	}

	public String getTel() {
		return tel;
	}

	public String getZipcode() {
		return zipcode;
	}

	public String getAddress() {
		return address;
	}
	
	public int getRole() {
		return role;
	}
	
	public int getCheck() {
		return check;
	}

	// Setters
	public void setEmail(String email) {
		this.email = email;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public void setRole(int role) {
		this.role = role;
	}
	
	public void setCheck(int check) {
		this.check = check;
	}
	
}
