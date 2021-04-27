#pragma once

template <typename T>
class Ptr {
public:
	Ptr() {
		count = new int(1);
		t = NULL;
	}
	Ptr(T* t) {
		count = new int(1);
		this->t = t;
	}
	~Ptr() {
		
		//���ü��� -1
		--(*count);
		if (*count == 0)
		{
			if (t != NULL) {
				delete t;
				t = NULL;
			}
			delete count;
			count = NULL;
		}
		
	}

	Ptr(const Ptr<T> &p) {
		
		cout << "����" << endl;
		// p ָ��һ������ x
		// x�ֻᱻһ���µ�Ptr���� ���ü���+1
		++(*p.count);
		t = p.t;
		count = p.count;
	}

	Ptr<T>& operator=(const Ptr<T>& p) {
		cout << "operator=" << endl;
		++(*p.count);
		// ��ǰ�����������һ�� T t;
		// ���ڵ�ǰ�������������һ�� T
		if (--(*count) == 0) {
			if (t) {
				delete t;
				t = 0;
			}
		}
		t = p.t;
		count = p.count;
		return *this;
	}
private:
	int *count; //���ü���
	T *t;
};