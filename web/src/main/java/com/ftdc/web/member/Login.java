package com.ftdc.web.member;


import com.alibaba.fastjson.JSONObject;
import com.ftdc.baseutil.util.S;
import com.ftdc.rmi.member.iface.MemberInterface;
import com.ftdc.rmi.member.pojo.Member;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
@Scope("prototype")
@RequestMapping("/")
public class Login {

//    public static final Logger logger = LoggerFactory.getLogger(PayNotifyResource.class);

    @Autowired
    private MemberInterface memberInterface;


    @RequestMapping(value = "/login")
    public String index(HttpServletRequest httpServletRequest) {

        String mobile = httpServletRequest.getParameter("mobile");
        String memberId = httpServletRequest.getParameter("memberId");

//        member.setCreateTime(System.currentTimeMillis());
//        member.setIdCardNo("420703798811166373");
//        member.setIsActive(1);
//        member.setLastLoginTime(System.currentTimeMillis());
//        member.setLoginCount(1);
//        member.setMemberId(S.getUuid());
//        member.setName("韩梅梅");
//        member.setMobile(mobile);
//        member.setUpdateTime(System.currentTimeMillis());

//        int uid  = memberInterface.saveMember(member);

        System.out.println("-------------------------------------------");
        memberInterface.getMember(memberId);
//        System.out.println(JSONObject.toJSONString(member));
        return "success";
    }


}
