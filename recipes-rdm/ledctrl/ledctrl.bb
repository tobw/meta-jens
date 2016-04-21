LICENSE = "GPL-2.0"

LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

SRC_URI = "\
    file://ledplay.sh \
    file://ledbootup.sh \
    file://ledgodown.sh \
    file://ledbootdown.sh \
    file://ledreadyonce.sh \
    file://ledctrl-helper.sh \
    file://ledctrl-helper.sudoers \
"

SRC_URI_append_curie = "\
    file://ledctrl.curie \
"

SRC_URI_append_bohr = "\
    file://ledctrl.bohr \
"

RDEPENDS_${PN} += "daemontools"

inherit useradd

USERADD_PACKAGES = "${PN}"
GROUPADD_PARAM_${PN} = "-r ledctrl"

SERVICE_ROOT = "${sysconfdir}/daemontools/service"
LEDREADY_SERVICE_DIR = "${SERVICE_ROOT}/ledready"

PACKAGE_ARCH = "${MACHINE_ARCH}"

do_compile () {
    cp ${WORKDIR}/ledplay.sh ${WORKDIR}/ledreadyonce.sh \
        ${WORKDIR}/ledgodown.sh ${WORKDIR}/ledbootdown.sh ${WORKDIR}/ledbootup.sh \
        ${WORKDIR}/ledctrl-helper.sh ${WORKDIR}/ledctrl-helper.sudoers ${B}/
    sed -i -e "s,@LEDREADY_SERVICE_DIR@,${LEDREADY_SERVICE_DIR},g" -e "s,@LIBEXEC[@],${libexecdir},g" \
        -e "s,@BINDIR[@],${bindir},g" ${B}/*.sh ${B}/*.sudoers
}

do_install_prepend_curie () {
    # install led functions
    install -d ${D}${libexecdir}
    install -m 0644 ${WORKDIR}/ledctrl.curie ${D}${libexecdir}/ledctrl
}

do_install_prepend_bohr () {
    # install led functions
    install -d ${D}${libexecdir}
    install -m 0644 ${WORKDIR}/ledctrl.bohr ${D}${libexecdir}/ledctrl
}

do_install () {
    install -d ${D}${bindir}
    install -m 0755 ${B}/ledctrl-helper.sh ${D}${bindir}/ledctrl-helper

    # allow %ledctrl to call ledctrl-helper
    install -d ${D}${sysconfdir}/sudoers.d
    install -m 600 ${B}/ledctrl-helper.sudoers ${D}${sysconfdir}/sudoers.d/ledctrl-helper

    # install and setup SysV init scripts
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${B}/ledplay.sh ${B}/ledreadyonce.sh ${B}/ledgodown.sh ${B}/ledbootdown.sh ${D}${sysconfdir}/init.d/

    update-rc.d -r ${D} ledplay.sh start 03 S .
    update-rc.d -r ${D} ledreadyonce.sh start 95 5 .
    update-rc.d -r ${D} ledgodown.sh stop 99 6 .
    update-rc.d -r ${D} ledbootdown.sh stop 15 0 .

    #create daemontools service directory
    install -d ${D}${LEDREADY_SERVICE_DIR}
    
    #install svc run script and make it executable
    install -m 0755 ${B}/ledbootup.sh ${D}${LEDREADY_SERVICE_DIR}/run
    touch ${D}${LEDREADY_SERVICE_DIR}/down
}

FILES_${PN} += "\
    ${SERVICE_ROOT} \
"
