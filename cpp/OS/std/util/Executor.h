/*
 * Executor.h
 *
 *  Created on: Mar 30, 2015
 *      Author: wfeehery17
 */

#ifndef EXECUTOR_H_
#define EXECUTOR_H_

namespace Concurrency {
class Executor {
public:

	Executor();
	virtual ~Executor();
	void submit(Runnable* r);
	void shutdown();
};
}

#endif /* EXECUTOR_H_ */
