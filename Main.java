import java.util.Vector;
import java.awt.*;
import Apps.*;
import MyGraphics.Frame;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args){
	Frame frame = new Frame();
	App.m_frame = frame;
	Vector<App> apps = new Vector<App>();
	apps.addElement( new Tubes( 0,0,0,0 ) );
	apps.addElement( new Weather( "E5", 0,0,0,0 ) );
	apps.addElement( new Sports( 20, 0,0,0 ) );
	apps.addElement( new Messages( 0,0,0,0 ) );
	apps.addElement( new TimeDate( 0,0,0,0 ) );
	frame.DrawText("Working...", 10, 20 );
	while( true ){
	    for ( App a : apps ){
		try{ a.Update(); }
		catch( Exception e ){ 
		    System.out.println("Exception from " + a.m_name );
		    System.out.println( "Exception: " + e );
		}
	    }
	    frame.Clear();
	    for ( App a : apps ) a.Draw();
	    while (true){
		try{
		    TimeUnit.SECONDS.sleep(30);
		    for ( App a : apps ){
			a.Update();
			a.Draw();
		    }
		    
		}
		catch(Exception e){ System.out.println(e); }
		
	    }
	}
    }

   
}
	
