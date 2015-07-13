SUMMARY = "A full-featured SSL VPN solution via tun device"
HOMEPAGE = "http://openvpn.sourceforge.net"
SECTION = "console/network"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=5aac200199fde47501876cba7263cb0c"
DEPENDS = "libpam lzo openssl"

inherit autotools

SRC_URI = "https://swupdate.openvpn.org/community/releases/openvpn-${PV}.tar.gz"

SRC_URI[md5sum] = "070bca95e478f88dff9ec6a221e2c3f7"
SRC_URI[sha256sum] = "1f02a4cd6aeb6250ca9311560875b10ce8957a3c9101a8005bd1e17e5b03146e"

CFLAGS += "-fno-inline"

EXTRA_OECONF += "--disable-server"

FILES_${PN}-dbg += "/usr/lib/openvpn/plugins/.debug"

RRECOMMENDS_${PN} = "kernel-module-tun"
