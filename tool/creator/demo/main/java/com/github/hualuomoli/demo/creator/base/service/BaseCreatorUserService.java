package com.github.hualuomoli.demo.creator.base.service;

import java.util.Collection;
import java.util.List;

import com.github.hualuomoli.base.entity.Page;
import com.github.hualuomoli.base.plugin.mybatis.entity.Order;
import com.github.hualuomoli.base.plugin.mybatis.entity.Pagination;
import com.github.hualuomoli.demo.creator.base.entity.BaseCreatorUser;

// #BaseCreatorUser
public interface BaseCreatorUserService {

	BaseCreatorUser get(BaseCreatorUser baseCreatorUser);
	
	BaseCreatorUser get(String id);
	
	
	int insert(BaseCreatorUser baseCreatorUser);
	
	int batchInsert(List<BaseCreatorUser> list);

	int update(BaseCreatorUser baseCreatorUser);
	
	int logicalDelete(BaseCreatorUser baseCreatorUser);

	int logicalDelete(String id);

	int delete(BaseCreatorUser baseCreatorUser);
	
	int delete(String id);
	
	int deleteByIds(String[] ids);
	
	int deleteByIds(Collection<String> ids);

	List<BaseCreatorUser> findList(BaseCreatorUser baseCreatorUser);
	
	List<BaseCreatorUser> findList(BaseCreatorUser baseCreatorUser, String... orderByStrArray);

	List<BaseCreatorUser> findList(BaseCreatorUser baseCreatorUser, Order... orders);

	List<BaseCreatorUser> findList(BaseCreatorUser baseCreatorUser, List<Order> orders);
	
	Integer getTotal(BaseCreatorUser baseCreatorUser);
	
	Page findPage(BaseCreatorUser baseCreatorUser, Integer pageNo, Integer pageSize);

	Page findPage(BaseCreatorUser baseCreatorUser, Integer pageNo, Integer pageSize, String... orderByStrArray);

	Page findPage(BaseCreatorUser baseCreatorUser, Integer pageNo, Integer pageSize, Order... orders);

	Page findPage(BaseCreatorUser baseCreatorUser, Integer pageNo, Integer pageSize, List<Order> orders);

	Page findPage(BaseCreatorUser baseCreatorUser, Pagination pagination);
	
}