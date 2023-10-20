package com.lyle.train.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyle.train.business.req.TrainQueryReq;
import com.lyle.train.common.resp.PageResp;
import com.lyle.train.common.util.SnowUtil;
import com.lyle.train.business.domain.Train;
import com.lyle.train.business.mapper.TrainMapper;
import com.lyle.train.business.req.TrainSaveReq;
import com.lyle.train.business.resp.TrainQueryResp;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainService {

    private static final Logger LOG = LoggerFactory.getLogger(TrainService.class);

    @Resource
    private TrainMapper trainMapper;

    public void save(TrainSaveReq req) {
        DateTime now = DateTime.now();
        Train train = BeanUtil.copyProperties(req, Train.class);
        if (ObjectUtil.isNull(train.getId())) {
            train.setId(SnowUtil.getSnowflakeNextId());
            train.setCreateTime(now);
            train.setUpdateTime(now);
            trainMapper.insert(train);
        } else {
            train.setUpdateTime(now);
            trainMapper.updateById(train);
        }
    }

    public PageResp<TrainQueryResp> queryList(TrainQueryReq req) {
        LOG.info("查询页码：{}", req.getPage());
        LOG.info("每页条数：{}", req.getSize());
        Page<Train> page = new Page<>(req.getPage(),req.getSize());
        page.addOrder(OrderItem.desc("id"));
        trainMapper.selectPage(page, null);

        LOG.info("总行数：{}", page.getTotal());
        LOG.info("总页数：{}", page.getPages());
        System.out.println(page.getPages());
        List<TrainQueryResp> list = BeanUtil.copyToList( page.getRecords(), TrainQueryResp.class);
        PageResp<TrainQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(page.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    public List<TrainQueryResp> queryAll() {
//        QueryWrapper<Train> wrapper=new QueryWrapper<>();
//        //时间升序排序
//        wrapper.orderByDesc("code");
//        List<Train> trainList = trainMapper.selectList(wrapper);
        return BeanUtil.copyToList(selectAll(), TrainQueryResp.class);
    }

    public List<Train> selectAll() {
        QueryWrapper<Train> wrapper=new QueryWrapper<>();
        //时间升序排序
        wrapper.orderByDesc("code");
        return trainMapper.selectList(wrapper);
    }

    public void delete(Long id) {
        trainMapper.deleteById(id);
    }
}
