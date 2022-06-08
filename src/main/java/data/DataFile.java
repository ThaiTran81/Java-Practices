package data;

import model.CompanyModel;

import java.nio.file.Path;
import java.util.List;

public abstract class DataFile {
    protected Path path;
    protected DataFile(Path path){
        this.path = path;
    }
    public abstract List<CompanyModel> getData();
}
