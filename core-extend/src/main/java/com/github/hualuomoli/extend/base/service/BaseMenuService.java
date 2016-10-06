package com.github.hualuomoli.extend.base.service;

import java.util.Collection;
import java.util.List;

import com.github.hualuomoli.base.entity.Page;
import com.github.hualuomoli.base.plugin.mybatis.entity.Order;
import com.github.hualuomoli.base.plugin.mybatis.entity.Pagination;
import com.github.hualuomoli.extend.base.entity.BaseMenu;
import com.github.hualuomoli.extend.entity.Menu;

// #BaseMenu
public interface BaseMenuService {

	BaseMenu get(Menu menu);
	
	BaseMenu get(String id);
	
	BaseMenu getUnique(
		java.lang.String menuCode
	);
	
	int insert(Menu menu);
	
	<T extends Menu> int batchInsert(List<T> list);

	int update(Menu menu);
	
	 int logicalDelete(Menu menu);

	 int logicalDelete(String id);
	

	int delete(Menu menu);
	
	int delete(String id);
	
	int deleteByIds(String[] ids);
	
	int deleteByIds(Collection<String> ids);

	List<BaseMenu> findList(BaseMenu baseMenu);
	
	List<BaseMenu> findList(BaseMenu baseMenu, String... orderByStrArray);

	List<BaseMenu> findList(BaseMenu baseMenu, Order... orders);

	List<BaseMenu> findList(BaseMenu baseMenu, List<Order> orders);
	
	Integer getTotal(BaseMenu baseMenu);
	
	Page findPage(BaseMenu baseMenu, Integer pageNo, Integer pageSize);

	Page findPage(BaseMenu baseMenu, Integer pageNo, Integer pageSize, String... orderByStrArray);

	Page findPage(BaseMenu baseMenu, Integer pageNo, Integer pageSize, Order... orders);

	Page findPage(BaseMenu baseMenu, Integer pageNo, Integer pageSize, List<Order> orders);

	Page findPage(BaseMenu baseMenu, Pagination pagination);
	
}
