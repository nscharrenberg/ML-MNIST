package com.nscharrenberg.factory;

import com.nscharrenberg.interfaces.IAppRepository;

public class LocalFactory implements IFactory {
    private IAppRepository appRepository;

    public LocalFactory(IAppRepository appRepository) {
        this.appRepository = appRepository;
    }

    @Override
    public IAppRepository getAppRepository() {
        return this.appRepository;
    }
}
