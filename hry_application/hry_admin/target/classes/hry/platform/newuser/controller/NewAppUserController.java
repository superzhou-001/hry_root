/**
 * Copyright:    互融云
 * @author:      liushilei
 * @version:     V1.0
 * @Date:        2017-05-27 16:05:55
 */
package hry.platform.newuser.controller;


import com.alibaba.fastjson.JSON;
import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.platform.newuser.model.NewAppOrganization;
import hry.platform.newuser.model.NewAppUser;
import hry.platform.newuser.service.NewAppOrganizationService;
import hry.platform.newuser.service.NewAppUserService;
import hry.security.jwt.JWTContext;
import hry.util.GoogleAuthenticatorUtil;
import hry.util.StringUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * Copyright:   互融云
 * @author:      liushilei
 * @version:     V1.0
 * @Date:        2017-05-27 16:05:55
 */
@Api(value = "管理用户接口", tags = "管理用户接口", description = "管理用户接口")
@RestController
@RequestMapping("/user/newappuser")
public class NewAppUserController extends BaseController {


	@Autowired
	private NewAppUserService newAppUserService;

	@Autowired
	private NewAppOrganizationService newAppOrganizationService;


	@ApiOperation(value = "添加用户", notes = "添加用户")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header")
	})
	@PostMapping(value="/add")
	public JsonResult add(NewAppUser appUser){
		return newAppUserService.addUser(appUser);
	}


	@ApiOperation(value = "修改用户", notes = "修改用户")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header")
	})
	@PostMapping(value="/modify")
	public JsonResult modify(NewAppUser appUser){
		return newAppUserService.modifyUser(appUser);
	}

	/**
	 * 禁用用户
	 * */
	@ApiOperation(value = "禁用用户", notes = "禁用用户")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header")
	})
	@PostMapping(value="/isLock")
	public JsonResult isLock(@ApiParam(name = "ids", value = "用户id集合,用英文逗号连接", required = true) @RequestParam String ids,
							 @ApiParam(name = "islock", value = "是否禁用 0 激活 1 禁用", required = true) @RequestParam String islock){
		String[] idList = ids.split(",");
		Arrays.asList(idList).forEach((id) -> {
			NewAppUser user = newAppUserService.get(Long.parseLong(id));
			user.setIslock(Integer.parseInt(islock));
			newAppUserService.update(user);
		});
		return new JsonResult(true);
	}


	@ApiOperation(value = "删除用户", notes = "删除用户")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),

	})
	@PostMapping(value="/remove")
	public JsonResult remove(
			@ApiParam(name = "ids", value = "用户id集合,用英文逗号连接", required = true) @RequestParam String ids){
		if(StringUtils.isEmpty(ids)){
			return new JsonResult().setMsg("id不能为空");
		}
		return newAppUserService.remove(ids);
	}

	/**
	 * 分页查询未加入组人员
	 * */
	@ApiOperation(value = "未加入组人员---分页查询", notes = "未加入组人员---分页查询")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
	})
	@PostMapping(value="/findNotUserOrganList")
	public PageResult findNotUserOrganList(
			@ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
			@ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
			HttpServletRequest request){
		QueryFilter filter = new QueryFilter(NewAppUser.class, request);
		return newAppUserService.findNotUserOrganList(filter);
	}


	@ApiOperation(value = "人员管理---分页查询用户", notes = "人员管理---分页查询用户")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
	})
	@PostMapping(value="/list")
	public PageResult list(
				@ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
				@ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
				HttpServletRequest request){
		return newAppUserService.findPageBySql(request);
	}

	@ApiOperation(value = "查询全部用户", notes = "查询全部用户")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
	})
	@PostMapping(value="/findAll")
	public List<NewAppUser> findAll(HttpServletRequest request){
		return newAppUserService.findAll();
	}

	@ApiOperation(value = "查询用户-分页", notes = "查询用户-分页")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
	})
	@PostMapping(value="/findPage")
	public PageResult findPage(@ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
							   @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
							   HttpServletRequest request){
		QueryFilter filter = new QueryFilter(NewAppUser.class,request);
		return newAppUserService.findPageResult(filter);
	}

	@ApiOperation(value = "重置密码", notes = "重置密码")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
	})
	@PostMapping(value="/resetpwd")
	public JsonResult resetpwd(
			@ApiParam(name = "password", value = "新密码", required = true) @RequestParam String password,
			@ApiParam(name = "repassword", value = "确认密码", required = true) @RequestParam String repassword
	){
		JsonResult jsonResult = new JsonResult();
		if(StringUtils.isEmpty(password)){
			jsonResult.setMsg("密码不能为空");
			return  jsonResult;
		}
		if(StringUtils.isEmpty(repassword)){
			jsonResult.setMsg("密码不能为空");
			return jsonResult;
		}
		if(!password.equals(repassword)){
			jsonResult.setMsg("次密码不一致");
			return jsonResult;
		}

		NewAppUser user = (NewAppUser) JWTContext.getUser(NewAppUser.class);
		return newAppUserService.resetpw(user.getId(),password);
	}

	@ApiOperation(value = "子用户设置密码", notes = "子用户设置密码")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
	})
	@PostMapping(value="/resetpwdtwo")
	public JsonResult resetpwdtwo(
			@ApiParam(name = "id", value = "用户id", required = true) @RequestParam String id,
			@ApiParam(name = "password", value = "新密码", required = true) @RequestParam String password,
			@ApiParam(name = "repassword", value = "确认密码", required = true) @RequestParam String repassword
	){
		JsonResult jsonResult = new JsonResult();
		if(StringUtils.isEmpty(password)){
			jsonResult.setMsg("密码不能为空");
			return  jsonResult;
		}
		if(StringUtils.isEmpty(repassword)){
			jsonResult.setMsg("密码不能为空");
			return jsonResult;
		}
		if(!password.equals(repassword)){
			jsonResult.setMsg("次密码不一致");
			return jsonResult;
		}
		if (StringUtils.isEmpty(id)) {
			jsonResult.setMsg("用户Id不能为空");
			return jsonResult;
		}
		return newAppUserService.resetpw(Long.valueOf(id),password);
	}


	@ApiOperation(value = "查看用户", notes = "查看用户")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
	})
	@GetMapping(value="/get/{id}")
	public JsonResult get(@ApiParam(name = "id", value = "用户id", required = true) @PathVariable Long id){
		return newAppUserService.getUser(id);
	}

	@ApiOperation(value = "查询全部组织", notes = "查询全部组织")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
	})
	@PostMapping("/listorganization")
	public List<NewAppOrganization> listorganization(){
		QueryFilter filter = new QueryFilter(NewAppOrganization.class);
		return newAppOrganizationService.find(filter);
	}

	@ApiOperation(value = "Ukey设置", notes = "Ukey设置")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
	})
	@PostMapping("/setUkey")
	public JsonResult setUkey(@ApiParam(name = "id", value = "用户id", required = true) @RequestParam Long id,
							  @ApiParam(name = "ukeyState", value = "Ukey状态 0 关闭 1 开启", required = true) @RequestParam Long ukeyState,
							  @ApiParam(name = "ukeyCode", value = "ukey动态口令 ukeyState为0时必填", required = false) @RequestParam String ukeyCode,
							  @ApiParam(name = "ukeyNo", value = "ukey编号 ukeyState为1时必填", required = false) @RequestParam String ukeyNo) {
		JsonResult result = new JsonResult();
		if (StringUtils.isEmpty(id)) {
			return result.setSuccess(false).setMsg("用户Id不能为空");
		}
		NewAppUser user = new NewAppUser();
		user.setId(id);
		if (ukeyState == 1) {
			if (StringUtils.isEmpty(ukeyNo)) {
				return result.setSuccess(false).setMsg("ukey编号不能空");
			}
			user.setUkeyState(1);
			user.setUkeyNo(ukeyNo);
		} else if (ukeyState == 0) {
			if (StringUtils.isEmpty(ukeyCode)) {
				return result.setSuccess(false).setMsg("动态口令不能为空");
			}
			// 校验动态口令
			Boolean flag = true;
			if (flag) {
				user.setUkeyState(0);
			} else {
				return result.setSuccess(false).setMsg("动态口令不正确");
			}
		} else {
			return result.setSuccess(false).setMsg("Ukey状态不正确");
		}
		newAppUserService.update(user);
		return result.setSuccess(true);
	}

	@ApiOperation(value = "取消谷歌", notes = "取消谷歌")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
	})
	@PostMapping("/abolishGoogle")
	public JsonResult abolishGoogle(@ApiParam(name = "id", value = "用户id", required = true) @RequestParam Long id) {
		if (StringUtils.isEmpty(id)) {
			return new JsonResult(false).setMsg("用户Id不能为空");
		}
		NewAppUser user = newAppUserService.get(id);
		user.setGoogleState(0);
		newAppUserService.update(user);
		return new JsonResult(true);
	}

	@ApiOperation(value = "生成谷歌密文", notes = "生成谷歌密文")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
	})
	@PostMapping("/createGoogle")
	public JsonResult createGoogle() {
		NewAppUser user = (NewAppUser) JWTContext.getUser(NewAppUser.class);
		String secret = GoogleAuthenticatorUtil.generateSecretKey();
		user.setGoogleKey(secret);
		return new JsonResult(true).setObj(user);
	}

	@ApiOperation(value = "谷歌设置", notes = "谷歌设置")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
	})
	@PostMapping("/setGoogle")
	public JsonResult setGoogle(HttpServletRequest request,
								@ApiParam(name = "googleState", value = "谷歌状态 0 关闭 1 开启", required = true) @RequestParam Long googleState,
								@ApiParam(name = "googleCode", value = "谷歌验证码", required = true) @RequestParam String googleCode,
								@ApiParam(name = "googleKey", value = "谷歌密文 googleState 为1时 必填", required = false) @RequestParam String googleKey) {
		NewAppUser user = (NewAppUser) JWTContext.getUser(NewAppUser.class);
		if (googleState == 1) {
			if (StringUtils.isEmpty(googleKey)) {
				return new JsonResult(false).setMsg("谷歌密文不能为空");
			}
			user.setGoogleState(1);
			user.setGoogleKey(googleKey);
		} else if (googleState == 0) {
			user.setGoogleState(0);
		} else {
			return new JsonResult(false).setMsg("谷歌状态不正确");
		}
		if (StringUtils.isEmpty(googleCode)) {
			return new JsonResult(false).setMsg("谷歌验证码不能为空");
		}
		// 谷歌码验证
		Long code = Long.parseLong(googleCode);
		long times = System.currentTimeMillis();
		GoogleAuthenticatorUtil ga = new GoogleAuthenticatorUtil();
		boolean flag = ga.check_code(user.getGoogleKey(), code, times);
		if (!flag) {
			return new JsonResult(false).setMsg("谷歌验证码不正确");
		}
		newAppUserService.update(user);
		// 更新token
		JWTContext.updateUser(request,JSON.toJSONString(user));
		return new JsonResult(true);
	}

	@ApiOperation(value = "账户设置---修改账户基础信息", notes = "修改账户基础信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
	})
	@PostMapping("/updateUser")
	public JsonResult updateUser(HttpServletRequest request,
								@ApiParam(name = "originalMobile", value = "原手机号", required = false) @RequestParam(required = false) String originalMobile,
								@ApiParam(name = "mobile", value = "手机号", required = false) @RequestParam(required = false) String mobile,
								@ApiParam(name = "headImage", value = "用户头像", required = false) @RequestParam(required = false) String headImage,
								@ApiParam(name = "originalEmail", value = "原邮箱", required = false) @RequestParam(required = false) String originalEmail,
								@ApiParam(name = "email", value = "邮箱", required = false) @RequestParam(required = false) String email,
								@ApiParam(name = "weChatAuthLogin", value = "是否授权登录 0 否 1 是", required = false) @RequestParam(required = false) String weChatAuthLogin) {
		NewAppUser user = (NewAppUser) JWTContext.getUser(NewAppUser.class);
		// 是否更新
		boolean isUpdate = false;
		if (StringUtil.isNull(mobile)) {
			if (StringUtil.isNull(user.getMobile())) {
				if (user.getMobile().equals(originalMobile)) {
					return new JsonResult(false).setMsg("原手机号不正确");
				}
			}
			user.setMobile(mobile);
			isUpdate = true;
		}
		if (StringUtil.isNull(headImage)) {
			user.setHeadImage(headImage);
			isUpdate = true;
		}
		if (StringUtil.isNull(email)) {
			if (StringUtil.isNull(user.getEmail())) {
				if (user.getEmail().equals(originalEmail)) {
					return new JsonResult(false).setMsg("原邮箱不正确");
				}
			}
			user.setEmail(email);
			isUpdate = true;
		}
		if (StringUtil.isNull(weChatAuthLogin)) {
			user.setWeChatAuthLogin(Integer.parseInt(weChatAuthLogin));
			isUpdate = true;
		}
		if (isUpdate) {
			newAppUserService.update(user);
			// 更新token
			JWTContext.updateUser(request,JSON.toJSONString(user));
		}
		return new JsonResult(true);
	}
}
