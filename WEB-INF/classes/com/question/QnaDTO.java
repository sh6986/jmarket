package com.question;

public class QnaDTO {
	private int listNum;
	public int getListNum() {
		return listNum;
	}
	public void setListNum(int listNum) {
		this.listNum = listNum;
	}
	private int num;  //�۹�ȣ
	private String category;  //�з�
	private String subject;  //��������
	private String content;  //���ǳ���
	private String created;  //���ǳ�¥
	private String an_created;  //�亯��¥
	private int status;  //ó������ (0:�����Ϸ�, 1:�亯�Ϸ�)
	private String an_content;  //�亯����
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
