package com.ftdc.rmi.member.iface;

import com.ftdc.rmi.member.pojo.Member;

public interface MemberInterface {
    /**
     * 保存会员
     *
     * @param member 会员
     */
    public int saveMember(Member member);


    public Member getMember(String MemberId);
}
