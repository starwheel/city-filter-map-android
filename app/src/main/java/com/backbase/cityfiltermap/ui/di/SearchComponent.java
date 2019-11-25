package com.backbase.cityfiltermap.ui.di;

import com.backbase.cityfiltermap.di.annotations.ActivityScope;
import com.backbase.cityfiltermap.ui.search.CityListFragment;
import com.backbase.cityfiltermap.ui.search.MainActivity;
import com.backbase.cityfiltermap.ui.search.MapFragment;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = { SearchModule.class })
public interface SearchComponent {

    @Subcomponent.Factory
    interface Factory {
        SearchComponent create();
    }

    void inject(MainActivity activity);
    void inject(CityListFragment fragment);
    void inject(MapFragment fragment);
}
