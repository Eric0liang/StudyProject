/* 模块名是 Unix. */
%module Unix

%{
/* 包含POSIX操作系统API. */
#include<unistd.h>
%}

/* 告诉 SWIG uid_t. */
typedef unsigned int uid_t;

/* 让SWIG 包装getuid函数. */
extern uid_t getuid(void);