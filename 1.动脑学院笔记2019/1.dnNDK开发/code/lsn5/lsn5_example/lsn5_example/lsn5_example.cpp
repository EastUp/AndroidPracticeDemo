// lsn5_example.cpp: 定义应用程序的入口点。
//

#include "lsn5_example.h"
#include <vector>
#include <queue>
#include <stack>
#include <set>
#include <map>
#include <fstream>
using namespace std;

class Type {
public:
	int i;
	Type(int i) :i(i) {}
};

struct TypeLess
{	
	constexpr bool operator()(const Type& _Left, const Type& _Right) const
	{	
		
		return (_Left.i < _Right.i);
	}
};

vector<int> vec;

void testVec() {
	vec.push_back(1);
	vec.push_back(2);
	vec.clear();
	//swap  替换
	vector<int> v;
	v.swap(vec);
	cout << "testVec:" << vec.capacity() << endl;
}

class Parent {
public:
	int i;
	virtual void test() {
		cout << "parent" << endl;
	}
};
class Child :public Parent {
public:
	void test() {
		cout << "child" << endl;
	}
};

void testExeceptioon() {
	Parent p;
	p.i = 10086;
	throw p;
}

int main()
{
	testVec();
	// 容器  stl:标准模板库
	//序列式 与 关联式
	// 序列式容器 ： 元素排列顺序与元素本身无关，由添加顺序决定的
	// vector、list、dequeue、queue、stack  priority queue
	vector<int> vec_1;
	// 声明有一个元素空间
	vector<int> vec_2(1);
	// 6个元素 值都是1
	vector<int> vec_3(6,1);
	vector<int> vec_4(vec_3);

	//增加元素
	vec_1.push_back(10);
	vec_1.push_back(20);

	vec_1.pop_back();

	//通过下标来获得元素
	cout << "通过下标来获得元素:" << vec_1[0] << endl;
	//vec_1.at(0);

	//直接获得队首与队尾的元素
	vec_1.front();
	vec_1.back();

	vec_1.clear();
	vec_1.erase(vec_1.begin(), vec_1.end());

	cout << "获得vec的容量大小:" << vec_1.capacity() << endl;
	

	//队列
	queue<int> q;
	//加入队列
	q.push(1);
	q.push(2);
	//弹出队列
	q.pop();

	//栈 stack
	stack<int> s;
	
	//优先级队列 在 vector 之上实现
	priority_queue<int> pq;
	pq.push(2);
	pq.push(1);
	pq.push(3);

	cout << "默认优先级队列，队首:" << pq.top() << endl;

	priority_queue<int, vector<int>, less<int> > pq1;
	//less : 最大的元素在前面
	priority_queue<int, vector<int>, greater<int> > pq2;
	//greater :小的在前面

	// 不知道如何给 Type 排序
	// TypeLess : 自定义 TypeLess 对type进行排序指定
	priority_queue<Type, vector<Type>, TypeLess> pq_type;
	
	pq_type.push(Type(2));
	pq_type.push(Type(1));
	pq_type.push(Type(3));

	cout << "Type 优先级队列，队首:" << pq_type.top().i << endl;

	//关联式容器
	//通过一个关键字 保存和访问 元素的  map set
	
	// set 集合 元素不可重复
	set<int> set1 = {1,2,3,4};
	pair<set<int>::iterator, bool> _pair = set1.insert(5);
	set1.insert(1);

	set1.erase(1);
	cout << "set里面有:" << set1.size() << endl;

	set<int>::iterator itt = set1.begin();
	for (; itt != set1.end(); itt++)
	{

	}
	//set1.size();


	//使用迭代器
	//指向容器中第0个元素
	vector<int> vec_5;
	vec_5.push_back(10);
	vec_5.push_back(20);
	//迭代器 行为和指针 是个模板类
	vector<int>::iterator it = vec_5.begin();
	// 指向容器中 最后一个的下一个元素 
	// vs 编译器 cl.exe 有问题 ，g++没问题
	// g++ lsn5_example.cpp xxx.exe 
	for (; it < vec_5.end(); )
	{
		if (*it == 10) {
			// windows vs 需要再给it重新赋值
			it = vec_5.erase(it);
		}
		else {
			it++;
		}	
	}
	it = vec_5.begin();
	for (; it < vec_5.end(); ++it)
	{
		cout << "vec_5删除后有:" << *it << endl;
	}


	//map
	map<int, string> map1;
	map<int, string> map2 = { {1,"A"},{2,"B"} };
	map2.insert({3,"C"});
	//修改key为3的元素
	map2[3] = "D";

	//红黑树


	//新式转换
	//转换操作符
	//const_cast : 主要 修改类型的const 与 volatile 属性
	const char *p = "123";
	char *b = const_cast<char*>(p);



	//AS 
	// 编译时确定
	Child  *p1 = new Child;
	Parent  *c1 = static_cast<Parent*>(p1);
	//输出c
	c1->test();

	//dynamic_cast 父类必须有一个 虚函数
	// 动态绑定: 运行时候 确定调用的函数
	Child  *p11 = new Child;
	Parent  *c11 = dynamic_cast<Parent*>(p11);
	//输出c
	if (!c11) {
		cout << "转换失败" << endl;
	}
	else {
		cout << "转换成功" << endl;
		c11->test();
	}

	float i = 10;
	float *j = &i;
	//&i float指针，指向一个地址，转换为int类型，j就是这个地址
	int k = reinterpret_cast<int>(j);


	//
	
	try {
		testExeceptioon();
	}
	catch (Parent &p) {
		cout << "异常:" << p.i << endl;
	}

	FILE *f2 = fopen("D:\\Lance\\ndk\\lsn5_cpp\\a.txt", "w");

	// windows gb2312
	// 文本形式写入
	fprintf(f2, "i go to %d", 88);
	//操作一张图片 一个视频 二进制的形式
	fclose(f2);


	FILE *f3 = fopen("D:\\Lance\\ndk\\lsn5_cpp\\a.txt", "r");
	
	char buffer[1025]; 

	//遇到 空格 结束读取
	/*while(!feof(f3)){
		fscanf(f3,"%s",buffer);
		cout << "读取到文件：" << buffer << endl;
	}*/

	//最大可以读取 1024个字符 
	//换行、/0都会结束读取
	fgets(buffer, 1025,f3);
	cout << "读取到文件：" << buffer << endl;
	fclose(f3);


	char data[100];
	// 以写模式打开文件
	ofstream outfile;
	outfile.open("D:\\Lance\\ndk\\lsn5_cpp\\b.txt");
	cout << "输入你的名字: ";
	//cin 接收终端的输入
	cin >> data;
	// 向文件写入用户输入的数据
	outfile << data << endl;
	// 关闭打开的文件
	outfile.close();

	// 以读模式打开文件
	ifstream infile;
	infile.open("D:\\Lance\\ndk\\lsn5_cpp\\b.txt");

	cout << "读取文件" << endl;
	infile >> data;
	cout << data << endl;

	// 关闭
	infile.close();

	system("pause");
	return 0;
}
