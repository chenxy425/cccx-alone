package com.ship.cccx.web.controller;

import com.ship.cccx.common.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ship.cccx.dao.dto.AccessToken;
import com.ship.cccx.dao.dto.JsonResult;
import com.ship.cccx.dao.model.AccessLog;
import com.ship.cccx.service.AccessLogService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

/*
 * 用户基本信息部分接口 1.Access_token获取方式
 */
@Controller
@Api(value = "账户登录管理", description = "账户登录管理")
public class XUserController{

	private static Logger logger = LoggerFactory.getLogger(XUserController.class);

	@Autowired
	protected AccessLogService userService;


	@ApiOperation(value = "获取token", notes = "结算中心商家信息", response = AccessToken.class)
	@RequestMapping (value="/mobile/accesstoken", method=RequestMethod.POST)
	public @ResponseBody JsonResult accesstoken(HttpServletRequest request, HttpServletResponse response) {
		JsonResult result = new JsonResult();

		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String source = request.getParameter("source");
		String version = request.getParameter("version");
		String imei = request.getParameter("imei");
		String system = request.getParameter("system");
		String width = request.getParameter("width");
		String heigth = request.getParameter("height");
		
		// 获取积分墙参数
		String pSysType = PhoneSystem.getPhoneSystemType(request);
		String equipment = "2";
		if ("android".equalsIgnoreCase(pSysType)) {
			equipment = "4";
		}
		String ip = SystemProperties.getIpAddr(request);

		// 检验signature
		String computedSignature = Signature.computeSignature(timestamp, source, version, imei, system, width, heigth);
		if (computedSignature.equals(signature)) {
			// 创建accessToken
			AccessLog accessLog = userService.generateAccessToken(signature, timestamp, imei, system, version);

			AccessToken accessToken = new AccessToken();
			accessToken.setAccess_token( accessLog.getAccessToken());

			Date createTime = accessLog.getCreattime();
			long creatTimestamp = createTime.getTime() / 1000;

			accessToken.setExpires_in(accessLog.getExpiresIn() + creatTimestamp);

			result.setErrorCode(Constants.SUCC);
			result.setData(accessToken);
		} else {
			result.setErrorCode(Constants.FAIL);
			result.setErrorMessage("参数不正确");
		}
		return result;
	}


