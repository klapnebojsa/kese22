/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Stampa;

import Forme.FormPrintPreview;
import Sistem.OsnovneDefinicije.RezolucijaEkrana;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;
import javax.swing.JComponent;

/**
 *
 * @author Nebojsa
 */
public class Prikazi extends JComponent implements Printable { 
    double p = 1;
    private int trenutniRbrStrane;
    private Vector lineVector;
    private Vector pageVector;

    private Font font;
    private int fontSize;
    private Dimension preferredSize;
    PageFormat pageFormat;
    FormPrintPreview formPrintPreview;
    boolean jesteStampa;

    public Prikazi(File file, PageFormat pageFormat, FormPrintPreview formPrintPreview) throws IOException {
        this.pageFormat=pageFormat;     
        this.formPrintPreview = formPrintPreview;
        double r = 12 * p;        
        fontSize = (int) r;
        font = new Font("Serif", Font.PLAIN, fontSize);
        BufferedReader in = new BufferedReader(new FileReader(file));
        String line;
        lineVector = new Vector();
        while ((line = in.readLine()) != null) lineVector.addElement(line);
        in.close();
        pageInit(pageFormat);
        formPrintPreview.showTitle(this);
      
        addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                double delta = 0.1f * e.getPreciseWheelRotation();
                p += delta;
                //max i min preview
                p=(p<0.8)? 0.8:p;
                p=(p>10)? 10:p;
                revalidate();
                repaint();
            }
        });
    }

    public void pageInit(PageFormat pageFormat) { 
        trenutniRbrStrane = 0;
        pageVector = new Vector();
        float y = fontSize;
        Vector page = new Vector();
        for (int i = 0; i < lineVector.size(); i++) {
            String line = (String) lineVector.elementAt(i);
            page.addElement(line);
            y += fontSize;
            //Kontrola kada je nova strana
            if (y + fontSize * 2 > pageFormat.getImageableHeight()) {
                y = 0;
                pageVector.addElement(page);
                page = new Vector();
            }
        }
        if (page.size() > 0) pageVector.addElement(page);
        preferredSize = new Dimension((int) pageFormat.getImageableWidth(), (int) pageFormat.getImageableHeight());
        revalidate();
        repaint();
    }
    public void paintComponent(Graphics g){
        if (getJesteStampa()){previewStampac(g);
        }else{                previewEkran(g);}
    }
    
    //Pregled EKRAN
    public void previewEkran(Graphics g) {
        double rF = 12 * p + 0.5;        
        fontSize = (int) rF;
        font = new Font("Serif", Font.PLAIN, fontSize);
        RezolucijaEkrana re = new RezolucijaEkrana();
        Dimension fullScr = re.FullScreen();
        double pX = preferredSize.width*p;  
        int xC = (fullScr.width - (int)pX) / 2;
        double pY = preferredSize.height*p*(1+(p-1)*0.8);
        int yC = (fullScr.height - (int)pY) / 2;
        yC = 0;
        Graphics2D g2D = (Graphics2D) g;
        java.awt.geom.Rectangle2D r = new java.awt.geom.Rectangle2D.Float(xC, yC,(int)pX, (int)pY);
        g2D.setPaint(Color.white);
        g2D.fill(r);
        Vector page = (Vector) pageVector.elementAt(trenutniRbrStrane);

        g2D.setFont(font);
        g2D.setPaint(Color.black);
        float x = 0;
        float y = fontSize;
        for (int i = 0; i < page.size(); i++) {
          String line = (String) page.elementAt(i);
          if (line.length() > 0)
            pX = x * p;
            pY = y * p;          
            g2D.drawString(line, (int)pX + xC, (int)pY + yC);
          y += fontSize;
        }
    }
    //Stampa STAMPAC
    public void previewStampac(Graphics g) {   
      Graphics2D g2D = (Graphics2D) g;
      java.awt.geom.Rectangle2D r = new java.awt.geom.Rectangle2D.Float(0, 0,
          preferredSize.width, preferredSize.height);
      g2D.setPaint(Color.white);
      g2D.fill(r);
      Vector page = (Vector) pageVector.elementAt(trenutniRbrStrane);

      g2D.setFont(font);
      g2D.setPaint(Color.black);
      float x = 0;
      float y = fontSize;
      for (int i = 0; i < page.size(); i++) {
        String line = (String) page.elementAt(i);
        if (line.length() > 0)
          g2D.drawString(line, (int) x, (int) y);
        y += fontSize;
      }
    }
    
    public int print(Graphics g, PageFormat pageFormat, int rbrStrane) {
        if (rbrStrane >= pageVector.size()) return NO_SUCH_PAGE;
        int savedPage = trenutniRbrStrane;
        trenutniRbrStrane = rbrStrane;
        Graphics2D g2D = (Graphics2D) g;
        g2D.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
        paint(g2D);
        trenutniRbrStrane = savedPage;
        return PAGE_EXISTS;
    }
  
    public Dimension getPreferredSize() {
      return preferredSize;
    }

    public int getTrenutnatPage() {
      return trenutniRbrStrane;
    }

    public int getNumPages() {
      return pageVector.size();
    }

    public void sledecaStrana() {
      if (trenutniRbrStrane < pageVector.size() - 1) trenutniRbrStrane++;
          repaint();
    }

    public void prethodnaStrana() {
      if (trenutniRbrStrane > 0) trenutniRbrStrane--;
        repaint();
    }
    public void poslednjaStrana() {
        trenutniRbrStrane=pageVector.size() - 1;
        repaint();
    }
    public void prvaStrana() {
        trenutniRbrStrane=0;
        repaint();
    }
    public void setJestStampa(boolean jesteStampa){
        this.jesteStampa = jesteStampa;
    }
    public boolean getJesteStampa(){
        return jesteStampa;
    }
  }  