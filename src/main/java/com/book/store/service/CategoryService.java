package com.book.store.service;


import com.book.store.common.ServerResponse;
import com.book.store.entity.Category;
import com.book.store.vo.CategoryVo;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface CategoryService {
    ServerResponse addCategory(String categoryName, Integer parentId);
    ServerResponse deleteCategory(Integer id);
    ServerResponse updateCategoryName(Integer categoryId, String categoryName);
    ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId);
    ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId);
    ServerResponse<List<Map<String, Object>>> selectAllProductCategory();
    ServerResponse <List<CategoryVo>> selectParentById(Integer parentId);
    ServerResponse<List<CategoryVo>> selectChildrenByParentId(Integer parentId);
    ServerResponse<List<CategoryVo>> selectAllCategoryList();
    ServerResponse<List<CategoryVo>> selectParentCategoryList();

    ServerResponse selectNewAllCategoryList(int pageNum, int pageSize);

    ServerResponse updateCategory(Integer parentId, String newCategory);
}
