package com.file.server;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.block.client.ClientBlock;

public class ServerAction extends Thread {

	private long length = 0;

	private Object O;

	private String Path;

	public Object getO() {
		return O;
	}

	public void setO(Object o) {
		O = o;
	}

	public String getPath() {
		return Path;
	}

	/**
	 * method to get a File Path to write
	 */
	public String getPath(String fileName, String fileID) {
		return fileID + fileName;
	}

	public void setPath(String path) {
		Path = path;
	}

	public ServerAction(String Path, Object o) {
		this.Path = Path;
		this.O = o;
	}

	public ServerAction(Object o) {
		this.O = o;
	}

	@Override
	public void run() {
		super.run();

		try {
			ServerSocket serverSocket = new ServerSocket(5209);

			System.out.println("Server run..");

			Socket socket = serverSocket.accept();

			ObjectInputStream Ois = new ObjectInputStream(socket
					.getInputStream());

			this.GetAndWrite(Ois);

			Ois.close();

			socket.close();

			serverSocket.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
	}

	private void GetAndWrite(ObjectInputStream Ois) throws Exception {

		// TODO Auto-generated method stub

		ClientBlock clientBlock = null;

		FileOutputStream fops = null;

		byte[] getbyte;

		while (!Thread.interrupted()) {

			getbyte = new byte[ClientBlock.getBlockLength()];

			synchronized (O) {

				clientBlock = (ClientBlock) Ois.readObject();

				if (!clientBlock.isEndflag()) {

					fops = new FileOutputStream(getPath(clientBlock
							.getFileName(), clientBlock.getFileID()), true);

					fops
							.write(clientBlock.getBlock(), 0, clientBlock
									.getSize());

					fops.close();
				}
			}
		}
	}
}
