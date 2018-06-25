package weka.classifiers.misc;

public class TablaDistancia implements Comparable{
	double distancia;
	String clase;

	TablaDistancia(){}
	
	TablaDistancia(double distancia, String clase){
		this.distancia = distancia;
		this.clase = clase;
	}
	
	 public double getDistancia() {
		return distancia;
	}

	public void setDistancia(double distancia) {
		this.distancia = distancia;
	}

	public String getClase() {
		return clase;
	}

	public void setClase(String clase) {
		this.clase = clase;
	}

	@Override
	public int compareTo(Object comparaDis) {
		double comparaDist =((TablaDistancia)comparaDis).getDistancia();
	    /* For Ascending order*/
	    return (int) ((int)this.distancia-comparaDist);
	}
	
}
