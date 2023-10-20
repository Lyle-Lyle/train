package com.lyle.train.business.resp;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;


@Data
public class DailyTrainTicketQueryResp {

    /**
     * id
     */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /**
     * 日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date date;

    /**
     * 车次编号
     */
    private String trainCode;

    /**
     * 出发站
     */
    private String origin;


    /**
     * 出发时间
     */
    @JsonFormat(pattern = "HH:mm:ss",timezone = "GMT+8")
    private Date departure;

    /**
     * 出发站序|本站是整个车次的第几站
     */
    private Integer startIndex;

    /**
     * 到达站
     */
    private String destination;


    /**
     * 到站时间
     */
    @JsonFormat(pattern = "HH:mm:ss",timezone = "GMT+8")
    private Date arrival;

    /**
     * 到站站序|本站是整个车次的第几站
     */
    private Integer endIndex;

    /**
     * 一等座余票
     */
    private Integer bu;

    /**
     * 一等座票价
     */
    private BigDecimal buPrice;

    /**
     * 二等座余票
     */
    private Integer ecPlus;

    /**
     * 二等座票价
     */
    private BigDecimal ecPlusPrice;

    /**
     * 软卧余票
     */
    private Integer ec;

    /**
     * 软卧票价
     */
    private BigDecimal ecPrice;

    /**
     * 新增时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
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
        sb.append(", end=").append(destination);
        sb.append(", arrival=").append(arrival);
        sb.append(", endIndex=").append(endIndex);
        sb.append(", bu=").append(bu);
        sb.append(", buPrice=").append(buPrice);
        sb.append(", ecPlus=").append(ecPlus);
        sb.append(", ecPlusPrice=").append(ecPlusPrice);
        sb.append(", ec=").append(ec);
        sb.append(", ecPrice=").append(ecPrice);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}
