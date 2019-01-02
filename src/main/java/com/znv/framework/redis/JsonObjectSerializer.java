package com.znv.framework.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.lang.Nullable;

import java.nio.charset.Charset;

/**
 * Fastjson序列化
 * fastjson默认序列化解码有点问题
 * @author MaHuiming
 * @date 2018/12/7.
 */
public class JsonObjectSerializer<T> implements RedisSerializer<T> {
    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    private Class<T> clazz;

    public JsonObjectSerializer(Class<T> clazz) {
        super();
        this.clazz = clazz;
    }


    @Override
    public byte[] serialize(@Nullable T t)  {
        if(t == null ){
            return new byte[0];
        }
        return JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET);
    }

    @Override
    public T deserialize(@Nullable byte[] bytes) throws SerializationException {
        //序列化为空
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
            String str = new String(bytes, DEFAULT_CHARSET);
//        return (T) JSON.toJSONString(str, SerializerFeature.WriteClassName);
        return (T) JSON.parseObject(str, clazz);
    }
}
