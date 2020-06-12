package com.book.store.service.impl;

import com.book.store.common.ResponseCode;
import com.book.store.common.ServerResponse;
import com.book.store.entity.Category;
import com.book.store.entity.Product;
import com.book.store.mapper.CategoryMapper;
import com.book.store.mapper.ProductMapper;
import com.book.store.service.ProductService;
import com.book.store.vo.ProductDetailVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ServerResponse deleteProductById(Integer id) {
        if (id < 0) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        try {
            int rowCount = productMapper.deleteByPrimaryKey(id);
            return rowCount > 0 ? ServerResponse.createBySuccessCodeMessage(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getDesc())
                    : ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc());
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc());
        }
    }

    @Override
    public ServerResponse saveOrUpdateProduct(Product product) {

        if (product == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        if (productMapper.checkProductCount(product.getId()) > 0) {
            try {
                int rowCount = productMapper.updateByPrimaryKey(product);
                return rowCount > 0 ? ServerResponse.createBySuccessCodeMessage(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getDesc())
                        : ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc());
            } catch (Exception e) {
                return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc());
            }
        }

        String imgs = product.getImages().replace("/", "");
        String detial = product.getDetail().replace("/", "");

        String[] mainImage = imgs.split(",");
        product.setImages(imgs);
        product.setDetail(detial);
        product.setMainImage(mainImage[0]);

        try {
            int rowCount = productMapper.insert(product);
            return rowCount > 0 ? ServerResponse.createBySuccessCodeMessage(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getDesc())
                    : ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc());
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc());
        }
    }

    @Override
    public ServerResponse<String> setSaleStatus(Integer productId, Integer status) {
        return null;
    }

    @Override
    public ServerResponse setCommentById(Product product) {
        return null;
    }

    @Override
    public ServerResponse<ProductDetailVo> manageProductDetail(Integer productId) {
        return null;
    }

    @Override
    public ServerResponse<PageInfo> getProductList(Integer pageNum, Integer pageSize) {
        Page page = PageHelper.startPage(pageNum, pageSize);
        List<Product> list = productMapper.selectList();
        List<ProductDetailVo> productDetailVoList = Lists.newArrayList();
        for (Product product : list) {
            ProductDetailVo productDetailVo = this.getProductVo(product);
            productDetailVoList.add(productDetailVo);
        }
        PageInfo pageInfo = new PageInfo(page);
        pageInfo.setList(productDetailVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse<PageInfo> getPeriodical(Integer pageNum, Integer pageSise) {
        Page page = PageHelper.startPage(pageNum, pageSise);
        List<Product> list = productMapper.selectByCategory(42);
        List<ProductDetailVo> productDetailVoList = Lists.newArrayList();
        for (Product product : list) {
            ProductDetailVo productDetailVo = this.getProductVo(product);
            productDetailVoList.add(productDetailVo);
        }
        PageInfo pageInfo = new PageInfo(page);
        pageInfo.setList(productDetailVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse<PageInfo> getMaterialList(Integer page, Integer pageSize) {
        Page pg = PageHelper.startPage(page, pageSize);
        List<Product> list = productMapper.selectByCategory(38);
        List<ProductDetailVo> productDetailVoList = Lists.newArrayList();
        for (Product product : list) {
            ProductDetailVo productDetailVo = this.getProductVo(product);
            productDetailVoList.add(productDetailVo);
        }
        PageInfo pageInfo = new PageInfo(pg);
        pageInfo.setList(productDetailVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse<PageInfo> getChildrenList(Integer pageNum, Integer pageSize) {
        Page page = PageHelper.startPage(pageNum, pageSize);
        List<Product> list = productMapper.selectByCategory(44);
        List<ProductDetailVo> productDetailVoList = Lists.newArrayList();
        for (Product product : list) {
            ProductDetailVo productDetailVo = this.getProductVo(product);
            productDetailVoList.add(productDetailVo);
        }
        PageInfo pageInfo = new PageInfo(page);
        pageInfo.setList(productDetailVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse<PageInfo> getProductListByMerchantId(Integer pageNum, Integer pageSize,String merchantId) {
        Page page = PageHelper.startPage(pageNum, pageSize);
        List<Product> list = productMapper.selectListByMerchantId(merchantId);
        List<ProductDetailVo> productDetailVoList = Lists.newArrayList();
        for (Product product : list) {
            ProductDetailVo productDetailVo = this.getProductVo(product);
            productDetailVoList.add(productDetailVo);
        }
        PageInfo pageInfo = new PageInfo(page);
        pageInfo.setList(productDetailVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse getProductListByMerchantIdAndSearch(Integer page, Integer limit, String merchantId, String search) {
        Page pg = PageHelper.startPage(page, limit);
        List<Product> list = productMapper.selectListByMerchantIdAndSearch(merchantId,search);
        List<ProductDetailVo> productDetailVoList = Lists.newArrayList();

        for (Product product : list) {
            ProductDetailVo productDetailVo = this.getProductVo(product);
            productDetailVoList.add(productDetailVo);
        }
        PageInfo pageInfo = new PageInfo(pg);
        pageInfo.setList(productDetailVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse<PageInfo> selectListOrderByRecommend(Integer pageNum, Integer pageSize) {
        return null;
    }

    @Override
    public ServerResponse<PageInfo> searchProduct(String productName, Integer productId, int pageNum, int pageSize) {
        return null;
    }

    @Override
    public ServerResponse<ProductDetailVo> getProductDetail(Integer productId) {
        return null;
    }

    @Override
    public ServerResponse<PageInfo> getProductByKeywordCategory(String keyword, Integer categoryId, int pageNum, int pageSize, String orderBy) {
        return null;
    }

    @Override
    public ServerResponse<PageInfo> getProduct(Integer pageNum, Integer pageSize) {
        return null;
    }

    @Override
    public ServerResponse getProductByCategoryId(int pageNum, int pageSize, Integer categoryId) {
        return null;
    }

    @Override
    public ServerResponse getProductBag(int pageNum, int pageSize) {
        return null;
    }

    @Override
    public ServerResponse<PageInfo> getProductByRecommend(Integer pageNum, Integer pageSize) {
        return null;
    }

    @Override
    public ServerResponse<ProductDetailVo> getProductById(Integer productId) {
        if (productId < 0){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),"参数错误");
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        ProductDetailVo productDetailVo = this.getProductVo(product);
        return ServerResponse.createBySuccess(productDetailVo);
    }



    private ProductDetailVo getProductVo(Product product) {
        ProductDetailVo productDetailVo = new ProductDetailVo();

        productDetailVo.setId(product.getId());
        productDetailVo.setAuthorName(product.getAuthorName());
        productDetailVo.setComment(product.getComment());
        productDetailVo.setMerchantId(product.getMerchantId());
        productDetailVo.setCommentStart(product.getCommentStart());

        String[] img = product.getImages().split(",");
        for (int i = 0; i < img.length; i++) {
            img[i] = "/manage/file/file_download?path=/"+img[i];
        }
        String[] detail = product.getDetail().split(",");
        for (int i = 0; i < detail.length; i++) {
            detail[i] = "/manage/file/file_download?path=/"+detail[i];
        }
        productDetailVo.setDetail(detail);
        productDetailVo.setImages(img);
        productDetailVo.setName(product.getName());
        productDetailVo.setNumber(product.getNumber());
        productDetailVo.setMainImage("/manage/file/file_download?path=/"+product.getMainImage());
        /*productDetailVo.setSuMainImage("/manage/file/file_download?path=/"+product.getImages().split(",")[1]);*/
        productDetailVo.setSuMainImage("/manage/file/file_download?path=/"+product.getImages().split(",")[1]);
        productDetailVo.setPrice(product.getPrice());
        productDetailVo.setPublish(product.getPublish());
        productDetailVo.setPublishTime(product.getPublishTime());
        productDetailVo.setSaleNumber(product.getSaleNumber());
        productDetailVo.setStock(product.getStock());
        productDetailVo.setSubtitle(product.getSubtitle());
        if (product.getStatus() == 0) {
            productDetailVo.setStatus("下架");
        } else {
            productDetailVo.setStatus("在售");
        }
        Category categoryParent = categoryMapper.selectByPrimaryKey(product.getCategoryId());//父分类
        Category categoryChild = categoryMapper.selectByPrimaryKey(product.getCategoryChildId());
        productDetailVo.setCategoryName(categoryParent.getName() + "/" + categoryChild.getName());

        productDetailVo.setCreateTime(product.getCreateTime());
        productDetailVo.setUpdateTime(product.getUpdateTime());
        return productDetailVo;
    }

    @Override
    public ServerResponse<PageInfo> getProductByMerchantId(String merchantId, Integer pageNum, Integer pageSize) {
        if (StringUtils.isEmpty(merchantId)){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getDesc());
        }
        Page page = PageHelper.startPage(pageNum,pageSize);
        List<Product> productList = productMapper.selectProductByMerchantId(merchantId);
        List<ProductDetailVo> productDetailVoList = Lists.newArrayList();
        for (Product product : productList) {
            ProductDetailVo productDetailVo = getProductVo(product);
            productDetailVoList.add(productDetailVo);
        }
        PageInfo pageInfo = new PageInfo(page);
        pageInfo.setList(productDetailVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse<PageInfo> getSearchProductList(int pageNum, int pageSize, String search) {
        Page page = PageHelper.startPage(pageNum, pageSize);
        List<Product> lists = productMapper.selectListBySearch(search);
        List<ProductDetailVo> productDetailVoList = Lists.newArrayList();
        for (Product product : lists) {
            ProductDetailVo productDetailVo = this.getProductVo(product);
            productDetailVoList.add(productDetailVo);
        }
        PageInfo pageInfo = new PageInfo(page);
        pageInfo.setList(productDetailVoList);
        return ServerResponse.createBySuccess(pageInfo);

    }
}
