#!/bin/sh

if [ -f /etc/ip6tables.rules ]; then
	@SBINDIR@/ip6tables-restore < /etc/ip6tables.rules
fi

exit 0
