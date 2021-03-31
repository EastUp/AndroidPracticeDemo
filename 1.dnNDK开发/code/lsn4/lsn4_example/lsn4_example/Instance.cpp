#include "Instance.h"

Instance * Instance::instance = 0;

Instance::Instance() {

}

Instance* Instance::getInstance() {
	// c++11  编译器 保证内部静态变量 的线程安全
	// 
	if (!instance)
	{
		instance = new Instance;
	}
	return instance;
}