#!/bin/sh

@BINDIR@/disable-error failed-DAD
ADDR=`ip addr show $IFACE | egrep '\<inet\>' | awk '{print $2}' | sed -e 's,/.*,,'`
@SBINDIR@/arping -c 5 -D -I $IFACE $ADDR || @BINDIR@/enable-error failed-DAD

:
