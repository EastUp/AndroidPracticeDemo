package com.dn.socketserver;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * 
 * @author Damon
 * @Date 2019/05/29 00:31:39
 *
 */
public class ClientReadTask extends Thread {

	private Socket socket;

	public ClientReadTask(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			// 获得输入流 读取客户端消息
			InputStream is = socket.getInputStream();
			// 1、直接获得数据
//			 readMsg(is);
			// 2、处理包标识
//			 NormalParser.readMsg(is);
			// 3、protobuf处理
			ProtobufParser.readMsg(is);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	void readMsg(InputStream is) throws IOException {
		byte[] buffer = new byte[1024];
		int len = 0;
		// 读取数据
		while ((len = is.read(buffer)) != -1) {
			System.out.println("recv message:" + new String(buffer, 0, len));
		}
	}

}
