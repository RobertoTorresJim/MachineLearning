package mx.uam.izt.am;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.trees.J48;
import weka.core.DenseInstance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class UsaAprendiz extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int BI_WIDTH = 40;
	private JPanel contentPane;
	private JLabel atribute_00;
	private JLabel atribute_01;
	private JLabel atribute_03;
	private JLabel atribute_04;
	private JLabel atribute_05;
	private JLabel atribute_06;
	private JLabel atribute_07;
	
	private JLabel valor_00;
	private JLabel valor_01;
	private JLabel valor_03;
	private JLabel valor_04;
	private JLabel valor_05;
	private JLabel valor_06;
	private JLabel valor_07;
	
	private ImageIcon odor_00,odor_01,odor_02,odor_03,odor_04,odor_05,odor_06,odor_07,odor_08;
	private ImageIcon spore_00, spore_01, spore_02, spore_03, spore_04, spore_05, spore_06, spore_07, spore_08;
	
	private ButtonGroup odorBtnGrp = new ButtonGroup();
    private JButton button ;
    private JButton next;
    private JButton finish;
    private int atribute = 0;
	private ArrayList<ImageIcon> imgArray = new ArrayList();
	private String[] odors = {"almendra","anis","creosota", "pescado", "podrido", "musgoso", "ninguno", "picante0", "picante"};
	
	static J48 mlp;

	
	/**
	 * Launch the application.
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
		
		// Lee el modelo 
		ObjectInputStream ois = new ObjectInputStream(
												new FileInputStream("j48.model"));
		mlp = (J48) ois.readObject();
		
		ois.close();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UsaAprendiz frame = new UsaAprendiz();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public UsaAprendiz() throws IOException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		//contentPane.setLayout(null);
		
		odor();
		drawRadioButton(odorBtnGrp);
		
		next = new JButton();
		
		contentPane.add(next);
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//  Variables para
				
				// Daselas a la MLP
				//DataSource source;
				try {
					
					JRadioButton selected = getSelection();
				    System.out.println("se presiono: " + selected.getText());
					
					/*
					double lsp, asp, lpt, apt;
					lsp = Double.valueOf(txtLongitudSepalo.getText());
					asp = Double.valueOf(txtAnchoSepalo.getText());
					lpt = Double.valueOf(txtLongitudPetalo.getText());
					apt = Double.valueOf(txtAnchoPetalo.getText());*/
					DataSource source = new DataSource("agaricus-lepiota-prueba.arff");
					Instances ins = source.getStructure();
					ins.setClassIndex(ins.numAttributes()-1);
					
					DenseInstance ejemplo = new DenseInstance(ins.numAttributes());
					ejemplo.setDataset(ins);
					double attribute = getOdor(selected.getText());
					System.out.println(attribute);
					ejemplo.setValue(5, attribute);
					/*ejemplo.setValue(20, lsp);
					ejemplo.setValue(13, apt);
					ejemplo.setValue(19, lpt);
					
					double clase = mlp.classifyInstance(ejemplo);
					String nomclase = ins.classAttribute().value((int) clase); 
					
					
					BufferedImage flor = ImageIO.read(new File(nomclase + ".jpg"));
					Image img = flor.getScaledInstance(175, 175, NORMAL);
					ImageIcon imagen = new ImageIcon(img,"Iris");

					lblEs.setIcon(imagen);
					lblEs.setName(nomclase);
					lblEs.setSize(275, 175);
					lblEs.setText(nomclase);

					
					*/
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					
				}
				
			}
		});
		//btnClasifica.setBounds(100, 239, 117, 29);
		//contentPane.add(btnClasifica);
		
		//lblEs = new JLabel("Es ...");
		//lblEs.setBounds(323, 60, 61, 16);
		//contentPane.add(lblEs);
	}
	
	   private JRadioButton createRadioButton(String label, ImageIcon emptyIcon) {
		      JRadioButton rBtn = new JRadioButton(label, emptyIcon);
		      Icon icon;
		      BufferedImage gray = new BufferedImage(emptyIcon.getIconWidth(), emptyIcon.getIconHeight(), BufferedImage.TYPE_INT_BGR);
		      Graphics g = gray.createGraphics();
		    		  emptyIcon.paintIcon(null, g, 0, 0);
		      for(int i = 0; i < gray.getWidth(); i++ ) {
		    	  for(int j = 0; j < gray.getHeight(); j++) {
		    		  Color color = new Color(gray.getRGB(i, j)); 
		    		  int gr = color.getGreen();
		    		  gray.setRGB(i, j, new Color(0,gr,0).getRGB());
		    	  }
		      }
		      icon = new ImageIcon(gray);
		      rBtn.setSelectedIcon(icon);
		      return rBtn;
		   }
	   public void drawRadioButton(ButtonGroup btnGrp) {
		      //setLayout(new GridLayout(2, 2, 50, 5));
		      for (int i = 0; i < 9; i++) {
		    	 JRadioButton radioBtn = createRadioButton(odors[i],imgArray.get(i));
		    	 radioBtn.setVerticalTextPosition(JRadioButton.BOTTOM);
		    	 radioBtn.setHorizontalTextPosition(JRadioButton.CENTER);
		         btnGrp.add(radioBtn);
		         add(radioBtn);
		         
		      }
		}
	   
	   public void scaleImage() {
		   for(int i = 0; i < imgArray.size(); i++) {
			   Image scaleImage = imgArray.get(i).getImage().getScaledInstance(100, 100, Image.SCALE_AREA_AVERAGING);
				imgArray.set(i, new ImageIcon(scaleImage));
		   }
	   }
	   
	   public JRadioButton getSelection() {
		   for(Enumeration<AbstractButton> e = odorBtnGrp.getElements(); e.hasMoreElements();) {
				JRadioButton selected = (JRadioButton)e.nextElement();
				if(selected.getModel() == odorBtnGrp.getSelection()) {
					return selected;
				}
			}
		   return null;
	   }
	   
	   public void odor() {
		   imgArray.clear();
		   odor_00 = new ImageIcon("img/cap-shape/image_00.png");
		   imgArray.add(odor_00);
		   odor_01 = new ImageIcon("img/cap-shape/image_01.png");
		   imgArray.add(odor_01);
		   odor_02 = new ImageIcon("img/cap-shape/image_02.png");
		   imgArray.add(odor_02);
		   odor_03 = new ImageIcon("img/cap-shape/image_03.png");
		   imgArray.add(odor_03);
		   odor_04 = new ImageIcon("img/cap-shape/image_04.png");
		   imgArray.add(odor_04);
		   odor_05 = new ImageIcon("img/cap-shape/image_05.png");
		   imgArray.add(odor_05);
		   odor_06 = new ImageIcon("img/cap-shape/image_06.png");
		   imgArray.add(odor_06);
		   odor_07 = new ImageIcon("img/cap-shape/image_07.png");
		   imgArray.add(odor_07);
		   odor_08 = new ImageIcon("img/cap-shape/image_08.png");
		   imgArray.add(odor_08);
		   scaleImage();
	   }
	   public double getOdor(String odor) {
		   double at = -1.0;
		   switch(odor) {
		   case "almendra":
			   at = 0.0;
			   break;
		   case "anis":
			   at = 1.0;
			   break;
		   case "creosota":
			   at = 2.0;
			   break;
		   case "pescado":
			   at = 3.0;
			   break;
		   case  "podrido":
			   at = 4.0;
			   break;
		   case "musgoso":
			   at = 5.0;
			   break;
		   case "ninguno":
			   at = 6.0;
			   break;
		   case "picante0":
			   at = 7.0;
			   break;
		   case "picante":
			   at = 8.0;
			   break;
		   
		   }
		return at;
	   }
	   
}