	@ApiOperation(value = "登录接口", notes = "登录接口")
	@RequestMapping (value="/mobile/login", method=RequestMethod.POST)
	public @ResponseBody JsonResult login(@ApiParam(value = "app token信息", required = true) String accessToken,
										  @ApiParam(value = "登录用户名", required = true) String userName,
										  @ApiParam(value = "登录密码", required = true) String password,
										  @ApiParam(value = "接口api 版本", required = false) @RequestParam("v") String uservapi) {

		JsonResult result = new JsonResult();
		String errorMsg = null;
		/*String accessToken = request.getParameter("access_token");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String uservapi = request.getParameter("v");*/
		/*LoginReturn loginReturn = new LoginReturn();
		UserInfo u = null;
		if (uservapi == null || uservapi.equals("0")) {
			UUserInfo vo = JinxinRemoteUserService.getUserinfoByKey(username);
			if (vo != null) {
				username = vo.getUserName();
			} else {
				result.setErrorCode(Constants.FAIL);
				result.setErrorMessage("登录账号与密码不匹配");
				return result;
			}
			String pSysType = PhoneSystem.getPhoneSystemType(request);
			String equipment = "2";
			if ("android".equalsIgnoreCase(pSysType)) {
				equipment = "4";
			}
			String ip = SystemProperties.getIpAddr(request);
			//去登录
			JinxinRemoteReturn remoteReturn = JinxinRemoteUserService.login(username, password, ip, equipment);
			u = (UserInfo) remoteReturn.getNormalObject();
			if (u != null && vo != null) {
				u.setIdentify(vo.getCardId());
				u.setRealname(vo.getRealName());
				if (vo.getUserType() != null)
					u.setUserType(vo.getUserType().intValue());
			}
			errorMsg = remoteReturn.getErrorMessage();

			// 验证是否已发送注册大礼包，如未发送则发送大礼包
			//JinxinRemoteAssetService.checkRedSend(u.getUid());
		}
		if (u != null) {
			userService.updateAccessTokenByLoginUser(request.getParameter("access_token"), u.getUid(), username, new Date());
			AccessLog accessLog = userService.findAccessTokenByToken(accessToken);
			Userext userext = userService.findUserext(u.getUid());
			if (userext != null && StringUtils.isNotBlank(userext.getAvatar())) {
				u.setAvatar(FileUtils.HOST_ROOT + userext.getAvatar());
			}
			userService.login(u.getUid(), accessLog);

			//获取财务信息(登录的时候是否需要财务信息)
			if (uservapi == null || uservapi.equals("0")) {
				//JinxinRemoteReturn remoteReturn2 = JinxinRemoteUserService.getFinanceInfo((int) u.getUid(), u.getUsername());
				ReturnObjectTO remoteReturn2 = iJSCashBoxRPC.queryAccountInfo(u.getUid());
				Map<String, Object> map = remoteReturn2.getReturnObjects();
				FinanceInfo financeInfo = null;
				if (map != null && !map.isEmpty()) {
					CapitalAccountTO capitalAccountTO = (CapitalAccountTO)remoteReturn2.getReturnObjects().get(IJSCashBoxRPC.ACCOUNT_INF);
					//将CapitalAccountTO对象的数据转成FinanceInfo对象的数据
					financeInfo = new FinanceInfo();
					financeInfo.setAmount(capitalAccountTO.getAvailableMoney());//可用余额
					financeInfo.setBankCnt(capitalAccountTO.getBankNum());//银行卡张数
					financeInfo.setCapitalAmount(capitalAccountTO.getDueInMoney());//待收本金
					financeInfo.setFrozenAmount(capitalAccountTO.getFreezeMoney());//冻结金额
					financeInfo.setProfitAmount(capitalAccountTO.getBidDueInInterest());//待收利息
					financeInfo.setTotalAmount(capitalAccountTO.getCapitalTotal()); //资产总额
					financeInfo.setTotalCapital(capitalAccountTO.getCapitalTotal());//待收资金？
					financeInfo.setTotalProfit(capitalAccountTO.getBidReceiptInterest());//累计收益（第一个界面，不包括加息宝）
					financeInfo.setWithdrawfreeeze(capitalAccountTO.getWithdrawfreeeze()==null ? 0.0 : capitalAccountTO.getWithdrawfreeeze());//提现冻结金额
					//获取用户红包个数
					int rednum = JinxinRemoteAssetService.getRedPacketNum(u.getUid(), username, accessLog.getVersion());
					financeInfo.setRedCnt(rednum);
					financeInfo.setUid(u.getUid());

					//FinanceInfo f = (FinanceInfo) remoteReturn2.getNormalObject();
					loginReturn.setFinanceInfo(financeInfo);
					loginReturn.setUserInfo(u);
				}
			}
			result.setErrorCode(Constants.SUCC);
			result.setData(loginReturn);
		} else {
			result.setErrorCode( Constants.FAIL );
			if (errorMsg != null) {
				result.setErrorMessage(errorMsg);
			} else {
				result.setErrorMessage("登录失败");
			}
		}*/
		return result;
	}

	@ApiOperation(value = "退出接口", notes = "退出接口", response = String.class)
	@RequestMapping ( value = "/mobile/logout", method = RequestMethod.POST )
	public @ResponseBody JsonResult logout(@ApiParam(value = "用户id", required = true) @RequestParam ( value = "uid", required = true ) Long uid,
			@ApiParam(value = "app token", required = true)@RequestParam ( value = "accessToken", required = false ) String accessToken ) {

		JsonResult result = new JsonResult();
		userService.logout(uid, accessToken);
		result.setErrorCode(Constants.SUCC);
		return result;
	}

