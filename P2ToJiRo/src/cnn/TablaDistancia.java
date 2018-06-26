package cnn;

public class TablaDistancia implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	double distancia;
	double clase;

	TablaDistancia(){}
	
	public TablaDistancia(double distancia, double clase){
		this.distancia = distancia;
		this.clase = clase;
	}
	
	 public double getDistancia() {
		return distancia;
	}

	public void setDistancia(double distancia) {
		this.distancia = distancia;
	}

	public double getClase() {
		return clase;
	}

	public void setClase(double clase) {
		this.clase = clase;
	}
	
}
