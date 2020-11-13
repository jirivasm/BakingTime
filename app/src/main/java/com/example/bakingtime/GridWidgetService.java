package com.example.bakingtime;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class GridWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        //simply calls the factory and give it the context
        return new GridRemoteViewsFactory(this.getApplicationContext());
    }
}
