import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.swing.*;

import clarifai2.exception.ClarifaiException;

public class MLGui{
	public static JFrame frame = new JFrame("ML");
	public MLbot bot;
	//public Transferable t;
	
	public MLGui(){
		bot = new MLbot();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000,1000);
		frame.setLayout(null);
		
		//t = null;
		JLabel text = new JLabel("Paste URL");
		text.setBounds(425,300,1000,200);
		frame.add(text);
		
		JTextField textBar = new JTextField();
		textBar.setBounds(300, 500,300,50);
		frame.add(textBar);
			
		JButton button = new JButton("Search");
		button.setBounds(350, 700, 200, 200);
		button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {	
				try {
					List<String> data = bot.getData(textBar.getText());
					int i=0;
					String search = "";
					for(String s: data ){
						if (!s.equals("portrait")){
							for(int d=0; d <s.length()-1;d++){
								if(s.substring(d, d+1).equals(" ")){
									s = s.substring(0, d) + s.substring(d+1);
								}
							}
							search+= "_" + s;
							if(i==1){
								break;
							}
							i++;
						}
					}
					System.out.println(search);
					List<String> theList = bot.search(search);
					
					FileWriter writer = new FileWriter("output.txt"); 
					for(String str: theList) {
					  writer.write(str + "\n");
					}
					writer.close();
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(frame, "Error");
				}catch(ClarifaiException e2){
					JOptionPane.showMessageDialog(frame, "Invalid URL");

				}
			}
			
		});
		frame.add(button);
		
		
		/*
		 * String myString = "1This text will be copied into clipboard when running this code!";
		StringSelection stringSelection = new StringSelection(myString);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);
		Transferable t = null;
		 try {
			 t = clipboard.getContents(null);
	         if (t.isDataFlavorSupported(DataFlavor.stringFlavor))
	            System.out.println(t.getTransferData(DataFlavor
	               .stringFlavor));
	      } catch (UnsupportedFlavorException | IOException ex) {
	          System.out.println("");
	      }
	   JButton newButton = new JButton("Copy Links");
	   button.setBounds(0,0, 200, 200);
	   button.addActionListener(new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			try {
				Object xd = t.getTransferData(DataFlavor
				           .stringFlavor);
			} catch (UnsupportedFlavorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		   
	   }); */
		 
		frame.setVisible(true);
		
	}
	public static void main(String[] args){
		MLGui gui = new MLGui();
	}
}
