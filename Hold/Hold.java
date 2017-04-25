package Hold;

import Bolygo.*;
import Vektor.Vektor;

public class Hold extends Bolygo {

	protected String bolygoja;
	
	public Hold ( String nev, double R, double m, Vektor r, Vektor v, String B ){
		  super( nev, R, m, r, v );
		  bolygoja = B;
	}
	
	public String getBolygoja(){ return bolygoja; }
	public void setBolygoja(String B){ bolygoja = B; }
	
	public String toString(){
		return "\nHold=" + neve + " \n Bolygoja " + bolygoja + "\n sugar=" + sugar + "\n tomeg=" + tomeg + "\n Pozicio=" + kezdoPozicio + "\n Sebesseg=" + kezdoSebesseg; 
	}
	
}
