#include <stdlib.h>
#include<stdio.h>
#include<windows.h>
#include<time.h>



typedef int buffer_item;
#define BUFFER_SIZE 5
buffer_item buffer[BUFFER_SIZE];
int in=0;
int out=0;

HANDLE mutex;

HANDLE empty;
HANDLE full;


void insert_item(buffer_item item)
{
	WaitForSingleObject(empty,INFINITE);
	WaitForSingleObject(mutex,INFINITE);


buffer[in]=item;
in = (in+1)%BUFFER_SIZE;

ReleaseSemaphore(mutex,1,NULL);
ReleaseSemaphore(full,1,NULL);


}
void remove_item(buffer_item &item)
{
	WaitForSingleObject(full,INFINITE);
	WaitForSingleObject(mutex,INFINITE);
item=buffer[out];
out=(out+1)%BUFFER_SIZE;


ReleaseSemaphore(mutex,1,NULL);
ReleaseSemaphore(empty,1,NULL);

}
DWORD WINAPI producer(LPVOID Param)
{buffer_item rand1;
while(1)
{Sleep(rand()%3000);
srand(time(0));
rand1=rand()%300;

insert_item(rand1);
printf("producer produced%d\n",rand1);

}
}
DWORD WINAPI consumer(LPVOID Param)
{
buffer_item rand1;
while(1)
{
Sleep(rand()%3000);
remove_item(rand1);

printf("consumer consumed %d\n",rand1);

}
}
int main(int argc,char *argv[])
{



mutex=CreateSemaphore(NULL,1,1,NULL);
full=CreateSemaphore(NULL,0,5,NULL);
empty=CreateSemaphore(NULL,5,5,NULL);




	DWORD ThreadId;
	HANDLE ThreadHandle1;
	HANDLE ThreadHandle2;
	int Param;
	//Param=atoi(argv[1]);
	ThreadHandle1=CreateThread(NULL,0,producer,&Param,0,&ThreadId);
	ThreadHandle2=CreateThread(NULL,0,consumer,&Param,0,&ThreadId);
     
//	if(ThreadHandle1!=NULL || ThreadHandle2!=NULL)
	{
		WaitForSingleObject(ThreadHandle1,INFINITE);
			WaitForSingleObject(ThreadHandle2,INFINITE);}
	
	return 0;

}

