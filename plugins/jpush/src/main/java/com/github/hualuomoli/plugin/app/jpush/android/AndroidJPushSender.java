package com.github.hualuomoli.plugin.app.jpush.android;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.github.hualuomoli.plugin.app.exception.AppException;
import com.github.hualuomoli.plugin.app.jpush.JPushSender;
import com.github.hualuomoli.plugin.app.push.entity.Message;
import com.github.hualuomoli.plugin.app.push.entity.Notification;
import com.github.hualuomoli.plugin.app.push.entity.RichTxt;

import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;

/**
 * android
 * @author hualuomoli
 *
 */
public class AndroidJPushSender extends JPushSender {

	private static JPushSender jpush;

	public static JPushSender getInstance() {
		if (jpush == null) {
			jpush = new AndroidJPushSender();
		}
		return jpush;
	}

	@Override
	public List<PushPayload> build(Notification push) throws AppException {
		Set<String> tags = push.getTags();
		Set<String> aliases = push.getAliases();
		Set<String> registrationIds = push.getRegistrationIds();

		List<PushPayload> list = new ArrayList<PushPayload>();
		if (tags == null && aliases == null && registrationIds == null) {
			PushPayload pushPayload = PushPayload.newBuilder() //
					.setPlatform(Platform.android())//
					.setAudience(Audience.all()) //
					.setNotification(cn.jpush.api.push.model.notification.Notification.android(push.getAlert(), push.getTitle(), push.getExtras()))//
					.build();
			list.add(pushPayload);
		} else {
			// add tag
			if (tags != null) {
				PushPayload pushPayload = PushPayload.newBuilder() //
						.setPlatform(Platform.android())//
						.setAudience(Audience.tag(tags)) //
						.setNotification(cn.jpush.api.push.model.notification.Notification.android(push.getAlert(), push.getTitle(), push.getExtras()))//
						.build();
				list.add(pushPayload);
			}
			// add alias
			if (aliases != null) {
				PushPayload pushPayload = PushPayload.newBuilder() //
						.setPlatform(Platform.android())//
						.setAudience(Audience.alias(aliases)) //
						.setNotification(cn.jpush.api.push.model.notification.Notification.android(push.getAlert(), push.getTitle(), push.getExtras()))//
						.build();
				list.add(pushPayload);
			}
			// add registrationId
			if (registrationIds != null) {
				PushPayload pushPayload = PushPayload.newBuilder() //
						.setPlatform(Platform.android())//
						.setAudience(Audience.registrationId(registrationIds)) //
						.setNotification(cn.jpush.api.push.model.notification.Notification.android(push.getAlert(), push.getTitle(), push.getExtras()))//
						.build();
				list.add(pushPayload);
			}
		}

		return list;

	}

	@Override
	public List<PushPayload> build(Message push) throws AppException {
		throw new AppException("can not instance");
	}

	@Override
	public List<PushPayload> build(RichTxt push) throws AppException {
		throw new AppException("can not instance");
	}

}
