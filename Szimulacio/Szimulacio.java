package Szimulacio;
public class Szimulacio {
    private double dt;
	private double szimulacioHossza;
	private boolean utkozes;
	private double elteltIdo;
        
	public Szimulacio ( double dt, double szimulacioHossza, boolean utkozes){
		  this.dt = dt;
		  this.szimulacioHossza = szimulacioHossza;
		  this.utkozes = utkozes;
	}
	
	public double getDt(){ return dt; }
	public void setDt(double dt ){ this.dt = dt; }
	
	public double getSzimulacioHossza(){ return szimulacioHossza; }
	public void setSzimulacioHossza(double szimulacioHossza ){ this.szimulacioHossza = szimulacioHossza; }	

	public boolean getUtkozes(){ return utkozes; }
	public void setUtkozes(boolean utkozes ){ this.utkozes = utkozes; }
	
	public double getElteltIdo(){ return elteltIdo; }
	public void setElteltIdo(double elteltIdo ){ this.elteltIdo = elteltIdo; }
	
	public boolean utkozesFigyelo( double R1, double R2, double tavolsag ){
		if (R1 + R2 >= tavolsag){
			utkozes = true;
		} else {
			utkozes = false;
		}
		return utkozes;
	}
}