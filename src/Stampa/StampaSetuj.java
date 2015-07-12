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
    int mLeft=25;
    int mRight=25;
    int mTop=25;
    int mDown=25;
    List mojeMargine;
    Double preracun;
    
    public void SetujPageSetup(PageFormat pageFormat, PrinterJob pj, FormPrintPreview formPrintPreview, FormForme koZove) throws Exception{
        
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
        
        //Setovanje podataka iz tabele MARGINE
        for(Object category : mojeMargine) {
            List element = (List)category;   
            mLeft = Integer.parseInt(element.get(1).toString());
            mRight = Integer.parseInt(element.get(2).toString());          
            mTop = Integer.parseInt(element.get(3).toString());
            mDown = Integer.parseInt(element.get(4).toString());
            
            
        }
        Mere mere = new Mere();
        preracun = mere.getMmPageFormat() / 10;               
        pageFormat = pj.defaultPage();
        Paper paper = pageFormat.getPaper();
        paper.setImageableArea(mLeft*preracun, mTop*preracun, paper.getWidth()-(mRight+mLeft)*preracun, paper.getHeight()-(mDown+mTop)*preracun);
        pageFormat.setPaper(paper);
        formPrintPreview.setPageFormat(pageFormat);

        pj.setPrintable(null, pj.defaultPage(pageFormat));
    }

    //GET
    public double getMLeft(){
        return mLeft*preracun;
    }
    public double getMRight(){
        return mRight*preracun;
    }
    public double getMTop(){
        return mTop*preracun;
    }
    public double getMDown(){
        return mDown*preracun;
    }
    
    //SET IZ FormPrintPreview - kada se promene koordinate
    //Promena nije trajna vec samo za tu odredjenu stampu
    //Ostala polja se ne setuju zato sto se ona menjaju u PRINT FORMI i oni se setuju samo pri ucitavanju iz tabele MARGINE (postoji samo get ali ne i set iz drugr klase)
    public void setMLeft(int mLeft){
        this.mLeft = mLeft;
    }
    public void setMRight(int mRight){
        this.mRight = mRight;
    }
    public void setMTop(int mTop){
        this.mTop = mTop;
    }
    public void setMDown(int mDown){
        this.mDown = mDown;
    }
}
