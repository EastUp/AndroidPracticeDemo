#pragma once

#ifndef STUDENT_H
#define STUDENT_H

class Student {
	// 友元函数
	friend void test(Student* );
	//友元类
	friend class Teacher;
	int i; //private 
public:
	Student(int i,int j);
	~Student(); // 析构函数
	// 常量函数
	//  表示不会 也不允许 去修改类中的成员
	void setJ(int j) const;

	int getJ() {
		return j;
	}

private:
	int j;
protected:
	int k = 1;
public:
	int l = 0;
	char *name;

};


class Teacher {
public:
	void call(Student *s) {
		s->j = 10086;
	}
	void call1(Student *s) {
		s->j = 10086;
	}
};

#endif // !STUDENT_H


