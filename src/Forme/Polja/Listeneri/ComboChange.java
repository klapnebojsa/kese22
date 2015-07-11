/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Forme.Polja.Listeneri;

import Forme.Konstante.FunkcijskiTasteri;
import Forme.Polja.Prikazi.PoljaIzTabeleDefinicija;
import Forme.Polja.Prikazi.PoljaIzTabeleNapuni;
import Forme.PopUpovi.TablePopUp;
import Forme.Tabele.MojaTabela;
import Stampa.PreviewMenuBar;
import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuItem;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
/**
 *
 * @author Nebojsa
 */
public class ComboChange implements ItemListener {
    JMenuItem menuItemNovi;
    JMenuItem menuItemIzmeni;
    PreviewMenuBar previewMenuBar; 

    public ComboChange(PreviewMenuBar previewMenuBar) {
        this.previewMenuBar = previewMenuBar;
    }

    Object vrednost = null;
    public void itemStateChanged(ItemEvent event) {
       if (event.getStateChange() == ItemEvent.SELECTED) {
          vrednost = event.getItem();
          try {
            previewMenuBar.tekuceUvecanje.setText(vrednost.toString());
            try{
                vrednost = vrednost.toString().replace(" %", "");
                previewMenuBar.formPrintPreview.prikazi.p = Double.parseDouble(vrednost.toString()) / 100;                
            }catch(Exception e){
                Dimension prefSize = previewMenuBar.formPrintPreview.prikazi.getPreferredSize();
                switch (vrednost.toString()){
                        case "page Width":
                            previewMenuBar.formPrintPreview.prikazi.p = 1920. / prefSize.width;
                            break;
                        case "page Height":
                            previewMenuBar.formPrintPreview.prikazi.p = 1080. / prefSize.height;                            
                            break;
                }
            }
            previewMenuBar.formPrintPreview.revalidate();
            previewMenuBar.formPrintPreview.repaint();   
          }catch(Exception e1){}
       }
    }
}
