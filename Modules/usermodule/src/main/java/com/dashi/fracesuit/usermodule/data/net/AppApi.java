package com.dashi.fracesuit.usermodule.data.net;


import com.dashi.fracesuit.usermodule.modle.User;
import com.dashi.fracesuit.usermodule.modle.User2;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Fracesuit on 2017/5/23.
 */

public interface AppApi {
    @GET("/user")
    Observable<User> getUser();
    @GET("/user2")
    Observable<User2> getUser2();
}
