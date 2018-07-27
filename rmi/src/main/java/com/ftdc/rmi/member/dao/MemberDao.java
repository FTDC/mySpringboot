package com.ftdc.rmi.member.dao;

import com.ftdc.rmi.member.mapper.MemberMapper;
import com.ftdc.rmi.member.pojo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class MemberDao {

    @Autowired
    private MemberMapper memberMapper;

    public int saveMember(Member member) {
        return memberMapper.saveMember(member);
    }

    public Member getMember(String memberId) {
        Member member = memberMapper.getMember(memberId);
        return member;
    }


}
