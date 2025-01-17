package org.epf.hadoop.colfil1;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
public class RelationshipReducer extends Reducer<Text, Text, Text, Text> {
    @Override
    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        ArrayList<String> relatedUsers = new ArrayList();
        for(Text value: values){
            relatedUsers.add(value.toString());
        }
        Collections.sort(relatedUsers);
        String r = String.join(",", relatedUsers);
        context.write(key, new Text(r));//all the users related to the key user separated by ,
    }
    
}
