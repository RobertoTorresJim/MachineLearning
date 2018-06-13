/**
 * 
 */
package weka.classifiers.misc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn2.*;
import weka.classifiers.AbstractClassifier;
import weka.core.Instance;
import weka.core.Instances;

/**
 * @author Torres Jiménez Roberto
 * @author  Rosalinda Mendoza Mendoza
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
		List<String> selectoresS = new ArrayList<String>();
		for(int i = 0; i < datos.numAttributes()-1; i++) {
			for (int j = 0; j < datos.attribute(i).numValues();j++) {
				Selector s = new Selector();
				s.setAtributo(datos.attribute(i).name());
				s.setValor(datos.attribute(i).value(j));
				selectores.add(s);
				selectoresS.add(datos.attribute(i).value(j));
				System.out.println(datos.attribute(i).value(j));
			}
		}
		
		Combinar com = new Combinar();
		com.Test(selectoresS);
		
		
		
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
				//System.out.println("index: " + index);
			}
			else {
				cuentaclaseMayor[index] += 1;
				//System.out.println("index: " + index);
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

	public class Combinar {
		   List<String> c;
		   List <String> d=new ArrayList<String>();
		   public Combinar() {}
		   public Combinar(List<String> lis){
		      c=new ArrayList<String>();
		      sinPermutacion(lis);
		      d=lis;
		   }
		    
		   public String Ar(){
		      String cad="";
		      List<String> comb=new ArrayList<String>();
		      Iterator iter=c.iterator();
		          
		      int tam=d.size();//d=lista que nos envien
		      int x=1;
		      double a[]=new double[tam];
		          
		         //fuente http://puraslineas.com/2011/01/19/combinaciones-permutaciones-y-agrupaciones-en-java/ 
		      for(int m=1;m<=tam;m++){
		         double n=1;
		         double r=1;
		         double aux1=1;
		         for(int i=1;i<=tam;n*=i,i++);//factorial de n que será el número de elementos.
		         int aux=(tam-m);
		         for(int i=1;i<=aux;aux1*=i,i++);//factorial de aux1, que es la resta anterior
		         for(int i=1;i<=m;r*=i,i++);//factorial del número de elementos por grupo
		         a[m-1]=n/(aux1*r);//formula para obtener número de combinaciones posibles y lo guardamos en un arreglo.
		      }
		          
		      while(iter.hasNext()){
		         for(int i=0;i<a.length;i++){
		            for(int j=1;j<=a[i];j++){
		               for(int k=1;k<=x;k++){
		                  cad+=(String)iter.next()+" ";//colocamos un separador
		               }//comb.add("\n");
		               cad+="\n";//colacamos un salto de linea
		            }
		            x++;
		         }
		      }
		      return cad;
		   }
		    
		   public void sinPermutacion(List<String> lista) {
			   //lista.toArray();
		      Object[] o = lista.toArray();
		      
		      for (int m = 1; m <= lista.size(); m++) {
		         int[] posArr = new int[m];
		         posArr[0] = 0;
		         if (m > 1) {
		            for (int i = 1; i < m; i++) {
		               posArr[i] = i;
		            }
		         }
		         combina(posArr, m - 1, m, o);
		      }
		   }
		 
		 //fuente http://puraslineas.com/2011/01/19/combinaciones-permutaciones-y-agrupaciones-en-java/ 
		   public void combina(int[] posArr, int posCam, int dea, Object[] o) {
		      int cantidad = o.length;
		      int j;
		      for (j = 0; j < posArr.length; j++) {
		         c.add((String)o[posArr[j]]);
		      }
		      posArr[posCam]++;
		      if (posArr[posCam] < cantidad) {
		         combina(posArr, posCam, dea, o);
		      } 
		      else {
		         int nuevaPosCam = posCam - 1;
		         if (nuevaPosCam >= 0) {
		            posArr[nuevaPosCam]++;
		            posArr[posCam] = posArr[posCam - 1] + 1;
		            if (posArr[nuevaPosCam] < cantidad - 1) {
		               combina(posArr, posCam, dea, o);
		            } 
		            else {
		               boolean salida = false;
		               if (nuevaPosCam != 0) {
		                  while (posArr[nuevaPosCam] >= cantidad - 1 || (salida && nuevaPosCam > 0)) {
		                     nuevaPosCam--;
		                     posArr[nuevaPosCam]++;
		                     for (int i = nuevaPosCam + 1; i < dea; i++) {
		                        posArr[i] = posArr[i - 1] + 1;
		                        salida = posArr[i] == cantidad;
		                     }
		                  }
		                  if (!salida) {
		                     combina(posArr, posCam, dea, o);
		                  }
		               }
		            }
		         }//end if nuevaPosCam>=0
		      }//end else 
		   }//end combina
		    
		     //fuente http://puraslineas.com/2011/01/19/combinaciones-permutaciones-y-agrupaciones-en-java/ 
		   public void Test(List<String> selectores){
		      
		      
		      Combinar comb=new Combinar(selectores);
		      System.out.println(comb.Ar());
		   }
		}//end class

}
