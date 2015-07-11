/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Stampa;

import Stampa.Podaci.PoljeZaStampu;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Vector;

/**
 *
 * @author Nebojsa
 */
public class PreviewPrint {
    //int fontSize;
    int visinaFonta;
    Font font;
    Graphics2D g2D;
    public void Prikazi(PrikaziPreview prikazi,  Vector pageVector, Graphics g){
        font = prikazi.getFont();        

        FontMetrics fm = g.getFontMetrics();
        visinaFonta = fm.getMaxAscent() + fm.getMaxDecent();

        Dimension pocetakPage = prikazi.getPocetakPage();
        int visinaPage = (int)prikazi.getVisinaPage();        
        int sirinaPage = (int)prikazi.getSirinaPage(); 
        
        java.awt.geom.Rectangle2D r = new java.awt.geom.Rectangle2D.Float (pocetakPage.width, pocetakPage.height, sirinaPage, visinaPage);        

        Vector <PoljeZaStampu> page = (Vector) pageVector.elementAt(prikazi.getTrenutnatPage());

        g2D = (Graphics2D) g;        
        g2D.setPaint(Color.white);
        g2D.fill(r);        
        g2D.setPaint(Color.black);
        double x = 0;
        double y = visinaFonta;
        for (int i = 0; i < page.size(); i++) {
            String line = (String) page.elementAt(i).getVrednost();
            if (line.length() > 0){
                double pX = x;
                double pY = y;          
                g2D.drawString(line, (int)pX + pocetakPage.width, (int)pY + pocetakPage.height - fm.getMaxDecent());
                if (page.elementAt(i).getDownLine()) 
                    g2D.drawLine((int)pX + pocetakPage.width, (int)pY + pocetakPage.height, 
                                 (int)pX + pocetakPage.width + sirinaPage, (int)pY + pocetakPage.height);
                y += visinaFonta;                
            }
        }            
    }
}
