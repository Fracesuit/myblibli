package com.dashi.fracesuit.usermodule.domain.repository;


import rx.Observable;

/**
 * Created by Fracesuit on 2017/5/23.
 */

public interface BaseRepository<T, P> {
    Observable<T> requestData(P p);
}
