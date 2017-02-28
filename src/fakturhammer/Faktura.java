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
import javax.swing.text.Document;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

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
    
    private String GLL_Odbiorcy;
    public String get_GLL_Odbiorcy()
    {      
        return GLL_Odbiorcy;
    }
    public void set_GLL_Odbiorcy(String GLL_Odbiorcy_)
    {       
        this.GLL_Odbiorcy = GLL_Odbiorcy_;
    }
    
    private String Nr_faktura_korygowanej;
    public String get_nr_faktura_korygowanej()
    {      
        return Nr_faktura_korygowanej;
    }
    public void set_nr_faktura_korygowanej(String Nr_faktura_korygowanej_)
    {       
        this.Nr_faktura_korygowanej = Nr_faktura_korygowanej_;
    }
    
    private String Przyczyna_korekty;
    public String get_Przyczyna_korekty()
    {      
        return Przyczyna_korekty;
    }
    public void set_Przyczyna_korekty(String Przyczyna_korekty_)
    {       
        this.Przyczyna_korekty = Przyczyna_korekty_;
    }
    
    private String Nr_korekty;
    public String get_Nr_korekty()
    {      
        return Nr_korekty;
    }
    public void set_Nr_korekty(String Nr_korekty_)
    {       
        this.Nr_korekty = Nr_korekty_;
    }
    
    private String Nr_zamówienia;
    public String get_Nr_zamówienia()
    {      
        return Nr_zamówienia;
    }
    public void set_Nr_zamówienia(String Nr_zamówienia_)
    {       
        this.Nr_zamówienia = Nr_zamówienia_;
    }
    
    Faktura()
    {
        rozszerzenie="";
        nazwa="";
        ścieżka="";
        liczba_cyfr="";
        stan="";
        katalog="";
        dane = new HashMap<String,String>();
        GLL_Odbiorcy = "";
        Nr_faktura_korygowanej = "";
    }
    
    Faktura(String rozszerzenie_,String nazwa_,
            String ścieżka_,String liczba_cyfr_,
            String stan_, String katalog_,
            String GLL_Odbiorcy_, String Nr_faktura_korygowanej_)
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
        GLL_Odbiorcy=GLL_Odbiorcy_;
        Nr_faktura_korygowanej=Nr_faktura_korygowanej_;
        show();
    }    

    Faktura(Faktura get, String str_2) {
        rozszerzenie=str_2.substring(str_2.lastIndexOf(".")+1);
        nazwa=get.get_nazwa();
//

/*
        if(get.get_rozszerzenie().equals("xml_b"))
        {
            str_2 = get.get_ścieżka().substring(0, str_.indexOf("."))+".xml_b";                            
        }
        else
        {
            str_2 = get.get_ścieżka().substring(0, str_.indexOf("."))+".xml";
        } */

        nazwa = str_2.substring(str_2.lastIndexOf("\\")+1);


//str_2.indexOf("", 0)
        ścieżka=str_2;      
        if(get.get_liczba_cyfr().equals("4"))
        {
            liczba_cyfr="Brak";
            rozszerzenie="xml_b";
        }
        else
        {
            liczba_cyfr=get.get_liczba_cyfr();
        }
        stan="Poprawione";//get.get_stan();
        katalog=get.get_katalog();
    }
    
    public void show()
    {
        System.out.println("\n\n============================\n"+
                "rozszerzenie -  \t\t"+rozszerzenie+
                "\nnazwa-     \t\t"+nazwa+
                "\nścieżka -  \t\t"+ścieżka+
                "\nliczba cyfr -  \t\t"+liczba_cyfr+
                "\nstan -    \t\t"+stan+
                "\nkatalog -  \t\t"+katalog+
                "\nGLL_Odbiorcy -  \t\t"+GLL_Odbiorcy+
                "\nNr_faktura_korygowanej -  \t\t"+Nr_faktura_korygowanej
        );
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

    String[] wczytajnumer() 
    {
  
        File file = new File(this.ścieżka);
        String[] wczytane={"","","","","",""};
        String dane = "";
        try
        {
            //parsowanie
            /*
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            
            String link = this.ścieżka;
            
            if(link.indexOf(".sent")!=-1)
            {
                link = link.substring(0,link.length()-5);
            }
            
            Document doc =  (Document) dBuilder.parse(link);
            
            System.out.println(doc.);
            
            
            
         //   doc.getDocumentElement().normalize();    
         //   NodeList nList = doc.getElementsByTagName("Order");
            
	System.out.println("----------------------------");

	for (int temp = 0; temp < nList.getLength(); temp++) 
        {            
            Node nNode = nList.item(temp);
            System.out.println("\nCurrent Element :" + nNode.getNodeName());   
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {

		Element eElement = (Element) nNode;

		System.out.println("Staff id : " + eElement.getElementsByTagName("Order").item(0).getTextContent());
	    }
	}
            */
            boolean[] logi = {false,false,false,false,false,false,false,false};
            // działa
            FileReader fileReader = new FileReader(file);
            BufferedReader odczytaj = new BufferedReader(fileReader);
            while ((dane = odczytaj.readLine()) != null) 
            {
                int a = 0,b = 0;
                if(dane.indexOf("<BuyerOrderNumber>")!=-1 && logi[0]==false)
                {
                    //
                    a = dane.indexOf(">")+1;
                    b = dane.indexOf("</");
                    
                    wczytane[0] = dane.substring(a, b);     
                    logi[0]=true;
                }
                if(dane.indexOf("<DespatchNumber>")!=-1 && logi[1]==false)
                {
                    //
                    a = dane.indexOf(">")+1;
                    b = dane.indexOf("</");
                    
                    wczytane[1] = dane.substring(a, b);  
                    logi[1]=true;
                }
                if(dane.indexOf("<Reference>")!=-1 && logi[3]==false)
                {
                    logi[3]=true;
                }
                if(dane.indexOf("<InvoiceReferenceNumber>")!=-1 && logi[2]==false && logi[3]==true)
                {
                        //
                    a = dane.indexOf(">")+1;
                    b = dane.indexOf("</");

                    wczytane[2] = dane.substring(a, b);   
                    logi[2]=true;
                    logi[3]=false;
                }
                if(dane.indexOf("<CorrectionReason>")!=-1 && logi[4]==false)
                {
                    //
                    a = dane.indexOf(">")+1;
                    b = dane.indexOf("</");
                    
                    wczytane[3] = dane.substring(a, b);  
                    logi[4]=true;
                }
                //  Buyer  ILN
                if(dane.indexOf("<Buyer>")!=-1 && logi[5]==false)
                {
                    logi[5]=true;
                }
                if(dane.indexOf("<ILN>")!=-1 && logi[6]==false && logi[5]==true)
                {
                        //
                    a = dane.indexOf(">")+1;
                    b = dane.indexOf("</");

                    wczytane[4] = dane.substring(a, b);   
                    logi[6]=true;
                    logi[5]=false;
                }      
             /*   if(dane.indexOf("<CorrectionReason>")!=-1 && logi[7]==false)
                {
                    //
                    a = dane.indexOf(">")+1;
                    b = dane.indexOf("</");
                    
                    wczytane[5] = dane.substring(a, b);     
                    logi[7]=true;
                }*/
            }//CorrectionReason    opis korekty, Buyer - ILN     GLL Odbiorcy    
        }
        catch(FileNotFoundException e)
        {
            System.err.println(e);
        }      
        catch(IOException e)
        {
            System.err.println(e);
        }
   /*     catch(ParserConfigurationException e)
        {
            System.err.println(e);
        }
        catch(SAXException e)
        {
            System.err.println(e);
        }*/
        return wczytane;    
    }
    
    Charset ch_pl = Charset.forName("UTF-8"); 
    Charset ch_de = Charset.forName("iso-8859-1");
    Charset ch_wn = Charset.forName("windows-1250");
        
    public void zapisz_zmiany() 
    {    
        ArrayList<String> lista = new ArrayList<String>();
        String zapis="";
        
        try{
            File file = new File(this.ścieżka);
            boolean[] zapisane = {false,false,false,false,false,false,false};            
            
            String dane = null;
            BufferedReader odczytaj = new BufferedReader(new InputStreamReader(new FileInputStream(file),ch_pl)); 
            while (((dane = odczytaj.readLine()) != null )) 
            {       
                if(dane.indexOf("<BuyerOrderNumber>")!=-1 && this.Nr_zamówienia!=null && zapisane[0]==false)
                {                         
                    dane = "\t\t<BuyerOrderNumber>"+this.Nr_zamówienia+"</BuyerOrderNumber>";  
                    zapisane[0]=true;
                } 
                if(dane.indexOf("<DespatchNumber>")!=-1 && this.Nr_korekty!=null && zapisane[1]==false)
                {                         
                    dane = "\t\t<DespatchNumber>"+this.Nr_korekty+"</DespatchNumber>";        
                    zapisane[1]=true;
                } 
                if(dane.indexOf("<Reference>")!=-1 && zapisane[3]==false)
                {                                
                    zapisane[3]=true;
                } 
                if(dane.indexOf("<InvoiceReferenceNumber>")!=-1 && zapisane[2]==false && 
                        zapisane[3]==true && this.Nr_faktura_korygowanej!=null && this.Nr_faktura_korygowanej!="Brak")
                {                         
                    dane = "\t\t<InvoiceReferenceNumber>"+this.Nr_faktura_korygowanej+"</InvoiceReferenceNumber>";      
                    zapisane[2]=true;
                    zapisane[3]=false;
                } 
                if(dane.indexOf("<CorrectionReason>")!=-1 && zapisane[4]==false && this.Przyczyna_korekty!=null && this.Przyczyna_korekty!="Brak")
                {                         
                    dane = "\t\t<CorrectionReason>"+this.Przyczyna_korekty+"</CorrectionReason>";      
                    zapisane[4]=true;
                }
                if(dane.indexOf("<Buyer>")!=-1 && zapisane[5]==false)
                {                                
                    zapisane[5]=true;
                } 
                if(dane.indexOf("<ILN>")!=-1 && zapisane[6]==false && 
                        zapisane[5]==true && this.GLL_Odbiorcy!=null && this.GLL_Odbiorcy!="Brak")
                {                         
                    dane = "\t\t<ILN>"+this.GLL_Odbiorcy+"</ILN>";      
                    zapisane[6]=true;
                    zapisane[5]=false;
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
