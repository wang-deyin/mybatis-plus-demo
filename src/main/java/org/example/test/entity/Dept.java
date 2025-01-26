package org.example.test.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * @author wdyin
 **/
@Data
@TableName(value = "dept")
public class Dept {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String delFlag;

    private String status;

    /**
     * 创建者
     */
    @TableField(value = "create_by", fill = FieldFill.INSERT, updateStrategy = FieldStrategy.NEVER)
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT, updateStrategy = FieldStrategy.NEVER)
    private Date createTime;
}
