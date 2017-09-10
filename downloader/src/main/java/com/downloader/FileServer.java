package com.downloader;

import com.downloader.http.Http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;


/**
 * 局域网聊天服务端，基于java的ServerSocket。
 * @author HGH
 * @since 2015/08/01
 * @version 0.1
 */
public class FileServer extends ServerSocket {
	/** 服务端程序监听端口。*/
	public static final int CHAT_PORT = 1995;

	/** 最大连接数限制。*/
	public static final int MAX_CLIENT = 1;

	/** 保存所有的客户端连接。*/
	private Socket[] mSockets;

	/** 当前连接到服务端的客户端数量。*/
	private int clientNum = 0;


	/**
	 * 构造方法，用于创建一个服务端。
	 * @param port 指定服务端程序监听的端口
	 */
	public FileServer(int port) throws IOException {
		super(port, MAX_CLIENT);	//调用父类的构造方法，传递指定监听端口以及最大客户端链接数量
									//并进行初始化工作。
	}


	/**
	 * 构造方法，用于创建一个服务端。
	 */
	public FileServer() throws IOException {
		this(CHAT_PORT);
	}

  
  	/**
  	 * 当调用此方法时，服务端开始监听指定的端口。当有客户端连接时，接受并与客户端建立连接。
  	 */
	public void service() {
		try{
			System.out.println("Listening..."); //输出Listening...
			while(true) {						//死循环，保证服务端程序始终运行。
				Socket socket = accept();		//接受客户端的请求，并返回一个已建立的与客户端之间的连接。
				new FileTransfer(socket).transfer();
			}
		}catch(Exception e) {
			
		}finally{	
			try {
				close();						//当循环结束后（异常），关闭服务端程序。
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		new FileServer(1234).service();
	}
}

class FileTransfer implements Runnable {
	public static final int MAX_CONNECTION_TIMEOUT = 100000;
	public static final int BUFFER_SIZE = 8192;

	private boolean isConnecting = true;
	private Socket mSocket = null;
	private InputStream mInputStream = null;
	private OutputStream mOutputStream = null;

	public FileTransfer(Socket socket) {
		try{
			if (socket == null || !socket.isConnected())
				throw new SocketException("Socket is invaild!");
			mSocket = socket;
			init();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}


	public void transfer() {
		new Thread(this).run();
	}


	public FileTransfer(InetSocketAddress address) {
		try{
			if (mSocket != null && mSocket.isConnected())
				throw new SocketException("Client was connected to server!");
	
			mSocket = new Socket();
			mSocket.connect(address, MAX_CONNECTION_TIMEOUT);
			init();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}


	protected void init() throws IOException {
		mInputStream = mSocket.getInputStream();
		mOutputStream = mSocket.getOutputStream();
		System.out.println("已连接到:" + mSocket.getRemoteSocketAddress());
	}


	public void abort() {
		if (mSocket != null && mSocket.isConnected() && !mSocket.isClosed()) {
			try {
				mSocket.shutdownInput();
				mSocket.shutdownOutput();
				mSocket.close();
				System.out.println("已断开与:" + mSocket.getRemoteSocketAddress() + "的连接");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	@Override
	public void run() {
		byte[] buffer = new byte[BUFFER_SIZE],
			   outBuffer = new byte[10];
		int length = 0, limit = 5;

		try {
			
				System.out.print(new String(buffer));
			
			
			mOutputStream.write(("HTTP/1.1 200 OK\r\n"
					+ "Connection: close\r\n"
					+ "Date: " + new SimpleDateFormat(Http.GMT_DATE_FORMAT[0], Locale.ENGLISH).format(new Date())+ "\r\n"
					+ "Content-Length: 50\r\n\r\n").getBytes());
			
			
			Arrays.fill(outBuffer, (byte) 47);
			while(mSocket.isConnected() && limit-- > 0) {
				try {
					mOutputStream.write(outBuffer);
					Thread.sleep(1000);
				} catch(IOException e) {
					e.printStackTrace();
					break;
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			mSocket.shutdownOutput();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//abort();
	}
}
