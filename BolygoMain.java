import java.io.*;
import java.util.*;

import bolygo1.*;
import hold1.*;
import szimulacio1.*;
import vektor1.*;

/**
 * Bolygomozgas szimulalasa.
 *
 * @author Aron Szrnka
 */

public class BolygoMain {	
  public static void main(String[] args) throws IOException {
         
	if (args.length > 2) {
	    System.err.println("Hasznalat: java BolygoMain input.txt output.txt");
	    System.exit(1);
	}

    Szimulacio szimulacio = null; // latszodnia kell mindenhol a main-ben   
/**	Letrehozok egy FileReader-t es egy FileWriter-t. */
	BufferedReader r = new BufferedReader( new FileReader(args[0]) );

	Writer w = ( new FileWriter(args[1]) );
	
/** Letrehozom a szukseges mennyisegu objektumot.
	Hogy tetszoleges szamu objektum lehessen, listaba rakom oket.
*/
//	Letrehozom a Bolygokbol allo listat.
	List<Bolygo> kBolygoList = new ArrayList<Bolygo>();
	
//	Beolvassa soronkent az inputot.
	for (String line; (line = r.readLine()) != null;) {
	    // a StringTokenizer-rel whitespace-nel tordeli.
	    StringTokenizer tokenizer = new StringTokenizer(line);

/**Beallitom a szimulaciohoz szukseges adatokat. 
Azt szeretném tenni, hogy ellenõrizni szeretném, hogy az adott sor elsõ stringje szám-e vagy betû-e. 
Ha szám, akkor szimulációs adat, ha betû, akkor bolygó adat következik mégpedig a neve.*/
	    
			String token = tokenizer.nextToken(); // az elso token a sorban
		    
			if (Character.isDigit(token.charAt(0))){
				double dt = Double.parseDouble(token);
				token = tokenizer.nextToken();
				double szimulacioHossza = Double.parseDouble(token);
			
				szimulacio = new Szimulacio(dt, szimulacioHossza, false );		
		    } else {
				//while (tokenizer.hasMoreTokens()) {
		    	
					String neve = token;						// a sor elso tokenje mindig az objektum neve
					token = tokenizer.nextToken();				
				    double sugar = Double.parseDouble(token);
					token = tokenizer.nextToken();				
				    double tomeg = Double.parseDouble(token);
					token = tokenizer.nextToken();				
				    double kezdoPozicioX = Double.parseDouble(token);
					token = tokenizer.nextToken();				
				    double kezdoPozicioY = Double.parseDouble(token);				    
					token = tokenizer.nextToken();				
				    double kezdoPozicioZ = Double.parseDouble(token);
					token = tokenizer.nextToken();
				    double kezdoSebessegX = Double.parseDouble(token);
					token = tokenizer.nextToken();				
				    double kezdoSebessegY = Double.parseDouble(token);				    
					token = tokenizer.nextToken();				
				    double kezdoSebessegZ = Double.parseDouble(token);
				    
				    
					Vektor kezdoPozicio = new Vektor( kezdoPozicioX, kezdoPozicioY, kezdoPozicioZ );

					Vektor kezdoSebesseg = new Vektor(kezdoSebessegX, kezdoSebessegY, kezdoSebessegZ );
					
				    if ( tokenizer.hasMoreTokens() ){	// ha van meg egy adat akkor Hold lesz, és az adat a hold bolygoja
				    	token = tokenizer.nextToken();
				    	if (Character.isLetter(token.charAt(0))){ //nem feltetlenul kell megvizsgalni
					    	String bolygoja = token;
					    	kBolygoList.add( new Hold( neve, sugar, tomeg, kezdoPozicio, kezdoSebesseg, bolygoja ));
				    	}
				    } else {
				    	kBolygoList.add( new Bolygo( neve, sugar, tomeg, kezdoPozicio, kezdoSebesseg ));
				    }	

			} //else
	}//for line
	
/**	Kiiratom a kezdeti ertekeket mind a konzolra es az output fajlba. */
	kiiratas("A kezdeti ertekek", w, kBolygoList);

/** UtkozesFigyelo az inicializalaskor */
	utkozesFigyelo(szimulacio, kBolygoList);

/**	Kiiratom a konzolra es az output fajlba, ha utkozes tortent az inicializalaskor. Ekkor a szimulacio nem kezdodik el.
	Ha nem tortent utkozes inicializalaskor, elkezdodik a szimulacio a t=0 pillanattol a szimulacioHosszaig vagy utkozesig */
	if(utkozesJelzese("inicializalaskor" , szimulacio, w) == false){

		szimulacio.setElteltIdo(0.0);
		
		
		while ( szimulacio.getSzimulacioHossza() >= szimulacio.getElteltIdo() ){
		/** dt idointervallumokkal leptetve a szimulacio idofejlodeset. */
		
			szimulacio.setElteltIdo(szimulacio.getElteltIdo() + szimulacio.getDt() ); 	//szimulacio.elteltIdo += szimulacio.getDt();
		
		/** Kiszamolom az eredoEro, gyorsulas, ujSebesseg, ujPozicio-kat az egyes bolygokra  */
		
			ujPozicioEsUjSebessegKiszamolasa(szimulacio, kBolygoList); 
		
		/** Kesz vannak az uj adatok, el kell mozgatni a testeket es beallitani az uj kezdoSebessegeket */
			ujPozicioEsUjSebessegBeallitasa(kBolygoList);
		
		/** Utkozes figyelo a szimulacio kozben */ 
			utkozesFigyelo(szimulacio, kBolygoList);
		
		/** Ha utkozes tortent, az megszakitja a szimulaciot es jelezve lesz a konzolon es az output fajlban. */
			if( szimulacio.getUtkozes() == true ){
				System.out.println( "\nUtkozes tortent a szimulacio kozben.\n" );
				w.write( "\nUtkozes tortent a szimulacio kozben.\n" );
				break;
			}
		}//while szimulacio

/**	Kiiratom a Bolygok adatait a konzolra es az output fajlba a szimulacio vegen. */	
		kiiratas("A vegso ertekek", w, kBolygoList);
		
		w.write("\nSzimulacio vege.\n");
		System.out.println("\nSzimulacio vege.\n");
	
/**	Ha nem volt utkozes azt is jelzem. */	
		utkozesJelzese("szimulacio vegen" , szimulacio, w);
		
	}// ha nem tortent utkozes az elejen... 

	w.flush(); 
	r.close(); //Ha minden kesz, lezarom az olvasast...
	w.close(); //es az irast.
  }//main

public static void kiiratas(String str, Writer w, List<Bolygo> kBolygoList) throws IOException {
	System.out.println("\n" + str + ":\n");
	System.out.println(kBolygoList.toString());
	w.write("\n" + str + ":\n");
	w.write(kBolygoList.toString());
}

public static boolean utkozesJelzese(String str, Szimulacio szimulacio, Writer w) throws IOException {
	if( szimulacio.getUtkozes() == true ){
		System.out.println( "\nUtkozes tortent a " + str + ".\n" );
		w.write( "\nUtkozes tortent a " + str + ".\n" );
		return true;
	} else { 
		System.out.println( "\nNem tortent utkozes a " + str + ".\n" );
		w.write( "\nNem tortent utkozes a " + str + ".\n" ); 
		return false;
	}
}  
  
public static void utkozesFigyelo(Szimulacio szimulacio, List<Bolygo> kBolygoList) {
	for (int a = 0; a < kBolygoList.size(); a++ ){
		for (int b=0; b < kBolygoList.size(); b++ ){
			if( a == b ){ // ha onmaga, akkor nem tesz semmit
			} else {
				szimulacio.setUtkozes( szimulacio.utkozesFigyelo( kBolygoList.get(a).getSugar(), kBolygoList.get(b).getSugar(), Vektor.pontTavolsag(kBolygoList.get(a).getKezdoPozicio(), kBolygoList.get(b).getKezdoPozicio() ) ) );
				if( szimulacio.getUtkozes() == true ){
					break;
				}
			}
			if( szimulacio.getUtkozes() == true ){
				break;
			}
		} // for b
		if( szimulacio.getUtkozes() == true ){
			break;
		}
	}// for a
}

public static void ujPozicioEsUjSebessegKiszamolasa(Szimulacio szimulacio, List<Bolygo> kBolygoList) {
	for (int j=0; j < kBolygoList.size(); j++ ){	//Az egyes bolygokra
	    Bolygo.eredoEroSum = Vektor.zeroVektor();
		for (int l=0; l< kBolygoList.size(); l++){	// az osszes tobbi bolygo hatasa
			if( j == l ){ // ha onmaga, akkor nem tesz semmit
			} else {
			/** Egy bolygo hatasa az osszes tobbire es ez felosszegezve megadja az eredoEro-t. */
			Bolygo.eredoEroSum = Vektor.vektorOsszeadasKetto( Bolygo.eredoEroSum, kBolygoList.get(j).eredoEro(kBolygoList.get(j).getTomeg(), kBolygoList.get(l).getTomeg(), kBolygoList.get(j).getKezdoPozicio(), kBolygoList.get(l).getKezdoPozicio() ) );
			/**FONTOS: itt nem hasznalhato a list.set() , mert az Objektumot cserelne ki.*/
			}
		}
	            kBolygoList.get(j).setEredoEro(Bolygo.eredoEroSum);
	            /** Itt kesz a j-edik bolygo eredoEro-je. */
	            /** szamolhato a gyorsulas, ujSebesseg, ujPozicio. FONTOS: ekkor a kezdoPozicio es kezdoSebesseg nem valtozik */
	            /** gyorsulas beallitasa: setGyorsulas -> gyorsulas(eredoEro,tomeg) */
	            kBolygoList.get(j).setGyorsulas( kBolygoList.get(j).gyorsulas(kBolygoList.get(j).getEredoEro(), kBolygoList.get(j).getTomeg() ) );
	            /** ujSebesseg beallitasa:setUjSebesseg -> ujSebesseg(kezdoSebesseg, gyorsulas, dt) */
	            kBolygoList.get(j).setUjSebesseg( kBolygoList.get(j).ujSebesseg(kBolygoList.get(j).getKezdoSebesseg(), kBolygoList.get(j).getGyorsulas(), szimulacio.getDt() ) );
	            /** ujPozicio beallitasa: setUjPozicio -> ujPozicio(kezdoSebesseg, gyorsulas, dt) */
	            kBolygoList.get(j).setUjPozicio( kBolygoList.get(j).ujPozicio(kBolygoList.get(j).getKezdoPozicio(), kBolygoList.get(j).getKezdoSebesseg(), kBolygoList.get(j).getGyorsulas(), szimulacio.getDt() ) );
	}
}

public static void ujPozicioEsUjSebessegBeallitasa(List<Bolygo> kBolygoList) {
	for (int n=0; n < kBolygoList.size(); n++ ){
		// kezdoPozicio = ujPozicio;
		kBolygoList.get(n).setKezdoPozicio( kBolygoList.get(n).getUjPozicio() );
		// kezdoSebesseg = ujSebesseg;
		kBolygoList.get(n).setKezdoSebesseg( kBolygoList.get(n).getUjSebesseg() );
	}
}

}//class BolygoMain

