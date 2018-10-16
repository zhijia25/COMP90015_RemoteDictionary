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
package dictionary_client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.json.simple.JSONObject;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Client {

	public static void main(String[] args) {
		JSONObject newCommand = new JSONObject();
		try {
			String address = args[0];
			int port = Integer.parseInt(args[1]);
			Socket socket = new Socket(address, port);
			JFrame parent = new JFrame();
			JOptionPane.showMessageDialog(parent, "connect successfully to server " + address);
			DataInputStream input = new DataInputStream(socket.getInputStream());
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						GUI frame = new GUI(input, output);
						frame.setVisible(true);

						frame.addWindowListener(new WindowAdapter() {
							public void windowClosing(WindowEvent e) {
								try {
									newCommand.put("STATUS", "CLOSED");
									output.writeUTF(newCommand.toJSONString());
									output.flush();
									input.close();
									output.close();
								} catch (SocketException e2) {
									System.exit(0);
								} catch (IOException e1) {
									e1.printStackTrace();
									System.exit(0);
								}
								super.windowClosing(e);
							}
						});
						ClientListener ml = new ClientListener(input, frame);
						ml.start();
					} catch (Exception e) {
						e.printStackTrace();
						System.exit(0);
					}
				}
			});

		} catch (UnknownHostException e) {
			JFrame parent = new JFrame();
			JOptionPane.showMessageDialog(parent, "please enter correct address or port");
			System.exit(0);
		} catch (ArrayIndexOutOfBoundsException e) {
			JFrame parent = new JFrame();
			JOptionPane.showMessageDialog(parent, "please enter correct address or port");
			System.exit(0);
		} catch (NumberFormatException e) {
			JFrame parent = new JFrame();
			JOptionPane.showMessageDialog(parent, "please enter correct address or port");
			System.exit(0);
		} catch (ConnectException e1) {
			JFrame parent = new JFrame();
			JOptionPane.showMessageDialog(parent, "server disconnects");
			System.exit(0);
		} catch (SocketException e) {
			JFrame parent = new JFrame();
			JOptionPane.showMessageDialog(parent, "please enter correct address or port");
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}