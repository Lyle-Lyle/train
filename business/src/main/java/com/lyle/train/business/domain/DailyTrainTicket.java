package com.lyle.train.business.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


@Data
public class DailyTrainTicket {
    private Long id;

    private Date date;

    private String trainCode;

    private String origin;

    private Date departure;

    private Integer startIndex;

    private String destination;

    private Date arrival;

    private Integer endIndex;

    private Integer bu;

    private BigDecimal buPrice;

    private Integer ecPlus;

    private BigDecimal ecPlusPrice;

    private Integer ec;

    private BigDecimal ecPrice;

    private Date createTime;

    private Date updateTime;



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", date=").append(date);
        sb.append(", trainCode=").append(trainCode);
        sb.append(", origin=").append(origin);
        sb.append(", departure=").append(departure);
        sb.append(", startIndex=").append(startIndex);
        sb.append(", destination=").append(destination);
        sb.append(", arrival=").append(arrival);
        sb.append(", endIndex=").append(endIndex);
        sb.append(", bu=").append(bu);
        sb.append(", buPrice=").append(buPrice);
        sb.append(", ecPlus=").append(ecPlus);
        sb.append(", ecPlusPrice=").append(ecPlusPrice);
        sb.append(", ec=").append(ec);
        sb.append(", edzPrice=").append(ecPrice);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}