package com.wuwei.homework;

import com.wuwei.homework.dto.UserDto;
import com.wuwei.homework.entity.Comment;
import com.wuwei.homework.security.JwtUtil;
import com.wuwei.homework.service.CommentService;
import com.wuwei.homework.service.impl.UserService;
import com.wuwei.homework.vo.CommentsVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class HomeworkApplicationTests {



    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;


    /**
     * 测试JwtUtil
     */
    @Test
    public void testTokenUtils(){
        JwtUtil util = new JwtUtil();
        Map<String, Object> map = new HashMap<>();
        map.put("username", "david");
        map.put("password", "123456");
        map.put("age", 20);
        String jwtToken = util.encode("1",  map);
        System.out.println(jwtToken);
        util.isVerify(jwtToken);
        System.out.println("合法");
        util.decode(jwtToken).entrySet().forEach((entry) -> {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        });
    }

    @Test
    public void testGetComments(){
        //测试获取评论的方法
        List<CommentsVo> voList = commentService.getComments();
        System.out.println(voList);

    }

    @Test
    public void submitComments(){
        Comment comment = new Comment();
        comment.setMsg("this comment comes from test case !!!!!");
        comment.setUserId(1);
        comment.setPid(1);
        commentService.saveComment(comment);
    }


    /**
     * 登录测试
     */
    @Test
    public void testLogin(){
        String token = userService.login("david","123456");
        System.out.println(token);
    }


    @Test
    public void testRegister(){
        //注册新用户，查看数据库，并第二次运行
        UserDto user = new UserDto();
        user.setEmail("aaa@163.com");
        user.setPswd("123456");
        user.setUsername("testuser");
        try {
            userService.registerUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
