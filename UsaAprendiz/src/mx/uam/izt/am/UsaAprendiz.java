package mx.uam.izt.am;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
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
import weka.classifiers.trees.J48;
import weka.core.DenseInstance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class UsaAprendiz extends JFrame {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private static final int BI_WIDTH = 60;
	
	private JPanel generalContentPane;
	private JPanel odorContentPane;
	private JPanel sporeContentPane;
	private JPanel stalkContentPane;
	private JPanel ringContentPane;
	private JPanel buttonContentPane;
	private JPanel southContentPane; 
	
	private JLabel lblEs;
	private JLabel lblInstructions;
	private JLabel lblOdor;
	private JLabel lblSpore;
	private JLabel lblStalk;
	private JLabel lblRing;

	private ImageIcon odor_00, odor_01, odor_02, odor_03, odor_04, odor_05, odor_06, odor_07, odor_08;
	private ImageIcon spore_00, spore_01, spore_02, spore_03, spore_04, spore_05, spore_06, spore_07, spore_08;
	private ImageIcon stalk_00, stalk_01, stalk_02, stalk_03;
	private ImageIcon ring_00, ring_01, ring_02, ring_03, ring_04, ring_05, ring_06, ring_07;

	private ButtonGroup odorBtnGrp = new ButtonGroup();
	private ButtonGroup sporeBtnGrp = new ButtonGroup();
	private ButtonGroup stalkBtnGrp = new ButtonGroup();
	private ButtonGroup ringBtnGrp = new ButtonGroup();

    private JButton next;
    private ArrayList<ImageIcon> odorImgArray = new ArrayList<ImageIcon>();
	private ArrayList<ImageIcon> sporeImgArray = new ArrayList<ImageIcon>();
	private ArrayList<ImageIcon> stalkImgArray = new ArrayList<ImageIcon>();
	private ArrayList<ImageIcon> ringImgArray = new ArrayList<ImageIcon>();
	
	private String[] odors = {"almendra","anis","creosota", "pescado", "podrido", "musgoso", "ninguno", "picante0", "picante"};
	private String[] spores = {"negro", "cafe", "piel", "chocolate", "verde", "naranja", "purpura", "blanco", "amarillo"};
	private String[] stalks = {"fibroso", "escamoso","sedoso", "liso"};
	private String[] rings = {"telaraña", "evanescente", "flaring", "largo", "ninguno", "colgante", "arriba", "zone"};

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
		next = new JButton("Resultado");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1050, 550);
		
		generalContentPane = new JPanel(new GridLayout(4,10));
		southContentPane = new JPanel(new GridLayout(1,2));
		
		odorContentPane = new JPanel();
		sporeContentPane = new JPanel();
		stalkContentPane = new JPanel();
		ringContentPane = new JPanel();
		buttonContentPane = new JPanel();
		
		lblInstructions = new JLabel("Seleccione una imagen de cada uno de los atributos que se le muestran a continuación.\n");
		lblOdor = new JLabel("Su olor es parecido a:");
		lblSpore = new JLabel("Su impresion de esporas es de color:");
		lblStalk = new JLabel("La superficie del tallo es:");
		lblRing = new JLabel("El tipo de su(s) anillo(s) es:");
		odorContentPane.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		sporeContentPane.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		stalkContentPane.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		ringContentPane.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		
		
		odor();
		spore();
		stalk();
		ring();
		
		odorContentPane.add(lblOdor);
		drawRadioButton(odorBtnGrp, odors, odorContentPane, odorImgArray);
		sporeContentPane.add(lblSpore);
		drawRadioButton(sporeBtnGrp, spores, sporeContentPane, sporeImgArray);
		stalkContentPane.add(lblStalk);
		drawRadioButton(stalkBtnGrp, stalks, stalkContentPane, stalkImgArray);
		ringContentPane.add(lblRing);
		drawRadioButton(ringBtnGrp, rings, ringContentPane, ringImgArray);
		
		buttonContentPane.add(next,
				BorderLayout.EAST);
		generalContentPane.add(odorContentPane);
		generalContentPane.add(sporeContentPane);
		generalContentPane.add(stalkContentPane);
		generalContentPane.add(ringContentPane);
		
		southContentPane.add(buttonContentPane);
		
		
		add(lblInstructions, BorderLayout.NORTH);
		add(generalContentPane, BorderLayout.CENTER);
		add(southContentPane, BorderLayout.SOUTH);
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

					JRadioButton selected = getSelection(odorBtnGrp);

					DataSource source = new DataSource("agaricus-lepiota-prueba.arff");
					Instances ins = source.getStructure();
					ins.setClassIndex(ins.numAttributes()-1);

					DenseInstance ejemplo = new DenseInstance(ins.numAttributes());
					ejemplo.setDataset(ins);

					double odorAtt = getOdor(selected.getText());
					selected = getSelection(sporeBtnGrp);
					double sporeAtt = getSpore(selected.getText());
					selected = getSelection(stalkBtnGrp);
					double stalkAtt = getStalk(selected.getText());
					selected = getSelection(ringBtnGrp);
					double ringAtt = getRing(selected.getText());

					System.out.println(odorAtt);
					System.out.println(sporeAtt);
					System.out.println(stalkAtt);
					System.out.println(ringAtt);

					ejemplo.setValue(4, odorAtt);
					ejemplo.setValue(19, sporeAtt);
					ejemplo.setValue(12, stalkAtt);
					ejemplo.setValue(18, ringAtt);
					
					double clase = mlp.classifyInstance(ejemplo);
					String nomclase = getClase(ins.classAttribute().value((int) clase));

					System.out.println(nomclase);
					BufferedImage clas = ImageIO.read(new File("img/class/" + nomclase + ".png"));
					Image img = clas.getScaledInstance(60, 60, NORMAL);
					ImageIcon imagen = new ImageIcon(img,"class");

					lblEs.setIcon(imagen);
					lblEs.setName(nomclase);
					lblEs.setSize(100, 100);
					lblEs.setText(nomclase);
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();

				}

			}
		});
		next.setBounds(100, 239, 117, 29);
		

		lblEs = new JLabel("Es ...");
		lblEs.setBounds(323, 60, 61, 16);
		southContentPane.add(lblEs);
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
	   public void drawRadioButton(ButtonGroup btnGrp, String[] label, JPanel panel, ArrayList<ImageIcon> im) {
		      for (int i = 0; i < im.size(); i++) {
		    	 JRadioButton radioBtn = createRadioButton(label[i],im.get(i));
		    	 radioBtn.setVerticalTextPosition(JRadioButton.BOTTOM);
		    	 radioBtn.setHorizontalTextPosition(JRadioButton.CENTER);
		         btnGrp.add(radioBtn);
		         panel.add(radioBtn);

		      }
		}

	   public void scaleImage(ArrayList<ImageIcon> im) {
		   for(int i = 0; i < im.size(); i++) {
			   Image scaleImage = im.get(i).getImage().getScaledInstance(BI_WIDTH, BI_WIDTH, Image.SCALE_AREA_AVERAGING);
				im.set(i, new ImageIcon(scaleImage));
		   }
	   }

	   public JRadioButton getSelection(ButtonGroup elements) {
		   for(Enumeration<AbstractButton> e = elements.getElements(); e.hasMoreElements();) {
				JRadioButton selected = (JRadioButton)e.nextElement();
				if(selected.getModel() == elements.getSelection()) {
					return selected;
				}
			}
		   return null;
	   }

	   public void odor() {
		   odorImgArray.clear();
		   odor_00 = new ImageIcon("img/odor/image_00.png");
		   odorImgArray.add(odor_00);
		   odor_01 = new ImageIcon("img/odor/image_01.png");
		   odorImgArray.add(odor_01);
		   odor_02 = new ImageIcon("img/odor/image_02.png");
		   odorImgArray.add(odor_02);
		   odor_03 = new ImageIcon("img/odor/image_03.png");
		   odorImgArray.add(odor_03);
		   odor_04 = new ImageIcon("img/odor/image_04.png");
		   odorImgArray.add(odor_04);
		   odor_05 = new ImageIcon("img/odor/image_05.png");
		   odorImgArray.add(odor_05);
		   odor_06 = new ImageIcon("img/odor/image_06.png");
		   odorImgArray.add(odor_06);
		   odor_07 = new ImageIcon("img/odor/image_07.png");
		   odorImgArray.add(odor_07);
		   odor_08 = new ImageIcon("img/odor/image_08.png");
		   odorImgArray.add(odor_08);
		   scaleImage(odorImgArray);
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

	   public void spore() {
			 sporeImgArray.clear();
		   spore_00 = new ImageIcon("img/spore-print-color/image_00.png");
		   sporeImgArray.add(spore_00);
		   spore_01 = new ImageIcon("img/spore-print-color/image_01.png");
		   sporeImgArray.add(spore_01);
		   spore_02 = new ImageIcon("img/spore-print-color/image_02.png");
		   sporeImgArray.add(spore_02);
		   spore_03 = new ImageIcon("img/spore-print-color/image_03.png");
		   sporeImgArray.add(spore_03);
		   spore_04 = new ImageIcon("img/spore-print-color/image_04.png");
		   sporeImgArray.add(spore_04);
		   spore_05 = new ImageIcon("img/spore-print-color/image_05.png");
		   sporeImgArray.add(spore_05);
		   spore_06 = new ImageIcon("img/spore-print-color/image_06.png");
		   sporeImgArray.add(spore_06);
		   spore_07 = new ImageIcon("img/spore-print-color/image_07.png");
		   sporeImgArray.add(spore_07);
		   spore_08 = new ImageIcon("img/spore-print-color/image_08.png");
		   sporeImgArray.add(spore_08);
		   scaleImage(sporeImgArray);
	   }
		 public double getSpore(String spore) {
		   double at = -1.0;
		   switch(spore) {
		   case "negro":
			   at = 0.0;
			   break;
		   case "cafe":
			   at = 1.0;
			   break;
		   case "piel":
			   at = 2.0;
			   break;
		   case "chocolate":
			   at = 3.0;
			   break;
		   case  "verde":
			   at = 4.0;
			   break;
		   case "naranja":
			   at = 5.0;
			   break;
		   case "purpura":
			   at = 6.0;
			   break;
		   case "blanco":
			   at = 7.0;
			   break;
		   case "amarillo":
			   at = 8.0;
			   break;

		   }
		return at;
	   }
		 public void stalk() {
		   stalkImgArray.clear();
		   stalk_00 = new ImageIcon("img/stalk-surface-below-ring/image_00.png");
		   stalkImgArray.add(stalk_00);
		   stalk_01 = new ImageIcon("img/stalk-surface-below-ring/image_01.png");
		   stalkImgArray.add(stalk_01);
		   stalk_02 = new ImageIcon("img/stalk-surface-below-ring/image_02.png");
		   stalkImgArray.add(stalk_02);
		   stalk_03 = new ImageIcon("img/stalk-surface-below-ring/image_03.png");
		   stalkImgArray.add(stalk_03);
			 scaleImage(stalkImgArray);
		 }
	   public double getStalk(String stalk) {
		   double at = -1.0;
		   switch(stalk) {
		   case "fibroso":
			   at = 0.0;
			   break;
		   case "escamoso":
			   at = 1.0;
			   break;
		   case "sedoso":
			   at = 2.0;
			   break;
		   case "liso":
			   at = 3.0;
			   break;
		   }
		return at;
	   }
		 public void ring() {
		   ringImgArray.clear();
		   ring_00 = new ImageIcon("img/ring-type/image_00.png");
		   ringImgArray.add(ring_00);
		   ring_01 = new ImageIcon("img/ring-type/image_01.png");
		   ringImgArray.add(ring_01);
		   ring_02 = new ImageIcon("img/ring-type/image_02.png");
		   ringImgArray.add(ring_02);
		   ring_03 = new ImageIcon("img/ring-type/image_03.png");
		   ringImgArray.add(ring_03);
		   ring_04 = new ImageIcon("img/ring-type/image_04.png");
		   ringImgArray.add(ring_04);
		   ring_05 = new ImageIcon("img/ring-type/image_05.png");
		   ringImgArray.add(ring_05);
		   ring_06 = new ImageIcon("img/ring-type/image_06.png");
		   ringImgArray.add(ring_06);
		   ring_07 = new ImageIcon("img/ring-type/image_07.png");
		   ringImgArray.add(ring_07);
		   scaleImage(ringImgArray);
	   }
	   public double getRing(String ring) {
		   double at = -1.0;
		   switch(ring) {
		   case "telaraña":
			   at = 0.0;
			   break;
		   case "evanescente":
			   at = 1.0;
			   break;
		   case "flaring":
			   at = 2.0;
			   break;
		   case "largo":
			   at = 3.0;
			   break;
		   case  "ninguno":
			   at = 4.0;
			   break;
		   case "colgante":
			   at = 5.0;
			   break;
		   case "arriba":
			   at = 6.0;
			   break;
		   case "zone":
			   at = 7.0;
			   break;
		   }
		return at;
	   }
	   
	   public String getClase(String c) {
		   if (c == "e") 
			   return "comestible";
		   else return "venenoso";
	   }
}
