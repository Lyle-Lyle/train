package com.lyle.train.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.ObjectUtil;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyle.train.business.domain.DailyTrain;
import com.lyle.train.business.domain.DailyTrainStation;
import com.lyle.train.business.domain.TrainStation;
import com.lyle.train.business.resp.DailyTrainQueryResp;
import com.lyle.train.common.resp.PageResp;
import com.lyle.train.common.util.SnowUtil;
import com.lyle.train.business.domain.DailyTrainTicket;
import com.lyle.train.business.mapper.DailyTrainTicketMapper;
import com.lyle.train.business.req.DailyTrainTicketQueryReq;
import com.lyle.train.business.req.DailyTrainTicketSaveReq;
import com.lyle.train.business.resp.DailyTrainTicketQueryResp;
import com.lyle.train.business.enums.SeatTypeEnum;
import com.lyle.train.business.enums.TrainTypeEnum;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DailyTrainTicketService {

    private static final Logger LOG = LoggerFactory.getLogger(DailyTrainTicketService.class);

    @Resource
    private DailyTrainTicketMapper dailyTrainTicketMapper;

    @Resource
    private TrainStationService trainStationService;

    @Resource
    private DailyTrainSeatService dailyTrainSeatService;

    public void save(DailyTrainTicketSaveReq req) {
        DateTime now = DateTime.now();
        DailyTrainTicket dailyTrainTicket = BeanUtil.copyProperties(req, DailyTrainTicket.class);
        if (ObjectUtil.isNull(dailyTrainTicket.getId())) {
            dailyTrainTicket.setId(SnowUtil.getSnowflakeNextId());
            dailyTrainTicket.setCreateTime(now);
            dailyTrainTicket.setUpdateTime(now);
            dailyTrainTicketMapper.insert(dailyTrainTicket);
        } else {
            dailyTrainTicket.setUpdateTime(now);
            dailyTrainTicketMapper.updateById(dailyTrainTicket);
        }
    }

    public PageResp<DailyTrainTicketQueryResp> queryList(DailyTrainTicketQueryReq req) {
        LOG.info("查询页码：{}", req.getPage());
        LOG.info("每页条数：{}", req.getSize());
        Page<DailyTrainTicket> page = new Page<>(req.getPage(),req.getSize());
        page.addOrder(OrderItem.desc("id"));
        dailyTrainTicketMapper.selectPage(page, null);

        LOG.info("总行数：{}", page.getTotal());
        LOG.info("总页数：{}", page.getPages());
        System.out.println(page.getPages());
        List<DailyTrainTicketQueryResp> list = BeanUtil.copyToList( page.getRecords(), DailyTrainTicketQueryResp.class);
        PageResp<DailyTrainTicketQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(page.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    public void delete(Long id) {
        dailyTrainTicketMapper.deleteById(id);
    }

    @Transactional
    public void genDaily(DailyTrain dailyTrain, Date date, String trainCode) {
        LOG.info("生成日期【{}】车次【{}】的余票信息开始", DateUtil.formatDate(date), trainCode);

        // 删除某日某车次的余票信息
        QueryWrapper<DailyTrainTicket> queryWrapper = new QueryWrapper<DailyTrainTicket>();
        Map<String, Object> params = new HashMap<>();
        params.put("date", date);
        params.put("train_code", trainCode);
        queryWrapper.allEq(params);
        dailyTrainTicketMapper.delete(queryWrapper);

        // 查出某车次的所有的车站信息
        List<TrainStation> stationList = trainStationService.selectByTrainCode(trainCode);
        if (CollUtil.isEmpty(stationList)) {
            LOG.info("该车次没有车站基础数据，生成该车次的余票信息结束");
            return;
        }

        DateTime now = DateTime.now();
        for (int i = 0; i < stationList.size(); i++) {
            // 得到出发站
            TrainStation trainStationStart = stationList.get(i);
            BigDecimal sumKM = BigDecimal.ZERO;
            for (int j = (i + 1); j < stationList.size(); j++) {
                TrainStation trainStationEnd = stationList.get(j);
                sumKM = sumKM.add(trainStationEnd.getKm());

                DailyTrainTicket dailyTrainTicket = new DailyTrainTicket();

                dailyTrainTicket.setId(SnowUtil.getSnowflakeNextId());
                dailyTrainTicket.setDate(date);
                dailyTrainTicket.setTrainCode(trainCode);
                dailyTrainTicket.setOrigin(trainStationStart.getName());
                dailyTrainTicket.setDeparture(trainStationStart.getDeparture());
                dailyTrainTicket.setStartIndex(trainStationStart.getStationIndex());
                dailyTrainTicket.setDestination(trainStationEnd.getName());
                dailyTrainTicket.setArrival(trainStationEnd.getArrival());
                dailyTrainTicket.setEndIndex(trainStationEnd.getStationIndex());

                int Bu = dailyTrainSeatService.countSeat(date, trainCode, SeatTypeEnum.BU.getCode());
                int Ec_Plus = dailyTrainSeatService.countSeat(date, trainCode, SeatTypeEnum.EC_P.getCode());
                int Ec = dailyTrainSeatService.countSeat(date, trainCode, SeatTypeEnum.EC.getCode());
                // 票价 = 里程之和 * 座位单价 * 车次类型系数
                String trainType = dailyTrain.getType();
                // 计算票价系数：TrainTypeEnum.priceRate
                BigDecimal priceRate = EnumUtil.getFieldBy(TrainTypeEnum::getPriceRate, TrainTypeEnum::getCode, trainType);
                BigDecimal BuPrice = sumKM.multiply(SeatTypeEnum.BU.getPrice()).multiply(priceRate).setScale(2, RoundingMode.HALF_UP);
                BigDecimal EcPlusPrice = sumKM.multiply(SeatTypeEnum.EC_P.getPrice()).multiply(priceRate).setScale(2, RoundingMode.HALF_UP);
                BigDecimal EcPrice = sumKM.multiply(SeatTypeEnum.EC.getPrice()).multiply(priceRate).setScale(2, RoundingMode.HALF_UP);
                dailyTrainTicket.setBu(Bu);
                dailyTrainTicket.setBuPrice(BuPrice);
                dailyTrainTicket.setEcPlus(Ec_Plus);
                dailyTrainTicket.setEcPlusPrice(EcPlusPrice);
                dailyTrainTicket.setEc(Ec);
                dailyTrainTicket.setEcPrice(EcPrice);

                dailyTrainTicket.setCreateTime(now);
                dailyTrainTicket.setUpdateTime(now);
                dailyTrainTicketMapper.insert(dailyTrainTicket);
            }
        }
        LOG.info("生成日期【{}】车次【{}】的余票信息结束", DateUtil.formatDate(date), trainCode);

    }
}
