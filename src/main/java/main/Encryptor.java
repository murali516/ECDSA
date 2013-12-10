package main;

import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;

import javax.swing.JLabel;

import java.awt.TextField;

public class Encryptor extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane = new JPanel();
	Md5Hash md5Val = new Md5Hash();
	ECCSign sign = new ECCSign();
	/**
	 * Launch the application.
	 */
	
	  public static void main(String args[]) {
		  Encryptor sfc = new Encryptor();
		    sfc.setVisible(true);
		  }
	
	
	public Encryptor() {
		
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		
		contentPane.setBorder(new MatteBorder(3, 3, 3, 3, (Color) Color.GRAY));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblResult = new JLabel("");
		lblResult.setBounds(56, 20, 0, 0);
		
		contentPane.add(lblResult);
		
		JButton openButton = new JButton("Generate MD5");
		openButton.setBounds(36, 129, 188, 25);
		final JTextField statusbar = 
	                 new JTextField("");
		statusbar.setBounds(24, 35, 401, 58);
		
		openButton.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent ae) {
		        JFileChooser chooser = new JFileChooser();
		        chooser.setMultiSelectionEnabled(true);
		        chooser.setFileHidingEnabled(false);
		        int option = chooser.showOpenDialog(Encryptor.this);
		        
		        if (option == JFileChooser.APPROVE_OPTION) {
		        	
		        	File file = chooser.getSelectedFile();
		        	
		        	if(file == null){
		        		statusbar.setText("The file selected is null");
		        	}else{
		        		statusbar.setText(md5Val.getMD5Hash(file));
		        	}
		      }
		      }
		    });
		contentPane.add(openButton);
		contentPane.add(statusbar);
		
		JButton btnGenerateSignature = new JButton("Generate Signature");
		btnGenerateSignature.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
		        chooser.setMultiSelectionEnabled(true);
		        chooser.setFileHidingEnabled(false);
		        int option = chooser.showOpenDialog(Encryptor.this);
		        
		        if (option == JFileChooser.APPROVE_OPTION) {
		        	
		        	File file = chooser.getSelectedFile();
		        	
		        	if(file == null){
		        		statusbar.setText("The file selected is null");
		        	}else{
		        		try {
							statusbar.setText(sign.getSignature(file));
							
						} catch (InvalidKeyException e) {
							e.printStackTrace();
						} catch (NoSuchAlgorithmException e) {
							e.printStackTrace();
						} catch (NoSuchProviderException e) {
							e.printStackTrace();
						} catch (InvalidAlgorithmParameterException e) {
							e.printStackTrace();
						} catch (SignatureException e) {
							e.printStackTrace();
						} catch (InvalidKeySpecException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
		        	}
		      }
			}
		});
		btnGenerateSignature.setBounds(36, 179, 188, 25);
		contentPane.add(btnGenerateSignature);
		
	}
}
