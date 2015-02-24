package com.mindlin.spc;

import java.util.Collection;
import java.util.HashSet;

public class SuperProFinder {
	public Collection<SuperPro> search(long time) throws InterruptedException {
		final Collection<SuperPro> result = new HashSet<SuperPro>();
		Thread t = new Thread(()->{
			
		});
		t.start();
		Thread.sleep(time);
		t.interrupt();
		t.stop();
		return result;
	}
}
