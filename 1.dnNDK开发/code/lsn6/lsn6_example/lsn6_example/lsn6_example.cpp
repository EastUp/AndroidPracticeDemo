// lsn6_example.cpp: 定义应用程序的入口点。
//

#include "lsn6_example.h"
#include <thread>
#include <pthread.h>
#include "safe_queue.h"
#include "Ptr.h"
using namespace std;

class A {
public:
	~A() {
		cout << "释放A" << endl;
	}
};

void testPtr() {
	A *a1 = new A; 
	A *a2 = new A;
	
	//shared_a 处于栈当中
	Ptr<A> shared_a(a1); // a 引用计数:1
	//先创建了一个对象 变量是shared_b
	// a2 野生对象(没有智能指针指向a2了 )
	Ptr<A> shared_b(a2);  // 1
	//再将 shared_b 变量 重新赋值
	shared_b = shared_a;
	//出方法 shared_a 与 shared_b 会被回收
	//delete a2;
}
//智能指针
void main() {
	//c++ 11 stl 两种类型智能指针
	// shared_ptr : 共享，内部实现了引用计数  
	testPtr();
	

	auto i = 5;
	// [&] 表示外部变量都以引用的形式在lambda中使用，函数内部修改i的值会影响外部
	// 这里的 -> auto 自动推导在c++11不支持，c++14中对auto进行了扩展
	thread t1([&] () -> auto{
	
		cout << "线程" << i << endl;
		return 1;
	});
	_sleep(10);
	cout << i << endl;
	
	system("pause");
}

SafeQueue<int> sq;

void * get(void * ) {
 // 
	while (1)
	{
		int i = -1;
		// 如果队列中没有数据 就卡在这里
		// ffmpeg 
		sq.pop(i);
		//如果没有取出数据 再重试去取
		/*if (i == -1) {
			cout << "-1" << endl;
			continue;
		}*/
		cout << "取出数据:" << i << endl;
	}
	return 0;

}
void * put(void *) {
	while (1)
	{
		int i;
		// 将用户输入 给 i保存
		cin >> i;
		sq.push(i);
	}
	return 0;
}






void main4() {
	pthread_t pid1, pid2;
	pthread_create(&pid1,0, get,0);
	pthread_create(&pid2, 0, put, 0);
	pthread_join(pid1,0);
	system("pause");
}

void task(int i) {
	cout << "线程:" << i << endl;
}


void* pthreadtask(void* args) {
	_sleep(1000);
	int i = *static_cast<int*>(args);
	cout << "线程参数:" << i << endl;
	return 0;
}


#include <queue>

pthread_mutex_t mutex;
queue<int> q;

void* queue_task(void *args) {
	// 锁起来  synchronized
	pthread_mutex_lock(&mutex);
	if (!q.empty()) {
		printf("获取队列数据:%d\n",q.front());
		q.pop();
	}
	else {
		printf("未获取队列数据\n");
	}
	pthread_mutex_unlock(&mutex);
	return 0;
}




//同步线程
void main3() {
	//初始化
	pthread_mutex_init(&mutex,0);
	for (size_t i = 0; i < 5; i++)
	{
		q.push(i);
	}
	pthread_t pids[10];
	for (size_t i = 0; i < 10; i++)
	{
		pthread_create(&pids[i],0, queue_task,0);
	}
	//销毁
	pthread_mutex_destroy(&mutex);
	system("pause");
}
//线程创建
int main2()
{
	// posix 
	//线程属性
	pthread_attr_t attr = 0;
	//传递指针的指针
	pthread_attr_init(&attr);

	//分离线程 android ndk开发基本上不会使用这个玩意
	// 默认为非分离线程  
	// 分离 ： 不能被其他的线程操作 join ，自己玩自己的
	// 设置为分离线程 
	pthread_attr_setdetachstate(&attr,PTHREAD_CREATE_DETACHED);

	//调度策略与优先级
	//windows中测试 不成功

	 

	pthread_t pid;
	int i = 100;
	//多参数： 结构体、class

	pthread_create(&pid, &attr, pthreadtask,&i);

	//pthread_join(pid,0);

	pthread_attr_destroy(&attr);
	cout << "线程执行完毕" << endl;
	system("pause");
	return 0;
}
