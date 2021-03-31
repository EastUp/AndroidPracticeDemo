// lsn4_example.cpp: 定义应用程序的入口点。
//

#include "lsn4_example.h"

using namespace std;

#include "Student.h"
#include "Instance.h"
#include "operator.h"
#include "Extends.h"


void test() {
	Student student(1,2);
}

void test(Student *student) {
	student->j = 100;
}

void func(int x) {

}

void func(float x) {

}


Test2 operator+(const Test2& t1, const Test2& t2) {
	Test2 temp;
	temp.i = t1.i + t2.i;
	return temp;
}
// 什么情况使用new？
// 什么情况需要在堆中申请内存？

//模板函数  java 泛型方法
//template <typename T> 
template <class T>
T max(T a, T b) {
	return a > b ? a : b;
}

//float max(float a, float b);
//float max(int a, int b);


//模板类	java泛型类
template <class T,class E>
class Q {
public:
	T test(T t,E e) {
		return t + e;
	}
};


int main()
{
	
	Q<int, float> q;
	cout << q.test(1, 1.12f) << endl;


	max(1, 2);
	max(1.f, 2.f);


	test();

	Student *student = new Student(1,2);
	test(student);
	cout  << student->getJ() << endl;

	//=========================
	//单例
	Instance::getInstance();

	
	//=========================

	//重载函数
	
	//操作符重载
	//允许 重定义 c++的 内置运算符
	Test1 test1;
	test1.i = 100;
	Test1 test2;
	test2.i = 200;

	//cl.exe 
	//GNU g++ 
	//在vs 平台 cl编译器 在debug 模式下 会执行 让 rvo 优化
	//在release 模式下 执行 nrvo 优化 
	//1、拷贝temp 对象 给一个 临时对象
	//2、临时对象拷贝给 test3 
	//回收临时对象
	
	Test1 test3 = test1 + test2;
	cout << "+运算符重载" << test3.i << endl;

	//非成员函数 运算符重载
	//===================================================
	Test2 t1;
	t1.i = 100;
	Test2 t2;
	t2.i = 200;

	Test2 t3 = t1 + t2;
	cout << "非成员函数 +运算符重载" << t3.i << endl;



	//============================================================
	//继承
	// 静态多态 : 在编译期间 就确定了 函数的调用地址 
	Parent *p = new Child;

	//delete p;
	p->test();

	// 动态多态 :  运行时确定调用
	// 虚函数
	// 构造方法 永远不要设为虚函数
	// 析构方法 声明为虚函数 
	// Parent *p = new Child; 
	// 如果在child中 申请了 堆内存 不为虚函数 永远不会调用child的析构函数
	
	//===========================================
	

	//模板编程  泛型基础





	delete student;
	student = 0;

	delete p;
	p = 0;
	system("pause");
	return 0;
}
