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

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.SocketException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ClientListener extends Thread {
	private DataInputStream reader;
	private GUI frame;

	public ClientListener(DataInputStream reader, GUI frame) {
		this.reader = reader;
		this.frame = frame;
	}

	public void run() {

		try {

			String msg = null;
			while (true) {
				JSONParser parser = new JSONParser();
				if ((reader.available() > 0)) {
					msg = reader.readUTF();
					JSONObject reply = (JSONObject) parser.parse(msg);
					frame.setOutput("  " + (String) reply.get("ANSWER"));
				}
			}
		}

		catch (SocketException e) {
			JFrame parent = new JFrame();
			JOptionPane.showMessageDialog(parent, "client disconnects");
			System.exit(0);
		} catch (IOException e) {
			JFrame parent = new JFrame();
			JOptionPane.showMessageDialog(parent, "client disconnects");
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

	}
}
