package com.lyle.train.member.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyle.train.common.context.LoginMemberContext;
import com.lyle.train.common.resp.PageResp;
import com.lyle.train.common.util.SnowUtil;
import com.lyle.train.member.domain.Passenger;
import com.lyle.train.member.mapper.PassengerMapper;
import com.lyle.train.member.req.PassengerQueryReq;
import com.lyle.train.member.req.PassengerSaveReq;
import com.lyle.train.member.resp.PassengerQueryResp;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PassengerService {

    @Resource
    private PassengerMapper passengerMapper;

    private static final Logger LOG = LoggerFactory.getLogger(PassengerService.class);


//    前端只传了3个字段，其它都是空的 新增也是一样 其它字段是后端设置的
    public void save(PassengerSaveReq req) {
        DateTime now = DateTime.now();
        Passenger passenger = BeanUtil.copyProperties(req, Passenger.class);
        if (ObjectUtil.isNull(passenger.getId())) {
            passenger.setMemberId(LoginMemberContext.getId());
            passenger.setId(SnowUtil.getSnowflakeNextId());
            passenger.setCreateTime(now);
            passenger.setUpdateTime(now);
            passengerMapper.insert(passenger);
        } else {
            passenger.setUpdateTime(now);
            passengerMapper.updateById(passenger);
        }

    }

//    public List<PassengerQueryResp> queryList(PassengerQueryReq req) {
//        PassengerExample passengerExample = new PassengerExample();
//        PassengerExample.Criteria criteria = passengerExample.createCriteria();
//        if (ObjectUtil.isNotNull(req.getMemberId())) {
//            criteria.andMemberIdEqualTo(req.getMemberId());
//        }
//        List<Passenger> passengerList = passengerMapper.selectByExample(passengerExample);
//        return BeanUtil.copyToList(passengerList, PassengerQueryResp.class);
//    }
    public PageResp<PassengerQueryResp> queryList(PassengerQueryReq req) {
        LOG.info("查询页码：{}", req.getPage());
        LOG.info("每页条数：{}", req.getSize());


        if (ObjectUtil.isNotNull(req.getMemberId())) {
//            List<Passenger> passengerList = passengerMapper.selectList(null );
            Page<Passenger> page = new Page<>(req.getPage(),req.getSize());
            page.addOrder(OrderItem.desc("id"));
            passengerMapper.selectPage(page,null);

            LOG.info("总行数：{}", page.getTotal());
            LOG.info("总页数：{}", page.getPages());
            System.out.println(page.getPages());
            List<PassengerQueryResp> list = BeanUtil.copyToList( page.getRecords(), PassengerQueryResp.class);
            PageResp<PassengerQueryResp> pageResp = new PageResp<>();
            pageResp.setTotal(page.getTotal());
            pageResp.setList(list);
            return pageResp;
        }
        return null;
    }

    public void delete(long id) {
        passengerMapper.deleteById(id);
    }
}
