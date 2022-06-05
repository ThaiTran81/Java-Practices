import java.nio.file.Path;
import java.util.List;

public abstract class DataFile {
    Path path;
    DataFile(Path path){
        this.path = path;
    }
    abstract List<CompanyModel> getData();
}
