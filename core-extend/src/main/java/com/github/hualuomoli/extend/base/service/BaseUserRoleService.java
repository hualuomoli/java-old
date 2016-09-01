package com.github.hualuomoli.extend.base.service;

import java.util.Collection;
import java.util.List;

import com.github.hualuomoli.base.entity.Page;
import com.github.hualuomoli.base.plugin.mybatis.entity.Order;
import com.github.hualuomoli.base.plugin.mybatis.entity.Pagination;
import com.github.hualuomoli.extend.base.entity.BaseUserRole;
import com.github.hualuomoli.extend.entity.UserRole;

// #BaseUserRole
public interface BaseUserRoleService {

	BaseUserRole get(UserRole userRole);
	
	BaseUserRole get(String id);
	
	
	int insert(UserRole userRole);
	
	<T extends UserRole> int batchInsert(List<T> list);

	int update(UserRole userRole);
	
	

	int delete(UserRole userRole);
	
	int delete(String id);
	
	int deleteByIds(String[] ids);
	
	int deleteByIds(Collection<String> ids);

	List<BaseUserRole> findList(BaseUserRole baseUserRole);
	
	List<BaseUserRole> findList(BaseUserRole baseUserRole, String... orderByStrArray);

	List<BaseUserRole> findList(BaseUserRole baseUserRole, Order... orders);

	List<BaseUserRole> findList(BaseUserRole baseUserRole, List<Order> orders);
	
	Integer getTotal(BaseUserRole baseUserRole);
	
	Page findPage(BaseUserRole baseUserRole, Integer pageNo, Integer pageSize);

	Page findPage(BaseUserRole baseUserRole, Integer pageNo, Integer pageSize, String... orderByStrArray);

	Page findPage(BaseUserRole baseUserRole, Integer pageNo, Integer pageSize, Order... orders);

	Page findPage(BaseUserRole baseUserRole, Integer pageNo, Integer pageSize, List<Order> orders);

	Page findPage(BaseUserRole baseUserRole, Pagination pagination);
	
}
