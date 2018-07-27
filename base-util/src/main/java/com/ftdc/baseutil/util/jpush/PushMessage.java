package com.ftdc.baseutil.util.jpush;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.PushPayload.Builder;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.ftdc.baseutil.bean.JumpTo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 极光推送
 * 
 * @author haoxiaowei
 * @date 2018-2-28
 */
public class PushMessage {

	protected static final Logger logger = LoggerFactory.getLogger(PushMessage.class);

	protected static final String APP_KEY = "5b7081b8c95b564d50186171";
	protected static final String MASTER_SECRET = "21bb6a72f387e467d54bdc8c";

	public static final String TITLE = "测试推送title";
	public static final String ALERT = "测试推送alert";

	public static void main(String[] args) {
		// testSendPush("0ccf40871c6b4c5e8990421abea41778", ALERT, "tradeId",
		// "tradeType",JumpTo.JUMP_TO_ORDER.getValue());
		// sendCarditCardPush("0ccf40871c6b4c5e8990421abea41778", ALERT,
		// "c30fccdad89148858aee9c485d1091bf",
		// JumpTo.JUMP_TO_CREDIT.getValue());
		// sendCommonPush("38f9e380c7544917b3248c401d21d9e8", ALERT,
		// JumpTo.NO_JUMP.getValue(),2+"");
		// sendToAllPush("温馨提示：为解决漏洞问题，原来越转账到银行卡两个接口0310.0311已经停用，换用这个0501",
		// JumpTo.NO_JUMP.getValue());
		// sendToAllPush("温馨提示：联心服务端已部署到测试环境，前端访问:199q34d699.iok.la:40110，后台访问:192.168.121.45:5902，回调地址：199q34d699.iok.la:40114,前端未经过加密处理的请访问：192.168.123.162:5901",JumpTo.NO_JUMP.getValue());
	}

