import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class AppControl {
//    static public void main (String[] args){
//        DataAccess.getInstance().importDataFileFrom("/Users/tranthai/Desktop/companies.csv");
//
//
//    }

    void start(){
        boolean check = false;
        String path = "";
        while( !check){
            Scanner sc=new Scanner(System.in);
            System.out.print("Enter the url of file:");
            path = sc.nextLine();
            check = DataAccess.getInstance().importDataFileFrom(path);
        }

        try {
            Thread thrd = new Thread(new FileObserve(path, this));
            thrd.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        output();

    }

    void update(){
       Boolean check = DataAccess.getInstance().importDataFileFrom(DataAccess.getInstance().getPath());
       if(!check) System.out.println("ERROR");
       else output();
    }

    void output(){
        printTotalCapitalOfHeadIn("CH");
        printCompaniesIn("CH");
    }

    void printTotalCapitalOfHeadIn(String country){
        System.out.print("======= The total capital of headquarters located in "+country+": ");
        System.out.println(DataAccess.getInstance().getTotalCapitalOfHeadquartersIn(country));
    }

    void printCompaniesIn(String country){
        System.out.println("======== The name of companies that the country is in "+country+": ");
        List<CompanyModel> lst = DataAccess.getInstance().getByCountryAndOrderDesCapital(country);
        printCompanies(lst);
    }

    void printCompanies(List<CompanyModel> lst){
        lst.forEach(it -> System.out.println(it.id+" - "+it.name+" - "+it.country+ " - capital: "+it.capital));
    }
}