	@ApiOperation(value = "注册接口", notes = "注册接口")
	@RequestMapping ( value = "/mobile/register", method = RequestMethod.POST )
	public @ResponseBody JsonResult register(@ApiParam(value = "用户名", required = true) String username,
											 @ApiParam(value = "密码", required = true) String password,
											 @ApiParam(value = "接口版本", required = true) @RequestParam("v") String uservapi) {
		JsonResult result = new JsonResult( );

		/*String username = request.getParameter( "username" );
		String password = request.getParameter( "password" );
		String uservapi = request.getParameter( "v" );*/

		if ( !MessUtils.isCorrectUsername( username ) ) {
			result.setErrorCode( Constants.FAIL );
			result.setErrorMessage( "用户名应为6-14位半角英文或数字!" );
			return result;
		}
		if ( !MessUtils.isCorrectPassword( password ) ) {
			result.setErrorCode( Constants.FAIL );
			result.setErrorMessage( "登录密码应为6-20位半角英文和数字混合!" );
			return result;
		}

		/*LoginReturn loginReturn = new LoginReturn( );
		UserInfo u = null;
		String errorMsg = null;
		if ( uservapi == null || uservapi.equals( "0" ) ) {

			// 获取积分墙参数
			String deviceId = request.getParameter("idfa");
			String pSysType = PhoneSystem.getPhoneSystemType(request);
			String equipment = "2";
			Integer isRegisterOnPhone = 1;
			if ("android".equalsIgnoreCase(pSysType)) {
				equipment = "4";
				isRegisterOnPhone = 3;
			}
			String ip = SystemProperties.getIpAddr(request);

			JinxinRemoteReturn remoteReturn = JinxinRemoteUserService.register( username, password, inviteCode, isRegisterOnPhone, channel);
			u = ( UserInfo ) remoteReturn.getNormalObject( );
			errorMsg = remoteReturn.getErrorMessage( );

			if (u != null) {
				if ("2".equals(equipment)) { // ios, 积分墙
					JinxinRemotePromotionService.addAdClientAction(deviceId, u.getUid(), "1", ip);
				}
			}

		} else {
			u = new UserInfo( );
			u.setAvatar( "http://www.jinxin99.cn/webPage/photoFile/600ec962-bb95-4c0c-8da1-990ef173db9c.png" );
			u.setIdentify( "330719196804253671" );
			u.setIdentifyVerified( true );
			u.setMobile( "156010115611" );
			u.setMobileVerified( true );
			u.setTransaction( true );
			u.setUid( 99999 );
			u.setUsername( username );
			u.setRealname( "孙悟空" );
		}

		if ( u != null ) {
			if ( uservapi == null || uservapi.equals( "0" ) ) {

				//JinxinRemoteReturn ret = JinxinRemoteAssetService.sendredpacket( u.getUid( ) );
				String content = "fdsfdsf！";

				AccessLog access = userService.findAccessTokenByToken( request.getParameter( "access_token" ) );

				access.setLoginuserId( u.getUid( ) );
				access.setLoginuser( u.getUsername( ) );
				access.setLogintime( new Date( ) );
				if ( StringUtils.isNotBlank( inviteCode ) )
					access.setInviteCode( inviteCode.trim( ) );
				userService.updateAccessTokenByAccessLog( access );

				umengNoticeHelper.unicast( u.getUid( ), "欢迎",
				content, 1, null, NotifyUtils._production.equals( "true"
				) );
				String accessLogVersion = access.getVersion();//获得用户版本
				JinxinRemoteReturn remoteReturn2 = JinxinRemoteUserService.getFinanceInfo( ( int ) u.getUid( ), u.getUsername( ), accessLogVersion );
				FinanceInfo f = ( FinanceInfo ) remoteReturn2.getNormalObject( );

				loginReturn.setFinanceInfo( f );
				loginReturn.setUserInfo( u );

			} else {
				FinanceInfo f = new FinanceInfo( );

				f.setBankCnt( 2 );
				f.setRedCnt( 2 );
				f.setAmount( 10000.00 );
				f.setCapitalAmount( 500000.00 );
				f.setFrozenAmount( 100000.00 );
				f.setProfitAmount( 4999.00 );

				f.setTotalAmount( 500000.00 );
				f.setTotalCapital( 500000.00 );
				f.setTotalProfit( 4000.00 );
				f.setUid( 99999 );

				loginReturn.setFinanceInfo( f );
				loginReturn.setUserInfo( u );

			}
			result.setErrorCode( Constants.SUCC );
			result.setData( loginReturn );
		} else {
			result.setErrorCode( Constants.FAIL );
			if ( errorMsg != null )
				result.setErrorMessage( errorMsg );
			else
				result.setErrorMessage( "注册失败" );
		}*/

		return result;
	}

