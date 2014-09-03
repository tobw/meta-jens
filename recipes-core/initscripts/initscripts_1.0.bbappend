FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://mountunion.sh \
	file://umountunion.sh \
	file://wifi-fallback.sh \
	file://wr-connect.sh \
"

do_install_append () {
    install -m 0755 ${WORKDIR}/mountunion.sh ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/umountunion.sh ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/wifi-fallback.sh ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/wr-connect.sh ${D}${sysconfdir}/init.d

    update-rc.d -r ${D} umountunion.sh start 30 0 1 6 .
    update-rc.d -r ${D} mountunion.sh start 16 2 3 4 5 S .

    update-rc.d -r ${D} wifi-fallback.sh start 98 3 5 .
    update-rc.d -r ${D} wr-connect.sh start 95 3 5 .

    # mount by-* requires udev being started
    mv -f ${D}/${sysconfdir}/rcS.d/S03mountall.sh ${D}/${sysconfdir}/rcS.d/S04mountall.sh 
}
