/**
 * 
 */
package weka.classifiers.misc;

import java.util.ArrayList;

import cn2.*;
import weka.classifiers.AbstractClassifier;
import weka.core.Instance;
import weka.core.Instances;

/**
 * @author Roberto Torres Jiménez 
 *
 */
public class Clasificador1 extends AbstractClassifier {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	double claseMayor;
	double [][]estrella;
	int[] cuentaclaseMayor;
	int numClaseMayor;
	
	Complejo selector;
	
	@Override
	public double classifyInstance(Instance arg0) throws Exception {
		// TODO Auto-generated method stub
		
		
		return claseMayor;
	}

	/* (non-Javadoc)
	 * @see weka.classifiers.Classifier#buildClassifier(weka.core.Instances)
	 */
	@Override
	public void buildClassifier(Instances datos) throws Exception {
		claseMayor(datos);
		
		ArrayList<Selector> selectores = new ArrayList<Selector>();
		
		for(int i = 0; i < datos.numAttributes()-1; i++) {
			for (int j = 0; j < datos.attribute(i).numValues();j++) {
				Selector s = new Selector();
				s.setAtributo(datos.attribute(i).name());
				s.setValor(datos.attribute(i).value(j));
				selectores.add(s);
				System.out.println(datos.attribute(i).value(j));
			}
		}
		
		
		
	}
	

	//Busca la clase mayor
	public int claseMayor(Instances datos) {
		
		// TODO Auto-generated method stub
		//System.out.println(datos.attribute(0).);
		cuentaclaseMayor = new int [(int)datos.numClasses()];
		//System.out.println(cuentaclaseMayor.length);
		for(int num : cuentaclaseMayor) {
			num = 0;
		}
		datos.sort(datos.numAttributes()-1);
		int index = 0;
		for(int i = 0; i < datos.numInstances()-1; i++ ) {
			if(datos.instance(i).classValue() == datos.instance(i+1).classValue()){
				cuentaclaseMayor[index] += 1;
				System.out.println("index: " + index);
			}
			else {
				cuentaclaseMayor[index] += 1;
				System.out.println("index: " + index);
				index++;
			}
		}
		//Ejemplo que funciona para datos con dos claseMayor
		if(cuentaclaseMayor[0]>cuentaclaseMayor[1]) { 
			claseMayor = 0;
			numClaseMayor = (int)cuentaclaseMayor[0];
		}
		else {
			claseMayor = 1;
			numClaseMayor = (int)cuentaclaseMayor[1];
		}
		/*EJEMPLO PARA 3 claseMayor falta generalizar
		if(cuentaclaseMayor[0]>cuentaclaseMayor[1]) {
			if(cuentaclaseMayor[0]> cuentaclaseMayor[2]) {
				claseMayor = 0;
			}
			else {
					claseMayor = 2;	
			}
		}else {
			if(cuentaclaseMayor[1]>cuentaclaseMayor[2])
				claseMayor = 1;
			else
				claseMayor = 2;
		}*/
		return (int)claseMayor;
	}
	
	@Override
	public String toString() {
		return "Clasificador1 []";
	}

}
