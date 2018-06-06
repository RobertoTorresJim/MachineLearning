package cn2;
import java.util.ArrayList;

public class Complejo {
	ArrayList<Selector> selectores;
	
	public Complejo () {
		selectores = new ArrayList<Selector>();
	}
	//Regresa verdadero si se agrego el selector
	public boolean addSelector(Selector nuevo) {
		return selectores.add(nuevo);
	}
	
	public boolean removeSelector(int index) {
		if(selectores.remove(index) != null)
			return true;
		else return false;
	}
	
	public String toString() {
		return "";
	}
}
