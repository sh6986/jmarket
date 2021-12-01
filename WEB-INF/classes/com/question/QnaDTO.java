package com.question;

public class QnaDTO {
	private int listNum;
	public int getListNum() {
		return listNum;
	}
	public void setListNum(int listNum) {
		this.listNum = listNum;
	}
	private int num;  //글번호
	private String category;  //분류
	private String subject;  //문의제목
	private String content;  //문의내용
	private String created;  //문의날짜
	private String an_created;  //답변날짜
	private int status;  //처리상태 (0:접수완료, 1:답변완료)
	private String an_content;  //답변내용
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public String getAn_created() {
		return an_created;
	}
	public void setAn_created(String an_created) {
		this.an_created = an_created;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getAn_content() {
		return an_content;
	}
	public void setAn_content(String an_content) {
		this.an_content = an_content;
	}
}
