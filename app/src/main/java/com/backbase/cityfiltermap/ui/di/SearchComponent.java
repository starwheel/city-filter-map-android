package com.backbase.cityfiltermap.ui.di;

import com.backbase.cityfiltermap.di.annotations.ActivityScope;
import com.backbase.cityfiltermap.ui.MainActivity;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = { SearchModule.class })
public interface SearchComponent {

    @Subcomponent.Factory
    interface Factory {
        SearchComponent create();
    }

    void inject(MainActivity activity);
}
