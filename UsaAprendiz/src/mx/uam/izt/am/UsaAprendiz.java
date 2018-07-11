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

import javax.imageio.ImageIO;
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
	
	private ImageIcon image_00;
	private ImageIcon image_01;
	private ImageIcon image_02;
	private ImageIcon image_03;
	private ImageIcon image_04;
	private ImageIcon image_05;
	private ImageIcon image_06;
	private ImageIcon image_07;
	private ImageIcon image_08;
	private ImageIcon image_09;
	private ImageIcon image_10;
	private ImageIcon image_11;
	private ImageIcon image_12;
	
	private ButtonGroup btnGrp = new ButtonGroup();
    private JButton button ;
    private JButton next;
    private JButton finish;
	private ArrayList<ImageIcon> imgArray = new ArrayList();
	
	static MultilayerPerceptron mlp;

	
	/**
	 * Launch the application.
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
		
		// Lee el modelo 
		ObjectInputStream ois = new ObjectInputStream(
												new FileInputStream("IrisNN.model"));
		mlp = (MultilayerPerceptron) ois.readObject();
		
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
		
		capShape();
		scaleImage();
		drawRadioButton();
		//button = new JButton(new ImageIcon(scaleImage));
		
		//button.setBounds(6,6,100,100);
		//button.setBorder(BorderFactory.createBevelBorder(1,Color.BLACK, Color.GRAY));
		//button.setContentAreaFilled(false);
		
		//contentPane.add(button);
		/*btnClasificabutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//  Variables para
				
				// Daselas a la MLP
				DataSource source;
				button.setBorderPainted(true);
				button.setBorder(BorderFactory.createBevelBorder(1,Color.blue, Color.DARK_GRAY));
				button.setOpaque(true);//setRolloverEnabled(true);
				try {
					double lsp, asp, lpt, apt;
					
					lsp = Double.valueOf(txtLongitudSepalo.getText());
					asp = Double.valueOf(txtAnchoSepalo.getText());
					lpt = Double.valueOf(txtLongitudPetalo.getText());
					apt = Double.valueOf(txtAnchoPetalo.getText());
					source = new DataSource("iris.arff");
					Instances ins = source.getStructure();
					ins.setClassIndex(ins.numAttributes()-1);
					
					DenseInstance ejemplo = new DenseInstance(ins.numAttributes());
										ejemplo.setDataset(ins);

					ejemplo.setValue(0, asp);
					ejemplo.setValue(1, lsp);
					ejemplo.setValue(2, apt);
					ejemplo.setValue(3, lpt);
					
					double clase = mlp.classifyInstance(ejemplo);
					String nomclase = ins.classAttribute().value((int) clase); 
					
					
					BufferedImage flor = ImageIO.read(new File(nomclase + ".jpg"));
					Image img = flor.getScaledInstance(175, 175, NORMAL);
					ImageIcon imagen = new ImageIcon(img,"Iris");

					lblEs.setIcon(imagen);
					lblEs.setName(nomclase);
					lblEs.setSize(275, 175);
					lblEs.setText(nomclase);

					
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});*/
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
		    		  int r = color.getRed();
		    		  gray.setRGB(i, j, new Color(r,0,0).getRGB());
		    	  }
		      }
		      icon = new ImageIcon(gray);
		      rBtn.setSelectedIcon(icon);
		      return rBtn;
		   }
	   public void drawRadioButton() {
		      setLayout(new GridLayout(2, 2, 50, 5));
		      for (int i = 0; i < 6; i++) {
		    	 JRadioButton radioBtn = createRadioButton(Integer.toString(i),imgArray.get(i));
		         btnGrp.add(radioBtn);;
		         add(radioBtn);
		      }
		}
	   
	   public void scaleImage() {
		   for(int i = 0; i < imgArray.size(); i++) {
			   Image scaleImage = imgArray.get(i).getImage().getScaledInstance(100, 100, Image.SCALE_AREA_AVERAGING);
				imgArray.set(i, new ImageIcon(scaleImage));
		   }
	   }
	   
	   public void capShape() {
		   imgArray.clear();
		   image_00 = new ImageIcon("img/cap-shape/image_00.png");
		   imgArray.add(image_00);
		   image_01 = new ImageIcon("img/cap-shape/image_01.png");
		   imgArray.add(image_01);
		   image_02 = new ImageIcon("img/cap-shape/image_02.png");
		   imgArray.add(image_02);
		   image_03 = new ImageIcon("img/cap-shape/image_03.png");
		   imgArray.add(image_03);
		   image_04 = new ImageIcon("img/cap-shape/image_04.png");
		   imgArray.add(image_04);
		   image_05 = new ImageIcon("img/cap-shape/image_05.png");
		   imgArray.add(image_05);
		   image_06 = new ImageIcon("img/cap-shape/image_06.png");
		   imgArray.add(image_06);
		   image_07 = new ImageIcon("img/cap-shape/image_07.png");
		   imgArray.add(image_07);
		   image_08 = new ImageIcon("img/cap-shape/image_08.png");
		   imgArray.add(image_08);
		   image_09 = new ImageIcon("img/cap-shape/image_09.png");
		   imgArray.add(image_09);
	   }
}
