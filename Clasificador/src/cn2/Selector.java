package cn2;


public class Selector {
	private String atributo;
	private String valor;
	
	public String getAtributo() {
		return atributo;
	}
	
	public String getValor() {
		return valor;
	}
	
	public void setAtributo(String atributo) {
		this.atributo = atributo;
	}
	
	public void setValor(String valor) {
		this.valor = valor;
	}
	
	public String toString() {
		return "IF" + atributo + "==" + valor;
	}
}
