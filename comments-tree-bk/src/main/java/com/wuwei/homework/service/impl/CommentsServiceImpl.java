package com.wuwei.homework.service.impl;

import com.wuwei.homework.entity.Comment;
import com.wuwei.homework.mapper.CommentMapper;
import com.wuwei.homework.service.CommentService;
import com.wuwei.homework.vo.CommentsVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentsServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public List<CommentsVo> getComments() {
        //查询所有评论，按ID倒序排列
        List<CommentsVo> comments = commentMapper.getComments();
        //递归生成树
        return assembleCommentsTree(comments);
    }

    @Override
    public void saveComment(Comment comment) {
        //组装本条评论的path路径，获取父评论
        Comment pComment = commentMapper.getCommentById(comment.getPid());
        if(pComment != null){
            //父评论的id和path路径用"/"组织期新的路径
            if(StringUtils.isBlank(pComment.getPath())){
                comment.setPath(pComment.getId()+"");
            }else{
                comment.setPath(pComment.getPath()+"/"+ pComment.getId());
            }
        }
        commentMapper.insertRecord(comment);
    }


    private List<CommentsVo> assembleCommentsTree(List<CommentsVo> comments){
        //根节点
        List<CommentsVo> root = new ArrayList<>();
        //剩余的comments
        List<CommentsVo> leftComments = new ArrayList<>();
        comments.forEach(c->{
            //转换时间格式
            if(c.getCreatetime() != null ){
                c.setTime( dateFormat.format(c.getCreatetime()));
            }
            if(c.getPid() == 0) {
                root.add(c);
            }else{
                leftComments.add(c);
            }
        });
        //递归所有子节点
        if(! CollectionUtils.isEmpty(root)){
            root.forEach(p->{
                p.setChildList(this.getChildList(p.getId(), leftComments));
            });
        }
        return root;
    }

    private List<CommentsVo> getChildList(int pid, List<CommentsVo> comments){
        //父节点
        List<CommentsVo> parentComments = new ArrayList<>();
        //剩余的comments
        List<CommentsVo> leftComments = new ArrayList<>();
        comments.forEach(c->{
            if(pid == c.getPid()){
                parentComments.add(c);
            }else{
                leftComments.add(c);
            }
        });
        if(! CollectionUtils.isEmpty(parentComments)){
            parentComments.forEach(p->{
                p.setChildList(this.getChildList(p.getId(), leftComments));
            });
        }
        return parentComments;
    }

}
