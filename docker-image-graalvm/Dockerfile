FROM amazonlinux:2
LABEL maintainer="Cloudway NV" graalVersion="1.0.0-rc14"

COPY ./runtime /runtime
WORKDIR /runtime

RUN yum -y update && \
    yum -y group install "Development Tools" && \
    yum install -y tar.x86_64 && \
    yum -y install gzip gunzip && \
    yum install -y wget && \
    yum clean all

RUN wget https://github.com/graalvm/graalvm-ce-builds/releases/download/vm-19.3.1/graalvm-ce-java8-linux-amd64-19.3.1.tar.gz && \
    tar xvzf graalvm-ce-java8-linux-amd64-19.3.1.tar.gz && \
    rm graalvm-ce-java8-linux-amd64-19.3.1.tar.gz

ENV PATH="/runtime/graalvm-ce-java8-linux-amd64-19.3.1/bin:${PATH}"