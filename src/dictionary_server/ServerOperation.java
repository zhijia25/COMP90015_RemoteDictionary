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

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ServerOperation {

	private List<SocketController> clients;
	private static ServerOperation instance;

	public ServerOperation() {
		clients = new ArrayList<SocketController>();
	}

	public static synchronized ServerOperation getInstance() {
		if (instance == null) {
			instance = new ServerOperation();
		}
		return instance;
	}

	public static synchronized String add(JSONObject dic, String entry, String meaning) {
		try {
			if (dic.get(entry) == null) {
				dic.put(entry, meaning);
				return "add successfully";
			} else {
				return "the word is already in the dictionary, you cannot add!";
			}

		} catch (Exception e) {
			return "add unsuccessfully";

		}
	}

	public static synchronized String remove(JSONObject dic, String entry) {
		try {
			if (dic.get(entry) == null) {
				return "no such word in the dictionary";
			} else {

				dic.remove(entry);
				return "remove successfully";
			}
		} catch (Exception e) {
			return "remove unsuccessfully";
		}
	}

	public static synchronized String query(JSONObject dic, String entry) {
		try {
			if (dic.get(entry) == null) {
				return "no such word in the dictionary";
			} else {
				return (String) dic.get(entry);
			}

		} catch (Exception e) {
			return "query unsuccessfully";
		}
	}

	public synchronized void clientConnected(SocketController clientConnection, ServerGUI frame) {
		clients.add(clientConnection);
		frame.seClient("");
		String output = "client online:";
		for (int i = 0; i < clients.size(); i++) {
			output = output + " " + clients.get(i).getClientNum() + ",";
		}
		frame.seClient(output);

	}

	public synchronized void clientDisconnected(SocketController clientConnection, ServerGUI frame) {
		clients.remove(clientConnection);
		frame.seClient("");
		String output = "client online:";
		for (int i = 0; i < clients.size(); i++) {
			output = output + " " + clients.get(i).getClientNum() + ",";
		}
		frame.seClient(output);

	}

	public synchronized List<SocketController> getConnectedClients() {
		return clients;
	}
}
