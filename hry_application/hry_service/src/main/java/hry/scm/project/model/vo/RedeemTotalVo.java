package hry.scm.project.model.vo;

import hry.bean.BaseModel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author luyue
 * @date 2020/7/24 11:38
 */
@Data
public class RedeemTotalVo  extends BaseModel {
    /**
     * id
     */
    private Long id;
    /**
     * 赎货申请id
     */
    private Long redeemId;
    /**
     * 质押物汇总id
     */
    private Long totalId;
    /**
     *本次赎回件数
     */
    private Integer curBackCount;
    /**
     *本次赎回价值
     */
    private BigDecimal curBackWorth;
    /**
     *本次赎回重量
     */
    private BigDecimal curBackWeight;
    /**
     *物品名称
     */
    private String goodsName;
    /**
     *质押重量
     */
    private BigDecimal mortWeight;
    /**
     *质押件数
     */
    private Integer mortCount;
    /**
     *质押单价
     */
    private BigDecimal mortPrice;
    /**
     *质押物总价值
     */
    private BigDecimal mortTotalPrice;
    /**
     *剩余件数
     */
    private Integer surplusCount;
    /**
     *剩余重量
     */
    private BigDecimal surplusWeight;
    /**
     *剩余价值
     */
    private BigDecimal surTotalPrice;







}
