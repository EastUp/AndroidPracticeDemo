#include "Instance.h"

Instance * Instance::instance = 0;

Instance::Instance() {

}

Instance* Instance::getInstance() {
	// c++11  ������ ��֤�ڲ���̬���� ���̰߳�ȫ
	// 
	if (!instance)
	{
		instance = new Instance;
	}
	return instance;
}