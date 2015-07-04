package Stampa;

import Forme.FormPrintPreview;
import Forme.FormForme;
import Forme.PopUpovi.PopUp;
import Forme.Tabele.MojaTabela;
import java.awt.BorderLayout;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Nebojsa
 */
public class Pripremi {
    MojaTabela mt1;
    FormForme koZove;
    boolean ispravnaPriprema;
    
    public Pripremi(MojaTabela mt1, FormForme koZove){
        this.mt1 = mt1;
        this.koZove = koZove;
        ispravnaPriprema =true;
    }
    
    public void StampuTabele() throws IOException, Exception{
        
                // Pop up a file dialog.
        JFileChooser fc = new JFileChooser(".");
        int result = fc.showOpenDialog(koZove);
        if (result != 0) {
            return;
        }
        java.io.File f = fc.getSelectedFile();
        if (f == null) {
            return;
        }
        //Ovde odraditi pripremu Stampe umesto Pop up file dialog-a
        
        if (ispravnaPriprema){
            FormPrintPreview formPrintPreview = new FormPrintPreview(mt1, koZove, f);
            formPrintPreview.Prikazi();
 
        }else{
            PopUp popUp = new PopUp(koZove, "NE USPESNA PRIPREMA!");
            popUp.Ok();            
        } 
        
    } 
    
}
