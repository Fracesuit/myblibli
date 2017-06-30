package com.dashi.fracesuit.usermodule.domain.interator;


import com.dashi.fracesuit.rxjava1x.interator.BaseInteractor;
import com.dashi.fracesuit.usermodule.domain.repository.BaseRepository;
import com.dashi.fracesuit.usermodule.modules.trouble.Trouble;

import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by Fracesuit on 2017/5/23.
 */

public class TroubleInterator extends BaseInteractor<List<Trouble>, Map<String, String>> {
    BaseRepository<List<Trouble>, Map<String, String>> troubleInterator;

    public TroubleInterator(BaseRepository troubleInterator) {
        this.troubleInterator = troubleInterator;
    }

    @Override
    protected Observable<List<Trouble>> buildObservable(Map<String, String> param) {
        return troubleInterator.requestData(param);
    }
}
