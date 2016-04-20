FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

RRECOMMENDS_${PN} += "ethtool"

PACKAGE_ARCH = "${MACHINE_ARCH}"

RDEPENDS_${PN}_append = "\
    file-slurp-tiny-perl \
    errhlp \
    ledctrl \
    perl \
    netaddr-ip-perl \
"

SRC_URI += "\
    file://activate.pl \
    file://check4dad.sh \
    file://wifi/pre_up.sh \
    file://wifi/post_up.sh \
    file://wifi/pre_down.sh \
    file://wifi/post_down.sh \
"

do_compile_append () {
    sed -i -e "s,@LEDCTRL[@],${libdir}/ledctrl,g" \
            -e "s,@BINDIR[@],${bindir},g" \
            -e "s,@SBINDIR[@],${sbindir},g" \
        ${WORKDIR}/*.sh \
        ${WORKDIR}/wifi/*.sh
}

do_install_append () {
    install -d ${D}${sysconfdir}/network/wifi
    install -d ${D}${sysconfdir}/network/mapping
    install -d ${D}${sysconfdir}/network/if-up.d

    install -m 0755 ${WORKDIR}/check4dad.sh ${D}${sysconfdir}/network/if-up.d/check4dad
    install -m 0755 ${WORKDIR}/activate.pl ${D}${sysconfdir}/network/mapping/activate
    install -m 0755 ${WORKDIR}/wifi/pre_up.sh ${D}${sysconfdir}/network/wifi
    install -m 0755 ${WORKDIR}/wifi/pre_down.sh ${D}${sysconfdir}/network/wifi
    install -m 0755 ${WORKDIR}/wifi/post_up.sh ${D}${sysconfdir}/network/wifi
    install -m 0755 ${WORKDIR}/wifi/post_down.sh ${D}${sysconfdir}/network/wifi
}

FILES_${PN} += "/etc/network"
