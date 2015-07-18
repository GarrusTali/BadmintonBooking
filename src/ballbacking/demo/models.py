from django.db import models
# Create your models here.
class User(models.Model):
    username=models.CharField(primary_key=True,max_length=30)
    realname=models.CharField(max_length=30)
    password=models.CharField(max_length=30)
    phonenumber=models.CharField(max_length=30)
    isadmin = models.IntegerField()
    def __unicode__(self):
        return self.username

class Stadium(models.Model):
    sname = models.CharField(max_length=60)
    address=models.CharField(max_length=200)
    counter = models.IntegerField()
    description=models.CharField(max_length=200)
    user=models.ForeignKey(User)
    def __unicode__(self):
        return self.sname

class Position(models.Model):
    position=models.IntegerField()
    stadium=models.ForeignKey(Stadium)
    def __unicode__(self):
        return self.position

class Time(models.Model):
    time=models.IntegerField()
    empty=models.IntegerField()
    position=models.ForeignKey(Position)
    def __unicode__(self):
        return self.time

class Order(models.Model):
    #applyTime=models.DateField()
    stadium = models.ForeignKey(Stadium)
    user = models.ForeignKey(User)
    def __unicode__(self):
        return self.id

class OrderPosition(models.Model):
    position=models.IntegerField()
    order=models.ForeignKey(Order)
    def __unicode__(self):
        return self.position

class OrderTime(models.Model):
    time=models.IntegerField()
    orderPosition=models.ForeignKey(OrderPosition)
    def __unicode__(self):
        return self.time
