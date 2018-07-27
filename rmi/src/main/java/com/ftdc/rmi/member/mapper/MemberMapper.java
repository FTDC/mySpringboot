package com.ftdc.rmi.member.mapper;

import com.ftdc.rmi.member.pojo.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface MemberMapper {

    // 写入用户信息
    public int saveMember(@Param("member") Member member);

    // 查询用户
    public Member getMember(@Param("memberId") String memberId);

}
