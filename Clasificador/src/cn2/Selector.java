package cn2;


public class Selector {
	private double atributo;
	private double valor;
	
	public double getAtributo() {
		return atributo;
	}
	
	public double getValor() {
		return valor;
	}
	
	public void setAtributo(double atributo) {
		this.atributo = atributo;
	}
	
	public void setValor(double valor) {
		this.valor = valor;
	}
	
	public String toString() {
		return "IF" + atributo + "==" + valor;
	}
}
