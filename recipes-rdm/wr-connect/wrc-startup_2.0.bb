DESCRIPTION = "WR Connect 2 startup script"

LICENSE = "commercial"
LIC_FILES_CHKSUM = "file://${THISDIR}/wrc-startup-2.0/license.txt;md5=3ebe3464e841ddbf115af1f7019017c5"

SRC_URI += "file://route-up"
SRC_URI += "file://volatile.conf"
SRC_URI += "file://wrc-log.run"
SRC_URI += "file://wrc.run"
SRC_URI += "file://wrc_data-log.run"
SRC_URI += "file://wrc_data.run"
SRC_URI += "file://wrc_register-log.run"
SRC_URI += "file://wrc_register.run"

RDEPENDS_${PN} += "daemontools"
RDEPENDS_${PN} += "hp2sm"
RDEPENDS_${PN} += "openvpn"

SERVICE_ROOT = "${sysconfdir}/daemontools/service"
WRC_SERVICE_DIR = "${SERVICE_ROOT}/wrc"
WRC_REGISTER_SERVICE_DIR = "${SERVICE_ROOT}/wrc_register"
WRC_DATA_SERVICE_DIR = "${SERVICE_ROOT}/wrc_data"

WRC_BASE_DIR = "/opt/rdm/wrc"
WRC_SCRIPTS_DIR = "${WRC_BASE_DIR}/openvpn"

WRC_OPENVPN_DIR = "/var/lib/openvpn"

do_install () {
    install -d ${D}${WRC_SERVICE_DIR}
    install -d ${D}${WRC_SERVICE_DIR}/log
    install -m 0755 ${WORKDIR}/wrc.run ${D}${WRC_SERVICE_DIR}/run
    install -m 0755 ${WORKDIR}/wrc-log.run ${D}${WRC_SERVICE_DIR}/log/run
    touch ${D}${WRC_SERVICE_DIR}/down

    install -d ${D}${WRC_DATA_SERVICE_DIR}
    install -d ${D}${WRC_DATA_SERVICE_DIR}/log
    install -m 0755 ${WORKDIR}/wrc_data.run ${D}${WRC_DATA_SERVICE_DIR}/run
    install -m 0755 ${WORKDIR}/wrc_data-log.run ${D}${WRC_DATA_SERVICE_DIR}/log/run
    touch ${D}${WRC_DATA_SERVICE_DIR}/down

    install -d ${D}${WRC_REGISTER_SERVICE_DIR}
    install -d ${D}${WRC_REGISTER_SERVICE_DIR}/log
    install -m 0755 ${WORKDIR}/wrc_register.run ${D}${WRC_REGISTER_SERVICE_DIR}/run
    install -m 0755 ${WORKDIR}/wrc_register-log.run ${D}${WRC_REGISTER_SERVICE_DIR}/log/run
    touch ${D}${WRC_REGISTER_SERVICE_DIR}/down

    install -d ${D}${WRC_OPENVPN_DIR}
    install -d ${D}${sysconfdir}/default/volatiles
    install -m 0644 ${WORKDIR}/volatile.conf ${D}${sysconfdir}/default/volatiles/99_wrc

    install -d ${D}${WRC_SCRIPTS_DIR}
    install -m 0755 ${WORKDIR}/route-up ${D}${WRC_SCRIPTS_DIR}/route-up
}

FILES_${PN} += "${SERVICE_ROOT} ${WRC_BASE_DIR} ${WRC_OPENVPN_DIR}"
