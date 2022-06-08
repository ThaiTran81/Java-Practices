package ui;

import bussiness.FileImportState;
import bussiness.ImportingSate;
import data.DataAccess;
import data.FileObserve;
import data.Listener;
import model.CompanyModel;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ConcurrentMap;

public class AppControl implements Listener {

    private Thread fileObserveThrd;
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
            System.out.print("Enter the url of file:");
            path = sc.nextLine();
            check = DataAccess.getInstance().setPathFile(path);
            if(!check) System.out.println("Import failed! Please try again");
        }

        DataAccess.getInstance().fileObserve.subcribe(this);
        showMenu();

    }

    void showMenu() {
        boolean check = false;
        int choice = -1;

        while (!check) {
            System.out.println("============== Menu ===============");
            System.out.println("1. Output the total capital of headquarters located in “CH”.");
            System.out.println("2. Output the name of companies that the country is in “CH”. The list is sorted descending by capital.");
            System.out.println("3. Reimport");
            System.out.println("0. Exit");
            System.out.print("->>>>>>>> Your choice: ");
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
        System.out.print("======= The total capital of headquarters located in " + country + ": ");
        System.out.println(DataAccess.getInstance().getTotalCapitalOfHeadquartersIn(companies, country));
    }

    void printCompaniesIn(String country) {
        System.out.println("======== The name of companies that the country is in " + country + ": ");
        List<CompanyModel> lst = DataAccess.getInstance().getByCountryAndOrderDesCapital(companies,country);
        printCompanies(lst);
    }

    void printCompanies(List<CompanyModel> lst) {
        lst.forEach(it -> {
            System.out.println(it.getId() + " - " + it.getName() + " - " + it.getCountry() + " - capital: " + it.getCapital());
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
