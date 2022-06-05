import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class DataAccess {
    static DataAccess da = null;
    List<CompanyModel> companies = null;
    DataFile df = null;

    static public DataAccess getInstance() {
        if (da == null) da = new DataAccess();
        return da;
    }

    Boolean importDataFileFrom(String pathfile) {
        Path path = Paths.get(pathfile);

        if (!Files.exists(path) || Files.isDirectory(path)) return false;

        String[] fileNameAttr = path.getFileName().toString().split("\\.");
        String fileEx = fileNameAttr[fileNameAttr.length - 1];

        switch (fileEx) {
            case "csv" -> df = new CSVDataFile(path);
            default -> System.out.println("Not supported file extension");
        }

        if (df != null) {
            companies = df.getData();
            return true;
        }
        return false;
    }

    int getTotalCapitalOfHeadquartersIn(String country) {
        int count = 0;
        for (CompanyModel it : companies) {
            if(it.getCountry().equalsIgnoreCase(country) && it.isHeadQuarter) count++;
        }
        return count;
    }

    List<CompanyModel> getByCountryAndOrderDesCapital(String country){
        return companies.stream().filter(companyModel -> companyModel.getCountry().equalsIgnoreCase(country))
                .sorted((a,b)-> b.capital - a.capital).toList();
    }

    String getPath(){
        return df.path.toString();
    }

}
