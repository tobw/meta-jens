#!/bin/sh

if [ -f /etc/iptables.rules ]; then
	@SBINDIR@/iptables-restore < /etc/iptables.rules
fi

exit 0
