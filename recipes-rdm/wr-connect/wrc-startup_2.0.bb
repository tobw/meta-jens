DESCRIPTION = "WR Connect 2 startup script"

LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://${THISDIR}/wrc-startup-2.0/license.txt;md5=3ebe3464e841ddbf115af1f7019017c5"

SRC_URI += "file://crond-wrc"
SRC_URI += "file://route-up"
SRC_URI += "file://wrc-log.run"
SRC_URI += "file://wrc.run"
SRC_URI += "file://wrc_data-log.run"
SRC_URI += "file://wrc_data.run"
SRC_URI += "file://wrc_register-log.run"
SRC_URI += "file://wrc_register.run"

RDEPENDS_${PN} += "daemontools"
RDEPENDS_${PN} += "ntp-utils"
RDEPENDS_${PN} += "openvpn"
RDEPENDS_${PN} += "hp2sm-system-wrc2"

SERVICE_ROOT = "${sysconfdir}/daemontools/service"
WRC_SERVICE_DIR = "${SERVICE_ROOT}/wrc"
WRC_REGISTER_SERVICE_DIR = "${SERVICE_ROOT}/wrc_register"
WRC_DATA_SERVICE_DIR = "${SERVICE_ROOT}/wrc_data"

WRC_CONFIG_DIR = "${sysconfdir}/wrc"

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

    install -m 0700 -d ${D}${WRC_CONFIG_DIR}

    install -d ${D}${libexecdir}
    install -m 0755 ${WORKDIR}/route-up ${D}${libexecdir}/route-up

    install -d ${D}${sysconfdir}/cron.d
    install -m 0600 ${WORKDIR}/crond-wrc ${D}${sysconfdir}/cron.d/wrc
}
