package com.ximo.springbootblogmaster.domain.es;

import com.ximo.springbootblogmaster.domain.Blog;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * @author 朱文赵
 * @date 2018/4/8
 * @description es博客.  MediaType 转为 XML
 */
@Document(indexName = "blog", type = "blog")
@XmlRootElement
@Data
public class EsBlog implements Serializable {
 
	private static final long serialVersionUID = 1L;


	/** 主键id*/
	@Id
	private String id;

	/** 博客id*/
	@Field(index = false)
	private Long blogId;
 
	private String title;
 
	private String summary;
 
	private String content;
 
	@Field(index = false)
	private String username;
	@Field(index = false)
	private String avatar;
	@Field(index = false)
	private Timestamp createTime;

	/** 访问量、阅读量*/
	@Field(index = false)
	private Integer readSize = 0;
	/** 评论量*/
	@Field(index = false)
	private Integer commentSize = 0;

	/** 点赞量 */
	@Field(index = false)
	private Integer voteSize = 0;

	/** 标签*/
	private String tags;

	/** JPA 的规范要求无参构造函数；设为 protected 防止直接使用  */
	protected EsBlog() {
	}

	public EsBlog(String title, String content) {
		this.title = title;
		this.content = content;
	}
	
	public EsBlog(Long blogId, String title, String summary, String content, String username, String avatar,Timestamp createTime,
			Integer readSize,Integer commentSize, Integer voteSize , String tags) {
		this.blogId = blogId;
		this.title = title;
		this.summary = summary;
		this.content = content;
		this.username = username;
		this.avatar = avatar;
		this.createTime = createTime;
		this.readSize = readSize;
		this.commentSize = commentSize;
		this.voteSize = voteSize;
		this.tags = tags;
	}
	
	public EsBlog(Blog blog){
		this.blogId = blog.getId();
		this.title = blog.getTitle();
		this.summary = blog.getSummary();
		this.content = blog.getContent();
		this.username = blog.getUser().getUsername();
		this.avatar = blog.getUser().getAvatar();
		this.createTime = blog.getCreateTime();
		this.readSize = blog.getReadSize();
		this.commentSize = blog.getCommentSize();
		this.voteSize = blog.getVoteSize();
		this.tags = blog.getTags();
	}
 
	public void update(Blog blog){
		this.blogId = blog.getId();
		this.title = blog.getTitle();
		this.summary = blog.getSummary();
		this.content = blog.getContent();
		this.username = blog.getUser().getUsername();
		this.avatar = blog.getUser().getAvatar();
		this.createTime = blog.getCreateTime();
		this.readSize = blog.getReadSize();
		this.commentSize = blog.getCommentSize();
		this.voteSize = blog.getVoteSize();
		this.tags = blog.getTags();
	}

}