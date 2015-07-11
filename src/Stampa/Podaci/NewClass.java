/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Stampa.Podaci;


import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import javax.swing.JComponent;

/**
 *
 * @author Nebojsa
 */
public class NewClass extends JComponent{
    double visinaFonta;
    Font font;
    
    public NewClass(Font font) {
        super();
        this.font = font;
        setSize(50,50);
        setVisible(true);

    }
    public void preracunaj(){
        this.setVisible(true);
        repaint();        
    }
    
    public void paint(Graphics g){
        FontMetrics fm = g.getFontMetrics();
        g.setFont(font);        
        fm = g.getFontMetrics();
        setVisinaFonta(fm.getMaxAscent() + fm.getMaxDecent());       
    }
    
    public void setVisinaFonta(double visinaFonta){
        this.visinaFonta = visinaFonta;
    }
    public double getVisinaFonta(){
        return visinaFonta;
    }

}

