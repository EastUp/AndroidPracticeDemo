#pragma once
#include <iostream>
using namespace std;
//指定作用域继承
//默认 private 私有继承
// 私有继承： 父类的 public protected 变成了 private 



class Parent {
public:
	
	virtual void test() {
		cout << "Parent" << endl;
	}
	//纯虚函数 类似java的抽象方法
	virtual void test1() = 0;
	
};

class Parent1 {
public:
	
};
//使用子类的地方不允许 调用父类的 public的方法
class Child :public Parent, private Parent1 {
public:
	
	 void test() {
		// super
		// Parent::test();
		cout << "Child" << endl;
	}
	 void test1() {

	 }
};

class Child1 :Child {

};