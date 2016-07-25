package com.github.hualuomoli.base.aspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.github.hualuomoli.base.Persistent;
import com.github.hualuomoli.base.annotation.persistent.PreBatchInsert;
import com.github.hualuomoli.base.annotation.persistent.PreDelete;
import com.github.hualuomoli.base.annotation.persistent.PreInsert;
import com.github.hualuomoli.base.annotation.persistent.PreUpdate;
import com.github.hualuomoli.base.constant.Status;
import com.github.hualuomoli.commons.util.JsonUtils;
import com.github.hualuomoli.commons.util.RandomUtils;
import com.github.hualuomoli.user.UserUtils;

// service 持久化 预处理
@Aspect
@Component(value = "com.github.hualuomoli.base.aspect.ServicePretreatmentAspect")
public class ServicePersistentAspect {

	private static final Logger logger = LoggerFactory.getLogger(ServicePersistentAspect.class);

	// 切点到service
	@Pointcut("execution(* com.github.hualuomoli..*.*Service*.*(..))")
	public void pointcut() {
	}

	@Around(value = "pointcut()", argNames = "args")
	public Object doBefore(ProceedingJoinPoint joinPoint) throws Throwable {

		if (logger.isDebugEnabled()) {
			logger.debug("arounding.............");
		}
		Object ret = null;

		// 参数
		Object[] args = joinPoint.getArgs();

		if (args != null && args.length > 0) {

			MethodSignature signature = (MethodSignature) joinPoint.getSignature();
			Method method = signature.getMethod();

			// 预处理
			args = this.prePersistent(joinPoint, args, method);
			ret = joinPoint.proceed(args);
		} else {
			ret = joinPoint.proceed(args);
		}

		if (logger.isDebugEnabled()) {
			if (ret != null) {
				logger.debug("return value {}", JsonUtils.toJson(ret));
			}
			logger.debug("arounded...");
		}

		return ret;

	}

	/**
	 * 持久化预处理
	 * @param joinPoint 切点
	 * @param args 参数
	 * @param method 方法
	 */
	@SuppressWarnings("unchecked")
	private Object[] prePersistent(JoinPoint joinPoint, Object[] args, Method method) {

		// 参数注解,判断每个参数的注解
		Annotation[][] as = method.getParameterAnnotations();
		for (int i = 0; i < as.length; i++) {
			// 参数
			Object parameter = args[i];
			// 参数为空
			if (parameter == null) {
				continue;
			}

			// 注解
			Annotation[] annotations = as[i];

			// 注解不存在
			if (annotations == null || annotations.length == 0) {
				continue;
			}

			// 注解处理参数
			for (Annotation annotation : annotations) {

				if (annotation.annotationType() == PreInsert.class && Persistent.class.isAssignableFrom(parameter.getClass())) {
					// 插入前预处理
					args[i] = this._doPreInsert((Persistent) parameter);
					break;
				} else if (annotation.annotationType() == PreUpdate.class && Persistent.class.isAssignableFrom(parameter.getClass())) {
					// 修改前预处理
					args[i] = this._doPreUpdate((Persistent) parameter);
					break;
				} else if (annotation.annotationType() == PreDelete.class && Persistent.class.isAssignableFrom(parameter.getClass())) {
					// 逻辑删除前预处理
					args[i] = this._doPreDelete((Persistent) parameter);
					break;
				} else if (annotation.annotationType() == PreBatchInsert.class && Collection.class.isAssignableFrom(parameter.getClass())) {
					// 批量插入前持久化预处理
					args[i] = this._doPreBatchInsert((Collection<Persistent>) parameter);
					break;
				}
			}

		} // end for

		return args;

	}

	/**
	 * 插入前预处理
	 */
	private Persistent _doPreInsert(Persistent persistent) {
		if (logger.isDebugEnabled()) {
			logger.debug("插入前预处理");
		}
		String username = UserUtils.getUsername();
		Date currentDate = new Date();

		persistent.setId(StringUtils.isBlank(persistent.getId()) ? RandomUtils.getString() : persistent.getId());
		persistent.setCreateBy(username);
		persistent.setCreateDate(currentDate);
		persistent.setUpdateBy(username);
		persistent.setUpdateDate(currentDate);
		persistent.setStatus(Status.NOMAL.getValue());
		return persistent;

	}

	/**
	 * 修改前预处理
	 */
	private Persistent _doPreUpdate(Persistent persistent) {
		if (logger.isDebugEnabled()) {
			logger.debug("修改前预处理");
		}
		String username = UserUtils.getUsername();
		Date currentDate = new Date();

		persistent.setCreateBy(null);
		persistent.setCreateDate(null);
		persistent.setUpdateBy(username);
		persistent.setUpdateDate(currentDate);
		persistent.setStatus(Status.NOMAL.getValue()); // 只有正常数据才可以修改

		return persistent;

	}

	/**
	 * 删除前预处理
	 */
	private Persistent _doPreDelete(Persistent persistent) {
		if (logger.isDebugEnabled()) {
			logger.debug("删除前预处理");
		}
		String username = UserUtils.getUsername();
		Date currentDate = new Date();
		// 删除只设置ID
		String id = persistent.getId();
		try {
			persistent = persistent.getClass().newInstance();
			persistent.setId(id);

			persistent.setUpdateBy(username);
			persistent.setUpdateDate(currentDate);
			persistent.setStatus(Status.DELETED.getValue()); // 设置为删除状态

			return persistent;
		} catch (Exception e) {
			return persistent;
		}

	}

	/**
	 * 批量插入前预处理
	 */
	private Collection<Persistent> _doPreBatchInsert(Collection<Persistent> persistents) {
		if (logger.isDebugEnabled()) {
			logger.debug("批量插入前预处理");
		}
		String username = UserUtils.getUsername();
		Date currentDate = new Date();

		for (Persistent persistent : persistents) {
			persistent.setId(StringUtils.isBlank(persistent.getId()) ? RandomUtils.getString() : persistent.getId());
			persistent.setCreateBy(username);
			persistent.setCreateDate(currentDate);
			persistent.setUpdateBy(username);
			persistent.setUpdateDate(currentDate);
			persistent.setStatus(Status.NOMAL.getValue());
		}

		return persistents;

	}

}
