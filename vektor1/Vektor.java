package vektor1;
public class Vektor { //implements VektorInterface kiveve a static method miatt
	
	protected double x;
	protected double y;
	protected double z;
	  
	public Vektor ( double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
		
	}	

	public double getX(){ return x;}
	public void setX( double x ){ this.x = x;}
	
	public double getY(){ return y;}
	public void setY( double y ){ this.y = y;}
	
	public double getZ(){ return z;}
	public void setZ( double z ){ this.z = z;}
	
	public static Vektor zeroVektor(){
		return new Vektor( 0.0, 0.0, 0.0 );
	}
	
	public static Vektor vektorOsszeadasKetto(Vektor v1, Vektor v2){
		return new Vektor( v1.x + v2.x, v1.y + v2.y, v1.z + v2.z ); 
	}
	public static Vektor vektorOsszeadasHarom(Vektor v1, Vektor v2, Vektor v3){
		return new Vektor( v1.x + v2.x + v3.x, v1.y + v2.y + v3.y, v1.z + v2.z + v3.z ); 
	}	
	
	public static double vektorSzorzas(Vektor v1, Vektor v2){
		return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z ;
	}
	public static double pontTavolsag(Vektor v1, Vektor v2){
		return Math.pow( (Math.pow((v2.x - v1.x),2) + Math.pow((v2.y - v1.y),2) + Math.pow((v2.z - v1.z),2)) ,0.5);
	}
	public static Vektor vektorSzorzasaSkalarral(Vektor v1, double s1){
		return new Vektor( v1.x * s1, v1.y * s1, v1.z * s1 ); 
	}
	
	public static Vektor vektorTavolsagR21(Vektor v1, Vektor v2){
		return new Vektor( v2.x - v1.x, v2.y - v1.y, v2.z - v1.z ); 
	}
	
	public String toString() {
		return "[x=" + x + ", y=" + y + ", z=" + z +"]";
	}
}