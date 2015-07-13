/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Stampa.Podaci;

/**
 *
 * @author Nebojsa
 */
public class PoljeZaStampu {
    String vrednost;
    
    String alignment;    
    int velicinaFonta;

    boolean leftLine;
    boolean downLine;
    boolean rightLine;
    boolean topLine;

    public void setVrednost(String vrednost){
        this.vrednost = vrednost;
    } 
    public String getVrednost(){
        return vrednost;
    }
    
    public void setAlignment(String alignment){
        this.alignment = alignment;
    }
    public String getAlignment(){
        return alignment;
    } 
    
    public void setLeftLine(boolean leftLine){
        this.leftLine = leftLine;
    }
    public boolean getLeftLine(){
        return leftLine;
    }
    
    public void setDownLine(boolean downLine){
        this.downLine = downLine;
    }
    public boolean getDownLine(){
        return downLine;
    }

    public void setRightLine(boolean rightLine){
        this.rightLine = rightLine;
    }
    public boolean getRightLine(){
        return rightLine;
    }
    
    public void setTopLine(boolean topLine){
        this.topLine = topLine;
    }
    public boolean getTopLine(){
        return topLine;
    } 
    
}
