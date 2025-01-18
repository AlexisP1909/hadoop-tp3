# Step-by-Step Instructions for Docker Setup and HDFS Operations


## Install (using a powershell terminal)
```powershell
cd deploy
docker build -t hadoop-tp3 .
cd..
```

```powershell
docker run -d -p 8088:8088 -v ${PWD}\data:/data -v ${PWD}\jars:/jars --rm --name=pouillieute-tp-note hadoop-tp3:latest
```

```powershell
docker exec -it pouillieute-tp-note bash
```

```bash
hdfs dfs -mkdir -p /data/relationships
hdfs dfs -put /data/relationships/data.txt /data/relationships/
```
```bash
hadoop jar /jars/tpfinal-alexis_pouillieute_job1.jar /data/relationships/data.txt /output/job1
hdfs dfs -cat /output/job1/part-r-00001
```
```bash
hadoop jar /jars/tpfinal-alexis_pouillieute_job2.jar /output/job1/ /output/job2
hdfs dfs -cat /output/job2/part-r-00001
```
```bash
hadoop jar /jars/hadoop-tp3-collaborativeFiltering-job3-1.0.jar /output/job2 /output/job3
hdfs dfs -cat /output/job3/part-r-00000
```