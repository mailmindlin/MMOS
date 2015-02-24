package com.mindlin.serial;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;

import javax.comm.CommPortIdentifier;
import javax.comm.UnsupportedCommOperationException;

public class SerialPortFinder implements Callable<Set<SerialPort>> {

	@Override
	public Set<SerialPort> call() throws Exception {
		Set<SerialPort> result = new HashSet<SerialPort>();
		// get list of ports available on this particular computer,
		// by calling static method in CommPortIdentifier.
		Enumeration pList = CommPortIdentifier.getPortIdentifiers();

		// Process the list.
		while (pList.hasMoreElements()) {
			CommPortIdentifier cpi = (CommPortIdentifier) pList.nextElement();
			System.out.print("Port " + cpi.getName() + " ");
			if (cpi.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				System.out.println("is a Serial Port: " + cpi);
				
			} else if (cpi.getPortType() == CommPortIdentifier.PORT_PARALLEL) {
				System.out.println("is a Parallel Port: " + cpi);
			} else {
				System.out.println("is an Unknown Port: " + cpi);
			}
		}
		return result;
	}
	protected static class SerialPortImpl extends SerialPort{
		CommPortIdentifier spi;
		transient FileDescriptor fd;
		public SerialPortImpl(CommPortIdentifier spi) {
			this.spi=spi;
		}
		@Override
		public SerialPort write(byte b) {
			
			return this;
		}
		@Override
		public SerialPort writeChar(char c) {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public byte read() {
			return 0;
		}
		@Override
		public void close() throws IOException {
			
		}
		@Override
		public void open() throws IOException {
			if(spi.isCurrentlyOwned())
				throw new IOException("Port already owned!");
			try {
				spi.open(fd=new FileDescriptor());
			} catch (UnsupportedCommOperationException e) {
				throw new IOException(e);
			}
		}
	}
}
