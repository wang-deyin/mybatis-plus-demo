package org.example.test.config;

import java.util.Collection;

/**
 * @author wdyin
 * @date 2025/1/26
 **/
public interface BaseMapper<T> extends com.baomidou.mybatisplus.core.mapper.BaseMapper<T> {
    /**
     * 真正的批量插入
     * @param entityList
     * @return
     */
    int insertBatchSomeColumn(Collection<T> entityList);
}
