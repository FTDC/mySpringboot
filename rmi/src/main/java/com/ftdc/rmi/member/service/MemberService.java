package com.ftdc.rmi.member.service;

import com.ftdc.rmi.member.dao.MemberDao;
import com.ftdc.rmi.member.iface.MemberInterface;
import com.ftdc.rmi.member.pojo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MemberService implements MemberInterface {

    @Autowired
    private MemberDao memberDao;


    @Override
    public int saveMember(Member member) {
        return memberDao.saveMember(member);
    }


    @Override
    public Member getMember(String memberId) {
        Member member = memberDao.getMember(memberId);

        return member;
    }
}
