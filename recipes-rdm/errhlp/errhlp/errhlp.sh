#!/bin/sh

function fail () {
    echo "$@" >&2
    exit 1
}

test $# -eq 1 || fail "$0 <error-identifier>"

. @LEDCTRL@/ledctrl

CALL_NAME=`basename "$0"`

case "$CALL_NAME" in
    enable-error)
        touch "/run/hp-errors/$1"
	# led_error
        ;;
    disable-error)
        rm -f "/run/hp-errors/$1"
        test /run/hp-errors/* = "/run/hp-errors/*" && silence_error
        ;;
    enable-counted-error)
        test -f "/run/hp-errors/count-$1" && ERRCNT=$(cat /run/hp-errors/count-$1)
	test -z "$ERRCNT" && ERRCNT=0
	ERRCNT=$(expr $ERRCNT + 1)
	echo "$ERRCNT" > /run/hp-errors/count-$1
	# led_error
        ;;
    disable-counted-error)
        test -f "/run/hp-errors/count-$1" && ERRCNT=$(cat /run/hp-errors/count-$1)
	test -z "$ERRCNT" && ERRCNT=1
	ERRCNT=$(expr $ERRCNT - 1)
	echo "$ERRCNT" > /run/hp-errors/count-$1
	test "$ERRCNT" -gt 0 || rm -f "/run/hp-errors/count-$1"
        test /run/hp-errors/* = "/run/hp-errors/*" && silence_error
        ;;
    *)
        fail "<enable-error|disable-error|enable-counted-error|disable-counted-error> <error-identifier>"
        ;;
esac

test -S /dev/log && logger -s "$0 $1" || echo "$0 $1" >&2
exit 0
