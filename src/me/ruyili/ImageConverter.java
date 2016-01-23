package me.ruyili;

import java.applet.*;
import java.awt.event.*;
import java.io.*;
import java.awt.image.*;
import java.awt.*;
import javax.imageio.*;
import javax.swing.*;
import javax.swing.filechooser.*;

public class ImageConverter extends Applet{
	
	final JTextField in = new JTextField(13);
	JButton browse = new JButton("Browse...");
	
	final JTextField out = new JTextField(13);
	JButton output = new JButton("Output as...");
	
	JButton convert = new JButton("Convert");
	
	final String[] s1 = {"/"};
	final String[] s2 = {"/"};
	JLabel l = new JLabel();
	
	public void init(){
		
		in.setEditable(false);
		in.setText("Image directory");
		out.setEditable(false);
		out.setText("Output directory");
		
		setSize(350, 125);
		setVisible(true);
		add(in);
		add(browse);
		add(out);
		add(output);
		add(convert);
		add(l);
		
		browse.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {

				JFileChooser jfc = new JFileChooser();
				jfc.setFileFilter(new FileNameExtensionFilter("Image File", "GIF", "BMP", "PNG"));
	            jfc.setCurrentDirectory(new File("."));
	            jfc.setDialogTitle("Browse image...");
	            jfc.setAcceptAllFileFilterUsed(false);
	            
	            if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
	                in.setText("" + jfc.getSelectedFile());
	                s1[0] = in.getText();
	            }
				
			}
			
		});
		
		output.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {

				JFileChooser jfc1 = new JFileChooser();
				jfc1.setFileFilter(new FileNameExtensionFilter("(.jpg) ", "jpg"));
	            jfc1.setDialogTitle("Output as...");
	            jfc1.setAcceptAllFileFilterUsed(false);
	            
	            if (jfc1.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
	                out.setText("" + jfc1.getSelectedFile()+".jpg");
	                s2[0] = out.getText();
	            }
				
			}
		});
		
		convert.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(s1[0] == "/"){
					l.setText("You didn't specify the image input directory!");
					revalidate();
				}else if(s2[0] == "/"){
					l.setText("You didn't specify the image output directory!");
					revalidate();
				}else{
					try {
						pngToJpg(s1[0], s2[0]);
						l.setText("The file was successfully converted!");
						revalidate();
					} catch (IOException e1) {
						l.setText("Invalid input!");
						revalidate();
					}
				}
			}
			
		});
	}
	
	public void pngToJpg(String from, String to) throws IOException{
		File fr = new File(from);
		File t = new File(to);
		
		BufferedImage bi = ImageIO.read(fr);
		BufferedImage newBI = new BufferedImage(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_INT_RGB);
		newBI.createGraphics().drawImage(bi, 0, 0, Color.WHITE, null);
		ImageIO.write(newBI, out.getText().substring(out.getText().length() - 3), t);
	}
}
