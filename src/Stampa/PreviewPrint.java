/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Stampa;

import Forme.Konstante.Mere;
import Stampa.Podaci.FontMetric;
import Stampa.Podaci.PoljeZaStampu;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.util.Vector;

/**
 *
 * @author Nebojsa
 */
public class PreviewPrint {
    //int fontSize;
    double visinaFonta;
    Font font;
    Graphics2D g2D;
    double medjY;
    double medjYDw;    
    double medjX;
    PrikaziPreview prikazi;
    double p;
    public PreviewPrint(PrikaziPreview prikazi, double p){
        this.p = p;        
        this.prikazi = prikazi;
        medjY = prikazi.formPrintPreview.stampaSetuj.getMMedjY();
        medjYDw = prikazi.formPrintPreview.stampaSetuj.getMMedjYDw();        
        medjX = prikazi.formPrintPreview.stampaSetuj.getMMedjX();
    }
    public void Prikazi(Vector pageVector, Graphics g){
        font = g.getFont();

        FontMetrics fm = g.getFontMetrics();
        //visinaFonta = fm.getMaxAscent() + fm.getMaxDecent();*/
        FontMetric fontMetric = new FontMetric(g.getFont());
        visinaFonta = fontMetric.getVisinaFonta();
            
        Dimension pocetakPage = prikazi.getPocetakPage();

        int sirinaPage = (int)prikazi.getUkupnoSize().width;         
        int visinaPage = (int)prikazi.getUkupnoSize().height; 
        
        int sirinaPreffered = (int) prikazi.getPreferredSize().width;
        int visinaPreffered = (int) prikazi.getPreferredSize().height;
       
        java.awt.geom.Rectangle2D r = new java.awt.geom.Rectangle2D.Float (pocetakPage.width, pocetakPage.height, sirinaPage, visinaPage);        

        Vector <PoljeZaStampu> page = (Vector) pageVector.elementAt(prikazi.getTrenutnatPage());

        g2D = (Graphics2D) g;        
        g2D.setPaint(Color.white);
        g2D.fill(r);        
        g2D.setPaint(Color.black);
        g2D.setFont(font);
        
        double mLeft = prikazi.formPrintPreview.stampaSetuj.getMLeft();
        double mRight = prikazi.formPrintPreview.stampaSetuj.getMRight();
        //double trenutnoY = visinaFonta + prikazi.formPrintPreview.stampaSetuj.getMTop();
        double trenutnoY = prikazi.formPrintPreview.stampaSetuj.getMTop();
        
        for (int i = 0; i < page.size(); i++) {
            String line = (String) page.elementAt(i).getVrednost();
            //if (line.length() > 0){
                double pX = mLeft * p;
                double pY = trenutnoY + medjY*p;
                
                int xLf = (int)pX + pocetakPage.width;
                int xRg = pocetakPage.width  + (int)(sirinaPage - (mLeft + mRight) * p);
                int yUp = (int)(pY + pocetakPage.height - (medjY*p + visinaFonta));
                int yDw = (int)(pY + pocetakPage.height + (medjYDw*p));
                
                //Text
                AffineTransform affinetransform = new AffineTransform();     
                FontRenderContext frc = new FontRenderContext(affinetransform,true,true);     
                int textwidth = (int)(font.getStringBounds(line, frc).getWidth() + 0.5);             
                switch (page.elementAt(i).getAlignment()){          
                    case "Left":
                        g2D.drawString(line, (int)pX + pocetakPage.width + (int)medjX, (int)pY + pocetakPage.height - fm.getMaxDecent());
                        break;
                    case "Right":
                        double x = xRg - textwidth - medjX*p;
                        g2D.drawString(line, (int)x, (int)pY + pocetakPage.height - fm.getMaxDecent());
                        break;
                    case "Center":
                        double x1 = xLf + (xRg - xLf  - textwidth)/2;
                        g2D.drawString(line, (int)x1, (int)pY + pocetakPage.height - fm.getMaxDecent());
                        break;                        
                }

                //Linije tabele
                g2D.setStroke(new BasicStroke(1));
                if (page.elementAt(i).getDownLine())  g2D.drawLine(xLf, yDw, xRg, yDw);
                if (page.elementAt(i).getTopLine())   g2D.drawLine(xLf, yUp, xRg, yUp); 
                if (page.elementAt(i).getLeftLine())  g2D.drawLine(xLf, yUp, xLf, yDw);
                if (page.elementAt(i).getRightLine()) g2D.drawLine(xRg, yUp, xRg, yDw); 
                trenutnoY += visinaFonta + medjY*p + medjYDw*p;                                          
            //}
        }            
    }
}
