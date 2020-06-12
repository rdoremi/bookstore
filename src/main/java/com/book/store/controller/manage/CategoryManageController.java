package com.book.store.controller.manage;


import com.book.store.common.Const;
import com.book.store.common.ResponseCode;
import com.book.store.common.ServerResponse;
import com.book.store.entity.Merchant;
import com.book.store.entity.User;
import com.book.store.service.impl.CategoryServiceImpl;
import com.book.store.service.impl.MerchantServiceImpl;
import com.book.store.service.impl.UserServiceImpl;
import com.book.store.vo.CategoryVo;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/manage/categroy")
public class CategoryManageController {
    @Autowired
    private MerchantServiceImpl userService;
    @Autowired
    private CategoryServiceImpl categoryService;
    @GetMapping("/add_category")
    public ServerResponse addCategory(HttpSession session, String categoryName, @RequestParam(value = "parentId",defaultValue = "0") Integer parentId){
        Merchant user = (Merchant) session.getAttribute(Const.CURRENT_USER);
        if (userService.checkAdminRole(user).isSuccess()){
            //是管理员
            return categoryService.addCategory(categoryName,parentId);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理权限");
        }
    }
    @PostMapping("/add_parent")
    public ServerResponse addParrent(String name){

        return categoryService.addCategory(name,0);
    }
    @PostMapping("/add_child")
    public ServerResponse addChild(Integer parentId,String childrenName){

        return categoryService.addCategory(childrenName,parentId);
    }
    @GetMapping("set_category_name")
    public ServerResponse setCategoryName(HttpSession session,Integer categoryId,String categoryName){
        Merchant user = (Merchant) session.getAttribute(Const.CURRENT_USER);
        if (userService.checkRole(user).isSuccess()){
            //是管理员
            return categoryService.updateCategoryName(categoryId,categoryName);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理权限");
        }
    }
    @PostMapping("/update_category")
    public ServerResponse updateParentName(Integer parentId,String newCategory){
        return categoryService.updateCategoryName(parentId,newCategory);
    }
    @GetMapping("/get_category")
    public ServerResponse getChildrenParalleCategory(HttpSession session,@RequestParam(value = "categoryId",defaultValue = "0") Integer categoryId){
        Merchant user = (Merchant) session.getAttribute(Const.CURRENT_USER);

        if (userService.checkRole(user).isSuccess()){
            //是管理员
            //查询字节点的category信息, 并不递归，保存平级
            return categoryService.getChildrenParallelCategory(categoryId);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理权限");
        }
    }
    @GetMapping("/get_parent_and_children_category")
    public ServerResponse getParentAndChildern(@RequestParam(value = "parentId",defaultValue = "0") Integer parentId){

        return categoryService.selectParentById(parentId);
    }
    @GetMapping("/delete_category")
    public ServerResponse deleteCategory(HttpSession session,Integer id){
        Merchant user = (Merchant) session.getAttribute(Const.CURRENT_USER);
        if (userService.checkRole(user).isSuccess()){

            return categoryService.deleteCategory(id);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理权限");
        }
    }
    @GetMapping("/get_deep_category")
    public ServerResponse getCategoryAndDeepChildrenCategory(HttpSession session,@RequestParam(value = "categoryId",defaultValue = "0") Integer categoryId){
        Merchant user = (Merchant) session.getAttribute(Const.CURRENT_USER);

        if (userService.checkRole(user).isSuccess()){
            //是管理员
            //查询当前节点的id和递归字节点的id
            return categoryService.selectCategoryAndChildrenById(categoryId);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理权限");
        }
    }
    @GetMapping("/get_all_product_category")
    public ServerResponse getAllproductCategory(HttpSession session){
        Merchant user = (Merchant) session.getAttribute(Const.CURRENT_USER);
        if (userService.checkRole(user).isSuccess()){
            //是管理员

            return categoryService.selectAllProductCategory();
        }else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理权限");
        }

    }
    @GetMapping("/get_all_category_list")
    public ServerResponse getAllCategoryList(HttpSession session){
        Merchant user = (Merchant) session.getAttribute(Const.CURRENT_USER);
        if (userService.checkRole(user).isSuccess()){
            //是管理员

            return categoryService.selectAllCategoryList();
        }else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理权限");
        }
    }
    @GetMapping("/get_new_category")
    public ServerResponse getNewCategory(HttpSession session, @RequestParam(value = "page",defaultValue = "1") int page, @RequestParam(value = "limit",defaultValue = "20") int limit){
        Merchant user = (Merchant) session.getAttribute(Const.CURRENT_USER);
        if (userService.checkRole(user).isSuccess()){
            //是管理员
            return categoryService.selectNewAllCategoryList(page,limit);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理权限");
        }
    }


}
