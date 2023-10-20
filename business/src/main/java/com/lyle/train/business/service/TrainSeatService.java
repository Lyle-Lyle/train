package com.lyle.train.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyle.train.business.domain.TrainCarriage;
import com.lyle.train.business.enums.SeatColEnum;
import com.lyle.train.common.resp.PageResp;
import com.lyle.train.common.util.SnowUtil;
import com.lyle.train.business.domain.TrainSeat;
import com.lyle.train.business.mapper.TrainSeatMapper;
import com.lyle.train.business.req.TrainSeatQueryReq;
import com.lyle.train.business.req.TrainSeatSaveReq;
import com.lyle.train.business.resp.TrainSeatQueryResp;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TrainSeatService {

    private static final Logger LOG = LoggerFactory.getLogger(TrainSeatService.class);

    @Resource
    private TrainSeatMapper trainSeatMapper;

    @Resource
    private TrainCarriageService trainCarriageService;

    public void save(TrainSeatSaveReq req) {
        DateTime now = DateTime.now();
        TrainSeat trainSeat = BeanUtil.copyProperties(req, TrainSeat.class);
        if (ObjectUtil.isNull(trainSeat.getId())) {
            trainSeat.setId(SnowUtil.getSnowflakeNextId());
            trainSeat.setCreateTime(now);
            trainSeat.setUpdateTime(now);
            trainSeatMapper.insert(trainSeat);
        } else {
            trainSeat.setUpdateTime(now);
            trainSeatMapper.updateById(trainSeat);
        }
    }

    public PageResp<TrainSeatQueryResp> queryList(TrainSeatQueryReq req) {
        LOG.info("查询页码：{}", req.getPage());
        LOG.info("每页条数：{}", req.getSize());
        Page<TrainSeat> page = new Page<>(req.getPage(),req.getSize());
        page.addOrder(OrderItem.desc("id"));
        trainSeatMapper.selectPage(page, null);

        LOG.info("总行数：{}", page.getTotal());
        LOG.info("总页数：{}", page.getPages());
        System.out.println(page.getPages());
        List<TrainSeatQueryResp> list = BeanUtil.copyToList( page.getRecords(), TrainSeatQueryResp.class);
        PageResp<TrainSeatQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(page.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    public void delete(Long id) {
        trainSeatMapper.deleteById(id);
    }

    @Transactional
    public void genTrainSeat(String trainCode) {
        DateTime now = DateTime.now();
        // 清空当前车次下的所有座位记录
        QueryWrapper<TrainSeat> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("train_code", trainCode);
        trainSeatMapper.delete(queryWrapper);

        // 查找当前车次下的所有的车厢
        List<TrainCarriage> carriageList = trainCarriageService.selectByTrainCode(trainCode);
        LOG.info("numbers of seats in car", carriageList.size());
        // 循环生成每个车厢的座位
        for (TrainCarriage trainCarriage : carriageList) {

            // 拿到车厢数据 航速，座位类型（得到列数）
            Integer rowCount = trainCarriage.getRowCount();
            String seatType = trainCarriage.getSeatType();
            int seatIndex = 1;

            // 根据车厢的座位类型，筛选出所有的列，
            List<SeatColEnum> colEnumList = SeatColEnum.getColsByType(seatType);
            LOG.info("cols by seat type", colEnumList);
            // 循环行数
            for (int row = 1; row < rowCount; row++) {
                //循环列数
                for (SeatColEnum seatColEnum : colEnumList) {
                    TrainSeat trainSeat = new TrainSeat();
                    trainSeat.setId(SnowUtil.getSnowflakeNextId());
                    trainSeat.setTrainCode(trainCode);
                    trainSeat.setCarIndex(trainCarriage.getCarIndex());
                    trainSeat.setRowNum(StrUtil.fillBefore(String.valueOf(row), '0', 2));
                    trainSeat.setColNum(seatColEnum.getCode());
                    trainSeat.setSeatType(seatType);
                    trainSeat.setCarriageSeatIndex(seatIndex++);
                    trainSeat.setCreateTime(now);
                    trainSeat.setUpdateTime(now);
                    trainSeatMapper.insert(trainSeat);
                }
                // 构造座位
            }



        }

    }

    public List<TrainSeat> selectByTrainCode(String trainCode) {
        QueryWrapper<TrainSeat> wrapper=new QueryWrapper<>();
        wrapper.orderByAsc("id");
        wrapper.eq("train_code",trainCode);
        return trainSeatMapper.selectList(wrapper);
    }


}
