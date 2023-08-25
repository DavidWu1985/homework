package com.wuwei.homework.vo;

import com.wuwei.homework.entity.Comment;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CommentsVo extends Comment {

    private String userName;

    private String time;

    private List<CommentsVo> childList = new ArrayList<>();
}
