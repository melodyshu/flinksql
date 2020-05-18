/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.myorg.quickstart;

import java.util.ArrayList;
import java.util.List;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.types.Row;

/**
 * Skeleton for a Flink Streaming Job.
 *
 * <p>For a tutorial how to write a Flink streaming application, check the
 * tutorials and examples on the <a href="https://flink.apache.org/docs/stable/">Flink Website</a>.
 *
 * <p>To package your application into a JAR file for execution, run
 * 'mvn clean package' on the command line.
 *
 * <p>If you change the name of the main class (with the public static void main(String[] args))
 * method, change the respective entry in the POM.xml file (simply search for 'mainClass').
 */
public class StreamingJobWC {

  public static void main(String[] args) throws Exception {
    // set up the streaming execution environment
    StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironment();
    StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);

    List<WC> list = new ArrayList<>();
    //List<Tuple2<String,Long>>  list = new ArrayList<>();
    String wordsStr = "hello flink hello flink2";
    String[] words = wordsStr.split("\\W+");
    for (String word : words) {
     list.add(new WC(word, 1L));
      //list.add(new Tuple2<>(word,1L));
    }

    DataStream<WC> input = env.fromCollection(list);
   // input.print();
    Table table = tableEnv.fromDataStream(input, "word,frequency");

    tableEnv.createTemporaryView("tbs1", input, "word,frequency");
    table.printSchema();

    //System.out.println(table.toString());
    Table result = tableEnv.sqlQuery(
        "SELECT word,count(frequency) as frequency FROM tbs1 group by word");
   // System.out.println(result.toString());
   DataStream<Row> datas = tableEnv.toAppendStream(table, Row.class);

   datas.print();

    env.execute("xxxx");
  }
}
