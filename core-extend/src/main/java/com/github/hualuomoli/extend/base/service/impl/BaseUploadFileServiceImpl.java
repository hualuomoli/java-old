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
import com.github.hualuomoli.extend.base.entity.BaseUploadFile;
import com.github.hualuomoli.extend.entity.UploadFile;
import com.github.hualuomoli.extend.base.mapper.BaseUploadFileMapper;
import com.github.hualuomoli.extend.base.service.BaseUploadFileService;
import com.github.hualuomoli.exception.MoreDataFoundException;

// #BaseUploadFile
@Service(value = "com.github.hualuomoli.extend.base.service.BaseUploadFileServiceImpl")
@Transactional(readOnly = true)
public class BaseUploadFileServiceImpl implements BaseUploadFileService {

	@Autowired
	private BaseUploadFileMapper baseUploadFileMapper;
	
	@Override
	public BaseUploadFile get(UploadFile uploadFile) {
		return this.get(uploadFile.getId());
	}
	
	@Override
	public BaseUploadFile get(String id) {
		return baseUploadFileMapper.get(id);
	}
	

	@Override
	@Transactional(readOnly = false)
	public int insert(@PreInsert UploadFile uploadFile) {
		return baseUploadFileMapper.insert(uploadFile);
	}
	
	@Override
	@Transactional(readOnly = false)
	public <T extends UploadFile> int batchInsert(@PreBatchInsert  List<T> list) {
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
			count += baseUploadFileMapper.batchInsert(newList);
		}
		return count;
	}

	@Override
	@Transactional(readOnly = false)
	public int update(@PreUpdate UploadFile uploadFile) {
		return baseUploadFileMapper.update(uploadFile);
	}


	@Override
	@Transactional(readOnly = false)
	public int delete(UploadFile uploadFile) {
		return this.delete(uploadFile.getId());
	}
	
	@Override
	@Transactional(readOnly = false)
	public int delete(String id) {
		return baseUploadFileMapper.delete(id);
	}
	
	@Override
	@Transactional(readOnly = false)
	public int deleteByIds(String[] ids) {
		if (ids == null || ids.length == 0) {
			return 0;
		}
		return baseUploadFileMapper.deleteByIds(ids);
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
	public List<BaseUploadFile> findList(BaseUploadFile baseUploadFile) {
		return baseUploadFileMapper.findList(baseUploadFile);
	}
	
	@Override
	public List<BaseUploadFile> findList(BaseUploadFile baseUploadFile, String... orderByStrArray) {
		return this.findList(baseUploadFile, new Pagination(orderByStrArray));
	}

	@Override
	public List<BaseUploadFile> findList(BaseUploadFile baseUploadFile, Order... orders) {
		return this.findList(baseUploadFile, new Pagination(orders));
	}

	@Override
	public List<BaseUploadFile> findList(BaseUploadFile baseUploadFile, List<Order> orders) {
		return this.findList(baseUploadFile, new Pagination(orders));
	}
	
	private List<BaseUploadFile> findList(BaseUploadFile baseUploadFile, Pagination pagination) {
		pagination.setQueryType(QueryType.ONLY_DATA);
		Page page = this.findPage(baseUploadFile, pagination);
		return page.getDataList();
	}
	
	@Override
	public Integer getTotal(BaseUploadFile baseUploadFile) {
		Pagination pagination = new Pagination(QueryType.ONLY_COUNT);
		Page page = this.findPage(baseUploadFile, pagination);
		return page.getCount();
	}
	
	@Override
	public Page findPage(BaseUploadFile baseUploadFile, Integer pageNo, Integer pageSize) {
		return this.findPage(baseUploadFile, new Pagination(pageNo, pageSize));
	}

	@Override
	public Page findPage(BaseUploadFile baseUploadFile, Integer pageNo, Integer pageSize, String... orderByStrArray) {
		return this.findPage(baseUploadFile, new Pagination(pageNo, pageSize, orderByStrArray));
	}

	@Override
	public Page findPage(BaseUploadFile baseUploadFile, Integer pageNo, Integer pageSize, Order... orders) {
		return this.findPage(baseUploadFile, new Pagination(pageNo, pageSize, orders));
	}

	@Override
	public Page findPage(BaseUploadFile baseUploadFile, Integer pageNo, Integer pageSize, List<Order> orders) {
		return this.findPage(baseUploadFile, new Pagination(pageNo, pageSize, orders));
	}
	
	@Override
	public Page findPage(BaseUploadFile baseUploadFile, Pagination pagination) {

		// set local thread
		PaginationInterceptor.pushPagination(pagination);
		// query
		List<BaseUploadFile> list = baseUploadFileMapper.findList(baseUploadFile);
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
