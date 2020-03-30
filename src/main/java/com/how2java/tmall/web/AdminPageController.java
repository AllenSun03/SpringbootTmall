package com.how2java.tmall.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/** 后台管理页面跳转专用控制器
 * @ClassName: AdminPageController
 * @Author: AllenSun
 * @Date: 2020/2/4 12:58
 */
@Controller
public class AdminPageController {
    //管理员路径，首先跳转到列表
    @GetMapping(value = "/admin")
    public String admin(){
        return "redirect:admin_category_list";
    }

    //当跳转到分类列表时，返回到列表画面
    @GetMapping(value = "/admin_category_list")
    public String listCategory(){
        return "admin/listCategory";
    }

    //当跳转到分类编辑时，返回到编辑画面
    @GetMapping(value = "/admin_category_edit")
    public String editCategory(){
        return "admin/editCategory";
    }

    //跳转到属性的列表画面
    @GetMapping(value="/admin_property_list")
    public String listProperty(){
        return "admin/listProperty";
    }

    //跳转到属性的编辑画面
    @GetMapping(value="/admin_property_edit")
    public String editProperty(){
        return "admin/editProperty";
    }

    //属性的值编辑画面
    @GetMapping(value="/admin_propertyValue_edit")
    public String editPropertyValue(){
        return "admin/editPropertyValue";
    }

    //跳转到产品管理的列表画面
    @GetMapping(value="/admin_product_list")
    public String listProduct(){
        return "admin/listProduct";
    }

    //跳转到产品管理的编辑画面
    @GetMapping(value="/admin_product_edit")
    public String editProduct(){
        return "admin/editProduct";
    }

    //产品图片管理的列表画面
    @GetMapping(value="/admin_productImage_list")
    public String listProductImage(){
        return "admin/listProductImage";
    }

    //订单管理画面
    @GetMapping(value="/admin_order_list")
    public String listOrder(){
        return "admin/listOrder";
    }

    //用户管理画面
    @GetMapping(value="/admin_user_list")
    public String listUser(){
        return "admin/listUser";
    }


}
