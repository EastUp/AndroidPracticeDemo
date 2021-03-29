// ffmpeg_example.cpp: 定义应用程序的入口点。
//

#include "ffmpeg_example.h"

using namespace std;

extern "C" {
#include <libavcodec/avcodec.h>
#include <libavformat/avformat.h>
}

int main()
{
	//把 所有的dll 放到 c:/windows/system32下去
	cout << av_version_info() << endl;
	avcodec_register_all();

	//avformat_open_input();

	system("pause");
	return 0;
}
