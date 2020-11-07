package com.example.mybatis.plus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mybatis.plus.entity.SnapshotGroup;
import com.example.mybatis.plus.entity.SnapshotGroupBO;
import com.example.mybatis.plus.entity.SearchSnapshotQo;
import com.example.mybatis.plus.mapper.GraphConfSnapshotMapper;
import com.example.mybatis.plus.service.SnapshotService;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
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
public class SnapshotServiceImpl extends
		ServiceImpl<GraphConfSnapshotMapper, SnapshotGroup> implements
		SnapshotService {
	
	private static final int FIRST_PAGE_NUM = 1;
	
	private static SnapshotGroup apply(SnapshotGroup entity) {
		return entity.setCreateDate(
				entity.getCreateDate() == null ? LocalDateTime.now() : entity.getUpdateDate())
				.setUpdateDate(LocalDateTime.now());
	}
	
	@Override
	public boolean saveOrUpdateBatch(Collection<SnapshotGroup> entityList, int batchSize) {
		List<SnapshotGroup> entities =
				entityList.stream().map(SnapshotServiceImpl::apply).collect(Collectors.toList());
		return super.saveOrUpdateBatch(entities, batchSize);
	}
	
	
	@Override
	public Boolean deleteSnapshot(List<Long> ids) {
		LambdaQueryWrapper<SnapshotGroup> wrapper = new LambdaQueryWrapper<>();
		wrapper.in(SnapshotGroup::getId, ids);
		Integer count = this.baseMapper.selectCount(wrapper);
		return (count == 0 || count == null) ? true : removeByIds(ids);
	}
	
	@Override
	public Boolean deleteByGraphConfSnapshotIds(List<Long> graphConfSnapshotIds) {
		LambdaQueryWrapper<SnapshotGroup> wrapper = new LambdaQueryWrapper<>();
		wrapper.in(SnapshotGroup::getGraphConfSnapshotId, graphConfSnapshotIds);
		Integer count = this.baseMapper.selectCount(wrapper);
		return (count == 0 || count == null) ? true : this.remove(wrapper);
	}
	
	@Override
	public IPage<SnapshotGroupBO> search(
			SearchSnapshotQo snapshotQo) {
		QueryWrapper<SnapshotGroupBO> queryWrapper = new QueryWrapper<>();
		
		if (StringUtils.isNotBlank(snapshotQo.getFocusEntityName())) {
			queryWrapper
					.like("focus_entity_name", snapshotQo.getFocusEntityName());
		}
	
		if (StringUtils.isNotBlank(snapshotQo.getName())) {
			queryWrapper
					.like("name", snapshotQo.getName());
		}
		if (StringUtils.isNotBlank(snapshotQo.getSpaId())) {
			queryWrapper
					.like("spa_id", snapshotQo.getSpaId());
		}
		if (StringUtils.isNotBlank(snapshotQo.getSubjectName())) {
			queryWrapper
					.like("subject_name", snapshotQo.getSubjectName());
		}
		Page<SnapshotGroupBO> page =
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
