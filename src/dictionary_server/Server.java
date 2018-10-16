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
package dictionary_server;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Server {
	private static ServerGUI frame;
	private static String address;

	public static void main(String[] args) {
		JSONParser parser = new JSONParser();
		ServerSocket listeningSocket = null;

		try {
			int port = Integer.parseInt(args[0]);
			address = args[1];
			Reader in = new FileReader(args[1]);

			JSONObject localDic = (JSONObject) parser.parse(in);
			in.close();
			listeningSocket = new ServerSocket(port);
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						frame = new ServerGUI();
						frame.setVisible(true);
						frame.addWindowListener(new WindowAdapter() {
							public void windowClosing(WindowEvent e) {
								try {
									FileWriter file;
									file = new FileWriter(address);
									String JSONText = localDic.toJSONString();
									file.write(JSONText);
									file.flush();
									JFrame parent = new JFrame();
									JOptionPane.showMessageDialog(parent, "Dictionary successfully saved!");
									System.exit(0);
									file.close();
								}

								catch (IOException e1) {

									e1.printStackTrace();
									System.exit(0);
								}
								super.windowClosing(e);
							}
						});
						frame.setAction(Thread.currentThread().getName()
								+ " - Server listening on port 4444 for a connection\n");

					} catch (Exception e) {
						e.printStackTrace();
						System.exit(0);
					}
				}
			});
			int clientNum = 0;
			while (true) {

				Socket clientSocket = listeningSocket.accept();
				frame.addAction(Thread.currentThread().getName() + " - Client " + clientNum + " conection accepted\n");
				clientNum++;

				SocketController clientConnection = new SocketController(clientSocket, localDic, clientNum, frame);

				clientConnection.start();
				ServerOperation.getInstance().clientConnected(clientConnection, frame);

			}
		} catch (ArrayIndexOutOfBoundsException e) {
			JFrame parent = new JFrame();
			JOptionPane.showMessageDialog(parent, "please enter correct address or port");
			System.exit(0);
		}

		catch (NumberFormatException e) {
			JFrame parent = new JFrame();
			JOptionPane.showMessageDialog(parent, "please enter correct address or port");
			System.exit(0);
		}

		catch (BindException e2) {
			JFrame parent = new JFrame();
			JOptionPane.showMessageDialog(parent, "alreadt a server is running!");
			System.exit(0);
		} catch (SocketException e) {
			JFrame parent = new JFrame();
			JOptionPane.showMessageDialog(parent, "please enter correct address or port");
			System.exit(0);
		}

		catch (FileNotFoundException e1) {
			JFrame parent = new JFrame();
			JOptionPane.showMessageDialog(parent, "No file found! Please create one!");
			System.exit(0);
		}

		catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		} finally {
			if (listeningSocket != null) {
				try {
					listeningSocket.close();

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			System.exit(0);
		}
	}
}