package com.how2java.tmall.service;

import com.how2java.tmall.dao.CategoryDAO;
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: CategoryService
 * @Author: AllenSun
 * @Date: 2020/1/28 21:34
 */
/**
 * Redis的缓存是放在Service这一层上做的
 * 1-
 *
 */
@Service
//CacheConfig注解就表示分类在缓存里的keys，都是归 "categories" 这个管理的
//看到有一个 categories~keys, 就是它，用于维护分类信息在 redis里都有哪些 key.
@CacheConfig(cacheNames="categories")
public class CategoryService {
    @Autowired
    CategoryDAO categoryDAO;

    /** 检索全部数据
     * 首先创建一个 Sort 对象，表示通过 id 倒排序， 然后通过 categoryDAO进行查询
     * categoryDAO没有实现类，直接使用 CategoryService 作为实现类
     * @MethodName: list
     * @Author: AllenSun
     * @Date: 2020/2/4 12:53
     */
    @Cacheable(key="'categories-all'")
    public List<Category> list() {
        //这里springboot的版本必须是用1.8，如果是2.x版本就会报错
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        return categoryDAO.findAll(sort);
    }

    /** 实现分页的查找
     * @MethodName: list
     * @Author: AllenSun
     * @Date: 2020/2/4 16:30
     */
    //这个和获取一个，其实没什么区别，就是key不一样，数据不再是一个对象，而是一个集合。 （保存在 redis 里是一个 json 数组）
    // categories-page-0-5 就是第一页数据
    @Cacheable(key="'categories-page-'+#p0+ '-' + #p1")
    public Page4Navigator<Category> list(int start, int size, int navigatePages) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size,sort);
        Page pageFromJPA =categoryDAO.findAll(pageable);

        return new Page4Navigator<>(pageFromJPA,navigatePages);
    }

    /** 添加一个Category对象
     * @MethodName: add
     * @Author: AllenSun
     * @Date: 2020/2/4 16:31
     */
    //CacheEvict(allEntries=true)意义是删除 categories~keys 里的所有的keys
    @CacheEvict(allEntries=true)
    public void add(Category bean){
        categoryDAO.save(bean);
    }

    /** 根据id删除一个Category对象
     * @MethodName: delete
     * @Author: AllenSun
     * @Date: 2020/2/4 16:33
     */
    @CacheEvict(allEntries=true)
    public void delete(int id){
        categoryDAO.delete(id);
    }

    /** 根据id查询一个Category对象
     * @MethodName: get
     * @Author: AllenSun
     * @Date: 2020/2/4 16:34
     */
    //第一次访问的时候，redis 是不会有数据的，所以就会通过 jpa 到数据库里去取出来，一旦取出来之后，就会放在 redis里。 key 呢就是如图所示的 categories-one-84 这个 key。
    //第二次访问的时候，redis 就有数据了，就不会从数据库里获取了。
    @Cacheable(key="'categories-one-'+ #p0")
    public Category get(int id){
        return categoryDAO.findOne(id);
    }

    /** 保存一个Category对象
     * @MethodName: update
     * @Author: AllenSun
     * @Date: 2020/2/4 16:35
     */
    @CacheEvict(allEntries=true)
    public void update(Category bean){
        categoryDAO.save(bean);
    }


    //这个方法的用处是删除Product对象上的 分类。 为什么要删除呢？ 因为在对分类做序列还转换为 json 的时候，会遍历里面的 products, 然后遍历出来的产品上，又会有分类，接着就开始子子孙孙无穷溃矣地遍历了，就搞死个人了
    //而在这里去掉，就没事了。 只要在前端业务上，没有通过产品获取分类的业务，去掉也没有关系
    //前端新加
    public void removeCategoryFromProduct(List<Category> cs) {
        for (Category category : cs) {
            removeCategoryFromProduct(category);
        }
    }

    public void removeCategoryFromProduct(Category category) {
        List<Product> products =category.getProducts();
        if(null!=products) {
            for (Product product : products) {
                product.setCategory(null);
            }
        }

        List<List<Product>> productsByRow =category.getProductsByRow();
        if(null!=productsByRow) {
            for (List<Product> ps : productsByRow) {
                for (Product p: ps) {
                    p.setCategory(null);
                }
            }
        }
    }
}
