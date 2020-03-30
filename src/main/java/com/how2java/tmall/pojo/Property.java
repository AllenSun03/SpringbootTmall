package com.how2java.tmall.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/** 属性实体类
 * @ClassName: Property
 * @Author: AllenSun
 * @Date: 2020/2/4 21:23
 */
@Entity//这是一个实体类
@Table(name = "property")//对应的表格
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")//对应的数据表字段
    private int id;
    @Column(name = "name")
    private String name;


    @ManyToOne//一对多的关系
    @JoinColumn(name = "cid")
    private Category category;

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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Property{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category=" + category +
                '}';
    }
}
