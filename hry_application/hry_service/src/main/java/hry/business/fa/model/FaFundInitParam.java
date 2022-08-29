package hry.business.fa.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 生成款项所需参数
 * @author zhouming
 * @date 2020/7/21 14:06
 * @Version 1.0
 */
@Data
@ApiModel(value = "生成款项所需参数")
public class FaFundInitParam {
    @ApiModelProperty(value = "融资金额")
    private BigDecimal financingMoney;
    @ApiModelProperty(value = "融资期限")
    private Integer financingTerm;
    @ApiModelProperty(value = "申请放款日期")
    private Date applyLoanDate;
    @ApiModelProperty(value = "到期日期")
    private Date expireDate;
    @ApiModelProperty(value = "还款方式 1 等额本金 2 等额本息 3 等本等息 4 按期收息, 到本还期 5 其他方式")
    private Integer repaytype;
    @ApiModelProperty(value = "还款周期 1 日 2 月 3 季 4 半季 5 年 6 自定义")
    private Integer repayperiod;
    @ApiModelProperty(value = "每期天数 注：只有自定义时天数有值")
    private Integer periodday;
    @ApiModelProperty(value = "模型类型 1 算头不算尾 2 算头又算尾")
    private Integer modeltype;
    @ApiModelProperty(value = "月息（期息类型） 1 按月化利率直接计算 2 按日化利率乘实际天数计算 3 按日化利率乘固定天数30天计算")
    private Integer periodtype;
    @ApiModelProperty(value = "年模型 1 360天 2 365天")
    private Integer yearmodeltype;
    @ApiModelProperty(value = "还款日类型 1 按还款日对日还款 2 按固定还款")
    private Integer repaydaytype;
    @ApiModelProperty(value = "每月多少号还款 注 只有固定还款时有值")
    private Integer repayday;
    @ApiModelProperty(value = "产品费率集合")
    private String productRateJson;
}
