#
# @author liangjiangli
#

# 检查变量 SWIG_PACKAGE是否已经定义
ifndef SWIG_PACKAGE
    $(error SWIG_PACKAGE is not defined.)
endif

# 用斜线替换Java目录的圆点
SWIG_OUTDIR:=$(NDK_PROJECT_PATH)/src/$(subst .,/,$(SWIG_PACKAGE))

# SWIG的默认类型是C
ifndef SWIG_TYPE
    SWIG_TYPE := C
endif

# 设置SWIG的模式
#ifeq ($(SWIG_TYPE), cxx)
#    SWIG_TYPE_MODE := - c++
#else
#    SWIG_TYPE_MODE :=
#endif

# 追加SWIG封装源文件
LOCAL_SRC_FILES += $(foreach SWIG_INTERFACE, $(SWIG_INTERFACES),\
$(basename $(SWIG_INTERFACE))_wrap.$(SWIG_TYPE))

# 添加.cxx作为C++扩展名
#LOCAL_CPP_EXTENSION += .cxx

# 生成SWIG封闭代码(indention should be tabs for this block)
$_wap.$(SWIG_TYPE) : %.i \
      $(call host-mkdir, $(SWIG_OUTDIR)) \
      swig -java \
      $(SWIG_MODE) \
      -package $(SWIG_PACKAGE) \
      -outdir $(SWIG_OUTDIR) \
      $<