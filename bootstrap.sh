
#!/bin/bash
$hadoop_path/sbin/start-dfs.sh
$hadoop_path/sbin/start-yarn.sh
$hadoop_path/sbin/mr-jobhistory-daemon.sh --config etc/hadoop/ start historyserver

