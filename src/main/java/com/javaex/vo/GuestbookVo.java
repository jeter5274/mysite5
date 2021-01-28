package com.javaex.vo;

public class GuestbookVo {

	//필드
	int no;
	String name, password, content, regDate;

	//생성자
	public GuestbookVo() {}

	
	//메소드 - getter/setter
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	
	//메소드 - 일반
	@Override
	public String toString() {
		return "GBVo [no=" + no + ", name=" + name + ", password=" + password + ", content=" + content + ", regDate="
				+ regDate + "]";
	}
	
}
