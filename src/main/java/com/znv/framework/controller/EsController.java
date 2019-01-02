package com.znv.framework.controller;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author MaHuiming
 * @date 2018/12/21.
 */
@RestController
public class EsController {
    @Autowired
    private TransportClient client;

    @RequestMapping(value = "/insertEsMeteData", method = RequestMethod.GET)
    public ResponseEntity insertEsMeteData(@RequestParam(value = "fsuId", required = true) String fsuId,
    @RequestParam(value = "deviceId", required = true) String deviceId,
    @RequestParam(value = "meteId", required = true) String meteId,
    @RequestParam(value = "type", required = false) int type,
    @RequestParam(value = "measuredval", required = false) float measuredval,
    @RequestParam(value = "setupval", required = false) float setupval,
    @RequestParam(value = "status", required = false) int status,
    @RequestParam(value = "recordtime", required = false) String recordtime) throws IOException {
        // 将参数build成一个json对象
        XContentBuilder content = XContentFactory.jsonBuilder()
        .startObject()
        .field("fsuId", fsuId)
        .field("deviceId", deviceId)
        .field("meteId", meteId)
        .field("measuredval", measuredval)
        .field("setupval", setupval)
        .field("status", status)
        .field("recordtime", recordtime)
        .endObject();

        IndexResponse response = client.prepareIndex("fsu","mete" )
        .setSource(content)
        .get();
        return new ResponseEntity(response.getId(), HttpStatus.OK);
    }

    @DeleteMapping("/deleteEsMeteData")
    public ResponseEntity delete(@RequestParam("id") String id) {
        DeleteResponse response = client.prepareDelete("fsu", "mete", id).get();

        return new ResponseEntity(response.getResult(), HttpStatus.OK);
    }

    @GetMapping("/searchId")
    public ResponseEntity searchById(@RequestParam("id") String id) {
        if (id.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        // 通过索引、类型、id向es进行查询数据
        GetResponse response = client.prepareGet("fsu", "mete", id).get();

        if (!response.isExists()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        // 返回查询到的数据
        return new ResponseEntity(response.getSource(), HttpStatus.OK);
    }

    @PutMapping("/updateEs")
    public ResponseEntity update(@RequestParam(value = "fsuId", required = true) String fsuId,
    @RequestParam(value = "deviceId", required = true) String deviceId,
    @RequestParam(value = "meteId", required = true) String meteId,
    @RequestParam(value = "type", required = false) int type,
    @RequestParam(value = "measuredval", required = false) float measuredval,
    @RequestParam(value = "setupval", required = false) float setupval,
    @RequestParam(value = "status", required = false) int status,
    @RequestParam(value = "recordtime", required = false) String recordtime) {
        UpdateRequest update = new UpdateRequest("fsu", "mete", fsuId);
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder()
            .startObject();

            if (deviceId != null) {
                builder.field("deviceId", deviceId);
            }
            if (meteId != null) {
                builder.field("meteId", meteId);
            }
            if (recordtime != null) {
                builder.field("recordtime", recordtime);
            }

            builder.endObject();
            update.doc(builder);

            UpdateResponse response = client.update(update).get();

            return new ResponseEntity(response.getResult().toString(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
