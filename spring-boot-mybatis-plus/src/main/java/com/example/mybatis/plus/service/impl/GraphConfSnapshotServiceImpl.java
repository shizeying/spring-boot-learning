package com.example.mybatis.plus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mybatis.plus.entity.GraphConfSnapshotGroup;
import com.example.mybatis.plus.entity.GraphConfSnapshotGroupBO;
import com.example.mybatis.plus.entity.SearchSnapshotQo;
import com.example.mybatis.plus.mapper.GraphConfSnapshotMapper;
import com.example.mybatis.plus.service.GraphConfSnapshotService;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author hjh
 */
@Service
@SuppressWarnings("ALL")
@Transactional
public class GraphConfSnapshotServiceImpl extends
		ServiceImpl<GraphConfSnapshotMapper, GraphConfSnapshotGroup> implements
		GraphConfSnapshotService {
	
	private static final int FIRST_PAGE_NUM = 1;
	
	private static GraphConfSnapshotGroup apply(GraphConfSnapshotGroup entity) {
		return entity.setCreateDate(
				entity.getCreateDate() == null ? LocalDateTime.now() : entity.getUpdateDate())
				.setUpdateDate(LocalDateTime.now());
	}
	
	@Override
	public boolean saveOrUpdateBatch(Collection<GraphConfSnapshotGroup> entityList, int batchSize) {
		List<GraphConfSnapshotGroup> entities =
				entityList.stream().map(GraphConfSnapshotServiceImpl::apply).collect(Collectors.toList());
		return super.saveOrUpdateBatch(entities, batchSize);
	}
	
	
	@Override
	public Boolean deleteSnapshot(List<Long> ids) {
		LambdaQueryWrapper<GraphConfSnapshotGroup> wrapper = new LambdaQueryWrapper<>();
		wrapper.in(GraphConfSnapshotGroup::getId, ids);
		Integer count = this.baseMapper.selectCount(wrapper);
		return (count == 0 || count == null) ? true : removeByIds(ids);
	}
	
	@Override
	public Boolean deleteByGraphConfSnapshotIds(List<Long> graphConfSnapshotIds) {
		LambdaQueryWrapper<GraphConfSnapshotGroup> wrapper = new LambdaQueryWrapper<>();
		wrapper.in(GraphConfSnapshotGroup::getGraphConfSnapshotId, graphConfSnapshotIds);
		Integer count = this.baseMapper.selectCount(wrapper);
		return (count == 0 || count == null) ? true : this.remove(wrapper);
	}
	
	@Override
	public IPage<GraphConfSnapshotGroupBO> search(
			SearchSnapshotQo snapshotQo) {
		QueryWrapper<GraphConfSnapshotGroupBO> queryWrapper = new QueryWrapper<>();
		
		if (StringUtils.isNotBlank(snapshotQo.getFocusEntityName())) {
			queryWrapper
					.like("gcsg.focus_entity_name", snapshotQo.getFocusEntityName());
		}
		if (StringUtils.isNotBlank(snapshotQo.getKgName())) {
			queryWrapper
					.like("gcs.kg_name", snapshotQo.getKgName());
		}
		if (StringUtils.isNotBlank(snapshotQo.getName())) {
			queryWrapper
					.like("gcs.name", snapshotQo.getName());
		}
		if (StringUtils.isNotBlank(snapshotQo.getSpaId())) {
			queryWrapper
					.like("gcsg.spa_id", snapshotQo.getSpaId());
		}
		if (StringUtils.isNotBlank(snapshotQo.getSubjectName())) {
			queryWrapper
					.like("gcsg.subject_name", snapshotQo.getSubjectName());
		}
		Page<GraphConfSnapshotGroupBO> page =
				snapshotQo.getPage() == null ? new Page<>() : snapshotQo.getPage();
		
		return this.baseMapper.selectByExample(page, queryWrapper);
	}
	
	@Override
	public IPage<Map<String, Object>> groupBySubjectName(SearchSnapshotQo snapshotQo) {
		QueryWrapper queryWrapper = new QueryWrapper<>();
		if (StringUtils.isNotBlank(snapshotQo.getSubjectName())) {
			queryWrapper
					.like("gcsg.subject_name", snapshotQo.getSubjectName());
		}
		Page<Map<String, Object>> page =
				snapshotQo.getPage() == null ? new Page<>() : snapshotQo.getPage();
		return this.baseMapper
				.groupBySubjectName(page, queryWrapper);
	}
	
	
}
