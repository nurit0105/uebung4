package newsanalyzer.ui;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import newsanalyzer.ctrl.Controller;
import newsapi.NewsApi;
import newsapi.NewsApiBuilder;
import newsapi.beans.NewsApiException;
import newsapi.enums.Category;
import newsapi.enums.Country;
import newsapi.enums.Endpoint;
import Download.ParallelDownloader;
import Download.SequentialDownloader;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserInterface
{
    public static final String APIKEY = "214ade2bcab9482da030e57af2fdb48a";

    private Controller ctrl = new Controller();
    private List<String> urls = new ArrayList<>();


    public void getDataFromCtrl1(){
        System.out.println("Fußball");

        NewsApi Ctrl1 = new NewsApiBuilder()
                .setApiKey(APIKEY)
                .setQ("Fußball")
                .setEndPoint(Endpoint.TOP_HEADLINES)
                .setSourceCountry(Country.at)
                .setSourceCategory(Category.sports)
                .createNewsApi();
        try {
            urls.add(saveSearch(Ctrl1));
            ctrl.process(Ctrl1);
        }
        catch (IOException | NewsApiException e){
            System.out.println(e.getMessage());
        }
    }

    public void getDataFromCtrl2() {
        System.out.println("Corona");

        NewsApi Ctrl2 = new NewsApiBuilder()
                .setApiKey(APIKEY)
                .setQ("corona")
                .setEndPoint(Endpoint.TOP_HEADLINES)
                .setSourceCountry(Country.at)
                .setSourceCategory(Category.health)
                .createNewsApi();
        try {
            urls.add(saveSearch(Ctrl2));
            ctrl.process(Ctrl2);
        }
        catch (IOException | NewsApiException e){
            System.out.println(e.getMessage());
        }
    }

    public void getDataFromCtrl3(){
        System.out.println("Virus");

        NewsApi Ctrl3 = new NewsApiBuilder()
                .setApiKey(APIKEY)
                .setQ("Virus")
                .setEndPoint(Endpoint.TOP_HEADLINES)
                .setSourceCountry(Country.at)
                .setSourceCategory(Category.science)
                .createNewsApi();
        try {
            urls.add(saveSearch(Ctrl3));
            ctrl.process(Ctrl3);
        }
        catch (IOException | NewsApiException e){
            System.out.println(e.getMessage());
        }
    }

    public void getDataForCustomInput(){
        System.out.println("Choice");

        Scanner choice1 = new Scanner(System.in);

        System.out.println("Entertainment deiner Wahl: ");
        String choice = choice1.next();

        NewsApi CtrlCustom = new NewsApiBuilder()
                .setApiKey(APIKEY)
                .setQ(choice)
                .setEndPoint(Endpoint.TOP_HEADLINES)
                .setSourceCountry(Country.at)
                .setSourceCategory(Category.entertainment)
                .createNewsApi();

        try {
            urls.add(saveSearch(CtrlCustom));
            ctrl.process(CtrlCustom);
        }
        catch (IOException | NewsApiException e){
            System.out.println(e.getMessage());
        }
    }

    public void start(){
        Menu<Runnable> menu = new Menu<>("User Interfacx");
        menu.setTitel("Wählen Sie aus:");
        menu.insert("f", "Fußball", this::getDataFromCtrl1);
        menu.insert("c", "Corona", this::getDataFromCtrl2);
        menu.insert("v", "Virus", this::getDataFromCtrl3);
        menu.insert("e", "Entertainment deiner Wahl: ", this::getDataForCustomInput);
        menu.insert("d", "Last searches will be downloaded",this::downloadLastSearch );
        menu.insert("q", "Quit", null);


        Runnable choice;
        while ((choice = menu.exec()) != null) {
            choice.run();
        }
        System.out.println("Program finished");
    }


    protected String readLine() {
        String value = "\0";
        BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            value = inReader.readLine();
        } catch (IOException ignored) {
        }
        return value.trim();
    }

    protected Double readDouble(int lowerlimit, int upperlimit) 	{
        Double number = null;
        while (number == null) {
            String str = this.readLine();
            try {
                number = Double.parseDouble(str);
            } catch (NumberFormatException e) {
                number = null;
                System.out.println("Please enter a valid number:");
                continue;
            }
            if (number < lowerlimit) {
                System.out.println("Please enter a higher number:");
                number = null;
            } else if (number > upperlimit) {
                System.out.println("Please enter a lower number:");
                number = null;
            }
        }
        return number;
    }

    public void downloadLastSearch(){
        SequentialDownloader sd = new SequentialDownloader();
        ParallelDownloader pd = new ParallelDownloader();
        sd.process(urls);
    }

    public String saveSearch(NewsApi newsApi){
        return newsApi.toString();
    }
}
