FROM jodogne/orthanc

MAINTAINER Joel Zimmerli
LABEL Description="Docker Orthanc Taggy" Vendor="Joel Zimmerli, Universal Software Solution Taggy"

RUN apt-get update
RUN DEBIAN_FRONTEND=noninteractive apt-get -y install libgdcm2-dev libjpeg-dev postgresql-server-dev-all libtiff-dev libopenjpeg-dev libopenslide-dev && rm -rf /var/lib/apt/lists/*



ADD ./build-dicomweb.sh /root/build-dicomweb.sh
RUN bash /root/build-dicomweb.sh "default"

VOLUME [ "/var/lib/orthanc/db" ]
EXPOSE 4242
EXPOSE 8042

ENTRYPOINT [ "Orthanc" ]
CMD [ "/etc/orthanc/" ]