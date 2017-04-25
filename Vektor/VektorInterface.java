package Vektor;
public interface VektorInterface {
	static final double PI = 3.1415;
	
	Vektor vektorOsszeadasKetto(Vektor v1, Vektor v2);
	Vektor vektorOsszeadasHarom(Vektor v1, Vektor v2, Vektor v3);
//	Vektor vektorOsszeadas(Vektor... args);
	double vektorSzorzas(Vektor v1, Vektor v2);
	double pontTavolsag(Vektor v1, Vektor v2);
	Vektor vektorSzorzasaSkalarral(Vektor v1, double s1);
	Vektor vektorTavolsagR21(Vektor v1, Vektor v2);
	Vektor zeroVektor();
}