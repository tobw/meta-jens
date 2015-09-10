XBMC_INSTALL = "xbmc-rdm-hp-addon \
		xbmc-custom-settings \
		hp2sm-xbmc \
	       "
XBMC_INSTALL_append_mx6qcurie = "\
		alsa-utils \
		init-iecset \
	       "

RDEPENDS_append = " packagegroup-base-zeroconf packagegroup-base-alsa"
