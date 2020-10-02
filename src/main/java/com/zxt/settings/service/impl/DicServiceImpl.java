package com.zxt.settings.service.impl;

import com.zxt.settings.dao.DicTypeDao;
import com.zxt.settings.dao.DicValueDao;
import com.zxt.settings.domain.DicType;
import com.zxt.settings.domain.DicValue;
import com.zxt.settings.service.DicService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class DicServiceImpl implements DicService {

    @Resource
    private DicTypeDao dicTypeDao;
    @Resource
    private DicValueDao dicValueDao;

    @Override
    public Map<String, List<DicValue>> getDic() {

        Map<String, List<DicValue>> map = new HashMap<>();
        //查数据库找到key（dic_type）
        List<DicType> dicTypeList = dicTypeDao.selectType();

        //根据key查数据库找到value（dic_value）
        for (DicType dicType : dicTypeList){

            String type = dicType.getCode();
            List<DicValue> dicValueList = dicValueDao.selectByTypeCode(type);
            map.put(type,dicValueList);
        }

        return map;
    }
}


