	/**
	 * 自定义简单消息推送
	 * 
	 * @param title
	 *            标题
	 * @param alert
	 *            推送内容
	 * @param jumpTo
	 *            跳转目标，为0或空则默认跳转账单，1-跳转信用卡还款，2-登录，3-账单详情，4-余额明细，9-无跳转
	 * @param noticeType
	 *            通知类型 1-仅通知，2-通知并更新配置
	 */
	public static void sendOwnPush(String title, String alert, String jumpTo, String noticeType) {
		ClientConfig clientConfig = ClientConfig.getInstance();
		final JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, clientConfig);
		Map<String, String> extras = new HashMap<String, String>();
		extras.put("jumpTo", jumpTo);
		extras.put("title", title);
		extras.put("noticeType", noticeType);
		final PushPayload payload = new Builder().setPlatform(Platform.all()).setAudience(Audience.all())
				.setNotification(Notification.newBuilder().setAlert(alert)
						.addPlatformNotification(AndroidNotification.newBuilder().addExtras(extras).build())
						.addPlatformNotification(IosNotification.newBuilder().addExtras(extras).build()).build())
				.build();
		try {
			PushResult result = jpushClient.sendPush(payload);
			logger.info("Got result - " + result);
		} catch (APIConnectionException e) {
			logger.error("Connection error. Should retry later. ", e);
			logger.error("Sendno: " + payload.getSendno());
		} catch (APIRequestException e) {
			logger.error("Error response from JPush server. Should review and fix it. ", e);
			logger.info("HTTP Status: " + e.getStatus());
			logger.info("Error Code: " + e.getErrorCode());
			logger.info("Error Message: " + e.getErrorMessage());
			logger.info("Msg ID: " + e.getMsgId());
			logger.error("Sendno: " + payload.getSendno());
		}
	}

	/**
	 * 发送极光推送
	 * 
	 * @param alias
	 *            特定用户
	 * @param alert
	 *            推送内容
	 * @param balanceId
	 *            流水ID
	 * @param tradeType
	 *            交易类型
	 * @param jumpTo
	 *            跳转目标，为0或空则默认跳转账单，1-跳转信用卡还款，2-登录，3-账单详情，4-余额明细，9-无跳转
	 */
	public static void testSendPush(String alias, String alert, String balanceId, String tradeType, String jumpTo) {
		ClientConfig clientConfig = ClientConfig.getInstance();
		final JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, clientConfig);
		final PushPayload payload = buildPushObject_all_alias_alert_order(alias, alert, balanceId, tradeType, jumpTo);
		try {
			PushResult result = jpushClient.sendPush(payload);
			logger.info("Got result - " + result);
		} catch (APIConnectionException e) {
			logger.error("Connection error. Should retry later. ", e);
			logger.error("Sendno: " + payload.getSendno());
		} catch (APIRequestException e) {
			logger.error("Error response from JPush server. Should review and fix it. ", e);
			logger.info("HTTP Status: " + e.getStatus());
			logger.info("Error Code: " + e.getErrorCode());
			logger.info("Error Message: " + e.getErrorMessage());
			logger.info("Msg ID: " + e.getMsgId());
			logger.error("Sendno: " + payload.getSendno());
		}
	}

	/**
	 * 信用卡还款发送极光推送
	 * 
	 * @param alias
	 *            特定用户
	 * @param alert
	 *            推送内容
	 * @param cardId
	 *            信用卡ID
	 * @param jumpTo
	 *            跳转目标，为0或空则默认跳转账单，1-跳转信用卡还款，2-登录，9-无跳转
	 */
	public static void sendCarditCardPush(String alias, String alert, String cardId, String jumpTo) {
		ClientConfig clientConfig = ClientConfig.getInstance();
		final JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, clientConfig);
		final PushPayload payload = buildPushObject_all_alias_alert_creditCard(alias, alert, cardId, jumpTo);
		try {
			PushResult result = jpushClient.sendPush(payload);
			logger.info("Got result - " + result);
		} catch (APIConnectionException e) {
			logger.error("Connection error. Should retry later. ", e);
			logger.error("Sendno: " + payload.getSendno());
		} catch (APIRequestException e) {
			logger.error("Error response from JPush server. Should review and fix it. ", e);
			logger.info("HTTP Status: " + e.getStatus());
			logger.info("Error Code: " + e.getErrorCode());
			logger.info("Error Message: " + e.getErrorMessage());
			logger.info("Msg ID: " + e.getMsgId());
			logger.error("Sendno: " + payload.getSendno());
		}
	}

	/**
	 * 通用极光消息推送
	 * 
	 * @param alias
	 *            特定用户
	 * @param alert
	 *            推送内容
	 * @param jumpTo
	 *            跳转目标，为0则默认跳转账单，1-跳转信用卡还款，2-登录，9-无跳转
	 */
	public static void sendCommonPush(String alias, String alert, String jumpTo) {
		ClientConfig clientConfig = ClientConfig.getInstance();
		final JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, clientConfig);
		final PushPayload payload = buildPushObject_all_alias_alert_common(alias, alert, jumpTo);
		try {
			PushResult result = jpushClient.sendPush(payload);
			logger.info("Got result - " + result);
		} catch (APIConnectionException e) {
			logger.error("Connection error. Should retry later. ", e);
			logger.error("Sendno: " + payload.getSendno());
		} catch (APIRequestException e) {
			logger.error("Error response from JPush server. Should review and fix it. ", e);
			logger.info("HTTP Status: " + e.getStatus());
			logger.info("Error Code: " + e.getErrorCode());
			logger.info("Error Message: " + e.getErrorMessage());
			logger.info("Msg ID: " + e.getMsgId());
			logger.error("Sendno: " + payload.getSendno());
		}
	}

	/**
	 * 推送到所有用户
	 * 
	 * @param alias
	 *            特定用户
	 * @param alert
	 *            推送内容
	 * @param jumpTo
	 *            跳转目标，为0则默认跳转账单，1-跳转信用卡还款，9-无跳转
	 */
	public static void sendToAllPush(String alert, String jumpTo) {
		ClientConfig clientConfig = ClientConfig.getInstance();
		final JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, clientConfig);
		final PushPayload payload = buildPushObject_all_all_alert(alert, jumpTo);
		try {
			PushResult result = jpushClient.sendPush(payload);
			logger.info("Got result - " + result);
		} catch (APIConnectionException e) {
			logger.error("Connection error. Should retry later. ", e);
			logger.error("Sendno: " + payload.getSendno());
		} catch (APIRequestException e) {
			logger.error("Error response from JPush server. Should review and fix it. ", e);
			logger.info("HTTP Status: " + e.getStatus());
			logger.info("Error Code: " + e.getErrorCode());
			logger.info("Error Message: " + e.getErrorMessage());
			logger.info("Msg ID: " + e.getMsgId());
			logger.error("Sendno: " + payload.getSendno());
		}
	}

	public static PushPayload buildPushObject_android_and_ios() {
		Map<String, String> extras = new HashMap<String, String>();
		extras.put("test", "https://community.jiguang.cn/push");
		return PushPayload.newBuilder().setPlatform(Platform.android_ios()).setAudience(Audience.all())
				.setNotification(Notification.newBuilder().setAlert("alert content")
						.addPlatformNotification(
								AndroidNotification.newBuilder().setTitle("Android Title").addExtras(extras).build())
						.addPlatformNotification(
								IosNotification.newBuilder().incrBadge(1).addExtra("extra_key", "extra_value").build())
						.build())
				.build();
	}

	/**
	 * 快捷地构建推送对象：所有平台，所有设备，内容为 ALERT 的通知
	 * 
	 * @return PushPayload
	 */
	public static PushPayload buildPushObject_all_all_alert() {
		return PushPayload.alertAll(ALERT);
	}

	/**
	 * 快捷地构建推送对象：所有平台，所有设备，内容为 ALERT 的通知
	 * 
	 * @param alert
	 *            通知内容
	 * @param jumpTo
	 *            跳转目标，为0则默认跳转账单，1-跳转信用卡还款，9-无跳转
	 * @return PushPayload
	 */
	public static PushPayload buildPushObject_all_all_alert(String alert, String jumpTo) {
		Map<String, String> extras = new HashMap<String, String>();
		extras.put("jumpTo", jumpTo);
		fillTitle(jumpTo, extras);
		return new Builder().setPlatform(Platform.all()).setAudience(Audience.all())
				.setNotification(Notification.newBuilder().setAlert(alert)
						.addPlatformNotification(AndroidNotification.newBuilder().addExtras(extras).build())
						.addPlatformNotification(IosNotification.newBuilder().addExtras(extras).build()).build())
				.build();
	}

	/**
	 * 构建推送对象：所有平台，推送目标是别名为 "alias1"，通知内容为 ALERT
	 * 
	 * @return PushPayload
	 */
	public static PushPayload buildPushObject_all_alias_alert(String alias, String alert) {
		return PushPayload.newBuilder().setPlatform(Platform.all()).setAudience(Audience.alias(alias))
				.setNotification(Notification.alert(alert)).build();
	}

	/**
	 * 构建推送对象：所有平台，推送目标是别名为 "alias1"，通知内容为 ALERT,附加信息订单ID
	 * 
	 * @return PushPayload
	 */
	public static PushPayload buildPushObject_all_alias_alert_order(String alias, String alert, String balanceId,
			String tradeType, String jumpTo) {
		Map<String, String> extras = new HashMap<String, String>();
		extras.put("balanceId", balanceId);
		extras.put("tradeType", tradeType);
		extras.put("jumpTo", jumpTo);
		fillTitle(jumpTo, extras);
		return PushPayload.newBuilder().setPlatform(Platform.all()).setAudience(Audience.alias(alias))
				.setNotification(Notification.newBuilder().setAlert(alert)
						.addPlatformNotification(AndroidNotification.newBuilder().addExtras(extras).build())
						.addPlatformNotification(IosNotification.newBuilder().addExtras(extras).build()).build())
				.build();
	}

	/**
	 * 信用卡推送消息通知
	 * 
	 * @param alias
	 * @param alert
	 * @param jumpTo
	 *            跳转目标
	 * @return
	 */
	public static PushPayload buildPushObject_all_alias_alert_creditCard(String alias, String alert, String cardId,
			String jumpTo) {
		Map<String, String> extras = new HashMap<String, String>();
		extras.put("jumpTo", jumpTo);
		extras.put("cardId", cardId);
		fillTitle(jumpTo, extras);
		return PushPayload.newBuilder().setPlatform(Platform.all()).setAudience(Audience.alias(alias))
				.setNotification(Notification.newBuilder().setAlert(alert)
						.addPlatformNotification(AndroidNotification.newBuilder().addExtras(extras).build())
						.addPlatformNotification(IosNotification.newBuilder().addExtras(extras).build()).build())
				.build();
	}

	/**
	 * 通用极光消息通知
	 * 
	 * @param alias
	 * @param alert
	 * @param jumpTo
	 *            跳转目标
	 * @return
	 */
	public static PushPayload buildPushObject_all_alias_alert_common(String alias, String alert, String jumpTo) {
		Map<String, String> extras = new HashMap<String, String>();
		extras.put("jumpTo", jumpTo);
		/*
		 * for(String item:args){ //会员账号封停状态 extras.put("status", item); }
		 */
		fillTitle(jumpTo, extras);
		return PushPayload.newBuilder().setPlatform(Platform.all()).setAudience(Audience.alias(alias))
				.setNotification(Notification.newBuilder().setAlert(alert)
						.addPlatformNotification(AndroidNotification.newBuilder().addExtras(extras).build())
						.addPlatformNotification(IosNotification.newBuilder().addExtras(extras).build()).build())
				.build();
	}

	/**
	 * 构建推送对象：平台是 Android，目标是 tag 为 "tag1" 的设备，内容是 Android 通知 ALERT，并且标题为 TITLE
	 * 
	 * @return PushPayload
	 */
	public static PushPayload buildPushObject_android_tag_alertWithTitle() {
		return PushPayload.newBuilder().setPlatform(Platform.android()).setAudience(Audience.tag("tag1"))
				.setNotification(Notification.android(ALERT, TITLE, null)).build();
	}

	/**
	 * 添加标题
	 * 
	 * @param jumpTo
	 * @param extras
	 */
	private static void fillTitle(String jumpTo, Map<String, String> extras) {
		if (JumpTo.JUMP_TO_ORDER.getValue().equals(jumpTo)) {
			extras.put("title", JumpTo.JUMP_TO_ORDER.getTitle());
		} else if (JumpTo.JUMP_TO_CREDIT.getValue().equals(jumpTo)) {
			extras.put("title", JumpTo.JUMP_TO_CREDIT.getTitle());
		} else if (JumpTo.NO_JUMP.getValue().equals(jumpTo)) {
			extras.put("title", JumpTo.NO_JUMP.getTitle());
		} else if (JumpTo.JUMP_TO_LOGIN.getValue().equals(jumpTo)) {
			extras.put("title", JumpTo.JUMP_TO_LOGIN.getTitle());
		} else if (JumpTo.JUMP_TO_ORDETAIL.getValue().equals(jumpTo)) {
			extras.put("title", JumpTo.JUMP_TO_ORDETAIL.getTitle());
		} else if (JumpTo.JUMP_TO_BALANCE.getValue().equals(jumpTo)) {
			extras.put("title", JumpTo.JUMP_TO_BALANCE.getTitle());
		} else if (JumpTo.JUMP_TO_VISUALCARD.getValue().equals(jumpTo)) {
			extras.put("title", JumpTo.JUMP_TO_VISUALCARD.getTitle());
		} else {
			extras.put("title", JumpTo.DEFAULT.getTitle());
		}
	}
}
