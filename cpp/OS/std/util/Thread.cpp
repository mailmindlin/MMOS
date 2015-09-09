/*
 * Thread.cpp
 *
 *  Created on: Mar 30, 2015
 *      Author: wfeehery17
 */

#include "Thread.h"
#ifdef __REALCOMP__
#include <pthread.h>
#else
#define pthread_exit void
#define pthread_t void
#define pthread_attr_t void
void pthread_create(pthread_t *thread, const pthread_attr_t *attr, void *(*start_routine) (void *), void *arg);
#endif

namespace Concurrency {
void Thread::thread_exec(Thread* t) {
	((Runnable)(*(t->payload))).run();
	pthread_exit(0);
}
Thread::Thread() : payload(this){
}

Thread::Thread(Runnable* r) : payload(r){
}

Thread::~Thread() {
	delete this->payload;
}

void Thread::start() {
	pthread_t* thread;
	const pthread_attr_t* attributes;
	pthread_create(thread, attributes, (void *(*) (void *))Thread::thread_exec, (void*)this);
}

void Thread::getPID() {

}

void Thread::run() {

}

void Thread::end() {

}

} /* namespace Concurrency */
