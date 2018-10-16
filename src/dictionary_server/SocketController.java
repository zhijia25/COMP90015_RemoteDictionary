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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class SocketController extends Thread {

	private Socket clientSocket;
	private JSONObject dic, ans;
	private DataInputStream input;
	private DataOutputStream output;
	private int clientNum;
	ServerGUI frame;

	public SocketController(Socket clientSocket, JSONObject dic, int clientNum, ServerGUI frame) {
		ans = new JSONObject();
		this.frame = frame;
		this.clientNum = clientNum;
		this.dic = dic;
		try {
			this.clientSocket = clientSocket;
			this.dic = dic;
			input = new DataInputStream(clientSocket.getInputStream());
			output = new DataOutputStream(clientSocket.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getClientNum() {
		return clientNum;
	}

	public void run() {
		try {
			JSONParser parser = new JSONParser();
			while (true) {

				if (input.available() > 0) {

					JSONObject command = (JSONObject) parser.parse(input.readUTF());
					if (!(command.get("STATUS") == null)) {
						frame.addAction("client " + clientNum + " log off!\n");
						ServerOperation.getInstance().clientDisconnected(this, frame);

						clientSocket.close();
						break;
					}
					if (command.get("ACTION").equals("ADD")) {
						String word = (String) command.get("ENTRY");
						String meaning = (String) command.get("MEANING");
						frame.addAction(Thread.currentThread().getName() + " - Message from client " + clientNum
								+ " received: ADD" + " " + word + " " + meaning + "\n");

						ans.put("ANSWER", ServerOperation.getInstance().add(dic, word, meaning));
						output.writeUTF(ans.toJSONString());
						output.flush();
					}

					if (command.get("ACTION").equals("REMOVE")) {
						String word = (String) command.get("ENTRY");
						frame.addAction(Thread.currentThread().getName() + " - Message from client " + clientNum
								+ " received: REMOVE" + " " + word + "\n");

						ans.put("ANSWER", ServerOperation.getInstance().remove(dic, word));
						output.writeUTF(ans.toJSONString());
						output.flush();
					}
					if (command.get("ACTION").equals("QUERY")) {
						String word = (String) command.get("ENTRY");
						frame.addAction(Thread.currentThread().getName() + " - Message from client " + clientNum
								+ " received: QUERY" + " " + word + "\n");

						ans.put("ANSWER", ServerOperation.getInstance().query(dic, word));
						output.writeUTF(ans.toJSONString());
						output.flush();

					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}