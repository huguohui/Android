package com.downloader.io.writer;


import java.io.IOException;
import java.io.InputStream;

public class DataReader implements DataReadable {
	protected InputStream inputStream;

	public DataReader(InputStream is) {
		inputStream = is;
	}


	@Override
	public byte read() throws IOException {
		return (byte) inputStream.read();
	}


	@Override
	public byte[] read(int i) throws IOException {
		byte[] buff = new byte[i];
		inputStream.read(buff);
		return buff;
	}


	protected long readIntData(int bits) throws IOException {
		long retVal = 0x0;
		byte[] buff = read(bits >>> 3);
		int bytes = bits >> 3, moveBit = 0;
		long val = 0, bitMark = 0xff;
		for (int i = 0; i < bytes; i++) {
			moveBit = (~-bytes - i) << 3;
			val |= (bitMark << moveBit) & (buff[i] << moveBit);
			bitMark = 0xff;
		}

		return val;
	}


	protected long readLongData() throws IOException {
		long retVal = 0x0;
		for (int i = 0; i < 2; i++) {
			retVal |= readIntData(Integer.SIZE);
			retVal <<= (1 - i) * 32;
		}

		return retVal;
	}


	@Override
	public int readInt() throws IOException {
		return (int) readIntData(Integer.SIZE);
	}


	@Override
	public short readShort() throws IOException {
		return (short) readIntData(Short.SIZE);
	}


	@Override
	public char readChar() throws IOException {
		return (char) (short) readIntData(Short.SIZE);
	}


	@Override
	public long readLong() throws IOException {
		return readLongData();
	}


	@Override
	public int[] readInt(int i) throws IOException {
		int[] vals = new int[i];
		for (int j = 0; j < i; j++) {
			vals[j] = (int) readIntData(Integer.SIZE);
		}

		return vals;
	}


	@Override
	public short[] readShort(int i) throws IOException {
		short[] vals = new short[i];
		for (int j = 0; j < i; j++) {
			vals[j] = (short) readIntData(Short.SIZE);
		}

		return vals;
	}


	@Override
	public char[] readChar(int i) throws IOException {
		char[] vals = new char[i];
		for (int j = 0; j < i; j++) {
			vals[j] = (char) (short) readIntData(Short.SIZE);
		}

		return vals;
	}


	@Override
	public long[] readLong(int i) throws IOException {
		long[] vals = new long[i];
		for (int j = 0; j < i; j++) {
			vals[j] = readLongData();
		}

		return vals;
	}


	@Override
	public void close() throws IOException {
		inputStream.close();
	}
}
