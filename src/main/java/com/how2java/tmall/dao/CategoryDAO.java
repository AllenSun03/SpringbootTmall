package com.how2java.tmall.dao;

import com.how2java.tmall.pojo.Category;
import org.springframework.data.jpa.repository.JpaRepository;

//本来Dao接口里面要写方法，但是这里直接继承JpaRepository类
//并且放进去了实体类Category
//这样就不用自己去写增删改查的方法了，直接用现成的
public interface CategoryDAO extends JpaRepository<Category,Integer> {
}
