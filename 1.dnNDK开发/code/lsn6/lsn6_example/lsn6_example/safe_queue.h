#pragma once


#include <queue>
#include <pthread.h>
using namespace std;


template <typename T>
class SafeQueue {
public:
	SafeQueue() {
		pthread_mutex_init(&mutex,0);
		pthread_cond_init(&cond,0);
	}
	~SafeQueue() {
		pthread_mutex_destroy(&mutex);
		pthread_cond_destroy(&cond);
	}
	//���� ��������
	void push(T t) {
		pthread_mutex_lock(&mutex);
		q.push(t);
		//notify
		// ��ϵͳ����һ���߳�  ���Ʋ���
		//pthread_cond_signal(&cond);
		// �㲥 ֪ͨ���еȴ����߳�
		pthread_cond_broadcast(&cond);
		pthread_mutex_unlock(&mutex);
	}
	// queueΪ�� ��һֱ�ȴ��� ֪����һ��push ����������
	//���� ȡ����
	void pop(T& t) {

		pthread_mutex_lock(&mutex);
		/*if (!q.empty()) {
			t = q.front();
			q.pop();
		}*/
		//�ȴ������ط�����
		// ���ܱ�ϵͳ���⻽��
		while (q.empty()) {
			// ���� �ͷ��� 
			pthread_cond_wait(&cond, &mutex);
		}
		//
		t = q.front();
		q.pop();
		pthread_mutex_unlock(&mutex);
	}
private:
	queue<T> q;
	pthread_mutex_t mutex;
	//��������
	pthread_cond_t cond;
};