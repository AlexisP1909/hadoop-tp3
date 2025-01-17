public class RelationshipMapper {
    public void map(LongWritable key, Relationship value, Context context) throws IOException, InterruptedException {
        context.write(new Text(value.getId1()), new Text(value.getId2()));
        context.write(new Text(value.getId2()), new Text(value.getId1()));
    }
    
}
