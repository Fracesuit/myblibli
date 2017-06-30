package com.dashi.fracesuit.usermodule.modules.trouble;

import com.dashi.fracesuit.rxjava1x.interator.BaseInteractor;
import com.dashi.fracesuit.usermodule.data.repository.TroubleRepository;
import com.dashi.fracesuit.usermodule.domain.interator.TroubleInterator;
import com.dashi.fracesuit.usermodule.modules.baselist.BaseListPresenter;

import java.util.List;
import java.util.Map;


public class TroublePresenter extends BaseListPresenter<Trouble> {
    @Override
    public BaseInteractor<List<Trouble>, Map<String, String>> getInterator() {
        return new TroubleInterator(new TroubleRepository());
    }
}
