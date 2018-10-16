/**
 * The University of Melbourne
 * COMP90015 Distributed Systems 
 * 
 * @author  Zhijia Lu
 * @Student ID  921715
 * @Username  zhijial
 * @E-mail.addr  zhijial@student.unimelb.edu.au
 * 
 * @Date  6/9/2018 
 */
package dictionary_client;

import java.awt.BorderLayout;
import org.json.simple.JSONObject;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import javax.swing.SwingConstants;
import java.awt.Component;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Window.Type;
import java.awt.SystemColor;
import javax.swing.JTextArea;

public class GUI extends JFrame {

	private JPanel contentPane;
	private JTextField entry;
	private JTextArea meaning;
	private JTextArea output;
	private JTextArea status;
	private DataInputStream reader;
	private DataOutputStream writer;
	JSONObject newCommand = new JSONObject();

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public GUI(DataInputStream in, DataOutputStream out) {
		setTitle("Dictionary");

		this.reader = in;
		this.writer = out;

		setBackground(new Color(119, 136, 153));
		setForeground(new Color(255, 0, 0));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 835, 517);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setForeground(Color.GREEN);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnNewButton = new JButton("ADD");
		btnNewButton.setBackground(new Color(240, 128, 128));
		btnNewButton.setAlignmentY(Component.TOP_ALIGNMENT);
		btnNewButton.setFont(new Font("Tahoma", Font.ITALIC, 40));
		btnNewButton.setForeground(new Color(0, 0, 0));
		btnNewButton.setBounds(10, 191, 191, 54);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (!entry.getText().equals("") && !meaning.getText().equals("")) {
					try {

						newCommand.put("ACTION", "ADD");
						newCommand.put("ENTRY", entry.getText());
						newCommand.put("MEANING", meaning.getText());
						writer.writeUTF(newCommand.toJSONString());
						writer.flush();
					} catch (SocketException e) {
						JFrame parent = new JFrame();
						JOptionPane.showMessageDialog(parent, "server disconnects");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else {
					JFrame parent = new JFrame();
					JOptionPane.showMessageDialog(parent, "Please input a word and a meaning to be added!");

				}

			}
		});
		contentPane.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("REMOVE");
		btnNewButton_1.setBackground(new Color(240, 128, 128));
		btnNewButton_1.setForeground(new Color(0, 0, 0));
		btnNewButton_1.setFont(new Font("Tahoma", Font.ITALIC, 40));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (!entry.getText().equals("") && meaning.getText().equals("")) {
					try {

						newCommand.put("ACTION", "REMOVE");
						newCommand.put("ENTRY", entry.getText());
						writer.writeUTF(newCommand.toJSONString());
						writer.flush();
					} catch (SocketException e) {
						JFrame parent = new JFrame();
						JOptionPane.showMessageDialog(parent, "server disconnects");
					}

					catch (IOException e) {

						e.printStackTrace();
					}

				} else {
					JFrame parent = new JFrame();
					JOptionPane.showMessageDialog(parent, "Please input only one word to be removed!");

				}

			}
		});
		btnNewButton_1.setBounds(10, 289, 191, 54);
		contentPane.add(btnNewButton_1);
		JButton btnNewButton_2 = new JButton("QUERY");
		btnNewButton_2.setBackground(new Color(240, 128, 128));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (!entry.getText().equals("") && meaning.getText().equals("")) {

					try {

						newCommand.put("ACTION", "QUERY");
						newCommand.put("ENTRY", entry.getText());
						writer.writeUTF(newCommand.toJSONString());
						writer.flush();
					} catch (SocketException e) {
						JFrame parent = new JFrame();
						JOptionPane.showMessageDialog(parent, "server disconnects");
					}

					catch (IOException e) {

						e.printStackTrace();
					}

				} else {
					JFrame parent = new JFrame();
					JOptionPane.showMessageDialog(parent, "Please input only one word to be queried!");

				}

			}
		});
		btnNewButton_2.setFont(new Font("Tahoma", Font.ITALIC, 40));
		btnNewButton_2.setForeground(new Color(0, 0, 0));
		btnNewButton_2.setBounds(10, 385, 191, 54);
		contentPane.add(btnNewButton_2);

		entry = new JTextField();
		entry.setBackground(Color.LIGHT_GRAY);
		entry.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		entry.setBorder(new LineBorder(new Color(171, 173, 179)));
		entry.setBounds(10, 46, 177, 108);
		entry.setForeground(Color.DARK_GRAY);
		contentPane.add(entry);
		entry.setColumns(10);

		meaning = new JTextArea();
		meaning.setSelectionColor(new Color(255, 0, 0));
		meaning.setBackground(Color.LIGHT_GRAY);
		meaning.setBounds(299, 46, 512, 108);
		meaning.setColumns(10);
		meaning.setLineWrap(true);
		meaning.setWrapStyleWord(true);
		JScrollPane scrollPane1 = new JScrollPane();
		scrollPane1.setBounds(299, 46, 512, 108);
		this.getContentPane().add(scrollPane1);
		scrollPane1.setViewportView(meaning);

		output = new JTextArea();
		output.setFont(new Font("Monospaced", Font.PLAIN, 20));
		output.setBackground(Color.GRAY);
		output.setForeground(new Color(255, 0, 0));
		output.setEditable(false);
		output.setBounds(299, 191, 512, 248);
		output.setColumns(10);
		output.setLineWrap(true);
		output.setWrapStyleWord(true);
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(299, 191, 512, 248);
		this.getContentPane().add(scrollPane_1);
		scrollPane_1.setViewportView(output);

		JLabel lblEntry = new JLabel("ENTRY:");
		lblEntry.setForeground(new Color(0, 128, 128));
		lblEntry.setFont(new Font("ËÎÌו", Font.BOLD, 15));
		lblEntry.setBounds(10, 10, 58, 33);
		contentPane.add(lblEntry);

		JLabel lblMeaning = new JLabel("MEANING:");
		lblMeaning.setForeground(new Color(0, 128, 128));
		lblMeaning.setFont(new Font("ËÎÌו", Font.BOLD, 15));
		lblMeaning.setBounds(218, 12, 141, 28);
		contentPane.add(lblMeaning);

		JLabel lblOutput = new JLabel("OUTPUT:");
		lblOutput.setForeground(new Color(0, 128, 128));
		lblOutput.setFont(new Font("ËÎÌו", Font.BOLD, 15));
		lblOutput.setBounds(228, 191, 105, 54);
		contentPane.add(lblOutput);
	}

	public void setOutput(String theOutput) {
		output.setText(theOutput);
	}
}
