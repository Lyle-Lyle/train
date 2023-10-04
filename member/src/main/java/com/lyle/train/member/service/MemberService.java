package com.lyle.train.member.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyle.train.common.exception.BusinessException;
import com.lyle.train.common.exception.BusinessExceptionEnum;
import com.lyle.train.common.util.JwtUtil;
import com.lyle.train.common.util.SnowUtil;
import com.lyle.train.member.domain.Member;
import com.lyle.train.member.mapper.MemberMapper;
import com.lyle.train.member.req.MemberLoginReq;
import com.lyle.train.member.req.MemberRegisterReq;
import com.lyle.train.member.req.MemberSendCodeReq;
import com.lyle.train.member.resp.MemberLoginResp;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.management.Query;

@Service
public class MemberService {

    private static final Logger LOG = LoggerFactory.getLogger(MemberService.class);

    @Resource
    private MemberMapper memberMapper;


    public int count() {
        return Math.toIntExact(memberMapper.selectCount(null));

    }

    public long register(MemberRegisterReq req) {
        String mobile = req.getMobile();
        // 首先需要检查数据库中是否已经存在
//        MemberExample memberExample = new MemberExample();
//        memberExample.createCriteria().andMobileEqualTo(mobile);
//        List<Member> list = memberMapper.selectByExample(memberExample);
        Member memberDB = selectByMobile(mobile);
        // 判断是否为空
        if (ObjectUtil.isNull(memberDB)) {
//            return list.get(0).getId();
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_EXIST);
        }

        Member member = new Member();
        member.setId(System.currentTimeMillis());
        member.setMobile(mobile);
        memberMapper.insert(member);
        return member.getId();
    }


    public void sendCode(MemberSendCodeReq req) {
        String mobile = req.getMobile();
//        MemberExample memberExample = new MemberExample();
//        memberExample.createCriteria().andMobileEqualTo(mobile);
//        List<Member> list = memberMapper.selectByExample(memberExample);
        Member memberDB = selectByMobile(mobile);
        // 如果手机号不存在，则插入一条记录
        if (ObjectUtil.isNull(memberDB)) {
            LOG.info("手机号不存在，插入一条记录");
            Member member = new Member();
            member.setId(SnowUtil.getSnowflakeNextId());
            member.setMobile(mobile);
            memberMapper.insert(member);
        } else {
            LOG.info("手机号存在，不插入记录");
        }

        // 生成验证码
        // String code = RandomUtil.randomString(4);
        String code = "8888";
        LOG.info("生成短信验证码：{}", code);

        // 保存短信记录表：手机号，短信验证码，有效期，是否已使用，业务类型，发送时间，使用时间
        LOG.info("保存短信记录表");

        // 对接短信通道，发送短信
        LOG.info("对接短信通道");
    }

    public MemberLoginResp login(MemberLoginReq req) {
        String mobile = req.getMobile();
        String code = req.getCode();
        Member memberDB = selectByMobile(mobile);

        // 如果手机号不存在，则插入一条记录
        if (ObjectUtil.isNull(memberDB)) {
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_NOT_EXIST);
        }

        // 校验短信验证码不是一个高并发的动作，所以就不上缓存了
        if (!"8888".equals(code)) {
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_CODE_ERROR);
        }

        MemberLoginResp memberLoginResp = BeanUtil.copyProperties(memberDB, MemberLoginResp.class);
        String token = JwtUtil.createToken(memberLoginResp.getId(), memberLoginResp.getMobile());
        memberLoginResp.setToken(token);
        return memberLoginResp;
    }


    // 封装一下 根据手机号查用户
    private Member selectByMobile(String mobile) {
//        MemberExample memberExample = new MemberExample();
//        memberExample.createCriteria().andMobileEqualTo(mobile);
//        List<Member> list = memberMapper.selectByExample(memberExample);
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", mobile);
        Member member = memberMapper.selectOne(queryWrapper);
//        if (CollUtil.isEmpty(list)) {
//            return null;
//        } else {
//            return list.get(0);
//        }
        return member;
    }
}


