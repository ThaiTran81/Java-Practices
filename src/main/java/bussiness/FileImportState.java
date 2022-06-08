package bussiness;

import ui.AppControl;

public abstract class FileImportState {

    protected AppControl appControl;

    protected FileImportState(AppControl appControl){
        this.appControl = appControl;
    }

    public abstract void onImport();
    public abstract void onImported();
}
