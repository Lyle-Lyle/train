package com.lyle.train.member.controller;

import com.lyle.train.common.resp.CommonResp;
import com.lyle.train.member.req.MemberLoginReq;
import com.lyle.train.member.req.MemberRegisterReq;
import com.lyle.train.member.req.MemberSendCodeReq;
import com.lyle.train.member.resp.MemberLoginResp;
import com.lyle.train.member.service.MemberService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Resource
    private MemberService memberService;

    @GetMapping("/count")
    public CommonResp<Integer> count() {
        int count = memberService.count();
        CommonResp<Integer> commonResp = new CommonResp<>();
        commonResp.setContent(count);
        return commonResp;
    }

//    @Valid校验开关
    @PostMapping("/register")
    public CommonResp<Long> register(@Valid MemberRegisterReq req) {
        long register = memberService.register(req);
        CommonResp<Long> commonResp = new CommonResp<>();
        commonResp.setContent(register);
        return commonResp;
    }

    @PostMapping("/send-code")
    public CommonResp<Long> sendCode(@Valid @RequestBody MemberSendCodeReq req) {
                memberService.sendCode(req);
                return new CommonResp<>();
    }

    @PostMapping("/login")
    public CommonResp<MemberLoginResp> login(@Valid @RequestBody MemberLoginReq req) {
                MemberLoginResp resp = memberService.login(req);
                return new CommonResp<>(resp);
            }



}
