package com.wuwei.homework.service;

import com.wuwei.homework.entity.Comment;
import com.wuwei.homework.vo.CommentsVo;

import java.util.List;

public interface CommentService {

    List<CommentsVo> getComments();

    void saveComment(Comment comment);

}
