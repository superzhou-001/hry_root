package hry.scm.project.model.vo;

import hry.bean.BaseModel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author luyue
 * @date 2020/7/24 16:24
 */
@Data
public class RedeemDetailVo  extends BaseModel {
    /**
     * id
     */
    private Long id;
    /**
     * 质押物详细id
     */
    private Long detailId;
    /**
     *区列行卫衣标识
     */
    private String number;
    /**
     *物品名称
     */
    private String goodsName;
    /**
     *剩余件数
     */
    private Integer surplusCount;
    /**
     *报检号
     */
    private String inspectionNo;
    /**
     *箱号
     */
    private String caseNo;
    /**
     *库位
     */
    private String location;
    /**
     *区
     */
    private String area;
    /**
     *行
     */
    private String line;
    /**
     *列
     */
    private String queue;
    /**
     *层
     */
    private String layer;
    /**
     *
     */
    private Integer backCount;
    /**
     *
     */
    private BigDecimal backWeight;




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

}
