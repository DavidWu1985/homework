package com.wuwei.homework.security;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;

public class JwtDefaultSubjectFactory extends DefaultWebSubjectFactory {

    @Override
    public Subject createSubject(SubjectContext context) {
        // 无状态的web, 不创建 session
        context.setSessionCreationEnabled(false);
        return super.createSubject(context);
    }

}
