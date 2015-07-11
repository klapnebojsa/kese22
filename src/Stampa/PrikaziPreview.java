/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Stampa;

import Forme.FormPrintPreview;
import Sistem.OsnovneDefinicije.RezolucijaEkrana;
import Stampa.Podaci.FontMetric;
import Stampa.Podaci.Konstante;
import Stampa.Podaci.PoljeZaStampu;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.Rectangle2D;
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
public class PrikaziPreview extends JComponent implements Printable { 
    double p = 1;
    private int trenutniRbrStrane;
    private PoljeZaStampu poljeZaStampu;
    private Vector lineVector;
    private Vector pageVector;
    
    private Font font;
    private int fontSize;
    private Dimension preferredSize;
    
    private Dimension pocetakPage;
    private double sirinaPage;
    private double visinaPage;
    
    PageFormat pageFormat;
    FormPrintPreview formPrintPreview;
    boolean jesteStampa;
    private double visinaNovo;
    
    PreviewPrint previewPrint;

    public PrikaziPreview(File file, PageFormat pageFormat, FormPrintPreview formPrintPreview) throws IOException {
        this.pageFormat=pageFormat;     
        this.formPrintPreview = formPrintPreview;
        double r = 12 * p;        
        fontSize = (int) r;
        font = new Font("Serif", Font.PLAIN, fontSize);

        String line;
        lineVector = new Vector();
        BufferedReader in = new BufferedReader(new FileReader(file));
        while ((line = in.readLine()) != null) {
            PoljeZaStampu poljeZaStampu = new PoljeZaStampu();
            //OVDE SETOVATI I OSTALE PARAMETRE MARGINE, VELICINU FONTA ...
            poljeZaStampu.setLeftLine(true);            
            poljeZaStampu.setDownLine(true);
            poljeZaStampu.setRightLine(true);
            poljeZaStampu.setTopLine(true);
            
            poljeZaStampu.setVrednost(line);
            lineVector.addElement(poljeZaStampu);
        }               
        in.close();
        
        pageInit(pageFormat);
        formPrintPreview.showTitle(this);
      
        addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                Konstante konstante = new Konstante();
                p = konstante.MouseStep(e, p);
                revalidate();
                repaint();  
            }
        });
    }
    public void pageInit(PageFormat pageFormat) { 
        trenutniRbrStrane = 0;
        float y = 0;        
        pageVector = new Vector();
        Vector <PoljeZaStampu> pageXX = new Vector();
        for (int i = 0; i < lineVector.size(); i++) {
            PoljeZaStampu line = (PoljeZaStampu) lineVector.elementAt(i);
            pageXX.addElement(line);
            
            FontMetric fontMetric = new FontMetric(new Font("Serif", Font.PLAIN, fontSize));
            double visina = fontMetric.getVisinaFonta();
            
            y += visina;
            //Kontrola kada je nova strana
            if (y + visina * 2 > pageFormat.getImageableHeight()) {
                y = 0;
                pageVector.addElement(pageXX);
                pageXX = new Vector();
            }
        }
        if (pageXX.size() > 0) pageVector.addElement(pageXX);        
        
        preferredSize = new Dimension((int) pageFormat.getImageableWidth(), (int) pageFormat.getImageableHeight());
        revalidate();
        repaint();
    }

    public void paintComponent(Graphics g){
        pocetakPage = new Dimension();
       
        g.setFont(new Font("Serif", Font.PLAIN, fontSize));        
        /*FontMetrics fm = g.getFontMetrics();
        double visinaPrethodno = fm.getMaxAscent() + fm.getMaxDecent();*/
        
        FontMetric fontMetric = new FontMetric(new Font("Serif", Font.PLAIN, 12));
        double visinaPrethodno = fontMetric.getVisinaFonta();
            
        if (getJesteStampa()){
            //STAMPA
            setSirinaPage(preferredSize.width);
            pocetakPage.width = 0;
            pocetakPage.height = 0;
            setVisinaPage(preferredSize.height);
            fontSize = 12;  
        }else{
            //PREVIEW
            RezolucijaEkrana re = new RezolucijaEkrana();
            Dimension fullScr = re.FullScreen();
            fontSize = (int) (12 * p + 0.5); 
            
            g.setFont(new Font("Serif", Font.PLAIN, fontSize));        
            /*fm = g.getFontMetrics();
            visinaNovo = fm.getMaxAscent() + fm.getMaxDecent();*/
            
            fontMetric = new FontMetric(new Font("Serif", Font.PLAIN, fontSize));
            visinaNovo = fontMetric.getVisinaFonta();
            
            //Odredjivanje koefcijenta povecanja/smanjenja strane u odnosu na visinu reda teksta
            double p = visinaNovo / visinaPrethodno;
            setSirinaPage(preferredSize.width*p);
            pocetakPage.width = (fullScr.width - (int)getSirinaPage()) / 2;
            pocetakPage.height = 0;
            setVisinaPage(preferredSize.height*p);
        }
        previewPrint = new PreviewPrint();
        previewPrint.Prikazi(this, pageVector, g);          
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
    
    public void setPocetakPage(Dimension pocetakPage){
        this.pocetakPage = pocetakPage;
    }
    public Dimension getPocetakPage(){
        return pocetakPage;
    }

    public void setSirinaPage(double sirinaPage){
        this.sirinaPage = sirinaPage;
    }
    public double getSirinaPage(){
        return sirinaPage;
    }
    
    public void setVisinaPage(double visinaPage){
        this.visinaPage = visinaPage;
    }
    public double getVisinaPage(){
        return visinaPage;
    }
    
    public void setFont(Font font){
        this.font = font;
    }
    public Font getFont(){
        return font;
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