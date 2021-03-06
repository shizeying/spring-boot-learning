package com.example.mybatis.plus.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mybatis.plus.entity.SnapshotGroup;
import com.example.mybatis.plus.entity.SnapshotGroupBO;
import com.example.mybatis.plus.entity.SearchSnapshotQo;
import java.util.List;
import java.util.Map;

/**
 * @author hjh
 */
@SuppressWarnings("ALL")
public interface SnapshotService extends IService<SnapshotGroup> {
	
	/**
	 * 删除快照收藏夹id
	 *
	 * @param ids id
	 * @return {@link Boolean}
	 */
	Boolean deleteSnapshot(List<Long> ids);
	
	/**
	 * 当快照被删除，会触发此操作
	 *
	 * @param graphConfSnapshotIds 图配置快照id
	 * @return {@link Boolean}
	 */
	Boolean deleteByGraphConfSnapshotIds(List<Long> graphConfSnapshotIds);
	
	/**
	 * 分组查询，支持快砸收藏夹和快照功能
	 *
	 * @param snapshotQo 问:快照
	 * @return {@link List< SnapshotGroupBO >}
	 */
	IPage<SnapshotGroupBO> search(SearchSnapshotQo snapshotQo);
	
	/**
	 * 对收藏夹进行分组
	 *
	 * @param snapshotQo 问:快照
	 * @return {@link List<Map<String, Object>>}
	 */
	IPage<Map<String, Object>> groupBySubjectName(SearchSnapshotQo snapshotQo);
}
