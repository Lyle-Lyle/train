package com.lyle.train.member.controller;

import com.lyle.train.common.context.LoginMemberContext;
import com.lyle.train.common.resp.CommonResp;
import com.lyle.train.common.resp.PageResp;
import com.lyle.train.member.req.PassengerQueryReq;
import com.lyle.train.member.req.PassengerSaveReq;
import com.lyle.train.member.resp.PassengerQueryResp;
import com.lyle.train.member.service.PassengerService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/passenger")
public class PassengerController {

    @Resource
    private PassengerService passengerService;

    @PostMapping("/save")
    public CommonResp<Object> save(@Valid @RequestBody PassengerSaveReq req) {
        passengerService.save(req);
        return new CommonResp<>();
    }


    @GetMapping("/query-list")
    public CommonResp<PageResp<PassengerQueryResp>> queryList(@Valid PassengerQueryReq req) {
        req.setMemberId(LoginMemberContext.getId());
        PageResp<PassengerQueryResp> list = passengerService.queryList(req);
        return new CommonResp<>(list);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable long id) {
        passengerService.delete(id);
        return new CommonResp<>();
    }

}