package com.nscharrenberg.factory;

import com.nscharrenberg.interfaces.IAppRepository;
import com.nscharrenberg.interfaces.ICanvasRepository;

public interface IFactory {
    IAppRepository getAppRepository();
    ICanvasRepository getCanvasRepository();
}
