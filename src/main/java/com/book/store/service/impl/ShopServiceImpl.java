package com.book.store.service.impl;

import com.book.store.common.Const;
import com.book.store.common.ResponseCode;
import com.book.store.common.ServerResponse;
import com.book.store.entity.Merchant;
import com.book.store.entity.MyAccount;
import com.book.store.entity.Shop;
import com.book.store.mapper.MerchantMapper;
import com.book.store.mapper.MyAccountMapper;
import com.book.store.mapper.ShopMapper;
import com.book.store.service.ShopService;
import com.book.store.utils.Md5;
import com.book.store.vo.ShopVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    private ShopMapper shopMapper;
    @Autowired
    private MyAccountMapper myAccountMapper;

    @Autowired
    private MerchantMapper merchantMapper;
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public ServerResponse addShop(ShopVo shopVo) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if (shopVo == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        int rowCheck = shopMapper.checkByTel(shopVo.getTel());
        if (rowCheck > 0){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),"号码已存在");
        }

            Date date = new Date();
            String strDateFormat = "yyyyMMddHHmmssSSS";
            SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
            shopVo.setMerchantId(sdf.format(date));
            String img = shopVo.getImage();
            shopVo.setImage("/manage/file/file_download?path=/"+img);


            Shop shop = new Shop();
            shop.setImage(shopVo.getImage());
            shop.setMerchantId(shopVo.getMerchantId());
            shop.setAccount(shopVo.getAccount());
            shop.setShopManager(shopVo.getShopManager());
            shop.setAddress(shopVo.getAddress());
            shop.setEmail(shopVo.getEmail());
            shop.setShopName(shopVo.getShopName());
            shop.setIdCart(shopVo.getIdCart());
            shop.setTel(shopVo.getTel());
            shop.setSubTitle(shopVo.getSubTitle());
            shop.setDetail(shopVo.getDetail());

            Merchant merchant = new Merchant();
            merchant.setPassword(Md5.EncoderByMd5(shopVo.getPassword()));
            merchant.setUsername(shopVo.getShopManager());
            merchant.setTel(shopVo.getTel());
            merchant.setEmail(shopVo.getMerchantId());
            merchant.setShopName(shopVo.getShopName());
            merchant.setMerchantId(shopVo.getMerchantId());
            merchant.setRole(Const.Role.ROLE_MERCHANT);

            int rowCount = shopMapper.insert(shop);
            if (rowCount > 0){
                System.out.println("插入店铺");
            }
            int merchantRowCount = merchantMapper.insert(merchant);

            if (rowCount > 0 && merchantRowCount>0){
                MyAccount myAccount = new MyAccount();
                myAccount.setMerchantId(shopVo.getMerchantId());
                myAccount.setAccount(shopVo.getAccount());
                myAccount.setBalance(new BigDecimal("0"));
                myAccount.setDrawMoney(new BigDecimal("0"));
                myAccount.setApplicant(shopVo.getShopManager());
                int rowMyAccount = myAccountMapper.insert(myAccount);
                if (rowMyAccount <= 0 ){
                    return ServerResponse.createBySuccessCodeMessage(ResponseCode.SUCCESS.getCode(),"账户未添加成功");
                }
                return ServerResponse.createBySuccessCodeMessage(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getDesc());
            }
        /*}catch (Exception e){
            e.printStackTrace();
        }*/
        return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getDesc());
    }

    @Override
    public ServerResponse deleteShopById(Integer id) {
        if (id<0){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        try{
            int rowCount = shopMapper.deleteShopById(id);
            if (rowCount > 0){
                return ServerResponse.createBySuccessCodeMessage(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getDesc());
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getDesc());
    }

    @Override
    public ServerResponse deleteShopByMerchantId(String MerchantId) {
        return null;
    }

    @Override
    public ServerResponse updateShop(Shop shop) {
        return null;
    }

    @Override
    public ServerResponse<Shop> selectShop(String merchantId) {
        Shop shop = shopMapper.selectShopByMerchantId(merchantId);
        return ServerResponse.createBySuccess(shop);
    }

    @Override
    public ServerResponse<PageInfo> selectShopList(int pageNum,int pageSize) {
        Page page = PageHelper.startPage(pageNum,pageSize);
        List<Shop> shops = shopMapper.selectAllList();
        PageInfo pageInfo = new PageInfo(page);
        pageInfo.setList(shops);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse<PageInfo> selectShopListAndName(int page, int limit, String name) {
        Page pg = PageHelper.startPage(page,limit);
        List<Shop> shops = shopMapper.selectAllListByName(name);
        PageInfo pageInfo = new PageInfo(pg);
        pageInfo.setList(shops);
        return ServerResponse.createBySuccess(pageInfo);
    }
}
