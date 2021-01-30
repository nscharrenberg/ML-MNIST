package com.nscharrenberg.factory;

import com.nscharrenberg.interfaces.IAppRepository;
import com.nscharrenberg.interfaces.ICanvasRepository;

public class LocalFactory implements IFactory {
    private IAppRepository appRepository;
    private ICanvasRepository canvasRepository;

    public LocalFactory(IAppRepository appRepository, ICanvasRepository canvasRepository) {
        this.appRepository = appRepository;
        this.canvasRepository = canvasRepository;
    }

    @Override
    public IAppRepository getAppRepository() {
        return this.appRepository;
    }

    @Override
    public ICanvasRepository getCanvasRepository() {
        return this.canvasRepository;
    }
}
