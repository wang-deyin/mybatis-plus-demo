package org.example.test.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 自动填充处理器
 *
 * @author haicloud
 */
@Component
public class HaiCloudMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        //执行了以下代码，但是数据并没有插入进去
        if (metaObject.hasSetter("createBy")) {
            this.strictInsertFill(metaObject, "createBy", ()-> 1L, Long.class);
            this.strictInsertFill(metaObject, "createTime", Date.class, new Date());
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {

    }
}
