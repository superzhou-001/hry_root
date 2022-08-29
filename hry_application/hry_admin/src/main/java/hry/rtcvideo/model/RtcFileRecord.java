/**
 * Copyright: 互融云
 *
 * @author: zengzhiyan
 * @version: V1.0
 * @Date: 2020-07-01 15:24:59 
 */
package hry.rtcvideo.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p> RtcFileRecord </p>
 *
 * @author: zengzhiyan
 * @Date: 2020-07-01 15:24:59 
 */
@Data
@ApiModel(value = "音视频文件实体类")
@Table(name="rtc_file_record")
public class RtcFileRecord extends BaseModel {

	/**
	* 
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "")
	private Long id;

	/**
	* 任务Id
	*/
	@Column(name= "taskId")
    @ApiModelProperty(value = "任务Id")
	private String taskId;

	/**
	* 播流域名
	*/
	@Column(name= "playDomain")
    @ApiModelProperty(value = "播流域名")
	private String playDomain;

	/**
	* 推流地址
	*/
	@Column(name= "streamUrl")
    @ApiModelProperty(value = "推流地址")
	private String streamUrl;

	/**
	* 应用名称
	*/
	@Column(name= "appName")
    @ApiModelProperty(value = "应用名称")
	private String appName;

	/**
	* 流名称
	*/
	@Column(name= "streamName")
    @ApiModelProperty(value = "流名称")
	private String streamName;

	/**
	* oss,vod
	*/
	@Column(name= "storageWay")
    @ApiModelProperty(value = "oss,vod")
	private String storageWay;

	/**
	* 录制内容时长
	*/
	@Column(name= "duration")
    @ApiModelProperty(value = "录制内容时长")
	private BigDecimal duration;

	/**
	* 开始时间
	*/
	@Column(name= "startTime")
    @ApiModelProperty(value = "开始时间")
	private Date startTime;

	/**
	* 结束时间
	*/
	@Column(name= "stopTime")
    @ApiModelProperty(value = "结束时间")
	private Date stopTime;

	/**
	* 文件地址
	*/
	@Column(name= "uri")
    @ApiModelProperty(value = "文件地址")
	private String uri;

	/**
	* 推流域名
	*/
	@Column(name= "pushDomain")
    @ApiModelProperty(value = "推流域名")
	private String pushDomain;

	/**
	 * 文件格式
	 */
	@Column(name= "fileType")
	@ApiModelProperty(value = "文件格式")
	private String fileType;


}
