package com.example.handler;

import org.apache.flink.streaming.connectors.elasticsearch.*;
import org.apache.flink.util.*;
import org.elasticsearch.action.*;
import org.elasticsearch.common.util.concurrent.*;
import org.slf4j.*;

import java.io.*;
import java.net.*;
import java.util.*;

public class RetryRejectedExecutionFailureHandler implements ActionRequestFailureHandler {
	private static final long serialVersionUID = -7423562912824511906L;
	Logger log = LoggerFactory.getLogger(this.getClass().getName());

	public RetryRejectedExecutionFailureHandler() {
	}

	@Override
	public void onFailure(ActionRequest action, Throwable failure, int restStatusCode, RequestIndexer indexer) throws Throwable {
		if (ExceptionUtils.findThrowable(failure, EsRejectedExecutionException.class).isPresent()) {
			indexer.add(new ActionRequest[]{action});
		} else {
			if (ExceptionUtils.findThrowable(failure, SocketTimeoutException.class).isPresent()) {
				// 忽略写入超时，因为ElasticSearchSink 内部会重试请求，不需要抛出来去重启 flink job
				return;
			} else {
				Optional<IOException> exp = ExceptionUtils.findThrowable(failure, IOException.class);
				if (exp.isPresent()) {
					IOException ioExp = exp.get();
					if (ioExp != null && ioExp.getMessage() != null && ioExp.getMessage().contains("max retry timeout")) {
						// request retries exceeded max retry timeout
						// 经过多次不同的节点重试，还是写入失败的，则忽略这个错误，丢失数据。
						log.error(ioExp.getMessage());
						return;
					}
				}
			}
			throw failure;
		}
	}
}