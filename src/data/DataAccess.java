package data;

import model.CompanyModel;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;

public class DataAccess {
    static DataAccess da = null;
    private List<CompanyModel> companies = null;
    private DataFile df = null;

    static public DataAccess getInstance() {
        if (da == null) da = new DataAccess();
        return da;
    }

    public boolean importDataFileFrom(String pathfile) {
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

    public int getTotalCapitalOfHeadquartersIn(String country) {
        int count = 0;
        for (CompanyModel it : companies) {
            if(it.getCountry().equalsIgnoreCase(country) && it.getHeadQuarter()) count++;
        }
        return count;
    }

    public List<CompanyModel> getByCountryAndOrderDesCapital(String country){
        return companies.stream()
                .filter(companyModel -> companyModel.getCountry().equalsIgnoreCase(country))
                .sorted(Comparator.comparingInt(CompanyModel::getCapital).reversed())
                .toList();
    }

    public String getPath(){
        return df.path.toString();
    }

}
