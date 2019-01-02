#!/bin/sh
java_home=env|grep "JAVA_HOME";
process_exists=`ps -ef|grep poseidon_data_access-2.0.0|grep -v grep|awk '{print $2}'`
if [ -n "${process_exists}" ];then
	kill -9 ${process_exists}
fi

if [ ! -n "$java_home" ]; then
	cd ..;
        nohup java -jar iot_access-1.0.0.jar > /dev/null &
	echo "spring_base application is starting......";
        exit;
else
	java -version;
	if [ $? -eq 0]; then
		nohup java -jar iot_access-1.0.0.jar > /dev/null &
	else	
		echo "this system has no jdk";
		exit;
	fi
fi
