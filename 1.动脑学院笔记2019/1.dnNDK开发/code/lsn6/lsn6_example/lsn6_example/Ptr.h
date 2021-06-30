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
		
		//引用计数 -1
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
		
		cout << "拷贝" << endl;
		// p 指向一个对象 x
		// x又会被一个新的Ptr引用 引用计数+1
		++(*p.count);
		t = p.t;
		count = p.count;
	}

	Ptr<T>& operator=(const Ptr<T>& p) {
		cout << "operator=" << endl;
		++(*p.count);
		// 当前对象可能引用一个 T t;
		// 现在当前对象可能引用另一个 T
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
	int *count; //引用计数
	T *t;
};