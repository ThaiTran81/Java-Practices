package ui;

import data.DataAccess;
import data.FileObserve;
import model.CompanyModel;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class AppControl {

    private Thread fileObserveThrd;
    private final Scanner sc;

    public AppControl(){
        sc = new Scanner(System.in);
    }
    public void start() {
        boolean check = false;
        String path = "";

        while (!check) {
            System.out.print("Enter the url of file:");
            path = sc.nextLine();
            check = importFile(path);
        }

        try {
            fileObserveThrd = new Thread(new FileObserve(path, this));
            fileObserveThrd.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        showMenu();

    }

    public boolean importFile(String path) {
        return DataAccess.getInstance().importDataFileFrom(path);
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
                case 1 -> printTotalCapitalOfHeadIn("VN");
                case 2 -> printCompaniesIn("VN");
                case 3 -> start();
                default -> {}
            }
        }
    }

    void printTotalCapitalOfHeadIn(String country) {
        System.out.print("======= The total capital of headquarters located in " + country + ": ");
        System.out.println(DataAccess.getInstance().getTotalCapitalOfHeadquartersIn(country));
    }

    void printCompaniesIn(String country) {
        System.out.println("======== The name of companies that the country is in " + country + ": ");
        List<CompanyModel> lst = DataAccess.getInstance().getByCountryAndOrderDesCapital(country);
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
}
