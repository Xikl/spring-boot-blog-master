package com.ximo.springbootblogmaster.domain.es;

import com.ximo.springbootblogmaster.domain.Blog;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * @author 朱文赵
 * @date 2018/4/8
 * @description es博客.  MediaType 转为 XML
 */
@Document(indexName = "blog", type = "blog")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EsBlog implements Serializable {

	private static final long serialVersionUID = -8270068440095839719L;

	/** 主键id*/
	@Id
	@Field(type = FieldType.keyword)
	private String id;
	/** 博客id*/
	@Field(type = FieldType.Long)
	private Long blogId;
	/** 标题*/
	@Field(type = FieldType.text)
	private String title;
	/** 摘要 */
	@Field(type = FieldType.text)
	private String summary;
 	/** 内容 */
	@Field(type = FieldType.text)
	private String content;
 	/** 姓名 keyword 支持聚和 不支持分词 */
 	@Field(type = FieldType.keyword)
	private String username;
	/** 图片 */
	@Field(index = false, type = FieldType.text)
	private String avatar;
	/** 创建时间 */
	@Field(index = false, type = FieldType.Date)
	private Timestamp createTime;
	/** 访问量、阅读量*/
	@Field(index = false, type = FieldType.Integer)
	private Integer readSize = 0;
	/** 评论量*/
	@Field(index = false, type = FieldType.Integer)
	private Integer commentSize = 0;
	/** 点赞量 */
	@Field(index = false, type = FieldType.Integer)
	private Integer voteSize = 0;
	/** 标签 允许室友fieldData 类型为text 允许分词 不支持聚和*/
	@Field(searchAnalyzer = "ik_smart", analyzer = "ik_smart", fielddata = true, type = FieldType.text)
	private String tags;

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

	/**
	 * 将blog更新到esBlog中
	 *
	 * @param blog 博客实体类
	 */
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
