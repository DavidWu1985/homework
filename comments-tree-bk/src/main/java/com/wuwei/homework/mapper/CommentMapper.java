package com.wuwei.homework.mapper;


import com.wuwei.homework.entity.Comment;
import com.wuwei.homework.vo.CommentsVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentMapper {

    @Select("select * from comment t where t.pid = 0 order by createtime desc")
    List<Comment> getCommentsLevel1();

    @Select("select * from comment t where t.path like '${pid}%'")
    List<Comment> getCommentsChild(String pid);

    //查询所有评论，并按id倒序排列，id安自增自动生成
    @Select("select t.*, u.username from comment t, user u where t.userId = u.id order by t.id desc")
    List<CommentsVo> getComments();

    @Insert("insert into comment(msg, userId, pid, path, createtime) values('${comment.msg}', ${comment.userId}, ${comment.pid}, '${comment.path}', datetime('now'))")
    void insertRecord(@Param("comment") Comment comment);

    @Select("select * from comment t where t.id = ${pid}")
    Comment getCommentById(int pid);
}
