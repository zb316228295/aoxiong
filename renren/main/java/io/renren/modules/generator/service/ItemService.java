package io.renren.modules.generator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.generator.entity.ItemEntity;

import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-02-20 09:52:11
 */
public interface ItemService extends IService<ItemEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

