package com.github.hualuomoli.extend.base.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.hualuomoli.base.annotation.persistent.PreBatchInsert;
import com.github.hualuomoli.base.annotation.persistent.PreDelete;
import com.github.hualuomoli.base.annotation.persistent.PreInsert;
import com.github.hualuomoli.base.annotation.persistent.PreUpdate;
import com.github.hualuomoli.base.entity.Page;
import com.github.hualuomoli.base.plugin.mybatis.entity.Order;
import com.github.hualuomoli.base.plugin.mybatis.entity.Pagination;
import com.github.hualuomoli.base.plugin.mybatis.entity.Pagination.QueryType;
import com.github.hualuomoli.base.plugin.mybatis.interceptor.pagination.PaginationInterceptor;
import com.github.hualuomoli.commons.util.CollectionUtils;
import com.github.hualuomoli.commons.util.CollectionUtils.Config;
import com.github.hualuomoli.extend.base.entity.BaseMenu;
import com.github.hualuomoli.extend.entity.Menu;
import com.github.hualuomoli.extend.base.mapper.BaseMenuMapper;
import com.github.hualuomoli.extend.base.service.BaseMenuService;
import com.github.hualuomoli.exception.MoreDataFoundException;

// #BaseMenu
@Service(value = "com.github.hualuomoli.extend.base.service.BaseMenuServiceImpl")
@Transactional(readOnly = true)
public class BaseMenuServiceImpl implements BaseMenuService {

	@Autowired
	private BaseMenuMapper baseMenuMapper;
	
	@Override
	public BaseMenu get(Menu menu) {
		return this.get(menu.getId());
	}
	
	@Override
	public BaseMenu get(String id) {
		return baseMenuMapper.get(id);
	}
	
	@Override
	public BaseMenu getUnique(
		java.lang.String menuCode
	) {
		BaseMenu baseMenu = new BaseMenu();
		baseMenu.setMenuCode(menuCode);
		List<BaseMenu> list = this.findList(baseMenu);
		if (list == null || list.size() == 0) {
			return null;
		}
		if (list.size() != 1) {
			throw new MoreDataFoundException();
		}
		return list.get(0);
	}

	@Override
	@Transactional(readOnly = false)
	public int insert(@PreInsert Menu menu) {
		return baseMenuMapper.insert(menu);
	}
	
	@Override
	@Transactional(readOnly = false)
	public <T extends Menu> int batchInsert(@PreBatchInsert  List<T> list) {
		if (list == null || list.size() == 0) {
			return 0;
		}	
		Integer count = 0;
		Config config = new Config(100);
		while (true) {
			List<T> newList = CollectionUtils.fetchDatas(list, config);
			if (newList.size() == 0) {
				break;
			}
			count += baseMenuMapper.batchInsert(newList);
		}
		return count;
	}

	@Override
	@Transactional(readOnly = false)
	public int update(@PreUpdate Menu menu) {
		return baseMenuMapper.update(menu);
	}

	@Override
	@Transactional(readOnly = false)
	public int logicalDelete(@PreDelete Menu menu) {
		return baseMenuMapper.update(menu);
	}
	
	@Override
	@Transactional(readOnly = false)
	public int logicalDelete(String id) {
		Menu temp = new Menu();
		temp.setId(id);
		return this.logicalDelete(temp);
	}

	@Override
	@Transactional(readOnly = false)
	public int delete(Menu menu) {
		return this.delete(menu.getId());
	}
	
	@Override
	@Transactional(readOnly = false)
	public int delete(String id) {
		return baseMenuMapper.delete(id);
	}
	
	@Override
	@Transactional(readOnly = false)
	public int deleteByIds(String[] ids) {
		if (ids == null || ids.length == 0) {
			return 0;
		}
		return baseMenuMapper.deleteByIds(ids);
	}
	
	@Override
	@Transactional(readOnly = false)
	public int deleteByIds(Collection<String> ids) {
		if (ids == null || ids.size() == 0) {
			return 0;
		}
		return this.deleteByIds(ids.toArray(new String[]{}));
	}

	@Override
	public List<BaseMenu> findList(BaseMenu baseMenu) {
		return baseMenuMapper.findList(baseMenu);
	}
	
	@Override
	public List<BaseMenu> findList(BaseMenu baseMenu, String... orderByStrArray) {
		return this.findList(baseMenu, new Pagination(orderByStrArray));
	}

	@Override
	public List<BaseMenu> findList(BaseMenu baseMenu, Order... orders) {
		return this.findList(baseMenu, new Pagination(orders));
	}

	@Override
	public List<BaseMenu> findList(BaseMenu baseMenu, List<Order> orders) {
		return this.findList(baseMenu, new Pagination(orders));
	}
	
	private List<BaseMenu> findList(BaseMenu baseMenu, Pagination pagination) {
		pagination.setQueryType(QueryType.ONLY_DATA);
		Page page = this.findPage(baseMenu, pagination);
		return page.getDataList();
	}
	
	@Override
	public Integer getTotal(BaseMenu baseMenu) {
		Pagination pagination = new Pagination(QueryType.ONLY_COUNT);
		Page page = this.findPage(baseMenu, pagination);
		return page.getCount();
	}
	
	@Override
	public Page findPage(BaseMenu baseMenu, Integer pageNo, Integer pageSize) {
		return this.findPage(baseMenu, new Pagination(pageNo, pageSize));
	}

	@Override
	public Page findPage(BaseMenu baseMenu, Integer pageNo, Integer pageSize, String... orderByStrArray) {
		return this.findPage(baseMenu, new Pagination(pageNo, pageSize, orderByStrArray));
	}

	@Override
	public Page findPage(BaseMenu baseMenu, Integer pageNo, Integer pageSize, Order... orders) {
		return this.findPage(baseMenu, new Pagination(pageNo, pageSize, orders));
	}

	@Override
	public Page findPage(BaseMenu baseMenu, Integer pageNo, Integer pageSize, List<Order> orders) {
		return this.findPage(baseMenu, new Pagination(pageNo, pageSize, orders));
	}
	
	@Override
	public Page findPage(BaseMenu baseMenu, Pagination pagination) {

		// set local thread
		PaginationInterceptor.pushPagination(pagination);
		// query
		List<BaseMenu> list = baseMenuMapper.findList(baseMenu);
		// get local thread and remove
		pagination = PaginationInterceptor.popPagination();

		// set page
		Page page = new Page();
		page.setCount(pagination.getCount());
		page.setPageNo(pagination.getPageNo());
		page.setPageSize(pagination.getPageSize());
		page.setDataList(list);

		return page;
	}
	
}
