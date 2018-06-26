package weka.classifiers.misc;

import java.util.ArrayList;

import weka.classifiers.AbstractClassifier;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Normalize;

public class CNN extends AbstractClassifier {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Variables que inicializa el usuario 
	private int knn=3;
	private int m=0;
	private int numClases;
	
	//Arreglo de normas y clases de cada instancia (xi, c)
	ArrayList<TablaDistancia> td = new ArrayList<TablaDistancia>();
	Instance x;
	double[]  vecinos = new double[knn];
	
	double mayor;
	
	@Override
	public void buildClassifier(Instances datos) throws Exception {
		// TODO Auto-generated method stub
		x = datos.get(0);
		System.out.println("X-Class:"+ x.classValue());
		numClases = datos.numClasses();
		//System.out.println("Numero de clases: " + numClases);
		datos = Normalizar(datos);
		
		for(int i = 0; i < datos.size(); i++) {
			td.add(Distancia(datos.get(i), x));
			System.out.println("Clase datos: "+ datos.get(i).classValue());
		}
		ordena(td);
		
		
		for (int i = 0; i < td.size(); i++) {
			System.out.println(td.get(i).distancia+"\n");
		}
		
		for(int i = 0; i < knn; i++) {
			vecinos[i] = td.get(i).getClase();
			System.out.println("Vecino: " + td.get(i).getClase() + " añadido");
		}
		 mayor = getMayor(vecinos);
		System.out.println("Clase: " + mayor);
		classifyInstance(x);
	}

	@Override
	public double classifyInstance(Instance dato) throws Exception {
		// TODO Auto-generated method stub
		return mayor;
	}

	public TablaDistancia Distancia(Instance xi, Instance x) {
		//Función para obtener la distancia respecto x y xi;
		double distEuc = 0;
		/***********************
		 * La suma de (xi-x)^2 *
		 ***********************/
		for(int i = 0; i < xi.numAttributes()-1; i++) {
			distEuc = distEuc +
					Math.pow(xi.value(i) - x.value(i), 2.0f);
			//System.out.println("Clase : " + xi.stringValue(i));
		}
		/*****************************
		 * Raiz de Zigma((xi-x)^2 *) *
		 *****************************/
		distEuc = Math.sqrt(distEuc);
		System.out.println("Distancia: " + distEuc);
		
		return new TablaDistancia(distEuc, xi.classValue());
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

	public double getMayor(double[] lista) {
		int[] countClases = new int[numClases];
		double[] clases = new double[numClases];
		double max;
		for(int i = 0; i < clases.length; i++) {
			clases[i] = i;
			countClases[i] = 0;
		}
		int selectClase;
		for (int i = 0; i < clases.length; i++) {
			selectClase = i;
			for (int j = 0; j < lista.length; j++) {
				if(selectClase == lista[j]) {
					countClases[i]++;
				}
			}
		}
		max = countClases[0];
		for (int i = 1; i < countClases.length; i++) {
			if(max < countClases[i]) {
				max = countClases[i];
			}
		}
		//System.out.println("Maximo: " +max);
		return max;
	}

	@Override
	public String[] getOptions() {
		// TODO Auto-generated method stub
		String[] arr = {"-K",""+getKnn(),"m", ""+getM()};
		
		return arr;
	}	
	
	@Override
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
	public void ordena(ArrayList<TablaDistancia> t) {
		boolean intercambio = true;
		int j = 0;
		TablaDistancia temporal;
		while(intercambio) {
			intercambio = false;
			j++;
			for(int i = 0; i < t.size() - j; i++) {
				if(t.get(i).distancia > t.get(i + 1).distancia) {
					temporal = t.remove(i);
					t.add(i + 1, temporal);
					intercambio = true;
				}
			}
		}	
	}
	
}
