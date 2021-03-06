//
// Created by 123 on 2020/7/1.
//

#ifndef NDKPRACTICE_LINKEDLIST_HPP
#define NDKPRACTICE_LINKEDLIST_HPP

#include <assert.h>
#include <android/log.h>

// 单链表节点
template<class E>
struct Node {
    Node<E> *pre;
    Node<E> *next;
    E value;
public:
    Node(E value, Node<E> *pre, Node<E> *next) : value(value), pre(pre), next(next) {}
};

// list
template<class E>
class LinkedList {
private:
    // 头节点
    Node<E> *first = NULL;
    // 集合的长度
    int len = 0;
    // 尾节点
    Node<E> *last = NULL;
public:
    void push(E e);

    int size();

    E get(int index);

    // remove insert
    void insert(int index, E e);

    E remove(int index);

    ~LinkedList();

private:
    Node<E> *node(int index);

    void linkLast(E e);

    void linkBefore(Node<E> *pNode, E e);

    E unlink(Node<E> *pNode);
};

template<class E>
void LinkedList<E>::push(E e) {
    // 添加一个数据在列表的后面
    linkLast(e);


    // 下面是单链表的添加
    /*Node<E> *new_node = new Node<E>(e, NULL);

    if (head) {// 0 -> 1 -> 2 -> 3
        // ?
        // head->next = new_node;
        // 找到尾巴节点，有一个特定就是 next 节点为空
        *//*Node<E>* h = head;
        while(h){
            if(h->next == NULL){
                break;
            }
            h = h->next;
        }
        h->next = new_node;*//*
        // 每一次都需要找到最后一个节点  50000
        // 记录 last 节点

        // Node<E> *last = node(len - 1);
        last->next = new_node;// O(1)
    } else {
        head = new_node;
    }
    last = new_node;*/
}

template<class E>
int LinkedList<E>::size() {
    return len;
}

template<class E>
Node<E>* LinkedList<E>::node(int index) { // O(n)
    if(index < (len >> 1)){ // 从开头开始找要快点
        Node<E> *x = first;
        for(int i = 0; i< index; i++){
            x = x->next;
        }
        return x;
    }else{
        // 从后往前遍历
        Node<E> *x = last;
        for(int i = len-1; i > index; i--){
            x = x->pre;
        }
        return x;
    }
}


template<class E>
E LinkedList<E>::get(int index) {
    assert(index>=0 && index < len);
    return node(index)->value;
}

template<class E>
void LinkedList<E>::insert(int index, E e) { // len = 4;
    assert(index>=0 && index <= len);
    // 考虑边界 0
    if(index == len){
        linkLast(e);
    }else{
        linkBefore(node(index),e);
    }


    // 下面是单链表的插入
    /*Node<E> *new_node = new Node<E>(e, NULL);
    if (index == 0) {
        Node<E> *h = head;
        head = new_node;
        new_node->next = h;
    } else {
        // 考虑最后一个位置
        Node<E> *prev = node(index - 1);
        Node<E> *next = prev->next;// NULL
        prev->next = new_node;
        new_node->next = next;
    }*/
}

template<class E>
E LinkedList<E>::remove(int index) {
    // 考虑边界问题 0 , len ，mid
    assert(index>=0 && index < len);
    return unlink(node(index));

    // 单链表的移除
    /*if (index == 0) {
    Node<E> *h = head;
    head = h->next;
    // 释放
    delete h;
    } else {
        Node<E> *prev = node(index - 1);
        // 删除的节点
        Node<E> *cur = prev->next;
        prev->next = cur->next;
        delete cur;
    }*/
}

template<class E>
LinkedList<E>::~LinkedList() {
    // 析构释放内存 ？
    for(int i=0; i < len; i++){
        delete(node(i));
    }

    // 头指针和尾指针置为NULL
    first = NULL;
    last = NULL;
}


template<class E>
void LinkedList<E>::linkLast(E e) {
    Node<E> *l = last;
    Node<E> *new_node = new Node<E>(e,l,NULL);
    last = new_node;

    if(l){
        l->next = new_node;
    } else
        first = new_node;
    len ++;
}

template<class E>
void LinkedList<E>::linkBefore(Node<E> *pNode, E e) {
    Node<E> *pre = pNode->pre;// NULL
    Node<E> *new_node = new Node<E>(e,pre,pNode);
    // 当前节点的上一个节点 = 新增的节点
    pNode->pre = new_node;
    // 上一个节点的 next -> 新增的节点 ， 0 特殊处理
    if(pre){
        pre->next = new_node;
    } else
        first = new_node;
    len ++;
}

template<class E>
E LinkedList<E>::unlink(Node<E> *pNode) {
    E e = pNode->value;
    // 左右两个节点
    Node<E> *pre = pNode->pre;
    Node<E> *next = pNode->next;

    // 有两个为空的情况，严谨，思维灵活
    if(pre){
        pre->next = next;
        pNode->pre = NULL;
    } else
        first = next;

    if(next){
        next->pre = pre;
        pNode->next = NULL;
    }else
        last = pre;

    pNode -> value = NULL;
    delete pNode;
    len --;
    return e;
}



#endif //NDKPRACTICE_LINKEDLIST_HPP
