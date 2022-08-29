package hry.platform.config.model;

import hry.bean.BaseModel;
import hry.core.mvc.controller.BaseController;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.zxp.esclientrhl.annotation.ESMapping;
import org.zxp.esclientrhl.annotation.ESMetaData;
import org.zxp.esclientrhl.enums.Analyzer;
import org.zxp.esclientrhl.enums.DataType;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * @author zhouming
 * @date 2020/5/9 17:56
 * @Version 1.0
 */
@Data
@ApiModel(value = "用户行为实体类")
@ESMetaData(indexName = "actionlogs", number_of_shards = 5, number_of_replicas = 1)
public class ActionLogRecord extends BaseModel {

    @ESMapping(datatype = DataType.long_type)
    private Long id; // id

    @ESMapping(datatype = DataType.text_type, analyzer = Analyzer.ik_smart, search_analyzer = Analyzer.ik_smart)
    @ApiModelProperty(value = "操作者")
    private String handlers; // 操作者

    @ESMapping(datatype = DataType.long_type)
    private Long handlersId; // 操作者ID

    @ESMapping(datatype = DataType.text_type, analyzer = Analyzer.ik_smart, search_analyzer = Analyzer.ik_smart)
    @ApiModelProperty(value = "菜单名称")
    private String menuName; // 菜单名称

    @ESMapping(datatype = DataType.text_type, analyzer = Analyzer.ik_smart, search_analyzer = Analyzer.ik_smart)
    @ApiModelProperty(value = "功能名称")
    private String functionName; // 功能名称

    @ApiModelProperty(value = "请求路径")
    private String url; // 请求路径

    private String source; // 来源

    private String ip; // ip

    private Long port; // 端口号

    @ESMapping(datatype = DataType.long_type)
    private Long createdTime; // 操作时间

    @Transient
    private Long number;

}
