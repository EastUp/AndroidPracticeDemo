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
		cout << "��������" << endl;
	}
	//��Ա�������������
	Test1 operator+(const Test1& t) {
		Test1 temp;
		temp.i = this->i + t.i;
		//���ÿ������� ����ʱ ���� ������ tt��
		/*Test1 tt = a();
		Test1 tt1;
		a(tt1);*/
		return temp;
	}
	Test1* operator+(const Test1* t) {
		Test1 *temp = new Test1;
		temp->i = this->i + t->i;
		//���ÿ������� ����ʱ ���� ������ tt��
		/*Test1 tt = a();
		Test1 tt1;
		a(tt1);*/
		return temp;
	}

	//Test1 a(const Test1& t) {
	//	// temp -> ���ù��췽�� ������a�����͵��� temp ���� 
	//	Test1 temp;
	//	//���ÿ�������   ��temp ������ һ����ʱ���� 
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