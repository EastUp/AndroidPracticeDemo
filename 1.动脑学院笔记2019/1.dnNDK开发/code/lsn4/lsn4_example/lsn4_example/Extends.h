#pragma once
#include <iostream>
using namespace std;
//ָ��������̳�
//Ĭ�� private ˽�м̳�
// ˽�м̳У� ����� public protected ����� private 



class Parent {
public:
	
	virtual void test() {
		cout << "Parent" << endl;
	}
	//���麯�� ����java�ĳ��󷽷�
	virtual void test1() = 0;
	
};

class Parent1 {
public:
	
};
//ʹ������ĵط������� ���ø���� public�ķ���
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