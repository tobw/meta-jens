SRC_URI_append = " file://IFUPDOWN-Wait-for-kill-wpa-supplicant.patch;patchdir=.."

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

DEPENDS_remove = "dbus"
