public class RelationShipReducer {
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
