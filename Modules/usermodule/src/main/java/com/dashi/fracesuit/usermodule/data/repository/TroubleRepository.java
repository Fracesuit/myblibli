package com.dashi.fracesuit.usermodule.data.repository;

import com.alibaba.fastjson.JSONArray;
import com.dashi.fracesuit.usermodule.data.net.ApiService;
import com.dashi.fracesuit.usermodule.domain.repository.BaseRepository;
import com.dashi.fracesuit.usermodule.modules.trouble.Trouble;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Func1;


/**
 * Created by Fracesuit on 2017/5/22.
 */

public class TroubleRepository implements BaseRepository<List<Trouble>, Map<String, String>> {
    @Override
    public Observable<List<Trouble>> requestData(Map<String, String> params) {
        String username = params.get("username");
        String pageIndex = params.get("pageIndex");
        String pageSize = params.get("pageSize");
        return ApiService.instance().queryHistory(username, pageIndex, pageSize).map(new Func1<String, List<Trouble>>() {
            @Override
            public List<Trouble> call(String s) {
                //List<String> strings = JSONArray.parseArray(s, String.class);
                List<Trouble> strings = JSONArray.parseArray(s, Trouble.class);
                return strings;
            }
        });
    }
}
