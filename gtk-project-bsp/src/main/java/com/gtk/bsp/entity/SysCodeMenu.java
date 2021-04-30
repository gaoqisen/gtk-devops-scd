package com.gtk.bsp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.gtk.bsp.annotation.MaxLength;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@TableName(value = "sys_code_menu")
public class SysCodeMenu implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 系统ID
     */
    @TableField(value = "sys_id")
    @NotBlank(message = "系统ID不能为空")
    @MaxLength(message = "超长", maxLength = 5)
    private String sysId;

    /**
     * 菜单ID
     */
    @TableField(value = "menu_id")
    private String menuId;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    private static final long serialVersionUID = 1L;
}