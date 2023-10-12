package com.lyle.train.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyle.train.business.domain.Train;
import com.lyle.train.business.mapper.StationMapper;
import com.lyle.train.business.resp.TrainQueryResp;
import com.lyle.train.common.resp.PageResp;
import com.lyle.train.common.util.SnowUtil;
import com.lyle.train.business.domain.Station;
//import com.lyle.train.business.domain.StationExample;
//import com.lyle.train.business.mapper.StationMapper;
import com.lyle.train.business.req.StationQueryReq;
import com.lyle.train.business.req.StationSaveReq;
import com.lyle.train.business.resp.StationQueryResp;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationService {

    private static final Logger LOG = LoggerFactory.getLogger(StationService.class);

    @Resource
    private StationMapper stationMapper;

    public void save(StationSaveReq req) {
        DateTime now = DateTime.now();
        Station station = BeanUtil.copyProperties(req, Station.class);
        if (ObjectUtil.isNull(station.getId())) {
            station.setId(SnowUtil.getSnowflakeNextId());
            station.setCreateTime(now);
            station.setUpdateTime(now);
            stationMapper.insert(station);
        } else {
            station.setUpdateTime(now);
            stationMapper.updateById(station);
        }
    }

    public PageResp<StationQueryResp> queryList(StationQueryReq req) {
        LOG.info("查询页码：{}", req.getPage());
        LOG.info("每页条数：{}", req.getSize());
        Page<Station> page = new Page<>(req.getPage(),req.getSize());
        page.addOrder(OrderItem.desc("id"));
        stationMapper.selectPage(page, null);

        LOG.info("总行数：{}", page.getTotal());
        LOG.info("总页数：{}", page.getPages());
        System.out.println(page.getPages());
        List<StationQueryResp> list = BeanUtil.copyToList( page.getRecords(), StationQueryResp.class);
        PageResp<StationQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(page.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    public List<StationQueryResp> queryAll() {
        QueryWrapper<Station> wrapper=new QueryWrapper<>();
        //时间升序排序
        wrapper.orderByAsc( "name");
        List<Station> stationList = stationMapper.selectList(wrapper);
        return BeanUtil.copyToList(stationList, StationQueryResp.class);
    }


    public void delete(Long id) {
        stationMapper.deleteById(id);
    }
}
