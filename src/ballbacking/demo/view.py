#coding=utf8
from django.template.loader import get_template
from django.template import Context
from django.http import HttpResponse, HttpResponseRedirect
from django.shortcuts import render_to_response, render
from django.template import RequestContext
from django.views.decorators.csrf import csrf_exempt
from django.utils.encoding import smart_str
from django.utils import timezone
from django import forms
from models import User,Stadium,Position,Time,Order,OrderPosition 
import json

#普通用户注册
def ordinary_regist(request):
    if request.method == 'POST':
        #获得表单数据
        username = request.POST.get('username', '')
        realname = request.POST.get('realname', '')
        phonenumber = request.POST.get('phonenumber', '')
        password = request.POST.get('password', '')
        confirm_password = request.POST.get('confirm_password', '')
        isadmin = 0

        #每个属性都要填满
        if not (username and realname and phonenumber and password and confirm_password):
            return HttpResponse('empty data exist')

        #username不能重复
        if User.objects.filter(username=username):
            return HttpResponse('username exists')

        #新密码跟确认密码必须相同
        if password != confirm_password:
            return HttpResponse('password diff')

        #添加到数据库
        User.objects.create(
            username=username,
            realname=realname,
            phonenumber=phonenumber,
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
        username = request.POST.get('username', '')
        realname = ""
        sname = request.POST.get('sname', '')
        address = request.POST.get('address', '')
        description = request.POST.get('description', '')
        counter = request.POST.get('counter', '')
        password = request.POST.get('password', '')
        phonenumber = request.POST.get('phonenumber', '')
        confirm_password = request.POST.get('confirm_password', '')
        starttime = 9
        stoptime = 22
        
        isadmin = 1
        #添加到数据库
        if not (username and sname and address and description and counter and password and phonenumber and confirm_password
            and starttime and stoptime):
            return HttpResponse('empty data exist')

        #username不能重复
        if User.objects.filter(username=username):
            return HttpResponse('username exists')

        if password != confirm_password:
            return HttpResponse('password diff')        

        if int(counter) > 20:
            return HttpResponse('too many courts')

        user=User.objects.create(
            username=username,
            realname=realname,
            phonenumber=phonenumber,
            password=password,
            isadmin=isadmin
        )
        stadium=Stadium.objects.create(
            sname=sname,
            address=address,
            description=description,
            counter=counter,
            user=user,
            starttime=starttime,
            stoptime=stoptime
        )

        for i in range(1, int(counter)+1):
            position=Position.objects.create(
                position=i,
                stadium=stadium
                )
            for j in range(starttime, stoptime):
                time=Time.objects.create(
                    time=j,
                    empty=1,
                    position=position
                )
        return HttpResponse('regist success!!')
    else:
        return render_to_response('regist_administrator.html', context_instance=RequestContext(request))

#登陆
def login(request):
    if request.method=='POST':
        #获取表单用户密码
        username = request.POST.get('username', '')
        password = request.POST.get('password', '')

        #获取的表单数据与数据库进行比较
        user = User.objects.filter(username=username,password=password)
        
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
    if request.method == 'GET':
        username = request.session.get('username','anybody')
        stadium = Stadium.objects.all()
        stadium_list = []
        for i in stadium:
            user = User.objects.get(username=i.user)
            temp = {
                "id":i.id,
                "sname":i.sname,
                "phonenumber":user.phonenumber,
                "address":i.address,
                "counter":i.counter,
                "description":i.description
            }
            stadium_list.append(temp)
        stadium_list = json.dumps(stadium_list)
        return HttpResponse(stadium_list)

#预定详情
def display_detail(request):
    if request.method == 'POST':
        stadium_id = request.POST.get('stadium_id', '')
        time = request.POST.get('time','')

        if time:
            position = Position.objects.filter(stadium_id=stadium_id)
            court = []
            for pos in position:
                timepos = Time.objects.get(position_id=pos.id,time=time)
                court.append(timepos.empty)
            court = json.dumps(court)
            return HttpResponse(court)
        else:
            stadium = Stadium.objects.get(id=stadium_id)
            user = User.objects.get(username=stadium.user)
            stadium_detail = {
                "sname":stadium.sname,
                "phonenumber":user.phonenumber,                
                "address":stadium.address,
                "counter":stadium.counter,
                "description":stadium.description,
                "hour":timezone.localtime(timezone.now()).hour               
            }
            stadium_detail = json.dumps(stadium_detail)
            return HttpResponse(stadium_detail)

#订单页面
def display_order(request):
    if request.method == 'POST':
        username = request.session.get('username','anybody')
        stadium_id = request.POST.get('stadium_id', '')
        time = request.POST.get('time','')
        position = request.POST.get('position','')

        user = User.objects.get(username=username)
        stadium = Stadium.objects.get(id=stadium_id)
        curtime = timezone.localtime(timezone.now())
        booktime = curtime.replace(hour=int(time))
        order = Order.objects.create(
            stadium=stadium,
            user=user,
            datetime=booktime
        )

        position = position.split(',')
        for posint in position:
            orderPosition = OrderPosition.objects.create(
                position=posint,
                order=order
            )

            timepos = Time.objects.get(position_id=int(posint)+1,time=time)
            timepos.empty = 0
            timepos.save()
        return HttpResponse('order accepted')        
    elif request.method == 'GET':
        username = request.session.get('username','anybody')
        user=User.objects.get(username=username)

        display_order = []
        orders = Order.objects.filter(user_id=user)
        for order in orders:
            stadium = Stadium.objects.get(id=order.stadium_id)
            positions = OrderPosition.objects.filter(order=order)
            datetime = timezone.localtime(order.datetime)
            mypos = []
            for pos in positions:
                mypos.append(pos.position)

            showorder = {
                "sname":stadium.sname,
                "address":stadium.address,
                "time":{
                    "year":datetime.year,
                    "month":datetime.month,
                    "day":datetime.day,
                    "hour":datetime.hour
                },
                "position":mypos
            }
            display_order.append(showorder)

        display_order = json.dumps(display_order)
        return HttpResponse(display_order)

def ordinary_modifyinfo(request):
    if request.method == 'POST':
        username = request.session.get('username', 'anybody')
        realname = request.POST.get('realname', '')
        phonenumber = request.POST.get('phonenumber', '')

        if not (realname and phonenumber):
            return HttpResponse('empty data exist')

        user = User.objects.get(username=username)
        user.realname = realname                           
        user.phonenumber = phonenumber
        user.save()
        return HttpResponse('modify success!!')
    elif request.method == 'GET':
        username = request.session.get('username','anybody')
        user = User.objects.get(username=username)
        info = {"username":smart_str(username),"realname":smart_str(user.realname),"phonenumber":smart_str(user.phonenumber)}
        return HttpResponse(json.dumps(info, ensure_ascii=False))
    else:
        return render_to_response('ordinary_modifyinfo.html',context_instance=RequestContext(request))

def password_modify(request):
    if request.method == 'POST':
        oldpass = request.POST.get('oldpass', '')
        newpass = request.POST.get('newpass', '')
        confirmpass = request.POST.get('confirm_password', '')
        username = request.session.get('username','anybody')
        user = User.objects.get(username=username)
        if not newpass:
            return HttpResponse('no newpass')
        if user.password == oldpass:
            if newpass == confirmpass:
                user.password = newpass
                user.save()
                return HttpResponse('modify success!!')
            else:
                return HttpResponse('password diff')
        else:
            return HttpResponse('old password incorrect')
    else:
        return render_to_response('password_modify.html',context_instance = RequestContext(request))
