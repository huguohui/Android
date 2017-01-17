package com.downloader.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Arrays;

import com.AbsReceiver;
import com.Parser;
import com.Receive;

/**
 * The Chunked content parser.
 * @since 2015/12/2
 */
public class HttpChunkedParser implements Parser {
	/**
	 * Parse data to a kind of format.
	 *
	 * @param data Provided data.
	 * @return Some data.
	 */
	@Override
	public Object parse(byte[] data) {
		return null;
	}

	/**
	 * Parse a chunk from data stream.
	 *
	 * @param data Data stream.
	 * @return A chunk data.
	 */
	@Override
	public byte[] parse(InputStream data) throws IOException {
		int chunkSize = 0;

		while((chunkSize = getChunkSize(data)) != 0) {
			if (chunkSize > 0) {
				return receiveData(data, chunkSize);
			}
		}

		return null;
	}
	
	
	/**
	 * Receives data by size.
	 * @param size Size of receive.
	 * @throws IOException 
	 */
	private byte[] receiveData(InputStream source, int size) throws IOException {
		byte[] chunk = new byte[size];
		int count = 0, read = 0, freeLoop = 0;

		while(count < size) {
			int available = source.available();
			byte[] buff = new byte[AbsReceiver.BUFFER_SIZE];
			
			if (available == 0 && freeLoop++ < 100) {
				try { Thread.sleep(1); } catch( Exception ex ) {
					ex.printStackTrace();
				}
				continue;
			}

			if (AbsReceiver.END_OF_STREAM == (read = source.read(buff, 0,
					count + AbsReceiver.BUFFER_SIZE > size ? size - count : AbsReceiver.BUFFER_SIZE))) {
				if (count != 0 && count != size)
					return Arrays.copyOfRange(buff, 0, count);
				
				return null;
			}
			
			System.arraycopy(buff, 0, chunk, count, read);
			count += read;
			freeLoop = 0;
		}
		
		return chunk;
	}
	
	
	/**
	 * Gets chunk size from special InputStream.
	 * @param is Special InputStream.
	 * @return Chunk size.
	 * @throws IOException
	 */
	private int getChunkSize(InputStream is) throws IOException {
		byte aByte;
		int matchCount = 0, byteCount = 0, emptyLine = 0;
		byte[] buff = new byte[AbsReceiver.BUFFER_SIZE << 1], crlf = {0x0D, 0x0A};

		while(Receive.END_OF_STREAM != (aByte = (byte)is.read())) {
			if (aByte == crlf[matchCount]) {
				if (++matchCount == 2) {
					if (byteCount != 0)
						return Integer.parseInt(new String(buff, 0, byteCount), 16);
					else
						emptyLine++;

					byteCount = 0;
					matchCount = 0;
				}
			}else{
				matchCount = 0;
				buff[byteCount++] = aByte;
			}
		}

		return emptyLine > 1 ? 0 : -1;
	}
	

	/**
	 * Checks given string if hex.
	 * @param hex String that need to checking.
	 * @return If is hex true else false.
	 */
	public boolean isHex(String hex) {
		if (hex == null || hex.length() == 0) return false;

		byte[] chars = hex.getBytes();
		for (int i = 0; i < chars.length; i++) {
			if (!((chars[i] >= 48 && chars[i] <= 57)
					|| (chars[i] >= 65 && chars[i] <= 90)
					|| (chars[i] >= 97 && chars[i] <= 122)))
				return false;
		}
		return true;
	}


	/**
	 * Parse data to a kind of format.
	 *
	 * @param data Provided data.
	 * @return Some data.
	 */
	@Override
	public Object parse(Reader data) throws IOException {
		return null;
	}
}