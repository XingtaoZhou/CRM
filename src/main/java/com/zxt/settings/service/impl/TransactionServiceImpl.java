package com.zxt.settings.service.impl;

import com.zxt.settings.dao.CustomerDao;
import com.zxt.settings.dao.TranDao;
import com.zxt.settings.dao.TranHistoryDao;
import com.zxt.settings.dao.UserDao;
import com.zxt.settings.domain.Customer;
import com.zxt.settings.domain.Tran;
import com.zxt.settings.domain.TranHistory;
import com.zxt.settings.domain.User;
import com.zxt.settings.service.TransactionService;
import com.zxt.utils.UUIDUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service
public class TransactionServiceImpl implements TransactionService {

    @Resource
    private UserDao userDao;

    @Resource
    private CustomerDao customerDao;

    @Resource
    private TranDao tranDao;

    @Resource
    private TranHistoryDao tranHistoryDao;

    @Override
    public List<User> getUserList() {

        return userDao.getUserList();

    }

    @Override
    public List<String> getCustomerName(String name) {

        return customerDao.getCustomerName(name);

    }

    @Transactional(
            propagation = Propagation.REQUIRED,
            isolation = Isolation.DEFAULT
    )
    @Override
    public Boolean save(Tran tran, String customerName) {

        Boolean flag = true;
        //根据customerName查询customer表，如果有找到id，没有就新建
        Customer customer = customerDao.getCustomerByName(customerName);
        if (customer != null){
            String customerId = customer.getId();
        }else {
            customer = new Customer();
            //完善customer信息
            customer.setId(UUIDUtil.getUUID());
            customer.setCreateBy(tran.getCreateBy());
            customer.setCreateTime(tran.getCreateTime());
            customer.setName(customerName);
            customer.setDescription(tran.getDescription());
            customer.setContactSummary(tran.getContactSummary());
            customer.setOwner(tran.getOwner());

            int result1 = customerDao.save(customer);
            if (result1 != 1){
                flag = false;
            }
        }
        //完善tran的数据
        tran.setCustomerId(customer.getId());
        //添加交易信息
        int result2 = tranDao.save(tran);
        if (result2 != 1){
            flag = false;
        }
        //添加历史交易信息
        TranHistory tranHistory = new TranHistory();
        tranHistory.setId(UUIDUtil.getUUID());
        tranHistory.setCreateBy(tran.getCreateBy());
        tranHistory.setCreateTime(tran.getCreateTime());
        tranHistory.setExpectedDate(tran.getExpectedDate());
        tranHistory.setMoney(tran.getMoney());
        tranHistory.setStage(tran.getStage());
        tranHistory.setTranId(tran.getId());
        int result3 = tranHistoryDao.save(tranHistory);
        if (result3 != 1){
            flag = false;
        }
        return flag;
    }

    @Override
    public Tran detail(String id) {

        return tranDao.detail(id);

    }

    @Override
    public List<TranHistory> getHistoryListByTranId(String id) {
        return tranHistoryDao.getHistoryListByTranId(id);
    }
}
















