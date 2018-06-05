package cn2;
import java.util.LinkedList;

public class Selectores {
	LinkedList<Selector> selectores;
	
	public Selectores () {
		selectores = new LinkedList<Selector>();
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
}
