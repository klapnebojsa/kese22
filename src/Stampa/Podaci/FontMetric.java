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
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
  
 
public class FontMetric extends Frame {
    double visinaFonta;
    Font font; 
    private String ms;

    public FontMetric(Font font) {
        String text = "ŽĐŽĐjjzzyy{}";
        AffineTransform affinetransform = new AffineTransform();     
        FontRenderContext frc = new FontRenderContext(affinetransform,true,true);     
        //int textwidth = (int)(font.getStringBounds(text, frc).getWidth());

        int textheight = (int)(font.getStringBounds(text, frc).getHeight());
        setVisinaFonta(textheight);        
    }

    public void setVisinaFonta(double visinaFonta){
        this.visinaFonta = visinaFonta;
    }
    public double getVisinaFonta(){
        return visinaFonta;
    }
}
