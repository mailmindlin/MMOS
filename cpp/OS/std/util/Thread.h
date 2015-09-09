/*
 * Thread.h
 *
 *  Created on: Mar 30, 2015
 *      Author: wfeehery17
 */

#ifndef STD_UTIL_THREAD_H_
#define STD_UTIL_THREAD_H_

namespace Concurrency {

class Thread : public Runnable{
public:
	Thread();
	Thread(Runnable* r);
	virtual ~Thread();
	void start();
	void getPID();
	void run();
	void end();
	static void thread_exec(Thread* t);
protected:
	const Runnable* payload;
};

} /* namespace Concurrency */

#endif /* STD_UTIL_THREAD_H_ */
