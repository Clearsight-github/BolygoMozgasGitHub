package Bolygo;
import Vektor.*;

public class Bolygo {
	
	static final double G = Double.valueOf("6.673E-11"); //G = 6.673×10-11
	
	protected String neve; 
	protected double sugar;
	protected double tomeg;
	protected Vektor kezdoSebesseg;
	protected Vektor kezdoPozicio;
	protected Vektor eredoEro;
	protected Vektor gyorsulas;
	protected Vektor ujSebesseg;
	protected Vektor ujPozicio;
	public static Vektor eredoEroSum;
	
	public Bolygo ( String nev, double R, double m, Vektor r, Vektor v){
		  neve = nev;
		  sugar = R;
		  tomeg = m;
		  kezdoPozicio = r;
		  kezdoSebesseg = v;
		  
	}
		
		public double getSugar(){ return sugar;}
		public void setSugar( double R ){ sugar = R;}
		
		public double getTomeg(){ return tomeg;}
		public void setTomeg( double m ){ tomeg = m;}
		
		public Vektor getKezdoSebesseg(){ return kezdoSebesseg;}
		public void setKezdoSebesseg( Vektor v ){ kezdoSebesseg = v;}
		
		public Vektor getKezdoPozicio(){ return kezdoPozicio;}
		public void setKezdoPozicio( Vektor r ){ kezdoPozicio = r;}

		public Vektor getEredoEro(){ return eredoEro;}
		public void setEredoEro( Vektor F ){ eredoEro = F;}		

		public Vektor getUjSebesseg(){ return ujSebesseg;}
		public void setUjSebesseg( Vektor nv ){ ujSebesseg = nv;}		
		
		public Vektor getUjPozicio(){ return ujPozicio;}
		public void setUjPozicio( Vektor nr ){ ujPozicio = nr;}

		public Vektor getGyorsulas(){ return gyorsulas;}
		public void setGyorsulas( Vektor acc ){ gyorsulas = acc;}
		
		public Vektor eredoEro(double m1, double m2, Vektor r1, Vektor r2){
			eredoEro = Vektor.vektorSzorzasaSkalarral(Vektor.vektorTavolsagR21(r1, r2), G * m1 * m2 / Math.pow(Vektor.pontTavolsag(r1, r2),3));
			return eredoEro;
		}
		
		public Vektor gyorsulas(Vektor eredoEro, double m1){
			gyorsulas = Vektor.vektorSzorzasaSkalarral( eredoEro, 1.0/m1);
			return gyorsulas;
		}

		public Vektor ujSebesseg(Vektor kezdoSebesseg, Vektor gyorsulas, double dt){
			ujSebesseg = Vektor.vektorOsszeadasKetto(kezdoSebesseg, Vektor.vektorSzorzasaSkalarral(gyorsulas, dt) );
			return ujSebesseg;
		}
		
		public Vektor ujPozicio(Vektor kezdoPozicio, Vektor kezdoSebesseg, Vektor gyorsulas, double dt){
			ujPozicio = Vektor.vektorOsszeadasHarom(kezdoPozicio, Vektor.vektorSzorzasaSkalarral(kezdoSebesseg,dt), Vektor.vektorSzorzasaSkalarral(gyorsulas,0.5 * Math.pow(dt,2)) );
			return ujPozicio;
		}		
	
		public String toString(){
			return "\nBolygo=" + neve + " \n sugar=" + sugar + "\n tomeg=" + tomeg + "\n Pozicio=" + kezdoPozicio + "\n Sebesseg=" + kezdoSebesseg + "\n"; 
		}
		
}	






