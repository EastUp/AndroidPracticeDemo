# 面向对象编程

## 类

> C++ 在 C 语言的基础上增加了面向对象编程，C++ 支持面向对象程序设计。类是 C++ 的核心特性，用户定义的类型。

```c++
class Student {
	int i;    //默认 private
public:
	Student(int i,int j,int k):i(i),j(j),k(k){};	//构造方法 
	~Student(){};	//析构方法 
private:
	int j;
protected:
	int k;
};

void test() {
	Student student(1, 2，3); // 放在栈中的 会被回收
	//出方法释放student 调用析构方法
}

//动态内存(堆)
Student *student = new Student(1,2,3);
//释放
delete student;
student = 0;
```

> 类的析构函数是类的一种特殊的成员函数，它会在每次删除所创建的对象时执行(不需要手动调用)。
>
> private：可以被该类中的函数、友元函数访问。 不能被任何其他访问，该类的对象也不能访问。 
>
> protected：可以被该类中的函数、子类的函数、友元函数访问。 但不能被该类的对象访问。
>
> public：可以被该类中的函数、子类的函数、友元函数访问，也可以被该类的对象访问。  

## 常量函数 

> 函数后写上const，表示不会也不允许修改类中的成员。

```c++
class Student {
	int i;
public:
	Student() {}
	~Student() {}
	// 常量函数
	void  setName(char* _name) const  {
		//错误 不能修改name 去掉const之后可以
		name = _name;
	}
private:
	int j;
	char *name;
protected:
	int k;
};
```

## 友元

> 类的友元函数是定义在类外部，但有权访问类的所有私有（private）成员和保护（protected）成员
>
> 友元可以是一个函数，该函数被称为友元函数；友元也可以是一个类，该类被称为友元类，在这种情况下，整个类及其所有成员都是友元。

### 友元函数

```c++
class Student {
	int i;
public:
	Student() {}
	~Student() {}
	void  setName(char* _name)  {
		name = _name;
	}
	friend void printName(Student *student);
private:
	int j;
	char *name;
protected:
	int k;
};

void printName(Student *student) {
    //能够使用student中私有的name属性
	cout << student->name << endl;
}

Student *student = new Student;
student->setName("Lance");
printName(student);
```

### 友元类

```c++
class Student {
	int i;
public:
	Student() {}
	~Student() {}
	void  setName(char* _name)  {
		name = _name;
	}
	friend void printName(Student *student);
    //友元类
	friend class Teacher;
private:
	int j;
	char *name;
protected:
	int k;
};

class Teacher {
public:
	void call(Student *student) {
        //能够使用student中私有的name属性
		cout << "call:" << student->name << endl;
	}
};
```

## 静态成员

> 和Java一样，可以使用static来声明类成员为静态的
>
> `静态成员属性一定要初始化`
> 当我们使用静态成员属性或者函数时候 需要使用 `域运算符 :: `

```c++
//Instance.h
#ifndef INSTANCE_H
#define INSTANCE_H
class Instance {
public:
	static Instance* getInstance();
private:
	static Instance *instance;
};
#endif 

//Instance.cpp
#include "Instance.h"
// 一定要初始化
Instance* Instance::instance = 0;
Instance* Instance::getInstance() {
	//C++11以后，编译器保证内部静态变量的线程安全性
	if (!instance) {
		instance = new Instance;
	}
	return instance;
}
```

## 重载函数

> C++ 允许在同一作用域中的某个**函数**和**运算符**指定多个定义，分为**函数重载**和**运算符重载**。 

### 函数重载

```c++
void print(int i) {
	cout << "整数为: " << i << endl;
}
 
void print(double  f) {
	cout << "浮点数为: " << f << endl;
}
```

### 操作符重载

> C++允许重定义或重载大部分 C++ 内置的运算符 
>
> 函数名是由关键字 operator 和其后要重载的运算符符号构成的 
>
> 重载运算符可被定义为普通的非成员函数或者被定义为类成员函数 

**成员函数**

```c++
class Test1 {
public:
    Test1(){}
	//定义成员函数进行重载
    //返回对象   调用拷贝构造函数生成临时对象  释放函数内 temp 对象
    //引用类型(Test1&) 没有复制对象
    // 临时对象调用拷贝函数给 t3 可以输出 temp 与 t3 地址查看
	Test1 operator+(const Test1& t1) {
		Test1 temp;
		temp.i = this->i + t1.i;
		return temp;
	}
    //拷贝构造函数 (有默认的) 
    Test1(const Test1& t){
        //浅拷贝
		this->i = t.i;
		cout << "拷贝" << endl;
        //如果动态申请内存 需要深拷贝
	};
	int i;
};

Test1 t1;
Test1 t2;
t1.i = 100;
t2.i = 200;
//发生两次拷贝
// C++真正的临时对象是不可见的匿名对象
//1、拷贝 temp 对象 给一个 临时对象
//2、临时对象拷贝给 t3 
//语句结束析构临时对象
Test1 t3 = t1 + t2;
cout << t3.i << endl;
```

