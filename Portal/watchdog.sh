#!/bin/bash 

service=apacheds-2.0.0-M17

content2=$(wget http://gwpcc:8080/cc/ping -q -O -)
# echo $content2
content3=$(wget http://gwpbc:8580/bc/ping -q -O -)
# echo $content3
content4=$(wget http://gwppc:8180/pc/ping -q -O -)
# echo $content4

if [ $content2 -ne 2 ];
then 
cat /etc/properties/servicedown | mail -s "cc is down" 4054133912@txt.att.net
fi
if [ $content3 -ne 2 ];
then 
cat /etc/properties/servicedown | mail -s "bc is down" 4054133912@txt.att.net
fi
if [ $content4 -ne 2 ];
then 
cat /etc/properties/servicedown | mail -s "pc is down" 4054133912@txt.att.net
fi
if (( $(ps -ef | grep -v grep | grep $service | wc -l) > 0 ))
then
#echo "$service is running!!!"
else
cat /etc/properties/servicedown | mail -s "apacheds is down" 4054133912@txt.att.net
/etc/init.d/$service start
fi


