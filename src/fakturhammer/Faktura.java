/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fakturhammer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author user
 */
public class Faktura {
    
    private String rozszerzenie ;
    private String nazwa;
    private String ścieżka;
    private String liczba_cyfr;
    private String stan;
    private String katalog;
    private HashMap<String,String> dane = null;
    
    Faktura()
    {
        rozszerzenie="";
        nazwa="";
        ścieżka="";
        liczba_cyfr="";
        stan="";
        katalog="";
        dane = new HashMap<String,String>();
    }
    
    Faktura(String rozszerzenie_,String nazwa_,String ścieżka_,String liczba_cyfr_,String stan_, String katalog_)
    {
        rozszerzenie=rozszerzenie_;
        nazwa=nazwa_;
        ścieżka=ścieżka_;      
        if(liczba_cyfr_.equals("4"))
        {
            liczba_cyfr="Brak";
            rozszerzenie="xml_b";
        }
        else
        {
            liczba_cyfr=liczba_cyfr_;
        }
        stan=stan_;
        katalog=katalog_;
    }
    
    public void show()
    {
        System.out.println("\n\n============================\n"+
                "rozszerzenie - \t\t"+rozszerzenie+
                "\nnazwa-    \t\t"+nazwa+
                "\nścieżka - \t\t"+ścieżka+
                "\nliczba cyfr - \t\t"+liczba_cyfr+
                "\nstan -   \t\t"+stan+
                "\nkatalog - \t\t"+katalog);
    }
    
    public String get_rozszerzenie()
    {      
        return rozszerzenie;
    }
    public void set_rozszerzenie(String rozszerzenie_)
    {       
        this.rozszerzenie = rozszerzenie_;
    }
    
    public String get_nazwa()
    {
        return nazwa;
    }
    public void set_nazwa(String nazwa_)
    {
        this.nazwa = nazwa_;
    }
    
    public String get_ścieżka()
    {
        return ścieżka;
    }
    public void set_ścieżka(String ścieżka_)
    {
        this.ścieżka = ścieżka_;
    }
    
    public String get_liczba_cyfr()
    {
        return liczba_cyfr;
    }
    public void set_liczba_cyfr(String liczba_cyfr_)
    {
        this.liczba_cyfr = liczba_cyfr_;
    }
    
    public String get_stan()
    {
        return stan;
    }
    public void set_stan(String stan_)
    {
        this.stan = stan_;
    }     
    
    public String get_katalog()
    {
        return katalog;
    }
    public void set_katalog(String katalog_)
    {
        this.katalog = katalog_;
    }

    String wczytajnumer() 
    {
  
        File file = new File(this.ścieżka);
        String wczytane="";
        String dane = "";
        try
        {
            FileReader fileReader = new FileReader(file);
            BufferedReader odczytaj = new BufferedReader(fileReader);
            while ((dane = odczytaj.readLine()) != null) 
            {
                int a = 0,b = 0;
                if(dane.indexOf("<BuyerOrderNumber>")!=-1)
                {     
                    
                    //
                    a = dane.indexOf(">")+1;
                    b = dane.indexOf("</");
                    
                    wczytane = dane.substring(a, b);
                    
                }                
            }            
        }
        catch(FileNotFoundException e)
        {
            System.err.println(e);
        }      
        catch(IOException e)
        {
            System.err.println(e);
        }
        return wczytane;    
    }
    
    Charset ch_pl = Charset.forName("UTF-8"); 
    Charset ch_de = Charset.forName("iso-8859-1");
    Charset ch_wn = Charset.forName("windows-1250");

    public void zapisz_zmiany(String numer) {
    
        ArrayList<String> lista = new ArrayList<String>();
        String zapis="";
        
        try 
        {       
            File file = new File(this.ścieżka);
            
            String dane = null;
            BufferedReader odczytaj = new BufferedReader(new InputStreamReader(new FileInputStream(file),ch_pl)); 
            while (((dane = odczytaj.readLine()) != null )) 
            {                        
                int a = 0,b = 0;

                if(dane.indexOf("<BuyerOrderNumber>")!=-1)
                {                         
                    dane = "\t\t<BuyerOrderNumber>"+numer+"</BuyerOrderNumber>";                    
                } 
                lista.add("\n"+dane);
            }
            odczytaj.close();
            lista.set(0, "<Document-Invoice>");
            FileWriter fw = new FileWriter(this.ścieżka); 
            BufferedWriter bw = new BufferedWriter(fw);                   
            for (String line : lista) 
            {
                System.out.println(">"+line);
                bw.write(line);
                //oznaczenie krzyżykiem
            }
            bw.close();
            fw.close();
            
            odczytaj=null;
            lista.clear();
            
        }
        catch(IOException e)
        {
            System.err.println(e);
        }
    }
}
