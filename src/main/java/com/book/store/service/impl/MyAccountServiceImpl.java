package com.book.store.service.impl;

import com.book.store.common.Const;
import com.book.store.common.ResponseCode;
import com.book.store.common.ServerResponse;
import com.book.store.entity.MyAccount;

import com.book.store.entity.Shop;
import com.book.store.mapper.MyAccountMapper;
import com.book.store.mapper.ShopMapper;
import com.book.store.service.MyAccountService;
import com.book.store.vo.MyAccountVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (MyAccount)表服务实现类
 *
 * @author makejava
 * @since 2020-03-08 11:25:58
 */
@Service("myAccountService")
public class MyAccountServiceImpl implements MyAccountService {
    @Resource
    private MyAccountMapper myAccountDao;

    @Autowired
    private ShopMapper shopMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public MyAccount queryById(Integer id) {
        return this.myAccountDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<MyAccount> queryAllByLimit(int offset, int limit) {
        return this.myAccountDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param myAccount 实例对象
     * @return 实例对象
     */
    @Override
    public ServerResponse insert(MyAccount myAccount) {
        int rowCount = 0;
       try{
            rowCount = myAccountDao.insert(myAccount);
       }catch (Exception e){
           e.printStackTrace();
       }
        return rowCount > 0?ServerResponse.createBySuccessCodeMessage(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getDesc()):ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getDesc());
    }

    /**
     * 修改数据
     *
     * @param myAccount 实例对象
     * @return 实例对象
     */
    @Override
    public MyAccount update(MyAccount myAccount) {
        this.myAccountDao.update(myAccount);
        return this.queryById(myAccount.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.myAccountDao.deleteById(id) > 0;
    }

    @Override
    public ServerResponse selectMyAccount(String merchantId) {
        MyAccount myAccount = myAccountDao.selectMyAccountByMerchantId(merchantId);
        return ServerResponse.createBySuccess(myAccount);
    }

    @Override
    public ServerResponse<PageInfo> selectAllMyAccount(int pageNum, int pageSize) {
        Page page  = PageHelper.startPage(pageNum,pageSize);
        List<MyAccount> myAccountList = myAccountDao.selectAll();
        List<MyAccountVo> myAccountVoList = Lists.newArrayList();
        for (MyAccount myAccount : myAccountList) {
            MyAccountVo myAccountVo = new MyAccountVo();
            myAccountVo.setAccount(myAccount.getAccount());
            myAccountVo.setApplicant(myAccount.getApplicant());
            myAccountVo.setBalance(myAccount.getBalance());
            myAccountVo.setTime(myAccount.getTime());
            if (myAccount.getStatus() == Const.MyAccount.APPLYFOR){
                myAccountVo.setStatus("是");
            }else {
                myAccountVo.setStatus("否");
            }
            Shop shop = shopMapper.selectShopByMerchantId(myAccount.getMerchantId());
            myAccountVo.setShopName(shop.getShopName());
            myAccountVoList.add(myAccountVo);
        }
        PageInfo pageInfo = new PageInfo(page);
        pageInfo.setList(myAccountVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse<PageInfo> selectAllMyAccountAndTel(int page, int limit, String tel) {
        Page pg  = PageHelper.startPage(page,limit);
        List<MyAccount> myAccountList = myAccountDao.selectByTel(tel);
        List<MyAccountVo> myAccountVoList = Lists.newArrayList();
        for (MyAccount myAccount : myAccountList) {
            MyAccountVo myAccountVo = new MyAccountVo();
            myAccountVo.setAccount(myAccount.getAccount());
            myAccountVo.setApplicant(myAccount.getApplicant());
            myAccountVo.setBalance(myAccount.getBalance());
            myAccountVo.setTime(myAccount.getTime());
            if (myAccount.getStatus() == Const.MyAccount.APPLYFOR){
                myAccountVo.setStatus("是");
            }else {
                myAccountVo.setStatus("否");
            }
            Shop shop = shopMapper.selectShopByMerchantId(myAccount.getMerchantId());
            myAccountVo.setShopName(shop.getShopName());
            myAccountVoList.add(myAccountVo);
        }
        PageInfo pageInfo = new PageInfo(pg);
        pageInfo.setList(myAccountVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse<PageInfo> selectAllTransferMyAccount(int page, int limit) {
        Page pg  = PageHelper.startPage(page,limit);
        List<MyAccount> myAccountList = myAccountDao.selectNeedTransFer(Const.MyAccount.APPLYFOR);
        List<MyAccountVo> myAccountVoList = Lists.newArrayList();
        for (MyAccount myAccount : myAccountList) {
            MyAccountVo myAccountVo = new MyAccountVo();
            myAccountVo.setAccount(myAccount.getAccount());
            myAccountVo.setApplicant(myAccount.getApplicant());
            myAccountVo.setBalance(myAccount.getBalance());
            myAccountVo.setTime(myAccount.getTime());
            myAccountVo.setStatus("是");
            Shop shop = shopMapper.selectShopByMerchantId(myAccount.getMerchantId());
            myAccountVo.setShopName(shop.getShopName());
            myAccountVoList.add(myAccountVo);
        }
        PageInfo pageInfo = new PageInfo(pg);
        pageInfo.setList(myAccountVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }
}