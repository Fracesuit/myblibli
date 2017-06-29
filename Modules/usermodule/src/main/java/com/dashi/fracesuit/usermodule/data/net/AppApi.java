package com.dashi.fracesuit.usermodule.data.net;


import com.dashi.fracesuit.usermodule.modle.User;
import com.dashi.fracesuit.usermodule.modle.User2;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Fracesuit on 2017/5/23.
 */

public interface AppApi {
    @GET("/user")
    Observable<User> getUser();

    @GET("/user2")
    Observable<User2> getUser2();

    //查询历史记录
    @FormUrlEncoded
    @POST("http://219.134.134.155:10038/map/metaInfo/historyList")
    Observable<String> queryHistory(@Field("username") String username, @Field("pageIndex") String pageIndex, @Field("pageSize") String pageSize);


}
