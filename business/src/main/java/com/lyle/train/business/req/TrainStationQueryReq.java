package com.lyle.train.business.req;

import com.lyle.train.common.req.PageReq;
import lombok.Data;

@Data
public class TrainStationQueryReq extends PageReq {

    private String trainCode;

    @Override
    public String toString() {
        // 加上父类分页的toString一起
        return "TrainStationQueryReq{" +
                "} " + super.toString();
    }
}
