package com.wuwei.homework.entity;


import lombok.Data;

import java.util.Date;

@Data
public class Comment {

    private int id;
    private String msg;
    private int userId;
    private int pid;
    private String path;
    private Date createtime;

}
