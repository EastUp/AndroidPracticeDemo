#include "Student.h"
#include <iostream>

using namespace std;
Student::Student(int i,int j):i(i),j(j) {
	name = (char*)malloc(100); 
	cout << "构造方法" << endl;
}

Student::~Student() {
	free(name);
	cout << "析构方法" << endl;
}

void Student::setJ(int j) const {
	//
}
