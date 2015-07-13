/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Forme;

import Forme.Konstante.Mere;
import Forme.Tabele.MojaTabela;
import Sistem.OsnovneDefinicije.RezolucijaEkrana;
import Stampa.PrikaziPreview;
import Stampa.PreviewMenuBar;
import Stampa.StampaSetuj;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

/**
 *
 * @author Nebojsa
 */
public class FormPrintPreview extends JFrame implements ActionListener{
    MojaTabela mt1;
    FormForme koZove;
    boolean ispravanPrikaz = true;
    File f;
    PageFormat pageFormat;
    List mojeMargine;
    public PrikaziPreview prikazi;
    public PreviewMenuBar stampaMenuBar;
    PrinterJob pj;
    public StampaSetuj stampaSetuj;
    
    public FormPrintPreview (MojaTabela mt1, FormForme koZove, File f){
        super();
        this.mt1 = mt1;
        this.koZove = koZove;
        this.f = f;
    }
    public void Prikazi() throws IOException, Exception{
        koZove.setEnabled(false);
        
        //setTitle("Print Preview - " + koZove.getOpisForme()); 
        
        //rezolucija
        RezolucijaEkrana re = new RezolucijaEkrana();
        Dimension fullScr = re.FullScreen();
        
        //Velicina Forme (Cela Strana)
        setSize(fullScr);
        setLocationRelativeTo(null);
        
        //Ovde ucitati format iz baze
        pj = PrinterJob.getPrinterJob();
        pageFormat = pj.defaultPage();

        //Postavljanje vrednosti Margina u PrinterJob.pageDialog
        stampaSetuj = new StampaSetuj();
        stampaSetuj.SetujPageSetup(pageFormat, pj, this, koZove);
        
        //Preview Strane
        prikazi = new PrikaziPreview(f, pageFormat, this);
        add(new JScrollPane(prikazi), BorderLayout.CENTER);
        
        //Button u vrhu strane - Stampa, prethodni, sledeci
        stampaMenuBar = new PreviewMenuBar(true, this);
        add(stampaMenuBar, BorderLayout.NORTH);        
        
        setVisible(true);       
        
        //Listner-i ------------------------------------------------------------------------------------------ 
        // X-Za zatvaranje forme
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                koZove.setEnabled(true);
            }
        });
    }
    
    public void showTitle(PrikaziPreview prikazi) {
        int currentPage = prikazi.getTrenutnatPage() + 1;
        int numPages = prikazi.getNumPages();
        setTitle("Print Preview - " + koZove.getOpisForme() +  " strana " + currentPage + " od " + numPages);
        try{stampaMenuBar.tekucaStrana.setText("" + currentPage);
        }catch(Exception e){} 
    }
    
    public void preracunajSirinu(String vrednost){
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String a = e.getActionCommand();
        PrinterJob pj = PrinterJob.getPrinterJob();        
        switch (a){
            //Izbor Formata Strane            
            case "pageSetupButton":
                Mere mere = new Mere();
                Double preracun = mere.getMmPageFormat() / 10;
                pageFormat = pj.pageDialog(pageFormat);
                //Setovanje koordinata, orijentacije ... - POLJA IZ MARGINA            
                stampaSetuj.setMLeft((int)(pageFormat.getImageableX() / preracun));
                stampaSetuj.setMRight((int)((pageFormat.getWidth() - pageFormat.getImageableX() - pageFormat.getImageableWidth() + 0.5) / preracun));                 
                stampaSetuj.setMTop((int)(pageFormat.getImageableY() / preracun));
                stampaSetuj.setMDown((int)((pageFormat.getHeight() - pageFormat.getImageableY() - pageFormat.getImageableHeight() + 0.5) / preracun));
                if (prikazi != null) prikazi.pageInit(pageFormat);
                break;
            case "nextButton":
                if (prikazi != null) prikazi.sledecaStrana(); 
                break;
            case "previousButton":
                if (prikazi != null) prikazi.prethodnaStrana(); 
                break;                
            case "lastButton":
                if (prikazi != null) prikazi.poslednjaStrana();
                break;                
            case "firstButton":
                if (prikazi != null) prikazi.prvaStrana();
                break; 
            case "printButton":
                PrintService[] stampaci = PrinterJob.lookupPrintServices();
                DocPrintJob docPrintJob = null;
                
                for (PrintService printer : stampaci){
                    if (printer.getName().equalsIgnoreCase(stampaSetuj.getMStampac())) {docPrintJob = printer.createPrintJob(); break;}
                }
                try {pj.setPrintService(docPrintJob.getPrintService());
                } catch (PrinterException ex) { Logger.getLogger(FormPrintPreview.class.getName()).log(Level.SEVERE, null, ex);}

                pj.setPrintable(prikazi, pageFormat);
                if (pj.printDialog()) {
                    prikazi.setJestStampa(true);
                    prikazi.setBrKopija(pj.getCopies());
                    try {pj.print();                            
                    } catch (PrinterException e1) {}
                    prikazi.setBrKopija(1);
                    prikazi.setJestStampa(false);
                }
                break;                                  
            default:
                break;
        }
        if (prikazi != null) showTitle(prikazi);
    }
    public void setPageFormat(PageFormat pageFormat){
        this.pageFormat = pageFormat;
    }

}
