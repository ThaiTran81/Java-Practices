package model;

public class CompanyModel {
    private int id;
    private String name;
    private int day;
    private int month;
    private int year;
    private int capital;
    private String country;
    private boolean isHeadQuarter;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getCapital() {
        return capital;
    }

    public void setCapital(int capital) {
        this.capital = capital;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean getHeadQuarter() {
        return isHeadQuarter;
    }

    public void setHeadQuarter(boolean headQuarter) {
        isHeadQuarter = headQuarter;
    }

    public void setFoundationDate(String date){
        String [] dataSplt = date.split("/");
        if(dataSplt.length == 1) year = Integer.parseInt(dataSplt[0]);
        if(dataSplt.length ==2 ){
            year = Integer.parseInt(dataSplt[0]);
            month = Integer.parseInt(dataSplt[1]);
        }
        if( dataSplt.length == 3){
            year = Integer.parseInt(dataSplt[0]);
            month = Integer.parseInt(dataSplt[1]);
            day = Integer.parseInt(dataSplt[2]);
        }
    }
}
