package hry.platform.config.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;

/**
 * @Author: yaozh
 * @Description:
 * @slogan: 天下风云出我辈，一入代码岁月催！
 * @Date:
 */
@Data
public class YzyProductVo {

    private String id;

    /**
     * 剩余次数
     */
    private Integer remainingnumber;

    /**
     * 总次数
     */
    private Integer totalnumber;

    /**
     * 产品名称
     */
    private String productname;


}
