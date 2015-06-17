DESCRIPTION = "WRConnect startup script"
 
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

PR = "r0"

SRC_URI = "file://run \
	file://log-run \
"
#XXX maybe add dropbear to depends
RDEPENDS_${PN} += "daemontools"

SERVICE_ROOT = "${sysconfdir}/daemontools/service"
WRC_SERVICE_DIR = "${SERVICE_ROOT}/wrc"

do_install () {
        install -d ${D}${WRC_SERVICE_DIR}
        install -d ${D}${WRC_SERVICE_DIR}/log
	install -m 0755 ${WORKDIR}/run ${D}${WRC_SERVICE_DIR}/run
	install -m 0755 ${WORKDIR}/log-run ${D}${WRC_SERVICE_DIR}/log/run
	touch ${D}${WRC_SERVICE_DIR}/down
}

FILES_${PN} += "${SERVICE_ROOT}"
