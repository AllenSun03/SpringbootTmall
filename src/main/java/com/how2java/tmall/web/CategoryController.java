package com.how2java.tmall.web;

import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.util.ImageUtil;
import com.how2java.tmall.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/** 专门用来提供restful服务器控制器的
 * @ClassName: CategoryController
 * @Author: AllenSun
 * @Date: 2020/2/4 13:03
 */
//Controller注解是传统的控制器
//RestController是适应restful的控制器，只返回对象，对象数据作为json/xml直接写入http响应
//@RestController是@Controller和@ResponseBody的结合体，两个标注合并起来的作用。
//对每个方法的返回值都会直接转换为 json 数据格式
@RestController
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    //使用RestController返回的不是画面，而是数据
    /*@GetMapping("/categories")
    public List<Category> list() throws Exception{
        return categoryService.list();
    }*/
    @GetMapping("/categories")
    public Page4Navigator<Category> list(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
        start = start<0?0:start;
        Page4Navigator<Category> page =categoryService.list(start, size, 5);  //5表示导航分页最多有5个，像 [1,2,3,4,5] 这样
        return page;
    }

    //add“添加数据”
    @PostMapping("/categories")
    public Object add(Category bean, MultipartFile image, HttpServletRequest request) throws Exception {
        categoryService.add(bean);
        saveOrUpdateImageFile(bean, image, request);
        return bean;
    }

    //saveOrUpdateImageFile“保存或者更新图片”
    public void saveOrUpdateImageFile(Category bean, MultipartFile image, HttpServletRequest request)
            throws IOException {
        File imageFolder= new File(request.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder,bean.getId()+".jpg");
        if(!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        image.transferTo(file);
        BufferedImage img = ImageUtil.change2jpg(file);
        ImageIO.write(img, "jpg", file);
    }

    //delete“根据id删除数据”
    @DeleteMapping("/categories/{id}")
    public String delete(@PathVariable("id") int id, HttpServletRequest request)  throws Exception {
        categoryService.delete(id);
        File  imageFolder= new File(request.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder,id+".jpg");
        file.delete();
        return null;
    }

    //get“根据id检索单个数据”
    @GetMapping("/categories/{id}")
    public Category get(@PathVariable("id") int id) throws Exception {
        Category bean=categoryService.get(id);
        return bean;
    }

    //update“根据id更新数据”
    @PutMapping("/categories/{id}")
    public Object update(Category bean, MultipartFile image, HttpServletRequest request) throws Exception {
        String name = request.getParameter("name");
        bean.setName(name);
        categoryService.update(bean);

        if(image!=null) {
            saveOrUpdateImageFile(bean, image, request);
        }
        return bean;
    }


}
