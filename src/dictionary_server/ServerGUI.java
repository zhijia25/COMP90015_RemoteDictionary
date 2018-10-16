/**
 * The University of Melbourne
 * COMP90015 Distributed Systems 
 * 
 * @author  Zhijia Lu
 * @Student ID  921715
 * @Username  zhijial
 * @E-mail.addr  zhijial@student unimelb.edu.au
 * 
 * @Date  6/9/2018 
 */
package dictionary_server;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Window.Type;
import java.awt.CardLayout;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextArea;
import java.awt.SystemColor;

public class ServerGUI extends JFrame {

	private JPanel contentPane;
	private JTextArea action, client;

	public ServerGUI() {
		setTitle("Server");
		setForeground(new Color(255, 0, 0));
		setBackground(new Color(119, 136, 153));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 563, 395);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblLoadClientNumber = new JLabel("load client:");
		lblLoadClientNumber.setFont(new Font("ËÎÌו", Font.BOLD, 20));
		lblLoadClientNumber.setForeground(new Color(0, 128, 128));
		lblLoadClientNumber.setBounds(10, 206, 265, 78);
		contentPane.add(lblLoadClientNumber);

		JLabel lblLatestActions = new JLabel("latest actions:");
		lblLatestActions.setForeground(new Color(0, 128, 128));
		lblLatestActions.setFont(new Font("ËÎÌו", Font.BOLD, 20));
		lblLatestActions.setBounds(10, 10, 257, 40);
		contentPane.add(lblLatestActions);

		action = new JTextArea();
		action.setEditable(false);
		action.setLineWrap(true);
		action.setBackground(Color.GRAY);
		action.setBounds(10, 60, 523, 162);
		action.setLineWrap(true);
		action.setWrapStyleWord(true);
		JScrollPane scrollPane1 = new JScrollPane();
		scrollPane1.setBounds(10, 60, 523, 162);
		this.getContentPane().add(scrollPane1);
		scrollPane1.setViewportView(action);

		client = new JTextArea();
		client.setEditable(false);
		client.setLineWrap(true);
		client.setBackground(Color.GRAY);
		client.setBounds(10, 270, 523, 78);
		client.setLineWrap(true);
		client.setWrapStyleWord(true);
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 270, 523, 78);
		this.getContentPane().add(scrollPane_1);
		scrollPane_1.setViewportView(client);

	}

	public void addAction(String theOutput) {
		action.append(theOutput);
	}

	public void setAction(String theOutput) {
		action.setText(theOutput);
	}

	public void seClient(String theOutput) {
		client.setText(theOutput);
	}

}
