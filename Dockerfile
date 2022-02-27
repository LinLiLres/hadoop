# Set basic image
FROM ubuntu:18.04

# Assign the user to run
USER root

# password SSH
##################
# ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64
# ENV PATH $PATH:$JAVA_HOME/bin
ENV hadoop_path /home/ubuntu/hadoop
ENV java_path /usr/lib/jvm/java-8-openjdk-arm64
ENV PATH $PATH:$java_path/bin

RUN \
    apt-get update && apt-get install -y \
    ssh \
    rsync \
    vim \
    openjdk-8-jdk

RUN \
    mkdir /home/ubuntu && \
    wget https://archive.apache.org/dist/hadoop/common/hadoop-3.2.1/hadoop-3.2.1.tar.gz && \
    tar -xzf hadoop-3.2.1.tar.gz && \
    mv hadoop-3.2.1 /home/ubuntu/hadoop && \
    # ln -s hadoop-3.2.1 hadoop && \
    echo "export JAVA_HOME=$java_path" >> /home/ubuntu/hadoop/etc/hadoop/hadoop-env.sh && \
    echo "PATH=$PATH:$hadoop_path/bin" >> ~/.bashrc



# ADD /*xml $hadoop_path/etc/hadoop/

ADD bootstrap.sh /etc/bootstrap.sh

CMD ["/etc/bootstrap.sh", "-d"]

EXPOSE 8088 50070 50075 50030 50060

