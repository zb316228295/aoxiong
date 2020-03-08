package io.renren.modules.generator.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.generator.entity.ItemEntity;
import io.renren.modules.generator.service.ItemService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-02-20 09:52:11
 */
@RestController
@RequestMapping("generator/item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("generator:item:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = itemService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("generator:item:info")
    public R info(@PathVariable("id") Integer id){
		ItemEntity item = itemService.getById(id);

        return R.ok().put("item", item);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:item:save")
    public R save(@RequestBody ItemEntity item){
		itemService.save(item);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:item:update")
    public R update(@RequestBody ItemEntity item){
		itemService.updateById(item);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:item:delete")
    public R delete(@RequestBody Integer[] ids){
		itemService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
