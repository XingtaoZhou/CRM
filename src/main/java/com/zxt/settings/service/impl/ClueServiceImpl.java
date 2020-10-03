package com.zxt.settings.service.impl;

import com.zxt.settings.dao.*;
import com.zxt.settings.domain.*;
import com.zxt.settings.service.ClueService;
import com.zxt.settings.vo.PageVo;
import com.zxt.utils.DateTimeUtil;
import com.zxt.utils.UUIDUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ClueServiceImpl implements ClueService {

    @Resource
    private UserDao userDao;

    @Resource
    private ClueDao clueDao;

    @Resource
    private ClueActivityRelationDao clueActivityRelationDao;

    @Resource
    private ActivityDao activityDao;

    @Resource
    private TranDao tranDao;

    @Resource
    private ClueRemarkDao clueRemarkDao;

    @Resource
    private CustomerDao customerDao;

    @Resource
    private ContactsDao contactsDao;

    @Resource
    private CustomerRemarkDao customerRemarkDao;

    @Resource
    private ContactsRemarkDao contactsRemarkDao;

    @Resource
    private ContactsActivityRelationDao contactsActivityRelationDao;

    @Resource
    private TranHistoryDao tranHistoryDao;


    @Override
    public List<User> getUserList() {

        List<User> userList = userDao.getUserList();

        return userList;
    }

    @Override
    public Map<String, Object> save(Clue clue) {

        Boolean flag = true;

        int result = clueDao.save(clue);
        if (result != 1){
            flag = false;
        }
        Map<String,Object> map = new HashMap<>();
        map.put("success",flag);
        return map;
    }

    @Override
    public PageVo<Clue> pageList(Map<String, Object> map) {

        //获得total
        int total = clueDao.countPageList(map);

        //获得clueList
        List<Clue> clueList = clueDao.pageList(map);

        PageVo<Clue> vo = new PageVo<>();
        vo.setDataList(clueList);
        vo.setTotal(total);

        return vo;
    }

    @Override
    public Clue detail(String id) {

        Clue clue = clueDao.detail(id);
        return clue;
    }

    @Override
    public List<Activity> getActivityListByClueId(String id) {

        List<Activity> activityList = clueDao.getActivityListByClueId(id);

        return activityList;
    }

    @Override
    public Map<String, Object> deleteCARById(String id) {

        Boolean flag = true;

        int result = clueActivityRelationDao.deleteCARById(id);
        if (result != 1){
            flag = false;
        }

        Map<String,Object> map = new HashMap<>();
        map.put("success",flag);
        return map;
    }

    @Override
    public List<Activity> getActivityByName(Map<String,Object> map) {

        List<Activity> activityList = activityDao.getActivityByName(map);

        return activityList;
    }

    @Override
    public Map<String, Object> bund(String cid, String[] ids) {

        Boolean flag = true;
        ClueActivityRelation car = new ClueActivityRelation();

        for (String id : ids){

            car.setId(UUIDUtil.getUUID());
            car.setClueId(cid);
            car.setActivityId(id);
            int result = clueActivityRelationDao.insert(car);
            if (result != 1){
                flag = false;
            }
        }
        Map<String,Object> map = new HashMap<>();
        map.put("success",flag);

        return map;
    }

    @Override
    public List<Activity> searchActivityByName(String name) {

        return activityDao.searchActivityByName(name);

    }

    @Override
    public void convert(String clueId, Tran tran, String createBy) {

        Boolean flag = true;
        String currentTime = DateTimeUtil.getSysTime();

        //(1) 获取到线索id，通过线索id获取线索对象（线索对象当中封装了线索的信息）
        Clue clue = clueDao.getClueById(clueId);
        //(2) 通过线索对象提取客户信息，当该客户不存在的时候，新建客户（根据公司的名称精确匹配，判断该客户是否存在！）
        String company = clue.getCompany();
        Customer customer = customerDao.getCustomerByName(company);
        if (customer!=null){
            //客户存在
        }else {
            //客户不存在，新建客户
            customer.setWebsite(clue.getWebsite());
            customer.setPhone(clue.getPhone());
            customer.setOwner(clue.getOwner());
            customer.setNextContactTime(clue.getNextContactTime());
            customer.setName(clue.getCompany());
            customer.setId(UUIDUtil.getUUID());
            customer.setDescription(clue.getDescription());
            customer.setCreateTime(currentTime);
            customer.setCreateBy(createBy);
            customer.setContactSummary(clue.getContactSummary());
            customer.setAddress(clue.getAddress());
            int result1 = customerDao.save(customer);
            if (result1 != 1){
                flag = false;
            }
        }
        //(3) 通过线索对象提取联系人信息，保存联系人
        Contacts contacts = new Contacts();
        contacts.setId(UUIDUtil.getUUID());
        contacts.setCreateBy(createBy);
        contacts.setCreateTime(currentTime);
        contacts.setSource(clue.getSource());
        contacts.setOwner(clue.getOwner());
        contacts.setNextContactTime(clue.getNextContactTime());
        contacts.setMphone(clue.getMphone());
        contacts.setJob(clue.getJob());
        contacts.setFullname(clue.getFullname());
        contacts.setEmail(clue.getEmail());
        contacts.setDescription(clue.getDescription());
        contacts.setCustomerId(customer.getId());
        contacts.setContactSummary(clue.getContactSummary());
        contacts.setAppellation(clue.getAppellation());
        contacts.setAddress(clue.getAddress());
        int result2 = contactsDao.save(contacts);
        if (result2 != 1){
            flag = false;
        }
        //(4) 线索备注转换到客户备注以及联系人备注
        List<ClueRemark> clueRemarkList = clueRemarkDao.getRemarkById(clueId);
        for (ClueRemark remark : clueRemarkList){
            CustomerRemark customerRemark = new CustomerRemark();
            customerRemark.setNoteContent(remark.getNoteContent());
            customerRemark.setId(UUIDUtil.getUUID());
            customerRemark.setEditFlag("0");
            customerRemark.setCustomerId(customer.getId());
            customerRemark.setCreateTime(currentTime);
            customerRemark.setCreateBy(createBy);
            int result3 = customerRemarkDao.save(customerRemark);
            if (result3 != 1){
                flag = false;
            }
            ContactsRemark contactsRemark = new ContactsRemark();
            contactsRemark.setNoteContent(remark.getNoteContent());
            contactsRemark.setId(UUIDUtil.getUUID());
            contactsRemark.setEditFlag("0");
            contactsRemark.setContactsId(customer.getId());
            contactsRemark.setCreateTime(currentTime);
            contactsRemark.setCreateBy(createBy);
            int result4 = contactsRemarkDao.save(contactsRemark);
            if (result4 != 1){
                flag = false;
            }
        }
        //(5) “线索和市场活动”的关系转换到“联系人和市场活动”的关系
        List<ClueActivityRelation> relationList = clueActivityRelationDao.getRelation(clueId);
        for (ClueActivityRelation relation : relationList){
            ContactsActivityRelation relation1 = new ContactsActivityRelation();
            relation1.setId(UUIDUtil.getUUID());
            relation1.setContactsId(contacts.getId());
            relation1.setActivityId(relation.getActivityId());
            int result5 = contactsActivityRelationDao.save(relation1);
            if (result5 != 1){
                flag = false;
            }
        }
        //(6) 如果有创建交易需求，创建一条交易
        if (tran != null){
            tran.setSource(clue.getSource());
            tran.setOwner(clue.getOwner());
            tran.setNextContactTime(clue.getNextContactTime());
            tran.setDescription(clue.getDescription());
            tran.setCustomerId(customer.getId());
            tran.setContactsId(contacts.getId());
            tran.setContactSummary(clue.getContactSummary());
            int result6 = tranDao.save(tran);
            if (result6 != 1){
                flag = false;
            }

            //(7) 如果创建了交易，则创建一条该交易下的交易历史
            TranHistory tranHistory = new TranHistory();
            tranHistory.setTranId(tran.getId());
            tranHistory.setStage(tran.getStage());
            tranHistory.setMoney(tran.getMoney());
            tranHistory.setId(UUIDUtil.getUUID());
            tranHistory.setExpectedDate(tran.getExpectedDate());
            tranHistory.setCreateTime(currentTime);
            tranHistory.setCreateBy(createBy);
            int result7 = tranHistoryDao.save(tranHistory);
            if(result7 != 1){
                flag = false;
            }
        }

        //(8) 删除线索备注
        for (ClueRemark remark : clueRemarkList){
            int result8 = clueRemarkDao.delete(remark);
            if (result8 != 1){
                flag = false;
            }
        }

        //(9) 删除线索和市场活动的关系
        for (ClueActivityRelation relation : relationList){
            int result9 = clueActivityRelationDao.deleteCARById(relation.getId());
            if (result9 != 1){
                flag = false;
            }
        }
        //(10) 删除线索
        int result10 = clueDao.delete(clueId);
        if (result10 != 1){
            flag = false;
        }

    }

    @Override
    public List<ClueRemark> getRemarkById(String id) {

        return clueRemarkDao.getRemarkById(id);

    }
}










