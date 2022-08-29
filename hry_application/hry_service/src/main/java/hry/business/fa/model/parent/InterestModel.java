package hry.business.fa.model.parent;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.math.BigDecimal;

/**
 * @Author: yaozh
 * @Description:
 * @slogan: 天下风云出我辈，一入代码岁月催！
 * @Date:
 */
@Data
@ApiModel(value = "计息模型")
public class InterestModel extends BaseModel {

    /**
     * 还款方式 1 等额本金 2 等额本息 3 等本等息 4 按期收息, 到本还期 5 其他方式
     */
    @Column(name= "repaytype")
    @ApiModelProperty(value = "还款方式 1 等额本金 2 等额本息 3 等本等息 4 按期收息, 到本还期 5 其他方式")
    private Integer repaytype;

    /**
     * 还款周期 1 日 2 月 3 季 4 半季, 年 5 自定义
     */
    @Column(name= "repayperiod")
    @ApiModelProperty(value = "还款周期 1 日 2 月 3 季 4 半季, 年 5 自定义")
    private Integer repayperiod;

    /**
     * 每期天数 注：只有自定义时天数有值
     */
    @Column(name= "periodday")
    @ApiModelProperty(value = "每期天数 注：只有自定义时天数有值")
    private Integer periodday;

    /**
     * 还款日类型 1 按还款日对日还款 2 按固定还款
     */
    @Column(name= "repaydaytype")
    @ApiModelProperty(value = "还款日类型 1 按还款日对日还款 2 按固定还款")
    private Integer repaydaytype;

    /**
     * 罚息比例
     */
    @Column(name= "penaltyratio")
    @ApiModelProperty(value = "罚息比例")
    private BigDecimal penaltyratio;

    /**
     * 模型类型 1 算头不算尾 2 算头又算尾
     */
    @Column(name= "modeltype")
    @ApiModelProperty(value = "模型类型 1 算头不算尾 2 算头又算尾")
    private Integer modeltype;

    /**
     * 月息（期息类型） 1 按月化利率直接计算 2 按日化利率乘实际天数计算 3 按日化利率乘固定天数30天计算
     */
    @Column(name= "periodtype")
    @ApiModelProperty(value = "月息（期息类型） 1 按月化利率直接计算 2 按日化利率乘实际天数计算 3 按日化利率乘固定天数30天计算")
    private Integer periodtype;

    /**
     * 年模型 1 360天 2 365天
     */
    @Column(name= "yearmodeltype")
    @ApiModelProperty(value = "年模型 1 360天 2 365天")
    private Integer yearmodeltype;

    /**
     * 每月多少号还款 注 只有固定还款时有值
     */
    @Column(name= "repayday")
    @ApiModelProperty(value = "每月多少号还款 注 只有固定还款时有值")
    private Integer repayday;


}
