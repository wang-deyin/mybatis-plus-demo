package org.example.test.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.example.test.entity.Dept;
import org.example.test.mapper.DeptMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wdyin
 * @date 2025/1/26
 **/
@RestController
public class TestController {
    @Resource
    private DeptMapper deptMapper;

    @GetMapping("/test")
    public List<Dept> test() {
        List<Dept> depts = deptMapper.selectList(Wrappers.query());
        System.out.println(depts);
        return depts;
    }

    @GetMapping("/test/batch")
    public void batch() {
        List<Dept> depts = new ArrayList<>();
        depts.add(new Dept());
        // 无法插入MyMetaObjectHandler设置的创建人和创建时间
        deptMapper.insertBatchSomeColumn(depts);
    }
}