	@ApiOperation(value = "重置密码", notes = "重置密码")
	@RequestMapping ( value = "/mobile/resetpwd", method = RequestMethod.POST )
	public @ResponseBody JsonResult resetpwd( @ApiParam(value = "用户名", required = true) String username,
											  @ApiParam(value = "密码", required = true) String password,
											  @ApiParam(value = "接口版本", required = true) @RequestParam("v") String uservapi) {

		JsonResult result = new JsonResult( );
		/*String username = request.getParameter( "username" );
		String password = request.getParameter( "password" );
		String uservapi = request.getParameter( "v" );*/

		/*if ( uservapi == null || uservapi.equals( "0" ) ) {
			JinxinRemoteReturn remoteReturn = JinxinRemoteUserService.resetPwd( username, password );

			if ( remoteReturn.getErrorMessage( ) != null ) {
				result.setErrorCode( Constants.FAIL );
				result.setErrorMessage( remoteReturn.getErrorMessage( ) );
			} else {
				result.setErrorCode( Constants.SUCC );
			}

		} else {
			result.setErrorCode( Constants.SUCC );
		}*/

		return result;
	}

	/**
	 * 获取用户信息
	 * 
	 * @return
	 */
	@ApiOperation(value = "获取用户信息", notes = "获取用户信息")
	@RequestMapping (value = "/mobile/userinfo")
	public @ResponseBody JsonResult userinfo(@ApiParam(value = "用户名", required = true) String username,
											 @ApiParam(value = "用户id", required = true) String uid,
											 @ApiParam(value = "接口版本", required = true) @RequestParam("v") String uservapi) {

		JsonResult result = new JsonResult();
		/*String uid = request.getParameter("uid");
		String username = request.getParameter("username");
		String uservapi = request.getParameter("v");*/

		/*if (uid != null && uid.length( ) > 0) {
			UserInfo u = null;
			if (uservapi == null || uservapi.equals("0")) {
				JinxinRemoteReturn remoteReturn = JinxinRemoteUserService.getUserinfo(uid);
				u = (UserInfo) remoteReturn.getNormalObject( );

				if (u != null) {
					Userext userext = userService.findUserext(u.getUid());
					if (userext != null && StringUtils.isNotBlank(userext.getAvatar())) {
						u.setAvatar(FileUtils.HOST_ROOT + userext.getAvatar());
					}
					result.setErrorCode(Constants.SUCC);
					result.setData(u);
				} else {
					result.setErrorCode(Constants.FAIL);
					if (remoteReturn.getErrorMessage() != null) {
						result.setErrorMessage(remoteReturn.getErrorMessage());
					} else {
						result.setErrorMessage("获取用户信息失败");
					}
				}
			}
		} else {
			result.setErrorCode(Constants.FAIL);
			result.setErrorMessage("请输入正确的UID");
		}*/
		return result;
	}

