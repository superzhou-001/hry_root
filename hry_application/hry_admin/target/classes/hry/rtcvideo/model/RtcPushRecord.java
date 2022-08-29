/**
 * Copyright: 互融云
 *
 * @author: zengzhiyan
 * @version: V1.0
 * @Date: 2020-07-01 15:21:18 
 */
package hry.rtcvideo.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> RtcPushRecord </p>
 *
 * @author: zengzhiyan
 * @Date: 2020-07-01 15:21:18 
 */
@Data
@ApiModel(value = "音视频转推记录实体类")
@Table(name="rtc_push_record")
public class RtcPushRecord extends BaseModel {

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
	* 通道Id
	*/
	@Column(name= "channelId")
    @ApiModelProperty(value = "通道Id")
	private String channelId;

	/**
	* 推流地址
	*/
	@Column(name= "streamURL")
    @ApiModelProperty(value = "推流地址")
	private String streamURL;

	/**
	* 流名称
	*/
	@Column(name= "streamName")
    @ApiModelProperty(value = "流名称")
	private String streamName;

	/**
	* 推流域名
	*/
	@Column(name= "pushDomain")
    @ApiModelProperty(value = "推流域名")
	private String pushDomain;

	/**
	* 推流AppName
	*/
	@Column(name= "appName")
    @ApiModelProperty(value = "推流AppName")
	private String appName;

}
