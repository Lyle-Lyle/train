package com.lyle.train.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyle.train.business.mapper.TrainStationMapper;
import com.lyle.train.business.domain.TrainStation;
import com.lyle.train.common.resp.PageResp;
import com.lyle.train.common.util.SnowUtil;
import com.lyle.train.business.req.TrainStationQueryReq;
import com.lyle.train.business.req.TrainStationSaveReq;
import com.lyle.train.business.resp.TrainStationQueryResp;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainStationService {

    private static final Logger LOG = LoggerFactory.getLogger(TrainStationService.class);

    @Resource
    private TrainStationMapper trainStationMapper;

    public void save(TrainStationSaveReq req) {
        DateTime now = DateTime.now();
        TrainStation trainStation = BeanUtil.copyProperties(req, TrainStation.class);
        if (ObjectUtil.isNull(trainStation.getId())) {
            trainStation.setId(SnowUtil.getSnowflakeNextId());
            trainStation.setCreateTime(now);
            trainStation.setUpdateTime(now);
            trainStationMapper.insert(trainStation);
        } else {
            trainStation.setUpdateTime(now);
            trainStationMapper.updateById(trainStation);
        }
    }

    public PageResp<TrainStationQueryResp> queryList(TrainStationQueryReq req) {
        LOG.info("查询页码：{}", req.getPage());
        LOG.info("每页条数：{}", req.getSize());
        Page<TrainStation> page = new Page<>(req.getPage(),req.getSize());
        page.addOrder(OrderItem.asc("train_code"), OrderItem.asc("station_index"));


        if (ObjectUtil.isEmpty(req.getTrainCode())) {
            trainStationMapper.selectPage(page, null);
        } else {
            System.out.println(req.getTrainCode());
            QueryWrapper<TrainStation> queryWrapper = new QueryWrapper<>();
            QueryWrapper<TrainStation> trainCode = queryWrapper.eq("train_code", req.getTrainCode());
            trainStationMapper.selectPage(page, trainCode);
        }

        LOG.info("总行数：{}", page.getTotal());
        LOG.info("总页数：{}", page.getPages());
        System.out.println(page.getPages());
        List<TrainStationQueryResp> list = BeanUtil.copyToList( page.getRecords(), TrainStationQueryResp.class);
        PageResp<TrainStationQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(page.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    public void delete(Long id) {
        trainStationMapper.deleteById(id);
    }

    public List<TrainStation> selectByTrainCode(String trainCode) {
        QueryWrapper<TrainStation> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("index");
        wrapper.eq("train_code",trainCode);
        return trainStationMapper.selectList(wrapper);
    }
}