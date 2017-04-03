package me.davidvuong.http_api.utils

import com.amazonaws.AmazonWebServiceRequest
import com.amazonaws.auth.{AWSStaticCredentialsProvider, BasicAWSCredentials}
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration
import com.amazonaws.handlers.AsyncHandler
import com.amazonaws.services.sqs.model.{SendMessageRequest, SendMessageResult}
import com.amazonaws.services.sqs.{AmazonSQSAsync, AmazonSQSAsyncClientBuilder}

import scalaz._
import scalaz.concurrent.Task
import me.davidvuong.http_api.config.SqsConfig

case class SqsQueueService(config: SqsConfig) {
  val client: AmazonSQSAsync = AmazonSQSAsyncClientBuilder
    .standard
    .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(config.accessKey, config.secretKey)))
    .withEndpointConfiguration(new EndpointConfiguration(config.url, config.region))
    .build()

  def send(message: String): Task[SendMessageResult] = {
    Task.async[SendMessageResult] { k =>
      val request = new SendMessageRequest(config.url, message)
      client.sendMessageAsync(request, handler[SendMessageRequest, SendMessageResult](k))
    }
  }

  private def handler[E <: AmazonWebServiceRequest, A](k: (Throwable \/ A) => Unit) = new AsyncHandler[E, A] {
    override def onError(exception: Exception):    Unit = k(-\/(exception))
    override def onSuccess(request: E, result: A): Unit = k(\/-(result))
  }
}
