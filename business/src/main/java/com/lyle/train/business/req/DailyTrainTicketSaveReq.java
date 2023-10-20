package com.lyle.train.business.req;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class DailyTrainTicketSaveReq {

    /**
     * id
     */
    private Long id;

    /**
     * 日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @NotNull(message = "【日期】不能为空")
    private Date date;

    /**
     * 车次编号
     */
    @NotBlank(message = "【车次编号】不能为空")
    private String trainCode;

    /**
     * 出发站
     */
    @NotBlank(message = "【出发站】不能为空")
    private String origin;



    /**
     * 出发时间
     */
    @JsonFormat(pattern = "HH:mm:ss",timezone = "GMT+8")
    @NotNull(message = "【出发时间】不能为空")
    private Date departure;

    /**
     * 出发站序|本站是整个车次的第几站
     */
    @NotNull(message = "【出发站序】不能为空")
    private Integer startIndex;

    /**
     * 到达站
     */
    @NotBlank(message = "【到达站】不能为空")
    private String destination;

    /**
     * 到站时间
     */
    @JsonFormat(pattern = "HH:mm:ss",timezone = "GMT+8")
    @NotNull(message = "【到站时间】不能为空")
    private Date arrival;

    /**
     * 到站站序|本站是整个车次的第几站
     */
    @NotNull(message = "【到站站序】不能为空")
    private Integer endIndex;

    /**
     * 一等座余票
     */
    @NotNull(message = "【一等座余票】不能为空")
    private Integer bu;

    /**
     * 一等座票价
     */
    @NotNull(message = "【一等座票价】不能为空")
    private BigDecimal buPrice;

    /**
     * 二等座余票
     */
    @NotNull(message = "【二等座余票】不能为空")
    private Integer ecPlus;

    /**
     * 二等座票价
     */
    @NotNull(message = "【二等座票价】不能为空")
    private BigDecimal ecPlusPrice;

    /**
     * 软卧余票
     */
    @NotNull(message = "【软卧余票】不能为空")
    private Integer ec;

    /**
     * 软卧票价
     */
    @NotNull(message = "【软卧票价】不能为空")
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
        sb.append(", destination=").append(destination);
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