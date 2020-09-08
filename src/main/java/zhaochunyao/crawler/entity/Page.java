package zhaochunyao.crawler.entity;

import java.util.ArrayList;

//This entity class saves web page info. 
//list page 列表页；details page 楼盘详情页；comment page 评论页

public class Page {
	private String pageUrl; //当前页面的url，可能是列表页，也可能是详情页
	private String pageContent;
	private String houseName;
	private String houseId;
	private String city;
	private String commentPageUrl;
	private String allComments;
	private ArrayList<String> urlList=new ArrayList<String>();//列表页上通往下一页的url，和通往详情页 的url；如果是详情页，则本属性为空
	
	
	public ArrayList<String> getUrlList() {
		return urlList;
	}
	public void addToUrlList(String url) {
		this.urlList.add(url);
	}
	public String getPageUrl() {
		return pageUrl;
	}
	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPageContent() {
		return pageContent;
	}
	public void setPageContent(String pageContent) {
		this.pageContent = pageContent;
	}
	
	public String getHouseName() {
		return houseName;
	}
	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}
	public String getHouseId() {
		return houseId;
	}
	public void setHouseId(String houseId) {
		this.houseId = houseId;
	}
	public String getCommentPageUrl() {
		return commentPageUrl;
	}
	public void setCommentPageUrl(String commentPageUrl) {
		this.commentPageUrl = commentPageUrl;
	}
	public String getAllComments() {
		return allComments;
	}
	public void setAllComments(String allComments) {
		this.allComments = allComments;
	}

	
	
}
