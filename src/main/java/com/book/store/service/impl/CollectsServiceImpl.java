package com.book.store.service.impl;

import com.book.store.common.ResponseCode;
import com.book.store.common.ServerResponse;
import com.book.store.entity.Collects;
import com.book.store.entity.Product;
import com.book.store.mapper.CollectsMapper;
import com.book.store.mapper.ProductMapper;
import com.book.store.service.CollectsService;
import com.book.store.vo.CollectVo;
import com.book.store.vo.ProductDetailVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (Collects)表服务实现类
 *
 * @author makejava
 * @since 2020-03-04 17:55:05
 */
@Service
public class CollectsServiceImpl implements CollectsService {
    @Autowired
    private CollectsMapper collectsMapper;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public ServerResponse addCollect(Collects collects) {
        int col = collectsMapper.checkByProductIdAndTel(collects.getProductId(),collects.getTel());
        if (col > 0){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(),"该商品已收藏");
        }
        int rowCount = 0;
        try{
            rowCount = collectsMapper.insert(collects);

        }catch (Exception e){
            e.printStackTrace();
        }
        return rowCount>0?ServerResponse.createBySuccessCodeMessage(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getDesc()):ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(),"收藏失败");
    }

    @Override
    public ServerResponse<PageInfo> selectListByTel(String tel, int pageNum, int pageSize) {
        if (StringUtils.isEmpty(tel)){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Page page = PageHelper.startPage(pageNum,pageSize);
        List<Collects> collectsList = collectsMapper.selectByTel(tel);
        List<CollectVo> collectVoList = Lists.newArrayList();
        for (Collects collects : collectsList) {
            Product product = productMapper.selectById(collects.getProductId());
            if (product!=null){
                CollectVo pvo = new CollectVo();
                pvo.setId(collects.getId());
                pvo.setProductId(product.getId());
                pvo.setPublish(product.getPublish());
                pvo.setName(product.getName());
                pvo.setMainImage("/manage/file/file_download?path=/"+product.getMainImage());
                pvo.setAuthorName(product.getAuthorName());
                pvo.setPrice(product.getPrice());
                collectVoList.add(pvo);

            }
        }
        PageInfo pageInfo = new PageInfo(page);
        pageInfo.setList(collectVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse selectCollectsByTel(String tel) {

        return null;
    }

    @Override
    public ServerResponse delectByTel(String tel) {
        return null;
    }

    @Override
    public ServerResponse deleteById(int id) {
        if (id < 0){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        int rowCount = 0;
        try {
            rowCount = collectsMapper.deleteById(id);

        }catch (Exception e){
            e.printStackTrace();
        }
        return rowCount>0?ServerResponse.createBySuccessCodeMessage(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getDesc()):ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getDesc());
    }
}