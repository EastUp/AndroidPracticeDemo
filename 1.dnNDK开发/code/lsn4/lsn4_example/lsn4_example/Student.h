#pragma once

#ifndef STUDENT_H
#define STUDENT_H

class Student {
	// ��Ԫ����
	friend void test(Student* );
	//��Ԫ��
	friend class Teacher;
	int i; //private 
public:
	Student(int i,int j);
	~Student(); // ��������
	// ��������
	//  ��ʾ���� Ҳ������ ȥ�޸����еĳ�Ա
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


