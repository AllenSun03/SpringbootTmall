package com.how2java.tmall.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

/** 种类管理的实体类
 * @ClassName: Category
 * @Author: AllenSun
 * @Date: 2020/1/28 21:21
 */
@Entity//声明这是一个实体类
@Table(name = "category")//声明这个实体类对应的数据库的表
//前后端分离，数据交互用的是json格式，实体类对象会被转换成json数据
//jpa默认使用的是hibernate，会添加两个用不到的属性，要把这两个属性给忽略掉
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;
    String name;

    //前端新加的两个
    @Transient
    List<Product> products;
    @Transient
    List<List<Product>> productsByRow;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //前端新加
    public List<Product> getProducts() {
        return products;
    }
    public void setProducts(List<Product> products) {
        this.products = products;
    }
    public List<List<Product>> getProductsByRow() {
        return productsByRow;
    }
    public void setProductsByRow(List<List<Product>> productsByRow) {
        this.productsByRow = productsByRow;
    }
}
