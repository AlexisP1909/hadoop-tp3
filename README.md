# Step-by-Step Instructions for Docker Setup and HDFS Operations


## Install (using a powershell terminal)
```powershell
cd deploy
docker build -t hadoop-tp3 .
```

```powershell
cd ..
docker run -d -p 8088:8088 -p 9870:9870 -p 9864:9864 -v ${PWD}\data:/data -v ${PWD}\jars:/jars --rm --name=pouillieute-tp-note hadoop-tp3:latest
```

```powershell
docker exec -it pouillieute-tp-note bash
```

```bash
hdfs dfs -mkdir -p /data/relationships
hdfs dfs -put /data/relationships/data.txt /relationships/
```
```bash
hadoop jar /jars/tpfinal-alexis_pouillieute_job1.jar /data/relationships/data.txt /output/job1
hdfs dfs -cat /output/job1/part-r-00001
```