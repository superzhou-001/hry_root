package hry.business.fa.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author zhouming
 * @date 2020/7/23 10:33
 * @Version 1.0
 */
@Data
@ApiModel(value = "还款计划合计实体类")
public class FaFundIntentSynthesize {

    /**
     * 当前期数
     */
    @ApiModelProperty(value = "当前期数")
    private Integer payintentPeriod;

    /**
     * 计息开始时间
     */
    @ApiModelProperty(value = "计息开始时间")
    private Date interestStarTime;

    /**
     * 计息结束时间
     */
    @ApiModelProperty(value = "计息结束时间")
    private Date interestEndTime;

    /**
     * 计息天数
     */
    @ApiModelProperty(value = "计息天数")
    private Integer interestDays;

    /**
     * 计划到账日
     */
    @ApiModelProperty(value = "计划到账日")
    private Date intentDate;

    @Transient
    private List<FaFundIntent> fundIntentList; // 对应分录记录表



}
