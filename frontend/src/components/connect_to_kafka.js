// const kafka = require('kafka-node');
// const client = new kafka.KafkaClient({ 'kafkaHost': "127.0.0.1:9092" });
// const net=require("net");
import * as net from 'net';


// net.isIP('127.0.0.1');
import { KafkaClient,Consumer } from 'kafka-node';
test=net.createConnection("http://127.0.0.1:9092",()=>{});
export function createkafkausercleancart(callback) {
    // const kafka = require('kafka-node');
    const client = new KafkaClient({ 'kafkaHost': "127.0.0.1:9092" });
    //消费者
    let self = this;
    let consumer = new Consumer(client,
        [
            { topic: 'usercleancartSuccess', partition: 0, groupId: "group_topic_bookstore" },

        ], {
        autoCommit: true    //为true，代表自动提交offset
    });
    consumer.on('message', function (message) {
        console.log('consumer receive message:')
        console.log(message);
        callback(message);
    });

    consumer.on('error', function (err) {
        console.log('consumer err:')
        console.log(err);
    });
    return consumer;
}