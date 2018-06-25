package weka.classifiers.misc;

import java.util.ArrayList;
import java.util.Collections;

import weka.classifiers.AbstractClassifier;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Normalize;

public class CNN extends AbstractClassifier {
	//Variables que inicializa el usuario 
	private int knn = 3;
	private int m = 100;
	
	//Arreglo de normas y clases de cada instancia (xi, c)
	ArrayList<TablaDistancia> td = new ArrayList<TablaDistancia>();
	Instance x;
	int k;
	ArrayList<String>  vecinos = new ArrayList<String>();
	
	
	
	@Override
	public void buildClassifier(Instances datos) throws Exception {
		// TODO Auto-generated method stub
		datos = Normalizar(datos);
		
		for(int i = 0; i < datos.size(); i++) {
			td.add(Distancia(datos.get(i), x));
		}
		Collections.sort(td);
		for(int i = 0; i < k; i++) {
			vecinos.add(td.get(i).clase);
		}
		getMayor(vecinos);
	}

	@Override
	public double classifyInstance(Instance dato) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	public TablaDistancia Distancia(Instance xi, Instance x) {
		//Función para obtener la distancia respecto x y xi;
		double distEuc = 0;
		/***********************
		 * La suma de (xi-x)^2 *
		 ***********************/
		for(int i = 0; i < xi.numAttributes(); i++) {
			distEuc = distEuc +
					Math.pow(xi.value(i) - x.value(i), 2.0f);
			System.out.println("Clase : " + xi.stringValue(i));
		}
		/*****************************
		 * Raiz de Zigma((xi-x)^2 *) *
		 *****************************/
		distEuc = Math.sqrt(distEuc);
		
		return new TablaDistancia(distEuc, xi.getClass().getName());
	}
	
	public Instances Normalizar(Instances data) throws Exception {
		Normalize filter = new Normalize();
		filter.setInputFormat(data);
		return Filter.useFilter(data, filter);
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
