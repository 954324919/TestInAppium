#!/usr/bin/env python
# -*- coding: utf-8 -*-

#需要安装pychartdir模块，http://blog.csdn.net/gb112211/article/details/43272049

import string

import utils
from pychartdir import *
import time
from nt import times

PATH = lambda p: os.path.abspath(p)

#打开待测应用，运行脚本，默认times为20次（可自己手动修改次数），获取该应用cpu、memory占用率的曲线图，图表保存至chart目录下

#top次数
# times = 5
times = 40

#设备当前运行应用的包名
pkg_name = 'com.cmic.mmnes'

#当前文件的路径
pwd = os.getcwd()
#当前文件的父路径
targetDir = os.path.abspath(os.path.join(os.getcwd(), "../../../../../../../../"))
#实际路径
chartPath = targetDir+os.path.sep+'target'+os.path.sep+'chart'

#获取cpu、mem占用
def top():
    cpu = []
    mem = []
    # -i时间间隙
    top_info = utils.shell("top -n %s | %s %s$" %(str(times), utils.find_util, pkg_name)).stdout.readlines()

    for info in top_info:
        temp_list = info.split()
        cpu.append(temp_list[4])
        mem.append(temp_list[8])

    return (cpu, mem)

#绘制线性图表，具体接口的用法查看ChartDirecto的帮助文档
def line_chart():
    data = top()
    cpu_data = []
    mem_data = []
    
    #去掉cpu占用率中的百分号，并转换为int型
    for cpu in data[0]:
        cpu_data.append(string.atoi(cpu.split("%")[0]))
    
    #去掉内存占用中的单位K，并转换为int型，以M为单位  
    for mem in data[1]:
        mem_data.append(string.atof(mem.split("K")[0])/1024/10)
    
    #横坐标
    labels = []
    for i in range(1, times + 1):
        labels.append(str(i))
    
    #自动设置图表区域宽度
    if times <= 50:
        xArea = times * 40
    elif 50 < times <= 90:
        xArea = times * 20
    else:
        xArea = 1800
        
    c = XYChart(xArea, 800, 0xCCEEFF, 0x000000, 1)
    c.setPlotArea(60, 100, xArea - 100, 650)
    c.addLegend(50, 30, 0, "arialbd.ttf", 15).setBackground(Transparent)
    
    c.addTitle("cpu and memery info(%s)" %pkg_name, "timesbi.ttf", 15).setBackground(0xCCEEFF, 0x000000, glassEffect())
    c.yAxis().setTitle("The numerical", "arialbd.ttf", 12)
    c.xAxis().setTitle("Times", "arialbd.ttf", 12)
    
    c.xAxis().setLabels(labels)
    
    #自动设置X轴步长
    if times <= 50:
        step = 1
    else:
        step = times / 50 + 1
    
    c.xAxis().setLabelStep(step)
    
    layer = c.addLineLayer()
    layer.setLineWidth(2)
    layer.addDataSet(cpu_data, 0xff0000, "CPU(%)")
    layer.addDataSet(mem_data, 0x008800, "Memory(M)")
    
    # os.chdir("../../../../../../../target") #由于本项目路径的特俗性             
    # mPath=PATH("%s/chart" %os.getcwd())                              
    if not os.path.isdir(chartPath):                                     
        os.makedirs(chartPath)                                           
    
    #图片保存至脚本当前目录的chart目录下
    c.makeChart(PATH("%s/%s.png" %(chartPath, utils.timestamp())))

if __name__ == "__main__":
    line_chart()
    print 'Analyze Chart Draw Finish..'
    