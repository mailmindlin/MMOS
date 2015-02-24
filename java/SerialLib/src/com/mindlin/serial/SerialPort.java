package com.mindlin.serial;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public abstract class SerialPort implements Closeable {
	public abstract SerialPort write(byte b) throws IOException;
	public abstract SerialPort writeChar(char c) throws IOException;
	public abstract byte read() throws IOException;
	public abstract void open() throws IOException;
	public SerialPort write(byte...bytes) throws IOException{
		for(byte b:bytes)
			write(b);
		return this;
	}
	public SerialPort write(String s) throws IOException {
		char[] charr = s.toCharArray();
		for(char c:charr)
			writeChar(c);
		return this;
	}
	public SerialPort write(int i, boolean bigEndian) throws IOException {
		write(
				(byte)((i>>0)&0xFF),
				(byte)((i>>8)&0xFF),
				(byte)((i>>16)&0xFF),
				(byte)((i>>24)&0xFF));
		return this;
	}
	public SerialPort writeLong(long l, boolean bigEandian) throws IOException {
		for(int i=0;i<Long.BYTES;i++)
			write((byte) (l>>(8*(bigEandian?i:(Long.BYTES-i)))&0xFF));
		return this;
	}
	public byte[] read(int bytes) throws IOException {
		byte[] result = new byte[bytes];
		for(int i=0;i<bytes;i++)
			result[i]=read();
		return result;
	}
	@Override
	public void finalize() throws Throwable {
		super.finalize();
		close();
	}
	public OutputStream getOutputStream() {
		return new OutputStream() {
			@Override
			public void write(int b) throws IOException {
				write(b);
			}
			@Override
			public void close() throws IOException {
				super.close();
				close();
			}
		};
	}
	public InputStream getInputStream() {
		return new InputStream() {
			@Override
			public int read() throws IOException {
				return read() & 0xFF;
			}
			@Override
			public void close() throws IOException {
				super.close();
				close();
			}
		};
	}
}
