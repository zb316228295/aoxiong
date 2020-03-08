package io.renren.modules.generator.dao;

import io.renren.modules.generator.entity.ItemEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-02-20 09:52:11
 */
@Mapper
public interface ItemDao extends BaseMapper<ItemEntity> {
	
}
