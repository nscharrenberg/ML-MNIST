package com.nscharrenberg.factory;

import com.nscharrenberg.interfaces.IAppRepository;

public interface IFactory {
    IAppRepository getAppRepository();
}