	/**
	 * 获取验证码
	 */
	@RequestMapping ( value = "/mobile/phonetest" )
	public @ResponseBody JsonResult phonetest( HttpServletRequest request, HttpServletResponse response ) {

		JsonResult result = new JsonResult( );
		String type = request.getParameter( "type" );// 0=短信，1=语音，默认是0
		//注册时，获取的是短信验证码则数据库PHONE_CODE_RECORD表type=0，语音则type=1，业务类型值：onLineRegister,offLineRegister,forgotPassword,bankCard,modifyPhone,modifyMail,forgotTransactionPassword
		String business = request.getParameter( "business" );
		String mobile = request.getParameter( "mobile" );

		logger.info("验证码请求 ： " + mobile);

		if ( !"1".equals( type ) )
			type = "0";

		// 生成验证码
		boolean cansend = false;
		AccessLog accessLog = userService.findAccessTokenByToken( request.getParameter( "access_token" ) );

		String vericode = null;
		if ( accessLog != null ) {

			logger.info("手机号码：" + mobile + "，验证码：" + vericode + "，findAccessTokenByToken");
			// if ( !business.equals( "offLineRegister" ) &&
			// !business.equals( "onLineRegister" ) ) {
			if ( !business.equals( "forgotPassword" ) && !business.equals( "modifyPhone" ) ) {
				// 除了忘记密码，其他都需要去获取手机号
				/*long userId = accessLog.getLoginuserId( );
				JinxinRemoteReturn remoteReturn = JinxinRemoteUserService.getUserinfo( "" + userId );
				UserInfo userInfo = ( UserInfo ) remoteReturn.getNormalObject( );
				if ( userInfo != null ) {
					mobile = userInfo.getMobile( );
				}*/

			} else if ( business.equals( "forgotPassword" ) ) {
				/*UUserInfo uinfo = JinxinRemoteUserService.getUserinfoByKey( mobile );
				if ( uinfo == null ) {
					result.setErrorCode( Constants.FAIL );
					result.setErrorMessage( "此手机号码未注册" );
					return result;
				}*/
			} else if ( business.equals( "modifyPhone" ) ) {
				/*UUserInfo uinfo = JinxinRemoteUserService.getUserinfoByKey( mobile );
				if ( uinfo != null ) {
					result.setErrorCode( Constants.FAIL );
					result.setErrorMessage( "手机号已存在" );
					return result;
				}*/
			}

			if ( mobile != null && mobile.length( ) > 0 ) {
				vericode = RandomUtils.createRandomString( 6 );
				Date vericodeExpired = new Date( );
				vericodeExpired.setTime( new Date( ).getTime( ) + 30 * 60 * 1000 );
				accessLog.setVericode( vericode );
				accessLog.setVericodeExpired( vericodeExpired );
				accessLog.setVeriMobile( mobile );
				userService.updateAccessTokenByAccessLog( accessLog );
				cansend = true;
			} else {
				result.setErrorCode( Constants.FAIL );
				result.setErrorMessage( "未绑定手机号" );
				result.setCurrentTime(new Date());
				return result;
			}
			// 调用接口去发送
			if ( cansend ) {
				BigDecimal uid = new BigDecimal( accessLog.getLoginuserId( ) );
				/*if ( "1".equals( type ) ) {
					logger.info("手机号码：" + mobile + "，验证码：" + vericode + "，checkVoiceRecord");
					JinxinRemoteReturn remoteReturn = JinxinRemoteUserService.checkVoiceRecord( uid, mobile, vericode, type );
					String str = null;
					Object object = remoteReturn.getNormalObject();
					if (object != null) {
						List list = (List) object;
						if (list != null && list.size() > 0) {
							str = list.get(0).toString();
						}
					}
					if (null == str||!"timeOut".equals(str)) {
						logger.info("手机号码：" + mobile + "，验证码：" + vericode + "，sendMessage" + "，checkVoiceRecord");
						String msgid = SMS.sendVoiceMessage( this.getClass( ), vericode, mobile );
						logger.info( "MSGID = " + msgid + "，手机号码：" + mobile);
					} else {
						result.setErrorCode( Constants.FAIL );
						result.setErrorMessage("您由于发送过多次数的验证码,已被锁定,请在1小时后尝试再次获取验证码");
						return result;
					}
				} else {
					logger.info("手机号码：" + mobile + "，验证码：" + vericode + "，checkPhoneCodeRecord");
					JinxinRemoteReturn remoteReturn = JinxinRemoteUserService.checkPhoneCodeRecord( uid, mobile, vericode, type );
					String str = null;
					Object object = remoteReturn.getNormalObject();
					if (object != null) {
						List list = (List) object;
						if (list != null && list.size() > 0) {
							str = list.get(0).toString();
						}
					}
					if (null == str||!"timeOut".equals(str)) {
						logger.info("手机号码：" + mobile + "，验证码：" + vericode + "，sendMessage" + "，checkPhoneCodeRecord");
						String msgid = SMS.sendMessage( this.getClass( ), StringUtils.replace( SMS.SMS_CONTENT, "{code}", vericode ),
								mobile );
						logger.info( "MSGID = " + msgid + "，手机号码：" + mobile);
					} else {
						result.setErrorCode( Constants.FAIL );
						result.setErrorMessage("您由于发送过多次数的验证码,已被锁定,请在1小时后尝试再次获取验证码");
						return result;
					}
				}*/
				result.setErrorCode(Constants.SUCC);
				result.setErrorMessage("验证码发送成功");
				result.setCurrentTime(new Date());
			} else {
				result.setErrorCode( Constants.FAIL );
				result.setErrorMessage( "不是合法的请求" );
				result.setCurrentTime(new Date());
			}
		}
		return result;
	}