> Xcode上玩，使用的g++编译器会进行 **返回值优化(RVO、NRVO)** 从而看不到拷贝构造函数的调用。
>
> 可以加入 "-fno-elide-constructors" 取消GNU g++优化
>
> <u>对windows vs编译器cl.exe无效，VS Debug执行RVO，Release执行NRVO</u>
>
> RVO（Return Value Optimization）:消除函数返回时创建的临时对象 
>
> NRVO(Named Return Value Optimization)：属于 RVO 的一种技术, 直接将要初始化的对象替代掉返回的局部对象进行操作。

![返回值优化](返回值优化.jpg)



**非成员函数**

```c++
class Test2 {
public:
	int i;
};
//定义非成员函数进行 + 重载
Test2 operator+(const Test2& t21, const Test2& t22) {
	Test2 t;
	t.i = t21.i + t22.i;
	return t;
}

Test2 t21;
Test2 t22;
t21.i = 100;
t22.i = 200;
Test2 t23 = t21 + t22;
cout << t23.i << endl;
```

> 允许重载的运算符

| 类型           | 运算符                                                       |
| -------------- | ------------------------------------------------------------ |
| 关系运算符     | ==(等于)，!= (不等于)，< (小于)，> (大于>，<=(小于等于)，>=(大于等于) |
| 逻辑运算符     | \|\|(逻辑或)，&&(逻辑与)，!(逻辑非)                          |
| 单目运算符     | + (正)，-(负)，*(指针)，&(取地址)                            |
| 自增自减运算符 | ++(自增)，--(自减)                                           |
| 位运算符       | \| (按位或)，& (按位与)，~(按位取反)，^(按位异或),，<< (左移)，>>(右移) |
| 赋值运算符     | =, +=, -=, *=, /= , % = , &=, \|=, ^=, <<=, >>=              |
| 空间申请与释放 | new, delete, new[ ] , delete[]                               |
| 其他运算符     | ()(函数调用)，->(成员访问)，,(逗号)，\[](下标)                |

```c++
void *operator new (size_t size)
{
	cout << "新的new:" << size << endl;
	return malloc(size);
}

void operator delete(void *p)
{
	//释放由p指向的存储空间
	cout << "新的delete" << endl;
	free(p);
}
... ...
```



## 继承

> class A:\[private/protected/public] B
>
> 默认为private继承 
>
> B是基类，A称为子类或者派生类 

| 方式      | 说明                                                         |
| --------- | ------------------------------------------------------------ |
| public    | 基类的public、protected成员也是派生类相应的成员，基类的private成员不能直接被派生类访问，但是可以通过调用基类的公有和保护成员来访问。 |
| protected | 基类的公有和保护成员将成为派生类的保护成员                   |
| private   | 基类的公有和保护成员将成为派生类的私有成员                   |

```c++
class Parent {
public:
	void test() {
		cout << "parent" << endl;
	}
};

class Child :   Parent {
public:
	void test() {
         // 调用父类 方法
		Parent::test();
		cout << "child" << endl;
	}
};
```

**多继承**

> 一个子类可以有多个父类，它继承了多个父类的特性。
>
> class <派生类名>:<继承方式1><基类名1>,<继承方式2><基类名2>,…

## 多态

> 多种形态。当类之间存在层次结构，并且类之间是通过继承关联时，就会用到多态。
>
> 静态多态（静态联编）是指在编译期间就可以确定函数的调用地址，通过**函数重载**和**模版（泛型编程）**实现 
>
> 动态多态（动态联编）是指函数调用的地址不能在编译器期间确定，必须需要在运行时才确定 ,通过**继承+虚函数** 实现

### 虚函数

```c++
class Parent {
public:
	 void test() {
		cout << "parent" << endl;
	}
};

class Child :public Parent {
public:
	void test() {
		cout << "child" << endl;
	}
};

Parent *c = new Child();
// 编译期间 确定c 为 parent 调用parent的test方法
c->test();

//修改Parent为virtual 虚函数 动态链接,告诉编译器不要静态链接到该函数
virtual void test() {
		cout << "parent" << endl;
}
//动态多态 调用Child的test方法
c->test();
```

> 构造函数任何时候都不可以声明为虚函数
>
> 析构函数一般都是虚函数,释放先执行子类再执行父类

**纯虚函数**

```c++
class Parent {
public:
    //纯虚函数 继承自这个类需要实现 抽象类型
	virtual void test() = 0;
};

class Child :public Parent {
public:
	void test(){}
};
```

## 模板

> 模板是泛型编程的基础

### 函数模板

> 函数模板能够用来创建一个通用的函数。以支持多种不同的形參。避免重载函数的函数体反复设计。

```c++
template <typename T> 
T max(T a,T b)
{
	// 函数的主体
	return  a > b ? a : b;
}
//代替了
int max(int a,int b)
float max(float a,float b)
```



### 类模板(泛型类)

> 为类定义一种模式。使得类中的某些数据成员、默写成员函数的參数、某些成员函数的返回值，能够取随意类型
>
> 常见的 容器比如 向量 vector <int> 或 vector <string> 就是模板类

```c++
template<class E,class T>
class Queue {
public:
    T add(E e,T t){
        return e+t;
    }
};

Queue<int,float> q;
q.add(1,1.1f) = 2.1f
```

