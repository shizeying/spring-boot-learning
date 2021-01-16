package com.example.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.domain.TConfig;
import com.example.domain.bo.GeneralBuild;
import com.example.domain.bo.SearchPatternToBuild;
import com.example.domain.vo.ConfigVo;
import com.example.mapper.TConfigMapper;
import com.example.service.TConfigService;
import io.vavr.Tuple;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@CacheConfig(cacheNames = "TConfig")
@Service
public class TConfigServiceImpl extends ServiceImpl<TConfigMapper, TConfig> implements TConfigService {
	//@Autowired
	//private SearchStatusService searchStatusService;
	
	/**
	 * 通过docType获取搜索配置
	 *
	 * @param docType
	 * 		文档类型
	 *
	 * @return {@link SearchPatternToBuild}
	 */
	//@Cacheable(value = "docType", key = "#docType", unless = "#result == null")
	@Override
	public SearchPatternToBuild findByChineseName(String docType) {
		Objects.requireNonNull(docType);
		LambdaQueryWrapper<TConfig> wrapper = new LambdaQueryWrapper<>();
		wrapper
				.eq(TConfig::getChineseName, docType.trim())
				.last("LIMIT 1")
		;
		TConfig config = this.getOne(wrapper, false);
		
		
		return SearchPatternToBuild
				       .builder()
				       .chineseName(config.getChineseName())
				       .fields(config.getConfig())
				       .indexName(config.getIndexName())
				       .indexes(config.getIndexesList())
				       .build();
		
	}
	
	@CacheEvict(allEntries = true, beforeInvocation = true)
	@Override
	public Boolean save(SearchPatternToBuild searchPatternToBuild) {
		
		TConfig config = TConfig
				                 .builder()
				                 .indexName(searchPatternToBuild.getIndexName())
				                 .config(searchPatternToBuild.getFields())
				                 .chineseName(searchPatternToBuild.getChineseName())
				                 .indexesList(searchPatternToBuild.getIndexes())
				                 .build();
		return save(config);
		
	}
	
	@CacheEvict(allEntries = true, beforeInvocation = true)
	@Override
	public Boolean deleteById(Long id) {
		return this.removeById(id);
	}
	
	/**
	 * 得到所有搜索配置
	 *
	 * @param pageNo
	 * @param pageSize
	 *
	 * @return {@link List < ConfigVo >}
	 */
	@Cacheable(value = "configs", key = "#root.methodName", unless = "#result == null")
	@Override
	public Page<ConfigVo> getAllSearchConfig(Integer pageNo, Integer pageSize) {
		
		Page<TConfig> page = this.page(new Page<TConfig>(pageNo, pageSize));
		//List<ConfigVo> lists = page.getRecords()
		//                           .stream()
		//                           .map(config -> Tuple.of(config, searchStatusService))
		//                           .map(ConvertJavaUtils::formatConfigVo)
		//                           .collect(Collectors.toList());
		//Page<ConfigVo> newList = new Page<>();
		//newList.setRecords(lists);
		//newList.setCountId(page.getCountId());
		//newList.setCurrent(page.getCurrent());
		//newList.setOrders(page.getOrders());
		//newList.setMaxLimit(page.getMaxLimit());
		//newList.setPages(page.getPages());
		//newList.setSize(page.getSize());
		//newList.setTotal(page.getTotal());
		
		//return newList;
		return null;
	}
	
	@Override
	public List<ConfigVo> getAllSearchConfig() {
		//return this.list()
		//           .stream()
		//           .map(config -> Tuple.of(config, searchStatusService))
		//           .map(ConvertJavaUtils::formatConfigVo1)
		//           .collect(Collectors.toList());
		
		return null;
	}
	
	/**
	 * 更新通过id
	 *
	 * @param id
	 * 		id
	 * @param searchPatternToBuild
	 * 		搜索模式构建
	 *
	 * @return {@link Boolean}
	 */
	@CacheEvict(allEntries = true, beforeInvocation = true)
	@Override
	public Boolean updateById(Long id, SearchPatternToBuild searchPatternToBuild) {
		
		return this.baseMapper
				       .updateById(id, LocalDateTime.now(), searchPatternToBuild);
		
	}
	
	/**
	 * 得到所有搜索中文名字
	 *
	 * @return {@link List<String>}
	 */
	@Cacheable(value = "chineseNames", key = "#root.methodName", unless = "#result == null")
	@Override
	public List<String> getAllSearchChineseName() {
		return this.list()
		           .stream()
		           .map(TConfig::getChineseName)
		           .collect(Collectors.toList());
		
		
	}
	
	/**
	 * 根据中文名称获取所有搜索字段的集合，如果未填入则传入全部中文名称
	 *
	 * @param chineseName
	 * 		中文名字
	 *
	 * @return {@link List<String>}
	 */
	@Cacheable(value = "chineseNames", key = "#chineseName", unless = "#result == null")
	@Override
	public List<String> getAllSearchChineseColumns(String chineseName) {
		LambdaQueryWrapper<TConfig> wrapper = new LambdaQueryWrapper<>();
		if (StringUtils.isNotBlank(chineseName)) {
			wrapper.eq(TConfig::getChineseName, StringUtils.trim(chineseName));
		}
		
		return this.list()
		           .stream()
		           .map(TConfig::getConfig)
		           .flatMap(Collection::stream)
		           .map(GeneralBuild::getName)
		           .collect(Collectors.toList());
	}
	
	@Override
	public ConfigVo findById(Integer id) {
		TConfig config = this.getById(id);
		//return ConvertJavaUtils.formatConfigVo(Tuple.of(config, searchStatusService));
		return null;
	}
}

