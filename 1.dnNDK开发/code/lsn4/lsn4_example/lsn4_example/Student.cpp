#include "Student.h"
#include <iostream>

using namespace std;
Student::Student(int i,int j):i(i),j(j) {
	name = (char*)malloc(100); 
	cout << "���췽��" << endl;
}

Student::~Student() {
	free(name);
	cout << "��������" << endl;
}

void Student::setJ(int j) const {
	//
}
