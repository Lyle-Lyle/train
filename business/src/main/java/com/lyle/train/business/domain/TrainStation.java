package com.lyle.train.business.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TrainStation {
    private Long id;

    private String trainCode;

    private Integer stationIndex;

    private String name;

    private Date arrival;

    private Date departure;

    private BigDecimal km;

    private Date createTime;

    private Date updateTime;


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", trainCode=").append(trainCode);
        sb.append(", stationIndex=").append(stationIndex);
        sb.append(", name=").append(name);
        sb.append(", arrival=").append(arrival);
        sb.append(", departure=").append(departure);
        sb.append(", km=").append(km);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}