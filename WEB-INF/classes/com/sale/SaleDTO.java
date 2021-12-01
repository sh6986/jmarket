package com.sale;

public class SaleDTO {
	private int num; 
	private String name;
	private String pname;
	private int sprice;
	private String id;
	private int sold;
	private String subject;
	private String content;
	private String fileName1; 
	private String fileName2;
	private String fileName3;
	private int hitCount;
	private String created;
	
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	
	public String getName() {
		return name;
	}
	
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public int getSprice() {
		return sprice;
	}
	public void setSprice(int sprice) {
		this.sprice = sprice;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getSold() {
		return sold;
	}
	public void setSold(int sold) {
		this.sold = sold;
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
	
	public String getFileName1() {
		return fileName1;
	}
	public void setFileName1(String fileName1) {
		this.fileName1 = fileName1;
	}
	public String getFileName2() {
		return fileName2;
	}
	public void setFileName2(String fileName2) {
		this.fileName2 = fileName2;
	}
	public String getFileName3() {
		return fileName3;
	}
	public void setFileName3(String fileName3) {
		this.fileName3 = fileName3;
	}
	public int getHitCount() {
		return hitCount;
	}
	public void setHitCount(int hitCount) {
		this.hitCount = hitCount;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	@Override
	public String toString() {
		return "SaleDTO [num=" + num + ", name=" + name + ", pname=" + pname + ", sprice=" + sprice + ", id=" + id
				+ ", sold=" + sold + ", subject=" + subject + ", content=" + content + ", fileName1=" + fileName1
				+ ", fileName2=" + fileName2 + ", fileName3=" + fileName3 + ", hitCount=" + hitCount + ", created="
				+ created + "]";
	}
	
	

}