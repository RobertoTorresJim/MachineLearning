package weka.classifiers.misc;

import java.util.ArrayList;

import weka.classifiers.AbstractClassifier;
import weka.core.Instance;
import weka.core.Instances;

public class CNN extends AbstractClassifier {
	//Variables que inicializa el usuario 
	private int knn = 3;
	private int m = 100;
	
	//Arrelo de normas y clases de cada instancia (xi, c)
	ArrayList<TablaNormal> td = new ArrayList<TablaNormal>();
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
		return 0;
	}

	public TablaNormal Distancia(Instance xi, Instance x) {
		//Función para obtener la norma respecto x y xi;
		return null;
	}
	@Override
	public String toString() {
		return "CNN [knn=" + knn + ", m=" + m + "]";
	}

	public String getMayor(ArrayList<String> lista) {
		return "";
	}

	@Override
	public String[] getOptions() {
		// TODO Auto-generated method stub
		String[] arr = {"-K",""+getKnn(),"m", ""+getM()};
		
		return arr;
	}	@Override
	public void setOptions(String[] options) throws Exception {
		// TODO Auto-generated method stub
		super.setOptions(options);
	}

	public int getKnn() {
		return knn;
	}

	public void setKnn(int knn) {
		this.knn = knn;
	}

	public int getM() {
		return m;
	}

	public void setM(int m) {
		this.m = m;
	}


}
