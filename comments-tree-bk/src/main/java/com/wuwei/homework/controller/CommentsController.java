package com.wuwei.homework.controller;

import com.wuwei.homework.api.R;
import com.wuwei.homework.entity.Comment;
import com.wuwei.homework.entity.User;
import com.wuwei.homework.service.CommentService;
import com.wuwei.homework.vo.CommentsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("comments")
public class CommentsController {


    @Autowired
    private CommentService commentService;


    /**
     * 保存评论，此方法参数User会被特殊处理，根据token生成当前用户
     * @param user
     * @param comment
     * @return
     */
    @PostMapping("")
    public R<String> saveComments(User user, @RequestBody Comment comment){
        comment.setUserId(user.getId());
        commentService.saveComment(comment);
        return R.data("succ");
    }

    /**
     * 获取所有评论，并组装成树
     * @return
     */
    @GetMapping("/list")
    public R<List<CommentsVo>> getComments(){
        return R.data(commentService.getComments());
    }

}
