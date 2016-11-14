FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

RRECOMMENDS_${PN} += "init-ifupdown"

SRC_URI += "\
    file://ip6tables-restore.sh \
    file://iptables-restore.sh \
    file://iptables.rules \
"

do_compile_append () {
    cp ${WORKDIR}/iptables-restore.sh ${WORKDIR}/ip6tables-restore.sh ${B}/
    sed -i -e "s,@SBINDIR@,${sbindir},g" ${B}/iptables-restore.sh ${B}/ip6tables-restore.sh
}

do_install_append () {
    install -d ${D}/${sysconfdir}/network/if-pre-up.d
    install -m 755 ${B}/iptables-restore.sh ${D}/${sysconfdir}/network/if-pre-up.d/iptables-restore
    install -m 755 ${B}/ip6tables-restore.sh ${D}/${sysconfdir}/network/if-pre-up.d/ip6tables-restore

    install -m 600 ${WORKDIR}/iptables.rules ${D}/${sysconfdir}/
}
