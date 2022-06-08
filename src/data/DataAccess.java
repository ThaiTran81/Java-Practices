package data;

import model.CompanyModel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DataAccess {
    static DataAccess da;
    private DataFile df;

    public FileObserve fileObserve;
    private Thread fileObserveThread;

    static public DataAccess getInstance() {
        if (da == null) da = new DataAccess();
        return da;
    }

    public boolean setPathFile(String pathfile) {
        Path path = Paths.get(pathfile);

        if (!Files.exists(path) || Files.isDirectory(path)) return false;

        String[] fileNameAttr = path.getFileName().toString().split("\\.");
        String fileEx = fileNameAttr[fileNameAttr.length - 1];

        switch (fileEx) {
            case "csv" -> {
                df = new CSVDataFile(path);
            }
            default -> throw new UnsupportedOperationException("The file extension is not supported");
        }
        setFileObserve(pathfile);
        return true;
    }

    private void setFileObserve(String pathfile) {
        try {
            fileObserve = new FileObserve(pathfile);
            fileObserveThread = new Thread(fileObserve);
            fileObserveThread.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<CompanyModel> getAllData() throws NullPointerException {
        List<CompanyModel> companies = new ArrayList<>();
        if (df != null) {
            companies = df.getData();
        } else throw new NullPointerException("Need to set the url of the first");
        return companies;
    }

    public int getTotalCapitalOfHeadquartersIn(String country) {
        int count = 0;
        List<CompanyModel> companies = getAllData();
        for (CompanyModel it : companies) {
            if (it.getCountry().equalsIgnoreCase(country) && it.getHeadQuarter()) count++;
        }
        return count;
    }

    public int getTotalCapitalOfHeadquartersIn(List<CompanyModel> companies, String country) {
        int count = 0;
        for (CompanyModel it : companies) {
            if (it.getCountry().equalsIgnoreCase(country) && it.getHeadQuarter()) count++;
        }
        return count;
    }

    public List<CompanyModel> getByCountryAndOrderDesCapital(String country) {
        List<CompanyModel> companies = getAllData();
        return companies.stream()
                .filter(companyModel -> companyModel.getCountry().equalsIgnoreCase(country))
                .sorted(Comparator.comparingInt(CompanyModel::getCapital).reversed())
                .toList();
    }

    public List<CompanyModel> getByCountryAndOrderDesCapital(List<CompanyModel> companies, String country) {
        return companies.stream()
                .filter(companyModel -> companyModel.getCountry().equalsIgnoreCase(country))
                .sorted(Comparator.comparingInt(CompanyModel::getCapital).reversed())
                .toList();
    }

    public String getPath() {
        return df.path.toString();
    }

}
