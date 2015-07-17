#coding=utf8
from django.template.loader import get_template
from django.template import Context
from django.http import HttpResponse, HttpResponseRedirect
from django.shortcuts import render_to_response, render
from django.template import RequestContext
from django.views.decorators.csrf import csrf_exempt
from django import forms
from models import User,Stadium,Position,Time,Order,OrderPosition,OrderTime 
import datetime
import json

#普通用户注册
def ordinary_regist(request):
    if request.method=='POST':
        #获得表单数据
        username=request.POST.get('username', '')
        realname=request.POST.get('realname', '')
        phonenum=request.POST.get('phonenum', '')
        password=request.POST.get('password', '')
        confirm_password=request.POST.get('confirm_password', '')
        isadmin=0

        #添加到数据库
        User.objects.create(
            username=username,
            realname=realname,
            phonenumber=phonenum,
            password=password,
            isadmin=isadmin
            )
        return HttpResponse('regist success!!')
    else:
        return render_to_response('regist_ordinary.html')

#球场拥有者注册
def administrator_regist(request):
    if request.method=='POST':
        #获得表单数据
        username=request.POST.get('username', '')
        realname=""
        sname=request.POST.get('sname', '')
        address=request.POST.get('address', '')
        description=request.POST.get('description', '')
        counter=request.POST.get('counter', '')
        password=request.POST.get('password', '')
        phonenum=request.POST.get('phonenum', '')
        confirm_password=request.POST.get('confirm_password', '')
        
        isadmin=1
        print "success1"
        #添加到数据库
        if username and sname and address and description and counter and password and phonenum and confirm_password:
            exit=User.objects.filter(username=username)
            print "success2"
            if exit:
                return HttpResponse('regist faild!!')
            else:
                user=User.objects.create(
                username=username,
                realname=realname,
                phonenumber=phonenum,
                password=password,
                isadmin=isadmin
                )
                print "success3"
                stadium=Stadium.objects.create(
                    sname=sname,
                    address=address,
                    description=description,
                    counter=counter,
                    user=user
                    )

                counter=int(counter)
                print "success4"
                for i in range(1, counter):
                    position=Position.objects.create(
                        position=i,
                        stadium=stadium
                        )
                    timer=14
                    for j in range(1, timer):
                        time=Time.objects.create(
                            time=j,
                            empty=0,
                            position=position
                            )
                print "success"
                return HttpResponse('regist success!!')
        else:
            print "failed"
            return HttpResponse('regist faild!!')
    else:
        return render_to_response('regist_administrator.html', context_instance=RequestContext(request))

#登陆
def login(request):
    if request.method=='POST':
        #获取表单用户密码
        username=request.POST.get('username', '')
        password=request.POST.get('password', '')
        print username
        print password
        #获取的表单数据与数据库进行比较
        user=User.objects.filter(username=username,password=password)
        
        if user:
            
            request.session['username'] = username
            #将username写入浏览器cookie,失效时间为3600
            #response.set_cookie('username',username,3600)
            return HttpResponse('login success!!')
        else:
            #比较失败，还在login
            return HttpResponse('login falied!!')
    else:
        return render_to_response('login.html',context_instance=RequestContext(request))

#退出
def logout(request):
    del request.session['username']
    return HttpResponse('logout ok!')

#可预定列表
def display_reservation(request):
    if request.method=='GET':
        username = request.session.get('username','anybody')
        stadium=Stadium.objects.all()
        print stadium
        stadium_list=[]
        print 1
        for i in stadium:
            user=User.objects.get(username=i.user)
            temp={
                "id":i.id,
                "sname":i.sname,
                "phonenum":user.phonenumber,
                "address":i.address,
                "counter":i.counter,
                "description":i.description
            }
            stadium_list.append(temp)
        stadium_list=json.dumps(stadium_list)
        return HttpResponse(stadium_list)

