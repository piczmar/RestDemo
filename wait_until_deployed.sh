#!/bin/bash

echo $1
# max wait retry * sleep = 600s (10min)
maxRetry=20
retry=1
#until [ $retry < $maxRetry ] && [ "`curl --silent --show-error --connect-timeout 1 -I ${1} | grep 'HTTP/1.1 200'`" != "" ];
until (( $retry > $maxRetry )) || [ "`curl --silent --show-error --connect-timeout 1 -I -L ${1} | grep 'HTTP/1.1 200'`" != "" ];
do
  echo --- sleeping for 30 seconds
  sleep 30
  retry=$(($retry + 1))
done

if (( $retry > $maxRetry )) ; then
  echo Failed to start server
  exit 1
else
  echo Server is ready!
fi