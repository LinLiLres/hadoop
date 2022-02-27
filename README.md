# We already set up everything in remote 3 nodes. Due to connection reason, 
# if it is not able to demo there, we give another option to run it in docker container

# Follow this file, to build the jar and run it in docker 

# Build the docker image
docker build -t mini1


# Run the docker container
docker run -i -t mini /bin/bash



# In the docker container, we need to copy our local jar file to /home/ubuntu/hadoop, and make input and inputword dir for hdfs use
# Then we can run it in hadoop

hadoop jar ngram.jar WordCount input output 2

hadoop jar log1.jar input output
hadoop jar log2.jar input output
hadoop jar log3.jar input output
hadoop jar log4.jar input output



# Between each hadoop run, we need to run below command to check output, and delete output
hdfs dfs -cat output/*
hdfs dfs -rmr output