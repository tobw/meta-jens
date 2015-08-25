SUMMARY = "A full-featured SSL VPN solution via tun device"
HOMEPAGE = "http://openvpn.sourceforge.net"
SECTION = "console/network"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=5aac200199fde47501876cba7263cb0c"
DEPENDS = "libpam lzo openssl"

inherit autotools

SRC_URI = "https://swupdate.openvpn.org/community/releases/openvpn-${PV}.tar.gz"

SRC_URI[md5sum] = "51d996f1f1fc30f501ae251a254effeb"
SRC_URI[sha256sum] = "532435eff61c14b44a583f27b72f93e7864e96c95fe51134ec0ad4b1b1107c51"

CFLAGS += "-fno-inline"

EXTRA_OECONF += "--disable-server"

FILES_${PN}-dbg += "/usr/lib/openvpn/plugins/.debug"

RRECOMMENDS_${PN} = "kernel-module-tun"
