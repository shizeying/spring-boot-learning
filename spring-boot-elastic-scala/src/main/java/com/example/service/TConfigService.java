package com.example.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.domain.TConfig;
import com.example.domain.bo.SearchPatternToBuild;
import com.example.domain.vo.ConfigVo;

import java.util.List;

public interface TConfigService extends IService<TConfig> {
	
	/**
	 * 通过docType获取搜索配置
	 *
	 * @param docType
	 * 		文档类型
	 *
	 * @return {@link SearchPatternToBuild}
	 */
	SearchPatternToBuild findByChineseName(String docType);
	
	
	Boolean save(SearchPatternToBuild searchPatternToBuild);
	
	/**
	 * 通过id删除
	 *
	 * @param id
	 * 		id
	 *
	 * @return {@link Boolean}
	 */
	Boolean deleteById(Long id);
	
	
	/**
	 * 得到所有搜索配置
	 *
	 * @return {@link List<ConfigVo>}
	 * @param pageNo
	 * @param pageSize
	 */
	Page<ConfigVo> getAllSearchConfig(Integer pageNo, Integer pageSize);
	List<ConfigVo> getAllSearchConfig();
	
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
	Boolean updateById(Long id,SearchPatternToBuild searchPatternToBuild);
	/**
	 * 得到所有搜索中文名字
	 *
	 * @return {@link List<String>}
	 */
	List<String> getAllSearchChineseName();
	
	/**
	 * 根据中文名称获取所有搜索字段的集合，如果未填入则传入全部中文名称
	 *
	 * @param chineseName
	 * 		中文名字
	 *
	 * @return {@link List<String>}
	 */
	List<String> getAllSearchChineseColumns(String chineseName);
	
	
	ConfigVo findById(Integer id);
}

