package data;

import model.CompanyModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CSVDataFile extends DataFile {
    public CSVDataFile(Path path) {
        super(path);
    }
    @Override
    List<CompanyModel> getData() {
        List<CompanyModel> arr = new ArrayList<>();
        try(BufferedReader reader = Files.newBufferedReader(path)) {
            String line = null;

            //skip the first line
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                CompanyModel companyModel = new CompanyModel();
                String[] data = line.split(",", -1);


                companyModel.setId(Integer.parseInt(data[0]));
                companyModel.setName(data[1]);
                companyModel.setFoundationDate(data[2]);
                companyModel.setCapital(Integer.parseInt(data[3]));
                companyModel.setCountry(data[4]);
                companyModel.setHeadQuarter(!data[5].equals("") && !data[5].equals("0"));

                arr.add(companyModel);

            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return arr;
    }
}

