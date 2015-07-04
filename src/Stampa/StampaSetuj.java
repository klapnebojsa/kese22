/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Stampa;

import Class.Apstraktne.AbstractDAO;
import Class.DAO.BrokerDAO;
import Class.KlaseBaze.Margine;
import Class.Povezivanje.Procitaj;
import Class.Povezivanje.Setuj;
import Forme.FormForme;
import Forme.FormPrintPreview;
import Forme.Konstante.Mere;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterJob;
import java.util.List;

/**
 *
 * @author Nebojsa
 */
public class StampaSetuj {
    
    public void SetujPageSetup(List mojeMargine, PageFormat pageFormat, PrinterJob pj, FormPrintPreview formPrintPreview, FormForme koZove) throws Exception{
        
        //Postavljenje Sifre Margina
        Procitaj emp = new Procitaj();
        Setuj set = new Setuj(koZove.getBrokerDAO());               
        String sifraMargine = emp.ProcitajSifruMargina(koZove.getBrokerDAO());
        
        //CitanjeMargina za dobijenu sifru i klasu Margine
        AbstractDAO klasa = new Margine();
        BrokerDAO brokerDAO = new BrokerDAO(klasa);
        set = new Setuj(brokerDAO);                    
        set.SetujSifruMargina(sifraMargine);
        mojeMargine = emp.ProcitajJedanxx(brokerDAO);
        
        Double mLeft=null;
        Double mRight=null;
        Double mTop=null;
        Double mDown=null;
        
        for(Object category : mojeMargine) {
            List element = (List)category;   
            mLeft = Double.parseDouble(element.get(1).toString());
            mRight = Double.parseDouble(element.get(2).toString());            
            mTop = Double.parseDouble(element.get(3).toString());
            mDown = Double.parseDouble(element.get(4).toString());                              
        }
        Mere mere = new Mere();
        Double preracun = mere.getMmPageFormat() / 10;               
        pageFormat = pj.defaultPage();
        Paper paper = pageFormat.getPaper();
        paper.setImageableArea(mLeft*preracun, mTop*preracun, paper.getWidth()-(mRight+mLeft)*preracun, paper.getHeight()-(mDown+mTop)*preracun);
        pageFormat.setPaper(paper);
        formPrintPreview.setPageFormat(pageFormat);

        pj.setPrintable(null, pj.defaultPage(pageFormat));          
    }
    //kkkkkkkkkkkk
}