#预定详情
def display_detail(request):
    if request.method=='POST':
        stadium_id=request.POST.get('stadium_id', '')
        display_detail={}
        #获取球场
        stadium=Stadium.objects.get(id=stadium_id)
        if stadium:
            #获取球场所有位置
            position=Position.objects.filter(stadium_id=stadium_id)
            user=User.objects.get(username=stadium.user)
            display_detail={
                "id":stadium.id,
                "sname":stadium.sname,
                "phonenum":user.phonenum,                
                "address":stadium.address,
                "counter":stadium.counter,
                "description":stadium.description,
                "position":[]

            }
            for i in position:
                #获取对应位置时间段
                time=Time.objects.filter(position_id=i.id)
                temp_pos={
                    "position":i.position,
                    "time":[]
                }
                for j in time:
                    temp_time={
                        "time":j.time,
                        "empty":j.empty
                    }
                    temp_pos["time"].append(temp_time)
                display_detail["position"].append(temp_pos)
            display_detail=json.dumps(display_detail)
            return HttpResponse(display_detail)
        else:
            return HttpResponse("failed!!")

#订单页面
def display_order(request):
    if request.method=='GET':
        username = request.session.get('username','anybody')
        display_order=[]
        user=User.objects.get(username="123")
        #获取个人订单
        order=Order.objects.filter(user_id=user)
        for i in order:
            stadium=Stadium.objects.get(id=i.stadium.id)
            temp={
                "id":i.id,
                "sname":stadium.sname,
                "address":stadium.address,
                "counter":stadium.counter,
                "description":stadium.description,
                #"applyTime":i.time,
                "position":[]
                }
            #获取该订单预订位置
            orderPosition=OrderPosition.objects.filter(order=order)
            for j in orderPosition:
                orderTime=OrderTime.objects.filter(orderPosition_id=j.id)
                temp_pos={
                    "position":j.position,
                    "time":[]
                    }
                for k in orderTime:
                    temp_time={
                        "time":k.time
                        }
                    temp_pos["time"].append(temp_time)
                temp["position"].append(temp_pos)
            display_order.append(temp)
            print display_order
            display_order=json.dumps(display_order)
            return HttpResponse(display_order)
    else:
        username = request.session.get('username','anybody')
        order=request.POST.get('order', '')
        stadium_id=order.id
        #查找用户
        user=User.objects.get(username=username)
        #查找球场
        stadium=Stadium.objects.get(id=stadium_id)
        if user and stadium:
            #订单创建
            order=Order.objects.create(
                appltTime=date(),
                stadium=stadium,
                user=user
                )
            #遍历订单中的所有位置
            for i in order.position:
                position=i.position
                #订单位置创建
                orderPosition=orderPosition.objects.create(
                    position=position,
                    order=order
                    )
                #遍历订单中对应位置的时间
                for j in i.time:
                    time=j.time
                    #订单时间段创建
                    orderTime=orderTime.objects.create(
                        time=time,
                        orderPosition=orderPosition
                        )
                    #更新球场空余位置信息
                    _position=Position.objects.get(stadium_id=stadium_id,position=position)
                    _time=Time.objects.get(position_id=_position.id,time=time)
                    _time.empty=1
                    _time.save()

def ordinary_modifyinfo(request):
    if request.method == 'POST':
        username=request.POST.get('username', '')
        realname=request.POST.get('realname', '')
        phonenum=request.POST.get('phonenum', '')
        user=User.objects.filter(username=username)
        user.realname=realname                           
        user.phonenumber=phonenum
        user.save()
        return HttpResponse('modify success!!')
    elif request.method == 'GET':
        username = request.session.get('username','anybody')
        user=User.objects.filter(username=username)
        info = {"username":username,"realname":user.realname,"phonenumber":user.phonenumber}
        return HttpResponse(info)
    else:
        return render_to_response('ordinary_modifyinfo.html',context_instance=RequestContext(request))

def password_modify(request):
    if request.method == 'POST':
        oldpass=request.POST.get('oldpass', '')
        newpass=request.POST.get('newpass', '')
        confirmpass=request.POST.get('confirm_password', '')
        username = request.session.get('username','anybody')
        user=User.objects.filter(username=username)
        if user.password==oldpass:
            if newpass == confirmpass:
                user.password=newpass
                user.save()
                return HttpResponse('modify success!!')
            else:
                return HttpResponse('new_password and confirm_password should be the same!!')
        else:
            return HttpResponse('old password incorrect!!')
    else:
        return render_to_response('password_modify.html',context_instance=RequestContext(request))
