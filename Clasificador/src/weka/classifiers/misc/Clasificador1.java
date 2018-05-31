/**
 * 
 */
package weka.classifiers.misc;

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
	double clases;
	
	int[] cuentaClases;
	
	@Override
	public double classifyInstance(Instance arg0) throws Exception {
		// TODO Auto-generated method stub
		return clases;
	}

	/* (non-Javadoc)
	 * @see weka.classifiers.Classifier#buildClassifier(weka.core.Instances)
	 */
	@Override
	public void buildClassifier(Instances datos) throws Exception {
		// TODO Auto-generated method stub
		cuentaClases = new int [(int)datos.numClasses()];
		//System.out.println(cuentaClases.length);
		for(int num : cuentaClases) {
			num = 0;
		}
		
		datos.sort(datos.numAttributes()-1);
		int index = 0;
		for(int i = 0; i < datos.numInstances()-1; i++ ) {
			if(datos.instance(i).classValue() == datos.instance(i+1).classValue()){
				cuentaClases[index] += 1;
				System.out.println("index: " + index);
			}
			else {
				cuentaClases[index] += 1;
				System.out.println("index: " + index);
				index++;
			}
			
		}
		System.out.println(cuentaClases[0]+", "+ cuentaClases[1]+", "+cuentaClases[2]);
		if(cuentaClases[0]>cuentaClases[1]) {
			if(cuentaClases[0]> cuentaClases[2]) {
				clases = 0;
			}
			else {
					clases = 2;	
			}
		}else {
			if(cuentaClases[1]>cuentaClases[2])
				clases = 1;
			else
				clases = 2;
		}
		System.out.println("clasifico con: " + clases);
		
	}

	@Override
	public String toString() {
		return "Clasificador1 []";
	}

}
