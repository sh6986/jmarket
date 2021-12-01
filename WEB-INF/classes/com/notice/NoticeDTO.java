package com.notice;

public class NoticeDTO {
	private int num, listnum;
	private int notice;
	private String name;
	private String id;
	private String title;
	private String content;
	private String afilename;
	private String bfilename;
	private long filesize;
	private int hitcount;
	private String created;
	
	private long gap;

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getListnum() {
		return listnum;
	}

	public void setListnum(int listnum) {
		this.listnum = listnum;
	}

	public int getNotice() {
		return notice;
	}

	public void setNotice(int notice) {
		this.notice = notice;
	}

	public String getName() {
		return name;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAfilename() {
		return afilename;
	}

	public void setAfilename(String afilename) {
		this.afilename = afilename;
	}

	public String getBfilename() {
		return bfilename;
	}

	public void setBfilename(String bfilename) {
		this.bfilename = bfilename;
	}

	public long getFilesize() {
		return filesize;
	}

	public void setFilesize(long filesize) {
		this.filesize = filesize;
	}

	public int getHitcount() {
		return hitcount;
	}

	public void setHitcount(int hitcount) {
		this.hitcount = hitcount;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public long getGap() {
		return gap;
	}

	public void setGap(long gap) {
		this.gap = gap;
	}
	

}
