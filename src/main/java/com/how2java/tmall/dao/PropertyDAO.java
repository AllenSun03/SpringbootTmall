package com.how2java.tmall.dao;

import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/** 把Property实体类放进JpaRepository里去，里面自动生成了对应CRUD方法
 * @MethodName:
 * @Author: AllenSun
 * @Date: 2020/2/4 21:39
 */
public interface PropertyDAO extends JpaRepository<Property,Integer> {
    /** 在自动生成的CRUD方法以外。还要自己添加一个方法
     * 这个方法是根据种类来检索
     * @MethodName: findByCategory
     * @Author: AllenSun
     * @Date: 2020/2/4 21:39
     */
    Page<Property> findByCategory(Category category, Pageable pageable);
    List<Property> findByCategory(Category category);
}
