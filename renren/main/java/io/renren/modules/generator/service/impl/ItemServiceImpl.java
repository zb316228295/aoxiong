package io.renren.modules.generator.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.generator.dao.ItemDao;
import io.renren.modules.generator.entity.ItemEntity;
import io.renren.modules.generator.service.ItemService;


@Service("itemService")
public class ItemServiceImpl extends ServiceImpl<ItemDao, ItemEntity> implements ItemService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ItemEntity> page = this.page(
                new Query<ItemEntity>().getPage(params),
                new QueryWrapper<ItemEntity>()
        );

        return new PageUtils(page);
    }

}