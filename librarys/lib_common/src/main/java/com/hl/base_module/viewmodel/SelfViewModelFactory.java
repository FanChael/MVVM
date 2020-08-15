package com.hl.base_module.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.hl.lib_network.controller.presenter.BaseControlPresenter;

import java.lang.reflect.InvocationTargetException;

public class SelfViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private BaseControlPresenter baseControlPresenter;

    public SelfViewModelFactory(BaseControlPresenter _baseControlPresenter) {
        baseControlPresenter = _baseControlPresenter;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (ViewModel.class.isAssignableFrom(modelClass)) {
            try {
                return modelClass.getConstructor(BaseControlPresenter.class).newInstance(baseControlPresenter);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("Cannot create an instance of " + modelClass, e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Cannot create an instance of " + modelClass, e);
            } catch (InstantiationException e) {
                throw new RuntimeException("Cannot create an instance of " + modelClass, e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException("Cannot create an instance of " + modelClass, e);
            }
        }
        return super.create(modelClass);
    }
}