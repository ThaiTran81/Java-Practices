package bussiness;

import ui.AppControl;

public class ImportingSate extends FileImportState{

    public ImportingSate(AppControl appControl) {
        super(appControl);
    }

    @Override
    public void onImport() {
        appControl.importData();
    }

    @Override
    public void onImported() {
        appControl.changeImportState(new ImportedState(appControl));
    }
}
