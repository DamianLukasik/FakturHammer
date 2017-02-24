/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fakturhammer;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author user
 */
class FHAplication 
{    
    private JFrame mainFrame;
    private JLabel autorLabel; 
    private ArrayList<Faktura> faktury;
    private JTable tabelka_faktur;
    private DefaultTableModel model;
    private int liczba_plików;
    
    public FHAplication()
    {
    //    prepareGUI();
    }
    
    private void setIcon(String nazwa)
    {
        //ustawienie ikony
        mainFrame.setIconImage(java.awt.Toolkit.getDefaultToolkit().getImage(getClass().getResource(nazwa)));
    }
   
    private void prepareGUI() 
    {        
        mainFrame = new JFrame("FakturHammer");
        mainFrame.setSize(450,475);
        
        mainFrame.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        //ustawienie ikonek
        c.insets = new Insets(4,2,0,2);     
        c.ipady = 0;
        c.weightx = 0.5 ;
        c.weighty = 0.0;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
          
        int[] array =  { 0, 0 }; 
        //
        array[1] = 0;
        stworz_nowa_ikone("wczytaj.png","Wczytaj faktury",c,mainFrame,array).addActionListener( new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                wybierz_folder();
            }
        });
        array[1] = 1;
        stworz_nowa_ikone("wyczyść.png","Wyczyść tabele",c,mainFrame,array).addActionListener( new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                wyczysc_tabelke();
            }
        });
        array[1] = 2;
        stworz_nowa_ikone("napraw.png","Napraw faktury",c,mainFrame,array).addActionListener( new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                napraw_faktury();
            }
        });
        //ustawienie grida  
        c.ipady = 330;     
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 3;
        //ustawienie ikony
        setIcon("ikona.png");                           
        //zamknięcie programu
        mainFrame.addWindowListener(new WindowAdapter() {
           @Override
           public void windowClosing(WindowEvent windowEvent){
              System.exit(0);
           }        
        });          
        
        ////////////////
        //////menu//////
        ////////////////
        JMenuBar menubar = new JMenuBar();        
        JMenu m_faktury = new JMenu("Faktury");
        m_faktury.setMnemonic(KeyEvent.VK_P);        
        menubar.add(m_faktury);        
        ////////////        
        ustaw_pozycje_w_menu("Wybierz faktury","wczytaj.png",m_faktury).addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {           
                wybierz_folder();
            }
        });
        ustaw_pozycje_w_menu("Wyczyść tabele","wyczyść.png",m_faktury).addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {           
                wyczysc_tabelke();
            }
        });    
        ustaw_pozycje_w_menu("Napraw numery w fakturach","napraw.png",m_faktury).addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {           
                napraw_faktury();
            }
        }); 
        ustaw_pozycje_w_menu("Zamknij",null,m_faktury).addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }            
        });
        ///////////
        mainFrame.setJMenuBar(menubar);
        ////////////////
        /////tabela/////
        ////////////////
        
        Object[] tableHeader = new Object[]{"Nazwa pliku","Stan","Ilość znaków"};
        model = new DefaultTableModel(tableHeader, 0);    
        
        
        tabelka_faktur = new JTable(model); 
        
        tabelka_faktur.getColumn("Nazwa pliku").setPreferredWidth(105);           
        tabelka_faktur.getColumn("Stan").setPreferredWidth(75);   
        tabelka_faktur.getColumn("Ilość znaków").setPreferredWidth(5);   
 
        tabelka_faktur.setEnabled(false);
                     
        mainFrame.add(new JScrollPane(tabelka_faktur),c);
              
        c.ipady = 5;      //make this component tall        
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;        
        //autor projektu
        ustaw_autora_projektu("Autor by Damian Łukasik",c);
    }
    
    private void ustaw_autora_projektu(String str,GridBagConstraints c)
    {
        autorLabel = new JLabel(str,JLabel.CENTER); 
        autorLabel.setSize(400,5); 
        autorLabel.setFont(autorLabel.getFont().deriveFont(9.0f));
        //wyświetl
        mainFrame.setVisible(true); 
        mainFrame.add(autorLabel,c);
    }
    
    private JMenuItem ustaw_pozycje_w_menu(String nazwa,String grafika,JMenu menu_) 
    {
        JMenuItem menu = new JMenuItem(nazwa);
        menu.setMnemonic(KeyEvent.VK_W);  
        
        if(grafika!=null)
        {
            ImageIcon i = new ImageIcon((getClass().getResource(grafika)));
            Image image = i.getImage(); // transform it
            Image newimg = image.getScaledInstance(11, 11, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way 
            i = new ImageIcon(newimg);  // transform it back
            menu.setIcon(i);
        }
        menu_.add(menu);        
        
        return menu;    
    }
    
    private JButton stworz_nowa_ikone(String grafika, String nazwa,GridBagConstraints c, JFrame main,int[] lok)
    {
        JButton button = null;
        
        ImageIcon imageIcon = new ImageIcon(getClass().getResource(grafika)); // load the image to a imageIcon
        Image image = imageIcon.getImage(); // transform it
        Image newimg = image.getScaledInstance(27, 27,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way 
        imageIcon = new ImageIcon(newimg);  // transform it back    
        
        button = new JButton(nazwa,imageIcon);
        button.setFont(new Font("Arial", Font.PLAIN, 11));
        
        c.gridy = lok[0];
        c.gridx = lok[1];
        main.add(button,c);
        
        return button;
    }
    
    private String sciezka;

    private void wybierz_folder()
    {
        System.out.println("Wybierz folder");
        //
        JFileChooser fileChooser = new JFileChooser();
        
        wyczysc_tabelke();           
        
        // For Directory
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);

        int rVal = fileChooser.showOpenDialog(null);
        if (rVal == JFileChooser.APPROVE_OPTION) 
        { 
                    
            sciezka = ""+fileChooser.getSelectedFile();
                    
            File folder = new File(sciezka);
            faktury = new ArrayList<Faktura>();
            
            for(File x : folder.listFiles())
            {
                System.out.println(x);
                String[] znak = wykryj_blad(x.getAbsolutePath());
                
                System.out.println("\t"+znak[2]+"\n\t"+x.getName()+"\n\t"+x.getAbsolutePath().replace("\\", "\\"+"\\")+"\n\t"
                        +Integer.parseInt(znak[1])+"\n\t"+znak[0]+"  "+x.getParent());
                
                faktury.add(new Faktura(znak[2],x.getName(),x.getAbsolutePath(),
                        znak[1],znak[0],x.getParent()));
          
                //tu skończyłem
            }
             System.out.println(" = "+sciezka+"  - "+faktury.size());
                 
            for(Faktura x : faktury)
            {
                File file = new File(x.get_ścieżka());
                if (file.isFile() && (file.getName().endsWith(".sent") || file.getName().endsWith(".xml"))) 
                {  
                    String ilosc=x.get_liczba_cyfr();
                    
                    if(x.get_liczba_cyfr().equals("4")){ ilosc="Brak"; }
                    
                    model.addRow(new Object[]{file.getName(), x.get_stan(), ilosc});
                } 
            }
            liczba_plików = faktury.size();
        }
        else 
        {
            System.out.println("Nic nie wybrano  ");
        }       
    }    
    
    private String[] wykryj_blad(String str_)
    {
        File file = new File(str_.replace("\\","\\"+"\\"));
        String[] tab = {"nie jest plikiem","","0"};
        if(!file.isFile()){return tab;}
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
                    System.out.println(" - "+file.getName().substring(file.getName().lastIndexOf(".")+1)+" - ");
                    
                    tab[2]=file.getName().substring(file.getName().lastIndexOf(".")+1);
                    
                    //
                    a = dane.indexOf(">")+1;
                    b = dane.indexOf("</");
                    tab[1] = b-a+"";
                    if(dane.indexOf("brak")==-1)
                    {
                        if(dane.indexOf("_")!=-1)
                        {
                            tab[0] = "Do poprawy";                           
                            return tab;
                        }   
                        else                        
                        {            
                            tab[0] = "Numer jest w porządku " ;
                            return tab;
                        }
                    }
                    else
                    {
                        tab[0] = "Brakuje numeru faktury";
                     //   if(tab[1].equals("4")){tab[1]="Brak";}
                        return tab;
                    }
                }                
            }            
        }
        catch(FileNotFoundException e)
        {
            tab[0] =  "Plik nieistnieje";
            return tab;
        }      
        catch(IOException e)
        {
            tab[0] =  "Błąd odczytu pliku";
            return tab;
        }
        tab[0] =  "?";
        return tab;        
    }
   
    private void wyczysc_tabelke() 
    {        
        if(faktury!=null){
            int rowCount = model.getRowCount();            
            for (int i = rowCount - 1; i >= 0; i--) {
                model.removeRow(i);
            }    
            faktury.clear(); 
            System.out.println("Wyczyszczono tabele");          
        }
        else
        {
            System.out.println("Nie ma tabeli"); 
        }
    }
    
    int k=1;
    //Charset
    Charset ch_pl = Charset.forName("UTF-8"); 
    Charset ch_de = Charset.forName("iso-8859-1");
    Charset ch_wn = Charset.forName("windows-1250");
    
    private void napraw_faktury()  
    {         
        System.out.println("Naprawiam faktury");
        if(faktury!=null)
        {
            ArrayList<String> lista = new ArrayList<String>();

            try 
            {        
                int rowCount = model.getRowCount(); 
                for (int i = rowCount - 1; i >= 0; i--) 
                {
                    String str_ = faktury.get(i).get_ścieżka();
                    
                    
                    File txt = new File(str_);
                    if(!txt.isFile()){continue;}
                    String dane = null;
                    BufferedReader odczytaj = new BufferedReader(new InputStreamReader(new FileInputStream(txt),ch_pl));      
                     
                    String str_2;
                    if(faktury.get(i).get_rozszerzenie().equals("xml_b"))
                    {
                        str_2 = faktury.get(i).get_ścieżka().substring(0, str_.indexOf("."))+".xml_b";
                    }
                    else
                    {
                        str_2 = faktury.get(i).get_ścieżka().substring(0, str_.indexOf("."))+".xml";
                    }   
                    while (((dane = odczytaj.readLine()) != null )) {
                        
                        int a = 0,b = 0;

                        if(dane.contains("<BuyerOrderNumber>"))
                        {                   
                            a = dane.indexOf(">")+1;
                            if(!dane.contains("brak"))
                            {
                                if(dane.contains("_"))
                                {
                                    b = dane.indexOf("_")+1;      

                                    String str = dane.substring(0,a) + dane.substring(b);
                                    dane = str;
                                    b = (dane.indexOf("</"))-(dane.indexOf(">")+1);
                                    model.setValueAt(b+"", i, 2);
                                    model.setValueAt("Poprawione", i, 1);    
                                }           
                            }
                        }
                        lista.add("\n"+dane);
                    }
                    odczytaj.close();
                                        
                    lista.set(0, "<Document-Invoice>");
                    System.out.println(" == > "+str_2);                        
                    FileWriter fw = new FileWriter(str_2); 
                    BufferedWriter bw = new BufferedWriter(fw);                   
                    for (String line : lista) 
                    {                   
                        bw.write(line);                            
                            //oznaczenie krzyżykiem
                    }                    
                    bw.close();
                    fw.close();
                     
                    odczytaj=null;
                    lista.clear();
                    
                    Thread.sleep(1);
                    File fileDelete = new File(faktury.get(i).get_ścieżka());
                    if((faktury.get(i).get_nazwa().contains(".xml.sent")) 
                        ||
                       (faktury.get(i).get_nazwa().contains(".sent")))
                    {  
                        Thread.sleep(5);
                        fileDelete.delete();
                        System.out.println(" -- "+fileDelete.delete());
                        Thread.sleep(5);
                        if(fileDelete.delete())
                        {  
                            fileDelete.delete(); 
                        }
                    }
                }                
                //MLOT
                Thread.sleep(50); 
                if(liczba_plików<new File(sciezka).list().length)
                {    
                    System.out.println(liczba_plików+"  "+new File(sciezka).list().length);
                  //  Thread.sleep(30); 
                    Usun_niepotrzebnePliki();                
                }      
                Thread.sleep(100); 
                if(liczba_plików<new File(sciezka).list().length)
                {    
                    System.out.println(liczba_plików+"  "+new File(sciezka).list().length);
                    //Thread.sleep(30); 
                    Usun_niepotrzebnePliki();                
                }
            }
            catch (IOException | InterruptedException e)
            {
                System.err.println(e);
            }        
  //      faktury=null;            
        }
        else
        {
            System.out.println("Nie ma faktur do naprawy");
        }
    }

    private void Usun_niepotrzebnePliki() throws IOException, InterruptedException {
  
        System.out.println("Naprawiam faktury");
        if(faktury!=null)
        {
            Thread.sleep(10);
            faktury.clear();  
            File file = new File(sciezka);
            String files[] = file.list();          
            for (String temp : files) 
            {
                Thread.sleep(2);
                File fileDelete = new File(sciezka.replace("\\", "\\"+"\\"), temp);
                if((temp.contains(".xml.sent")) || (temp.contains(".sent")))
                {
                    Thread.sleep(5); 
                    fileDelete.delete(); 
                    Thread.sleep(5); 
                    if(fileDelete.delete())
                    {  
                        fileDelete.delete(); 
                    }
                }  
            }
            Thread.sleep(10); 
            System.out.println("Usunięto niepotrzebne pliki");
        }        
    }
}
