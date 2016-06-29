LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

SRC_URI = "\
    file://wa-mmc-timeout.${MACHINE} \
"

COMPATIBLE_MACHINE = "(bohr|curie)"
PACKAGE_ARCH = "${MACHINE_ARCH}"

RDEPENDS_${PN} += "daemontools"

SERVICE_ROOT = "${sysconfdir}/daemontools/service"
WA_SERVICE_DIR = "${SERVICE_ROOT}/wa-mmc-timeout"

do_install () {
    install -d ${D}${WA_SERVICE_DIR}
    install -m 0755 ${WORKDIR}/wa-mmc-timeout.${MACHINE} ${D}${WA_SERVICE_DIR}/run
}
