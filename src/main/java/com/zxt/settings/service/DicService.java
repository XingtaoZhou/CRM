package com.zxt.settings.service;

import com.zxt.settings.domain.DicValue;

import java.util.List;
import java.util.Map;

public interface DicService {

    Map<String, List<DicValue>> getDic();
}
