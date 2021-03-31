#pragma once


class Instance {
private:
	static Instance* instance;
	Instance();
public:
	static Instance* getInstance();
};