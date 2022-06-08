package ui;

import bussiness.FileImportState;
import bussiness.ImportingSate;
import data.DataAccess;
import data.Listener;
import model.CompanyModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Scanner;

public class AppControl implements Listener {

    private final Logger LOGGER = LogManager.getLogger(AppControl.class);

    private final Scanner sc;

    private FileImportState fileImportState;

    private List<CompanyModel> companies;

    public AppControl(){
        sc = new Scanner(System.in);
    }
    public void start() {
        boolean check = false;
        String path = "";

        changeImportState(new ImportingSate(this));
        while (!check) {
            LOGGER.info("Enter the url of file:");
            if(sc.hasNext())  path = sc.nextLine();
            check = DataAccess.getInstance().setPathFile(path);
            if(!check) LOGGER.info("Import failed! Please try again\n");
        }

        DataAccess.getInstance().fileObserve.subcribe(this);
        showMenu();

    }

    void showMenu() {
        boolean check = false;
        int choice = -1;

        while (!check) {
            LOGGER.info("============== Menu ===============\n");
            LOGGER.info("1. Output the total capital of headquarters located in “CH”.\n");
            LOGGER.info("2. Output the name of companies that the country is in “CH”. The list is sorted descending by capital.\n");
            LOGGER.info("3. Reimport\n");
            LOGGER.info("0. Exit\n");
            LOGGER.info("->>>>>>>> Your choice: ");
            choice = sc.nextInt();

            switch (choice){
                case 0 -> {
                    check = true;
                    exit();
                }
                case 1 -> {
                    fileImportState.onImport();
                    printTotalCapitalOfHeadIn("VN");
                }
                case 2 -> {
                    fileImportState.onImport();
                    printCompaniesIn("VN");
                }
                case 3 -> start();
                default -> {}
            }
        }
    }

    public void importData(){
        companies = DataAccess.getInstance().getAllData();
        fileImportState.onImported();
    }
    public void changeImportState(FileImportState fileImportState){
        this.fileImportState = fileImportState;
    }

    void printTotalCapitalOfHeadIn(String country) {
        LOGGER.info("======= The total capital of headquarters located in " + country + ": ");
        LOGGER.info(DataAccess.getInstance().getTotalCapitalOfHeadquartersIn(companies, country)+"\n");
    }

    void printCompaniesIn(String country) {
        LOGGER.info("======== The name of companies that the country is in " + country + ": \n");
        List<CompanyModel> lst = DataAccess.getInstance().getByCountryAndOrderDesCapital(companies,country);
        printCompanies(lst);
    }

    void printCompanies(List<CompanyModel> lst) {
        lst.forEach(it -> {
            LOGGER.info(it.getId() + " - " + it.getName() + " - " + it.getCountry() + " - capital: " + it.getCapital()+"\n");
        });
    }

    void exit(){
        sc.close();
       System.exit(0);
    }

    @Override
    public void update() {
        changeImportState(new ImportingSate(this));
    }
}
