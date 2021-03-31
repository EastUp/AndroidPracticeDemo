#pragma once

#include <iostream>

using namespace std;
class Test1 {
public:
	int i;
public:
	Test1() {}
	Test1(Test1& t) {
		this->i = t.i;
		cout << "拷贝对象" << endl;
	}
	//成员函数运算符重载
	Test1 operator+(const Test1& t) {
		Test1 temp;
		temp.i = this->i + t.i;
		//调用拷贝构造 将临时 对象 拷贝被 tt；
		/*Test1 tt = a();
		Test1 tt1;
		a(tt1);*/
		return temp;
	}
	Test1* operator+(const Test1* t) {
		Test1 *temp = new Test1;
		temp->i = this->i + t->i;
		//调用拷贝构造 将临时 对象 拷贝被 tt；
		/*Test1 tt = a();
		Test1 tt1;
		a(tt1);*/
		return temp;
	}

	//Test1 a(const Test1& t) {
	//	// temp -> 调用构造方法 ，出了a方法就调用 temp 析构 
	//	Test1 temp;
	//	//调用拷贝构造   将temp 拷贝给 一个临时对象 
	//	return temp;
	//}

	//void a(Test1& result,const Test1& t) {
	//	result.i = this->i + t.i;
	//}
	
};


class Test2 {
public:
	int i;
};