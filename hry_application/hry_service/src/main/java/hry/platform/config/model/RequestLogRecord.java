/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-04-13 17:30:46 
 */
package hry.platform.config.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.zxp.esclientrhl.annotation.ESMapping;
import org.zxp.esclientrhl.annotation.ESMetaData;
import org.zxp.esclientrhl.enums.Analyzer;
import org.zxp.esclientrhl.enums.DataType;

import javax.persistence.*;


/**
 * <p> RequestLogRecord </p>
 *
 * @author: liuchenghui
 * @Date: 2020-04-13 17:30:46
 * indexName 索引名称 注索引名称不能有大写
 * indexType 索引类型 (一个索引名称对应一个类型)
 * number_of_shards 分片数
 * number_of_replicas 备份数
 * analyzer 写时分词
 * search_analyzer 查询时分词
 */
@Data
@ApiModel(value = "请求日志记录实体类")
@Table(name="request_log_record")
@ESMetaData(indexName = "reqlogs", number_of_shards = 5, number_of_replicas = 1)
public class RequestLogRecord extends BaseModel {

	/**ends BaseModel {

	 * 主键
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "主键")
	@ESMapping(datatype = DataType.long_type)
	private Long id;

	/**
	* 登录名
	*/
	@Column(name= "userName")
    @ApiModelProperty(value = "登录名")
	@ESMapping(datatype = DataType.text_type, analyzer = Analyzer.ik_smart, search_analyzer = Analyzer.ik_smart)
	private String userName;

	/**
	* 请求路径
	*/
	@Column(name= "url")
    @ApiModelProperty(value = "请求路径")
	@ESMapping(datatype = DataType.text_type, analyzer = Analyzer.ik_smart, search_analyzer = Analyzer.ik_smart)
	private String url;

	/**
	* 访问ip
	*/
	@Column(name= "ip")
    @ApiModelProperty(value = "访问ip")
	private String ip;

	/**
	* 请求端口
	*/
	@Column(name= "port")
    @ApiModelProperty(value = "请求端口")
	@ESMapping(datatype = DataType.long_type)
	private Long port;

	/**
	* 方法说明
	*/
	@Column(name= "methodDesc")
    @ApiModelProperty(value = "方法说明")
	@ESMapping(datatype = DataType.text_type, analyzer = Analyzer.ik_smart, search_analyzer = Analyzer.ik_smart)
	private String methodDesc;

	/**
	* 方法名
	*/
	@Column(name= "methodName")
    @ApiModelProperty(value = "方法名")
	private String methodName;

	/**
	* 请求参数
	*/
	@Column(name= "args")
    @ApiModelProperty(value = "请求参数")
	private String args;

	/**
	* 类地址
	*/
	@Column(name= "target")
    @ApiModelProperty(value = "类地址")
	private String target;

	/**
	* 来源
	*/
	@Column(name= "source")
    @ApiModelProperty(value = "来源")
	private String source;

	/**
	* 备注
	*/
	@Column(name= "remark")
    @ApiModelProperty(value = "备注")
	private String remark;

	@Transient
	@ESMapping(datatype = DataType.long_type)
	private Long createdTime;

}