	/**
	 * 修改我的用户名
	 * 你
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping (value = "/mobile/modifyusername", method = {RequestMethod.POST, RequestMethod.GET})
	public JsonResult modifyusername( HttpServletRequest request, HttpServletResponse response) {
		String userId = request.getParameter("uid");
		String userName = request.getParameter("username");
		JsonResult result = new JsonResult();
		/*if (StringUtils.isEmpty(userId)) {
			result.setErrorCode(Constants.FAIL);
			result.setErrorMessage("用户ID为空");
			result.setCurrentTime(new Date());
			return result;
		}
		if (StringUtils.isEmpty(userName)) {
			result.setErrorCode(Constants.FAIL);
			result.setErrorMessage("用户名为空");
			result.setCurrentTime(new Date());
			return result;
		}
		//正式修改用户名
		//查询用户名是否被占用
		UUserInfo userInfo = JinxinRemoteUserService.getUserinfoByKey(userName);
		if (userInfo != null) {
			result.setErrorCode(Constants.FAIL);
			result.setErrorMessage("该用户名已被占用");
			result.setCurrentTime(new Date());
			return result;
		}
		JinxinRemoteReturn remoteReturn = JinxinRemoteUserService.getUserinfo(userId);
		UserInfo u = (UserInfo) remoteReturn.getNormalObject();
		//修改之前先判断一下用户名是否可以修改
		if (u.getUnameModified()) {//null或者0表示不可以修改，只有1表示可以修改, true标识可以修改
			boolean isAllowSecondModify = false;//true允许二次修改,false不允许二次修改
			JinxinRemoteReturn rtn = JinxinRemoteUserService.updateUserNameByUserId(userId, userName, isAllowSecondModify);
			if (rtn.isSuccess()) {
				result.setErrorCode(Constants.SUCC);
				result.setErrorMessage("修改成功");
				result.setCurrentTime(new Date());
			} else {
				result.setErrorCode(Constants.FAIL);
				result.setErrorMessage("修改失败");
				result.setCurrentTime(new Date());
			}
		} else {
			result.setErrorCode(Constants.FAIL);
			result.setErrorMessage("您已修改过用户名，不能再次修改");
			result.setCurrentTime(new Date());
		}*/
		return result;
	}

}