package com.lyle.train.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyle.train.business.enums.SeatColEnum;
import com.lyle.train.business.domain.TrainCarriage;
import com.lyle.train.business.mapper.TrainCarriageMapper;
import com.lyle.train.common.resp.PageResp;
import com.lyle.train.common.util.SnowUtil;
import com.lyle.train.business.req.TrainCarriageQueryReq;
import com.lyle.train.business.req.TrainCarriageSaveReq;
import com.lyle.train.business.resp.TrainCarriageQueryResp;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainCarriageService {

    private static final Logger LOG = LoggerFactory.getLogger(TrainCarriageService.class);

    @Resource
    private TrainCarriageMapper trainCarriageMapper;

    public void save(TrainCarriageSaveReq req) {
        DateTime now = DateTime.now();

        // 自动计算出列数和总座位数
        List<SeatColEnum> seatColEnums = SeatColEnum.getColsByType(req.getSeatType());
        req.setColCount(seatColEnums.size());
        req.setSeatCount(req.getColCount() * req.getRowCount());


        TrainCarriage trainCarriage = BeanUtil.copyProperties(req, TrainCarriage.class);
        if (ObjectUtil.isNull(trainCarriage.getId())) {
            trainCarriage.setId(SnowUtil.getSnowflakeNextId());
            trainCarriage.setCreateTime(now);
            trainCarriage.setUpdateTime(now);
            trainCarriageMapper.insert(trainCarriage);
        } else {
            trainCarriage.setUpdateTime(now);
            trainCarriageMapper.updateById(trainCarriage);
        }
    }

    public PageResp<TrainCarriageQueryResp> queryList(TrainCarriageQueryReq req) {
        LOG.info("查询页码：{}", req.getPage());
        LOG.info("每页条数：{}", req.getSize());
        Page<TrainCarriage> page = new Page<>(req.getPage(),req.getSize());
        page.addOrder(OrderItem.desc("id"));
        trainCarriageMapper.selectPage(page, null);

        LOG.info("总行数：{}", page.getTotal());
        LOG.info("总页数：{}", page.getPages());
        System.out.println(page.getPages());
        List<TrainCarriageQueryResp> list = BeanUtil.copyToList( page.getRecords(), TrainCarriageQueryResp.class);
        PageResp<TrainCarriageQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(page.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    public void delete(Long id) {
        trainCarriageMapper.deleteById(id);
    }

    // 根据车次查询车厢
    public List<TrainCarriage> selectByTrainCode(String trainCode) {
        QueryWrapper<TrainCarriage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("train_code", trainCode);
        queryWrapper.orderByAsc("car_index");
        return trainCarriageMapper.selectList(queryWrapper);
    }
}