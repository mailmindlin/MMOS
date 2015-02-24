package com.mindlin.serial.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.mindlin.serial.SerialPortFinder;

public class SerialPortFinderTest {

	@Test
	public void test() {
		SerialPortFinder finder = new SerialPortFinder();
		try {
			finder.call();
		} catch (Exception e) {

		}
		fail("Not yet implemented");
	}

}
