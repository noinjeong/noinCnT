package ssafy;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.mapreduce.Partitioner;

public class MatrixAdd {
	/* 
	Object, Text : input key-value pair type (always same (to get a line of input file))
	Text, IntWritable : output key-value pair type
	*/
	public static class MAddMapper extends Mapper<Object, Text, Text, IntWritable> {
		public void map(Object key, Text value, Context context)
			throws IOException, InterruptedException {
			String[] arr = value.toString().split("\t");
			Text emitkey = new Text(arr[1] + "\t" + arr[2]);
			IntWritable emitval = new IntWritable(Integer.parseInt(arr[3]));
			context.write(emitkey, emitval);
		}
	}
	public static class MAddReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
		public void reduce(Text key, Iterable <IntWritable> values, Context context)
			throws IOException, InterruptedException {
				int sum = 0;
				for(IntWritable val : values){
					sum += val.get();
				}
				context.write(key, new IntWritable (sum));
			}
	}

	/* Main function */
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		String[] otherArgs = new GenericOptionsParser(conf,args).getRemainingArgs();
		if ( otherArgs.length != 2 ) {
			System.err.println("Usage: <in> <out>");
			System.exit(2);
		}
		FileSystem hdfs = FileSystem.get(conf);
		Path output = new Path(otherArgs[1]);
		if(hdfs.exists(output)) hdfs.delete(output, true);

		Job job = new Job(conf,"matrix addition");
		job.setJarByClass(MatrixAdd.class);

		// let hadoop know my map and reduce classes
		job.setMapperClass(MAddMapper.class);
		job.setReducerClass(MAddReducer.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class); // Output value type

		// set number of reduces
		job.setNumReduceTasks(2);
		// Configuration config = job.getConfiguration();
		// config.set("name", "Shim");
		// config.setInt("one", 1);
		// config.setFloat("point_five", (float)0.5);


		// set input and output directories
		FileInputFormat.addInputPath(job,new Path(otherArgs[0]));
		FileOutputFormat.setOutputPath(job,new Path(otherArgs[1]));
		System.exit(job.waitForCompletion(true) ? 0 : 1 );
	}
}

