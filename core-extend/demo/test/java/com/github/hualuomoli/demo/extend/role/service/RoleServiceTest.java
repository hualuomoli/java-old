package com.github.hualuomoli.demo.extend.role.service;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.hualuomoli.extend.base.entity.BaseRole;
import com.github.hualuomoli.extend.base.service.BaseRoleService;
import com.github.hualuomoli.extend.notice.Noticer;
import com.github.hualuomoli.extend.notice.Notifer;
import com.github.hualuomoli.extend.service.NoticeService;
import com.github.hualuomoli.test.AbstractContextServiceTest;

public class RoleServiceTest extends AbstractContextServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(BaseRoleService.class);

	@Autowired
	private BaseRoleService baseRoleService;
	@Autowired
	private NoticeService noticeService;

	@Test
	public void testUpdate() {
		noticeService.addNotifer(new Notifer<BaseRole>() {

			@Override
			public boolean support(Class<?> cls) {
				return cls == BaseRole.class;
			}

			@Override
			public void notice(BaseRole baseRole) {
				logger.debug("role notice {} {}", baseRole.getRoleCode(), baseRole.getRoleName());
			}
		});
		noticeService.addNotifer(new Notifer<NoticerDemo>() {

			@Override
			public boolean support(Class<?> cls) {
				return cls == Object.class;
			}

			@Override
			public void notice(NoticerDemo noticer) {
				logger.debug("noticer demo.");
			}
		});
		BaseRole baseRole = new BaseRole();
		baseRole.setId("1");
		baseRole.setRoleCode("01");
		baseRole.setRoleName("管理员");
		baseRoleService.update(baseRole);

		noticeService.notice(baseRole);

	}

	private static class NoticerDemo implements Noticer {

	}

}
