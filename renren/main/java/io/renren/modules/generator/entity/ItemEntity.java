package io.renren.modules.generator.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-02-20 09:52:11
 */
@Data
@TableName("item")
public class ItemEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 
	 */
	private Date creatTime;
	/**
	 * 
	 */
	private Date changeTime;
	/**
	 * 
	 */
	private Integer status;
	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private String brief;
	/**
	 * 
	 */
	private String describe;
	/**
	 * 
	 */
	private String pics;

}
