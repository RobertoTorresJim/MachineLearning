package weka.classifiers.misc;

import java.util.ArrayList;

import cnn.TablaDistancia;
import weka.classifiers.AbstractClassifier;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Normalize;

/**
 * @author Torres Jiménez Roberto
 * @author Rosalinda Mendoza Mendoza
 *
 */
public class CNN extends AbstractClassifier {
	
	private static final long serialVersionUID = 1L;
	//Variables que inicializa el usuario 
	private int knn=5;
	private int m;
	private int numClases;
	
	//Arreglo de normas y clases de cada instancia (xi, c)
	ArrayList<TablaDistancia> td = new ArrayList<TablaDistancia>();
	Instance x;
	double[]  vecinos = new double[knn];
	
	double mayor;
	
	@Override
	public void buildClassifier(Instances datos) throws Exception {
		x = datos.get(m);
		System.out.println("X-Class:"+ x.classValue());
		numClases = datos.numClasses();
		datos = Normalizar(datos);
		
		for(int i = 0; i < datos.size(); i++) {
			td.add(Distancia(datos.get(i), x));
			
		}
		ordena(td);
		
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

	/**************************************
	 * Funcion que regresa la clase mayor *
	 * en los vecinos mas cercanos        *
	 * @param lista                       *
	 * @return double mayor               *
	 **************************************/
	public double getMayor(double[] lista) {
		int[] countClases = new int[numClases];
		double[] clases = new double[numClases];
		double max;
		double aux;
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
		aux = countClases[0];
		max = 0;
		for (int i = 1; i < countClases.length; i++) {
			if(aux < countClases[i]) {
				aux = countClases[i];
				max = i;
			}
		}
		return max;
	}

	@Override
	public String[] getOptions() {
		String[] arr = {"-K",""+getKnn(),"m", ""+getM()};
		return arr;
	}	
	
	@Override
	public void setOptions(String[] options) throws Exception {
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
	
	/********************************************
	 * Metodo que ordena la tabla de distancias *
	 * @param t                                 *
	 ********************************************/
	public void ordena(ArrayList<TablaDistancia> t) {
		boolean intercambio = true;
		int j = 0;
		TablaDistancia temporal;
		while(intercambio) {
			intercambio = false;
			j++;
			for(int i = 0; i < t.size() - j; i++) {
				if(t.get(i).getDistancia() > t.get(i + 1).getDistancia()) {
					temporal = t.remove(i);
					t.add(i + 1, temporal);
					intercambio = true;
				}
			}
		}	
	}
	
}
