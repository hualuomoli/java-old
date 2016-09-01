package com.github.hualuomoli.extend.base.service;

import java.util.Collection;
import java.util.List;

import com.github.hualuomoli.base.entity.Page;
import com.github.hualuomoli.base.plugin.mybatis.entity.Order;
import com.github.hualuomoli.base.plugin.mybatis.entity.Pagination;
import com.github.hualuomoli.extend.base.entity.BaseRole;
import com.github.hualuomoli.extend.entity.Role;

// #BaseRole
public interface BaseRoleService {

	BaseRole get(Role role);
	
	BaseRole get(String id);
	
	BaseRole getUnique(
		java.lang.String roleCode
	);
	
	int insert(Role role);
	
	<T extends Role> int batchInsert(List<T> list);

	int update(Role role);
	
	 int logicalDelete(Role role);

	 int logicalDelete(String id);
	

	int delete(Role role);
	
	int delete(String id);
	
	int deleteByIds(String[] ids);
	
	int deleteByIds(Collection<String> ids);

	List<BaseRole> findList(BaseRole baseRole);
	
	List<BaseRole> findList(BaseRole baseRole, String... orderByStrArray);

	List<BaseRole> findList(BaseRole baseRole, Order... orders);

	List<BaseRole> findList(BaseRole baseRole, List<Order> orders);
	
	Integer getTotal(BaseRole baseRole);
	
	Page findPage(BaseRole baseRole, Integer pageNo, Integer pageSize);

	Page findPage(BaseRole baseRole, Integer pageNo, Integer pageSize, String... orderByStrArray);

	Page findPage(BaseRole baseRole, Integer pageNo, Integer pageSize, Order... orders);

	Page findPage(BaseRole baseRole, Integer pageNo, Integer pageSize, List<Order> orders);

	Page findPage(BaseRole baseRole, Pagination pagination);
	
}
