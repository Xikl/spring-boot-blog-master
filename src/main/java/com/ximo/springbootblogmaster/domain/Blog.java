package com.ximo.springbootblogmaster.domain;

import com.github.rjeschke.txtmark.Processor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author 朱文赵
 * @date 2018/4/8
 * @description 博客.
 */
@Entity
@Data
@Document(indexName = "blog", type = "blog")
@NoArgsConstructor
@AllArgsConstructor
public class Blog implements Serializable {

    private static final long serialVersionUID = 4756516151363843515L;

    /** 主键 博客的唯一标识*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 博客标题 */
    @NotEmpty(message = "标题不能为空")
    @Size(min = 2, max = 50)
    @Column(nullable = false, length = 50)
    private String title;

    /** 博客摘要*/
    @NotEmpty(message = "摘要不能为空")
    @Size(min = 2, max = 300)
    @Column(nullable = false)
    private String summary;

    /**
     *  标签：@Lob 大对象，映射 MySQL 的 Long Text 类型
     *  懒加载 @Basic
     *  映射为字段，值不能为空 @Nullable
     *  存储的是markdown的内容
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @NotEmpty(message = "内容不能为空")
    @Size(min = 2)
    @Column(nullable = false)
    private String content;

    /**
     * 大对象，映射 MySQL 的 Long Text 类型
     * 懒加载
     * 映射为字段，值不能为空
     * 存储的是将 md 转为 html的内容
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @NotEmpty(message = "内容不能为空")
    @Size(min = 2)
    @Column(nullable = false)
    private String htmlContent;

    /** 用户一对一关联*/
    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    /** 由数据库自动创建时间 */
    @Column(nullable = false)
    @CreationTimestamp
    private Timestamp createTime;

    /** 访问量、阅读量*/
    @Column(name = "readSize")
    private Integer readSize = 0;

    /** 评论量 */
    @Column(name = "commentSize")
    private Integer commentSize = 0;

    /** 点赞量 */
    @Column(name = "voteSize")
    private Integer voteSize = 0;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "blog_comment", joinColumns = @JoinColumn(name = "blog_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "comment_id", referencedColumnName = "id"))
    private List<Comment> comments;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "blog_vote", joinColumns = @JoinColumn(name = "blog_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "vote_id", referencedColumnName = "id"))
    private List<Vote> votes;

    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "catalog_id")
    private Catalog catalog;

    /** 标签 */
    @Column(name = "tags", length = 100)
    private String tags;

    public Blog(String title, String summary, String content) {
        this.title = title;
        this.summary = summary;
        this.content = content;
    }

    public void setContent(String content) {
        this.content = content;
        //解析markdown的格式
        this.htmlContent = Processor.process(content);
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
        this.commentSize = this.comments.size();
    }

    /**
     * 添加评论
     * 同时修改评论量的大小
     *
     * @param comment 评论
     */
    public void addComment(Comment comment) {
        this.comments.add(comment);
        this.commentSize = this.comments.size();
    }

    /**
     * 删除评论
     *
     * @param commentId 评论id
     */
    public void removeComment(Long commentId) {
//        for (int index = 0; index < this.comments.size(); index++) {
//            if (comments.get(index).getId().equals(commentId)) {
//                this.comments.remove(index);
//                break;
//            }
//        }
        comments.removeIf(comment -> comment.getId().equals(commentId));
        this.commentSize = this.comments.size();
    }

    /**
     * 点赞
     *
     * @param vote 点赞
     * @return
     */
    public boolean addVote(Vote vote) {
        boolean isExist = false;
        // 判断重复
        for (Vote theVote : this.votes) {
            if (theVote.getUser().getId().equals(vote.getUser().getId())) {
                isExist = true;
                break;
            }
        }

        if (!isExist) {
            this.votes.add(vote);
            this.voteSize = this.votes.size();
        }

        return isExist;
    }

    /**
     * 取消点赞
     *
     * @param voteId  点赞id
     */
    public void removeVote(Long voteId) {
//        for (int index = 0; index < this.votes.size(); index++) {
//            if (this.votes.get(index).getId().equals(voteId)) {
//                this.votes.remove(index);
//                break;
//            }
//        }
//        for (Vote vote : this.votes) {
//            if (vote.getId().equals(voteId)) {
//                this.votes.remove(vote);
//                break;
//            }
//        }

        votes.removeIf(vote -> vote.getId().equals(voteId));
        this.voteSize = this.votes.size();
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
        this.voteSize = this.votes.size();
    }

}
