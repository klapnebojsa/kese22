/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Forme.Polja.Listeneri;

/**
 *
 * @author Nebojsa
 */
public class OgranicenjaListeneri {
    boolean ogranicenja=true;
    String koZove;
    public OgranicenjaListeneri (String koZove){
        this.koZove=koZove;
    }   
    
    private String NoviListener(){
        return "Margine,";
    }
    private String IzmenaListener(){
        return "";
    }
    private String MargineListener(){
        return "Margine,";
    }
    private String BrisiListener(){
        return "Margine,";
    }
    private String StampaListener(){
        return "Margine,";
    }
    
    private void setOgranicenja (boolean ogranicenja){
        this.ogranicenja = ogranicenja;
    }
    
    public boolean getOgranicenja (){
        return ogranicenja;
    }
    
    public void proveriNoviListener(){
        String[] a = NoviListener().split(koZove);
        if (a.length>1){setOgranicenja(true);
        }else{setOgranicenja(false);}
    }
    public void proveriIzmenaListener(){
        String[] a = IzmenaListener().split(koZove);
        if (a.length>1){setOgranicenja(true);
        }else{setOgranicenja(false);}
    } 
    public void proveriMargineListener(){
        String[] a = MargineListener().split(koZove);
        if (a.length>1){setOgranicenja(true);
        }else{setOgranicenja(false);}
    }  
    public void proveriBrisiListener(){
        String[] a = BrisiListener().split(koZove);
        if (a.length>1){setOgranicenja(true);
        }else{setOgranicenja(false);}
    }
    public void proveriStampaListener(){
        String[] a = StampaListener().split(koZove);
        if (a.length>1){setOgranicenja(true);
        }else{setOgranicenja(false);}
    }
}
