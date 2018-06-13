package weka.classifier.misc;

import java.util.ArrayList;

import weka.classifiers.AbstractClassifier;
import weka.core.Instance;
import weka.core.Instances;

public class CNN extends AbstractClassifier {

	ArrayList<TablaDistancias> td = new ArrayList<TablaDistancias>();
	Instance x;
	int k;
	ArrayList<String>  listaClases = new ArrayList<String>();
	@Override
	public void buildClassifier(Instances datos) throws Exception {
		// TODO Auto-generated method stub
		for(int i = 0; i < datos.size(); i++) {
			td.add(Distancia(datos.get(i), x));
		}
		//td.sort();
		for(int i = 0; i < k; i++) {
			listaClases.add(td.get(i).clase);
		}
		getMayor(listaClases);
	}

	@Override
	public double classifyInstance(Instance dato) throws Exception {
		// TODO Auto-generated method stub
		return super.classifyInstance(dato);
	}

	public TablaDistancias Distancia(Instance xi, Instance x) {
		//Función para obtener la norma respecto x y xi;
		return null;
	}
	public String getMayor(ArrayList<String> lista) {
		return "";
	}
	
}
