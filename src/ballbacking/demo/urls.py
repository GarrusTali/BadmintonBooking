from django.conf.urls import patterns, include, url
from django.contrib.staticfiles.urls import staticfiles_urlpatterns
from django.contrib import admin
from demo import view
# Uncomment the next two lines to enable the admin:
# from django.contrib import admin
admin.autodiscover()

import settings
urlpatterns = patterns('',
      url(r'^static/(?P<path>.*)$','django.views.static.serve',{'document_root':settings.STATIC_ROOT}, name='static'),
)

urlpatterns += patterns('',
    # Examples:
    # url(r'^$', 'demo.views.home', name='home'),
    # url(r'^demo/', include('demo.foo.urls')),

    # Uncomment the admin/doc line below to enable admin documentation:
    # url(r'^admin/doc/', include('django.contrib.admindocs.urls')),

    # Uncomment the next line to enable the admin:
    url(r'^admin/', include(admin.site.urls)),
    url(r'^login/$', view.login, name='login'),
    url(r'^regist/ordinary/$',view.ordinary_regist,name = 'ordinary_regist'),
    url(r'^regist/administrator/$',view.administrator_regist,name = 'administrator_regist'),
    url(r'^logout/$',view.logout,name = 'logout'),
    url(r'^booked/$',view.display_reservation,name = 'display_reservation'),
    url(r'^booked/detail/$',view.display_detail,name = 'display_detail'),
    url(r'^booking/$',view.display_order,name = 'display_order'),
    url(r'^modifyinfo/ordinary/$',view.ordinary_modifyinfo,name = 'ordinary_modifyinfo'),
    url(r'^password_modify/$',view.password_modify,name = 'password_modify')   
)