from django.db import models
# Create your models here.
class User(models.Model):
    username = models.CharField(primary_key=True,max_length=30)
    realname = models.CharField(max_length=30)
    password = models.CharField(max_length=30)
    phonenumber = models.CharField(max_length=30)
    isadmin = models.IntegerField()
    def __unicode__(self):
        return self.username

class Stadium(models.Model):
    sname = models.CharField(max_length=60)
    address = models.CharField(max_length=200)
    counter = models.IntegerField()
    description = models.CharField(max_length=200)
    starttime = models.IntegerField()
    stoptime = models.IntegerField()
    user=models.ForeignKey(User)
    def __unicode__(self):
        return self.sname

class Position(models.Model):
    position = models.IntegerField()
    stadium = models.ForeignKey(Stadium)
    def __unicode__(self):
        return str(self.stadium_id) + '-' + str(self.position) 

class Time(models.Model):
    time = models.IntegerField()
    empty = models.IntegerField()
    position = models.ForeignKey(Position)
    def __unicode__(self):
        return str(self.position_id) + '-' + str(self.time) 

class Order(models.Model):
    stadium = models.ForeignKey(Stadium)
    user = models.ForeignKey(User)
    datetime = models.DateTimeField()
    def __unicode__(self):
        return str(self.id)

class OrderPosition(models.Model):
    position = models.IntegerField()
    order = models.ForeignKey(Order)
    def __unicode__(self):
        return str(self.order_id) + '-' + str(self.position)

