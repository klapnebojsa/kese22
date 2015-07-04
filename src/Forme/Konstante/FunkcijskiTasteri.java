/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Forme.Konstante;

import java.awt.event.KeyEvent;

/**
 *
 * @author Nebojsa
 */
public class FunkcijskiTasteri {

    //Novu F1
    public int getFtNovi() {  //F3
        return KeyEvent.VK_F3;
    }

    //Ispravi F2
    public int getFtIspravi() {  //F2
       return KeyEvent.VK_F2;
    }
    
    //Ispravi F5
    public int getFtStampa() {  //F5
       return KeyEvent.VK_F5;
    }
    
    public int getFtMargine(){ //F12
        return KeyEvent.VK_F12;
    }
    
    //Brisi Del
    public int getFtBrisi() {  //Del
        return KeyEvent.VK_DELETE;
    }
    
    //Upisi Enter   
    public int getFtUpisi() {  //Enter
        return KeyEvent.VK_ENTER;
    }  
    
    //Odustani Esc
    public int getFtOdustni() { //Ecs
        return KeyEvent.VK_ESCAPE;
    }      
}
